package org.fxapps.javafx.fatjar;

import java.io.IOException;
import java.time.LocalDate;
//Konstruktoren und Variablen des aktuell angemeldeten Mitarbeiters. Die bei der Anmeldung gesetzt werden.
public class Person {

	static String nachname;
	static String vorname;
	static String email = "a";
	static String passwort = "s";
	static int art;
	static LocalDate geburtstag;
	static boolean sprache = true;

	static boolean leiter;

	static double gleitzeit = 0;
	static double warngrenze = 0;
	static double wochenstunden = 0;

	static LocalDate hdatum;
	static ZeitRechner zr = new ZeitRechner();
	static EinlesenUndSpeichern parser = new EinlesenUndSpeichern();
	static Arbeiter test = new Arbeiter();
	/*Setzt den aktuellen Arbeiter um in anderen Klassen und Methoden immer auf den richten Arbeiter zu referenzieren
	 */
	public static void setAktuellEingeloggtenArbeiter() throws IOException {
		test = parser.zuordnungDesEingeloggtenArbeiters(Person.email, Person.passwort);
		System.out.println(test.email + " Person");
	}
/*
 * getter fuer den in der drueber gesetzten Methode angemeldeten Mitarbeiter
 * gibt diesen Mitarbeiter zurueck
 */
	public static Arbeiter getAktuellEingeloggterArbeiter() {
		return test;
	}
/*
 * gibt das Objekt von Einlesen und speichern zurueck das immer nur ein Objekt davon exisitert.
 * gibt Einlesen und Speichern zurueck um auf die Methoden der Klasse zugreifen zu koennen.
 */
	public static EinlesenUndSpeichern getEinlesenUndSpeichern() {
		return parser;
	}
	/*
	 * gibt das Objekt von Zeitrechner zurueck das immer nur ein Objekt davon exisitert.
	 * gibt Zeitrechner zurueck um auf die Methoden der Klasse zugreifen zu koennen.
	 */
	public static ZeitRechner getZeitRechner() {
		return zr;
	}
	/*
	 * gibt das aktuelle geburtsdatum von LocalDate als String zurueck
	 */
	public static String getDatum() {

		String s = geburtstag.toString();
		return s;
	}
}
