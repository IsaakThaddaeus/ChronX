package ChronXProgramm;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ZeitRechner {
	
	public LocalDateTime aktuelleZeit() {
		LocalDateTime date = LocalDateTime.now();
	date=	date.withSecond(0);
	date=	date.withNano(0);

		return date;

	}
	public Arbeiter aktuellenTagZeitHinzufuegen(Arbeiter aktuellerArbeiter) {
		List<LocalDateTime> zeitarbeitsTag = new ArrayList<LocalDateTime>();
		if(aktuellerArbeiter.zeit.zeitarbeitsTag.size()==0) {
			zeitarbeitsTag.add(aktuelleZeit());
			aktuellerArbeiter.zeit.zeitarbeitsTag.add(zeitarbeitsTag);
		}else {
			aktuellerArbeiter.zeit.zeitarbeitsTag.get(aktuellerArbeiter.zeit.zeitarbeitsTag.size() - 1).add(aktuelleZeit());
		}
		return aktuellerArbeiter;
	}

	public Arbeiter naechstenTagUndErsteZeitHinzufuegen(Arbeiter aktuellerArbeiter) {
		List<LocalDateTime> zeitarbeitsTag = new ArrayList<LocalDateTime>();
		aktuellerArbeiter.zeit.zeitarbeitsTag.add(zeitarbeitsTag);
		aktuellenTagZeitHinzufuegen(aktuellerArbeiter);
		return aktuellerArbeiter;
	}

	public double zeitDesTagesBerechnen(Arbeiter aktuellerArbeiter, int index) {
		if( aktuellerArbeiter.zeit.zeitarbeitsTag.size()<=index) {
			return 0;
		}
		List<LocalDateTime> listeMitZeitEintraegen = aktuellerArbeiter.zeit.zeitarbeitsTag.get(index);
		if (listeMitZeitEintraegen.size()% 2 == 0) {
			LocalDateTime vorherigerEintrag=listeMitZeitEintraegen.get(0);
			for(int i=1;i<listeMitZeitEintraegen.size();i=i+2 ){
				zeitEintragZusammenrechner(vorherigerEintrag,listeMitZeitEintraegen.get(i));
				vorherigerEintrag=listeMitZeitEintraegen.get(i);
			}
		}
		return 0;

	}

	public int zeitEintragZusammenrechner(LocalDateTime vorherigerEintrag,LocalDateTime localDateTime) {
		System.out.println("Eintrag Eins: "+vorherigerEintrag+"  eintragZwei: "+ localDateTime);
		return 2;
	}
	
		
	}
	

