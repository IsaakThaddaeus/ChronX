package ChronXProgramm;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Main {
	private static final String dateiPfadHardCoded = "src/main/resources/jsonfiles";

	public static void main(String[] args) throws IOException {
		EinlesenUndSpeichern parser = new EinlesenUndSpeichern(dateiPfadHardCoded);
		Arbeiter test = parser.zuordnungDesEingeloggtenArbeiters("justin.schick@bbqgmbh.de", "qwertz123");
		
		
		
		ZeitRechner zr = new ZeitRechner();
		zr.vergangeneTageInDatenBankAnlegen(test); // ignorieren ist noch in arbeit
		zr.zeiteintragFuerAktuellenTagHinzufuegen(test);
		
		List<LocalDateTime> anzupassendeListe = new ArrayList<LocalDateTime>();
		anzupassendeListe.add(zr.aktuelleZeit());
		anzupassendeListe.add(zr.aktuelleZeit());
		zr.gegebeneZeitenAnpasser(anzupassendeListe, test);
		parser.abspeichernVonAenderungen(test); // speichert den aktuellen Stand ab

		

	}

}
