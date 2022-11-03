package org.fxapps.javafx.fatjar;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class ControlStatistik implements Initializable {

	@FXML
	private CategoryAxis X;
	@FXML
	private CategoryAxis xquartal;
	@FXML
	private CategoryAxis XJahr;

	@FXML
	private NumberAxis YQ;
	@FXML
	private NumberAxis YJahr;
	@FXML
	private NumberAxis Y;

	@FXML
	private BarChart<String, Number> barchart;
	@FXML
	private BarChart<String, Number> barchartQ;
	@FXML
	private BarChart<String, Number> barchartJahr;

	@FXML
	private AnchorPane aPaneQuartal;
	@FXML
	private AnchorPane aPaneMonat;
	@FXML
	private AnchorPane aPaneJahr;

	@FXML
	private Label labelDienstag;
	@FXML
	private Label labelDonnerstag;
	@FXML
	private Label labelFreitag;
	@FXML
	private Label labelHeute;
	@FXML
	private Label labelJahr;
	@FXML
	private Label labelKalender;
	@FXML
	private Label labelMittwoch;
	@FXML
	private Label labelMontag;
	@FXML
	private Label labelSamstag;
	@FXML
	private Label labelSonntag;
	@FXML
	private Label labelStatistik;
	@FXML
	private Label labelUrlaubAbwesenheit;
	@FXML
	private Label labelQuartalAnzeige;

	@FXML
	private Label tag01, tag02, tag03, tag04, tag05, tag06, tag11, tag12, tag13, tag14, tag15, tag16, tag21, tag22,
			tag23, tag24, tag25, tag26, tag31, tag32, tag33, tag34, tag35, tag36, tag41, tag42, tag43, tag44, tag45,
			tag46;

	@FXML
	private Label tag51, tag52, tag53, tag54, tag55, tag56, tag61, tag62, tag63, tag64, tag65, tag66;


	public Label[][] arrayLabel;

	@FXML
	private Label monat;

	@FXML
	private AnchorPane uebersicht1;
	@FXML
	private AnchorPane uebersicht11;

	@FXML
	private Button zurueckS;

	@FXML
	private MenuButton auswahlJahre;
	@FXML
	private MenuButton auswahlqm;

	@FXML
	private MenuItem monate;
	@FXML
	private MenuItem quartale;
	@FXML
	private MenuItem jahr;

	int auswahlQuartalMoanat = 1;
	int jahrErsterTag;
	int aktuellesJahr;

	private Stage stage;
	private Scene scene;
	
	/* Diese Methode wird ausgefuehrt wenn die Seite aufgerufen wird
	   Es wird URL und ResourceBundle übergeben. Diese Klassen werden importiert
	   Es gibt keinen Rückgabewert
	*/

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {


		arrayLabel = new Label[7][6];
		arrayLabel[0][0] = tag01;
		arrayLabel[1][0] = tag11;
		arrayLabel[2][0] = tag21;
		arrayLabel[3][0] = tag31;
		arrayLabel[4][0] = tag41;
		arrayLabel[5][0] = tag51;
		arrayLabel[6][0] = tag61;
		arrayLabel[0][1] = tag02;
		arrayLabel[1][1] = tag12;
		arrayLabel[2][1] = tag22;
		arrayLabel[3][1] = tag32;
		arrayLabel[4][1] = tag42;
		arrayLabel[5][1] = tag52;
		arrayLabel[6][1] = tag62;
		arrayLabel[0][2] = tag03;
		arrayLabel[1][2] = tag13;
		arrayLabel[2][2] = tag23;
		arrayLabel[3][2] = tag33;
		arrayLabel[4][2] = tag43;
		arrayLabel[5][2] = tag53;
		arrayLabel[6][2] = tag63;
		arrayLabel[0][3] = tag04;
		arrayLabel[1][3] = tag14;
		arrayLabel[2][3] = tag24;
		arrayLabel[3][3] = tag34;
		arrayLabel[4][3] = tag44;
		arrayLabel[5][3] = tag54;
		arrayLabel[6][3] = tag64;
		arrayLabel[0][4] = tag05;
		arrayLabel[1][4] = tag15;
		arrayLabel[2][4] = tag25;
		arrayLabel[3][4] = tag35;
		arrayLabel[4][4] = tag45;
		arrayLabel[5][4] = tag55;
		arrayLabel[6][4] = tag65;
		arrayLabel[0][5] = tag06;
		arrayLabel[1][5] = tag16;
		arrayLabel[2][5] = tag26;
		arrayLabel[3][5] = tag36;
		arrayLabel[4][5] = tag46;
		arrayLabel[5][5] = tag56;
		arrayLabel[6][5] = tag66;
//    

		LocalDate date = LocalDate.now().withDayOfMonth(1);
		int ersterTagDesMonats = date.getDayOfWeek().getValue();

		LocalDate date2 = LocalDate.now();
		int heutigerTag = date2.getDayOfMonth();
		int aktuellerMonat = date2.getMonthValue();
		String aktuellerMonatString = date2.getMonth().getDisplayName(TextStyle.FULL, Locale.GERMAN).toString();
		monat.setText(aktuellerMonatString);

		ersterTagDesMonats--;
		int monatstag = 1;
		int DisableBegleiter = 1;

		Calendar cal = Calendar.getInstance();
		int tageDesMonats = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

		int monatDavor = 0;

		if (aktuellerMonat == 1) {
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

					arrayLabel[k][i].setText(tageDesMonatsDavor - (ersterTagDesMonats - (k + 1)) + "");
					arrayLabel[k][i].setDisable(true);
					monatstag = 0;
					DisableBegleiter = 0;
				} else {

					if (monatstag > tageDesMonats) {
						monatstag = 1;
					}
					if (DisableBegleiter > tageDesMonats) {
						arrayLabel[k][i].setDisable(true);
					}

					arrayLabel[k][i].setText(monatstag + "");

				}

				for (int s = 0; s < abwesenheitstageMitMonat.size(); s = s + 2) {

					if (abwesenheitstageMitMonat.get(s).getMonthValue() == aktuellerMonat
							&& abwesenheitstageMitMonat.get(s + 1).getMonthValue() == aktuellerMonat
							&& DisableBegleiter <= tageDesMonats) {
						if (monatstag >= abwesenheitstageMitMonat.get(s).getDayOfMonth()
								&& monatstag <= abwesenheitstageMitMonat.get(s + 1).getDayOfMonth()) {
							arrayLabel[k][i].setStyle("-fx-background-color: #0c9ec6");
						}

					}

					if (abwesenheitstageMitMonat.get(s).getMonthValue() != aktuellerMonat
							&& abwesenheitstageMitMonat.get(s + 1).getMonthValue() == aktuellerMonat) {
						if (DisableBegleiter <= abwesenheitstageMitMonat.get(s + 1).getDayOfMonth()) {
							arrayLabel[k][i].setStyle("-fx-background-color: #0c9ec6");
						}
					}

					if (abwesenheitstageMitMonat.get(s).getMonthValue() == aktuellerMonat
							&& abwesenheitstageMitMonat.get(s + 1).getMonthValue() != aktuellerMonat) {
						if (DisableBegleiter >= abwesenheitstageMitMonat.get(s).getDayOfMonth()) {
							arrayLabel[k][i].setStyle("-fx-background-color: #0c9ec6");
						}
					}

				}

				if (monatstag == heutigerTag && check == true) {
					arrayLabel[k][i].setStyle("-fx-background-color: #dbba51");
					check = false;
				}

				monatstag++;
				DisableBegleiter++;

			}

		}

		try {

			jahrErsterTag = Person.getZeitRechner().gibErsterTag(Person.getAktuellEingeloggterArbeiter()).getYear();

		} catch (Exception es) {
			jahrErsterTag = LocalDate.now().getYear();
		}

		aktuellesJahr = LocalDate.now().getYear();

		MenuItem[] items = new MenuItem[aktuellesJahr - jahrErsterTag + 1];

		for (int i = 0; i <= aktuellesJahr - jahrErsterTag; i++) {

			items[i] = new MenuItem();
			items[i].setText((jahrErsterTag + i) + "");

			auswahlJahre.getItems().add(items[i]);

			final int i2 = i;

			items[i].setOnAction((ActionEvent e) -> {
				auswahlJahre.setText(items[i2].getText());

				barchartCreator(auswahlQuartalMoanat, Integer.parseInt(items[i2].getText()));

			});
		}

		auswahlJahre.setText(LocalDate.now().getYear() + "");
		barchartCreator(auswahlQuartalMoanat, LocalDate.now().getYear());

		if (Person.getAktuellEingeloggterArbeiter().sprache == true) {

			labelKalender.setText("Calendar");
			monat.setText(date2.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH).toString());
			labelMontag.setText("Monday");
			labelDienstag.setText("Tuesday");
			labelMittwoch.setText("Wednesday");
			labelDonnerstag.setText("Thursday");
			labelFreitag.setText("Friday");
			labelSamstag.setText("Saturday");
			labelSonntag.setText("Sunday");
			labelUrlaubAbwesenheit.setText("Vacation / Absence");
			labelHeute.setText("Today");
			labelStatistik.setText("Statistics");
			labelJahr.setText("Year");
			auswahlqm.setText("Months");
			monate.setText("Months");
			quartale.setText("Quarters");
			jahr.setText("Year");
			labelQuartalAnzeige.setText(
					"               Quarter 1                        Quarter 2                          Quarter 3                        Quarter 4");

		}

	}
	
	/* Mit dieser Methode wird eine BarChart erstellt und mit Inhalten gefüllt
	   Es wird eine Zahl uebergeben die Auskunft ueber die Art der Barchart gibt (also ob es die Monats, Quartals oder Jahresuebersicht erstellen soll) und das ausgewaehlte Jahr
	   Es gibt keinen Rückgabewert
	*/

	public void barchartCreator(int qm, int jahr) {

		barchart.getData().clear();
		barchartQ.getData().clear();
		barchartJahr.getData().clear();

		String[] monateKurz = new String[12];
		monateKurz[0] = "Jan";
		monateKurz[1] = "Feb";
		monateKurz[2] = "März";
		monateKurz[3] = "Apr";
		monateKurz[4] = "Mai";
		monateKurz[5] = "Jun";
		monateKurz[6] = "Jul";
		monateKurz[7] = "Aug";
		monateKurz[8] = "Sep";
		monateKurz[9] = "Okt";
		monateKurz[10] = "Nov";
		monateKurz[11] = "Dez";

		String[] monateKurzEnglisch = new String[12];
		monateKurzEnglisch[0] = "Jan";
		monateKurzEnglisch[1] = "Feb";
		monateKurzEnglisch[2] = "Mar";
		monateKurzEnglisch[3] = "Apr";
		monateKurzEnglisch[4] = "May";
		monateKurzEnglisch[5] = "Jun";
		monateKurzEnglisch[6] = "Jul";
		monateKurzEnglisch[7] = "Aug";
		monateKurzEnglisch[8] = "Sep";
		monateKurzEnglisch[9] = "Okt";
		monateKurzEnglisch[10] = "Nov";
		monateKurzEnglisch[11] = "Dec";

		String[] quartale = new String[4];
		quartale[0] = "Quartal 1";
		quartale[1] = "Quartal 2";
		quartale[2] = "Quartal 3";
		quartale[3] = "Quartal 4";

		int durchlauf = 0;
		ObservableList<String> sources = FXCollections.observableArrayList();

		switch (qm) {

		case 1:

			aPaneMonat.setVisible(true);
			aPaneQuartal.setVisible(false);
			aPaneJahr.setVisible(false);

			if (Person.getAktuellEingeloggterArbeiter().sprache == true) {

				X.setCategories(FXCollections.<String>observableArrayList(Arrays.asList(monateKurzEnglisch[0],
						monateKurzEnglisch[1], monateKurzEnglisch[2], monateKurzEnglisch[3], monateKurzEnglisch[4],
						monateKurzEnglisch[5], monateKurzEnglisch[6], monateKurzEnglisch[7], monateKurzEnglisch[8],
						monateKurzEnglisch[9], monateKurzEnglisch[10], monateKurzEnglisch[11])));

			} else {

				X.setCategories(FXCollections.<String>observableArrayList(Arrays.asList(monateKurz[0], monateKurz[1],
						monateKurz[2], monateKurz[3], monateKurz[4], monateKurz[5], monateKurz[6], monateKurz[7],
						monateKurz[8], monateKurz[9], monateKurz[10], monateKurz[11])));

			}
			durchlauf = 12;

			break;

		case 2:

			aPaneMonat.setVisible(false);
			aPaneQuartal.setVisible(true);
			aPaneJahr.setVisible(false);

			xquartal.setCategories(FXCollections
					.<String>observableArrayList(Arrays.asList(quartale[0], quartale[1], quartale[2], quartale[3])));
			durchlauf = 4;

			break;

		case 3:

			aPaneMonat.setVisible(false);
			aPaneQuartal.setVisible(false);
			aPaneJahr.setVisible(true);

			for (int i = 0; i <= (aktuellesJahr - jahrErsterTag); i++) {
				sources.add((jahrErsterTag + i) + "");

			}

			XJahr.setCategories(sources);
			durchlauf = aktuellesJahr - jahrErsterTag + 1;

			break;

		}

		XYChart.Series<String, Number> seriesM = new XYChart.Series<>();
		XYChart.Series<String, Number> seriesQ = new XYChart.Series<>();
		XYChart.Series<String, Number> seriesJ = new XYChart.Series<>();

		seriesM.setName("Monate");
		seriesQ.setName("Quartale");
		seriesJ.setName("Jahre");

		for (int i = 0; i < durchlauf; i++) {

			switch (qm) {

			case 1:
				seriesM.getData().add(new XYChart.Data<>(monateKurz[i], Person.getZeitRechner().gibGleitzeitMonat(
						Person.getAktuellEingeloggterArbeiter(), LocalDate.now().withMonth(i + 1).withYear(jahr))));

				break;

			case 2:

				seriesQ.getData().add(new XYChart.Data<>(quartale[i], Person.getZeitRechner().gibGleitzeitQuartal(
						Person.getAktuellEingeloggterArbeiter(), LocalDate.now().withMonth(i * 3 + 1).withYear(jahr))));

				break;

			case 3:

				seriesJ.getData().add(new XYChart.Data<>(sources.get(i), Person.getZeitRechner().gibGleitzeitJahr(Person.getAktuellEingeloggterArbeiter(), LocalDate.now().withYear(jahrErsterTag + i))));

				break;

			}
		}

		switch (qm) {

		case 1:
			barchart.getData().addAll(seriesM);
			break;

		case 2:
			barchartQ.getData().addAll(seriesQ);
			break;

		case 3:
			boolean addAll = barchartJahr.getData().addAll(seriesJ);

			break;
		}

	}
	
	/* Mit dieser Methode wird die MOnatsuebersicht ausgewaehlt
	   Es wird ein ActionEvent uebergeben
	   Es gibt keinen Rückgabewert
	*/

	@FXML
	void monateClick(ActionEvent event) {

		auswahlQuartalMoanat = 1;
		auswahlqm.setText("Monat");
		barchartCreator(auswahlQuartalMoanat, Integer.parseInt(auswahlJahre.getText()));

	}
	
	/* Mit dieser Methode wird die Quartalsuebersicht ausgewaehlt
	   Es wird ein ActionEvent uebergeben
	   Es gibt keinen Rückgabewert
	*/

	@FXML
	void quartaleClick(ActionEvent event) {

		auswahlQuartalMoanat = 2;
		auswahlqm.setText("Quartal");
		barchartCreator(auswahlQuartalMoanat, Integer.parseInt(auswahlJahre.getText()));

	}
	
	/* Mit dieser Methode wird die Jahresuebersicht ausgewaehlt
	   Es wird ein ActionEvent uebergeben
	   Es gibt keinen Rückgabewert
	*/
	

	@FXML
	void jahrClick(ActionEvent event) {

		auswahlQuartalMoanat = 3;
		auswahlqm.setText("Jahre");
		barchartCreator(auswahlQuartalMoanat, Integer.parseInt(auswahlJahre.getText()));

	}
	
	/* Mit dieser Methode die Startseite aufgerufen
	   Es wird ein ActionEvent uebergeben
	   Es gibt keinen Rückgabewert
	*/

	@FXML
	void zurueckSClick(ActionEvent event) throws IOException {

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
	void zurueckSHervorClick(MouseEvent event) {
		zurueckS.setStyle("-fx-background-color:  #696969");
	}

	@FXML
	void zurueckSHintenClick(MouseEvent event) {
		zurueckS.setStyle("-fx-background-color:  grey");
	}

}
