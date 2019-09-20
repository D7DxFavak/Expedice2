/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.d7dxfavak.dbtridy;

import java.sql.ResultSet;
import java.util.Date;
import cz.d7dxfavak.expedice.PripojeniDB;
import cz.d7dxfavak.dbfunkce.SQLFunkceObecne;
import cz.d7dxfavak.dbfunkce.SQLFunkceObecne2;
import cz.d7dxfavak.dbfunkce.TextFunkce1;

/**
 *
 * @author Favak
 */
public class TridaObjednavka1 {

    /**
     * @return the tp1
     */
    public TridaPruvodka getTp1() {
        return tp1;
    }

    /**
     * @param tp1 the tp1 to set
     */
    public void setTp1(TridaPruvodka tp1) {
        this.tp1 = tp1;
    }

    private boolean reklamace;
    private boolean virtualni;
    private long idObjednavky;
    private int idZakaznik;
    private int idVykres;
    private int pocetObjednanychKusu;
    private int idMena;
    private int idPovrchUprava;
    private int idTepelneZpracovani;
    private int kusuNavic;
    private int kusuVyrobit;
    private long idRamcovaObjednavka;
    private int idTermin;
    private int skluz;
    private Date datumObjednani;
    private Date datumDodani;
    private Date datumExpedice;
    private Date datumPotvrzeni;
    private String nazevSoucasti;
    private String cenaKus;
    private String materialRozmer;
    private String cisloObjednavky;
    private String cisloFaktury;
    private String poznamka;
    private String material;
    private String popisPovrchUprava;
    private String popisMena;
    private String popisRamcovaObjednavka;
    private String casCelkem;
    private TridaVykres1 tv1;
    protected TridaPruvodka tp1;
    private int poradi;
    protected java.text.DateFormat df = java.text.DateFormat.getDateInstance();
    protected java.text.NumberFormat nf2;

    public TridaObjednavka1() {
        reklamace = false;
        virtualni = false;
        idObjednavky = 0;
        idZakaznik = 0;
        idVykres = 0;
        pocetObjednanychKusu = 0;
        idMena = 0;
        idPovrchUprava = 10;
        idTepelneZpracovani = 4;
        kusuNavic = 0;
        kusuVyrobit = 0;
        idRamcovaObjednavka = 0;
        datumObjednani = null;
        datumDodani = null;
        datumExpedice = null;
        datumPotvrzeni = null;
        nazevSoucasti = "";
        cenaKus = "";
        materialRozmer = "";
        cisloObjednavky = "";
        cisloFaktury = "";
        poznamka = "";
        material = "";
        tv1 = null;
        tp1 = null;
    }

    public TridaObjednavka1(long id) {
        this.selectData(id);
    }

    /**
     * @return the reklamace
     */
    public boolean isReklamace() {
        return reklamace;
    }

    /**
     * @param reklamace the reklamace to set
     */
    public void setReklamace(boolean reklamace) {
        this.reklamace = reklamace;
    }

    /**
     * @return the id
     */
    public long getId() {
        return idObjednavky;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.idObjednavky = id;
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
     * @return the pocetObjednanychKusu
     */
    public int getPocetObjednanychKusu() {
        return pocetObjednanychKusu;
    }

    /**
     * @param pocetObjednanychKusu the pocetObjednanychKusu to set
     */
    public void setPocetObjednanychKusu(int pocetObjednanychKusu) {
        this.pocetObjednanychKusu = pocetObjednanychKusu;
    }

    /**
     * @return the idMena
     */
    public int getIdMena() {
        return idMena;
    }

    /**
     * @param idMena the idMena to set
     */
    public void setIdMena(int idMena) {
        this.idMena = idMena;
    }

    /**
     * @return the idPovrchUprava
     */
    public int getIdPovrchUprava() {
        return idPovrchUprava;
    }

    /**
     * @param idPovrchUprava the idPovrchUprava to set
     */
    public void setIdPovrchUprava(int idPovrchUprava) {
        this.idPovrchUprava = idPovrchUprava;
    }

    /**
     * @return the idTepelneZpracovani
     */
    public int getIdTepelneZpracovani() {
        return idTepelneZpracovani;
    }

    /**
     * @param idTepelneZpracovani the idTepelneZpracovani to set
     */
    public void setIdTepelneZpracovani(int idTepelneZpracovani) {
        this.idTepelneZpracovani = idTepelneZpracovani;
    }

    /**
     * @return the kusuNavic
     */
    public int getKusuNavic() {
        return kusuNavic;
    }

    /**
     * @param kusuNavic the kusuNavic to set
     */
    public void setKusuNavic(int kusuNavic) {
        this.kusuNavic = kusuNavic;
    }

    /**
     * @return the kusuVyrobit
     */
    public int getKusuVyrobit() {
        return kusuVyrobit;
    }

    /**
     * @param kusuVyrobit the kusuVyrobit to set
     */
    public void setKusuVyrobit(int kusuVyrobit) {
        this.kusuVyrobit = kusuVyrobit;
    }

    /**
     * @return the idRamcovaObjednavka
     */
    public long getIdRamcovaObjednavka() {
        return idRamcovaObjednavka;
    }

    /**
     * @param idRamcovaObjednavka the idRamcovaObjednavka to set
     */
    public void setIdRamcovaObjednavka(long idRamcovaObjednavka) {
        this.idRamcovaObjednavka = idRamcovaObjednavka;
    }

    /**
     * @return the datumObjednani
     */
    public Date getDatumObjednani() {
        return datumObjednani;
    }

    /**
     * @param datumObjednani the datumObjednani to set
     */
    public void setDatumObjednani(Date datumObjednani) {
        this.datumObjednani = datumObjednani;
    }

    /**
     * @return the datumDodani
     */
    public Date getDatumDodani() {
        return datumDodani;
    }

    /**
     * @param datumDodani the datumDodani to set
     */
    public void setDatumDodani(Date datumDodani) {
        this.datumDodani = datumDodani;
    }

    /**
     * @return the datumExpedice
     */
    public Date getDatumExpedice() {
        return datumExpedice;
    }

    /**
     * @param datumExpedice the datumExpedice to set
     */
    public void setDatumExpedice(Date datumExpedice) {
        this.datumExpedice = datumExpedice;
    }

    /**
     * @return the nazevSoucasti
     */
    public String getNazevSoucasti() {
        return nazevSoucasti;
    }

    /**
     * @param nazevSoucasti the nazevSoucasti to set
     */
    public void setNazevSoucasti(String nazevSoucasti) {
        this.nazevSoucasti = nazevSoucasti;
    }

    /**
     * @return the cenaKus
     */
    public String getCenaKus() {
        return cenaKus;
    }

    /**
     * @param cenaKus the cenaKus to set
     */
    public void setCenaKus(String cenaKus) {
        this.cenaKus = cenaKus;
    }

    /**
     * @return the materialRozmer
     */
    public String getMaterialRozmer() {
        return materialRozmer;
    }

    /**
     * @param materialRozmer the materialRozmer to set
     */
    public void setMaterialRozmer(String materialRozmer) {
        this.materialRozmer = materialRozmer;
    }

    /**
     * @return the cisloObjednavky
     */
    public String getCisloObjednavky() {
        return cisloObjednavky;
    }

    /**
     * @param cisloObjednavky the cisloObjednavky to set
     */
    public void setCisloObjednavky(String cisloObjednavky) {
        this.cisloObjednavky = cisloObjednavky;
    }

    /**
     * @return the cisloFaktury
     */
    public String getCisloFaktury() {
        return cisloFaktury;
    }

    /**
     * @param cisloFaktury the cisloFaktury to set
     */
    public void setCisloFaktury(String cisloFaktury) {
        this.cisloFaktury = cisloFaktury;
    }

    /**
     * @return the poznamka
     */
    public String getPoznamka() {
        return poznamka;
    }

    /**
     * @param poznamka the poznamka to set
     */
    public void setPoznamka(String poznamka) {
        this.poznamka = poznamka;
    }

    /**
     * @return the material
     */
    public String getMaterial() {
        return material;
    }

    /**
     * @param material the material to set
     */
    public void setMaterial(String material) {
        this.material = material;
    }

    public boolean selectData() {
        return selectData(this.idObjednavky);
    }

    public boolean selectData(long id) {
        try {
            ResultSet q = PripojeniDB.dotazS("SELECT objednavky_id, objednavky_zakaznik_id, objednavky_datum_objednani, "
                    + "objednavky_nazev_soucasti, objednavky_cislo_vykresu, objednavky_pocet_objednanych_kusu, "
                    + "objednavky_cena_za_kus, objednavky_mena_id, objednavky_termin_dodani, "
                    + "objednavky_material_rozmer, objednavky_povrchova_uprava_id, objednavky_tepelne_zpracovani_id, "
                    + "objednavky_cislo_objednavky, objednavky_cislo_faktury, objednavky_datum_expedice, "
                    + "objednavky_poznamka, objednavky_kusu_navic, objednavky_kusu_vyrobit, "
                    + "objednavky_reklamace, objendavky_ramcova_objednavka, objednavky_material, objednavky_datum_potvrzeni, objednavky_virtualni_polozka "
                    + "FROM spolecne.objednavky "
                    + "WHERE objednavky_id = " + id);
            q.last();
            if (q.getRow() == 1) {
                q.first();
                this.setId(SQLFunkceObecne.osetriCteniInt(q.getInt(1)));
                this.setIdZakaznik(SQLFunkceObecne.osetriCteniInt(q.getInt(2)));
                this.setDatumObjednani(q.getDate(3));
                this.setNazevSoucasti(SQLFunkceObecne.osetriCteniString(q.getString(4)));
                this.setIdVykres(SQLFunkceObecne.osetriCteniInt(q.getInt(5)));
                this.setPocetObjednanychKusu(SQLFunkceObecne.osetriCteniInt(q.getInt(6)));
                this.setCenaKus(SQLFunkceObecne.osetriCteniString(q.getString(7)));
                this.setIdMena(SQLFunkceObecne.osetriCteniInt(q.getInt(8)));
                this.setDatumDodani(q.getDate(9));
                this.setMaterialRozmer(SQLFunkceObecne.osetriCteniString(q.getString(10)));
                this.setIdPovrchUprava(SQLFunkceObecne.osetriCteniInt(q.getInt(11)));
                this.setIdTepelneZpracovani(SQLFunkceObecne.osetriCteniInt(q.getInt(12)));
                this.setCisloObjednavky(SQLFunkceObecne.osetriCteniString(q.getString(13)));
                this.setCisloFaktury(SQLFunkceObecne.osetriCteniString(q.getString(14)));
                this.setDatumExpedice(q.getDate(15));
                this.setPoznamka(SQLFunkceObecne.osetriCteniString(q.getString(16)));
                this.setKusuNavic(SQLFunkceObecne.osetriCteniInt(q.getInt(17)));
                this.setKusuVyrobit(SQLFunkceObecne.osetriCteniInt(q.getInt(18)));
                this.setReklamace(q.getBoolean(19));
                this.setIdRamcovaObjednavka(SQLFunkceObecne.osetriCteniInt(q.getInt(20)));
                this.setMaterial(SQLFunkceObecne.osetriCteniString(q.getString(21)));
                this.setDatumPotvrzeni(q.getDate(22));
                this.setVirtualni(q.getBoolean(23));
            }
            return true;
        } catch (Exception e) {
            PripojeniDB.vyjimkaS(e);
            return false;
        } finally {
            PripojeniDB.zavriPrikaz();
        }
    }

    public int updateData() {
        int r = -10000;
        try {
            String dotaz = "UPDATE spolecne.objednavky "
                    + "SET objednavky_id= " + this.idObjednavky + ", "
                    + "objednavky_zakaznik_id= " + this.idZakaznik + ", ";
            if (this.datumObjednani != null) {
                dotaz += "objednavky_datum_objednani= " + TextFunkce1.osetriZapisTextDB1(df.format(this.datumObjednani)) + ", ";
            }
            dotaz += "objednavky_nazev_soucasti= " + TextFunkce1.osetriZapisTextDB1(this.nazevSoucasti) + ", "
                    + "objednavky_cislo_vykresu= " + this.idVykres + ", "
                    + "objednavky_pocet_objednanych_kusu= " + this.pocetObjednanychKusu + ", "
                    + "objednavky_cena_za_kus= " + TextFunkce1.osetriZapisTextDB1(this.cenaKus) + ", "
                    + "objednavky_mena_id= " + this.idMena + ", ";
            if (this.datumDodani != null) {
                dotaz += "objednavky_termin_dodani= " + TextFunkce1.osetriZapisTextDB1(df.format(this.datumDodani)) + ", ";
            }
            dotaz += "objednavky_material_rozmer= " + TextFunkce1.osetriZapisTextDB1(this.materialRozmer) + ", "
                    + "objednavky_povrchova_uprava_id= " + this.idPovrchUprava + ", "
                    + "objednavky_tepelne_zpracovani_id= " + this.idTepelneZpracovani + ", "
                    + "objednavky_cislo_objednavky= " + TextFunkce1.osetriZapisTextDB1(this.cisloObjednavky) + ", "
                    + "objednavky_cislo_faktury= " + TextFunkce1.osetriZapisTextDB1(this.cisloFaktury) + ", ";
            if (this.datumExpedice != null) {
                dotaz += "objednavky_datum_expedice=" + TextFunkce1.osetriZapisTextDB1(df.format(this.datumExpedice)) + ", ";
            }
            dotaz += "objednavky_poznamka= " + TextFunkce1.osetriZapisTextDB1(this.poznamka) + ", "
                    + "objednavky_kusu_navic= " + this.kusuNavic + ", "
                    + "objednavky_kusu_vyrobit= " + this.kusuVyrobit + ", "
                    + "objednavky_reklamace= " + this.reklamace + ", "
                    + "objendavky_ramcova_objednavka= " + this.idRamcovaObjednavka + ", "
                    + "objednavky_material= " + TextFunkce1.osetriZapisTextDB1(this.material) + ", "
                    + "objednavky_datum_potvrzeni= " + TextFunkce1.osetriZapisTextDB1(df.format(this.datumPotvrzeni)) + ", "
                    + "objednavky_virtualni_polozka = " + this.virtualni + " "
                    + "WHERE objednavky_id= " + this.idObjednavky;
            //System.out.println("Update data : " + dotaz);
            r = SQLFunkceObecne2.update(dotaz);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return r;
        }
        /*
         * int vu = -10000; try { int r = SQLFunkceObecne2.update(dotaz);
         * System.out.println("r : " + r); if (r == 1) { vu = 0; } // konec if
         * radky } // konec try catch (Exception e) { System.out.println("Update
         * osoby - doslo k vyjimce"); e.printStackTrace(); // vu =
         * -Pripojeni.vyjimkaUD(e); //System.out.println("vu je ve vyjimce " +
         * vu); //Pripojeni.zavriPrikaz(); if (vu == 0) { vu = -10000; } } //
         * konec catch finally { Pripojeni.zavriPrikaz();
         * System.out.println("return vu : " + vu); return vu; } // konec
         * finally
         */

    }

    public long insertData() {
        try {
            ResultSet id = PripojeniDB.dotazS("SELECT COALESCE(MAX(objednavky_id)+1,1) FROM spolecne.objednavky");
            while (id.next()) {
                this.idObjednavky = id.getLong(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            String dotaz = "INSERT INTO spolecne.objednavky( "
                    + "objednavky_id, objednavky_zakaznik_id, objednavky_datum_objednani, "
                    + "objednavky_nazev_soucasti, objednavky_cislo_vykresu, objednavky_pocet_objednanych_kusu, "
                    + "objednavky_cena_za_kus, objednavky_mena_id, objednavky_termin_dodani, "
                    + "objednavky_material_rozmer, objednavky_povrchova_uprava_id, objednavky_tepelne_zpracovani_id, "
                    + "objednavky_cislo_objednavky, objednavky_cislo_faktury, objednavky_datum_expedice, "
                    + "objednavky_poznamka, objednavky_kusu_navic, objednavky_kusu_vyrobit, "
                    + "objednavky_reklamace, objendavky_ramcova_objednavka, objednavky_material, "
                    + "objednavky_datum_potvrzeni, objednavky_virtualni_polozka) "
                    + "VALUES (" + this.idObjednavky + ", "
                    + this.idZakaznik + ", "
                    + TextFunkce1.osetriZapisDatumDB1(this.datumObjednani) + ", "
                    + TextFunkce1.osetriZapisTextDB1(this.nazevSoucasti) + ", "
                    + this.idVykres + ", "
                    + this.pocetObjednanychKusu + ", "
                    + TextFunkce1.osetriZapisTextDB1(this.cenaKus) + ", "
                    + this.idMena + ", "
                    + TextFunkce1.osetriZapisDatumDB1(this.datumDodani) + ", "
                    + TextFunkce1.osetriZapisTextDB1(this.materialRozmer) + ", "
                    + this.idPovrchUprava + ", "
                    + this.idTepelneZpracovani + ", "
                    + TextFunkce1.osetriZapisTextDB1(this.cisloObjednavky) + ", "
                    + TextFunkce1.osetriZapisTextDB1(this.cisloFaktury) + ", "
                    + TextFunkce1.osetriZapisDatumDB1(this.datumExpedice) + ", "
                    + TextFunkce1.osetriZapisTextDB1(this.poznamka) + ", "
                    + this.kusuNavic + ", "
                    + this.kusuVyrobit + ", "
                    + TextFunkce1.osetriZapisTextDB1(this.cisloFaktury) + ", "
                    + TextFunkce1.osetriZapisTextDB1(this.cisloFaktury) + ", "
                    + this.reklamace + ", "
                    + this.idRamcovaObjednavka + ", "
                    + TextFunkce1.osetriZapisTextDB1(this.material) + ", "
                    + TextFunkce1.osetriZapisTextDB1(df.format(this.datumPotvrzeni)) + ", "
                    + this.virtualni + ") ";
            int a = PripojeniDB.dotazIUD(dotaz);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return this.idObjednavky;
        }
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
     * @return the popisPovrchUprava
     */
    public String getPopisPovrchUprava() {
        return popisPovrchUprava;
    }

    /**
     * @param popisPovrchUprava the popisPovrchUprava to set
     */
    public void setPopisPovrchUprava(String popisPovrchUprava) {
        this.popisPovrchUprava = popisPovrchUprava;
    }

    /**
     * @return the popisMena
     */
    public String getPopisMena() {
        return popisMena;
    }

    /**
     * @param popisMena the popisMena to set
     */
    public void setPopisMena(String popisMena) {
        this.popisMena = popisMena;
    }

    /**
     * @return the casCelkem
     */
    public String getCasCelkem() {
        return casCelkem;
    }

    /**
     * @param casCelkem the casCelkem to set
     */
    public void setCasCelkem(String casCelkem) {
        this.casCelkem = casCelkem;
    }

    /**
     * @return the popisRamcovaObjednavka
     */
    public String getPopisRamcovaObjednavka() {
        return popisRamcovaObjednavka;
    }

    /**
     * @param popisRamcovaObjednavka the popisRamcovaObjednavka to set
     */
    public void setPopisRamcovaObjednavka(String popisRamcovaObjednavka) {
        this.popisRamcovaObjednavka = popisRamcovaObjednavka;
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
     * @return the datumPotvrzeni
     */
    public Date getDatumPotvrzeni() {
        return datumPotvrzeni;
    }

    /**
     * @param datumPotvrzeni the datumPotvrzeni to set
     */
    public void setDatumPotvrzeni(Date datumPotvrzeni) {
        this.datumPotvrzeni = datumPotvrzeni;
    }

    /**
     * @return the skluz
     */
    public int getSkluz() {
        return skluz;
    }

    /**
     * @param skluz the skluz to set
     */
    public void setSkluz(int skluz) {
        this.skluz = skluz;
    }

    /**
     * @return the virtualni
     */
    public boolean isVirtualni() {
        return virtualni;
    }

    /**
     * @param virtualni the virtualni to set
     */
    public void setVirtualni(boolean virtualni) {
        this.virtualni = virtualni;
    }

    public int getCisloPruvodky() {
        return tp1.getId();
    }
    
    public String getVykres() {
        return (tv1.getCislo() + " " + tv1.getRevize());
    }
    
    public int getVyrobenoKusu() {
        return tp1.getVyrobenoKusu();
    }
    
   
    
}
