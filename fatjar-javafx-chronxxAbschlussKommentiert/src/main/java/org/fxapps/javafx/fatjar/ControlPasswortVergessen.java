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
	private Label labelEmailAdresse;
	@FXML
	private Label labelGeneralpasswort;
	@FXML
	private Label labelPasswortVergessen;

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

	private Arbeiter passwortZurueckArbeiter = null;

	boolean spracheWert = true;
	
	/* Diese Methode wechselt von Deutsch auf Englisch und umgekehrt
	   Es wird ein ActionEvent aus der FX Bibliothek übergeben
	   Es gibt keinen Rückgabewert
	*/

	@FXML
	void EnglischClick(ActionEvent event) {

		englisch.setVisible(false);

		if (spracheWert == true) {
			spracheWert = false;

			englisch.setText("Deutsch");

			labelPasswortVergessen.setText("Reset Password");
			labelEmailAdresse.setText("Please enter your e-mail address");
			benutzernameP.setPromptText("Username");
			labelGeneralpasswort.setText("Please enter the general password");
			generalPasswort.setPromptText("general password");
			passwortzurueck.setText("reset Password");
			neuesPasswort.setText("Please enter a new password (at least 8 characters and a special character)");
			passwortP1.setPromptText("Enter new password");
			passwortP2.setPromptText("Confirm Password");
			abbrechen.setText("Abort");
			bestaetigen.setText("Confirm");

		} else {
			spracheWert = true;

			englisch.setText("Englisch");

			labelPasswortVergessen.setText("Passwort zurücksetzen");
			labelEmailAdresse.setText("Bitte geben sie ihre E-mail Adresse ein");
			benutzernameP.setPromptText("Benutzername");
			labelGeneralpasswort.setText("Bitte geben sie das Generalpasswort ein");
			generalPasswort.setPromptText("Generalpasswort");
			passwortzurueck.setText("Passwort zurücksetzen");
			neuesPasswort.setText("Bitte vergeben sie ein neues Passwort (mind. 8 Zeichen und ein Sonderzeichen)");
			passwortP1.setPromptText("neues Passwort eingeben");
			passwortP2.setPromptText("Passwort bestätigen");
			abbrechen.setText("abbrechen");
			bestaetigen.setText("Bestätigen");
		}

	}
	
	/* Diese Methode startet die Anmeldeseite wenn die Passwortaenderung abgebrochen wird
	   Es wird ein ActionEvent aus der FX Bibliothek übergeben
	   Es gibt keinen Rückgabewert
	*/

	@FXML
	void abbrechenClick(ActionEvent event) throws IOException {

		Parent root = FXMLLoader.load(getClass().getResource("/org/fxapps/javafx/fatjar/Anmeldung.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();

	}
	
	/* Diese Methode startet die Anmeldeseite nach erfolgreich geaendertem Passwort
	   Es wird ein ActionEvent aus der FX Bibliothek übergeben
	   Es gibt keinen Rückgabewert
	*/
	
	@FXML
	void startseiteClick(ActionEvent event) throws IOException {

		Parent root = FXMLLoader.load(getClass().getResource("/org/fxapps/javafx/fatjar/Anmeldung.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	/* Diese Methode ueberprueft die eingegebenen Daten und speichert das neuse Passwort
	   Es wird ein ActionEvent aus der FX Bibliothek übergeben
	   Es gibt keinen Rückgabewert
	*/

	@FXML
	void bestaetigenClick(ActionEvent event) {

		if (passwortP1.getText().equals(passwortP2.getText()) == false || passwortP2.getText().matches("[a-zA-Z]*")
				|| passwortP2.getText().length() < 8) {
			falschpasswort.setVisible(true);
		} else {

			passwortZurueckArbeiter.passwort = passwortP2.getText();
			EinlesenUndSpeichern.abspeichernVonAenderungen(passwortZurueckArbeiter);

			paneerfolgreich.setVisible(true);
			paneP.setDisable(true);

		}

	}
	
	/* Diese Methode ueberprueft den eingegebenen Benutzer und das Generalpasswort und gibt bei richtiger Eningabe die Felder zum Aendern des Passworts frei.
	   Es wird ein ActionEvent aus der FX Bibliothek übergeben
	   Es gibt keinen Rückgabewert
	*/

	@FXML
	void passwortzurueckClick(ActionEvent event) {

		int zaehler = 0;

		EinlesenUndSpeichern pruefer = new EinlesenUndSpeichern();

		// Hier email Adresse suchen und schauen ob sie existiert

		try {
			if (pruefer.zuordnungDesArbeitersBeiPasswortZuruecksetzen(benutzernameP.getText(),
					generalPasswort.getText()).email != null) {

				passwortZurueckArbeiter = pruefer.zuordnungDesArbeitersBeiPasswortZuruecksetzen(benutzernameP.getText(),
						generalPasswort.getText());

				zaehler++;
			}

		} catch (Exception e) {
			falschGpasswort.setVisible(true);

		}

		if (zaehler == 1) {

			neuesPasswort.setDisable(false);
			passwortP1.setDisable(false);
			passwortP2.setDisable(false);
			bestaetigen.setDisable(false);
			falschGpasswort.setVisible(false);

		}

	}
	
	/* Diese Methode setzt den Button zum Sprache aendern auf sichtbar
	   Es wird ein ActionEvent aus der FX Bibliothek übergeben
	   Es gibt keinen Rückgabewert
	*/
	
	@FXML
	void spracheClick(MouseEvent event) {
		englisch.setVisible(true);

	}

	
	/* Diese Methoden verdunkeln die Buttons und Felder wenn mann mit dem Mauszeiger in das Feld geht, beziehungsweise setzen es wieder auf die ursprüngliche Farbe wenn man wieder hinaus geht.
	   Es wird ein ActionEvent aus der FX Bibliothek übergeben
	   Es gibt keinen Rückgabewert
	*/
	
	@FXML
	void bestaetigenHervorClick(MouseEvent event) {
		bestaetigen.setStyle("-fx-background-color: #696969");
	}

	@FXML
	void bestaetigenHintenClick(MouseEvent event) {
		bestaetigen.setStyle("-fx-background-color: grey");
	}

	@FXML
	void passwortzurueckHervorClick(MouseEvent event) {
		passwortzurueck.setStyle("-fx-background-color: #696969");
	}

	@FXML
	void passwortzurueckHintenClick(MouseEvent event) {
		passwortzurueck.setStyle("-fx-background-color: grey");
	}

	@FXML
	void spracheHervorClcik(MouseEvent event) {
		sprache.setStyle("-fx-background-color: #696969");
	}

	@FXML
	void spracheHintenClick(MouseEvent event) {
		sprache.setStyle("-fx-background-color: white");
	}
	
	@FXML
	void startseiteHervorClick(MouseEvent event) {
		startseite.setStyle("-fx-background-color: #696969");
	}

	@FXML
	void startseiteHintenClick(MouseEvent event) {
		startseite.setStyle("-fx-background-color: grey");
	}
	
	@FXML
	void abbrechenHervorClick(MouseEvent event) {
		abbrechen.setStyle("-fx-background-color: #696969");
	}

	@FXML
	void abbrechenHintenClick(MouseEvent event) {
		abbrechen.setStyle("-fx-background-color: grey");
	}

}
