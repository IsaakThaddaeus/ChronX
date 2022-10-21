package org.fxapps.javafx.fatjar;


import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;


public class ControlAnmeldung {

    @FXML
    private Button anmelden;
    @FXML
    private Button englisch;
    @FXML
    public Button registrierung;
    @FXML
    private Button passwortVergessen;

    
    @FXML
    private TextField benutzername;
    @FXML
    public TextField falsch;

    @FXML
    private PasswordField passwort;

    @FXML
    private Pane sprache;

    @FXML
    private Label anmeldung;
    
    private Stage stage;
    private Scene scene;
    

    public void sts1 (Event event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/org/fxapps/javafx/fatjar/Startseite.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
           
    }

    public void sts2 (ActionEvent event){}

     /**
     * @param event
     */
    @FXML
    void anmeldenNext(Event event) {

        String bn = benutzername.getText();
        String ps = passwort.getText();
        try {
			if (Person.getEinlesenUndSpeichern().zuordnungDesEingeloggtenArbeiters(bn, ps)==null)  {
				
				System.out.println("ungültig");
				falsch.setVisible(true);
				
			}else {
				System.out.println("gültig");
				Person.email=bn;
				Person.passwort=ps;
				Person.setAktuellEingeloggtenArbeiter();
				System.out.println("BN: "+Person.email+" PS: "+Person.passwort);
				sts1(event);
				
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        

    }

    @FXML
    void enterClick(KeyEvent event) {

        switch (event.getCode()) {
            case ENTER: anmeldenNext(event);
        }
    }

    boolean inhalt = true;
    // true = deutsch
    // false = englisch

     @FXML
     void spracheClick(MouseEvent event) {

        if (inhalt == false) {englisch.setText("Deutsch");} 
        else {englisch.setText("Englisch");}
        
        englisch.setVisible(true);

    } 

    @FXML
    void EnglischClick(ActionEvent event) {

        if (inhalt == false) {

            inhalt = true;

            anmeldung.setText("Anmeldung");
            benutzername.setPromptText("Benutzername");
            passwort.setPromptText("Passwort");
            anmelden.setText("Anmelden");
            passwortVergessen.setText("Passwort vergessen");
            registrierung.setText("Registrierung");

        }

        else {
            
            inhalt = false;
            
            anmeldung.setText("sign in");
            benutzername.setPromptText("username");
            passwort.setPromptText("password");
            anmelden.setText("sign in");
            passwortVergessen.setText("forgot password");
            registrierung.setText("registration");

        }
        
        englisch.setVisible(false);

    }


    @FXML
    void anmeldenHervorClick(MouseEvent event) {anmelden.setStyle("-fx-background-color: #696969");}
    @FXML
    void anmeldenHintenClick(MouseEvent event) {anmelden.setStyle("-fx-background-color: grey");}

    @FXML
    void passwortVergessenHervorClick(MouseEvent event) {passwortVergessen.setStyle("-fx-background-color: #696969");}
    @FXML
    void passwortVergessenHintenClick(MouseEvent event) {passwortVergessen.setStyle("-fx-background-color: grey");}

    @FXML
    void registrierungHervorClick(MouseEvent event) {registrierung.setStyle("-fx-background-color: #696969");}
    @FXML
    void registrierungHintenClick(MouseEvent event) {registrierung.setStyle("-fx-background-color: grey");}

    @FXML
    void spracheHervorClcik(MouseEvent event) {sprache.setStyle("-fx-background-color: #696969");}
    @FXML
    void spracheHintenClick(MouseEvent event) {sprache.setStyle("-fx-background-color: white");}

    /**
     * @param event
     * @throws IOException
     */
    @FXML
    void registrierungClick(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/org/fxapps/javafx/fatjar/Registrierung.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show(); 

    }



    

}






