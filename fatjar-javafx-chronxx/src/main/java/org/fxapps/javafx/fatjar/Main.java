package org.fxapps.javafx.fatjar;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

// This is the Main used by the shade plugin.
public class Main {
//	private static final String dateiPfadHardCoded = "/org/fxapps/javafx/fatjar/Daten.json";

	public static void main(String[] args) throws IOException, URISyntaxException {
//		String dateiPfadVonJar = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI())
//				.getPath();
//		String dateiPfadJsonFile = dateiPfadVonJar.substring(0, dateiPfadVonJar.lastIndexOf("\\")) + "\\jsonfile";
//		System.out.println(dateiPfadJsonFile);

		
//		EinlesenUndSpeichern parser = new EinlesenUndSpeichern();
//		Arbeiter test = parser.zuordnungDesEingeloggtenArbeiters("justin.schick@bbqgmbh.de", "qwertz123");
//
//		ZeitRechner zr = new ZeitRechner();
//		zr.vergangeneTageInDatenBankAnlegen(test); // ignorieren ist noch in arbeit
//		zr.zeiteintragFuerAktuellenTagHinzufuegen(test);
//
//		List<LocalDateTime> anzupassendeListe = new ArrayList<LocalDateTime>();
//		anzupassendeListe.add(zr.aktuelleZeit());
//		anzupassendeListe.add(zr.aktuelleZeit());
//		zr.gegebeneZeitenAnpasser(anzupassendeListe, test);
//		EinlesenUndSpeichern.abspeichernVonAenderungen(test); // speichert den aktuellen Stand ab

//		System.out.println(zr.gibGleitzeitGesamt(test));
//		System.out.println(zr.gibGleitzeitJahr(test));
//		System.out.println(zr.gibGleitzeitQuartal(test));
//		System.out.println(zr.gibGleitzeitMonat(test));
//		LocalDate date = LocalDate.of(2022, 9, 21);
//
//		System.out.println(zr.wurdePauseEingehalten(test, date));
//
//		
//		List<LocalDateTime> urlaubsUndKrankheitsTage = new ArrayList<LocalDateTime>();
		
		App.main(args);

	}

}
