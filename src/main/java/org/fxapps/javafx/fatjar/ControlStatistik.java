package org.fxapps.javafx.fatjar;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ControlStatistik implements Initializable {

    @FXML
    private Label tag01, tag02, tag03, tag04, tag05, tag11, tag12, tag13, tag14, tag15, tag21, tag22, tag23, tag24, tag25, tag31, tag32, tag33, tag34, tag35, tag41, tag42, tag43, tag44, tag45;

//    @FXML
//    private Label tag02;

//    @FXML
//    private Label tag03;
//
//    @FXML
//    private Label tag04;
//
//    @FXML
//    private Label tag05;
//
//    @FXML
//    private Label tag11;
//
//    @FXML
//    private Label tag12;
//
//    @FXML
//    private Label tag13;
//
//    @FXML
//    private Label tag14;
//
//    @FXML
//    private Label tag15;
//
//    @FXML
//    private Label tag21;
//
//    @FXML
//    private Label tag22;
//
//    @FXML
//    private Label tag23;
//
//    @FXML
//    private Label tag24;
//
//    @FXML
//    private Label tag25;
//
//    @FXML
//    private Label tag31;
//
//    @FXML
//    private Label tag32;
//
//    @FXML
//    private Label tag33;
//
//    @FXML
//    private Label tag34;
//
//    @FXML
//    private Label tag35;
//
//    @FXML
//    private Label tag41;
//
//    @FXML
//    private Label tag42;
//
//    @FXML
//    private Label tag43;
//
//    @FXML
//    private Label tag44;
//
//    @FXML
//    private Label tag45;

    @FXML
    private Label tag51, tag52, tag53, tag54, tag55, tag61, tag62, tag63, tag64, tag65;
 
//    @FXML
//    private Label tag52;
//
//    @FXML
//    private Label tag53;
//
//    @FXML
//    private Label tag54;
//
//    @FXML
//    private Label tag55;
//
//    @FXML
//    private Label tag61;
//
//    @FXML
//    private Label tag62;
//
//    @FXML
//    private Label tag63;
//
//    @FXML
//    private Label tag64;
//
//    @FXML
//    private Label tag65;
    
    public Label[][] arrayLabel;

    @FXML
    private AnchorPane uebersicht1;

    @FXML
    private AnchorPane uebersicht11;

    @FXML
    private Button zurueckP;
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    	
    	System.out.println("Initialisiere");
    	
    	arrayLabel = new Label[6][4];
    	arrayLabel[0][0] = tag01;
    	arrayLabel[0][1] = tag01;
    	arrayLabel[0][2] = tag01;
    	arrayLabel[0][3] = tag01;
    	arrayLabel[0][4] = tag01;
    	arrayLabel[0][5] = tag01;
    	arrayLabel[1][0] = tag01;
    	arrayLabel[1][1] = tag01;
    	arrayLabel[1][2] = tag01;
    	arrayLabel[1][3] = tag01;
    	arrayLabel[1][4] = tag01;
    	arrayLabel[1][5] = tag01;
    	arrayLabel[2][0] = tag01;
    	arrayLabel[2][1] = tag01;
    	arrayLabel[2][2] = tag01;
    	arrayLabel[2][3] = tag01;
    	arrayLabel[2][4] = tag01;
    	arrayLabel[2][5] = tag01;
    	arrayLabel[3][0] = tag01;
    	arrayLabel[3][1] = tag01;
    	arrayLabel[3][2] = tag01;
    	arrayLabel[3][3] = tag01;
    	arrayLabel[3][4] = tag01;
    	arrayLabel[3][5] = tag01;
    	arrayLabel[4][0] = tag01;
    	arrayLabel[4][1] = tag01;
    	arrayLabel[4][2] = tag01;
    	arrayLabel[4][3] = tag01;
    	arrayLabel[4][4] = tag01;
    	arrayLabel[4][5] = tag01;
    	arrayLabel[5][0] = tag01;
    	arrayLabel[5][1] = tag01;
    	arrayLabel[5][2] = tag01;
    	arrayLabel[5][3] = tag01;
    	arrayLabel[5][4] = tag01;
    	arrayLabel[5][5] = tag01;
    	arrayLabel[6][0] = tag01;
    	arrayLabel[6][1] = tag01;
    	arrayLabel[6][2] = tag01;
    	arrayLabel[6][3] = tag01;
    	arrayLabel[6][4] = tag01;
    	arrayLabel[6][5] = tag01;
    
    	
    	
    	
    }

    @FXML
    void zurueckPClick(ActionEvent event) {

    }

    @FXML
    void zurueckPHervorClick(MouseEvent event) {

    }

    @FXML
    void zurueckPHintenClick(MouseEvent event) {

    }

}
