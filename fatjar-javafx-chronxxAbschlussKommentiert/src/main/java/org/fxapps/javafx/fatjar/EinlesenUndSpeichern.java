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

public class EinlesenUndSpeichern {
	private static String dateiPfad;
	public static List<Arbeiter> zuDruckendeWerte = new ArrayList<>();
	Set<File> datein = new HashSet<>();
	private static final String generalPasswort = "ysaEsIgnidoc";
/*
 * Konstruktor der den Aktuellen Dateipfad der JsonDatei ausliesst.
 */
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
/*
 * Nimmt einen Ordner und liesst alle Dateien daraus und uberprueft ob die Datein eine Json Datei ist.
 * Diese Dateien werden als Set zurueckgeben ebenfalls ist diese Methode bei mehreren Ordner rekursiv 
 */
	private Set<File> leseDateiAusOrdner(File ordner) throws IOException {
		System.out.println(ordner);
		for (final File datei : ordner.listFiles()) {
			if (datei.getName().contains(".json")) {
				System.out.println(datei.getName());
				datein.add(datei);
			} else if (datei.isDirectory()) {
				leseDateiAusOrdner(datei);
			} else {
			}
		}
		return datein;
	}
	/*
	 * Loescht den mitgegebenen aus der zudruckendenListe Seite und durckt diese danach wodurch der Mitarbeiter entfernt wird
	 */
	public boolean arbeiterloeschen(Arbeiter arbeiter) {
		try {
			zuDruckendeWerte.remove(arbeiter);
			drucker();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
/*
 * Nimmt die Email von einem arbeiter und manipuliert den String das in der Klasse Person der Vorname und Nachname gesetzt werden kann 
 */
	public void nameAusEmailErmittler(Arbeiter arbeiter) {

		try {
			String vorname = arbeiter.email.substring(0, arbeiter.email.indexOf('.'));
			String nachname = arbeiter.email.substring(arbeiter.email.indexOf('.') + 1, (arbeiter.email.indexOf('@')));

			Person.vorname = vorname.substring(0, 1).toUpperCase() + vorname.substring(1, vorname.length());
			Person.nachname = nachname.substring(0, 1).toUpperCase() + nachname.substring(1, nachname.length());

		} catch (Exception e) {
		}
	}

	public static boolean bereitseingelesen = false;
	/*
	 * liest mithilfe der anderen methoden die vorhandenen Arbeiter in der Json Datei aus.
	 */
	private List<Arbeiter> arbeiterEinlesen(File zielDatei) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.findAndRegisterModules();
		try (MappingIterator<Arbeiter> alleEintraegeDerJsonDatei = mapper.readerFor(Arbeiter.class)
				.readValues(zielDatei)) {
			if (!bereitseingelesen) {
				bereitseingelesen = true;
				while (alleEintraegeDerJsonDatei.hasNextValue()) {
					Arbeiter eineZeile = alleEintraegeDerJsonDatei.nextValue();
					zuDruckendeWerte.add(eineZeile);
					System.out.println("Hinzugefügt");
				}
			}
			return zuDruckendeWerte;
		}
	}
/*
 * Die methode nimmt das Passwort und Email 
 * der Anmeldung und schaut ob dieser Arbeiter existiert und gibt den Arbeiter zuruck wenn dieser existiert
 */
	public Arbeiter zuordnungDesEingeloggtenArbeiters(String email, String passwort) throws IOException {
		Set<File> rohDatei = leseDateiAusOrdner(new File(Person.getEinlesenUndSpeichern().dateiPfad));
		Iterator<File> iteratorDerRohDatei = rohDatei.iterator();
		File datei = iteratorDerRohDatei.next();
		List<Arbeiter> gemappteDatei = null;

		gemappteDatei = arbeiterEinlesen(datei);

		for (Arbeiter eintrag : gemappteDatei) {
			if (eintrag.email.equalsIgnoreCase(email) && eintrag.passwort.equals(passwort)) {
				System.out.println("Zugriff auf " + eintrag.email + " genehmigt");
				return eintrag;
			}
		}

		return null;
	}
/*
 * nimmt die email und passwort uberprueft ob dieser arbeiter existiert und aendert mit dem Generalpasswort das passwort des arbeiters
 */
	public Arbeiter zuordnungDesArbeitersBeiPasswortZuruecksetzen(String email, String passwort) throws IOException {
		Set<File> rohDatei = leseDateiAusOrdner(new File(Person.getEinlesenUndSpeichern().dateiPfad));
		Iterator<File> iteratorDerRohDatei = rohDatei.iterator();
		File datei = iteratorDerRohDatei.next();
		List<Arbeiter> gemappteDatei = null;

		gemappteDatei = arbeiterEinlesen(datei);

		for (Arbeiter eintrag : gemappteDatei) {
			if (eintrag.email.equalsIgnoreCase(email) && generalPasswort.equals(passwort)) {
				return eintrag;
			}
		}

		return null;
	}
/*
 * Speichert die aktuele Version vom arbeiter ab, indem der alte geloescht wird und der neue hinzugefuegt.
 */
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
			}
		}
		drucker();
	}
/*
 * Setzt alle attribute eines arbeiters mit den mitgegebenen Parametern und speichert anschliessend diesen ab
 */
	public static void arbeiterRegistrieren(String email, String passwort, LocalDate geburtstag, double wochenstunde,
			double warngrenze, boolean leiter) throws IOException {
		Arbeiter zuRegistrierenderArbeiter = new Arbeiter();

		zuRegistrierenderArbeiter.email = email;
		zuRegistrierenderArbeiter.passwort = passwort;
		zuRegistrierenderArbeiter.geburtstag = geburtstag;

		zuRegistrierenderArbeiter.wochenstunden = wochenstunde;
		zuRegistrierenderArbeiter.gleitzeitWarngrenze = warngrenze;
		zuRegistrierenderArbeiter.artDesAngestellten = leiter;
		Person.getEinlesenUndSpeichern().zuordnungDesEingeloggtenArbeiters(null, null);
		zuDruckendeWerte.add(zuRegistrierenderArbeiter);
		abspeichernVonAenderungen(null);
	}

/*
 * Druckt die neue JsonDatei in den selben Dateipfad der alten Datei umsomit die Datei zu ueberschreiben
 */
	private static void drucker() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.findAndRegisterModules();
		try {
			mapper.writeValue(new File(Person.getEinlesenUndSpeichern().dateiPfad + "/Daten.json"), zuDruckendeWerte);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}