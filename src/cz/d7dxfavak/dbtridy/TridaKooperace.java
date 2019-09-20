/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.d7dxfavak.dbtridy;

import java.util.Date;

/**
 *
 * @author Favak-ntb
 */
public class TridaKooperace {

    private int idPruvodka;
    private int poradi;
    private String popis;
    private Date datumOdeslani;
    private Date datumPrijeti;
    private int pocetOdeslano;
    private int pocetPrijato;
    private boolean rozpracovana;
    
    
    public TridaKooperace() {
        
    }
    /**
     * @return the idPruvodka
     */
    public int getIdPruvodka() {
        return idPruvodka;
    }

    /**
     * @param idPruvodka the idPruvodka to set
     */
    public void setIdPruvodka(int idPruvodka) {
        this.idPruvodka = idPruvodka;
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
     * @return the popis
     */
    public String getPopis() {
        return popis;
    }

    /**
     * @param popis the popis to set
     */
    public void setPopis(String popis) {
        this.popis = popis;
    }

    /**
     * @return the datumOdeslani
     */
    public Date getDatumOdeslani() {
        return datumOdeslani;
    }

    /**
     * @param datumOdeslani the datumOdeslani to set
     */
    public void setDatumOdeslani(Date datumOdeslani) {
        this.datumOdeslani = datumOdeslani;
    }

    /**
     * @return the datumPrijeti
     */
    public Date getDatumPrijeti() {
        return datumPrijeti;
    }

    /**
     * @param datumPrijeti the datumPrijeti to set
     */
    public void setDatumPrijeti(Date datumPrijeti) {
        this.datumPrijeti = datumPrijeti;
    }

    /**
     * @return the pocetOdeslano
     */
    public int getPocetOdeslano() {
        return pocetOdeslano;
    }

    /**
     * @param pocetOdeslano the pocetOdeslano to set
     */
    public void setPocetOdeslano(int pocetOdeslano) {
        this.pocetOdeslano = pocetOdeslano;
    }

    /**
     * @return the pocetPrijato
     */
    public int getPocetPrijato() {
        return pocetPrijato;
    }

    /**
     * @param pocetPrijato the pocetPrijato to set
     */
    public void setPocetPrijato(int pocetPrijato) {
        this.pocetPrijato = pocetPrijato;
    }

    /**
     * @return the jeRozpracovana
     */
    public boolean isRozpracovana() {
        return rozpracovana;
    }

    /**
     * @param rozpracovana the jeRozpracovana to set
     */
    public void setRozpracovana(boolean rozpracovana) {
        this.rozpracovana = rozpracovana;
    }
    
 
    
            
    
}
