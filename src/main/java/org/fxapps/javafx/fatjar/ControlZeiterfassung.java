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
import java.time.format.DateTimeFormatter;
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

	@FXML
	void zurueckClick(ActionEvent event) throws IOException {

		Parent root2 = FXMLLoader.load(getClass().getResource("/org/fxapps/javafx/fatjar/Startseite.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root2);
		stage.setScene(scene);
		stage.show();

	}
	
	boolean kg = false;
	String uhrzeitKommen;
	double gesamtZeit = 0;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.fxml.Initializable#initialize(java.net.URL,
	 * java.util.ResourceBundle)
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
		
		ladeTabelle(Person.getZeitRechner().eintraegeFuerBeliebigenTagAufrufen(LocalDate.now(), Person.getAktuellEingeloggterArbeiter()));
		
		
	}

	@FXML
	void startClick(ActionEvent event) {

		String datum = LocalDateTime.now().withSecond(0).withNano(0).toString();
		String[] uhrzeit = datum.split("T");

		if (kg == false) {
			kg = true;
			start.setStyle("-fx-background-color: grey");
			start.setText("Zeiterfassung beenden");
			gehZeit.setText("");
			stundenL.setText("");
			kommZeit.setText(uhrzeit[1]);
			uhrzeitKommen = uhrzeit[1];
			
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
			stundenL.setText(stundenD+"");
			
			gesamtZeit = gesamtZeit + stundenD;
			tagesstunden.setText(gesamtZeit+"");
			
			Person.getZeitRechner().zeiteintragFuerAktuellenTagHinzufuegen(Person.getAktuellEingeloggterArbeiter());
			EinlesenUndSpeichern.abspeichernVonAenderungen(Person.getAktuellEingeloggterArbeiter());
			
			gleitzeitAktualisieren();
			
			/*
			 *  Hinzufügen Jan du Eumel: Zeitverstoß Rückgabe als String
			 */


		}
	}

	public static final LocalDate NOW_LOCAL_DATE() {
		String date = new SimpleDateFormat("dd-MM-  yyyy").format(Calendar.getInstance().getTime());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate localDate = LocalDate.parse(date, formatter);
		return localDate;
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

	boolean ba = false;

	@FXML
	void bearbeitenClick(ActionEvent event) {
		System.out.println("TEST AWDAWDAWDAWDWADAWDAWDAWD");

		if (ba == false) {
			editableCols();
			ba = true;
			bearbeiten.setText("Fertig");
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

				
				arrList.add(date.atTime(Integer.parseInt(kommenS[0]),Integer.parseInt(kommenS[1]))
						.withSecond(0).withNano(0));
				arrList.add(date.atTime(Integer.parseInt(gegangenS[0]),Integer.parseInt(gegangenS[1]))
						.withSecond(0).withNano(0));
				
				gesamtZeit = gesamtZeit + einzelStunden;
			}
			 System.out.println(arrList +" Hallo " + arrList.get(0));
		     // System.out.println(gesamtZeit);
		      
		      tagesstunden.setText(gesamtZeit+"");
		      
	
			
			ZeitRechner zr = new ZeitRechner();
			System.out.println("ER GEHT HIER HIN");

			zr.gegebeneZeitenAnpasser(arrList, Person.getAktuellEingeloggterArbeiter());
			
			gleitzeitAktualisieren();
			
			/*
			 *  Hinzufügen Jan du Eumel: Zeitverstoß Rückgabe als String
			 */

		}

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

	private void editableCols() {
		kommen.setCellFactory(TextFieldTableCell.forTableColumn());
		kommen.setOnEditCommit(
				e -> e.getTableView().getItems().get(e.getTablePosition().getRow()).setKommen(e.getNewValue()));

		gegangen.setCellFactory(TextFieldTableCell.forTableColumn());
		gegangen.setOnEditCommit(
				e -> e.getTableView().getItems().get(e.getTablePosition().getRow()).setGegangen(e.getNewValue()));

		tabelle.setEditable(true);
	}
	@FXML
	void KalenderPicker2Click(ActionEvent event) {
		//hier hinzufuegen

		LocalDate datum = kalenderPicker2.getValue();
		//Person.hdatum = datum;
		System.out.println("aktueller Tag " + datum);
		
		 ladeTabelle(Person.getZeitRechner().eintraegeFuerBeliebigenTagAufrufen(datum, Person.getAktuellEingeloggterArbeiter()));
		 
		

	}
	
	public void ladeTabelle(List<LocalDateTime> l) {
		
			System.out.println("Jetzt geht er in ladeTabelle und ich überprüfe");
			
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
			
			}
			catch (IndexOutOfBoundsException e) {
				System.out.println("Hier wurde die Schleife abgebrochen");
				break;
				
			}
		}
			
			System.out.println("Hier befinden wir uns außerhalb der Schleife");
			if (l.size()%2 == 0) {gleitzeitAktualisieren();}
		
			tagesstunden.setText(gesamtZeit+"");
			}
			
			catch (Exception e) {System.out.println("Die Schleife wurde nicht einmal ausgeführt");}
			
			
		
		}
	
	public void gleitzeitAktualisieren () {
		
		Person.gleitzeit = Math.round(Person.getZeitRechner().gibGleitzeitGesamt(Person.getAktuellEingeloggterArbeiter())* 100.0) / 100.0;
		aktuelleGleitzeit.setText(Person.gleitzeit+"");
		

		
	}
		
	@FXML
	void hinzufuegenClick(ActionEvent event) {

		ZeiterfassungEintrag eintrag3 = new ZeiterfassungEintrag("00:00", "00:00", 0.0);
		double stundenD = eintrag3.getStunden();
		ObservableList<ZeiterfassungEintrag> customers = tabelle.getItems();
		customers.add(eintrag3);
		tabelle.setItems(customers);

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
	void loeschenClick(ActionEvent event) {
		tabelle.getItems().removeAll(tabelle.getSelectionModel().getSelectedItems());
	}

	@FXML
	void loeschenHervorClick(MouseEvent event) {
		loeschen.setStyle("-fx-background-color: #800000");
	}

	@FXML
	void loeschenHintenClick(MouseEvent event) {
		loeschen.setStyle("-fx-background-color:  #c32828");
	}

}
