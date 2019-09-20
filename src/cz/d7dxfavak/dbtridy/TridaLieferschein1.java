/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.d7dxfavak.dbtridy;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import cz.d7dxfavak.expedice.PripojeniDB;
import cz.d7dxfavak.dbfunkce.SQLFunkceObecne;
import cz.d7dxfavak.dbfunkce.SQLFunkceObecne2;
import cz.d7dxfavak.dbfunkce.TextFunkce1;

/**
 *
 * @author Favak
 */
public class TridaLieferschein1 {

    private int idLieferschein;
    private String cisloLieferschein;
    private int idZakaznik;
    private Date datumVystaveni;
    private boolean fixovana;
    private String poznamky;
    private int pocetPolozek;
    private int pocetKusu;
    private String cistaVaha;
    private String hrubaVaha;
    private String cisloAwb;
    private String comCode;
    private String dopravce;
    private boolean deklarovana;
    private String stavLieferschein;
    private int idAdresa;
    private ArrayList<TridaObjednavka1> arTObj1;
    private String pdf;
    private String vystavitel;
    private String nazev1Radek;
    private String nazev2Radek;
    private String adresa;
    private String mesto;
    private String psc;
    private String kontaktOsoba;
    private String stat;
    private String kontaktTelefon;
    private String zakaznikJmenoFirmy;
    private boolean zadanaAdresa;

    public TridaLieferschein1() {
        arTObj1 = new ArrayList<TridaObjednavka1>();
    }

    public long insertData() {
        boolean existuje = false;

        try {
            ResultSet exist = PripojeniDB.dotazS("SELECT EXISTS "
                    + "(SELECT lieferscheiny_id FROM spolecne.lieferscheiny WHERE lieferscheiny_cislo_lieferscheinu = "
                    + TextFunkce1.osetriZapisTextDB1(this.cisloLieferschein) + ")");
            while (exist.next()) {
                existuje = exist.getBoolean(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            existuje = false;
        }      
        if (existuje == false) {
            int rc = SQLFunkceObecne2.spustPrikaz("BEGIN");
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            java.util.Date date = new java.util.Date();

            try {
                ResultSet id = PripojeniDB.dotazS("SELECT MAX(lieferscheiny_id) FROM spolecne.lieferscheiny");
                while (id.next()) {
                    this.idLieferschein = id.getInt(1) + 1;
                }
            } catch (Exception e) {
                rc = SQLFunkceObecne2.spustPrikaz("ROLLBACK");
                this.idLieferschein = -1;
            }
            // TODO kontrola datumu
            try {
                String dotazFakt = "INSERT INTO spolecne.lieferscheiny("
                        + "lieferscheiny_id, "
                        + "lieferscheiny_cislo_lieferscheinu, "
                        + "lieferscheiny_zakaznik_id, "
                        + "lieferscheiny_datum_vystaveni, "
                        + "lieferscheiny_poznamka, "
                        + "lieferscheiny_vystavil, "
                        + "lieferscheiny_com_code, "
                        + "lieferscheiny_cista_vaha, "
                        + "lieferscheiny_hruba_vaha, "
                        + "lieferscheiny_dodaci_adresa, "
                        + "lieferscheiny_adresa, "
                        + "lieferscheiny_mesto, "
                        + "lieferscheiny_psc, "
                        + "lieferscheiny_kontakt_osoba, "
                        + "lieferscheiny_kontakt_telefon, "
                        + "lieferscheiny_stat, "
                        + "lieferscheiny_1radek, "
                        + "lieferscheiny_2radek, "
                        + "lieferscheiny_zakaznik_jmeno, "
                        + "lieferscheiny_zadana_adresa) "
                        + "VALUES( " + this.idLieferschein
                        + ", " + TextFunkce1.osetriZapisTextDB1(this.cisloLieferschein)
                        + ", " + this.idZakaznik
                        + ", " + TextFunkce1.osetriZapisTextDB1(dateFormat.format(this.datumVystaveni))
                        + ", " + TextFunkce1.osetriZapisTextDB1(this.poznamky)
                        + ", " + TextFunkce1.osetriZapisTextDB1(this.vystavitel)
                        + ", " + TextFunkce1.osetriZapisTextDB1(this.comCode)
                        + ", " + TextFunkce1.osetriZapisTextDB1(this.cistaVaha)
                        + ", " + TextFunkce1.osetriZapisTextDB1(this.hrubaVaha)
                        + ", " + ((this.idAdresa == 0) ? null : this.idAdresa)
                        + ", " + TextFunkce1.osetriZapisTextDB1(this.adresa)
                        + ", " + TextFunkce1.osetriZapisTextDB1(this.mesto)
                        + ", " + TextFunkce1.osetriZapisTextDB1(this.psc)
                        + ", " + TextFunkce1.osetriZapisTextDB1(this.kontaktOsoba)
                        + ", " + TextFunkce1.osetriZapisTextDB1(this.kontaktTelefon)
                        + ", " + TextFunkce1.osetriZapisTextDB1(this.stat)
                        + ", " + TextFunkce1.osetriZapisTextDB1(this.nazev1Radek)
                        + ", " + TextFunkce1.osetriZapisTextDB1(this.nazev2Radek)
                        + ", " + TextFunkce1.osetriZapisTextDB1(this.zakaznikJmenoFirmy) + ", true)";
                int a = PripojeniDB.dotazIUD(dotazFakt);
            } catch (Exception e) {
                rc = SQLFunkceObecne2.spustPrikaz("ROLLBACK");
                e.printStackTrace();
            }

            for (int objIndex = 0; objIndex < arTObj1.size(); objIndex++) {
                try {

                    int a = PripojeniDB.dotazIUD("INSERT INTO spolecne.vazba_lieferscheiny_objednavky("
                            + "vazba_lieferscheiny_objednavky_lieferscheiny_id, "
                            + "vazba_lieferscheiny_objednavky_objednavky_id, "
                            + "vazba_lieferscheiny_objednavky_kusu, "
                            + "vazba_lieferscheiny_objednavky_poradi) "
                            + "VALUES( " + this.idLieferschein + ", " + arTObj1.get(objIndex).getId() + ", "
                            + arTObj1.get(objIndex).getPocetObjednanychKusu() + ", "
                            + arTObj1.get(objIndex).getPoradi() + ")");
                } catch (Exception e) {
                    rc = SQLFunkceObecne2.spustPrikaz("ROLLBACK");
                    e.printStackTrace();
                }
            }
            rc = SQLFunkceObecne2.spustPrikaz("COMMIT");
        } else {
            System.out.println( "Chyba při ukládání lieferscheinu, " + "Lieferschein s daným číslem již existuje,"
                    + "změnte prosím číslo lieferscheinu");
        }

        return idLieferschein;
    }

    public int updateData() {
        int r = -10000;
        int rc = SQLFunkceObecne2.spustPrikaz("BEGIN");
        try {
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            java.util.Date date = new java.util.Date();
            String dotaz = "UPDATE spolecne.lieferscheiny "
                    + " SET lieferscheiny_cislo_lieferscheinu= " + TextFunkce1.osetriZapisTextDB1(this.cisloLieferschein) + ", "
                    + "lieferscheiny_zakaznik_id= " + this.idZakaznik + ", "
                    + "lieferscheiny_datum_vystaveni= " + TextFunkce1.osetriZapisTextDB1(dateFormat.format(this.datumVystaveni)) + ", "
                    + "lieferscheiny_vystavil= " + TextFunkce1.osetriZapisTextDB1(this.vystavitel) + ", "
                    + "lieferscheiny_com_code= " + TextFunkce1.osetriZapisTextDB1(this.comCode) + ", "
                    + "lieferscheiny_cista_vaha = " + TextFunkce1.osetriZapisTextDB1(this.cistaVaha) + ", "
                    + "lieferscheiny_hruba_vaha= " + TextFunkce1.osetriZapisTextDB1(this.hrubaVaha) + ", "
                    + "lieferscheiny_poznamka = " + TextFunkce1.osetriZapisTextDB1(this.poznamky) + ", "
                    + "lieferscheiny_dodaci_adresa = " + ((this.idAdresa == 0) ? null : this.idAdresa) + ", "
                    + "lieferscheiny_adresa = " + TextFunkce1.osetriZapisTextDB1(this.adresa) + ", "
                    + "lieferscheiny_mesto = " + TextFunkce1.osetriZapisTextDB1(this.mesto) + ", "
                    + "lieferscheiny_psc = " + TextFunkce1.osetriZapisTextDB1(this.psc) + ", "
                    + "lieferscheiny_kontakt_osoba = " + TextFunkce1.osetriZapisTextDB1(this.kontaktOsoba) + ", "
                    + "lieferscheiny_kontakt_telefon = " + TextFunkce1.osetriZapisTextDB1(this.kontaktTelefon) + ", "
                    + "lieferscheiny_stat = " + TextFunkce1.osetriZapisTextDB1(this.stat) + ", "
                    + "lieferscheiny_1radek = " + TextFunkce1.osetriZapisTextDB1(this.nazev1Radek) + ", "
                    + "lieferscheiny_2radek = " + TextFunkce1.osetriZapisTextDB1(this.nazev2Radek) + ", "
                    + "lieferscheiny_zakaznik_jmeno = " + TextFunkce1.osetriZapisTextDB1(this.zakaznikJmenoFirmy) + ", "
                    + "lieferscheiny_zadana_adresa = true "
                    + "WHERE lieferscheiny_id = " + this.idLieferschein;
            
            r = PripojeniDB.dotazIUD(dotaz);

            String dotazLiefDelete = "DELETE FROM spolecne.vazba_lieferscheiny_objednavky "
                    + "WHERE vazba_lieferscheiny_objednavky_lieferscheiny_id = " + this.idLieferschein;

            r = PripojeniDB.dotazIUD(dotazLiefDelete);

            for (int objIndex = 0; objIndex < arTObj1.size(); objIndex++) {
                int a = PripojeniDB.dotazIUD("INSERT INTO spolecne.vazba_lieferscheiny_objednavky("
                        + "vazba_lieferscheiny_objednavky_lieferscheiny_id, "
                        + "vazba_lieferscheiny_objednavky_objednavky_id, "
                        + "vazba_lieferscheiny_objednavky_kusu, "
                        + "vazba_lieferscheiny_objednavky_poradi) "
                        + "VALUES( " + this.idLieferschein + ", " + arTObj1.get(objIndex).getId() + ", "
                        + arTObj1.get(objIndex).getPocetObjednanychKusu() + ", "
                        + arTObj1.get(objIndex).getPoradi() + ")");
            }

        } catch (Exception e) {
            rc = SQLFunkceObecne2.spustPrikaz("ROLLBACK");
            System.out.println("Chyba při ukládání dodacího listu , " + " Nepodařilo se uložit novou verzi dodacího listu, zůstane zachovaná stará verze!");
            e.printStackTrace();
        } finally {
            rc = SQLFunkceObecne2.spustPrikaz("COMMIT");
            return r;
        }
    }

    public boolean selectData() {
        return selectData(this.cisloLieferschein);
    }

    public boolean selectData(String cislo) {
        try {
            ResultSet q = PripojeniDB.dotazS("SELECT lieferscheiny_id, lieferscheiny_cislo_lieferscheinu, lieferscheiny_zakaznik_id, " // 1 - 3
                    + "lieferscheiny_datum_vystaveni, lieferscheiny_poznamka, lieferscheiny_vystavil, " // 4 - 6
                    + "lieferscheiny_fixovany, lieferscheiny_com_code, " // 7 - 8
                    + "lieferscheiny_cista_vaha, lieferscheiny_hruba_vaha, lieferscheiny_dodaci_adresa, " // 9 - 11
                    + "lieferscheiny_adresa, lieferscheiny_mesto, lieferscheiny_psc, " // 12 - 14
                    + "lieferscheiny_kontakt_osoba, lieferscheiny_kontakt_telefon, lieferscheiny_stat, " // 15 - 17
                    + "lieferscheiny_zadana_adresa, lieferscheiny_1radek, lieferscheiny_2radek, lieferscheiny_zakaznik_jmeno " // 18 - 21
                    + "FROM spolecne.lieferscheiny "
                    + "WHERE lieferscheiny_cislo_lieferscheinu = " + TextFunkce1.osetriZapisTextDB1(cislo));
            q.last();
            if (q.getRow() == 1) {
                q.first();
                this.setIdLieferschein(SQLFunkceObecne.osetriCteniInt(q.getInt(1)));
                this.setCisloLieferschein(SQLFunkceObecne.osetriCteniString(q.getString(2)));
                this.setIdZakaznik(SQLFunkceObecne.osetriCteniInt(q.getInt(3)));
                this.setDatumVystaveni(q.getDate(4));
                this.setPoznamky(SQLFunkceObecne.osetriCteniString(q.getString(5)));
                this.setVystavitel(SQLFunkceObecne.osetriCteniString(q.getString(6)));
                this.setFixovana(q.getBoolean(7));
                this.setComCode(SQLFunkceObecne.osetriCteniString(q.getString(8)));
                this.setCistaVaha(SQLFunkceObecne.osetriCteniString(q.getString(9)));
                this.setHrubaVaha(SQLFunkceObecne.osetriCteniString(q.getString(10)));
                this.setIdAdresa(SQLFunkceObecne.osetriCteniInt(q.getInt(11)));
                this.setAdresa(SQLFunkceObecne.osetriCteniString(q.getString(12)));
                this.setMesto(SQLFunkceObecne.osetriCteniString(q.getString(13)));
                this.setPsc(SQLFunkceObecne.osetriCteniString(q.getString(14)));
                this.setKontaktOsoba(SQLFunkceObecne.osetriCteniString(q.getString(15)));
                this.setKontaktTelefon(SQLFunkceObecne.osetriCteniString(q.getString(16)));
                this.setStat(SQLFunkceObecne.osetriCteniString(q.getString(17)));
                this.setZadanaAdresa(q.getBoolean(18));
                this.setNazev1Radek(SQLFunkceObecne.osetriCteniString(q.getString(19)));
                this.setNazev2Radek(SQLFunkceObecne.osetriCteniString(q.getString(20)));
                this.setZakaznikJmenoFirmy(SQLFunkceObecne.osetriCteniString(q.getString(21)));
            }
            return true;
        } catch (Exception e) {
            PripojeniDB.vyjimkaS(e);
            return false;
        } finally {
            PripojeniDB.zavriPrikaz();
        }
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
     * @return the datumVystaveni
     */
    public Date getDatumVystaveni() {
        return datumVystaveni;
    }

    /**
     * @param datumVystaveni the datumVystaveni to set
     */
    public void setDatumVystaveni(Date datumVystaveni) {
        this.datumVystaveni = datumVystaveni;
    }

    /**
     * @return the idLieferschein
     */
    public int getIdLieferschein() {
        return idLieferschein;
    }

    /**
     * @param idLieferschein the idLieferschein to set
     */
    public void setIdLieferschein(int idLieferschein) {
        this.idLieferschein = idLieferschein;
    }

    /**
     * @return the fixovana
     */
    public boolean isFixovana() {
        return fixovana;
    }

    /**
     * @param fixovana the fixovana to set
     */
    public void setFixovana(boolean fixovana) {
        this.fixovana = fixovana;
    }

    /**
     * @return the pocetPolozek
     */
    public int getPocetPolozek() {
        return pocetPolozek;
    }

    /**
     * @param pocetPolozek the pocetPolozek to set
     */
    public void setPocetPolozek(int pocetPolozek) {
        this.pocetPolozek = pocetPolozek;
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
     * @return the cistaVaha
     */
    public String getCistaVaha() {
        return cistaVaha;
    }

    /**
     * @param cistaVaha the cistaVaha to set
     */
    public void setCistaVaha(String cistaVaha) {
        this.cistaVaha = cistaVaha;
    }

    /**
     * @return the hrubaVaha
     */
    public String getHrubaVaha() {
        return hrubaVaha;
    }

    /**
     * @param hrubaVaha the hrubaVaha to set
     */
    public void setHrubaVaha(String hrubaVaha) {
        this.hrubaVaha = hrubaVaha;
    }

    /**
     * @return the cisloAwb
     */
    public String getCisloAwb() {
        return cisloAwb;
    }

    /**
     * @param cisloAwb the cisloAwb to set
     */
    public void setCisloAwb(String cisloAwb) {
        this.cisloAwb = cisloAwb;
    }

    /**
     * @return the comCode
     */
    public String getComCode() {
        return comCode;
    }

    /**
     * @param comCode the comCode to set
     */
    public void setComCode(String comCode) {
        this.comCode = comCode;
    }

    /**
     * @return the dopravce
     */
    public String getDopravce() {
        return dopravce;
    }

    /**
     * @param dopravce the dopravce to set
     */
    public void setDopravce(String dopravce) {
        this.dopravce = dopravce;
    }

    /**
     * @return the deklarovana
     */
    public boolean isDeklarovana() {
        return deklarovana;
    }

    /**
     * @param deklarovana the deklarovana to set
     */
    public void setDeklarovana(boolean deklarovana) {
        this.deklarovana = deklarovana;
    }

    /**
     * @return the idAdresa
     */
    public int getIdAdresa() {
        return idAdresa;
    }

    /**
     * @param idAdresa the idAdresa to set
     */
    public void setIdAdresa(int idAdresa) {
        this.idAdresa = idAdresa;
    }

    /**
     * @return the arTObj1
     */
    public ArrayList<TridaObjednavka1> getArTObj1() {
        return arTObj1;
    }

    /**
     * @param arTObj1 the arTObj1 to set
     */
    public void setArTObj1(ArrayList<TridaObjednavka1> arTObj1) {
        this.arTObj1 = arTObj1;
    }

    /**
     * @return the cisloLieferschein
     */
    public String getCisloLieferschein() {
        return cisloLieferschein;
    }

    /**
     * @param cisloLieferschein the cisloLieferschein to set
     */
    public void setCisloLieferschein(String cisloLieferschein) {
        this.cisloLieferschein = cisloLieferschein;
    }

    /**
     * @return the pdf
     */
    public String getPdf() {
        return pdf;
    }

    /**
     * @param pdf the pdf to set
     */
    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    /**
     * @return the stavFaktury
     */
    public String getStavLieferschein() {
        return stavLieferschein;
    }

    /**
     * @param stavLieferschein the stavFaktury to set
     */
    public void setStavLieferschein(String stavLieferschein) {
        this.stavLieferschein = stavLieferschein;
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
     * @return the vystavitel
     */
    public String getVystavitel() {
        return vystavitel;
    }

    /**
     * @param vystavitel the vystavitel to set
     */
    public void setVystavitel(String vystavitel) {
        this.vystavitel = vystavitel;
    }

    /**
     * @return the adresa
     */
    public String getAdresa() {
        return adresa;
    }

    /**
     * @param adresa the adresa to set
     */
    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    /**
     * @return the mesto
     */
    public String getMesto() {
        return mesto;
    }

    /**
     * @param mesto the mesto to set
     */
    public void setMesto(String mesto) {
        this.mesto = mesto;
    }

    /**
     * @return the psc
     */
    public String getPsc() {
        return psc;
    }

    /**
     * @param psc the psc to set
     */
    public void setPsc(String psc) {
        this.psc = psc;
    }

    /**
     * @return the kontaktOsoba
     */
    public String getKontaktOsoba() {
        return kontaktOsoba;
    }

    /**
     * @param kontaktOsoba the kontaktOsoba to set
     */
    public void setKontaktOsoba(String kontaktOsoba) {
        this.kontaktOsoba = kontaktOsoba;
    }

    /**
     * @return the stat
     */
    public String getStat() {
        return stat;
    }

    /**
     * @param stat the stat to set
     */
    public void setStat(String stat) {
        this.stat = stat;
    }

    /**
     * @return the zadanaAdresa
     */
    public boolean isZadanaAdresa() {
        return zadanaAdresa;
    }

    /**
     * @param zadanaAdresa the zadanaAdresa to set
     */
    public void setZadanaAdresa(boolean zadanaAdresa) {
        this.zadanaAdresa = zadanaAdresa;
    }

    /**
     * @return the nazev1Radek
     */
    public String getNazev1Radek() {
        return nazev1Radek;
    }

    /**
     * @param nazev1Radek the nazev1Radek to set
     */
    public void setNazev1Radek(String nazev1Radek) {
        this.nazev1Radek = nazev1Radek;
    }

    /**
     * @return the nazev2Radek
     */
    public String getNazev2Radek() {
        return nazev2Radek;
    }

    /**
     * @param nazev2Radek the nazev2Radek to set
     */
    public void setNazev2Radek(String nazev2Radek) {
        this.nazev2Radek = nazev2Radek;
    }

    /**
     * @return the kontaktTelefon
     */
    public String getKontaktTelefon() {
        return kontaktTelefon;
    }

    /**
     * @param kontaktTelefon the kontaktTelefon to set
     */
    public void setKontaktTelefon(String kontaktTelefon) {
        this.kontaktTelefon = kontaktTelefon;
    }

    /**
     * @return the zakaznikJmenoFirmy
     */
    public String getZakaznikJmenoFirmy() {
        return zakaznikJmenoFirmy;
    }

    /**
     * @param zakaznikJmenoFirmy the zakaznikJmenoFirmy to set
     */
    public void setZakaznikJmenoFirmy(String zakaznikJmenoFirmy) {
        this.zakaznikJmenoFirmy = zakaznikJmenoFirmy;
    }
}
