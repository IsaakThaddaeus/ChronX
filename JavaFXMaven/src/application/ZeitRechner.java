package application;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import de.jollyday.Holiday;
import de.jollyday.HolidayCalendar;
import de.jollyday.HolidayManager;
import de.jollyday.HolidayType;
import de.jollyday.ManagerParameters;

public class ZeitRechner {

	public LocalDateTime aktuelleZeit() { // Gibt aktuelle Zeit ohne Sekunden und Millisekunden
		LocalDateTime date = LocalDateTime.now().plusDays(0); // Fuer testzwecke koennen Tage hizugefuegt werden.
		date = date.withSecond(0);
		date = date.withNano(0);

		return date;

	}

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

	public Arbeiter zeiteintragFuerAktuellenTagHinzufuegen(Arbeiter aktuellerArbeiter) {
		// Fuegt dem aktuellen Tag einen Zeitsatz hinzu, wo kuenftig weitere Eintraege
		// fuer den aktuellen Tag eingetragen werden.
		if (aktuellerArbeiter.zeit.zeitarbeitsTag.get(aktuellerArbeiter.zeit.zeitarbeitsTag.size() - 1).get(
				aktuellerArbeiter.zeit.zeitarbeitsTag.get(aktuellerArbeiter.zeit.zeitarbeitsTag.size() - 1).size() - 1)
				.toLocalDate().equals(aktuelleZeit().toLocalDate())) {

			aktuellerArbeiter.zeit.zeitarbeitsTag.get(aktuellerArbeiter.zeit.zeitarbeitsTag.size() - 1)
					.add(aktuelleZeit());
			return aktuellerArbeiter;
		} else {
			List<LocalDateTime> zeitarbeitsTag = new ArrayList<LocalDateTime>();
			aktuellerArbeiter.zeit.zeitarbeitsTag.add(zeitarbeitsTag);
			aktuellerArbeiter.zeit.zeitarbeitsTag.get(aktuellerArbeiter.zeit.zeitarbeitsTag.size() - 1)
					.add(aktuelleZeit());
			return aktuellerArbeiter;

		}

	}

	public Arbeiter vergangeneTageInDatenBankAnlegen(Arbeiter arbeiter) {
		// Dient der aktualisierung der Datenbank bei nichtnutzung der Stechuhr. So wird
		// abgeglichen ob die vergangenen Tage Feier-/ Sonn-/ Urlaubs- oder
		// Krankheitstage waren. Falls nichts davon zutrifft wird von einem Gleitzeittag
		// ausgegangen.
		LocalDateTime aktuellerTag = aktuelleZeit();
		int letzertag = arbeiter.zeit.zeitarbeitsTag.size() - 1;
		int letzteZeit = arbeiter.zeit.zeitarbeitsTag.get(arbeiter.zeit.zeitarbeitsTag.size() - 1).size() - 1;

		LocalDateTime letzterEingetragenerTag = arbeiter.zeit.zeitarbeitsTag.get(letzertag).get(letzteZeit);

		long unterschiedTage = ChronoUnit.DAYS.between(letzterEingetragenerTag, aktuellerTag.withHour(0).withMinute(0))
				+ 1;
		System.out.println("LetztereingetragenerTag: " + letzterEingetragenerTag + " AktuellerTag: "
				+ aktuellerTag.withHour(0).withMinute(0) + " UnterschiedTage: " + unterschiedTage);

		for (int i = 1; i < unterschiedTage; i++) {
			LocalDateTime aktuellerVergangenerTag = letzterEingetragenerTag.plusDays(i).withHour(0).withMinute(0);
			if (feiertagUndSonntagsUeberpruefer(aktuellerVergangenerTag.toLocalDate())) {
				System.out.println("Feiern " + aktuellerVergangenerTag);
				arbeiter.zeit.zeitarbeitsTag.add(new ArrayList<LocalDateTime>());
				arbeiter.zeit.zeitarbeitsTag.get(arbeiter.zeit.zeitarbeitsTag.size() - 1)
						.add(aktuellerVergangenerTag.withSecond(3));

			} else if (aufUrlaubsKrankheitsliste(aktuellerVergangenerTag.toLocalDate(), arbeiter)) {
				System.out.println("UrlaubKrank " + aktuellerVergangenerTag);
				arbeiter.zeit.zeitarbeitsTag.add(new ArrayList<LocalDateTime>());
				arbeiter.zeit.zeitarbeitsTag.get(arbeiter.zeit.zeitarbeitsTag.size() - 1)
						.add(aktuellerVergangenerTag.withSecond(2));
			} else {
				System.out.println("GleitzeitTag " + aktuellerVergangenerTag);
				arbeiter.zeit.zeitarbeitsTag.add(new ArrayList<LocalDateTime>());
				arbeiter.zeit.zeitarbeitsTag.get(arbeiter.zeit.zeitarbeitsTag.size() - 1)
						.add(aktuellerVergangenerTag.withSecond(1));
			}

		}
		return arbeiter;
	}

	public boolean aufUrlaubsKrankheitsliste(LocalDate zuTestenderTag, Arbeiter arbeiter) {
		// Iteriert ueber die gegebene Urlaubs- bzw Krankheitsliste im Json.
		for (int i = 0; i < arbeiter.zeit.urlaubsUndKrankheitsTage.size(); i++) {
			if (arbeiter.zeit.urlaubsUndKrankheitsTage.get(i).equals(zuTestenderTag)) {
				return true;
			}
		}
		return false;
	}

	public void gegebeneZeitenAnpasser(List<LocalDateTime> zuaenderndeEintraege, Arbeiter arbeiter) {
//		Ueberprueft ob es Eintraege am gewuenschten Tag gibt und ersetzt diese durch die neuen Eintraege.
		for (int i = 0; i < arbeiter.zeit.zeitarbeitsTag.size(); i++) {

			if (arbeiter.zeit.zeitarbeitsTag.get(i).get(0).toLocalDate()
					.equals(zuaenderndeEintraege.get(0).toLocalDate())) {
				System.out.println(arbeiter.zeit.zeitarbeitsTag.get(i).get(0) + " Test ");
				arbeiter.zeit.zeitarbeitsTag.get(i).clear();
				arbeiter.zeit.zeitarbeitsTag.get(i).addAll(zuaenderndeEintraege);

			} else {
				System.out.println("ERROR fuer den Tag gibt es noch keine Eintraege");
				// Evt noch eine Anforderung auch fernvergangene oder zukuenftige Tage schon
				// Eintragen zu koennen.
			}
		}
	}

	public boolean wurdePauseEingehalten(Arbeiter MitArbeiter, LocalDate tag) {
		if (MitArbeiter.artDesAngestellten == 1) {
			return true;
		}
		List<LocalDateTime> zeitArbeitsTag = null;
		for (int i = 0; i < MitArbeiter.zeit.zeitarbeitsTag.size(); i++) {
			if (MitArbeiter.zeit.zeitarbeitsTag.get(i).get(0).toLocalDate().equals(tag)) {
			 zeitArbeitsTag = MitArbeiter.zeit.zeitarbeitsTag.get(i);
			}
		}
		

		LocalDate Geburtstag = MitArbeiter.geburtstag;
		LocalDate heute = LocalDate.now();

		if (Period.between(Geburtstag, heute).getYears() < 18) {
			return pausenRechner(MitArbeiter, zeitArbeitsTag, 4.5f, 0.5f, 6f, 1f);
		}

		else {
			return pausenRechner(MitArbeiter, zeitArbeitsTag, 6f, 0.5f, 9f, 0.75f);
		}
	}

	private boolean pausenRechner(Arbeiter MitArbeiter, List<LocalDateTime> zeitArbeitsTag, float unterbrechungsfrei,
			float pause1, float obergrenze, float pause2) {
		double gesamtArbeitsZeit = 0;
		double gesamtPauseZeit = 0;

		int zeitArbeitsTagLaenge = zeitArbeitsTag.size();

		for (int i = 0; i < zeitArbeitsTagLaenge; i += 2) {
			double arbeitsIntervall = gibZeitIntervall(zeitArbeitsTag.get(i), zeitArbeitsTag.get(i + 1));

			if (arbeitsIntervall > unterbrechungsfrei) {
				return false;
			}
			gesamtArbeitsZeit += arbeitsIntervall;
		}

		if (gesamtArbeitsZeit <= unterbrechungsfrei) {
			return true;
		}

		for (int i = 0; i < zeitArbeitsTagLaenge; i += 2) {
			if ((i + 2) < zeitArbeitsTagLaenge) {
				double pauseIntervall = gibZeitIntervall(zeitArbeitsTag.get(i + 1), zeitArbeitsTag.get(i + 2));
				if (pauseIntervall >= 0.25f) {
					gesamtPauseZeit += pauseIntervall;
				}
			}
		}

		System.out.println(gesamtArbeitsZeit + "  " + gesamtPauseZeit);

		if (gesamtArbeitsZeit > obergrenze) {
			if (gesamtPauseZeit < pause2) {
				return false;
			}
			return true;
		}

		if (gesamtArbeitsZeit > unterbrechungsfrei) {
			if (gesamtPauseZeit < pause1) {
				return false;
			}
			return true;
		}

		return true;
	}

	public double inIndustrie(LocalDateTime zeit) {
		double ausgabe = zeit.getHour();
		double minute = zeit.getMinute();
		ausgabe += minute / 60;

		return ausgabe;
	}

	public double gibZeitIntervall(LocalDateTime Zeitpunkt1, LocalDateTime Zeitpunkt2) {
		return inIndustrie(Zeitpunkt2) - inIndustrie(Zeitpunkt1);
	}

}
