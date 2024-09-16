package ChronXProgramm;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class ZeitRechner {

	public LocalDateTime aktuelleZeit() { //muss so bleiben f�r meinen Code der in arbeit ist
		LocalDateTime date = LocalDateTime.now();
		date = date.withSecond(0);
		date = date.withNano(0);

		return date;

	}

	public Arbeiter aktuellenTagZeitHinzufuegen(Arbeiter aktuellerArbeiter) { //nicht mehr aktuell kann f�r Zeiterfassung genutzt oder ignoriert werden
		List<List<LocalDateTime>> zeitarbeitsTag = new ArrayList<List<LocalDateTime>>();
		if (aktuellerArbeiter.zeit.zeitarbeitsTag.size() == 0) {
			zeitarbeitsTag.get(0).add(aktuelleZeit());
			aktuellerArbeiter.zeit.zeitarbeitsTag.add(zeitarbeitsTag);
		} else {
			aktuellerArbeiter.zeit.zeitarbeitsTag.get(aktuellerArbeiter.zeit.zeitarbeitsTag.size() - 1).get(0)
					.add(aktuelleZeit());
		}
		return aktuellerArbeiter;
	}

	public Arbeiter naechstenTagUndErsteZeitHinzufuegen(Arbeiter aktuellerArbeiter) { //nicht mehr aktuell kann f�r Zeiterfassung genutzt oder ignoriert werden
		List<List<LocalDateTime>> zeitarbeitsTag = new ArrayList<List<LocalDateTime>>();
		aktuellerArbeiter.zeit.zeitarbeitsTag.add(zeitarbeitsTag);
		aktuellenTagZeitHinzufuegen(aktuellerArbeiter);
		return aktuellerArbeiter;
	}

	public Arbeiter aktuellenTagInDatenBankAnlegen(Arbeiter arbeiter) { //ignorieren ist noch in arbeit!!
		LocalDateTime aktuellerTag = aktuelleZeit().plusDays(6);
		arbeiter.zeit.zeitarbeitsTag.size();
		int letzterMonat =arbeiter.zeit.zeitarbeitsTag.size()-1;
		int letzterTag =arbeiter.zeit.zeitarbeitsTag.get(arbeiter.zeit.zeitarbeitsTag.size()-1).size()-1;
		int letzterZeiteintrag = arbeiter.zeit.zeitarbeitsTag.get(arbeiter.zeit.zeitarbeitsTag.size()-1).get(arbeiter.zeit.zeitarbeitsTag.get(arbeiter.zeit.zeitarbeitsTag.size()-1).size()-1).size()-1;
	LocalDateTime letzterEingetragenerTag = arbeiter.zeit.zeitarbeitsTag.get(letzterMonat).get(letzterTag).get(letzterZeiteintrag);
	int unterschiedMonat = aktuellerTag.getMonthValue()-letzterEingetragenerTag.getMonthValue();
	int unterschiedTag = aktuellerTag.getDayOfMonth()-letzterEingetragenerTag.getDayOfMonth();
	 LocalDate datum = LocalDate.of(letzterEingetragenerTag.getYear(), letzterEingetragenerTag.getMonthValue(), letzterEingetragenerTag.getDayOfMonth());
	 if(unterschiedTag>0) {
		for(int i=1; i<unterschiedTag; i++) {
			for(int k=0; k<arbeiter.zeit.urlaubsUndKrankheitsTage.size(); k++) {
				if(arbeiter.zeit.urlaubsUndKrankheitsTage.get(k).equals(datum.plusDays(i))) {
					arbeiter.zeit.zeitarbeitsTag.get(letzterMonat).add(new ArrayList<LocalDateTime>());
					arbeiter.zeit.zeitarbeitsTag.get(letzterMonat).get(letzterTag+i).add(LocalDateTime.of(datum.getYear(), datum.getMonthValue(), datum.plusDays(i).getDayOfMonth(), 0, 0));
				}
			}
		}
	 }
		return arbeiter;
	}
	public List<LocalDateTime> monatsZuschreiber(int monatAnzahlTage){ //ignorieren ist noch in arbeit
		List<LocalDateTime> zeitarbeitsTag = new ArrayList<LocalDateTime>();
		for(int i=0; i<monatAnzahlTage;i++) {
			zeitarbeitsTag.add(null);
		}
		return zeitarbeitsTag;
	}
	public int anzahlTageImMonat(LocalDate datum){ //ignorieren ist noch in arbeit
		int[] monate = {31,0,31,30,31,30,31,31,30,31,30,31};

		 if(!datum.isLeapYear()&&datum.getMonthValue()==2){
			return 28;
		}else if(datum.isLeapYear()&&datum.getMonthValue()==2){
			return 29;
		}else {
			return monate[datum.getMonthValue()];
		}
	}

	public double zeitDesTagesBerechnen(Arbeiter aktuellerArbeiter, int index) { //nicht mehr aktuell kann f�r Zeiterfassung genutzt oder ignoriert werden
		if (aktuellerArbeiter.zeit.zeitarbeitsTag.size() <= index) {
			return 0;
		}
		List<List<LocalDateTime>> listeMitZeitEintraegen = aktuellerArbeiter.zeit.zeitarbeitsTag.get(index);
		if (listeMitZeitEintraegen.size() % 2 == 0) {
			LocalDateTime vorherigerEintrag = listeMitZeitEintraegen.get(0).get(0);
			for (int i = 1; i < listeMitZeitEintraegen.size(); i = i + 2) {
				zeitEintragZusammenrechner(vorherigerEintrag, listeMitZeitEintraegen.get(i));
				vorherigerEintrag = listeMitZeitEintraegen.get(0).get(i);
			}
		}
		return 0;

	}

	public int zeitEintragZusammenrechner(LocalDateTime vorherigerEintrag, List<LocalDateTime> list) {//nicht mehr aktuell kann f�r Zeiterfassung genutzt oder ignoriert werden
		System.out.println("Eintrag Eins: " + vorherigerEintrag + "  eintragZwei: " + list);
		return 2;
	}
	
	public boolean wurdePauseEingehaltenalt(Arbeiter MitArbeiter, int monat, int tag)
	{
		if(MitArbeiter.artDesAngestellten == 1)
		{
			//return true;
		}
		
		
		double gesamtArbeitsZeit = 0;
		double gesamtPauseZeit = 0;
		
		int zeitArbeitsTagLaenge = MitArbeiter.zeit.zeitarbeitsTag.get(monat).get(tag).size();
		List<LocalDateTime> zeitArbeitsTag = MitArbeiter.zeit.zeitarbeitsTag.get(monat).get(tag);
		
		for(int i = 0; i < zeitArbeitsTagLaenge; i+=2)
		{
			double arbeitsIntervall = gibZeitIntervall(zeitArbeitsTag.get(i), zeitArbeitsTag.get(i+1));
			
			if(arbeitsIntervall > 6){
				return false;
			}
			gesamtArbeitsZeit += arbeitsIntervall;
		}
		
		if(gesamtArbeitsZeit <= 6)
		{
			return true;
		}
		
		for(int i = 0; i < zeitArbeitsTagLaenge; i+=2) {
			if((i+2) < zeitArbeitsTagLaenge ) {
				double pauseIntervall = gibZeitIntervall(zeitArbeitsTag.get(i+1), zeitArbeitsTag.get(i+2));
				if(pauseIntervall < 0.25f) {
					return false;
				}
				gesamtPauseZeit += pauseIntervall;
			}
		}
		
		
		System.out.println(gesamtArbeitsZeit + "  " + gesamtPauseZeit);
		
		if(gesamtArbeitsZeit > 9)
		{
			if(gesamtPauseZeit < 0.75) {
				return false;
			}
			return true;
		}
		
		if(gesamtArbeitsZeit > 6) {
			if(gesamtPauseZeit < 0.5) {
				return false;
			}
		    return true;
		}
		
		return true;
	}
	
	
	public boolean wurdePauseEingehalten(Arbeiter MitArbeiter, int monat, int tag)
	{
		if(MitArbeiter.artDesAngestellten == 1)
		{
			return true;
		}
		
		List<LocalDateTime> zeitArbeitsTag = MitArbeiter.zeit.zeitarbeitsTag.get(monat).get(tag);
	
		LocalDate Geburtstag = MitArbeiter.geburtstag;
		LocalDate heute = LocalDate.now();
	
		
		if(Period.between(Geburtstag, heute).getYears() < 18)
		{
			return pausenRechner(MitArbeiter, zeitArbeitsTag, 4.5f , 0.5f , 6f, 1f);
		}
 
		else
		{
			return pausenRechner(MitArbeiter, zeitArbeitsTag, 6f , 0.5f , 9f, 0.75f);
		}
	}
	
	private boolean pausenRechner(Arbeiter MitArbeiter, List<LocalDateTime> zeitArbeitsTag,float unterbrechungsfrei, float pause1, float obergrenze, float pause2)
	{
		double gesamtArbeitsZeit = 0;
		double gesamtPauseZeit = 0;
		
		int zeitArbeitsTagLaenge = zeitArbeitsTag.size();
	
		
		for(int i = 0; i < zeitArbeitsTagLaenge; i+=2)
		{
			double arbeitsIntervall = gibZeitIntervall(zeitArbeitsTag.get(i), zeitArbeitsTag.get(i+1));
			
			if(arbeitsIntervall > unterbrechungsfrei){
				return false;
			}
			gesamtArbeitsZeit += arbeitsIntervall;
		}
		
		
		if(gesamtArbeitsZeit <= unterbrechungsfrei)
		{
			return true;
		}
		
		
		
		for(int i = 0; i < zeitArbeitsTagLaenge; i+=2) {
			if((i+2) < zeitArbeitsTagLaenge ) {
				double pauseIntervall = gibZeitIntervall(zeitArbeitsTag.get(i+1), zeitArbeitsTag.get(i+2));
				if(pauseIntervall < 0.25f) {
					return false;
				}
				gesamtPauseZeit += pauseIntervall;
			}
		}
		
		
		System.out.println(gesamtArbeitsZeit + "  " + gesamtPauseZeit);
		
		if(gesamtArbeitsZeit > obergrenze)
		{
			if(gesamtPauseZeit < pause2) {
				return false;
			}
			return true;
		}
		
		if(gesamtArbeitsZeit > unterbrechungsfrei) {
			if(gesamtPauseZeit < pause1) {
				return false;
			}
		    return true;
		}
		
		return true;
	}
	
	public double inIndustrie(LocalDateTime zeit)
	{
		double ausgabe = zeit.getHour();
		double minute = zeit.getMinute();
	    ausgabe += minute/60;
		
		return ausgabe;
	}
	
	public double gibZeitIntervall(LocalDateTime Zeitpunkt1, LocalDateTime Zeitpunkt2)
	{
		return inIndustrie(Zeitpunkt2) - inIndustrie(Zeitpunkt1);
	}

}
