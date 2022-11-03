package org.fxapps.javafx.fatjar;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

// Diese Klasse wird verwendet um ein Objekt der Tabelle für den Urlaub zu erstellen. Es werden Start- und Endtag gespeichert und die genommenen Urlaubstage berechnet

public class UrlaubEintrag {

	public String start;
	public String ende;
	public int tage;

	public UrlaubEintrag() {
	}

	public UrlaubEintrag(String s, String e, int t) {

		this.start = s;
		this.ende = e;
		this.tage = t;
	}

	public String getStart() {
		return start;
	}

	public String getEnde() {
		return ende;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public void setEnde(String ende) {
		this.ende = ende;
	}
	
	// In dieser Methode werden die Urlaubstage aus Start- und Endtag berechnet
	// Es wird nicht uebrgeben
	// die tage sind der Rueckgabewert

	public int getTage() {

		DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd.MM.uuuu");

		LocalDate startL = LocalDate.parse(start, formatters);
		LocalDate endeL = LocalDate.parse(ende, formatters);

		long noOfDaysBetween = ChronoUnit.DAYS.between(startL, endeL);

		int tagedazwischen = (int) noOfDaysBetween;

		this.tage = tagedazwischen + 1;
		return tage;

	}

}
