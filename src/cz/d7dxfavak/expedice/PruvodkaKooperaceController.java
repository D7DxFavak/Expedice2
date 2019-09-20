/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.d7dxfavak.expedice;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import cz.d7dxfavak.dbtridy.TridaPruvodka;

/**
 * FXML Controller class
 *
 * @author Favak
 */
public class PruvodkaKooperaceController implements Initializable {

    @FXML
    private Button buttonUlozit;
    @FXML
    private Button buttonZrusit;
    @FXML
    private TextField textFieldPruvodka;
    @FXML
    private TextField textFieldVykres;
    @FXML
    private TextField textFieldDatum;
    @FXML
    private TextField textFieldKusu;
    @FXML
    private TextField textFieldKoop;
    @FXML
    private Label labelDatum;

    private java.text.DateFormat df = java.text.DateFormat.getDateInstance();
    private TridaPruvodka tp1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        textFieldPruvodka.requestFocus();
        buttonUlozit.setDisable(true);
        tp1 = new TridaPruvodka();
    }

    public void kontrolaUdaju() {
        if (!textFieldPruvodka.getText().trim().isEmpty() && !textFieldKusu.getText().trim().isEmpty()) {
            buttonUlozit.setDisable(false);
        }
    }

    @FXML
    private void ulozitKooperaci() {
        if (tp1.getAktualniKooperace().isRozpracovana()) {
            tp1.getAktualniKooperace().setPocetPrijato(Integer.valueOf(textFieldKusu.getText().trim()));
        } else {
            tp1.getAktualniKooperace().setPocetOdeslano(Integer.valueOf(textFieldKusu.getText().trim()));
        }
        MikronExpedice.ulozKooperaci(tp1);
        Stage stage = (Stage) buttonUlozit.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void nacistData() {
        System.out.println("nacist data");
        tp1 = MikronExpedice.nactiDataPruvodky(Integer.valueOf(textFieldPruvodka.getText().trim()));
        if (tp1.getAktualniKooperace() == null) {
            textFieldKusu.setEditable(false);
        } else {
            if (tp1.getTv1().getRevize().isEmpty()) {
                textFieldVykres.setText(tp1.getTv1().getCislo());
            } else {
                textFieldVykres.setText(tp1.getTv1().getCislo() + " - " + tp1.getTv1().getRevize());
            }

            textFieldKoop.setText(tp1.getAktualniKooperace().getPopis());
            if (tp1.getAktualniKooperace().isRozpracovana()) {
                textFieldDatum.setText(df.format(tp1.getAktualniKooperace().getDatumPrijeti()));
                labelDatum.setText("Datum přijetí");
            } else {
                textFieldDatum.setText(df.format(tp1.getAktualniKooperace().getDatumOdeslani()));
                labelDatum.setText("Datum odeslání");
            }
        }

    }

    @FXML
    private void focusVyrobeno() {
        System.out.println("focus vyrobeno");
        nacistData();
        textFieldKusu.requestFocus();
    }

    @FXML
    private void zrusitPruvodku() {
        Stage stage = (Stage) buttonZrusit.getScene().getWindow();
        stage.close();
    }

}
