package org.fxapps.javafx.fatjar;

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
	
	/* Diese Methode ruft die Anmeldeseite wieder auf
	   Es wird ein ActionEvent aus der FX Bibliothek übergeben
	   Es gibt keinen Rückgabewert
	*/

	@FXML
	void zurueckStartseiteClick(ActionEvent event) throws IOException {

		Parent root = FXMLLoader.load(getClass().getResource("Anmeldung.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();

	}
	
	/* Diese Methoden verdunkeln die Buttons und Felder wenn mann mit dem Mauszeiger in das Feld geht, beziehungsweise setzen es wieder auf die ursprüngliche Farbe wenn man wieder hinaus geht.
	   Es wird ein ActionEvent aus der FX Bibliothek übergeben
	   Es gibt keinen Rückgabewert
	*/

	@FXML
	void zurueckStartseiteHervorClick(MouseEvent event) {
		zurueckStartseite.setStyle("-fx-background-color: #696969");
	}

	@FXML
	void zurueckStartseiteHintenClick(MouseEvent event) {
		zurueckStartseite.setStyle("-fx-background-color: grey");
	}

}
