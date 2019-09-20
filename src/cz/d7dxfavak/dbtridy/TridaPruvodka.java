/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.d7dxfavak.dbtridy;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import cz.d7dxfavak.expedice.PripojeniDB;
import cz.d7dxfavak.dbfunkce.SQLFunkceObecne;
import cz.d7dxfavak.dbfunkce.SQLFunkceObecne2;
import cz.d7dxfavak.dbfunkce.TextFunkce1;
import java.sql.SQLException;

/**
 *
 * @author Favak
 */
public class TridaPruvodka {

    private int id;
    private String nazev;
    private int idVykres;
    private Date terminDokonceni;
    private int pocetKusu;
    private String poznamky;
    private int pocetKusuSklad;
    private int pocetKusuPolotovar;
    private int idPolotovar;
    private int materialDelka;
    private int idObjednavky;
    private int vyrobenoKusu;
    private boolean uzavrena;
    private String polotovarRozmer;
    private String celkovyCas;
    private TridaVykres1 tv1;
    private TridaObjednavka1 to1;
    private ArrayList<TridaKooperace> arTK1;
    private TridaKooperace aktualniKooperace;
    private int poradi;
    protected java.text.DateFormat df = java.text.DateFormat.getDateInstance();

    public TridaPruvodka() {
        id = 0;
        idObjednavky = 0;
        arTK1 = new ArrayList<>();
        aktualniKooperace = null;
        idVykres = 0;
        idPolotovar = 0;
        pocetKusu = 0;
        pocetKusuPolotovar = 0;
        pocetKusuSklad = 10;
        materialDelka = 0;
        celkovyCas = "";
        polotovarRozmer = "";
        poznamky = "";
        terminDokonceni = null;
        vyrobenoKusu = 0;
        tv1 = null;
        to1 = null;
        uzavrena = false;
        poradi = 0;
    }

    public TridaPruvodka(long id) {
        this.selectData(id);
    }

    public boolean selectData() {
        return selectData(this.id);
    }

    public boolean selectData(long id) {
        try {
            ResultSet q = PripojeniDB.dotazS("SELECT pruvodky_id, pruvodky_nazev, pruvodky_cislo_vykresu, "
                    + "pruvodky_termin_dokonceni, pruvodky_pocet_kusu, pruvodky_poznamky, pruvodky_pocet_kusu_sklad, "
                    + "pruvodky_polotovar_pocet_kusu, pruvodky_polotovar_id, pruvodky_material_prumerna_delka, "
                    + "pruvodky_objednavky_id, pruvodky_vyrobeno_kusu, pruvodky_polotovar_rozmer, pruvodky_celkovy_cas "
                    + "FROM spolecne.pruvodky "
                    + "WHERE pruvodky_id = " + id);
            q.last();
            if (q.getRow() == 1) {
                q.first();
                this.setId(SQLFunkceObecne.osetriCteniInt(q.getInt(1)));
                this.setNazev(SQLFunkceObecne.osetriCteniString(q.getString(2)));
                this.setIdVykres(SQLFunkceObecne.osetriCteniInt(q.getInt(3)));
                this.tv1 = new TridaVykres1(this.getIdVykres());
                this.setTerminDokonceni(q.getDate(4));
                this.setPocetKusu(SQLFunkceObecne.osetriCteniInt(q.getInt(5)));
                this.setPoznamky(SQLFunkceObecne.osetriCteniString(q.getString(6)));
                this.setPocetKusuSklad(SQLFunkceObecne.osetriCteniInt(q.getInt(7)));
                this.setPocetKusuPolotovar(SQLFunkceObecne.osetriCteniInt(q.getInt(8)));
                this.setIdPolotovar(SQLFunkceObecne.osetriCteniInt(q.getInt(9)));
                this.setMaterialDelka(SQLFunkceObecne.osetriCteniInt(q.getInt(10)));
                this.setIdObjednavky(SQLFunkceObecne.osetriCteniInt(q.getInt(11)));
                this.to1 = new TridaObjednavka1(this.getIdObjednavky());
                this.setVyrobenoKusu(SQLFunkceObecne.osetriCteniInt(q.getInt(12)));
                this.setPolotovarRozmer(SQLFunkceObecne.osetriCteniString(q.getString(13)));
                this.setCelkovyCas(SQLFunkceObecne.osetriCteniString(q.getString(14)));
                this.arTK1 = new ArrayList<>();
                this.setAktualniKooperace(null);

            }
            return true;
        } catch (SQLException e) {
            PripojeniDB.vyjimkaS(e);
            return false;
        } finally {
            PripojeniDB.zavriPrikaz();
        }
    }

    public int updateData() {
        int r = -10000;
        int rc = SQLFunkceObecne2.spustPrikaz("BEGIN");
        try {

            String dotaz = " UPDATE spolecne.pruvodky "
                    + "SET pruvodky_id= " + this.id + ", "
                    + "pruvodky_nazev=" + TextFunkce1.osetriZapisTextDB1(this.nazev) + ", "
                    + "pruvodky_cislo_vykresu=" + this.idVykres + ", ";
            if (this.terminDokonceni != null) {
                dotaz += "pruvodky_termin_dokonceni=" + TextFunkce1.osetriZapisTextDB1(df.format(this.terminDokonceni)) + ", ";
            }
            dotaz += "pruvodky_pocet_kusu= " + this.pocetKusu + ", "
                    + "pruvodky_poznamky=" + TextFunkce1.osetriZapisTextDB1(this.poznamky) + ", "
                    + "pruvodky_pocet_kusu_sklad=" + this.pocetKusuSklad + ", "
                    + "pruvodky_polotovar_pocet_kusu= " + this.pocetKusuPolotovar + ", "
                    + "pruvodky_polotovar_id= " + this.idPolotovar + ", "
                    + "pruvodky_material_prumerna_delka= " + this.materialDelka + ", "
                    + "pruvodky_objednavky_id= " + this.idObjednavky + ", "
                    + "pruvodky_vyrobeno_kusu= " + this.vyrobenoKusu + ", "
                    + "pruvodky_polotovar_rozmer=" + TextFunkce1.osetriZapisTextDB1(this.polotovarRozmer) + ", "
                    + "pruvodky_celkovy_cas=" + TextFunkce1.osetriZapisTextDB1(this.celkovyCas) + " "
                    + "WHERE pruvodky_id = " + this.id;

            r = SQLFunkceObecne2.update(dotaz);

            rc = SQLFunkceObecne2.spustPrikaz("COMMIT");
        } catch (Exception e) {
            rc = SQLFunkceObecne2.spustPrikaz("ROLLBACK");
            e.printStackTrace();
        } finally {
            return r;
        }
    }

    public long insertData() {
        int rc = SQLFunkceObecne2.spustPrikaz("BEGIN");
        try {
            ResultSet id = PripojeniDB.dotazS("SELECT COALESCE(MAX(pruvodky_id)+1,1) FROM spolecne.pruvodky");
            while (id.next()) {
                this.id = id.getInt(1);
            }
        } catch (Exception e) {
            rc = SQLFunkceObecne2.spustPrikaz("ROLLBACK");
            e.printStackTrace();
        }
        try {
            String dotaz = "INSERT INTO spolecne.pruvodky("
                    + "pruvodky_id, pruvodky_nazev, pruvodky_cislo_vykresu, pruvodky_termin_dokonceni, "
                    + "pruvodky_pocet_kusu, pruvodky_poznamky, pruvodky_pocet_kusu_sklad, "
                    + "pruvodky_polotovar_pocet_kusu, pruvodky_polotovar_id, pruvodky_material_prumerna_delka, "
                    + "pruvodky_objednavky_id, pruvodky_vyrobeno_kusu, "
                    + "pruvodky_polotovar_rozmer, pruvodky_celkovy_cas) "
                    + "VALUES (" + this.id + ", "
                    + TextFunkce1.osetriZapisTextDB1(this.nazev) + ", "
                    + this.idVykres + ", "
                    + TextFunkce1.osetriZapisDatumDB1(this.terminDokonceni) + ", "
                    + this.pocetKusu + ", "
                    + TextFunkce1.osetriZapisTextDB1(this.poznamky) + ", "
                    + this.pocetKusuSklad + ", "
                    + this.pocetKusuPolotovar + ", "
                    + this.idPolotovar + ", "
                    + this.materialDelka + ", "
                    + this.idObjednavky + ", "
                    + this.vyrobenoKusu + ", "
                    + TextFunkce1.osetriZapisTextDB1(this.polotovarRozmer) + ", "
                    + TextFunkce1.osetriZapisTextDB1(this.celkovyCas) + ") ";
            rc = PripojeniDB.dotazIUD(dotaz);

            rc = SQLFunkceObecne2.spustPrikaz("COMMIT");
        } catch (Exception e) {
            rc = SQLFunkceObecne2.spustPrikaz("ROLLBACK");
            e.printStackTrace();
        } finally {
            return this.idObjednavky;
        }
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the nazev
     */
    public String getNazev() {
        return nazev;
    }

    /**
     * @param nazev the nazev to set
     */
    public void setNazev(String nazev) {
        this.nazev = nazev;
    }

    /**
     * @return the idVykres
     */
    public int getIdVykres() {
        return idVykres;
    }

    /**
     * @param idVykres the idVykres to set
     */
    public void setIdVykres(int idVykres) {
        this.idVykres = idVykres;
    }

    /**
     * @return the terminDokonceni
     */
    public Date getTerminDokonceni() {
        return terminDokonceni;
    }

    /**
     * @param terminDokonceni the terminDokonceni to set
     */
    public void setTerminDokonceni(Date terminDokonceni) {
        this.terminDokonceni = terminDokonceni;
    }

    /**
     * @return the pocetKusu
     */
    public int getPocetKusu() {
        return pocetKusu;
    }

    /**
     * @param pocetKusu the pocetKusu to set
     */
    public void setPocetKusu(int pocetKusu) {
        this.pocetKusu = pocetKusu;
    }

    /**
     * @return the poznamky
     */
    public String getPoznamky() {
        return poznamky;
    }

    /**
     * @param poznamky the poznamky to set
     */
    public void setPoznamky(String poznamky) {
        this.poznamky = poznamky;
    }

    /**
     * @return the pocetKusuSklad
     */
    public int getPocetKusuSklad() {
        return pocetKusuSklad;
    }

    /**
     * @param pocetKusuSklad the pocetKusuSklad to set
     */
    public void setPocetKusuSklad(int pocetKusuSklad) {
        this.pocetKusuSklad = pocetKusuSklad;
    }

    /**
     * @return the pocetKusuPolotovar
     */
    public int getPocetKusuPolotovar() {
        return pocetKusuPolotovar;
    }

    /**
     * @param pocetKusuPolotovar the pocetKusuPolotovar to set
     */
    public void setPocetKusuPolotovar(int pocetKusuPolotovar) {
        this.pocetKusuPolotovar = pocetKusuPolotovar;
    }

    /**
     * @return the idPolotovar
     */
    public int getIdPolotovar() {
        return idPolotovar;
    }

    /**
     * @param idPolotovar the idPolotovar to set
     */
    public void setIdPolotovar(int idPolotovar) {
        this.idPolotovar = idPolotovar;
    }

    /**
     * @return the materialDelka
     */
    public int getMaterialDelka() {
        return materialDelka;
    }

    /**
     * @param materialDelka the materialDelka to set
     */
    public void setMaterialDelka(int materialDelka) {
        this.materialDelka = materialDelka;
    }

    /**
     * @return the idObjednavky
     */
    public int getIdObjednavky() {
        return idObjednavky;
    }

    /**
     * @param idObjednavky the idObjednavky to set
     */
    public void setIdObjednavky(int idObjednavky) {
        this.idObjednavky = idObjednavky;
    }

    /**
     * @return the vyrobenoKusu
     */
    public int getVyrobenoKusu() {
        return vyrobenoKusu;
    }

    /**
     * @param vyrobenoKusu the vyrobenoKusu to set
     */
    public void setVyrobenoKusu(int vyrobenoKusu) {
        this.vyrobenoKusu = vyrobenoKusu;
    }

    /**
     * @return the polotovarRozmer
     */
    public String getPolotovarRozmer() {
        return polotovarRozmer;
    }

    /**
     * @param polotovarRozmer the polotovarRozmer to set
     */
    public void setPolotovarRozmer(String polotovarRozmer) {
        this.polotovarRozmer = polotovarRozmer;
    }

    /**
     * @return the celkovyCas
     */
    public String getCelkovyCas() {
        return celkovyCas;
    }

    /**
     * @param celkovyCas the celkovyCas to set
     */
    public void setCelkovyCas(String celkovyCas) {
        this.celkovyCas = celkovyCas;
    }

    /**
     * @return the tv1
     */
    public TridaVykres1 getTv1() {
        return tv1;
    }

    /**
     * @param tv1 the tv1 to set
     */
    public void setTv1(TridaVykres1 tv1) {
        this.tv1 = tv1;
    }

    /**
     * @return the poradi
     */
    public int getPoradi() {
        return poradi;
    }

    /**
     * @param poradi the poradi to set
     */
    public void setPoradi(int poradi) {
        this.poradi = poradi;
    }

    /**
     * @return the to1
     */
    public TridaObjednavka1 getTo1() {
        return to1;
    }

    /**
     * @param to1 the to1 to set
     */
    public void setTo1(TridaObjednavka1 to1) {
        this.to1 = to1;
    }

    /**
     * @return the uzavrena
     */
    public boolean isUzavrena() {
        return uzavrena;
    }

    /**
     * @param uzavrena the uzavrena to set
     */
    public void setUzavrena(boolean uzavrena) {
        this.uzavrena = uzavrena;
    }

    /**
     * @return the arTK1
     */
    public ArrayList<TridaKooperace> getArTK1() {
        return arTK1;
    }

    /**
     * @param arTK1 the arTK1 to set
     */
    public void setArTK1(ArrayList<TridaKooperace> arTK1) {
        this.arTK1 = arTK1;
    }

    /**
     * @return the aktualniKooperace
     */
    public TridaKooperace getAktualniKooperace() {
        return aktualniKooperace;
    }

    /**
     * @param aktualniKooperace the aktualniKooperace to set
     */
    public void setAktualniKooperace(TridaKooperace aktualniKooperace) {
        this.aktualniKooperace = aktualniKooperace;
    }

    public String getVykres() {
        return (tv1.getCislo() + " " + tv1.getRevize());
    }

    public String getZpracovani() {
        return aktualniKooperace.getPopis();
    }

    public String getDatumOdeslani() {
        if (aktualniKooperace.getDatumOdeslani() != null) {
            return df.format(aktualniKooperace.getDatumOdeslani());
        } else {
            return "";
        }
    }

    public String getDatumPrijeti() {
        if (aktualniKooperace.getDatumPrijeti() != null) {
            return df.format(aktualniKooperace.getDatumPrijeti());
        } else {
            return "";
        }
    }

    public String getOdeslanoKusu() {
        if (aktualniKooperace.getPocetOdeslano()== 0) {
            return null;
        } else {
            return String.valueOf(aktualniKooperace.getPocetOdeslano());
        }
        
    }

    public String getPrijatoKusu() {
        if (aktualniKooperace.getPocetPrijato() == 0) {
            return null;
        } else {
            return String.valueOf(aktualniKooperace.getPocetPrijato());
        }
    }

}
