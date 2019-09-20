/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.d7dxfavak.dbtridy;

import java.sql.ResultSet;
import cz.d7dxfavak.expedice.PripojeniDB;
import cz.d7dxfavak.dbfunkce.SQLFunkceObecne;
import cz.d7dxfavak.dbfunkce.SQLFunkceObecne2;
import cz.d7dxfavak.dbfunkce.TextFunkce1;

/**
 *
 * @author Favak
 */
public class TridaVykres1 {

    private int idVykres;
    private int idZakaznik;
    private String cislo;
    private String revize;
    private String nazev;
    private String poznamky;
    private boolean jeRealny;
    
    private String mcv;
    private String mcvOdhad;
    private String tornado;
    private String tornadoOdhad;
    private String dmu;
    private String dmuOdhad;
    private String nazevSoubor;
    private String nazevZakaznik;

    public TridaVykres1() {
        idVykres = 0;
        idZakaznik = 0;
        cislo = "";
        revize = "";
        nazev = "";
        poznamky = "";
        mcv = "";
        mcvOdhad = "";
        tornado = "";
        tornadoOdhad = "";
        dmu = "";
        dmuOdhad = "";
        nazevSoubor = "";
        nazevZakaznik = "";
        jeRealny = true;
    }

    public TridaVykres1(int id) {
        selectData(id);
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
     * @return the idZakaznik
     */
    public int getIdZakaznik() {
        return idZakaznik;
    }

    /**
     * @param idZakaznik the idZakaznik to set
     */
    public void setIdZakaznik(int idZakaznik) {
        this.idZakaznik = idZakaznik;
    }

    /**
     * @return the cislo
     */
    public String getCislo() {
        return cislo;
    }

    /**
     * @param cislo the cislo to set
     */
    public void setCislo(String cislo) {
        this.cislo = cislo;
    }

    /**
     * @return the revize
     */
    public String getRevize() {
        return revize;
    }

    /**
     * @param revize the revize to set
     */
    public void setRevize(String revize) {
        this.revize = revize;
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
     * @return the jeRealny
     */
    public boolean isJeRealny() {
        return jeRealny;
    }

    /**
     * @param jeRealny the jeRealny to set
     */
    public void setJeRealny(boolean jeRealny) {
        this.jeRealny = jeRealny;
    }

    public boolean selectData(int id) {
        try {
            ResultSet q = PripojeniDB.dotazS("SELECT vykresy_id, vykresy_cislo, vykresy_revize, vykresy_poznamky, "
                    + "vykresy_nazev, vykresy_zakaznik_id, vykresy_je_realny "
                    + "FROM spolecne.vykresy "
                    + "WHERE vykresy_id = " + id);
            q.last();
            if (q.getRow() == 1) {
                q.first();

                this.idVykres = SQLFunkceObecne.osetriCteniInt(q.getInt(1));
                this.cislo = SQLFunkceObecne.osetriCteniString(q.getString(2));
                this.revize = SQLFunkceObecne.osetriCteniString(q.getString(3));
                this.poznamky = SQLFunkceObecne.osetriCteniString(q.getString(4));
                this.nazev = SQLFunkceObecne.osetriCteniString(q.getString(5));
                this.idZakaznik = SQLFunkceObecne.osetriCteniInt(q.getInt(6));
                this.jeRealny = q.getBoolean(7);
            }
            return true;
        } catch (Exception e) {
            PripojeniDB.vyjimkaS(e);
            return false;
        } finally {
            PripojeniDB.zavriPrikaz();

        }
    }

    public int insertData() {
        try {
            int rc = 0;
            rc = SQLFunkceObecne2.spustPrikaz("BEGIN");
            ResultSet id = PripojeniDB.dotazS("SELECT MAX(vykresy_id) FROM spolecne.vykresy");
            while (id.next()) {
                this.idVykres = id.getInt(1) + 1;
            }
            //int pocet_mj = Integer.valueOf(PocetKusuTextField1.getText().trim()).intValue();
            int a = PripojeniDB.dotazIUD("INSERT INTO spolecne.vykresy("
                    + "vykresy_id, vykresy_cislo, vykresy_revize, vykresy_poznamky, "
                    + "vykresy_nazev, vykresy_zakaznik_id, vykresy_je_realny) "
                    + "VALUES( " + this.idVykres
                    + ", " + TextFunkce1.osetriZapisTextDB1(this.cislo)
                    + ", " + TextFunkce1.osetriZapisTextDB1(this.revize)
                    + ", " + TextFunkce1.osetriZapisTextDB1(this.poznamky)
                    + ", " + TextFunkce1.osetriZapisTextDB1(this.nazev)
                    + ", " + this.idZakaznik
                    + ", " + this.jeRealny
                    + ")");
            rc = SQLFunkceObecne2.spustPrikaz("COMMIT");
        } catch (Exception e) {
            int rc = SQLFunkceObecne2.spustPrikaz("ROLLBACK");
            System.out.println("Chyba při ukládání dat dodací adresy " +  e.toString());
        }
        return this.idVykres;
    }

    public int updateData() {
        int rc = 0;
        try {
            rc = SQLFunkceObecne2.spustPrikaz("BEGIN");
            String dotaz = "UPDATE spolecne.vykresy "
                    + "SET vykresy_id= " + this.idVykres + ", "
                    + "vykresy_cislo= " + TextFunkce1.osetriZapisTextDB1(this.cislo) + ", "
                    + "vykresy_revize= " + TextFunkce1.osetriZapisTextDB1(this.revize) + ", "
                    + "vykresy_poznamky= " + TextFunkce1.osetriZapisTextDB1(this.poznamky) + ", "
                    + "vykresy_nazev= " + TextFunkce1.osetriZapisTextDB1(this.nazev) + ", "
                    + "vykresy_zakaznik_id= " + this.idZakaznik + ", "
                    + "vykresy_je_realny= true "
                    + "WHERE vykresy_id = " + this.idVykres;

            rc = PripojeniDB.dotazIUD(dotaz);
            rc = SQLFunkceObecne2.spustPrikaz("COMMIT");
        } catch (Exception e) {
            rc = SQLFunkceObecne2.spustPrikaz("ROLLBACK");
        } finally {
            return rc;
        }
    }

    public void deleteData() {
        String dotaz = "DELETE FROM spolecne.vykresy WHERE vykresy_id = " + this.idVykres;
        try {
            int a = PripojeniDB.dotazIUD(dotaz);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @return the mcv
     */
    public String getMcv() {
        return mcv;
    }

    /**
     * @param mcv the mcv to set
     */
    public void setMcv(String mcv) {
        this.mcv = mcv;
    }

    /**
     * @return the mcvOdhad
     */
    public String getMcvOdhad() {
        return mcvOdhad;
    }

    /**
     * @param mcvOdhad the mcvOdhad to set
     */
    public void setMcvOdhad(String mcvOdhad) {
        this.mcvOdhad = mcvOdhad;
    }

    /**
     * @return the tornado
     */
    public String getTornado() {
        return tornado;
    }

    /**
     * @param tornado the tornado to set
     */
    public void setTornado(String tornado) {
        this.tornado = tornado;
    }

    /**
     * @return the tornadoOdhad
     */
    public String getTornadoOdhad() {
        return tornadoOdhad;
    }

    /**
     * @param tornadoOdhad the tornadoOdhad to set
     */
    public void setTornadoOdhad(String tornadoOdhad) {
        this.tornadoOdhad = tornadoOdhad;
    }

    /**
     * @return the dmu
     */
    public String getDmu() {
        return dmu;
    }

    /**
     * @param dmu the dmu to set
     */
    public void setDmu(String dmu) {
        this.dmu = dmu;
    }

    /**
     * @return the dmuOdhad
     */
    public String getDmuOdhad() {
        return dmuOdhad;
    }

    /**
     * @param dmuOdhad the dmuOdhad to set
     */
    public void setDmuOdhad(String dmuOdhad) {
        this.dmuOdhad = dmuOdhad;
    }

    /**
     * @return the nazevSoubor
     */
    public String getNazevSoubor() {
        return nazevSoubor;
    }

    /**
     * @param nazevSoubor the nazevSoubor to set
     */
    public void setNazevSoubor(String nazevSoubor) {
        this.nazevSoubor = nazevSoubor;
    }

    /**
     * @return the nazevZakaznik
     */
    public String getNazevZakaznik() {
        return nazevZakaznik;
    }

    /**
     * @param nazevZakaznik the nazevZakaznik to set
     */
    public void setNazevZakaznik(String nazevZakaznik) {
        this.nazevZakaznik = nazevZakaznik;
    }
}
