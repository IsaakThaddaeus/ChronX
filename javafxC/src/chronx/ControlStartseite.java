package chronx;


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
    private Label aktuelleGleitzeit2;

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

        aktuelleGleitzeit2.setText("+ " + Double.toString(Person.gleitzeit));

        warngrenzePerson = Person.warngrenze;
        gleitzeitPerson = Person.gleitzeit;
        
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
        
       
      
    }

    @FXML
    void abmeldenM(MouseEvent event) {

        try {sts4(event);} 
        catch (IOException e) {e.printStackTrace();}
    }

    /**
     * @param event
     * @throws IOException
     */
    public void sts4 (MouseEvent event) throws IOException {

        Parent root2 = FXMLLoader.load(getClass().getResource("/chronx/Anmeldung.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root2);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void gleitzeitClick(MouseEvent event) throws IOException {

        Parent root2 = FXMLLoader.load(getClass().getResource("/chronx/Zeiterfassung.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root2);
        stage.setScene(scene);
        //ZeiterfassungControl.aktuelleGleitzeit.setText("hh");
        stage.show();

    }

    @FXML
    void einstellungenClick(MouseEvent event) throws IOException {

        Parent root2 = FXMLLoader.load(getClass().getResource("/chronx/Einstellungen.fxml"));
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


}
