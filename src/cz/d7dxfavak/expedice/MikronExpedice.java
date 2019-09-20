/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.d7dxfavak.expedice;

import cz.d7dxfavak.dbfunkce.SQLFunkceObecne;
import cz.d7dxfavak.dbfunkce.SQLFunkceObecne2;
import cz.d7dxfavak.dbfunkce.TextFunkce1;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Vector;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import cz.d7dxfavak.dbtridy.TridaKooperace;
import cz.d7dxfavak.dbtridy.TridaObjednavka1;
import cz.d7dxfavak.dbtridy.TridaPruvodka;

/**
 *
 * @author Favak
 */
public class MikronExpedice extends Application {

    protected static ObservableList<TridaObjednavka1> arTO1;
    protected static ObservableList<TridaPruvodka> arTP1;
    private static FXMLLoader fxmlLoader;

    public static PripojeniDB pripojeniDB;

    @Override
    public void start(Stage stage) throws Exception {

        fxmlLoader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("expedice.png")));
        stage.setTitle("Expedice/Kooperace");
        stage.setMaximized(true);
        stage.show();
    }

    protected static void uzavriPruvodky(int idPruvodky, int vyrobenoKusu) {

        for (TridaObjednavka1 tO1 : arTO1) {
            if (tO1.getTp1().getId() == idPruvodky) {
                tO1.getTp1().setVyrobenoKusu(vyrobenoKusu);
            }
        }
        fxmlLoader.<FXMLDocumentController>getController().updateList();

    }

    protected static void potvrditExpedici(String datumExpedice) {
        int rc = SQLFunkceObecne2.spustPrikaz("BEGIN");
        try {
            for (TridaObjednavka1 tO1 : arTO1) {
                if (tO1.getTp1().getId() > 0) {
                    int skladyVyrobyUmisteniId = -1;
                    String celkovyCas = "";
                    ResultSet umisteni1 = PripojeniDB.dotazS(
                            "SELECT sklady_vyrobky_transakce_umisteni_id "
                            + "FROM logistika.sklady_vyrobky_transakce "
                            + "WHERE sklady_vyrobky_transakce_vykres_cislo  = " + TextFunkce1.osetriZapisTextDB1(tO1.getTv1().getCislo()) + " "
                            + "ORDER BY sklady_vyrobky_transakce_log_timestamp DESC LIMIT 1");
                    while (umisteni1.next()) {
                        skladyVyrobyUmisteniId = SQLFunkceObecne.osetriCteniInt(umisteni1.getInt(1));
                    }// konec while

                    if (tO1.getTp1().getVyrobenoKusu() > 0) {
                        naskladnitVyrobky(skladyVyrobyUmisteniId, tO1.getTp1().getVyrobenoKusu(), tO1.getTv1().getCislo());
                        celkovyCas = upravitKapacitu(tO1.getTp1().getId(), tO1.getTp1().getVyrobenoKusu(), tO1.getTv1().getCislo());
                    }

                    String dotaz = "UPDATE spolecne.objednavky "
                            + "SET objednavky_datum_expedice= " + TextFunkce1.osetriZapisTextDB1(datumExpedice) + " "
                            + "WHERE objednavky_id= " + tO1.getId();
                    rc = SQLFunkceObecne2.update(dotaz);
                    dotaz = "UPDATE spolecne.pruvodky "
                            + "SET pruvodky_vyrobeno_kusu = " + tO1.getTp1().getVyrobenoKusu() + ", "
                            + "pruvodky_celkovy_cas = " + TextFunkce1.osetriZapisTextDB1(celkovyCas) + " "
                            + "WHERE pruvodky_id = " + tO1.getTp1().getId();
                    rc = SQLFunkceObecne2.update(dotaz);
                } else {
                    String dotaz = "UPDATE spolecne.objednavky "
                            + "SET objednavky_datum_expedice= " + TextFunkce1.osetriZapisTextDB1(datumExpedice) + " "
                            + "WHERE objednavky_id= " + tO1.getId();
                    rc = SQLFunkceObecne2.update(dotaz);
                }
            }
            rc = SQLFunkceObecne2.spustPrikaz("COMMIT");
        } catch (Exception e) {
            rc = SQLFunkceObecne2.spustPrikaz("ROLLBACK");
            e.printStackTrace();
        }

        arTO1.clear();
        fxmlLoader.<FXMLDocumentController>getController().updateList();
        fxmlLoader.<FXMLDocumentController>getController().TFLieferFocus();
    }

    private static void naskladnitVyrobky(int idUmisteni, int pocetKusu, String cisloVykresu) {
        long transakce_id = -1;
        // System.out.println("Cislo vykresu  : " + cisloVykresu  );
        try {
            ResultSet transakce = PripojeniDB.dotazS("SELECT MAX(sklady_vyrobky_transakce_id) FROM logistika.sklady_vyrobky_transakce");
            while (transakce.next()) {
                transakce_id = transakce.getLong(1) + 1;
            }

            if (idUmisteni <= 1) {
                idUmisteni = 1;
            }

            int a = PripojeniDB.dotazIUD("INSERT INTO logistika.sklady_vyrobky_transakce("
                    + "sklady_vyrobky_transakce_id, "
                    + "sklady_vyrobky_transakce_sklad_id, "
                    + "sklady_vyrobky_transakce_umisteni_id, "
                    + "sklady_vyrobky_transakce_druh_id, "
                    + "sklady_vyrobky_transakce_pocet_mj, "
                    + "sklady_vyrobky_transakce_vykres_cislo) "
                    + "VALUES( " + transakce_id + ", " + 1 + ", " + idUmisteni + ", " + 200 + ", " + pocetKusu + ", "
                    + TextFunkce1.osetriZapisTextDB1(cisloVykresu) + ")");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static String upravitKapacitu(int idPruvodky, int pocetKusu, String cisloVykresu) {

        Vector vrZamestnanci1 = new Vector();
        Vector vsZamestnanci1 = new Vector();
        Vector vsCasVector1 = new Vector();
        Vector vrCasVector1 = new Vector();
        long celkovyCasZamestnanci = 0;
        long celkovyCasTornado = 0;
        long celkovyCasMCV = 0;
        long celkovyCasDMU = 0;
        long celkovyCasDrat = 0;
        int strojeDruh = 0;

        Vector zamestnanciIdPomocne = new Vector();

        try {
            ResultSet zamestnanciTransakce = PripojeniDB.dotazS(
                    "select * from ( "
                    + "select distinct on (zamestnanci_stroje_transakce_zamestnanci_id) zamestnanci_stroje_transakce_zamestnanci_id, "
                    + "zamestnanci_stroje_transakce_log_timestamp "
                    + "from spolecne.zamestnanci_stroje_transakce "
                    + "WHERE zamestnanci_stroje_transakce_pruvodky_id =  " + idPruvodky + "  )  "
                    + "foo order by zamestnanci_stroje_transakce_log_timestamp");

            while (zamestnanciTransakce.next()) {
                zamestnanciIdPomocne.add(zamestnanciTransakce.getInt(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int z = 0; z < zamestnanciIdPomocne.size(); z++) {
            Vector strojeIdPomocne = new Vector();
            try {
                ResultSet strojeTransakce = PripojeniDB.dotazS(
                        "SELECT DISTINCT(zamestnanci_stroje_transakce_stroje_id) zamestnanci_stroje_transakce_stroje_id  "
                        + "FROM spolecne.zamestnanci_stroje_transakce "
                        + "WHERE zamestnanci_stroje_transakce_pruvodky_id = " + idPruvodky + " "
                        + "AND zamestnanci_stroje_transakce_zamestnanci_id = " + (Integer) zamestnanciIdPomocne.get(z) + " "
                        + "ORDER BY zamestnanci_stroje_transakce_stroje_id ASC");
                while (strojeTransakce.next()) {
                    strojeIdPomocne.add(strojeTransakce.getInt(1));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            for (int i = 0; i < strojeIdPomocne.size(); i++) {
                vrZamestnanci1.removeAllElements();
                vsZamestnanci1.removeAllElements();

                try {
                    ResultSet transakceZamestnanci = PripojeniDB.dotazS(
                            "SELECT zamestnanci_stroje_transakce_id, "
                            + "zamestnanci_stroje_transakce_druh_id, "
                            + "stroje_id, "
                            + "stroje_nazev, "
                            + "stroje_druh_stroje, "
                            + "zamestnanci_id, "
                            + "zamestnanci_jmeno || ' ' || zamestnanci_prijmeni AS zamestnanec, "
                            + "zamestnanci_stroje_transakce_log_timestamp, "
                            + "current_timestamp "
                            + "FROM spolecne.zamestnanci_stroje_transakce "
                            + "CROSS JOIN spolecne.zamestnanci "
                            + "CROSS JOIN spolecne.stroje "
                            + "WHERE zamestnanci_stroje_transakce_pruvodky_id = " + idPruvodky + " "
                            + "AND zamestnanci_id = zamestnanci_stroje_transakce_zamestnanci_id "
                            + "AND stroje_id = zamestnanci_stroje_transakce_stroje_id "
                            + "AND stroje_id = " + (Integer) strojeIdPomocne.get(i) + " "
                            + "AND zamestnanci_id = " + (Integer) zamestnanciIdPomocne.get(z) + " "
                            + "ORDER BY zamestnanci_stroje_transakce_log_timestamp ASC,zamestnanci_stroje_transakce_zamestnanci_id, zamestnanci_stroje_transakce_stroje_id");
                    while (transakceZamestnanci.next()) {
                        vsZamestnanci1 = new Vector();
                        vsZamestnanci1.add(transakceZamestnanci.getLong(1));    // transakce
                        vsZamestnanci1.add(transakceZamestnanci.getInt(2));     // druh
                        vsZamestnanci1.add(transakceZamestnanci.getInt(3));     // stroj id
                        vsZamestnanci1.add(transakceZamestnanci.getString(4));  // stroj nazev
                        vsZamestnanci1.add(transakceZamestnanci.getInt(5));     // stroje druh
                        vsZamestnanci1.add(transakceZamestnanci.getInt(6));     // zamestnanec id
                        vsZamestnanci1.add(transakceZamestnanci.getString(7));  // zamestnanec jmeno
                        vsZamestnanci1.add(transakceZamestnanci.getTimestamp(8)); // cas
                        vsZamestnanci1.add(transakceZamestnanci.getTimestamp(9)); // nyni cas
                        vrZamestnanci1.add(vsZamestnanci1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    ResultSet transakceZamestnanci = PripojeniDB.dotazS(
                            "SELECT to_char(MIN(zamestnanci_stroje_transakce_log_timestamp), 'DD.MM.YY HH24:MI') AS casZ, "
                            + "to_char(MAX(zamestnanci_stroje_transakce_log_timestamp), 'DD.MM.YY HH24:MI') AS casK "
                            + "FROM spolecne.zamestnanci_stroje_transakce "
                            + "WHERE zamestnanci_stroje_transakce_pruvodky_id = " + idPruvodky + " "
                            + "AND zamestnanci_stroje_transakce_stroje_id = " + (Integer) strojeIdPomocne.get(i) + " "
                            + "AND zamestnanci_stroje_transakce_zamestnanci_id = " + (Integer) zamestnanciIdPomocne.get(z) + " "
                            + "GROUP BY zamestnanci_stroje_transakce_zamestnanci_id, zamestnanci_stroje_transakce_stroje_id "
                            + "ORDER BY casZ,zamestnanci_stroje_transakce_zamestnanci_id, zamestnanci_stroje_transakce_stroje_id ASC");
                    while (transakceZamestnanci.next()) {
                        vsCasVector1 = new Vector();
                        vsCasVector1.add(transakceZamestnanci.getString(1));    // zac
                        vsCasVector1.add(transakceZamestnanci.getString(2));     // kon
                        vrCasVector1.add(vsCasVector1);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                long time135 = 0;
                long time246 = 0;

                for (int indexTransakce = 0; indexTransakce < vrZamestnanci1.size(); indexTransakce++) {
                    // System.out.println("transakceIndex  " + indexTransakce);
                    if (indexTransakce == 0) {
                        // System.out.println("transakce 0  " + (String) ((Vector) vrZamestnanci1.get(indexTransakce)).get(3));

                        strojeDruh = ((Integer) ((Vector) vrZamestnanci1.get(indexTransakce)).get(4)).intValue();
                    }
                    if ((indexTransakce % 2 == 0) && (indexTransakce != vrZamestnanci1.size() - 1)) {
                        time135 = ((java.sql.Timestamp) ((Vector) vrZamestnanci1.get(indexTransakce)).get(7)).getTime();
                    } else if (indexTransakce % 2 == 1) {
                        time246 += (((java.sql.Timestamp) ((Vector) vrZamestnanci1.get(indexTransakce)).get(7)).getTime() - time135);
                        time135 = 0;
                    } else if ((indexTransakce == vrZamestnanci1.size() - 1) && (indexTransakce % 2 == 0)) {
                        time246 += ((((java.sql.Timestamp) ((Vector) vrZamestnanci1.get(indexTransakce)).get(8)).getTime() - ((java.sql.Timestamp) ((Vector) vrZamestnanci1.get(indexTransakce)).get(7)).getTime()));

                    }
                }

                celkovyCasZamestnanci += time246;
                if (strojeDruh == 1) {
                    celkovyCasMCV += time246;
                } else if ((strojeDruh == 2) || (strojeDruh == 3) || (strojeDruh == 7)) {
                    celkovyCasTornado += time246;
                } else if (strojeDruh == 17) {
                    celkovyCasDMU += time246;
                } else if (strojeDruh == 19) {
                    celkovyCasDrat += time246;
                }
            }
        }
        String celkovyCas = "";
        Timestamp CasTimestamp = new Timestamp(celkovyCasZamestnanci);
        int dnu = Integer.parseInt(new SimpleDateFormat("dd").format(CasTimestamp)) - 1;
        int hodin = Integer.parseInt(new SimpleDateFormat("HH").format(CasTimestamp)) - 1;
        String minsec = new SimpleDateFormat("mm").format(CasTimestamp);
        if (dnu > 0) {
            celkovyCas = (24 * dnu + hodin) + ":" + minsec;
        } else {
            celkovyCas = hodin + ":" + minsec;
        }

        if (SQLFunkceObecne2.selectBooleanPole(
                "SELECT EXISTS (SELECT vyrobni_kapacita_id FROM spolecne.vyrobni_kapacita WHERE vyrobni_kapacita_cislo_vykresu = " + TextFunkce1.osetriZapisTextDB1(cisloVykresu) + ")") == true) {
            String dotaz = "";
            if (celkovyCasDMU != 0) {
                dotaz = "UPDATE spolecne.vyrobni_kapacita "
                        + "SET vyrobni_kapacita_cas_dmu_vyroba = vyrobni_kapacita_cas_dmu_vyroba + " + celkovyCasDMU + ", "
                        + "vyrobni_kapacita_dmu_kusy = vyrobni_kapacita_dmu_kusy + " + pocetKusu + " "
                        + "WHERE vyrobni_kapacita_cislo_vykresu = " + TextFunkce1.osetriZapisTextDB1(cisloVykresu) + " ";
                try {
                    int a = PripojeniDB.dotazIUD(dotaz);
                    //int programyId = 0;

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (celkovyCasMCV != 0) {
                dotaz = "UPDATE spolecne.vyrobni_kapacita "
                        + "SET vyrobni_kapacita_cas_mcv_vyroba = vyrobni_kapacita_cas_mcv_vyroba + " + celkovyCasMCV + ", "
                        + "vyrobni_kapacita_mcv_kusy = vyrobni_kapacita_mcv_kusy + " + pocetKusu + " "
                        + "WHERE vyrobni_kapacita_cislo_vykresu =  " + TextFunkce1.osetriZapisTextDB1(cisloVykresu) + " ";
                try {
                    int a = PripojeniDB.dotazIUD(dotaz);
                    //int programyId = 0;

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (celkovyCasTornado != 0) {
                dotaz = "UPDATE spolecne.vyrobni_kapacita "
                        + "SET vyrobni_kapacita_cas_tornado_vyroba = vyrobni_kapacita_cas_tornado_vyroba + " + celkovyCasTornado + ", "
                        + "vyrobni_kapacita_tornado_kusy = vyrobni_kapacita_tornado_kusy + " + pocetKusu + " "
                        + "WHERE vyrobni_kapacita_cislo_vykresu = " + TextFunkce1.osetriZapisTextDB1(cisloVykresu) + " ";
                try {
                    int a = PripojeniDB.dotazIUD(dotaz);
                    //int programyId = 0;

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (celkovyCasDrat != 0) {
                dotaz = "UPDATE spolecne.vyrobni_kapacita "
                        + "SET vyrobni_kapacita_cas_edm_vyroba = vyrobni_kapacita_cas_edm_vyroba + " + celkovyCasDrat + ", "
                        + "vyrobni_kapacita_edm_kusy = vyrobni_kapacita_edm_kusy + " + pocetKusu + " "
                        + "WHERE vyrobni_kapacita_cislo_vykresu = " + TextFunkce1.osetriZapisTextDB1(cisloVykresu) + " ";
                try {
                    int a = PripojeniDB.dotazIUD(dotaz);
                    //int programyId = 0;

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            long kapacita_id = -1;
            try {
                ResultSet transakce = PripojeniDB.dotazS("SELECT MAX(vyrobni_kapacita_id) FROM spolecne.vyrobni_kapacita");
                while (transakce.next()) {
                    kapacita_id = transakce.getLong(1) + 1;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            String dotaz = "";
            if ((celkovyCasMCV != 0) || (celkovyCasTornado != 0) || (celkovyCasDMU != 0) || (celkovyCasDrat != 0)) {
                dotaz = "INSERT INTO spolecne.vyrobni_kapacita( "
                        + "vyrobni_kapacita_id, "
                        + "vyrobni_kapacita_cislo_vykresu ";
                if (celkovyCasMCV != 0) {
                    dotaz += ", vyrobni_kapacita_cas_mcv "
                            + ", vyrobni_kapacita_cas_mcv_vyroba "
                            + ", vyrobni_kapacita_mcv_kusy ";
                }
                if (celkovyCasTornado != 0) {
                    dotaz += ", vyrobni_kapacita_cas_tornado "
                            + ", vyrobni_kapacita_cas_tornado_vyroba "
                            + ", vyrobni_kapacita_tornado_kusy ";
                }
                if (celkovyCasDMU != 0) {
                    dotaz += ", vyrobni_kapacita_cas_dmu "
                            + ", vyrobni_kapacita_cas_dmu_vyroba "
                            + ", vyrobni_kapacita_dmu_kusy ";
                }
                if (celkovyCasDrat != 0) {
                    dotaz += ", vyrobni_kapacita_cas_edm "
                            + ", vyrobni_kapacita_cas_edm_vyroba "
                            + ", vyrobni_kapacita_edm_kusy ";
                }
                dotaz += " ) VALUES ( " + kapacita_id + ", " + TextFunkce1.osetriZapisTextDB1(cisloVykresu) + " ";
                if (celkovyCasMCV != 0) {
                    dotaz += ", 0 "
                            + ", " + celkovyCasMCV + " "
                            + ", " + pocetKusu + " ";
                }
                if (celkovyCasTornado != 0) {
                    dotaz += ", 0 "
                            + ", " + celkovyCasTornado + " "
                            + ", " + pocetKusu + " ";
                }
                if (celkovyCasDMU != 0) {
                    dotaz += ", 0 "
                            + ", " + celkovyCasDMU + " "
                            + ", " + pocetKusu + " ";
                }
                if (celkovyCasDrat != 0) {
                    dotaz += ", 0 "
                            + ", " + celkovyCasDrat + " "
                            + ", " + pocetKusu + " ";
                }
                dotaz += ")";
            }

            try {
                int a = PripojeniDB.dotazIUD(dotaz);
                //int programyId = 0;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return celkovyCas;

    }

    protected static TridaPruvodka nactiDataPruvodky(int idPruvodky) {
        TridaPruvodka tp1 = new TridaPruvodka(idPruvodky);
        try {

            if (SQLFunkceObecne2.selectBooleanPole(
                    "SELECT EXISTS (SELECT pruvodka_kooperace_pruvodka_id FROM spolecne.pruvodka_kooperace WHERE "
                    + "pruvodka_kooperace_datum_prijeti IS NULL "
                    + "AND pruvodka_kooperace_pruvodka_id = " + idPruvodky + ")") == true) {
                ResultSet q = PripojeniDB.dotazS("SELECT pracovni_postup_pruvodka_popis, pracovni_postup_pruvodka_poradi, current_date "
                        + "FROM spolecne.pracovni_postup_pruvodka "
                        + "WHERE pracovni_postup_pruvodka_pruvodka_id = " + idPruvodky + " "
                        + "AND pracovni_postup_pruvodka_druh_stroje_id = 20 "
                        + "AND pracovni_postup_pruvodka_poradi = (SELECT pruvodka_kooperace_poradi FROM spolecne.pruvodka_kooperace "
                        + "WHERE pruvodka_kooperace_pruvodka_id = pracovni_postup_pruvodka_pruvodka_id AND pruvodka_kooperace_datum_prijeti IS NULL) "
                        + "ORDER BY pracovni_postup_pruvodka_poradi "
                        + "LIMIT 1");
                while (q.next()) {
                    TridaKooperace tk1 = new TridaKooperace();
                    tk1.setIdPruvodka(idPruvodky);
                    tk1.setPoradi(SQLFunkceObecne.osetriCteniInt(q.getInt(2)));
                    tk1.setPopis(SQLFunkceObecne.osetriCteniString(q.getString(1)));
                    tk1.setDatumPrijeti(q.getDate(3));
                    tk1.setRozpracovana(true);
                    tp1.setAktualniKooperace(tk1);
                }

            } else {
                ResultSet q = PripojeniDB.dotazS(
                        "SELECT pracovni_postup_pruvodka_popis, pracovni_postup_pruvodka_poradi, current_date "
                        + "FROM spolecne.pracovni_postup_pruvodka "
                        + "WHERE pracovni_postup_pruvodka_pruvodka_id = " + idPruvodky + " "
                        + "AND pracovni_postup_pruvodka_druh_stroje_id = 20 "
                        + "AND pracovni_postup_pruvodka_poradi NOT IN (SELECT pruvodka_kooperace_poradi FROM spolecne.pruvodka_kooperace "
                        + "WHERE pruvodka_kooperace_pruvodka_id = pracovni_postup_pruvodka_pruvodka_id) "
                        + "ORDER BY pracovni_postup_pruvodka_poradi "
                        + "LIMIT 1");
                while (q.next()) {
                    TridaKooperace tk1 = new TridaKooperace();
                    tk1.setIdPruvodka(idPruvodky);
                    tk1.setPoradi(SQLFunkceObecne.osetriCteniInt(q.getInt(2)));
                    tk1.setPopis(SQLFunkceObecne.osetriCteniString(q.getString(1)));
                    tk1.setDatumOdeslani(q.getDate(3));
                    tk1.setRozpracovana(false);
                    tp1.setAktualniKooperace(tk1);

                }// konec while
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return tp1;
    }

    protected static void ulozKooperaci(TridaPruvodka tp1) {
        TridaKooperace tk1 = tp1.getAktualniKooperace();
        String dotaz = "";
        try {
            if (tk1.isRozpracovana()) {
                dotaz = "UPDATE spolecne.pruvodka_kooperace "
                        + "SET pruvodka_kooperace_datum_prijeti = " + TextFunkce1.osetriZapisDatumDB1(tk1.getDatumPrijeti()) + ", "
                        + "pruvodka_kooperace_pocet_prijeti = " + tk1.getPocetPrijato() + " "
                        + "WHERE pruvodka_kooperace_pruvodka_id = " + tk1.getIdPruvodka() + " "
                        + "AND pruvodka_kooperace_poradi = " + tk1.getPoradi();
            } else {
                dotaz = "INSERT INTO spolecne.pruvodka_kooperace( "
                        + "pruvodka_kooperace_pruvodka_id, pruvodka_kooperace_poradi, pruvodka_kooperace_datum_odeslani, "
                        + "pruvodka_kooperace_pocet_odeslani, pruvodka_kooperace_poznamky) "
                        + "VALUES (" + tk1.getIdPruvodka() + ", " + tk1.getPoradi() + ", " + TextFunkce1.osetriZapisDatumDB1(tk1.getDatumOdeslani()) + ", "
                        + tk1.getPocetOdeslano() + ", null)";

            }
            int a = PripojeniDB.dotazIUD(dotaz);
        } catch (Exception e) {
            e.printStackTrace();
        }

        fxmlLoader.<FXMLDocumentController>getController().nacistPruvodkyKooperace();

    }

    /**
     * @param args the command line arguments
     * args[1] - DBAdress : "jdbc:postgresql://localhost:54325/database1?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory"
     * args[3] - DBusername
     * args[5] - DBpassword
     */
    public static void main(String[] args) {
        pripojeniDB = new PripojeniDB();      

        int rc = pripojeniDB.navazSpojeniDB(args[1], args[3], args[5]);

        if (rc == 1) {
            System.out.println("Selhání připojení - "
                    + "Selhalo připojení k databázi. Pravděpodobně bylo zadáno chybné jméno nebo heslo\n"
                    + "nebo byl detekován pokus o současné navázání více než jednoho spojení k databázi");
            launch(args);
        }
    }

}
