package org.fxapps.javafx.fatjar;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.Node;



public class ControlZeiterfassung implements Initializable {

    @FXML
    private Button start;
    @FXML
    private Button bearbeiten;
    @FXML
    private Button zurueck;

    @FXML
    private AnchorPane uebersicht;

    @FXML
    private Label zeitlich;
    @FXML
    private Label gehZeit;
    @FXML
    private Label kommZeit;
    @FXML
    private Label stundenL;
    @FXML
    public Label aktuelleGleitzeit;

    @FXML
    private TableView<Eintrag> tabelle;
    @FXML
    private TableColumn<Eintrag, String> gegangen;
    @FXML
    private TableColumn<Eintrag, String> kommen;
    @FXML
    private TableColumn<Eintrag, Double> stunden;

    @FXML
    public static DatePicker kalenderPicker2;

    @FXML
    private Circle gelb2;
    @FXML
    private Circle gruen2;
    @FXML
    private Circle rot2;


    private Stage stage;
    private Scene scene;

    @FXML
    void zurueckClick(ActionEvent event) throws IOException {

        Parent root2 = FXMLLoader.load(getClass().getResource("/org/fxapps/javafx/fatjar/Startseite.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root2);
        stage.setScene(scene);
        stage.show();

    }

    /* (non-Javadoc)
     * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        kommen. setCellValueFactory(new PropertyValueFactory<Eintrag, String>("kommen"));
        gegangen. setCellValueFactory(new PropertyValueFactory<Eintrag, String>("gegangen"));
        stunden. setCellValueFactory(new PropertyValueFactory<Eintrag, Double>("stunden"));

        aktuelleGleitzeit.setText("+ " + Double.toString(Person.gleitzeit));

        if (ControlStartseite.ampel == 1) {

          gruen2.setStyle("-fx-fill: #139024");
          gelb2.setStyle("-fx-fill: #000000");
          rot2.setStyle("-fx-fill: #000000");
      }
      
      else if (ControlStartseite.ampel == 2) {

          gruen2.setStyle("-fx-fill: #000000");
          gelb2.setStyle("-fx-fill: #dbba51");
          rot2.setStyle("-fx-fill: #000000");
      }
      
      else {

          gruen2.setStyle("-fx-fill: #000000");
          gelb2.setStyle("-fx-fill: #000000");
          rot2.setStyle("-fx-fill: #c32828");
         
      }


    }

    boolean kg = false;
    String uhrzeitKommen;

    @FXML
    void startClick(ActionEvent event) {


      String datum = LocalDateTime.now().withSecond(0).withNano(0).toString();
      
      String[] uhrzeit = datum.split("T");

      System.out.println(uhrzeit[1] + "  1");
      //uhrzeit[1] = uhrzeit[1].substring(0,uhrzeit[1].length()-13);

      System.out.println(uhrzeit[1] + "  2");

      if (kg == false) {
        kg = true;
        start.setStyle("-fx-background-color: grey");
        start.setText("Zeiterfassung beenden");
        gehZeit.setText("");
        stundenL.setText("");
        kommZeit.setText(uhrzeit[1]);
        uhrzeitKommen = uhrzeit[1]; 

      
      }
      else {
        kg = false;

        Eintrag eintrag = new Eintrag(uhrzeitKommen, uhrzeit[1]);
        double stundenD = eintrag.getStunden();
        ObservableList<Eintrag> customers = tabelle.getItems();
        customers.add(eintrag);
        tabelle.setItems(customers);
  
        
        gehZeit.setText(uhrzeit[1]);

        start.setText("Zeiterfassung starten");
        start.setStyle("-fx-background-color:  #dbba51");
        stundenL.setText(Double.toString(stundenD));

       /*  kalenderPicker2.setValue(null);
        LocalDate date = LocalDate.now();
        kalenderPicker2.setValue(date);
        kalenderPicker2.setPromptText("Hallo"); */
       
      }
}

public static final LocalDate NOW_LOCAL_DATE (){
  String date = new SimpleDateFormat("dd-MM-  yyyy").format(Calendar.getInstance().getTime());
  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
  LocalDate localDate = LocalDate.parse(date , formatter);
  return localDate;
}

@FXML
void startHervorClick(MouseEvent event) {

    if (kg == true) {start.setStyle("-fx-background-color:  #696969"); }
    else {start.setStyle("-fx-background-color:  #a18634");}

}
@FXML
void startHintenClick(MouseEvent event) {

  if (kg == true) {start.setStyle("-fx-background-color:  grey");}
  else {start.setStyle("-fx-background-color:  #dbba51");}
}

@FXML
void zurueckHervorClick(MouseEvent event) {zurueck.setStyle("-fx-background-color:  #696969");}
@FXML
void zurueckHintenClick(MouseEvent event) {zurueck.setStyle("-fx-background-color: grey");

}

boolean ba = false;
@FXML
void bearbeitenClick(ActionEvent event) {

    if (ba == false) {
      editableCols();
      ba = true;
      bearbeiten.setText("Fertig");
      bearbeiten.setStyle("-fx-background-color: grey");
    }

    else {
      tabelle.setEditable(false);
      ba = false;
      bearbeiten.setText("Bearbeiten");
      bearbeiten.setStyle("-fx-background-color: #dbba51");


    }

}

private void editableCols() {
  kommen.setCellFactory(TextFieldTableCell.forTableColumn());
  kommen.setOnEditCommit(e -> e.getTableView().getItems().get(e.getTablePosition().getRow()).setKommen(e.getNewValue()));

  gegangen.setCellFactory(TextFieldTableCell.forTableColumn());
  gegangen.setOnEditCommit(e -> e.getTableView().getItems().get(e.getTablePosition().getRow()).setGegangen(e.getNewValue()));

  tabelle.setEditable(true);
}

@FXML
void KalenderPicker2Click(ActionEvent event) {

    LocalDate datum = kalenderPicker2.getValue();
    Person.hdatum = datum;


}

}

