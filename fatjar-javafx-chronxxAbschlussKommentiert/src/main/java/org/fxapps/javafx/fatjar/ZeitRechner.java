package org.fxapps.javafx.fatjar;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.time.temporal.IsoFields;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import de.jollyday.Holiday;
import de.jollyday.HolidayCalendar;
import de.jollyday.HolidayManager;
import de.jollyday.HolidayType;
import de.jollyday.ManagerParameters;

public class ZeitRechner {
	/*
	 * Wird genutzt um die jetzige Uhrzeit zurueckzugeben um damit rechnen zu
	 * koennen
	 * 
	 * Gibt LocalDateTime zurueck mit Sekunden und Millisekunden 0, da diese nicht
	 * relevant sind. Spaeter werden Sekunden gesetzt um damit zu symbolisieren das
	 * ein Besoderer Tag ist, wie zum Beispiel Gleitzeit oder Feiertag
	 */
	public LocalDateTime aktuelleZeit() { // Gibt aktuelle Zeit ohne Sekunden und Millisekunden
		LocalDateTime date = LocalDateTime.now().plusDays(0); // Fuer testzwecke koennen Tage hizugefuegt werden.
		date = date.withSecond(0);
		date = date.withNano(0);
		return date;

	}

	/*
	 * Wird benutzt um mit der Bibiliothek Jollydays die Badenwuettembergischen
	 * Feiertage zu erfassen. Ein Tag der ueberprueft werden soll ob dieser ein
	 * Feiertag oder Sonntag ist wird mitgegeben Gibt zurueck ob der Tag ein
	 * Feiertag oder ein Sonntag ist
	 */
	public boolean feiertagUndSonntagsUeberpruefer(LocalDate zuTestenderTag) {
		// Ueberprueft den zu tentenden Tag ob es sich um einen Feier- oder Sonntag
		// handelt.

		if (zuTestenderTag.getDayOfWeek() == DayOfWeek.SUNDAY) {
			return true;

		} else if (!(zuTestenderTag.getDayOfWeek() == DayOfWeek.SUNDAY)) {
			final HolidayManager holidayManager = HolidayManager
					.getInstance(ManagerParameters.create(HolidayCalendar.GERMANY));
			Set<Holiday> holidays = holidayManager.getHolidays(zuTestenderTag.getYear(), "bw");
			holidays.add(new Holiday(LocalDate.of(zuTestenderTag.getYear(), 12, 24), "Heiligabend",
					HolidayType.OFFICIAL_HOLIDAY));
			holidays.add(new Holiday(LocalDate.of(zuTestenderTag.getYear(), 12, 31), "Silvester",
					HolidayType.OFFICIAL_HOLIDAY));
			Iterator<Holiday> iterate_value = holidays.iterator();
			while (iterate_value.hasNext()) {
				if (zuTestenderTag.equals(iterate_value.next().getDate())) {
					return true;
				}
			}
		}
		return false;
	}

	/*
	 * Fuegt dem aktuellen Tag einen Zeitsatz hinzu, wo kuenftig weitere Eintraege
	 * fuer den aktuellen Tag eingetragen werden. Jenachdem ob schon Tage existieren
	 * wird ein Neuer Tag in der Json hinzugefuegt. Ein Mitarbeiter wird mitgegeben
	 * bei welcher der Tag hinzugefuegt wird. Der Mitarbeiter wird dann wieder mit
	 * den neuen Eintraegen zurueckgegeben
	 */
	public Arbeiter zeiteintragFuerAktuellenTagHinzufuegen(Arbeiter aktuellerArbeiter) {

		if (aktuellerArbeiter.zeitarbeitsTag.size() == 0) {

			List<LocalDateTime> liste = new ArrayList<LocalDateTime>();
			liste.add(aktuelleZeit());
			aktuellerArbeiter.zeitarbeitsTag.add(liste);
			return aktuellerArbeiter;

		}
		if (aktuellerArbeiter.zeitarbeitsTag.get(aktuellerArbeiter.zeitarbeitsTag.size() - 1).size() == 0) {
			aktuellerArbeiter.zeitarbeitsTag.get(aktuellerArbeiter.zeitarbeitsTag.size() - 1).add(aktuelleZeit());
			return aktuellerArbeiter;
		} else if (aktuellerArbeiter.zeitarbeitsTag.get(aktuellerArbeiter.zeitarbeitsTag.size() - 1)
				.get(aktuellerArbeiter.zeitarbeitsTag.get(aktuellerArbeiter.zeitarbeitsTag.size() - 1).size() - 1)
				.toLocalDate().equals(aktuelleZeit().toLocalDate())) {
			aktuellerArbeiter.zeitarbeitsTag.get(aktuellerArbeiter.zeitarbeitsTag.size() - 1).add(aktuelleZeit());
			return aktuellerArbeiter;
		} else {
			List<LocalDateTime> zeitarbeitsTag = new ArrayList<LocalDateTime>();
			if (aktuellerArbeiter.zeitarbeitsTag.get(aktuellerArbeiter.zeitarbeitsTag.size() - 1).size() % 2 != 0
					&& aktuellerArbeiter.zeitarbeitsTag.get(aktuellerArbeiter.zeitarbeitsTag.size() - 1).get(
							aktuellerArbeiter.zeitarbeitsTag.get(aktuellerArbeiter.zeitarbeitsTag.size() - 1).size()
									- 1)
							.getSecond() == 0) {
				aktuellerArbeiter.zeitarbeitsTag.get(aktuellerArbeiter.zeitarbeitsTag.size() - 1).add(aktuelleZeit());
				aktuellerArbeiter.zeitarbeitsTag.add(zeitarbeitsTag);
				return aktuellerArbeiter;
			}
			aktuellerArbeiter.zeitarbeitsTag.add(zeitarbeitsTag);
			aktuellerArbeiter.zeitarbeitsTag.get(aktuellerArbeiter.zeitarbeitsTag.size() - 1).add(aktuelleZeit());
			return aktuellerArbeiter;

		}

	}

	/*
	 * Dient der aktualisierung der Datenbank bei nichtnutzung der Stechuhr. So wird
	 * abgeglichen ob die vergangenen Tage Feier-/ Sonn-/ Urlaubs- oder
	 * Krankheitstage waren. Falls nichts davon zutrifft wird von einem Gleitzeittag
	 * ausgegangen.
	 */
	public Arbeiter vergangeneTageInDatenBankAnlegen(Arbeiter arbeiter) {

		if (arbeiter.zeitarbeitsTag.size() == 0) {
			return null;
		}
		LocalDateTime aktuellerTag = aktuelleZeit();
		int letzertag = arbeiter.zeitarbeitsTag.size() - 1;
		int letzteZeit = arbeiter.zeitarbeitsTag.get(arbeiter.zeitarbeitsTag.size() - 1).size() - 1;

		LocalDateTime letzterEingetragenerTag = arbeiter.zeitarbeitsTag.get(letzertag).get(letzteZeit);

		long unterschiedTage = ChronoUnit.DAYS.between(letzterEingetragenerTag, aktuellerTag.withHour(0).withMinute(0))
				+ 1;

		for (int i = 1; i < unterschiedTage; i++) {
			LocalDateTime aktuellerVergangenerTag = letzterEingetragenerTag.plusDays(i).withHour(0).withMinute(0);
			if (feiertagUndSonntagsUeberpruefer(aktuellerVergangenerTag.toLocalDate())) {
				System.out.println("Feiern " + aktuellerVergangenerTag);
				arbeiter.zeitarbeitsTag.add(new ArrayList<LocalDateTime>());
				arbeiter.zeitarbeitsTag.get(arbeiter.zeitarbeitsTag.size() - 1)
						.add(aktuellerVergangenerTag.withSecond(3));

			} else if (aufUrlaubsKrankheitsliste(aktuellerVergangenerTag.toLocalDate(), arbeiter)) {
				System.out.println("UrlaubKrank " + aktuellerVergangenerTag);
				arbeiter.zeitarbeitsTag.add(new ArrayList<LocalDateTime>());
				arbeiter.zeitarbeitsTag.get(arbeiter.zeitarbeitsTag.size() - 1)
						.add(aktuellerVergangenerTag.withSecond(2));
			} else {
				arbeiter.zeitarbeitsTag.add(new ArrayList<LocalDateTime>());
				arbeiter.zeitarbeitsTag.get(arbeiter.zeitarbeitsTag.size() - 1)
						.add(aktuellerVergangenerTag.withSecond(1));
			}

		}
		return arbeiter;
	}

	/*
	 * Dem Mitarbeiter werden auf seiner UrlaubsKrankheitsListe alle seine Eintraege
	 * entfernt und durch die neue Liste ersetzt
	 */
	public void urlaubsKrankheitsListeHizufuegen(List<LocalDate> listeDerEintraege, Arbeiter arbeiter) {
		System.out.println("Methode: urlaubsKrankheits Hinzufüger");
		arbeiter.urlaubsUndKrankheitsTage.clear();
		arbeiter.urlaubsUndKrankheitsTage.addAll(listeDerEintraege);
		EinlesenUndSpeichern.abspeichernVonAenderungen(arbeiter);

	}

	/*
	 * Testet ob ein gegebener Tag auf der Urlaubskrankheitsliste der Person
	 * vorhanden ist, und gibt ein True zurueck falls der Tag exisitert
	 */
	public boolean aufUrlaubsKrankheitsliste(LocalDate zuTestenderTag, Arbeiter arbeiter) {
		// Iteriert ueber die gegebene Urlaubs- bzw Krankheitsliste im Json.
		for (int i = 0; i < arbeiter.urlaubsUndKrankheitsTage.size(); i++) {
			if (arbeiter.urlaubsUndKrankheitsTage.get(i).equals(zuTestenderTag)) {
				return true;
			}
		}
		return false;
	}

	/*
	 * Nimmt einen Arbeiter und ein zutestenden Tag bei welchem die gesamte
	 * Zeiteintragsliste zuruckgegeben wird.
	 */
	public List<LocalDateTime> eintraegeFuerBeliebigenTagAufrufen(LocalDate date, Arbeiter arbeiter) {
		for (int i = 0; i < arbeiter.zeitarbeitsTag.size(); i++) {

			if (arbeiter.zeitarbeitsTag.get(i).get(0).toLocalDate().equals(date)) {
				return arbeiter.zeitarbeitsTag.get(i);
			}
		}

		return null;
	}

	/*
	 * Gibt zurueck ob der mitgegebene Mitarbeiter im Letzten Zeitintervall eine
	 * Gerade oder ungerade Anzahl an eintraegen besitzt bei gerader Menge wird Null
	 * zurueck gegeben bei ungerader Menge der letzte Eintrag.
	 */
	public LocalDateTime aktuellAmZeitErfassen(Arbeiter arbeiter) {
		if (arbeiter.zeitarbeitsTag.size() == 0) {
			return null;
		}
		System.out.println(arbeiter.zeitarbeitsTag.get(arbeiter.zeitarbeitsTag.size() - 1)
				.get(arbeiter.zeitarbeitsTag.get(arbeiter.zeitarbeitsTag.size() - 1).size() - 1).getSecond() == 0);
		if (arbeiter.zeitarbeitsTag.get(arbeiter.zeitarbeitsTag.size() - 1).size() % 2 != 0 && arbeiter.zeitarbeitsTag
				.get(arbeiter.zeitarbeitsTag.size() - 1)
				.get(arbeiter.zeitarbeitsTag.get(arbeiter.zeitarbeitsTag.size() - 1).size() - 1).getSecond() == 0) {
			return arbeiter.zeitarbeitsTag.get(arbeiter.zeitarbeitsTag.size() - 1)
					.get(arbeiter.zeitarbeitsTag.get(arbeiter.zeitarbeitsTag.size() - 1).size() - 1);

			// nimmt letzten Zeiteintrag, wenn die Menge an Zeiteinträgen ungerade ist.
		}
		return null;
	}

	/*
	 * Nimmt einen Zeiteintrag und formatiert diesen zu einer Stunden Minuten Zahl
	 * mit doppelpunkt als String, um dies besser in Tabellen anzeigen lassen zu
	 * können.
	 */
	public String zeitFuerTabellenAufbereiter(LocalDateTime zeitEintrag) {
		return zeitEintrag.toString().substring(zeitEintrag.toString().indexOf(':') - 2,
				zeitEintrag.toString().indexOf(':') + 3);
	}

	/*	Nimmt einen Tag der veraendert werden soll, mit einer Liste bei welcher neue LocalDateTime eintraege mitgegeben werden 
	 * und der arbeiter bei welchem Zeiten geaendert werden sollen. Dabei werden die bisherigen eintraege durch die neuen ersetzt.
	 */
	public void gegebeneZeitenAnpasser(LocalDate kalenderPickerTag, List<LocalDateTime> zuaenderndeEintraege,
			Arbeiter arbeiter) {
//		Ueberprueft ob es Eintraege am gewuenschten Tag gibt und ersetzt diese durch die neuen Eintraege.

		for (int i = 0; i < arbeiter.zeitarbeitsTag.size(); i++) {

			if (zuaenderndeEintraege.size() == 0) {

				if (arbeiter.zeitarbeitsTag.get(i).get(0).toLocalDate().equals(kalenderPickerTag)) {
					arbeiter.zeitarbeitsTag.get(i).clear();
					arbeiter.zeitarbeitsTag.get(i).add(kalenderPickerTag.atTime(0, 0, 1));
					EinlesenUndSpeichern.abspeichernVonAenderungen(arbeiter);
					break;

				}
			}

			else if (arbeiter.zeitarbeitsTag.get(i).get(0).toLocalDate()
					.equals(zuaenderndeEintraege.get(0).toLocalDate())) {
				arbeiter.zeitarbeitsTag.get(i).clear();
				arbeiter.zeitarbeitsTag.get(i).addAll(zuaenderndeEintraege);
				EinlesenUndSpeichern.abspeichernVonAenderungen(arbeiter);
				break;

			}
		}
	}
	
	//gibt zurück ob Pausenrecht eingehalten wurde
	//Arbeiter um zugang auf Zeiteinträge und Geburtsdatum des Arbeiters zu enthalten, LocalDate für den Tag den man prüfen möchte
	//Sting gibt auskunft darüber ob und welcher Verstoß beganden wurde. "a" = zu lange am Stück gearbeitet, "b" = maximale Arbeitseit überschritten, "c" = nicht genug Pausen, "d" = alles okay, "e" = keine Einträge zu Tag vorhanden
	public String wurdePauseEingehalten(Arbeiter MitArbeiter, LocalDate tag) {
		if (MitArbeiter.artDesAngestellten == true) {
			// true bedeutet das der angestellte eine Führungskraft ist.
			// Die nach Gesetz anders behandelt wird.
			return "d";
		}
		
		List<LocalDateTime> zeitArbeitsTag = null;
		for (int i = 0; i < MitArbeiter.zeitarbeitsTag.size(); i++) {
			if (MitArbeiter.zeitarbeitsTag.get(i).get(0).toLocalDate().equals(tag)
					&& MitArbeiter.zeitarbeitsTag.get(i).size() % 2 == 0) {
				zeitArbeitsTag = MitArbeiter.zeitarbeitsTag.get(i);
				break;
			}
		}
		if (zeitArbeitsTag == null) {
			return "e";
		}
		// List<LocalDateTime> zeitArbeitsTag =
		// MitArbeiter.zeit.zeitarbeitsTag.get(tag);

		LocalDate Geburtstag = MitArbeiter.geburtstag;
		LocalDate heute = LocalDate.now();

		if (Period.between(Geburtstag, heute).getYears() < 18) {
			return pausenRechner(MitArbeiter, zeitArbeitsTag, 4.5f, 0.5f, 6f, 1f, 8f);
		}

		else {
			return pausenRechner(MitArbeiter, zeitArbeitsTag, 6f, 0.5f, 9f, 0.75f, 10f);
		}
	}

	//gibt zurück ob Pausenrecht eingehalten wurde, wird zur unterteilung zwischen Jugenliche und Erwachsenen genutzt
	//Arbeiter für zugang auf Zeiteinträge, List<LocalDateTime> für zeiteinträge des Tages den man prüfen möchte, unterbrechungsfrei = zeit die am Stück geabreitet werden darf, pause1 = pausezeit wenn nicht länger als "unterbrechungsfrei" gearbeitet wurde, obergrenze = ab hier muss pause2 pause gemacht werden, pause2 = pausenzeit wernn obergreze üerschritten wurde, maximal = wie lange an einem Tag maximal gearbeitet werden darf 
	//Sting gibt auskunft darüber ob und welcher Verstoß beganden wurde. "a" = zu lange am Stück gearbeitet, "b" = maximale Arbeitseit überschritten, "c" = nicht genug Pausen, "d" = alles okay, "e" = keine Einträge zu Tag vorhanden
	private String pausenRechner(Arbeiter MitArbeiter, List<LocalDateTime> zeitArbeitsTag, float unterbrechungsfrei,
			float pause1, float obergrenze, float pause2, float maximal) {
		double gesamtArbeitsZeit = 0;
		double gesamtPauseZeit = 0;

		int zeitArbeitsTagLaenge = zeitArbeitsTag.size();

		for (int i = 0; i < zeitArbeitsTagLaenge; i += 2) {

			double arbeitsIntervall = (double) Duration.between(zeitArbeitsTag.get(i), zeitArbeitsTag.get(i + 1)).toMinutes() / 60;

			if (arbeitsIntervall > unterbrechungsfrei) {
				return "a";
			}
			gesamtArbeitsZeit += arbeitsIntervall;
		}

		
		if (gesamtArbeitsZeit > maximal) {
			return "b";
		}

		for (int i = 0; i < zeitArbeitsTagLaenge; i += 2) {
			if ((i + 2) < zeitArbeitsTagLaenge) {

				double pauseIntervall = (double) Duration.between(zeitArbeitsTag.get(i + 1), zeitArbeitsTag.get(i + 2)).toMinutes() / 60;
				
				if (pauseIntervall >= 0.25f) {
					gesamtPauseZeit += pauseIntervall;
				}
			}
		}


		if (gesamtArbeitsZeit > obergrenze) {
			if (gesamtPauseZeit < pause2) {
				return "c";
			}
			return "d";
		}

		if (gesamtArbeitsZeit > unterbrechungsfrei) {
			if (gesamtPauseZeit < pause1) {
				return "c";
			}
			return "d";
		}

		return "d";
	}

	//gibt ein LocalDateTime mit stunden innerhalb von zwei Grenzwerten zurück
	//ldt = übergebenes localDateTime, untere Grenze, obere Grenze
	//ldt innerhab der Grenzwerte
	private LocalDateTime clampLDT(LocalDateTime ldt, int min, int max)
	{
		
	    if (ldt.getHour() < min ) {
			return LocalDateTime.of(ldt.getYear(), ldt.getMonth(), ldt.getDayOfMonth(), min, 0, ldt.getSecond());
		}
		
		if (ldt.getHour() > max) {
			return LocalDateTime.of(ldt.getYear(), ldt.getMonth(), ldt.getDayOfMonth(), max, 0 , ldt.getSecond());		
		}

		return LocalDateTime.of(ldt.getYear(), ldt.getMonth(), ldt.getDayOfMonth(), ldt.getHour(), ldt.getMinute(), ldt.getSecond());
	}
	
	//gibt ein LocalDateTime mit stunden innerhalb von zwei Grenzwerten zurück. Zusätzlich wird bei Tageswechsel ein Studenwert von 22 zurück gegeben
	//ldt = übergebenes localDateTime, untere Grenze, obere Grenze
	//ldt innerhab der Grenzwerte
	private LocalDateTime clampLDTNeu(LocalDateTime erster,LocalDateTime ldt, int min, int max)
	{
		
	    if (ldt.getHour() < min && ldt.getYear() == erster.getYear() && ldt.getMonth() == erster.getMonth() && ldt.getDayOfMonth() == erster.getDayOfMonth()) {
	    	return LocalDateTime.of(ldt.getYear(), ldt.getMonth(), ldt.getDayOfMonth(), min, 0, ldt.getSecond());
		}
		
		if (ldt.getYear() > erster.getYear() || ldt.getMonthValue() > erster.getMonthValue() || ldt.getDayOfMonth() > erster.getDayOfMonth() || ldt.getHour() > max) {
			return LocalDateTime.of(erster.getYear(), erster.getMonth(), erster.getDayOfMonth(), max, 0 , ldt.getSecond());	
		}


		return LocalDateTime.of(ldt.getYear(), ldt.getMonth(), ldt.getDayOfMonth(), ldt.getHour(), ldt.getMinute(), ldt.getSecond());
	}
	
	
	//gibt die Gesamte Gleitzeit zurück
	//Arbeiter genutzt um zugriff auf Zeiteinträge zu erhalten
	//double enthält die Gleitzeit
	public double gibGleitzeitGesamt(Arbeiter mitarbeiter) {
		double gleitzeit = 0;

		for (int i = 0; i < mitarbeiter.zeitarbeitsTag.size(); i++) {
			gleitzeit += rechneZeitarbeitsTag(mitarbeiter.zeitarbeitsTag.get(i), mitarbeiter)
					- (mitarbeiter.wochenstunden / 6);
		}

		return gleitzeit;
	}

	//gibt die Gleitzeit in einem Jahr zurück
	//Arbeiter genutzt um zugriff auf Zeiteinträge zu erhalten, LocalDate definiert welches Jahr man berechnen möchte
	//double enthält die Gleitzeit für Jahr
	public double gibGleitzeitJahr(Arbeiter mitarbeiter , LocalDate ld) {
		double gleitzeit = 0;
		
		
		for (int i = 0; i < mitarbeiter.zeitarbeitsTag.size(); i++) {
			if (mitarbeiter.zeitarbeitsTag.get(i).get(0).getYear() == ld.getYear()) {
				gleitzeit += rechneZeitarbeitsTag(mitarbeiter.zeitarbeitsTag.get(i), mitarbeiter)
						- (mitarbeiter.wochenstunden / 6);
			}
		}

		return gleitzeit;
	}

	//gibt die Gleitzeit in einem Quartal zurück
	//Arbeiter genutzt um zugriff auf Zeiteinträge zu erhalten, LocalDate definiert welches Quartal man berechnen möchte
	//double enthält die Gleitzeit für Quartal
	public double gibGleitzeitQuartal(Arbeiter mitarbeiter, LocalDate ld) {
		double gleitzeit = 0;

		for (int i = 0; i < mitarbeiter.zeitarbeitsTag.size(); i++) {
			if (mitarbeiter.zeitarbeitsTag.get(i).get(0).getYear() == ld.getYear()
					&& mitarbeiter.zeitarbeitsTag.get(i).get(0).get(IsoFields.QUARTER_OF_YEAR) == 
							ld.get(IsoFields.QUARTER_OF_YEAR)) {
				gleitzeit += rechneZeitarbeitsTag(mitarbeiter.zeitarbeitsTag.get(i), mitarbeiter)
						- (mitarbeiter.wochenstunden / 6);
			}
		}
		return gleitzeit;
	}

	//gibt die Gleitzeit in einem Monat zurück
	//Arbeiter genutzt um zugriff auf Zeiteinträge zu erhalten, LocalDate definiert welchen Monat man berechnen möchte
	//double enthält die Gleitzeit für Monat
	public double gibGleitzeitMonat(Arbeiter mitarbeiter, LocalDate ld) {
		double gleitzeit = 0;

		for (int i = 0; i < mitarbeiter.zeitarbeitsTag.size(); i++) {
			if (mitarbeiter.zeitarbeitsTag.get(i).get(0).getYear() == ld.getYear()
					&& mitarbeiter.zeitarbeitsTag.get(i).get(0).getMonth() == ld.getMonth()) {
				gleitzeit += rechneZeitarbeitsTag(mitarbeiter.zeitarbeitsTag.get(i), mitarbeiter)
						- (mitarbeiter.wochenstunden / 6);
			}
		}
		return gleitzeit;
	}
	
	//gibt Geleitzeit für einen Tag zurück
	//List<LocalDateTime> Liste der Zeiteintäge für einen Tag, Arbieter um Geburtsjahr festzustellen
	//double enthält die Gleitzeit für Tag
	public double rechneZeitarbeitsTag(List<LocalDateTime> zeitarbeitstag, Arbeiter mitarbeiter) {
		double zeitProTag = 0;

		if (zeitarbeitstag.size() == 1) {

			if (zeitarbeitstag.get(0).getSecond() == 1) {
				zeitProTag = 0;

			} else if (zeitarbeitstag.get(0).getSecond() > 1) {
				zeitProTag += (mitarbeiter.wochenstunden / 6);
			}
		}

		else {

			for (int j = 0; j < zeitarbeitstag.size(); j += 2) {
		

				
				if( Period.between(mitarbeiter.geburtstag, LocalDate.now()).getYears() < 18)
				{
				zeitProTag += (double) Duration.between(clampLDTNeu(zeitarbeitstag.get(0),zeitarbeitstag.get(j), 6, 20), clampLDTNeu(zeitarbeitstag.get(0), zeitarbeitstag.get(j + 1), 6, 20)).toMinutes() / 60;
				}
				else
				{
				zeitProTag += (double) Duration.between(clampLDTNeu(zeitarbeitstag.get(0),zeitarbeitstag.get(j), 6, 22), clampLDTNeu(zeitarbeitstag.get(0),zeitarbeitstag.get(j + 1), 6, 22)).toMinutes() / 60;
				}

			}
		}

		return zeitProTag;
	}
	
	//gibt LocalDateTime des aller ersten Zeiteintrages zurück
	//List<LocalDateTime> Liste der Zeiteintäge für einen Tag, Arbieter um Geburtsjahr festzustellen
	//LocalDateTime enthält den ersten Tag der je aufgenommen wurde
	public LocalDateTime gibErsterTag(Arbeiter mitarbeiter)
	{
		return mitarbeiter.zeitarbeitsTag.get(0).get(0);
	}
	
	//gibt zurück ob zwischen dem Ende des einen und Anfang des anderen Arbeitstages Pause gemacht wurde
	//Arbeiter um Geburtsdatum festzustellen und Zugang zu Zeiteintägen zu haben. LocalDateTime enthält heutigen Tag
	//String gibt zurück ob Pause eingehalten wurde. "d" = ja, "f" = nein
	public String wurde12StdPauseGeamcht(Arbeiter ma, LocalDateTime heute)
	{
		try {
		if( Period.between(ma.geburtstag, LocalDate.now()).getYears() < 18)
		{
			if( (double) Duration.between(ma.zeitarbeitsTag.get(ma.zeitarbeitsTag.size()-2).get(ma.zeitarbeitsTag.get(ma.zeitarbeitsTag.size()-2).size() -1), heute).toMinutes() / 60 >= 12)
			{
				return "d";
			}
			
			return "f";
		}
		
		else
		{
			if( (double) Duration.between(ma.zeitarbeitsTag.get(ma.zeitarbeitsTag.size()-2).get(ma.zeitarbeitsTag.get(ma.zeitarbeitsTag.size()-2).size() -1), heute).toMinutes() / 60 >= 11)
			{
				return "d";
			}
			
			return "f";
		}
		
		}catch(Exception e) {
			return "d";
		}
	}
	
}

