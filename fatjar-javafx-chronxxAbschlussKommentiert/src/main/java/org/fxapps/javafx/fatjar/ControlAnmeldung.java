package org.fxapps.javafx.fatjar;


import java.io.IOException;

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
	private Button passwortvergessen;

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
	
	// Diese Methode wird aufgerufen wenn der Button zum Anmelden geklickt wird. Bei richtigter Eingabe der Anemeldedaten wird die Startseite aufgerufen.
	// Es wird ein Event aus der FX Bibliothek übergeben
	// Es gibt keinen Rückgabewert
	
	@FXML
	void anmeldenNext(Event event) {

		String bn = benutzername.getText();
		String ps = passwort.getText();
		try {
			if (Person.getEinlesenUndSpeichern().zuordnungDesEingeloggtenArbeiters(bn, ps) == null) {

				
				falsch.setVisible(true);

			} else {
				System.out.println("gültig");
				Person.email = bn;
				Person.passwort = ps;
				Person.setAktuellEingeloggtenArbeiter();
				Person.geburtstag = Person.getAktuellEingeloggterArbeiter().geburtstag;
				Person.warngrenze = Person.getAktuellEingeloggterArbeiter().gleitzeitWarngrenze;
				Person.wochenstunden = Person.getAktuellEingeloggterArbeiter().wochenstunden;
				Person.sprache = Person.getAktuellEingeloggterArbeiter().sprache;

				Person.getEinlesenUndSpeichern().nameAusEmailErmittler(Person.getAktuellEingeloggterArbeiter());

				try {
					Person.getZeitRechner().vergangeneTageInDatenBankAnlegen(Person.getAktuellEingeloggterArbeiter());
				} catch (Exception e) {
					
				}

				Parent root = FXMLLoader.load(getClass().getResource("/org/fxapps/javafx/fatjar/Startseite.fxml"));
				stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				scene = new Scene(root);
			//	scene.setFill(Color.TRANSPARENT);

				stage.setScene(scene);
				//stage.initStyle(StageStyle.TRANSPARENT);
				stage.show();

			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}
	
	// Diese Methode wird aufgerufen wenn der Button zum Anmelden geklickt wird. Bei richtigter Eingabe der Anemeldedaten wird die Startseite aufgerufen.
	// Es wird ein Event aus der FX Bibliothek übergeben
	// Es gibt keinen Rückgabewert

	@FXML
	void enterClick(KeyEvent event) {

		switch (event.getCode()) {
		case ENTER:
			anmeldenNext(event);
		}
	}

	boolean inhalt = true;
	// true = deutsch
	// false = englisch
	
	// Diese Methode wird aufgerufen wenn der Button zum Anmelden geklickt wird. Bei richtigter Eingabe der Anemeldedaten wird die Startseite aufgerufen.
	// Es wird ein Event aus der FX Bibliothek übergeben
	// Es gibt keinen Rückgabewert

	@FXML
	void spracheClick(MouseEvent event) {

		if (inhalt == false) {
			englisch.setText("Deutsch");
		} else {
			englisch.setText("Englisch");
		}

		englisch.setVisible(true);

	}
	
	// Diese Methode wandelt alles in Englisch beziehungsweise in Deutsch um.
	// Es wird ein ActionEvent aus der FX Bibliothek übergeben
	// Es gibt keinen Rückgabewert

	@FXML
	void EnglischClick(ActionEvent event) {

		if (inhalt == false) {

			inhalt = true;

			anmeldung.setText("Anmeldung");
			benutzername.setPromptText("Benutzername");
			passwort.setPromptText("Passwort");
			anmelden.setText("Anmelden");
			passwortvergessen.setText("Passwort vergessen");
			registrierung.setText("Registrierung");

		}

		else {

			inhalt = false;

			anmeldung.setText("sign in");
			benutzername.setPromptText("username");
			passwort.setPromptText("password");
			anmelden.setText("sign in");
			passwortvergessen.setText("forgot password");
			registrierung.setText("registration");

		}

		englisch.setVisible(false);

	}
	
	// Diese Methode öffnet sie Seite zur Registrierung
	// Es wird ein ActionEvent aus der FX Bibliothek übergeben
	// Es gibt keinen Rückgabewert
	
	@FXML
	void registrierungClick(ActionEvent event) throws IOException {

		Parent root = FXMLLoader.load(getClass().getResource("/org/fxapps/javafx/fatjar/Registrierung.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();

	}
	
	// Diese Methode öffnet sie Seite zum Zurücksetzen des Passworts für den Fall, dass man sein Passwort vergessen hat.
	// Es wird ein ActionEvent aus der FX Bibliothek übergeben
	// Es gibt keinen Rückgabewert

	@FXML
	void passwortvergessenClick(ActionEvent event) throws IOException {

		Parent root = FXMLLoader.load(getClass().getResource("/org/fxapps/javafx/fatjar/PasswortVergessen.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	// Diese Methoden verdunkeln die Buttons und Felder wenn mann mit dem Mauszeiger in das Feld geht, beziehungsweise setzen es wieder auf die ursprüngliche Farbe wenn man wieder hinaus geht.
	// Es wird ein MouseEvent aus der FX Bibliothek übergeben
	// Es gibt keinen Rückgabewert

	@FXML
	void anmeldenHervorClick(MouseEvent event) {
		anmelden.setStyle("-fx-background-color: #696969");
	}

	@FXML
	void anmeldenHintenClick(MouseEvent event) {
		anmelden.setStyle("-fx-background-color: grey");
	}

	@FXML
	void registrierungHervorClick(MouseEvent event) {
		registrierung.setStyle("-fx-background-color: #696969");
	}

	@FXML
	void registrierungHintenClick(MouseEvent event) {
		registrierung.setStyle("-fx-background-color: grey");
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
	void passwortvergessenHervorClick(MouseEvent event) {
		passwortvergessen.setStyle("-fx-background-color: #696969");
	}

	@FXML
	void passwortvergessenHintenClick(MouseEvent event) {
		passwortvergessen.setStyle("-fx-background-color: grey");
	}

}
