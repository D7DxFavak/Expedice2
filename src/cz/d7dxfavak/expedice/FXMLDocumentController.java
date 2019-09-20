/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.d7dxfavak.expedice;

import cz.d7dxfavak.dbfunkce.SQLFunkceObecne;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableRow;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import cz.d7dxfavak.dbtridy.TridaKooperace;
import cz.d7dxfavak.dbtridy.TridaLieferschein1;
import cz.d7dxfavak.dbtridy.TridaObjednavka1;
import cz.d7dxfavak.dbtridy.TridaPruvodka;
import cz.d7dxfavak.dbtridy.TridaVykres1;

/**
 *
 * @author Favak
 */
public class FXMLDocumentController implements Initializable {

    
    private TridaLieferschein1 tLief1;

    @FXML
    private Button buttonNacistLiefer;
    @FXML
    private Button buttonNacistPruvodka;
    @FXML
    private Button buttonPotvrditExpedici;
    @FXML
    private TextField textFieldCisloLiefer;
    @FXML
    private TableView tablePruvodky;

    @FXML
    private TableColumn poradiColumn;
    @FXML
    private TableColumn pruvodkaColumn;
    @FXML
    private TableColumn vykresColumn;
    @FXML
    private TableColumn soucastColumn;
    @FXML
    private TableColumn vyrobenoColumn;

    @FXML
    private TableView tablePruvodkyKoop;
    @FXML
    private TableColumn pruvodkaKoopColumn;
    @FXML
    private TableColumn vykresKoopColumn;
    @FXML
    private TableColumn zpracovaniKoopColumn;
    @FXML
    private TableColumn datumOdeslaniKoopColumn;
    @FXML
    private TableColumn odeslanoKusuKoopColumn;
    @FXML
    private TableColumn datumPrijetiKoopColumn;
    @FXML
    private TableColumn prijatoKusuKoopColumn;

    @FXML
    private Tab kooperaceTab;
    @FXML
    private Tab expediceTab;
    @FXML
    private TabPane tabPaneMain;

    private TridaObjednavka1 tObj1;
    private TridaPruvodka tPr1;

    @FXML
    private void nacistLieferSchein(ActionEvent event) {

        tLief1 = new TridaLieferschein1();
        tLief1.selectData(textFieldCisloLiefer.getText().trim());
        System.out.println("nl " + tLief1.getCisloLieferschein());

        MikronExpedice.arTO1.clear();

        try {
            String dotaz = "";

            dotaz = "SELECT objednavky_id, " //1            
                    + "objednavky_nazev_soucasti, " //2
                    + "objednavky_datum_expedice, " //3
                    + "vykresy_cislo, " //4
                    + "vykresy_revize, " //5                    
                    + "vykresy_id," //6
                    + "p.pruvodky_id, " //7
                    + "p.pruvodky_vyrobeno_kusu " //8
                    + "FROM spolecne.objednavky "
                    + "LEFT JOIN (SELECT pruvodky_id, pruvodky_vyrobeno_kusu, pruvodky_objednavky_id FROM spolecne.pruvodky) AS p "
                    + "ON p.pruvodky_objednavky_id = objednavky_id "
                    + "CROSS JOIN spolecne.vazba_lieferscheiny_objednavky "
                    + "CROSS JOIN spolecne.vykresy "
                    + "WHERE vazba_lieferscheiny_objednavky_lieferscheiny_id = " + tLief1.getIdLieferschein() + " "
                    + "AND vykresy.vykresy_id = objednavky.objednavky_cislo_vykresu "
                    + "AND vazba_lieferscheiny_objednavky_objednavky_id = objednavky_id "
                    + "ORDER BY vazba_lieferscheiny_objednavky_poradi , vykresy_cislo,vykresy_revize";

            //System.out.println(dotaz);
            ResultSet objednavka1 = PripojeniDB.dotazS(dotaz);
            int poradi = 1;
            while (objednavka1.next()) {
                tObj1 = new TridaObjednavka1();
                tObj1.setId(new Long(objednavka1.getLong(1)));
                tObj1.setPoradi(poradi);
                tObj1.setNazevSoucasti((objednavka1.getString(2) == null) ? "" : objednavka1.getString(2));
                try {
                    tObj1.setDatumExpedice(objednavka1.getDate(3)); // datum expedice
                } catch (Exception e) {
                    tObj1.setDatumExpedice(objednavka1.getDate(3));
                }
                TridaVykres1 tv1 = new TridaVykres1();
                tv1.setIdVykres(objednavka1.getInt(6));
                tv1.setCislo((objednavka1.getString(4) == null) ? "" : objednavka1.getString(4));
                tv1.setRevize((objednavka1.getString(5) == null) ? "" : objednavka1.getString(5));
                tObj1.setTv1(tv1);
                tObj1.setIdVykres(objednavka1.getInt(6));
                TridaPruvodka tp1 = new TridaPruvodka();
                tp1.setId(SQLFunkceObecne.osetriCteniInt(objednavka1.getInt(7)));
                tp1.setVyrobenoKusu(SQLFunkceObecne.osetriCteniInt(objednavka1.getInt(8)));
                tObj1.setTp1(tp1);

                MikronExpedice.arTO1.add(tObj1);
                poradi++;

            }// konec while

            tablePruvodky.setItems(MikronExpedice.arTO1);
            tablePruvodky.setRowFactory(tv -> new TableRow<TridaObjednavka1>() {
                @Override
                public void updateItem(TridaObjednavka1 item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null) {
                        setStyle("");
                    } else if (item.getVyrobenoKusu() > 0 || item.getTp1().getId() == 0) {

                        setStyle("-fx-background-color: white;");
                    } else {
                        setStyle("-fx-background-color: #ff6666;");
                    }
                }
            });
        } // konec try
        catch (Exception e) {
            e.printStackTrace();
            PripojeniDB.vyjimkaS(e);
        } // konec catch
    }

    @FXML
    protected void nacistPruvodkyKooperace(/*ActionEvent event*/) {

        MikronExpedice.arTP1.clear();

        try {
            String dotaz = "";

            dotaz = "SELECT pruvodka_kooperace_pruvodka_id, vykres.vykresy_id, vykres.vykresy_cislo, vykres.vykresy_revize, postup.pracovni_postup_pruvodka_popis, "
                    + "pruvodka_kooperace_datum_odeslani, pruvodka_kooperace_pocet_odeslani, pruvodka_kooperace_datum_prijeti, pruvodka_kooperace_pocet_prijeti "
                    + "FROM spolecne.pruvodka_kooperace "
                    + "CROSS JOIN spolecne.pruvodky "
                    + "LEFT JOIN (SELECT vykresy_id, vykresy_cislo, vykresy_revize "
                    + "FROM spolecne.vykresy) AS vykres "
                    + "ON vykres.vykresy_id = pruvodky_cislo_vykresu "
                    + "LEFT JOIN (SELECT pracovni_postup_pruvodka_pruvodka_id, pracovni_postup_pruvodka_poradi, pracovni_postup_pruvodka_popis "
                    + "FROM spolecne.pracovni_postup_pruvodka) AS postup "
                    + "ON (postup.pracovni_postup_pruvodka_pruvodka_id = pruvodka_kooperace_pruvodka_id  "
                    + "AND postup.pracovni_postup_pruvodka_poradi = pruvodka_kooperace_poradi) "
                    + "WHERE pruvodky_id = pruvodka_kooperace_pruvodka_id "
                    + "AND (pruvodka_kooperace_datum_prijeti > current_date - integer '7' OR pruvodka_kooperace_datum_prijeti IS NULL) "
                    + "ORDER BY pruvodka_kooperace_datum_prijeti DESC";

            
            ResultSet q = PripojeniDB.dotazS(dotaz);
            int poradi = 1;
            while (q.next()) {
                tPr1 = new TridaPruvodka();
                tPr1.setId(SQLFunkceObecne.osetriCteniInt(q.getInt(1)));
                tPr1.setIdVykres(SQLFunkceObecne.osetriCteniInt(q.getInt(2)));
                tPr1.setTv1(new TridaVykres1());
                tPr1.getTv1().setCislo(SQLFunkceObecne.osetriCteniString(q.getString(3)));
                tPr1.getTv1().setRevize(SQLFunkceObecne.osetriCteniString(q.getString(4)));
                TridaKooperace tk1 = new TridaKooperace();
                tk1.setIdPruvodka(SQLFunkceObecne.osetriCteniInt(q.getInt(1)));
                tk1.setPopis(SQLFunkceObecne.osetriCteniString(q.getString(5)));
                tk1.setDatumOdeslani(q.getDate(6));
                tk1.setPocetOdeslano(SQLFunkceObecne.osetriCteniInt(q.getInt(7)));
                tk1.setDatumPrijeti(q.getDate(8));
                tk1.setPocetPrijato(SQLFunkceObecne.osetriCteniInt(q.getInt(9)));
                tPr1.setAktualniKooperace(tk1);

                MikronExpedice.arTP1.add(tPr1);
                poradi++;

            }// konec while

            tablePruvodkyKoop.setItems(MikronExpedice.arTP1);
            tablePruvodkyKoop.setRowFactory(tpv -> new TableRow<TridaPruvodka>() {
                @Override
                public void updateItem(TridaPruvodka item, boolean empty) {
                    super.updateItem(item, empty);
                    /*if (item == null) {
                        setStyle("");
                    } else if (item.getVyrobenoKusu() > 0 || item.getTp1().getId() == 0) {

                        setStyle("-fx-background-color: white;");
                    } else {
                        setStyle("-fx-background-color: #ff6666;");
                    }*/
                }
            });
        } // konec try
        catch (Exception e) {
            e.printStackTrace();
            PripojeniDB.vyjimkaS(e);
        } // konec catch
    }

    @FXML
    private void buttonNacistPruvodku(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("PruvodkaUzavreni.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Uzavření průvodky");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("expedice.png")));
            stage.setScene(new Scene(root, 600, 350));
            stage.show();
            // Hide this current window (if this is what you want)
            //((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void buttonNacistPruvodkuKooperace(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("PruvodkaKooperace.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Kooperace");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("expedice.png")));
            stage.setScene(new Scene(root, 600, 500));
            stage.show();
            // Hide this current window (if this is what you want)
            //((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void buttonPotvrditExpedici(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("FXMLPotvrzeni.fxml"));
            Stage stage = new Stage();
            stage.getIcons().add(new Image(getClass().getResourceAsStream("expedice.png")));
            stage.setTitle("Potvrzení expedice");
            stage.setScene(new Scene(root, 600, 350));
            stage.show();
            // Hide this current window (if this is what you want)
            //((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void buttonSmazatList(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Potvrzení smazání");
        alert.setHeaderText("Potvrzení smazání dat");
        alert.setContentText("Opravdu smazat načtený dodací líst?");

        ButtonType buttonTypePotvrdit = new ButtonType("Potvrdit", ButtonData.OK_DONE);
        ButtonType buttonTypeZrusit = new ButtonType("Zrušit", ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypePotvrdit, buttonTypeZrusit);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypePotvrdit) {
            MikronExpedice.arTO1.clear();
            updateList();
            textFieldCisloLiefer.clear();
            alert.close();
        } else {
            alert.close();
        }
        textFieldCisloLiefer.requestFocus();
    }

    protected void updateList() {
        tablePruvodky.refresh();
    }

    protected void updateListKoop() {
        tablePruvodkyKoop.refresh();
    }

    protected void TFLieferFocus() {
        textFieldCisloLiefer.setText("");
        textFieldCisloLiefer.requestFocus();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       /* pripojeniDB = new PripojeniDB();
       
        String addr = "jdbc:postgresql://localhost:54325/database1?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory"; 
        String user = "test"; 
        String pass = "123456"; 

        int rc = pripojeniDB.navazSpojeniDB(addr, user, pass);

        if (rc == 1) {
            System.out.println("Selhání připojení - "
                    + "Selhalo připojení k databázi. Pravděpodobně bylo zadáno chybné jméno nebo heslo\n"
                    + "nebo byl detekován pokus o současné navázání více než jednoho spojení k databázi");

        }*/

        MikronExpedice.arTO1 = FXCollections.observableArrayList();
        poradiColumn.setCellValueFactory(
                new PropertyValueFactory<TridaObjednavka1, Integer>("poradi"));
        poradiColumn.setStyle("-fx-alignment: CENTER-RIGHT;-fx-padding: 0 15 0 0; -fx-font-size: 16px;");
        pruvodkaColumn.setCellValueFactory(
                new PropertyValueFactory<TridaObjednavka1, Integer>("cisloPruvodky"));
        pruvodkaColumn.setStyle("-fx-alignment: CENTER; ; -fx-font-size: 16px;");
        vykresColumn.setCellValueFactory(
                new PropertyValueFactory<TridaObjednavka1, String>("vykres"));
        vykresColumn.setStyle("-fx-font-size: 16px;");
        soucastColumn.setCellValueFactory(
                new PropertyValueFactory<TridaObjednavka1, String>("nazevSoucasti"));
        soucastColumn.setStyle("-fx-font-size: 16px;");
        vyrobenoColumn.setCellValueFactory(
                new PropertyValueFactory<TridaObjednavka1, String>("vyrobenoKusu"));
        vyrobenoColumn.setStyle("-fx-alignment: CENTER-RIGHT;-fx-padding: 0 15 0 0;-fx-font-size: 16px;");

        MikronExpedice.arTP1 = FXCollections.observableArrayList();
        pruvodkaKoopColumn.setCellValueFactory(
                new PropertyValueFactory<TridaPruvodka, Integer>("id"));
        pruvodkaKoopColumn.setStyle("-fx-alignment: CENTER; ; -fx-font-size: 16px;");
        vykresKoopColumn.setCellValueFactory(
                new PropertyValueFactory<TridaPruvodka, String>("vykres"));
        vykresKoopColumn.setStyle("-fx-font-size: 16px;");
        zpracovaniKoopColumn.setCellValueFactory(
                new PropertyValueFactory<TridaPruvodka, String>("zpracovani"));
        zpracovaniKoopColumn.setStyle("-fx-font-size: 16px;");
        datumOdeslaniKoopColumn.setCellValueFactory(
                new PropertyValueFactory<TridaPruvodka, String>("datumOdeslani"));
        datumOdeslaniKoopColumn.setStyle("-fx-alignment: CENTER-RIGHT;-fx-padding: 0 15 0 0;-fx-font-size: 16px;");
        odeslanoKusuKoopColumn.setCellValueFactory(
                new PropertyValueFactory<TridaPruvodka, String>("odeslanoKusu"));
        odeslanoKusuKoopColumn.setStyle("-fx-alignment: CENTER-RIGHT;-fx-padding: 0 15 0 0;-fx-font-size: 16px;");
        datumPrijetiKoopColumn.setCellValueFactory(
                new PropertyValueFactory<TridaPruvodka, String>("datumPrijeti"));
        datumPrijetiKoopColumn.setStyle("-fx-alignment: CENTER-RIGHT;-fx-padding: 0 15 0 0;-fx-font-size: 16px;");
        prijatoKusuKoopColumn.setCellValueFactory(
                new PropertyValueFactory<TridaPruvodka, String>("prijatoKusu"));
        prijatoKusuKoopColumn.setStyle("-fx-alignment: CENTER-RIGHT;-fx-padding: 0 15 0 0;-fx-font-size: 16px;");

        kooperaceTab.setStyle("-fx-font-size: 20px; -fx-pref-height: 40px;   ");
        expediceTab.setStyle("-fx-font-size: 20px;-fx-pref-height: 40px;   ");
        
      // tabPaneMain.setTabMinHeight(50);

        nacistPruvodkyKooperace();

    }

}
