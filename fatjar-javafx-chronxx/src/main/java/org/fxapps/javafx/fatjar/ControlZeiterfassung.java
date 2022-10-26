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
	private TableView<Eintrag> tabelle;
	@FXML
	private TableColumn<Eintrag, String> gegangen;
	@FXML
	private TableColumn<Eintrag, String> kommen;
	@FXML
	private TableColumn<Eintrag, Double> stunden;

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.fxml.Initializable#initialize(java.net.URL,
	 * java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		kommen.setCellValueFactory(new PropertyValueFactory<Eintrag, String>("kommen"));
		gegangen.setCellValueFactory(new PropertyValueFactory<Eintrag, String>("gegangen"));
		stunden.setCellValueFactory(new PropertyValueFactory<Eintrag, Double>("stunden"));

		aktuelleGleitzeit.setText("+ " + Double.toString(Person.gleitzeit));

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
		
		List <LocalDateTime> listeAktuellerTag = Person.getZeitRechner().eintraegeFuerBeliebigenTagAufrufen(LocalDate.now(), Person.getAktuellEingeloggterArbeiter());
		
		double gesamtZeit2 = 0;
		
		for (int i = 0; i < listeAktuellerTag.size(); i = i + 2) {
			
			String zk = Person.getZeitRechner().zeitFuerTabellenAufbereiter(listeAktuellerTag.get(i));
			String zg = Person.getZeitRechner().zeitFuerTabellenAufbereiter(listeAktuellerTag.get(i + 1));
			
			Eintrag eintrag4 = new Eintrag(zk, zg, 0.0);
			double stundenD = eintrag4.getStunden();
			ObservableList<Eintrag> customers = tabelle.getItems();
			customers.add(eintrag4);
			tabelle.setItems(customers);
			gesamtZeit2 = gesamtZeit2 + eintrag4.stunden;
		}
		
		tagesstunden.setText(gesamtZeit2+"");
		
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
			
			Person.getZeitRechner().zeiteintragFuerAktuellenTagHinzufuegen(Person.getAktuellEingeloggterArbeiter());
			EinlesenUndSpeichern.abspeichernVonAenderungen(Person.getAktuellEingeloggterArbeiter());
			
			} 
		
		else {
			kg = false;

			Eintrag eintrag = new Eintrag(uhrzeitKommen, uhrzeit[1], 0.0);
			double stundenD = eintrag.getStunden();
			ObservableList<Eintrag> customers = tabelle.getItems();
			customers.add(eintrag);
			tabelle.setItems(customers);

			gehZeit.setText(uhrzeit[1]);

			start.setText("Zeiterfassung starten");
			start.setStyle("-fx-background-color:  #dbba51");
			stundenL.setText(Double.toString(stundenD));
			
			Person.getZeitRechner().zeiteintragFuerAktuellenTagHinzufuegen(Person.getAktuellEingeloggterArbeiter());
			EinlesenUndSpeichern.abspeichernVonAenderungen(Person.getAktuellEingeloggterArbeiter());
			
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

			Eintrag eintrag2 = new Eintrag();
			LocalDateTime date = LocalDateTime.now();
			List<LocalDateTime> arrList = new ArrayList<LocalDateTime>();

			 double gesamtStunden = 0;
			
			for (int i = 0; i < tabelle.getItems().size(); i++) {
			  
				eintrag2 = tabelle.getItems().get(i);
				double einzelStunden = eintrag2.getStunden();
				String[] kommenS = eintrag2.kommen.split(":");
				String[] gegangenS = eintrag2.gegangen.split(":");

				// arrList.add(new ArrayList<>());
				arrList.add(date.withHour(Integer.parseInt(kommenS[0])).withMinute(Integer.parseInt(kommenS[1]))
						.withSecond(0).withNano(0));
				arrList.add(date.withHour(Integer.parseInt(gegangenS[0])).withMinute(Integer.parseInt(gegangenS[1]))
						.withSecond(0).withNano(0));
				// arrList.get(i).add(eintrag2.stunden);
				gesamtStunden = gesamtStunden + einzelStunden;
			}
			 System.out.println(arrList);
		      System.out.println(gesamtStunden);
		      
		      tagesstunden.setText(gesamtStunden+"");
		      
	
			
			ZeitRechner zr = new ZeitRechner();
			System.out.println("ER GEHT HIER HIN");

			zr.gegebeneZeitenAnpasser(arrList, Person.getAktuellEingeloggterArbeiter());
			
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
		
		 List<LocalDateTime> a = Person.getZeitRechner().eintraegeFuerBeliebigenTagAufrufen(datum, Person.getAktuellEingeloggterArbeiter());
		
		System.out.println(a);
		 
	
		 
			for (int i = 0; i < a.size(); i++) {
			 
			 System.out.println("Liste aktueller Tag" + a.get(i));
			 
			 
			 	
		 }

	}
	

	@FXML
	void hinzufuegenClick(ActionEvent event) {

		Eintrag eintrag3 = new Eintrag("00:00", "00:00", 0.0);
		double stundenD = eintrag3.getStunden();
		ObservableList<Eintrag> customers = tabelle.getItems();
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
