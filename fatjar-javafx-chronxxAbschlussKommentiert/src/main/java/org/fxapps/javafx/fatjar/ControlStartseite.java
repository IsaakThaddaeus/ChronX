package org.fxapps.javafx.fatjar;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
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
	private Label tag01sk;

	@FXML
	private Label tag02s;

	@FXML
	private Label tag03s;

	@FXML
	private Label tag04s;

	@FXML
	private Label tag05s;

	@FXML
	private Label tag06s;

	@FXML
	private Label tag11s;

	@FXML
	private Label tag12s;

	@FXML
	private Label tag13s;

	@FXML
	private Label tag14s;

	@FXML
	private Label tag15s;

	@FXML
	private Label tag16s;

	@FXML
	private Label tag21s;

	@FXML
	private Label tag22s;

	@FXML
	private Label tag23s;

	@FXML
	private Label tag24s;

	@FXML
	private Label tag25s;

	@FXML
	private Label tag26s;

	@FXML
	private Label tag31s;

	@FXML
	private Label tag32s;

	@FXML
	private Label tag33s;

	@FXML
	private Label tag34s;

	@FXML
	private Label tag35s;

	@FXML
	private Label tag36s;

	@FXML
	private Label tag41s;

	@FXML
	private Label tag42s;

	@FXML
	private Label tag43s;

	@FXML
	private Label tag44s;

	@FXML
	private Label tag45s;

	@FXML
	private Label tag46s;

	@FXML
	private Label tag51s;

	@FXML
	private Label tag52s;

	@FXML
	private Label tag53s;

	@FXML
	private Label tag54s;

	@FXML
	private Label tag55s;

	@FXML
	private Label tag56s;

	@FXML
	private Label tag61s;

	@FXML
	private Label tag62s;

	@FXML
	private Label tag63s;

	@FXML
	private Label tag64s;

	@FXML
	private Label tag65s;

	@FXML
	private Label tag66s;

	@FXML
	private Label monat;
	@FXML
	private Label aktuellerTag;
	@FXML
	private Label aktuellerTagString;
	@FXML
	private Label labelAbmelden;
	@FXML
	private Label labelAbwesenheit;
	@FXML
	private Label labelEinstellungen;
	@FXML
	private Label labelStunden;
	@FXML
	private Label labelUebersicht;
	@FXML
	private Label labelZeiterfassung;
	@FXML
	private Label labelZeitkonto;

	public Label[][] arrayLabel2;

	@FXML
	private Circle gruen;
	@FXML
	private Circle gelb;
	@FXML
	private Circle rot;

	double warngrenzePerson;
	double gleitzeitPerson;
	public static int ampel;
	
	/* Diese Methode wird ausgefuehrt wenn die Seite aufgerufen wird
	   Es wird URL und ResourceBundle übergeben. Diese Klassen werden importiert
	   Es gibt keinen Rückgabewert
	*/

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {

		try {

			Person.gleitzeit = Math
					.round(Person.getZeitRechner().gibGleitzeitGesamt(Person.getAktuellEingeloggterArbeiter()) * 100.0)
					/ 100.0;

		}

		catch (Exception e) {
			Person.gleitzeit = Person.getAktuellEingeloggterArbeiter().aktuelleGleitzeit;
		}
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

		// System.out.println(aktuellerTagString.getText());

		// System.out.println(tag01sk.getText());

		arrayLabel2 = new Label[7][6];
		arrayLabel2[0][0] = tag01sk;

		// System.out.println(arrayLabel2[0][0].getText());
		arrayLabel2[1][0] = tag11s;
		arrayLabel2[2][0] = tag21s;
		arrayLabel2[3][0] = tag31s;
		arrayLabel2[4][0] = tag41s;
		arrayLabel2[5][0] = tag51s;
		arrayLabel2[6][0] = tag61s;
		arrayLabel2[0][1] = tag02s;
		arrayLabel2[1][1] = tag12s;
		arrayLabel2[2][1] = tag22s;
		arrayLabel2[3][1] = tag32s;
		arrayLabel2[4][1] = tag42s;
		arrayLabel2[5][1] = tag52s;
		arrayLabel2[6][1] = tag62s;
		arrayLabel2[0][2] = tag03s;
		arrayLabel2[1][2] = tag13s;
		arrayLabel2[2][2] = tag23s;
		arrayLabel2[3][2] = tag33s;
		arrayLabel2[4][2] = tag43s;
		arrayLabel2[5][2] = tag53s;
		arrayLabel2[6][2] = tag63s;
		arrayLabel2[0][3] = tag04s;
		arrayLabel2[1][3] = tag14s;
		arrayLabel2[2][3] = tag24s;
		arrayLabel2[3][3] = tag34s;
		arrayLabel2[4][3] = tag44s;
		arrayLabel2[5][3] = tag54s;
		arrayLabel2[6][3] = tag64s;
		arrayLabel2[0][4] = tag05s;
		arrayLabel2[1][4] = tag15s;
		arrayLabel2[2][4] = tag25s;
		arrayLabel2[3][4] = tag35s;
		arrayLabel2[4][4] = tag45s;
		arrayLabel2[5][4] = tag55s;
		arrayLabel2[6][4] = tag65s;
		arrayLabel2[0][5] = tag06s;
		arrayLabel2[1][5] = tag16s;
		arrayLabel2[2][5] = tag26s;
		arrayLabel2[3][5] = tag36s;
		arrayLabel2[4][5] = tag46s;
		arrayLabel2[5][5] = tag56s;
		arrayLabel2[6][5] = tag66s;

		LocalDate date = LocalDate.now().withDayOfMonth(1);
		int ersterTagDesMonats = date.getDayOfWeek().getValue();
		
		LocalDate date2 = LocalDate.now();
		int heutigerTag = date2.getDayOfMonth();
		int aktuellerMonat = date2.getMonthValue();
		

		
		if (Person.getAktuellEingeloggterArbeiter().sprache == false) {
			monat.setText(date2.getMonth().getDisplayName(TextStyle.FULL, Locale.GERMAN) + "");
			aktuellerTagString.setText(date2.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.GERMAN).toString());

		}

		else {

			monat.setText(date2.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH) + "");
			aktuellerTagString.setText(date2.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH).toString());

		}
		aktuellerTag.setText(heutigerTag + "");

		ersterTagDesMonats--;
		int monatstag = 1;
		int DisableBegleiter = 1;

		Calendar cal = Calendar.getInstance();
		int tageDesMonats = cal.getActualMaximum(Calendar.DAY_OF_MONTH);


		int monatDavor = 0;

		if (aktuellerMonat == 0) {
			monatDavor = 11;
		} else {
			monatDavor = aktuellerMonat - 2;
		}

		cal.clear();
		cal.set(Calendar.MONTH, monatDavor);
		int tageDesMonatsDavor = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

		List<LocalDate> abwesenheitstageMitMonat = Person.getAktuellEingeloggterArbeiter().urlaubsUndKrankheitsTage;
	

		boolean check = true;

		for (int i = 0; i < 6; i++) {
			for (int k = 0; k < 7; k++) {

				if (i == 0 && k < ersterTagDesMonats) {

					arrayLabel2[k][i].setText(tageDesMonatsDavor - (ersterTagDesMonats - (k + 1)) + "");
					arrayLabel2[k][i].setDisable(true);
					monatstag = 0;
					DisableBegleiter = 0;
				} else {

					if (monatstag > tageDesMonats) {
						monatstag = 1;
					}
					if (DisableBegleiter > tageDesMonats) {
						arrayLabel2[k][i].setDisable(true);
					}

					arrayLabel2[k][i].setText(monatstag + "");

				}

				for (int s = 0; s < abwesenheitstageMitMonat.size(); s = s + 2) {

					try {

						if (abwesenheitstageMitMonat.get(s).getMonthValue() == aktuellerMonat
								&& abwesenheitstageMitMonat.get(s + 1).getMonthValue() == aktuellerMonat
								&& DisableBegleiter <= tageDesMonats) {
							if (monatstag >= abwesenheitstageMitMonat.get(s).getDayOfMonth()
									&& monatstag <= abwesenheitstageMitMonat.get(s + 1).getDayOfMonth()) {								
								arrayLabel2[k][i].setStyle("-fx-background-color: #0c9ec6");
							}

						}

						if (abwesenheitstageMitMonat.get(s).getMonthValue() != aktuellerMonat
								&& abwesenheitstageMitMonat.get(s + 1).getMonthValue() == aktuellerMonat) {
							if (DisableBegleiter <= abwesenheitstageMitMonat.get(s + 1).getDayOfMonth()) {								
								arrayLabel2[k][i].setStyle("-fx-background-color: #0c9ec6");
							}
						}

						if (abwesenheitstageMitMonat.get(s).getMonthValue() == aktuellerMonat
								&& abwesenheitstageMitMonat.get(s + 1).getMonthValue() != aktuellerMonat) {
							if (DisableBegleiter >= abwesenheitstageMitMonat.get(s).getDayOfMonth()) {								
								arrayLabel2[k][i].setStyle("-fx-background-color: #0c9ec6");
							}
						}

					}

					catch (Exception e) {
						break;
					}
				}

				if (monatstag == heutigerTag && check == true) {
					arrayLabel2[k][i].setStyle("-fx-background-color: #dbba51");
					check = false;
				}

				monatstag++;
				DisableBegleiter++;

			}

		}

		if (Person.getAktuellEingeloggterArbeiter().sprache == true) {

			labelUebersicht.setText("Overview");
			labelEinstellungen.setText("Settings");
			labelZeiterfassung.setText("Time tracking");
			labelZeitkonto.setText("flexible time");
			labelAbwesenheit.setText("Vacation / Absence");
			labelAbmelden.setText("Sign out");
			labelStunden.setText("hours");

		}

	}
	
	/* Mit dieser Methode wird die Person abgemeldet und die Anmeldeseite wird geladen
	   Es wird ein MouseEvent uebergeben
	   Es gibt keinen Rückgabewert
	*/

	@FXML
	void abmeldenM(MouseEvent event) {

		try {
			Person.vorname = null;
			Person.nachname = null;
			Person.email = null;
			Person.passwort = null;
			EinlesenUndSpeichern.bereitseingelesen = false;
			EinlesenUndSpeichern.zuDruckendeWerte.clear();
			
			Parent root2 = FXMLLoader.load(getClass().getResource("/org/fxapps/javafx/fatjar/Anmeldung.fxml"));
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			scene = new Scene(root2);
			stage.setScene(scene);
			stage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/* Mit dieser Methode wird die Seite zur Zeiterfassung aufgerufen
	   Es wird ein MouseEvent uebergeben
	   Es gibt keinen Rückgabewert
	*/

	@FXML
	void gleitzeitClick(MouseEvent event) throws IOException {

		Parent root2 = FXMLLoader.load(getClass().getResource("/org/fxapps/javafx/fatjar/Zeiterfassung.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root2);
		stage.setScene(scene);
		stage.show();

	}
	
	/* Mit dieser Methode wird die Seite zur Zeiterfassung aufgerufen
	   Es wird ein MouseEvent uebergeben
	   Es gibt keinen Rückgabewert
	*/

	@FXML
	void einstellungenClick(MouseEvent event) throws IOException {

		Parent root2 = FXMLLoader.load(getClass().getResource("/org/fxapps/javafx/fatjar/Einstellungen.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root2);
		stage.setScene(scene);
		stage.show();

	}
	
	/* Mit dieser Methode wird die Seite zur Statistik aufgerufen
	   Es wird ein MouseEvent uebergeben
	   Es gibt keinen Rückgabewert
	*/

	@FXML
	void kalenderClick2(MouseEvent event) throws IOException {

		Parent root2 = FXMLLoader.load(getClass().getResource("/org/fxapps/javafx/fatjar/Statistik.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root2);
		stage.setScene(scene);
		stage.show();

	}
	
	/* Mit dieser Methode wird die Seite zur Urlaubserfassung aufgerufen
	   Es wird ein MouseEvent uebergeben
	   Es gibt keinen Rückgabewert
	*/
	
	@FXML
	void urlaubClick(MouseEvent event) throws IOException {

		Parent root2 = FXMLLoader.load(getClass().getResource("/org/fxapps/javafx/fatjar/Urlaub.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root2);
		stage.setScene(scene);
		stage.show();

	}
	
	/* Diese Methoden verdunkeln die Buttons und Felder wenn mann mit dem Mauszeiger in das Feld geht, beziehungsweise setzen es wieder auf die ursprüngliche Farbe wenn man wieder hinaus geht.
	   Es wird ein ActionEvent aus der FX Bibliothek übergeben
	   Es gibt keinen Rückgabewert
	*/

	@FXML
	void hervorClick(MouseEvent event) {
		abmelden.setStyle("-fx-background-color: #696969; -fx-background-radius: 10;");
	}

	@FXML
	void hintenClick(MouseEvent event) {
		abmelden.setStyle("-fx-background-color: grey; -fx-background-radius: 10;");
	}

	@FXML
	void einstellungenHervorClick(MouseEvent event) {
		einstellungen.setStyle("-fx-background-color: #696969; -fx-background-radius: 10;");
	}

	@FXML
	void einstellungenHintenClick(MouseEvent event) {
		einstellungen.setStyle("-fx-background-color: grey; -fx-background-radius: 10;");
	}

	@FXML
	void gleitzeitHervorClick(MouseEvent event) {
		gleitzeit.setStyle("-fx-background-color: #696969; -fx-background-radius: 10;");
	}

	@FXML
	void gleitzeitHintenClick(MouseEvent event) {
		gleitzeit.setStyle("-fx-background-color: grey; -fx-background-radius: 10;");
	}

	@FXML
	void kalenderHervorClick(MouseEvent event) {
		kalender.setStyle("-fx-background-color: #696969; -fx-background-radius: 10;");
	}

	@FXML
	void kalenderHintenClick(MouseEvent event) {
		kalender.setStyle("-fx-background-color: grey; -fx-background-radius: 10;");
	}

	@FXML
	void urlaubHervorClick(MouseEvent event) {
		urlaub.setStyle("-fx-background-color: #696969; -fx-background-radius: 10;");
	}

	@FXML
	void urlaubHintenClick(MouseEvent event) {
		urlaub.setStyle("-fx-background-color: grey; -fx-background-radius: 10;");
	}

}
