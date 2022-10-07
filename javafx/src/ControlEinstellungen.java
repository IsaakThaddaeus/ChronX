import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.Node;

public class ControlEinstellungen {

    @FXML
    private Circle gruen;
    @FXML
    private Circle gruen1;
    @FXML
    private Circle gruen11;

    @FXML
    private Pane zurueckP;

    private Stage stage;
    private Scene scene;

    @FXML
    void zurueckPClick(MouseEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("Startseite.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show(); 

    }

    @FXML
    void zurueckPHervorClick(MouseEvent event) {zurueckP.setStyle("-fx-background-color: #696969");}
    @FXML
    void zurueckPHintenClick(MouseEvent event) {zurueckP.setStyle("-fx-background-color: grey");}

}


