package ChronXProgramm;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ZeitRechner {

	public LocalDateTime aktuelleZeit() { //muss so bleiben für meinen Code der in arbeit ist
		LocalDateTime date = LocalDateTime.now();
		date = date.withSecond(0);
		date = date.withNano(0);

		return date;

	}

	public Arbeiter aktuellenTagZeitHinzufuegen(Arbeiter aktuellerArbeiter) { //nicht mehr aktuell kann für Zeiterfassung genutzt oder ignoriert werden
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

	public Arbeiter naechstenTagUndErsteZeitHinzufuegen(Arbeiter aktuellerArbeiter) { //nicht mehr aktuell kann für Zeiterfassung genutzt oder ignoriert werden
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

	public double zeitDesTagesBerechnen(Arbeiter aktuellerArbeiter, int index) { //nicht mehr aktuell kann für Zeiterfassung genutzt oder ignoriert werden
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

	public int zeitEintragZusammenrechner(LocalDateTime vorherigerEintrag, List<LocalDateTime> list) {//nicht mehr aktuell kann für Zeiterfassung genutzt oder ignoriert werden
		System.out.println("Eintrag Eins: " + vorherigerEintrag + "  eintragZwei: " + list);
		return 2;
	}

}
