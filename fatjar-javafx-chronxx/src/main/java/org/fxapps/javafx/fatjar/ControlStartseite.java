package org.fxapps.javafx.fatjar;


import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.fxml.FXML;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;


public class ControlStartseite implements Initializable {

    private Stage stage;
    private Scene scene;
    

    @FXML
    private Pane abmelden;
    @FXML
    private Pane einstellungen;
    @FXML
    private Pane gleitzeit;
    @FXML
    private Pane kalender;
    @FXML
    private Pane urlaub;

    @FXML
    private Label aktuelleGleitzeit2;
    @FXML
    private Label name;


    @FXML
    private Circle gruen;
    @FXML
    private Circle gelb;
    @FXML
    private Circle rot;

    double warngrenzePerson;
    double gleitzeitPerson;
    public static int ampel;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    	
    	try {
    	
    	Person.gleitzeit = Math.round(Person.getZeitRechner().gibGleitzeitGesamt(Person.getAktuellEingeloggterArbeiter())* 100.0) / 100.0;  
    	
    	}
    	
    	catch (Exception e) {Person.gleitzeit = Person.getAktuellEingeloggterArbeiter().aktuelleGleitzeit;}
        aktuelleGleitzeit2.setText(Double.toString(Person.gleitzeit));

        warngrenzePerson = Person.warngrenze;
        gleitzeitPerson = Math.abs(Person.gleitzeit);
        
        
        if (gleitzeitPerson < warngrenzePerson) {
            
            ampel = 1;

            gruen.setStyle("-fx-fill: #139024");
            gelb.setStyle("-fx-fill: #000000");
            rot.setStyle("-fx-fill: #000000");
        }
        
        else if (gleitzeitPerson >= warngrenzePerson && gleitzeitPerson < 100) {
            
            ampel = 2;

            gruen.setStyle("-fx-fill: #000000");
            gelb.setStyle("-fx-fill: #dbba51");
            rot.setStyle("-fx-fill: #000000");
        }
        
        else {

            ampel = 3;

            gruen.setStyle("-fx-fill: #000000");
            gelb.setStyle("-fx-fill: #000000");
            rot.setStyle("-fx-fill: #c32828");
           
        }
        
        
       // name.setText("Hallo");
        
        
        
       name.setText(Person.vorname + " " + Person.nachname);
  
      
    }

    @FXML
    void abmeldenM(MouseEvent event) {
    	
        try {
        	Person.vorname = null;
        	Person.nachname = null;
        	Person.email=null;
			Person.passwort=null;
			EinlesenUndSpeichern.bereitseingelesen=false;
			EinlesenUndSpeichern.zuDruckendeWerte.clear();
			
			System.out.println("BN: "+Person.email+" PS: "+Person.passwort);

        	sts4(event);} 
        catch (IOException e) {e.printStackTrace();}
    }

    /**
     * @param event
     * @throws IOException
     */
    public void sts4 (MouseEvent event) throws IOException {

        Parent root2 = FXMLLoader.load(getClass().getResource("/org/fxapps/javafx/fatjar/Anmeldung.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root2);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void gleitzeitClick(MouseEvent event) throws IOException {

        Parent root2 = FXMLLoader.load(getClass().getResource("/org/fxapps/javafx/fatjar/Zeiterfassung.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root2);
        stage.setScene(scene);
        //ZeiterfassungControl.aktuelleGleitzeit.setText("hh");
        stage.show();

    }

    @FXML
    void einstellungenClick(MouseEvent event) throws IOException {

        Parent root2 = FXMLLoader.load(getClass().getResource("/org/fxapps/javafx/fatjar/Einstellungen.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root2);
        stage.setScene(scene);
        stage.show();


    }

    @FXML
    void hervorClick(MouseEvent event) {abmelden.setStyle("-fx-background-color: #696969");}
    @FXML
    void hintenClick(MouseEvent event) {abmelden.setStyle("-fx-background-color: grey");}

    @FXML
    void einstellungenHervorClick(MouseEvent event) {einstellungen.setStyle("-fx-background-color: #696969");}
    @FXML
    void einstellungenHintenClick(MouseEvent event) {einstellungen.setStyle("-fx-background-color: grey");}

    @FXML
    void gleitzeitHervorClick(MouseEvent event) {gleitzeit.setStyle("-fx-background-color: #696969");}
    @FXML
    void gleitzeitHintenClick(MouseEvent event) {gleitzeit.setStyle("-fx-background-color: grey");}

    @FXML
    void kalenderHervorClick(MouseEvent event) {kalender.setStyle("-fx-background-color: #696969");}
    @FXML
    void kalenderHintenClick(MouseEvent event) {kalender.setStyle("-fx-background-color: grey");}
    
    @FXML
    void urlaubClick(MouseEvent event) throws IOException {
    	
    	Parent root2 = FXMLLoader.load(getClass().getResource("/org/fxapps/javafx/fatjar/Urlaub.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root2);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void urlaubHervorClick(MouseEvent event) {urlaub.setStyle("-fx-background-color: #696969");}
    @FXML
    void urlaubHintenClick(MouseEvent event) {urlaub.setStyle("-fx-background-color: grey");}



}
