package ChronXProgramm;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;

public class Main {
	private static final String dateiPfadHardCoded = "src/main/resources/jsonfiles";
//Kannst hier �ndern was du willst
	public static void main(String[] args) throws IOException {
		EinlesenUndSpeichern parser = new EinlesenUndSpeichern(dateiPfadHardCoded);
		Arbeiter test = parser.zuordnungDesEingeloggtenArbeiters("justin.schick@bbqgmbh.de", "qwertz123");
		
		ZeitRechner zr = new ZeitRechner();
		//zr.aktuellenTagZeitHinzufuegen(test);//f�gt bei jeder ausf�hrung die aktuelleUhrzeit als NEUEN Tag hinzu. Solltest du wahrscheinlich auskommentiert lassen
		//zr.zeitDesTagesBerechnen(test, 1); //macht noch nichts
		//zr.aktuellenTagInDatenBankAnlegen(test); //ignorieren ist noch in arbeit 
		test.zeit.zeitarbeitsTag.get(0).get(0).add(zr.aktuelleZeit());
		parser.abspeichernVonAenderungen(test); //speichert den aktuellen Stand ab
//		konsolenausgabe(test); //gibt dir alle Infos f�r den Arbeiter und alle seine Arbeitstage
		
		System.out.println(test.zeit.zeitarbeitsTag.get(0).get(4).get(0));
		System.out.println(zr.inIndustrie(test.zeit.zeitarbeitsTag.get(0).get(4).get(1)));
		System.out.println(zr.gibZeitIntervall(test.zeit.zeitarbeitsTag.get(0).get(4).get(0), test.zeit.zeitarbeitsTag.get(0).get(4).get(1)));
		System.out.println(test.zeit.zeitarbeitsTag.get(0).get(4).size());
	
		System.out.println(zr.wurdePauseEingehalten(test, 0, 4));
		

	}

	private static void konsolenausgabe(Arbeiter arbeiterFuerAusgabe) {
		System.out.println("**NeuerEintragsAnfang**" + "\n" + "Email:" + arbeiterFuerAusgabe.email + "\nPasswort:"
				+ arbeiterFuerAusgabe.passwort + "\nLeitenderAngestellter:" + arbeiterFuerAusgabe.artDesAngestellten
				+ "\nSprache:" + arbeiterFuerAusgabe.sprache + "\nAktuelle Gleitzeit:"
				+ arbeiterFuerAusgabe.zeit.aktuelleGleitzeit + "\nWochenstunden:"
				+ arbeiterFuerAusgabe.zeit.wochenstunden + "\nGleitzeit Warngrenze:"
				+ arbeiterFuerAusgabe.zeit.gleitzeitWarngrenze +"\n");
		int l=-1,o=-1,u=-1;
		for(int i = 0; i< arbeiterFuerAusgabe.zeit.zeitarbeitsTag.size(); i++) {
			u++;
			for(int j=0;j< arbeiterFuerAusgabe.zeit.zeitarbeitsTag.get(i).size();j++) {
				o++;
				for(int k=0;k< arbeiterFuerAusgabe.zeit.zeitarbeitsTag.get(i).get(j).size()-1;k++) {
					
					l++;
					System.out.println("Jahr"+u+" Monat"+o+" Tag"+l);
					System.out.println(l+" : "+arbeiterFuerAusgabe.zeit.zeitarbeitsTag.get(i).get(j).get(k));

				}
			}
		}
	}
}
