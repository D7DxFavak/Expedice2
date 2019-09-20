/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.d7dxfavak.expedice;

import cz.d7dxfavak.dbfunkce.SQLFunkceObecne2;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Favak
 */
public class FXMLPotvrzeniController implements Initializable {

    @FXML
    private Button buttonPotvrdit;
    @FXML
    private Button buttonZrusit;
    @FXML
    private TextField textFieldDatum;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        textFieldDatum.setText(SQLFunkceObecne2.getStringCurrentDate());
    }

    @FXML
    private void potvrditExpedici() {
        if (textFieldDatum.getText().trim().length() >= 8) {
            MikronExpedice.potvrditExpedici(textFieldDatum.getText().trim());
            Stage stage = (Stage) buttonPotvrdit.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    private void zrusitExpedici() {
        Stage stage = (Stage) buttonZrusit.getScene().getWindow();
        stage.close();
    }

}
