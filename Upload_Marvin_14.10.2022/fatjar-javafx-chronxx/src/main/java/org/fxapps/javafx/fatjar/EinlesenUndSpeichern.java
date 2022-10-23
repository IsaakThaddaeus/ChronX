package org.fxapps.javafx.fatjar;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.fxapps.javafx.fatjar.Arbeiter;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;

//Hier nichts veraendern!!
public class EinlesenUndSpeichern {
	private static String dateiPfad;
	public static List<Arbeiter> zuDruckendeWerte = new ArrayList<>();
	Set<File> datein = new HashSet<>();
	
	public EinlesenUndSpeichern() {

		String dateiPfadVonJar = null;
		try {
			dateiPfadVonJar = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI())
					.getPath();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String dateiPfadJsonFile = dateiPfadVonJar.substring(0, dateiPfadVonJar.lastIndexOf("\\")) + "\\jsonfile";
		dateiPfad = dateiPfadJsonFile;
	}

	private Set<File> leseDateiAusOrdner(File ordner) throws IOException {
		System.out.println(ordner);
		for (final File datei : ordner.listFiles()) {
			if (datei.getName().contains(".json")) {
				System.out.println(datei.getName());
				datein.add(datei);
			} else if (datei.isDirectory()) {
				System.out.println("Es befindet sich ein weiterer Ordner im Zielordner");
				leseDateiAusOrdner(datei);
			} else {
				System.out.println(datei.getName() + " Ist keine lesbarer Dateintyp");
			}
		}
		return datein;
	}
//	private Set<File> leseDateiAusOrdner(File datei) throws IOException {
//		System.out.println(datei);
//	
//		
//
//				datein.add(new File("src/main/java/org/fxapps/javafx/fatjar/Daten.json"));
//		
//				System.out.println(datein);
//		return datein;
//	}
public static boolean bereitseingelesen=false;
	private List<Arbeiter> arbeiterEinlesen(File zielDatei) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.findAndRegisterModules();
		try (MappingIterator<Arbeiter> alleEintraegeDerJsonDatei = mapper.readerFor(Arbeiter.class)
				.readValues(zielDatei)) {
			if(!bereitseingelesen) {
				bereitseingelesen =true;
			while (alleEintraegeDerJsonDatei.hasNextValue()) {
				Arbeiter eineZeile = alleEintraegeDerJsonDatei.nextValue();
				zuDruckendeWerte.add(eineZeile);
				System.out.println("Hinzugefügt");
			}}
			return zuDruckendeWerte;
		}
	}
	
	public Arbeiter zuordnungDesEingeloggtenArbeiters(String email, String passwort) throws IOException {
		Set<File> rohDatei = leseDateiAusOrdner(new File(dateiPfad));
		Iterator<File> iteratorDerRohDatei = rohDatei.iterator();
		File datei = iteratorDerRohDatei.next();
		List<Arbeiter> gemappteDatei=null;
		
		
			gemappteDatei	= arbeiterEinlesen(datei);
		
		
		
		for (Arbeiter eintrag : gemappteDatei) {
			if (eintrag.email.equalsIgnoreCase(email) && eintrag.passwort.equals(passwort)) {
				System.out.println("Zugriff auf " + eintrag.email + " genehmigt");
				return eintrag;
			}
		}
		System.out.println("Falsche Login Daten");
		
		return null;
	}

//	public static void abspeichernVonAenderungen(Arbeiter arbeiter) throws NullPointerException {
//		for (Arbeiter eintrag : zuDruckendeWerte) {
//			try {
//				if (eintrag.email == arbeiter.email) {
//					List<Arbeiter> neueZuDruckendeWerte = new ArrayList<>();
//					neueZuDruckendeWerte = zuDruckendeWerte;
//					System.out.println(eintrag.email + " aw2222awdwd  " + arbeiter.email+" ad "+neueZuDruckendeWerte+"aa"+zuDruckendeWerte);
//
//					zuDruckendeWerte.clear();
//					zuDruckendeWerte.addAll(neueZuDruckendeWerte);
//					System.out.println(eintrag.email + " awdawdwd  " + arbeiter.email+" ad "+neueZuDruckendeWerte+"aa"+zuDruckendeWerte);
//					break;
//				}
//
//			} catch (Exception e) {
//				System.out.println("Arbeiter ist Fehlerhaft");
//			}
//		}
//		drucker();
//	}
	public static void abspeichernVonAenderungen(Arbeiter arbeiter) throws NullPointerException {
		for (Arbeiter eintrag : zuDruckendeWerte) {
			try {
				if (eintrag.email == arbeiter.email) {
					zuDruckendeWerte.remove(arbeiter);
					zuDruckendeWerte.add(arbeiter);
					System.out.println("Test");
					break;
				}
			} catch (Exception e) {
				System.out.println("Arbeiter ist Fehlerhaft");
			}
		}
		drucker();
	}

	public static void arbeiterRegistrieren(String email, String passwort, LocalDate geburtstag, double wochenstunde,
			double warngrenze, boolean leiter) {
		Arbeiter zuRegistrierenderArbeiter = new Arbeiter();

		zuRegistrierenderArbeiter.email = email;
		zuRegistrierenderArbeiter.passwort = passwort;
		zuRegistrierenderArbeiter.geburtstag = geburtstag;

		zuRegistrierenderArbeiter.wochenstunden = wochenstunde;
		zuRegistrierenderArbeiter.gleitzeitWarngrenze = warngrenze;
		zuRegistrierenderArbeiter.artDesAngestellten = leiter;
		zuDruckendeWerte.add(zuRegistrierenderArbeiter);
		abspeichernVonAenderungen(zuRegistrierenderArbeiter);
	}

//	  Person.email = emailR.getText();
//      Person.passwort = passwort.getText();
//      Person.geburtstag = kalenderPickerR.getValue();
//      Person.wochenstunden = Double.parseDouble(menuButtonH.getText());
//      Person.warngrenze = Double.parseDouble(warngrenzeR.getText());
//      Person.leiter = leiterR.isSelected();
	private static void drucker() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.findAndRegisterModules();
		try {
			mapper.writeValue(new File(dateiPfad + "/Daten.json"), zuDruckendeWerte);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("-Printing Finished-");
	}
}