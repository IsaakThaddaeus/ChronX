package org.fxapps.javafx.fatjar;


import java.io.IOException;
import java.time.LocalDate;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class ControlRegistrierung {

    private Stage stage;
    private Scene scene;

    @FXML
    private TextField emailR;
    @FXML
    private TextField falsch;
    @FXML
    private TextField warngrenzeR;

    @FXML
    private Button englisch;
    @FXML
    private Button registrieren;
    @FXML
    private Button zurueck;
    
    @FXML
    private PasswordField passwort;
    @FXML
    private PasswordField passwortB;

    @FXML
    private Pane spracheR;

     @FXML
    private MenuItem menuItem30;
    @FXML
    private MenuItem menuItem35;
    @FXML
    private MenuItem menuItem40;
    
    @FXML
    private MenuButton menuButtonH;
    
    @FXML
    private DatePicker kalenderPickerR;

    @FXML
    private Label emailInkorrekt;
    @FXML
    private Label passwortInkorrekt;
    @FXML
    private Label passwortUnstimmung;

    @FXML
    private CheckBox leiterR;


    @FXML
    void EnglischClick(ActionEvent event) {}

    @FXML
    void enterClick(KeyEvent event) {}

    @FXML
    void spracheClick(MouseEvent event) {}

    @FXML
    void zurueckClick(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/org/fxapps/javafx/fatjar/Anmeldung.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show(); 

    }

    @FXML
    void menuItem30Click(ActionEvent event) {menuButtonH.setText("30");}
    @FXML
    void menuItem35Click(ActionEvent event) {menuButtonH.setText("35");}
    @FXML
    void menuItem40Click(ActionEvent event) {menuButtonH.setText("40");}

    @FXML
    void kalenderPickerRClick(ActionEvent event) {

        menuButtonH.setDisable(false);

        String geburtstag = kalenderPickerR.getValue().toString();
        System.out.println(geburtstag);
        String[] datenG = geburtstag.split("-");
        System.out.println(datenG[0] + datenG[1] + datenG[2]);

        int tagG = Integer.parseInt(datenG[2]);
        int monatG = Integer.parseInt(datenG[1]);
        int jahrG =Integer.parseInt(datenG[0]);
        
        String todaysDate = LocalDate.now().toString();
        System.out.println(todaysDate);
        String[] datenH = todaysDate.split("-");

        int tagH = Integer.parseInt(datenH[2]);
        int monatH = Integer.parseInt(datenH[1]);
        int jahrH =Integer.parseInt(datenH[0]);


        boolean min = false;
        int jahr = jahrH -jahrG;
        int monat = monatH - monatG;
        int tag = tagH - tagG;
       
        if (jahr < 18) {min = true;}

            else if (jahr == 18) {

                if (monat < 0) {min = true;}

                    else if (tag < 0) {min = true;}

         }

        
        if (min == true) {

            menuButtonH.setText("35");
            menuButtonH.setDisable(true);
        }      
        
    }

    @FXML
    void registrierenClick(ActionEvent event)  throws IOException {

        int counter = 0;

        try{ 
            
            String[] emailS = emailR.getText().split("@");

            if (emailS[1].equals("BBQgmbh.de")) { 
                
                counter++;
                emailInkorrekt.setVisible(false);

                String[] emailS1 = emailS[0].split(".");
                Person.vorname = emailS1[0];
                Person.nachname = emailS1[1];

                System.out.println(emailS1[0] + " halter " + emailS1[1]); 
            
            }

            else {emailInkorrekt.setVisible(true);}
    
        }
        catch (Exception e) {emailInkorrekt.setVisible(true); }


        if (passwort.getText().length() < 8 || passwort.getText().matches("[a-zA-Z]*")) {

            passwortInkorrekt.setVisible(true);

        }

        else {
            counter++;
            passwortInkorrekt.setVisible(false);

        }


        if (passwort.getText().equals(passwortB.getText())) {
            
            counter++;
            passwortUnstimmung.setVisible(false);

        
        }

        else {passwortUnstimmung.setVisible(true);}


        if (counter == 3){ 

            Person.email = emailR.getText();
            Person.passwort = passwort.getText();
            Person.geburtstag = kalenderPickerR.getValue();
            Person.wochenstunden = Double.parseDouble(menuButtonH.getText());
            Person.warngrenze = Double.parseDouble(warngrenzeR.getText());
            Person.leiter = leiterR.isSelected();

            Parent root = FXMLLoader.load(getClass().getResource("/org/fxapps/javafx/fatjar/RegistrierungAbgeschlossen.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show(); 
        }
    }

    @FXML
    void registrierenHervorClick(MouseEvent event) {registrieren.setStyle("-fx-background-color: #696969");}
    @FXML
    void registrierenHintenClick(MouseEvent event) {registrieren.setStyle("-fx-background-color: grey");}

    @FXML
    void spracheHervorClick(MouseEvent event) {spracheR.setStyle("-fx-background-color: #696969");}
    @FXML
    void spracheHintenClick(MouseEvent event) {spracheR.setStyle("-fx-background-color: white");}

    @FXML
    void zurueckHervorClick(MouseEvent event) {zurueck.setStyle("-fx-background-color: #696969");}
    @FXML
    void zurueckHintenClick(MouseEvent event) {zurueck.setStyle("-fx-background-color: grey");}


}