package ChronXProgramm;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
//Hier nichts ver�ndern!!
public class EinlesenUndSpeichern {
	private String dateiPfad;
	private List<Arbeiter> zuDruckendeWerte = new ArrayList<>();
	Set<File> datein = new HashSet<>();

	public EinlesenUndSpeichern(String dateiPfad) {
		this.dateiPfad = dateiPfad;
	}

	private Set<File> leseDateiAusOrdner(File ordner) throws IOException {
		System.out.println(ordner);
		for (final File datei : ordner.listFiles()) {
			if (datei.getName().contains(".json")) {

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


	private List<Arbeiter> arbeiterEinlesen(File zielDatei) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.findAndRegisterModules();
		try (MappingIterator<Arbeiter> alleEinträgeDerJsonDatei = mapper.readerFor(Arbeiter.class)
				.readValues(zielDatei)) {

			while (alleEinträgeDerJsonDatei.hasNextValue()) {
				Arbeiter eineZeile = alleEinträgeDerJsonDatei.nextValue();
				zuDruckendeWerte.add(eineZeile);
			}
			return zuDruckendeWerte;
		}
	}

	public Arbeiter zuordnungDesEingeloggtenArbeiters(String email, String passwort) throws IOException {
		Set<File> rohDatei = leseDateiAusOrdner(new File(dateiPfad));
		Iterator<File> iteratorDerRohDatei = rohDatei.iterator();
		File datei = iteratorDerRohDatei.next();
		List<Arbeiter> gemappteDatei = arbeiterEinlesen(datei);

		for (Arbeiter eintrag : gemappteDatei) {
			if (eintrag.email.equalsIgnoreCase(email) && eintrag.passwort.equals(passwort)) {
				System.out.println("Zugriff auf " + eintrag.email + " genehmigt");
				return eintrag;
			}
		}
		System.out.println("Falsche Login Daten");
		return null;
	}

	public void abspeichernVonAenderungen(Arbeiter arbeiter) throws NullPointerException {
		for (Arbeiter eintrag : zuDruckendeWerte) {
			try {
				if (eintrag.email == arbeiter.email) {
					zuDruckendeWerte.remove(eintrag);
					zuDruckendeWerte.add(arbeiter);
					break;
				}
			} catch (Exception e) {
				System.out.println("Arbeiter ist Fehlerhaft");
			}
		}
		drucker();
	}

	private void drucker() {
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