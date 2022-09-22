package ChronXProgramm;

import java.io.IOException;

public class Main {
	private static final String dateiPfadHardCoded = "src/main/resources/jsonfiles";

	public static void main(String[] args) throws IOException {
		EinlesenUndSpeichern parser = new EinlesenUndSpeichern(dateiPfadHardCoded);
		Arbeiter test = parser.zuordnungDesEingeloggtenArbeiters("justin.schick@bbqgmbh.de", "qwertz123");
		
		ZeitRechner zr = new ZeitRechner();
		zr.aktuellenTagZeitHinzufuegen(test);
		zr.zeitDesTagesBerechnen(test, 1);
		
		parser.abspeichernVonAenderungen(test);
		konsolenausgabe(test);
	}

	private static void konsolenausgabe(Arbeiter arbeiterFuerAusgabe) {
		System.out.println("**NeuerEintragsAnfang**" + "\n" + "Email:" + arbeiterFuerAusgabe.email + "\nPasswort:"
				+ arbeiterFuerAusgabe.passwort + "\nLeitenderAngestellter:" + arbeiterFuerAusgabe.artDesAngestellten
				+ "\nSprache:" + arbeiterFuerAusgabe.sprache + "\nAktuelle Gleitzeit:"
				+ arbeiterFuerAusgabe.zeit.aktuelleGleitzeit + "\nWochenstunden:"
				+ arbeiterFuerAusgabe.zeit.wochenstunden + "\nGleitzeit Warngrenze:"
				+ arbeiterFuerAusgabe.zeit.gleitzeitWarngrenze + "\nZeit Arbeitstag:"
				+ arbeiterFuerAusgabe.zeit.zeitarbeitsTag.get(0).get(0).getMinute() + "\nMonatszeit:" + arbeiterFuerAusgabe.zeit.gleitzeit[1][1][1]
				+ "\n");
	}
}
