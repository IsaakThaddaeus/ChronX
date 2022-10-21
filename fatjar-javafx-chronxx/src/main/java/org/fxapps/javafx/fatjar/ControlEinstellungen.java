package org.fxapps.javafx.fatjar;


import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.geometry.*;

public class ControlEinstellungen implements Initializable {

    @FXML
    private Button aendern;
    @FXML
    private Button zurueckP;
    @FXML
    private Button acloeschen;

    @FXML
    private Circle gruen;
    @FXML
    private Circle circleYellow;
    @FXML
    private Circle gruen11;

    @FXML
    private TextField emailE;
    @FXML
    private TextField geburtsdatumE;
    @FXML
    private TextField nachnameE;
    @FXML
    private TextField vornameE;

    @FXML
    private Label passwortAnforderungen;

    @FXML
    private PasswordField passwort1E;

    @FXML
    private PasswordField passwort2E;

    @FXML
    private Slider sliderAmpel;

    private Stage stage;
    private Scene scene;
    
    Text text = new Text();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

                sliderAmpel.setValue(Person.warngrenze);
                circleYellow.setCenterX(Person.warngrenze * 4.238);

                text = new Text(Double.toString(Person.warngrenze));

                sliderAmpel.skinProperty().addListener((obs,old,skin)->{
                    if(skin!=null){
                        StackPane thumb = (StackPane)sliderAmpel.lookup(".thumb");
                        thumb.setPadding(new Insets(5));
                        thumb.getChildren().add(text);
                        
                    }
                });
                
                sliderAmpel.valueProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {circleYellow.setCenterX(new_val.doubleValue() * 4.238);});
                sliderAmpel.valueProperty().addListener((obs,old,val)->text.setText(val.intValue()+ ""));
                Person.warngrenze = sliderAmpel.getValue();

                nachnameE.setText(Person.nachname);
                vornameE.setText(Person.vorname);
                //geburtsdatumE.setText(formatter.format(Person.geburtstag));
                emailE.setText(Person.email);
                passwort1E.setText(Person.passwort);
                passwort2E.setText(Person.passwort);


                
        }

    @FXML
    void zurueckPClick(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/org/fxapps/javafx/fatjar/Startseite.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show(); 

    }

    boolean riegel = false;

    @FXML
    void aendernClick(ActionEvent event) {

        if (riegel == false) {

            riegel = true;
            aendern.setText("Fertig");
            aendern.setStyle("-fx-background-color: #dbba51");
            acloeschen.setVisible(true);

           nachnameE.setDisable(false);
           vornameE.setDisable(false);
           geburtsdatumE.setDisable(false);
           emailE.setDisable(false);
           passwort1E.setDisable(false);
           passwort2E.setVisible(true);
            

        }

        else {

            riegel = false;

            aendern.setText("Ändern");
            aendern.setStyle("-fx-background-color: #4d4d4d");
            //aendern.setStyle("-fx-text-fill: #dbba51");

            nachnameE.setDisable(true);
           vornameE.setDisable(true);
           geburtsdatumE.setDisable(true);
           emailE.setDisable(true);
           passwort1E.setDisable(true);
           passwort2E.setVisible(false);

           Person.nachname = nachnameE.getText();
           Person.vorname = vornameE.getText();

           Person.geburtstag = LocalDate.parse(geburtsdatumE.getText(), formatter);

           System.out.println(Person.geburtstag);
           
           Person.email = emailE.getText();

           if (passwort1E.getText().length() < 8 || passwort1E.getText().matches("[a-zA-Z]*")) {passwortAnforderungen.setVisible(true);}
           else {
            
            passwortAnforderungen.setVisible(false);
            Person.passwort = passwort2E.getText();

            } 

        }


    }

    @FXML
    void sliderAmpelClick(MouseEvent event) {Person.warngrenze = Math.floor(sliderAmpel.getValue());}

    @FXML
    void zurueckPHervorClick(MouseEvent event) {zurueckP.setStyle("-fx-background-color: #696969");}
    @FXML
    void zurueckPHintenClick(MouseEvent event) {zurueckP.setStyle("-fx-background-color: grey");}

    @FXML
    void aendernHervorClick(MouseEvent event) {

        if (riegel == false) {aendern.setStyle("-fx-background-color: #8d8d8d");}
        else {aendern.setStyle("-fx-background-color: #a18634");}
    }
    @FXML
    void aendernHintenClick(MouseEvent event) {

        if (riegel == false) {aendern.setStyle("-fx-background-color: #b5b5b5");}
        else {aendern.setStyle("-fx-background-color: #dbba51");}

}
    
    @FXML
    void acloeschenClick(ActionEvent event) throws IOException {
    	
    	Parent root = FXMLLoader.load(getClass().getResource("/org/fxapps/javafx/fatjar/Anmeldung.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show(); 

    }

    @FXML
    void acloeschenHervorClick(MouseEvent event) {acloeschen.setStyle("-fx-background-color: black");}
    @FXML
    void acloeschenHintenClick(MouseEvent event) {acloeschen.setStyle("-fx-background-color:  #2c2c2c");}


}


