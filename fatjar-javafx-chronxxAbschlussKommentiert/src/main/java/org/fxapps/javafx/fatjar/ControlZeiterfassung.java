package org.fxapps.javafx.fatjar;

//alt
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.Node;

public class ControlZeiterfassung implements Initializable {

	@FXML
	private AnchorPane paneWarnung;

	@FXML
	private Button start;
	@FXML
	private Button bearbeiten;
	@FXML
	private Button zurueck;
	@FXML
	private Button hinzufuegen;
	@FXML
	private Button loeschen;
	@FXML
	private Button verstanden;

	@FXML
	private AnchorPane uebersicht;
	@FXML
	private Label tagesstunden;
	@FXML
	private Label zeitlich;
	@FXML
	private Label gehZeit;
	@FXML
	private Label kommZeit;
	@FXML
	private Label stundenL;
	@FXML
	public Label aktuelleGleitzeit;
	@FXML
	private Label wochenstunden;
	@FXML
	private Label labelWarnung;
	@FXML
	private Label labelAktuellerGleitzeitstand;
	@FXML
	private Label labelGegangen;
	@FXML
	private Label labelGekommen;
	@FXML
	private Label labelStunden;
	@FXML
	private Label labelStundenT3;
	@FXML
	private Label labelStundenZ2;
	@FXML
	private Label labelTag;
	@FXML
	private Label labelTagesübersicht;
	@FXML
	private Label labelUebersicht;
	@FXML
	private Label labelWochenstunden;
	@FXML
	private Label labelZeiterfassung;

	@FXML
	private TableView<ZeiterfassungEintrag> tabelle;
	@FXML
	private TableColumn<ZeiterfassungEintrag, String> gegangen;
	@FXML
	private TableColumn<ZeiterfassungEintrag, String> kommen;
	@FXML
	private TableColumn<ZeiterfassungEintrag, Double> stunden;

	@FXML
	public DatePicker kalenderPicker2;

	@FXML
	private Circle gelb2;
	@FXML
	private Circle gruen2;
	@FXML
	private Circle rot2;

	private Stage stage;
	private Scene scene;

	boolean kg = false;
	String uhrzeitKommen;
	double gesamtZeit = 0;
	
	/* Diese Methode wird ausgefuehrt wenn die Seite aufgerufen wird
	   Es wird URL und ResourceBundle übergeben. Diese Klassen werden importiert
	   Es gibt keinen Rückgabewert
	*/


	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		kommen.setCellValueFactory(new PropertyValueFactory<ZeiterfassungEintrag, String>("kommen"));
		gegangen.setCellValueFactory(new PropertyValueFactory<ZeiterfassungEintrag, String>("gegangen"));
		stunden.setCellValueFactory(new PropertyValueFactory<ZeiterfassungEintrag, Double>("stunden"));

		aktuelleGleitzeit.setText(Double.toString(Person.gleitzeit));

		if (ControlStartseite.ampel == 1) {

			gruen2.setStyle("-fx-fill: #139024");
			gelb2.setStyle("-fx-fill: #000000");
			rot2.setStyle("-fx-fill: #000000");
		}

		else if (ControlStartseite.ampel == 2) {

			gruen2.setStyle("-fx-fill: #000000");
			gelb2.setStyle("-fx-fill: #dbba51");
			rot2.setStyle("-fx-fill: #000000");
		}

		else {

			gruen2.setStyle("-fx-fill: #000000");
			gelb2.setStyle("-fx-fill: #000000");
			rot2.setStyle("-fx-fill: #c32828");

		}

		kalenderPicker2.setValue(LocalDate.now());

		LocalDateTime startzeit = Person.getZeitRechner().aktuellAmZeitErfassen(Person.getAktuellEingeloggterArbeiter());

		if (startzeit != null) {

			kg = true;
			kommZeit.setText(Person.getZeitRechner().zeitFuerTabellenAufbereiter(startzeit));
			start.setStyle("-fx-background-color: grey");
			start.setText("Zeiterfassung beenden");
			uhrzeitKommen = Person.getZeitRechner().zeitFuerTabellenAufbereiter(startzeit);

		}

		wochenstunden.setText((int) Person.wochenstunden + "");

		ladeTabelle(Person.getZeitRechner().eintraegeFuerBeliebigenTagAufrufen(LocalDate.now(),
				Person.getAktuellEingeloggterArbeiter()));

		spracheAendernZ(Person.getAktuellEingeloggterArbeiter().sprache);

	}
	
	/* Mit dieser Methode wird die Startseite aufgerufen
	   Es wird ein ActionEvent uebergeben
	   Es gibt keinen Rückgabewert
	*/

	
	@FXML
	void zurueckClick(ActionEvent event) throws IOException {

		Parent root2 = FXMLLoader.load(getClass().getResource("/org/fxapps/javafx/fatjar/Startseite.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root2);
		stage.setScene(scene);
		stage.show();

	}
	
	/* Mit dieser Methode wird Zeiterfassung gestartet beziehungsweise beu erneutem Druecken des Buttons beendet
	   Es wird ein ActionEvent uebergeben
	   Es gibt keinen Rückgabewert
	*/
	
	@FXML
	void startClick(ActionEvent event) {
		
		LocalDateTime date = LocalDateTime.now();
		LocalDate dateD = LocalDate.now();
		String datum = date.withSecond(0).withNano(0).toString();
		String[] uhrzeit = datum.split("T");

		if (kg == false) {
			kg = true;
			start.setStyle("-fx-background-color: grey");
			start.setText("Zeiterfassung beenden");
			gehZeit.setText("");
			stundenL.setText("");
			kommZeit.setText(uhrzeit[1]);
			uhrzeitKommen = uhrzeit[1];
			
			try  {
			
			List <LocalDateTime> zeiten = Person.getZeitRechner().eintraegeFuerBeliebigenTagAufrufen(dateD, Person.getAktuellEingeloggterArbeiter());
			
			if (zeiten.size() == 1) {
				
				zeiten.clear();
				zeiten.add(date);
				Person.getZeitRechner().gegebeneZeitenAnpasser(dateD, zeiten, Person.getAktuellEingeloggterArbeiter());
				
			}
			
			}
			catch (Exception e) {}
			
			
			String rueckmeldung12Stunden = Person.getZeitRechner().wurde12StdPauseGeamcht(Person.getAktuellEingeloggterArbeiter(), date);


			Person.getAktuellEingeloggterArbeiter().aktuelleGleitzeit = Person.gleitzeit;
			Person.getZeitRechner().zeiteintragFuerAktuellenTagHinzufuegen(Person.getAktuellEingeloggterArbeiter());
			EinlesenUndSpeichern.abspeichernVonAenderungen(Person.getAktuellEingeloggterArbeiter());

		}

		else {
			kg = false;

			ZeiterfassungEintrag eintrag = new ZeiterfassungEintrag(uhrzeitKommen, uhrzeit[1], 0.0);
			double stundenD = eintrag.getStunden();

			if (LocalDate.now().isEqual(kalenderPicker2.getValue())) {

				ObservableList<ZeiterfassungEintrag> customers = tabelle.getItems();
				customers.add(eintrag);
				tabelle.setItems(customers);

			}

			gehZeit.setText(uhrzeit[1]);
			start.setText("Zeiterfassung starten");
			start.setStyle("-fx-background-color:  #dbba51");
			stundenL.setText(stundenD + "");
			gesamtZeit = gesamtZeit + stundenD;
			tagesstunden.setText(gesamtZeit + "");

			Person.getZeitRechner().zeiteintragFuerAktuellenTagHinzufuegen(Person.getAktuellEingeloggterArbeiter());
			EinlesenUndSpeichern.abspeichernVonAenderungen(Person.getAktuellEingeloggterArbeiter());

			gleitzeitAktualisieren();
			warnung(LocalDate.now());

		}

		spracheAendernZ(Person.getAktuellEingeloggterArbeiter().sprache);
	}
	
	/* Mit dieser Methode werden Verstoesse gegen das Zeitgesetzt ausgegeben
	   Es wird der im DatePicker ausgewaehlte Tag uebergeben
	   Es gibt keinen Rückgabewert
	*/

	public void warnung(LocalDate l) {

		String rueckmeldung = Person.getZeitRechner().wurdePauseEingehalten(Person.getAktuellEingeloggterArbeiter(), l);

		if (rueckmeldung.equals("d") == false) {

			if (Person.getAktuellEingeloggterArbeiter().sprache == false) {

				switch (rueckmeldung) {

				case "b":
					rueckmeldung = "Sie haben ihre maximale Arbeitszeit überschritten";
					break;

				case "a":
					rueckmeldung = "Sie haben ihre maximale Zeit einer Zeitperiode überschritten";
					break;

				case "c":
					rueckmeldung = "Sie haben Ihre Mindestpausenzeit nicht eingehalten";
					break;

				case "e":
					rueckmeldung = "Es sind keine Zeiten für diesen Tag erfasst";
					break;
					
				}
			} else {

				switch (rueckmeldung) {

				case "b":
					rueckmeldung = "You have exceeded your maximum working hours";
					break;

				case "a":
					rueckmeldung = "You have exceeded your maximum time of a time period";
					break;

				case "c":
					rueckmeldung = "You have not met your minimum break time";
					break;

				case "e":
					rueckmeldung = "There are no times recorded for this day";
					break;
				}

			}
			
			labelWarnung.setText(rueckmeldung);
			paneWarnung.setVisible(true);

		}	

	}
	
	/* Mit dieser Methode wird das Popup Fenster mit der Warnung wieder unsichtbar gemacht
	   Es wird ein ActionEvent uebergeben
	   Es gibt keinen Rückgabewert
	*/

	@FXML
	void verstandenClick(ActionEvent event) {
		paneWarnung.setVisible(false);
	}
	
	/* Mit dieser Methode wird ein String in ein LocalDate umgewandelt
	   Es wird nichts uebergeben
	   Es gibt ein LocalDate als Rueckgabewert
	*/

	public static final LocalDate NOW_LOCAL_DATE() {
		String date = new SimpleDateFormat("dd-MM-  yyyy").format(Calendar.getInstance().getTime());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate localDate = LocalDate.parse(date, formatter);
		return localDate;
	}

	boolean ba = false;
	
	/* Mit dieser Methode wird das Bearbeiten der Tabelle ermoeglicht und bei erneutem Clicken wieder unmoeglich gemacht
	   Es wird ein ActionEvent uebergeben
	   Es gibt keinen Rueckgabewert
	*/

	@FXML
	void bearbeitenClick(ActionEvent event) {
		

		if (ba == false) {
			editableCols();
			ba = true;
			bearbeiten.setText("Speichern");
			bearbeiten.setStyle("-fx-background-color: grey");

			hinzufuegen.setVisible(true);
			loeschen.setVisible(true);

			tabelle.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
				if (newSelection != null) {
					loeschen.setDisable(false);
				}
			});
		}

		else {
			tabelle.setEditable(false);
			ba = false;
			bearbeiten.setText("Bearbeiten");
			bearbeiten.setStyle("-fx-background-color: #dbba51");

			tabelle.getSelectionModel().clearSelection();
			hinzufuegen.setVisible(false);
			loeschen.setVisible(false);
			loeschen.setDisable(true);

			ZeiterfassungEintrag eintrag2 = new ZeiterfassungEintrag();
			LocalDate date = kalenderPicker2.getValue();
			List<LocalDateTime> arrList = new ArrayList<LocalDateTime>();

			gesamtZeit = 0;

			for (int i = 0; i < tabelle.getItems().size(); i++) {

				eintrag2 = tabelle.getItems().get(i);
				double einzelStunden = eintrag2.getStunden();
				String[] kommenS = eintrag2.kommen.split(":");
				String[] gegangenS = eintrag2.gegangen.split(":");

				arrList.add(date.atTime(Integer.parseInt(kommenS[0]), Integer.parseInt(kommenS[1])).withSecond(0)
						.withNano(0));
				arrList.add(date.atTime(Integer.parseInt(gegangenS[0]), Integer.parseInt(gegangenS[1])).withSecond(0)
						.withNano(0));

				gesamtZeit = gesamtZeit + einzelStunden;
			}

			tagesstunden.setText(gesamtZeit + "");

			ZeitRechner zr = new ZeitRechner();
			

			zr.gegebeneZeitenAnpasser(date, arrList, Person.getAktuellEingeloggterArbeiter());

			gleitzeitAktualisieren();
			warnung(date);
		}

		spracheAendernZ(Person.getAktuellEingeloggterArbeiter().sprache);

	}
	
	/* Mit dieser Methode wird das bearbeiten einer einzelnen Zelle der Tabelle ermoeglicht
	   Es wird kein Wer uebergeben
	   Es gibt keinen Rueckgabewert
	*/

	private void editableCols() {
		kommen.setCellFactory(TextFieldTableCell.forTableColumn());
		kommen.setOnEditCommit(
				e -> e.getTableView().getItems().get(e.getTablePosition().getRow()).setKommen(e.getNewValue()));

		gegangen.setCellFactory(TextFieldTableCell.forTableColumn());
		gegangen.setOnEditCommit(
				e -> e.getTableView().getItems().get(e.getTablePosition().getRow()).setGegangen(e.getNewValue()));

		tabelle.setEditable(true);
	}
	
	/* Mit dieser Methode wird das ausgewaehlte Datum eingelesen und die Zeiten fuer diesen Tag in die Tabelle geladen
	   Es wird ein ActionEvent uebergeben
	   Es gibt keinen Rueckgabewert
	*/

	@FXML
	void KalenderPicker2Click(ActionEvent event) {

		LocalDate datum = kalenderPicker2.getValue();

		ladeTabelle(Person.getZeitRechner().eintraegeFuerBeliebigenTagAufrufen(datum,
				Person.getAktuellEingeloggterArbeiter()));

	}
	
	/* Mit dieser Methode werden neue Daten in die Tabelle geladen
	   Es wird eine ArrayList LocalDateTime uebergeben
	   Es gibt keinen Rueckgabewert
	*/


	public void ladeTabelle(List<LocalDateTime> l) {

		tabelle.getItems().clear();

		gesamtZeit = 0;
		System.out.println("Das ist die ArrayList: " + l);

		try {

			for (int i = 0; i < l.size(); i = i + 2) {

				try {

					String zk = Person.getZeitRechner().zeitFuerTabellenAufbereiter(l.get(i));
					String zg = Person.getZeitRechner().zeitFuerTabellenAufbereiter(l.get(i + 1));

					ZeiterfassungEintrag eintrag4 = new ZeiterfassungEintrag(zk, zg, 0.0);
					double stundenD = eintrag4.getStunden();
					ObservableList<ZeiterfassungEintrag> customers = tabelle.getItems();
					customers.add(eintrag4);
					tabelle.setItems(customers);

					gesamtZeit = gesamtZeit + eintrag4.stunden;

				} catch (IndexOutOfBoundsException e) {
					break;

				}
			}

			if (l.size() % 2 == 0) {
				gleitzeitAktualisieren();
			}

			tagesstunden.setText(gesamtZeit + "");
		}

		catch (Exception e) {
		}

	}
	
	/* Mit dieser Methode wird die Gleitzeit aktualisiert
	   Es wird nichts uebergeben
	   Es gibt keinen Rueckgabewert
	*/

	public void gleitzeitAktualisieren() {

		Person.gleitzeit = Math.round(
				Person.getZeitRechner().gibGleitzeitGesamt(Person.getAktuellEingeloggterArbeiter()) * 100.0) / 100.0;
		aktuelleGleitzeit.setText(Person.gleitzeit + "");

	}
	
	/* Mit dieser Methode wird die Sprache geandert
	   Es wird die aktuelle Sprache als boolean Wert uebergeben
	   Es gibt keinen Rueckgabewert
	*/

	public void spracheAendernZ(boolean s) {

		if (s == true) {
			labelAktuellerGleitzeitstand.setText("Current flextime status");
			labelUebersicht.setText("Overview");
			labelStunden.setText("hours");
			labelWochenstunden.setText("Weekly hours");
			labelZeiterfassung.setText("time tracking");
			labelGekommen.setText("Start timte:");
			labelGegangen.setText("End time:");
			labelStundenZ2.setText("hours:");

			if (kg == false) {
				start.setText("Start time tracking");
			} else {
				start.setText("Stop time tracking");
			}

			labelTagesübersicht.setText("Daily overview");
			labelTag.setText("Day:");
			labelStundenT3.setText("hours:");
			hinzufuegen.setText("Add time");
			loeschen.setText("Delete");
			kommen.setText("Start time");
			gegangen.setText("End time");
			stunden.setText("hours");

			if (ba == false) {
				bearbeiten.setText("Edit");
			} else {
				bearbeiten.setText("Save");
			}

			verstanden.setText("Okay");
		}

	}
	
	/* Mit dieser Methode wird eine Zeile ohne Zeiten in die Tabelle hinzugefuegt
	   Es wird ein ActionEvent uebergeben
	   Es gibt keinen Rueckgabewert
	*/

	@FXML
	void hinzufuegenClick(ActionEvent event) {

		ZeiterfassungEintrag eintrag3 = new ZeiterfassungEintrag("00:00", "00:00", 0.0);
		double stundenD = eintrag3.getStunden();
		ObservableList<ZeiterfassungEintrag> customers = tabelle.getItems();
		customers.add(eintrag3);
		tabelle.setItems(customers);

	}
	
	/* Mit dieser Methode wird die ausgewaehlte Zeile aus der Tabelle geloescht
	   Es wird ein ActionEvent uebergeben
	   Es gibt keinen Rueckgabewert
	*/
	
	@FXML
	void loeschenClick(ActionEvent event) {
		tabelle.getItems().removeAll(tabelle.getSelectionModel().getSelectedItems());
	}
	
	/* Diese Methoden verdunkeln die Buttons und Felder wenn mann mit dem Mauszeiger in das Feld geht, beziehungsweise setzen es wieder auf die ursprüngliche Farbe wenn man wieder hinaus geht.
	   Es wird ein ActionEvent aus der FX Bibliothek übergeben
	   Es gibt keinen Rückgabewert
	*/
	
	@FXML
	void verstandenHervorClick(MouseEvent event) {
		verstanden.setStyle("-fx-background-color: #696969");
	}
	@FXML
	void verstandenHintenClick(MouseEvent event) {
		verstanden.setStyle("-fx-background-color: grey");
	}

	@FXML
	void hinzufuegenHervorClick(MouseEvent event) {
		hinzufuegen.setStyle("-fx-background-color: #034d07");
	}

	@FXML
	void hinzufuegenHintenClick(MouseEvent event) {
		hinzufuegen.setStyle("-fx-background-color:  #139024");
	}

	@FXML
	void loeschenHervorClick(MouseEvent event) {
		loeschen.setStyle("-fx-background-color: #800000");
	}

	@FXML
	void loeschenHintenClick(MouseEvent event) {
		loeschen.setStyle("-fx-background-color:  #c32828");
	}
	
	@FXML
	void bearbeitenHervorClick(MouseEvent event) {

		if (ba == false) {
			bearbeiten.setStyle("-fx-background-color: #a18634");
		} else {
			bearbeiten.setStyle("-fx-background-color: #696969");
		}
	}

	@FXML
	void bearbeitenHintenClick(MouseEvent event) {
		if (ba == false) {
			bearbeiten.setStyle("-fx-background-color: #dbba51");
		} else {
			bearbeiten.setStyle("-fx-background-color: grey");
		}

	}
	
	@FXML
	void startHervorClick(MouseEvent event) {

		if (kg == true) {
			start.setStyle("-fx-background-color:  #696969");
		} else {
			start.setStyle("-fx-background-color:  #a18634");
		}

	}

	@FXML
	void startHintenClick(MouseEvent event) {

		if (kg == true) {
			start.setStyle("-fx-background-color:  grey");
		} else {
			start.setStyle("-fx-background-color:  #dbba51");
		}
	}

	@FXML
	void zurueckHervorClick(MouseEvent event) {
		zurueck.setStyle("-fx-background-color:  #696969");
	}

	@FXML
	void zurueckHintenClick(MouseEvent event) {
		zurueck.setStyle("-fx-background-color: grey");

	}

}
