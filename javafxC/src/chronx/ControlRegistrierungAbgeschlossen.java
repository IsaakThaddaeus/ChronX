package chronx;


import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.Node;

public class ControlRegistrierungAbgeschlossen {

    private Stage stage;
    private Scene scene;

    @FXML
    private Pane sprache;

    @FXML
    private Button zurueckStartseite;

    @FXML
    void zurueckStartseiteClick(ActionEvent event) throws IOException {

            Parent root = FXMLLoader.load(getClass().getResource("Anmeldung.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show(); 


    }

    @FXML
    void zurueckStartseiteHervorClick(MouseEvent event) {zurueckStartseite.setStyle("-fx-background-color: #696969");}

    @FXML
    void zurueckStartseiteHintenClick(MouseEvent event) {zurueckStartseite.setStyle("-fx-background-color: grey");}


}
