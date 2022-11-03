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
	private Label registrierung;
	@FXML
	private Label hinweisEmail;
	@FXML
	private Label hinweisPasswort;
	@FXML
	private Label hinweisGeburtsdatum;
	@FXML
	private Label hinweisAngestellter;
	@FXML
	private Label hinweisWochenstunden;
	@FXML
	private Label hinweisGrenze;
	@FXML
	private Label fehlerGrenze;

	@FXML
	private CheckBox leiterR;

	boolean sprache = true;
	
	// Diese Methode wechselt von Deutsch zu Englisch und umgekehrt
	// Es wird ein ActionEvent aus der FX Bibliothek uebergeben
	// Es gibt keinen Rückgabewert

	@FXML
	void EnglischClick(ActionEvent event) {

		if (sprache == true) {
			registrierung.setText("registration");
			hinweisEmail.setText("Please enter your business email address here");
			emailR.setPromptText("firstname.lastname@BBQgmbh.de");
			emailInkorrekt.setText("your email address is not correct");
			hinweisPasswort.setText("Please enter a password with at least 8 digits and a special character");
			passwort.setPromptText("password");
			passwortB.setPromptText("confirm password");
			passwortInkorrekt.setText("your password does not meet the requirements");
			passwortUnstimmung.setText("their passwords do not match");
			hinweisGeburtsdatum.setText("Please enter your birthday");
			hinweisAngestellter.setText("Are you an executive ?");
			kalenderPickerR.setPromptText("dd.mm.yyyy");
			leiterR.setText("executive");
			hinweisWochenstunden.setText("Please select your weekly hours");
			hinweisGrenze.setText("Please enter your flextime warning limit");
			menuButtonH.setText("weekly hours");
			warngrenzeR.setPromptText("limit");
			fehlerGrenze.setText("The limit must be in the range between 1 and 100");
			zurueck.setText("back");
			registrieren.setText("registrate");
			englisch.setText("Deutsch");

			sprache = false;

		}

		else {

			registrierung.setText("Registrierung");
			hinweisEmail.setText("Bitte geben sie hier ihre geschäftliche E-mail Adresse ein");
			emailR.setPromptText("vorname.nachname@BBQgmbh.de");
			emailInkorrekt.setText("ihre Email-Adresse ist nicht korrekt");
			hinweisPasswort.setText("Bitte geben sie ein Passwort mit mind. 8 Zeichen und einem Sonderzeichen ein");
			passwort.setPromptText("passwort");
			passwortB.setPromptText("Passwort bestätigen");
			passwortInkorrekt.setText("ihr Passswort enspricht nicht den Anforderungen");
			passwortUnstimmung.setText("ihre Passwörter stimmen nicht überein");
			hinweisGeburtsdatum.setText("Bitte geben sie ihr Geburtsdatum ein");
			hinweisAngestellter.setText("Sind sie ein leitender Angestellter ?");
			kalenderPickerR.setPromptText("TT.MM.JJJJ");
			leiterR.setText("Leitender Angestellter");
			hinweisWochenstunden.setText("Bitte wählen sie ihre Wochenstunden aus");
			hinweisGrenze.setText("Bitte geben sie ihre Gleitzeitwarngrenze an");
			menuButtonH.setText("Wochenstunden");
			warngrenzeR.setPromptText("Grenze");
			fehlerGrenze.setText("Die Grenze muss sich im Bereich zwischen 1 und 100 befinden");
			zurueck.setText("Zurück");
			registrieren.setText("Registrieren");
			englisch.setText("Englisch");

			sprache = true;

		}

		englisch.setVisible(false);

	}
	
	// Diese Methode macht den Button zur Sprachaenderung sichtbar
	// Es wird ein ActionEvent aus der FX Bibliothek uebergeben
	// Es gibt keinen Rueckgabewert

	@FXML
	void spracheClick(MouseEvent event) {

		englisch.setVisible(true);

	}
	
	/* Diese Methode ruft die Anmeldeseite auf sie wird ausgefuehrt wenn der Benutzer auf Zurueck clickt.
       Es wird ein ActionEvent aus der FX Bibliothek uebergeben
	   Es gibt keinen Rueckgabewert
	*/

	@FXML
	void zurueckClick(ActionEvent event) throws IOException {

		Parent root = FXMLLoader.load(getClass().getResource("/org/fxapps/javafx/fatjar/Anmeldung.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();

	}
	
	/* In den folgenden drei Methoden werden dies Stundenzahlen in dem MenuButton gesetzt. Je nachdem welche Stunde ausgewählt wird, wird sie auch in den MenuButton geschrieben.
       Es wird ein ActionEvent aus der FX Bibliothek uebergeben
	   Es gibt keinen Rueckgabewert
	*/

	@FXML
	void menuItem30Click(ActionEvent event) {
		menuButtonH.setText("30");
	}

	@FXML
	void menuItem35Click(ActionEvent event) {
		menuButtonH.setText("35");
	}

	@FXML
	void menuItem40Click(ActionEvent event) {
		menuButtonH.setText("40");
	}
	
	/* Diese Methode wird aufgerufen wenn der Benutzer sein Geburtsdatum ausgewählt hat. Es wird ueberprueft ob die Person minderjaehrig ist oder nicht.
       Es wird ein ActionEvent aus der FX Bibliothek uebergeben
	   Es gibt keinen Rueckgabewert
	*/

	@FXML
	void kalenderPickerRClick(ActionEvent event) {

		menuButtonH.setDisable(false);

		String geburtstag = kalenderPickerR.getValue().toString();
		
		String[] datenG = geburtstag.split("-");

		int tagG = Integer.parseInt(datenG[2]);
		int monatG = Integer.parseInt(datenG[1]);
		int jahrG = Integer.parseInt(datenG[0]);

		String todaysDate = LocalDate.now().toString();
		String[] datenH = todaysDate.split("-");

		int tagH = Integer.parseInt(datenH[2]);
		int monatH = Integer.parseInt(datenH[1]);
		int jahrH = Integer.parseInt(datenH[0]);

		boolean min = false;
		int jahr = jahrH - jahrG;
		int monat = monatH - monatG;
		int tag = tagH - tagG;

		if (jahr < 18) {
			min = true;
		}

		else if (jahr == 18) {

			if (monat < 0) {
				min = true;
			}

			else if (tag < 0) {
				min = true;
			}

		}

		if (min == true) {

			menuButtonH.setText("35");
			menuButtonH.setDisable(true);
		}

	}
	
	/* In dieser Methode werden die eingegebenen Daten zur Registrierung ueberprueft. Wurden die Daten korrekt eingegeben wird der neue Benutzer angelegt, ansonsten kommt eine entsprechende Fehlermeldung
       Es wird ein ActionEvent aus der FX Bibliothek uebergeben
	   Es gibt keinen Rueckgabewert
	*/

	@FXML
	void registrierenClick(ActionEvent event) throws IOException {

		int counter = 0;

		try {

			String[] emailS = emailR.getText().split("@");

			if (emailS[1].equals("BBQgmbh.de")) {

				counter++;
				emailInkorrekt.setVisible(false);

			}

			else {
				emailInkorrekt.setVisible(true);
			}

		} catch (Exception e) {
			emailInkorrekt.setVisible(true);
		}

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

		else {
			passwortUnstimmung.setVisible(true);
		}

		try {

			if (Integer.parseInt(warngrenzeR.getText()) < 1
					|| Integer.parseInt(warngrenzeR.getText()) > 100 /* || warngrenzeR.getText().equals("") */) {
				fehlerGrenze.setVisible(true);

			} else {
				counter++;
				fehlerGrenze.setVisible(false);

			}
		} catch (Exception e) {
			fehlerGrenze.setVisible(true);

		}

		if (counter == 4) {

			EinlesenUndSpeichern.arbeiterRegistrieren(emailR.getText(), passwort.getText(), kalenderPickerR.getValue(),
					Double.parseDouble(menuButtonH.getText()), Double.parseDouble(warngrenzeR.getText()),
					leiterR.isSelected());
		
			Parent root = FXMLLoader
					.load(getClass().getResource("/org/fxapps/javafx/fatjar/RegistrierungAbgeschlossen.fxml"));
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}
	}
	
	/* Diese Methoden verdunkeln die Buttons und Felder wenn mann mit dem Mauszeiger in das Feld geht, beziehungsweise setzen es wieder auf die ursprüngliche Farbe wenn man wieder hinaus geht.
	   Es wird ein ActionEvent aus der FX Bibliothek übergeben
	   Es gibt keinen Rückgabewert
	*/

	@FXML
	void registrierenHervorClick(MouseEvent event) {
		registrieren.setStyle("-fx-background-color: #696969");
	}

	@FXML
	void registrierenHintenClick(MouseEvent event) {
		registrieren.setStyle("-fx-background-color: grey");
	}

	@FXML
	void spracheHervorClick(MouseEvent event) {
		spracheR.setStyle("-fx-background-color: #696969");
	}

	@FXML
	void spracheHintenClick(MouseEvent event) {
		spracheR.setStyle("-fx-background-color: white");
	}

	@FXML
	void zurueckHervorClick(MouseEvent event) {
		zurueck.setStyle("-fx-background-color: #696969");
	}

	@FXML
	void zurueckHintenClick(MouseEvent event) {
		zurueck.setStyle("-fx-background-color: grey");
	}

}