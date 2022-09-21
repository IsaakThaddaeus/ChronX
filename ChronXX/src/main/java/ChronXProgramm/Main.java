package ChronXProgramm;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;


public class Main {
	private static final String dateiPfadHardCoded = "src/main/resources/jsonfiles";
	
	public static void main(String[] args) throws IOException {
		EinlesenUndSpeichern parser = new EinlesenUndSpeichern(dateiPfadHardCoded);
		Arbeiter test =parser.zuordnungDesEingeloggtenArbeiters("jan.Holzhausen@bbqgmbh.de","123qwertz");
		parser.abspeichernVonAenderungen(test);


	}

	private static void konsolenausgabe(List<Arbeiter> gemappteDatei) {
		for (Arbeiter eintrag : gemappteDatei) {
			
			 LocalDateTime date = LocalDateTime.now();
				String aktuelleZeit = date.getYear()+"."+date.getMonthValue()+"."+date.getDayOfMonth()+"-"+date.getHour()+":"+date.getMinute();
				 System.out.println(aktuelleZeit);
				 eintrag.zeit.zeitarbeitsTag.get(1).add(aktuelleZeit);
				 if(	 eintrag.zeit.zeitarbeitsTag.size()%2==0) {
						System.out.println("Passender Arbeitsabschnitt: "+eintrag.zeit.zeitarbeitsTag.size());
					}
					 if( eintrag.zeit.zeitarbeitsTag.get(0).size()>=4) {
						 System.out.println("Davor"+eintrag.zeit.zeitarbeitsTag.size());
						 eintrag.zeit.zeitarbeitsTag.get(0).removeAll(eintrag.zeit.zeitarbeitsTag.get(0));
						 System.out.println("Danach"+eintrag.zeit.zeitarbeitsTag.size());
					 }
			System.out.println("**NeuerEintragsAnfang**" + "\n" + "Email:" + eintrag.email + "\nPasswort:" + eintrag.passwort
					+ "\nLeitenderAngestellter:" + eintrag.artDesAngestellten + "\nSprache:" + eintrag.sprache
					+ "\nAktuelle Gleitzeit:" + eintrag.zeit.aktuelleGleitzeit + "\nWochenstunden:"
					+ eintrag.zeit.wochenstunden + "\nGleitzeit Warngrenze:" + eintrag.zeit.gleitzeitWarngrenze
					+ "\nZeit Arbeitstag:" + eintrag.zeit.zeitarbeitsTag + "\nMonatszeit:" + eintrag.zeit.monatsZeit[0]+"\n");
			
			
		
		}
	}

	public static void bearbeite(List<Arbeiter> zubearbeitendeDatei) {
		System.out.println("**********");
		for (Arbeiter eintrag : zubearbeitendeDatei) {
			System.out.println(eintrag.email + " " + eintrag.zeit.aktuelleGleitzeit);

			if (eintrag.email.contains("jan.holzhausen@bbqgmbh.de")) { // Später hier benutzername abfragen
				eintrag.zeit.aktuelleGleitzeit += 4; // test
//				eintrag.zeit.zeitarbeitsTag
			}
		}
		System.out.println("**********");
		drucker(zubearbeitendeDatei);
	}

	public static void drucker(List<Arbeiter> zudruckendeDatei) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(new File(dateiPfadHardCoded+"/Daten.json"), zudruckendeDatei);

		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("-Finished-");
	}
}
