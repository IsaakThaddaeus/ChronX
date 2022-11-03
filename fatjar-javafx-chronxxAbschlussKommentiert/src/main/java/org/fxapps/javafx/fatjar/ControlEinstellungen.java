package org.fxapps.javafx.fatjar;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.geometry.*;

public class ControlEinstellungen implements Initializable {

	@FXML
	private AnchorPane paneloeschen;
	@FXML
	private AnchorPane paneschwarz;

	@FXML
	private Tab tabGrenzwertanpassung;
	@FXML
	private Tab tabKontoeinstellungen;
	@FXML
	private Tab tabSpracheinstellungen;

	@FXML
	private Button aendern;
	@FXML
	private Button zurueckP;
	@FXML
	private Button acloeschen;
	@FXML
	private Button ja;
	@FXML
	private Button nein;
	@FXML
	private Button sprache;

	@FXML
	private Circle gruen;
	@FXML
	private Circle circleYellow;
	@FXML
	private Circle gruen11;

	@FXML
	private TextField emailE;
	@FXML
	private TextField geburtsdatumE;

	@FXML
	private Label passwortAnforderungen;
	@FXML
	private Label vornachname;
	@FXML
	private Label labelEinstellungen;
	@FXML
	private Label labelEmailAdresse;
	@FXML
	private Label labelGeburtsdatum;
	@FXML
	private Label labelGrenzwertanpassung;
	@FXML
	private Label labelKontoeinstellungen;
	@FXML
	private Label labelPasswort;
	@FXML
	private Label labelSpracheWechseln;
	@FXML
	private Label labelSpracheinstellungen;

	@FXML
	private PasswordField passwort1E;

	@FXML
	private PasswordField passwort2E;

	@FXML
	private Slider sliderAmpel;

	private Stage stage;
	private Scene scene;

	Text text = new Text();
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {

		sliderAmpel.setValue(Person.warngrenze);
		circleYellow.setCenterX(Person.warngrenze * 4.238);

		text = new Text(Double.toString(Person.warngrenze));

		sliderAmpel.skinProperty().addListener((obs, old, skin) -> {
			if (skin != null) {
				StackPane thumb = (StackPane) sliderAmpel.lookup(".thumb");
				thumb.setPadding(new Insets(5));
				thumb.getChildren().add(text);

			}
		});

		sliderAmpel.valueProperty()
				.addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
					circleYellow.setCenterX(new_val.doubleValue() * 4.255);
				});
		sliderAmpel.valueProperty().addListener((obs, old, val) -> text.setText(val.intValue() + ""));
		Person.warngrenze = sliderAmpel.getValue();

		vornachname.setText(Person.vorname + " " + Person.nachname);
		emailE.setText(Person.email);
		passwort1E.setText(Person.passwort);
		passwort2E.setText(Person.passwort);

		DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd.MM.uuuu");
		geburtsdatumE.setText(Person.geburtstag.format(formatters));

		if (Person.getAktuellEingeloggterArbeiter().sprache == false) {
			sprache.setText("Englisch");
			spracheAendern(false);

		}

		else {
			sprache.setText("Deutsch");
			spracheAendern(true);

		}

	}

	@FXML
	void zurueckPClick(ActionEvent event) throws IOException {

		Parent root = FXMLLoader.load(getClass().getResource("/org/fxapps/javafx/fatjar/Startseite.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();

	}

	boolean riegel = false;

	@FXML
	void aendernClick(ActionEvent event) {

		if (riegel == false) {

			riegel = true;
			aendern.setText("Speichern");
			aendern.setStyle("-fx-background-color: #dbba51");
			acloeschen.setVisible(true);

//           nachnameE.setDisable(false);
//           vornameE.setDisable(false);
			geburtsdatumE.setDisable(false);
			emailE.setDisable(false);
			passwort1E.setDisable(false);
			passwort2E.setVisible(true);

		}

		else {

			if (passwort1E.getText().length() < 8 || passwort1E.getText().matches("[a-zA-Z]*")
					|| passwort1E.getText().equals(passwort2E.getText()) == false) {
				passwortAnforderungen.setVisible(true);
			} else {

				passwortAnforderungen.setVisible(false);
				Person.passwort = passwort2E.getText();
				Person.getAktuellEingeloggterArbeiter().passwort = Person.passwort;

				riegel = false;

				aendern.setText("Ändern");
				aendern.setStyle("-fx-background-color: #4d4d4d");
				acloeschen.setVisible(false);

				geburtsdatumE.setDisable(true);
				emailE.setDisable(true);
				passwort1E.setDisable(true);
				passwort2E.setVisible(false);

				Person.geburtstag = LocalDate.parse(geburtsdatumE.getText(), formatter);
				Person.getAktuellEingeloggterArbeiter().geburtstag = Person.geburtstag;

				System.out.println(Person.geburtstag + " Hallo " + Person.getAktuellEingeloggterArbeiter().geburtstag);

				Person.email = emailE.getText();
				Person.getAktuellEingeloggterArbeiter().email = Person.email;

				EinlesenUndSpeichern.abspeichernVonAenderungen(Person.getAktuellEingeloggterArbeiter());

			}

		}

	}

	@FXML
	void sliderAmpelClick(MouseEvent event) {
		Person.warngrenze = Math.floor(sliderAmpel.getValue());
		Person.getAktuellEingeloggterArbeiter().gleitzeitWarngrenze = Person.warngrenze;
		EinlesenUndSpeichern.abspeichernVonAenderungen(Person.getAktuellEingeloggterArbeiter());

		System.out.println("Warngrenze nach Änderung " + Person.warngrenze);
	}

	@FXML
	void zurueckPHervorClick(MouseEvent event) {
		zurueckP.setStyle("-fx-background-color: #696969");
	}

	@FXML
	void zurueckPHintenClick(MouseEvent event) {
		zurueckP.setStyle("-fx-background-color: grey");
	}

	@FXML
	void aendernHervorClick(MouseEvent event) {

		if (riegel == false) {
			aendern.setStyle("-fx-background-color: #8d8d8d");
		} else {
			aendern.setStyle("-fx-background-color: #a18634");
		}
	}

	@FXML
	void aendernHintenClick(MouseEvent event) {

		if (riegel == false) {
			aendern.setStyle("-fx-background-color: #b5b5b5");
		} else {
			aendern.setStyle("-fx-background-color: #dbba51");
		}

	}

	@FXML
	void acloeschenClick(ActionEvent event) {

		paneloeschen.setVisible(true);
		paneschwarz.setDisable(true);

	}

	@FXML
	void acloeschenHervorClick(MouseEvent event) {
		acloeschen.setStyle("-fx-background-color: black");
	}

	@FXML
	void acloeschenHintenClick(MouseEvent event) {
		acloeschen.setStyle("-fx-background-color:  #2c2c2c");
	}

	@FXML
	void jaClick(ActionEvent event) throws IOException {

		boolean funktioniert = Person.getEinlesenUndSpeichern()
				.arbeiterloeschen(Person.getAktuellEingeloggterArbeiter());

		System.out.println(funktioniert);

		Parent root = FXMLLoader.load(getClass().getResource("/org/fxapps/javafx/fatjar/Anmeldung.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	void jaHervorClick(MouseEvent event) {
		ja.setStyle("-fx-background-color: #696969");
	}

	@FXML
	void jaHintenClick(MouseEvent event) {
		ja.setStyle("-fx-background-color: grey");
	}

	@FXML
	void neinClick(ActionEvent event) {

		paneloeschen.setVisible(false);
		paneschwarz.setDisable(false);

	}

	@FXML
	void neinHervorClick(MouseEvent event) {
		nein.setStyle("-fx-background-color: #696969");
	}

	@FXML
	void neinHintenClick(MouseEvent event) {
		nein.setStyle("-fx-background-color: grey");
	}

	@FXML
	void spracheClick(ActionEvent event) {

		if (Person.getAktuellEingeloggterArbeiter().sprache == true) {
			Person.getAktuellEingeloggterArbeiter().sprache = false;
			sprache.setText("Englisch");

		} else {
			Person.getAktuellEingeloggterArbeiter().sprache = true;
			sprache.setText("Deutsch");
		}
		EinlesenUndSpeichern.abspeichernVonAenderungen(Person.getAktuellEingeloggterArbeiter());
		System.out.println("Sprache ist auf: " + Person.getAktuellEingeloggterArbeiter().sprache);
		spracheAendern(Person.getAktuellEingeloggterArbeiter().sprache);

	}

	public void spracheAendern(boolean s) {

		if (s == true) {

			labelEinstellungen.setText("Settings");
			tabGrenzwertanpassung.setText("limit adjustment");
			tabGrenzwertanpassung.setStyle("-fx-padding: 10em -3em 10em -3em; -fx-background-radius: 10; ");
			labelGrenzwertanpassung.setText("limit adjustment");
			tabSpracheinstellungen.setText("language settings");
			tabSpracheinstellungen.setStyle("-fx-padding: 10em -3em 10em -3.2em; -fx-background-radius: 10; ");
			labelSpracheinstellungen.setText("language settings");
			labelSpracheWechseln.setText("Change language");
			tabKontoeinstellungen.setText("Account Settings");
			tabKontoeinstellungen.setStyle("-fx-padding: 10em -3em 10em -3em; -fx-background-radius: 10; ");
			labelKontoeinstellungen.setText("Account Settings:");
			labelEmailAdresse.setText("E-mail adress:");
			labelGeburtsdatum.setText("Date of birth");
			labelPasswort.setText("Password");

			if (riegel == false) {
				aendern.setText("Change");
			} else {
				aendern.setText("Save");
			}

			passwortAnforderungen.setText("Password does not meet the requirements");
			acloeschen.setText("Delete Account");

		}

		else {

			labelEinstellungen.setText("Einstellungen");
			tabGrenzwertanpassung.setText("Grenzwertanpassung");
			tabGrenzwertanpassung.setStyle("-fx-padding:  10em -3em 10em -4.2em; -fx-background-radius: 10; ");
			labelGrenzwertanpassung.setText("Grenzwertanpassung");
			tabSpracheinstellungen.setText("Spracheinstellungen");
			tabSpracheinstellungen.setStyle("-fx-padding:  10em -1em 10em -4em; -fx-background-radius: 10; ");
			labelSpracheinstellungen.setText("Spracheinstellungen");
			labelSpracheWechseln.setText("Sprache wechseln");
			tabKontoeinstellungen.setText("Kontoeinstellungen");
			tabKontoeinstellungen.setStyle("-fx-padding:  10em -1em 10em -3.7em; -fx-background-radius: 10; ");
			labelKontoeinstellungen.setText("Kontoeinstellungen:");
			labelEmailAdresse.setText("E-mail-Adresse");
			labelGeburtsdatum.setText("Geburtsdatum:");
			labelPasswort.setText("Passwort:");

			if (riegel == false) {
				aendern.setText("Ändern");
			} else {
				aendern.setText("Speichern");
			}

			passwortAnforderungen.setText("Passwort entspricht nicht den Anforderungen");
			acloeschen.setText("Account löschen");

		}

	}

}
