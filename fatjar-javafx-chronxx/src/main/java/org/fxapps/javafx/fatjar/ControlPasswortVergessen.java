package org.fxapps.javafx.fatjar;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ControlPasswortVergessen {

    @FXML
    private Button abbrechen;
    @FXML
    private Button bestaetigen;
    @FXML
    private Button englisch;
    @FXML
    private Button passwortzurueck;
    @FXML
    private Button startseite;

    @FXML
    private Label anmeldung;
    @FXML
    private Label neuesPasswort;
    @FXML
    private Label falschGpasswort;
    @FXML
    private Label falschemail;
    @FXML
    private Label falschpasswort;

    @FXML
    private TextField benutzernameP;
    @FXML
    private TextField falsch;

    @FXML
    private PasswordField generalPasswort;
    @FXML
    private PasswordField passwortP2;
    @FXML
    private PasswordField passwortP1;

    @FXML
    private Pane sprache;
    
    private Stage stage;
    private Scene scene;
    
    @FXML
    private AnchorPane paneP;
    @FXML
    private AnchorPane paneerfolgreich;

    @FXML
    void EnglischClick(ActionEvent event) {

    }

    @FXML
    void abbrechenClick(ActionEvent event) throws IOException {
    	
    	Parent root = FXMLLoader.load(getClass().getResource("/org/fxapps/javafx/fatjar/Anmeldung.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();

    }

    @FXML
    void abbrechenHervorClick(MouseEvent event) {abbrechen.setStyle("-fx-background-color: #696969");}
    @FXML
    void abbrechenHintenClick(MouseEvent event) {abbrechen.setStyle("-fx-background-color: grey");}


    @FXML
    void bestaetigenClick(ActionEvent event) {
    	
    	if (passwortP1.getText().equals(passwortP2.getText()) == false || passwortP2.getText().matches("[a-zA-Z]*") ||  passwortP2.getText().length() < 8) {falschpasswort.setVisible(true);}
    	else {
    		
    		paneerfolgreich.setVisible(true);
    		
    		paneP.setDisable(true);
    		
    	}

    }

    @FXML
    void bestaetigenHervorClick(MouseEvent event) {bestaetigen.setStyle("-fx-background-color: #696969");}
    @FXML
    void bestaetigenHintenClick(MouseEvent event) {bestaetigen.setStyle("-fx-background-color: grey");}

    @FXML
    void passwortzurueckClick(ActionEvent event) {
    	
    	int zaehler = 0;
    
   	
    	// Hier email Adresse suchen und schauen ob sie existiert
    	
    	if (generalPasswort.getText().equals("a")) {zaehler++;}
    	else {falschGpasswort.setVisible(true);}
    	
    	if (zaehler == 1) {
    	
    		neuesPasswort.setDisable(false);
    		passwortP1.setDisable(false);
    		passwortP2.setDisable(false);
    		bestaetigen.setDisable(false);
    		
    	}
    	
    }

    @FXML
    void passwortzurueckHervorClick(MouseEvent event) {passwortzurueck.setStyle("-fx-background-color: #696969");}
    @FXML
    void passwortzurueckHintenClick(MouseEvent event) {passwortzurueck.setStyle("-fx-background-color: grey");}

    @FXML
    void spracheClick(MouseEvent event) {}

    @FXML
    void spracheHervorClcik(MouseEvent event) {sprache.setStyle("-fx-background-color: #696969");}
    @FXML
    void spracheHintenClick(MouseEvent event) {sprache.setStyle("-fx-background-color: white");}
    
    @FXML
    void startseiteClick(ActionEvent event) throws IOException {
    	
    	Parent root = FXMLLoader.load(getClass().getResource("/org/fxapps/javafx/fatjar/Anmeldung.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
    }

    @FXML
    void startseiteHervorClick(MouseEvent event) {startseite.setStyle("-fx-background-color: #696969");}
    @FXML
    void startseiteHintenClick(MouseEvent event) {startseite.setStyle("-fx-background-color: grey");}

}
