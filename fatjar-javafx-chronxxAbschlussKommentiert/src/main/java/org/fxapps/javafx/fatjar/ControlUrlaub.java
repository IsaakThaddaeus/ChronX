package org.fxapps.javafx.fatjar;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public class ControlUrlaub implements Initializable {

	@FXML
	private Button bearbeiten2;
	@FXML
	private Button hinzufuegen;
	@FXML
	private Button loeschen;
	@FXML
	private Button zurueckU;

	@FXML
	private Label abwesenheitstagegesamt;
	@FXML
	private Label datumLiegtInVergangenheit;
	@FXML
	private Label labelEintraegeBearbeiten;
	@FXML
	private Label labelEnde;
	@FXML
	private Label labelGebuchteTage;
	@FXML
	private Label labelUrlaubAbwesenheitH;

	@FXML
	private DatePicker endedatum;
	@FXML
	private DatePicker startdatum;

	@FXML
	private TableView<UrlaubEintrag> urlaub;
	@FXML
	private TableColumn<UrlaubEintrag, String> start;
	@FXML
	private TableColumn<UrlaubEintrag, String> ende;
	@FXML
	private TableColumn<UrlaubEintrag, Integer> tage;

	private Stage stage;
	private Scene scene;

	boolean ba = false;

	int gebuchteUrlaubstage;
	
	/* Diese Methode wird ausgefuehrt wenn die Seite aufgerufen wird
	   Es wird URL und ResourceBundle übergeben. Diese Klassen werden importiert
	   Es gibt keinen Rückgabewert
	*/

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		start.setCellValueFactory(new PropertyValueFactory<UrlaubEintrag, String>("start"));
		ende.setCellValueFactory(new PropertyValueFactory<UrlaubEintrag, String>("ende"));
		tage.setCellValueFactory(new PropertyValueFactory<UrlaubEintrag, Integer>("tage"));

		List<LocalDate> abwesenheitstage = Person.getAktuellEingeloggterArbeiter().urlaubsUndKrankheitsTage;

		try {

			for (int i = 0; i < abwesenheitstage.size(); i = i + 2) {

				try {
					int genommeneTage = tabelleneintrag(abwesenheitstage.get(i), abwesenheitstage.get(i + 1));
					gebuchteUrlaubstage = gebuchteUrlaubstage + genommeneTage;
				} catch (IndexOutOfBoundsException e) {
					break;

				}
			}

			abwesenheitstagegesamt.setText(gebuchteUrlaubstage + "");
		}

		catch (Exception e) {}

		if (Person.getAktuellEingeloggterArbeiter().sprache == true) {
			labelUrlaubAbwesenheitH.setText("Add Vacation / Abcence");
			labelEnde.setText("End");
			hinzufuegen.setText("Add");
			labelEintraegeBearbeiten.setText("Edit entries");
			labelGebuchteTage.setText("Booked days: ");
			loeschen.setText("Delete");

			if (ba == false) {
				bearbeiten2.setText("Edit");
			} else {
				bearbeiten2.setText("Save");
			}

			start.setText("Start Vacation / Absence");
			ende.setText("End Vacation / Absence");
			tage.setText("Days");

		}

	}
	
	/* Mit dieser Methode das Bearbeiten der Tabelle ermoeglicht und bei erneutem aufrufen wieder unmoeglich gemacht
	   Es wird ein ActionEvent uebergeben
	   Es gibt keinen Rückgabewert
	*/

	@FXML
	void bearbeiten2Click(ActionEvent event) {

		if (ba == false) {
			ba = true;

			bearbeiten2.setStyle("-fx-background-color: #545454");

			loeschen.setVisible(true);

			urlaub.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
				if (newSelection != null) {
					loeschen.setDisable(false);
				}
			});

			if (Person.getAktuellEingeloggterArbeiter().sprache == true) {
				bearbeiten2.setText("Save");
			} else {
				bearbeiten2.setText("Speichern");
			}

			editableCols();
		}

		else {
			ba = false;

			bearbeiten2.setStyle("-fx-background-color:  #dbba51");
			bearbeiten2.setText("Bearbeiten");

			if (Person.getAktuellEingeloggterArbeiter().sprache == true) {
				bearbeiten2.setText("Edit");
			} else {
				bearbeiten2.setText("Bearbeiten");
			}

			loeschen.setDisable(true);
			loeschen.setVisible(false);

			urlaub.setEditable(false);

			abwesenheitstagegesamt.setText(tabelleSpeichern() + "");

		}

	}
	
	/* Mit dieser Methode werden die einzelnen Felder in der Tabelle editierbar
	   Es wird nichts uebergeben
	   Es gibt keinen Rückgabewert
	*/

	private void editableCols() {
		start.setCellFactory(TextFieldTableCell.forTableColumn());
		start.setOnEditCommit(
				e -> e.getTableView().getItems().get(e.getTablePosition().getRow()).setStart(e.getNewValue()));

		ende.setCellFactory(TextFieldTableCell.forTableColumn());
		ende.setOnEditCommit(
				e -> e.getTableView().getItems().get(e.getTablePosition().getRow()).setEnde(e.getNewValue()));

		urlaub.setEditable(true);
	}

	boolean datumueberpruefer1 = false;
	boolean datumueberpruefer2 = false;
	
	/* Mit dieser Methode wird ueberprueft ob ein startdatum gesetzt wurde
	   Es wird ein ActionEvent uebergeben
	   Es gibt keinen Rückgabewert
	*/

	@FXML
	void startdatumClick(ActionEvent event) {

		if (startdatum.getValue() != null) {
			datumueberpruefer1 = true;
		}
		if (datumueberpruefer1 == true && datumueberpruefer2 == true) {
			hinzufuegen.setDisable(false);
		}
	}
	
	/* Mit dieser Methode wird ueberprueft ob ein Enddatum gesetzt wurde
	   Es wird ein ActionEvent uebergeben
	   Es gibt keinen Rückgabewert
	*/

	@FXML
	void endedatumClick(ActionEvent event) {

		if (endedatum.getValue() != null) {
			datumueberpruefer2 = true;
		}
		if (datumueberpruefer1 == true && datumueberpruefer2 == true) {
			hinzufuegen.setDisable(false);
		}
	}
	
	/* Mit dieser Methode werden die eingebenen Start- und Endzeiten eingelesen, gespeichert und der Tabelle hinzugefuegt
	   Es wird ein ActionEvent uebergeben
	   Es gibt keinen Rückgabewert
	*/

	@FXML
	void hinzufuegenClick(ActionEvent event) {

		if (startdatum.getValue().isBefore(LocalDate.now()) || endedatum.getValue().isBefore(LocalDate.now())) {
			datumLiegtInVergangenheit.setText("Eintrag nicht möglich. Mindestens ein Datum liegt in der Vergangenheit");
			datumLiegtInVergangenheit.setVisible(true);

		}

		else if (endedatum.getValue().isBefore(startdatum.getValue())) {
			datumLiegtInVergangenheit.setText("Eintrag nicht möglich. Starttag ist nach dem Endtag");
			datumLiegtInVergangenheit.setVisible(true);
		}

		else {

			datumLiegtInVergangenheit.setVisible(false);

			LocalDate starttag = startdatum.getValue();
			LocalDate endetag = endedatum.getValue();

			tabelleneintrag(starttag, endetag);

			abwesenheitstagegesamt.setText(tabelleSpeichern() + "");

		}
	}
	
	/* Mit dieser Methode wird ein neuer Eintrag der Tabelle hinzugefuegt
	   Es wird der Start- und Endtag uebergeben
	   Es werden die genommenen Urlaubstage zurrueckgegeben
	*/

	public int tabelleneintrag(LocalDate ts, LocalDate te) {

		DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd.MM.uuuu");

		String starttagS = ts.format(formatters);
		String endetagS = te.format(formatters);

		UrlaubEintrag neuerurlaub = new UrlaubEintrag(starttagS, endetagS, 1);
		int genommeneTage = neuerurlaub.getTage();

		ObservableList<UrlaubEintrag> customers = urlaub.getItems();
		customers.add(neuerurlaub);
		urlaub.setItems(customers);
		return genommeneTage;

	}
	
	/* Mit dieser Methode wird die Tabelle in der Json Datei gespeichert
	   Es wird nichts uebergeben
	   Es gibt keinen Rueckwert
	*/

	public int tabelleSpeichern() {

		UrlaubEintrag neuerurlaub2 = new UrlaubEintrag();
		List<LocalDate> arrList = new ArrayList<LocalDate>();

		int genommeneTage = 0;

		for (int i = 0; i < urlaub.getItems().size(); i++) {

			neuerurlaub2 = urlaub.getItems().get(i);

			DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd.MM.uuuu");

			LocalDate starttag = LocalDate.parse(neuerurlaub2.start, formatters);
			LocalDate endetag = LocalDate.parse(neuerurlaub2.ende, formatters);
			genommeneTage = +genommeneTage + neuerurlaub2.getTage();

			arrList.add(starttag);
			arrList.add(endetag);

		}

		Person.getZeitRechner().urlaubsKrankheitsListeHizufuegen(arrList, Person.getAktuellEingeloggterArbeiter());
		return genommeneTage;
	}
	
	/* Mit dieser Methode wird die Startseite aufgerufen
	   Es wird ein ActionEvent uebergeben
	   Es gibt keinen Rueckwert
	*/
	
	@FXML
	void zurueckUClick(ActionEvent event) throws IOException {

		Parent root2 = FXMLLoader.load(getClass().getResource("/org/fxapps/javafx/fatjar/Startseite.fxml"));
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
	void hinzufuegenHervorClick(MouseEvent event) {
		hinzufuegen.setStyle("-fx-background-color: #a18634");
	}

	@FXML
	void hinzufuegenHintenClick(MouseEvent event) {
		hinzufuegen.setStyle("-fx-background-color:  #dbba51");
	}

	@FXML
	void loeschenClick(ActionEvent event) {
		urlaub.getItems().removeAll(urlaub.getSelectionModel().getSelectedItems());
	}

	@FXML
	void loeschenHervorClick(MouseEvent event) {
		loeschen.setStyle("-fx-background-color: #800000");
	}

	@FXML
	void loeschenHintenClick(MouseEvent event) {
		loeschen.setStyle("-fx-background-color: #c32828");
	}

	@FXML
	void zurueckUHervorClick(MouseEvent event) {
		zurueckU.setStyle("-fx-background-color: #696969");
	}

	@FXML
	void zurueckUHintenClick(MouseEvent event) {
		zurueckU.setStyle("-fx-background-color: grey");
	}
	
	@FXML
	void bearbeiten2HervorClick(MouseEvent event) {

		if (ba == false) {
			bearbeiten2.setStyle("-fx-background-color: #a18634");
		} else {
			bearbeiten2.setStyle("-fx-background-color: #4d4d4d");
		}
	}

	@FXML
	void bearbeiten2HintenClick(MouseEvent event) {
		if (ba == false) {
			bearbeiten2.setStyle("-fx-background-color: #dbba51");
		} else {
			bearbeiten2.setStyle("-fx-background-color: #545454");
		}

	}

}
