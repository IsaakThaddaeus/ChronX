package org.fxapps.javafx.fatjar;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private TableView<UrlaubTabelle> urlaub;
    @FXML
    private TableColumn<UrlaubTabelle, String> start;
    @FXML
    private TableColumn<UrlaubTabelle, String> ende;
    @FXML
    private TableColumn<UrlaubTabelle, Integer> tage;
    
    private Stage stage;
	private Scene scene;
    
    @Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
    	start.setCellValueFactory(new PropertyValueFactory<UrlaubTabelle, String>("start"));
    	ende.setCellValueFactory(new PropertyValueFactory<UrlaubTabelle, String>("ende"));
    	tage.setCellValueFactory(new PropertyValueFactory<UrlaubTabelle, Integer>("tage"));
    	
    	List <LocalDate> abwesenheitstage = Person.getAktuellEingeloggterArbeiter().urlaubsUndKrankheitsTage;
    	ladeTabelle(abwesenheitstage);
    	
    }
    
    public void ladeTabelle(List<LocalDate> l) {
		
		urlaub.getItems().clear();
		System.out.println(l);
	
		for (int i = 0; i < l.size(); i = i + 2) {
			
		try {
		
			DateTimeFormatter formatters  = DateTimeFormatter.ofPattern("dd.MM.uuuu");
	    	
	    	String starttagS = l.get(i).format(formatters);
	    	String endetagS = l.get(i+1).format(formatters);
		
		UrlaubTabelle urlaub3 = new UrlaubTabelle(starttagS, endetagS, 0);
		//double stundenD = urlaub3.getStunden();
		ObservableList<UrlaubTabelle> customers = urlaub.getItems();
		customers.add(urlaub3);
		urlaub.setItems(customers);
		
		
		}
		catch (IndexOutOfBoundsException e) {
			System.out.println("Hier wurde die Schleife abgebrochen");
			break;
			
		}
	}
    }
    boolean ba = false;

    @FXML
    void bearbeiten2Click(ActionEvent event) {
    	
    	if (ba == false) {
    		
    		ba = true;
    		editableCols();
    		
    		
    	}

    }

    @FXML
	void bearbeiten2HervorClick(MouseEvent event) {

		if (ba == false) {
			bearbeiten2.setStyle("-fx-background-color: #a18634");
		} else {
			bearbeiten2.setStyle("-fx-background-color: #696969");
		}
	}

	@FXML
	void bearbeiten2HintenClick(MouseEvent event) {
		if (ba == false) {
			bearbeiten2.setStyle("-fx-background-color: #dbba51");
		} else {
			bearbeiten2.setStyle("-fx-background-color: grey");
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

    @FXML
    void hinzufuegenClick(ActionEvent event) {
    	
    	LocalDate starttag = startdatum.getValue();
    	LocalDate endetag = endedatum.getValue();
    	
    	DateTimeFormatter formatters  = DateTimeFormatter.ofPattern("dd.MM.uuuu");
    	
    	String starttagS = starttag.format(formatters);
    	String endetagS = endetag.format(formatters);
    	
    	UrlaubTabelle neuerurlaub = new UrlaubTabelle(starttagS, endetagS, 0);
    	UrlaubTabelle.getTage();
		ObservableList<UrlaubTabelle> customers = urlaub.getItems();
		customers.add(neuerurlaub);
		urlaub.setItems(customers);
    	

    }

    @FXML
    void hinzufuegenHervorClick(MouseEvent event) {hinzufuegen.setStyle("-fx-background-color: #a18634");}
    @FXML
    void hinzufuegenHintenClick(MouseEvent event) {hinzufuegen.setStyle("-fx-background-color:  #dbba51");}

    @FXML
    void loeschenClick(ActionEvent event) {

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
