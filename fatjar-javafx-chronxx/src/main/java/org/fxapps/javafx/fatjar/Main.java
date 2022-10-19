package org.fxapps.javafx.fatjar;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;



// This is the Main used by the shade plugin.
public class Main {
	private static final String dateiPfadHardCoded = "/org/fxapps/javafx/fatjar/Daten.json";

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


    	
    	System.out.println(zr.gibGleitzeitGesamt(test)); 
    	System.out.println(zr.gibGleitzeitJahr(test));
    	System.out.println(zr.gibGleitzeitQuartal(test));
    	System.out.println(zr.gibGleitzeitMonat(test));
    	LocalDate date = LocalDate.of(2022, 9, 21);
    	
    	System.out.println(zr.wurdePauseEingehalten(test, date));
    	
    		
        App.main(args);

    }
	


}

