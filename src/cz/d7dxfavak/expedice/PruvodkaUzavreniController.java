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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Favak
 */
public class PruvodkaUzavreniController implements Initializable {

    @FXML
    private Button buttonUzavrit;
    @FXML
    private Button buttonZrusit;
    @FXML
    private TextField textFieldPruvodka;
    @FXML
    private TextField textFieldKusu;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        textFieldPruvodka.requestFocus();
        buttonUzavrit.setDisable(true);
    }

    public void kontrolaUdaju() {
        if (!textFieldPruvodka.getText().trim().isEmpty() && !textFieldKusu.getText().trim().isEmpty()) {
            buttonUzavrit.setDisable(false);
        }
    }

    @FXML
    private void uzavritPruvodku() {
        MikronExpedice.uzavriPruvodky(Integer.valueOf(textFieldPruvodka.getText().trim()), Integer.valueOf(textFieldKusu.getText().trim()));
        Stage stage = (Stage) buttonUzavrit.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void focusVyrobeno() {
        textFieldKusu.requestFocus();
    }

    @FXML
    private void zrusitPruvodku() {
        Stage stage = (Stage) buttonZrusit.getScene().getWindow();
        stage.close();
    }

}
