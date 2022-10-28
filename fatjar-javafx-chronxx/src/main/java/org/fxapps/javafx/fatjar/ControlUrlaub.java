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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
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
    
    @Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
    	start.setCellValueFactory(new PropertyValueFactory<UrlaubEintrag, String>("start"));
    	ende.setCellValueFactory(new PropertyValueFactory<UrlaubEintrag, String>("ende"));
    	tage.setCellValueFactory(new PropertyValueFactory<UrlaubEintrag, Integer>("tage"));
    	
    	List <LocalDate> abwesenheitstage = Person.getAktuellEingeloggterArbeiter().urlaubsUndKrankheitsTage;
    	

		System.out.println("Jetzt geht er in ladeTabelle und ich überprüfe");
			
			//urlaub.getItems().clear();
		
			System.out.println("Das ist die ArrayList: " + abwesenheitstage);
			
			try {
		
			for (int i = 0; i < abwesenheitstage.size(); i = i + 2) {
				
			try {
				tabelleneintrag(abwesenheitstage.get(i), abwesenheitstage.get(i+1));
			}
			catch (IndexOutOfBoundsException e) {
				System.out.println("Hier wurde die Schleife abgebrochen");
				break;
				
			}
		}
		}
			
			// System.out.println("Hier befinden wir uns außerhalb der Schleife");
			// if (abwesenheitstage.size()%2 == 0) {gleitzeitAktualisieren();
			
			
			catch (Exception e) {System.out.println("Die Schleife wurde nicht einmal ausgeführt");}
		
		}
    boolean ba = false;

    @FXML
    void bearbeiten2Click(ActionEvent event) {
    	
    	if (ba == false) {   		
    		ba = true;
			
			bearbeiten2.setStyle("-fx-background-color: #545454");
			bearbeiten2.setText("Fertig");
			
			loeschen.setVisible(true);

			urlaub.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
				if (newSelection != null) {
					loeschen.setDisable(false);
				}
			});

    		editableCols();	
    	}

		else {
			ba = false;

			bearbeiten2.setStyle("-fx-background-color:  #dbba51");
			bearbeiten2.setText("Bearbeiten");
			
			loeschen.setVisible(false);
			loeschen.setVisible(true);

			urlaub.setEditable(false);

		}

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
	@FXML
    void startdatumClick(ActionEvent event) {

		if (startdatum.getValue() != null) {datumueberpruefer1 = true;}
		if (datumueberpruefer1 == true && datumueberpruefer2 == true) {hinzufuegen.setDisable(false);}
	}

	@FXML
    void endedatumClick(ActionEvent event) {

		if (endedatum.getValue() != null) {datumueberpruefer2 = true;}
		if (datumueberpruefer1 == true && datumueberpruefer2 == true) {hinzufuegen.setDisable(false);}
	}


    @FXML
    void hinzufuegenClick(ActionEvent event) {
    	
    	LocalDate starttag = startdatum.getValue();
    	LocalDate endetag = endedatum.getValue();
    	
		tabelleneintrag(starttag, endetag);
    	

    }

	public void tabelleneintrag (LocalDate ts, LocalDate te) {
		
		DateTimeFormatter formatters  = DateTimeFormatter.ofPattern("dd.MM.uuuu");
    	
    	String starttagS = ts.format(formatters);
    	String endetagS = te.format(formatters);
    	
    	UrlaubEintrag neuerurlaub = new UrlaubEintrag(starttagS, endetagS, 1);
    	int useless = neuerurlaub.getTage();
		
		ObservableList<UrlaubEintrag> customers = urlaub.getItems();

		System.out.println("Customers vorher " + customers);
		customers.add(neuerurlaub);
		System.out.println("Customers nachher " + customers);
		urlaub.setItems(customers);

	}

	public void tabelleSpeichern (List <LocalDate> l) {
		
			UrlaubEintrag neuerurlaub2 = new UrlaubEintrag();
			//LocalDate date = kalenderPicker2.getValue();
			List<LocalDate> arrList = new ArrayList<LocalDate>();
			
			for (int i = 0; i < urlaub.getItems().size(); i++) {
			  
				neuerurlaub2 = urlaub.getItems().get(i);

				LocalDate starttag = LocalDate.parse(neuerurlaub2.start);
				LocalDate endetag = LocalDate.parse(neuerurlaub2.ende);
				// double einzelStunden = neuerurlaub2.getStunden();
				// String[] kommenS = eintrag2.kommen.split(":");
				// String[] gegangenS = eintrag2.gegangen.split(":");
				
					//arrList.add(new ArrayList<>());
					arrList.add(starttag);
					arrList.add(endetag);
					// arrList.get(i).add(eintrag2.stunden);	
				
			}
			 System.out.println(arrList +" Hallo " + arrList.get(0));

			 //Person.getZeitRechner().aufUrlaubsKrankheitsliste(arrList, Person.getAktuellEingeloggterArbeiter());
	}

    @FXML
    void hinzufuegenHervorClick(MouseEvent event) {hinzufuegen.setStyle("-fx-background-color: #a18634");}
    @FXML
    void hinzufuegenHintenClick(MouseEvent event) {hinzufuegen.setStyle("-fx-background-color:  #dbba51");}

    @FXML
    void loeschenClick(ActionEvent event) {
		urlaub.getItems().removeAll(urlaub.getSelectionModel().getSelectedItems());
    }

    @FXML
    void loeschenHervorClick(MouseEvent event) {loeschen.setStyle("-fx-background-color: #800000");}
    @FXML
    void loeschenHintenClick(MouseEvent event) {loeschen.setStyle("-fx-background-color: #c32828");}

    @FXML
    void zurueckUClick(ActionEvent event) throws IOException {
    	
    	Parent root2 = FXMLLoader.load(getClass().getResource("/org/fxapps/javafx/fatjar/Startseite.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root2);
		stage.setScene(scene);
		stage.show();
    	
    }

    @FXML
    void zurueckUHervorClick(MouseEvent event) {zurueckU.setStyle("-fx-background-color: #696969");}
    @FXML
    void zurueckUHintenClick(MouseEvent event) {zurueckU.setStyle("-fx-background-color: grey");}

}
