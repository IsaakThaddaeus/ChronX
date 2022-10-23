package org.fxapps.javafx.fatjar;

import java.io.IOException;
import java.time.LocalDate;

public class Person {

	static String nachname;
	static String vorname;
	static String email = "a";
	static String passwort = "s";
	static int art;
	static LocalDate geburtstag;
	static boolean sprache = true;

	static boolean leiter;

	static double gleitzeit = 20;
	static double warngrenze = 40;
	static double wochenstunden = 40;

	static LocalDate hdatum;
	static ZeitRechner zr = new ZeitRechner();
	static EinlesenUndSpeichern parser = new EinlesenUndSpeichern();
	static Arbeiter test = new Arbeiter();

	public static void setAktuellEingeloggtenArbeiter() throws IOException {
		test = parser.zuordnungDesEingeloggtenArbeiters(Person.email, Person.passwort);
		System.out.println(test.email+" Person");
	}

	public static Arbeiter getAktuellEingeloggterArbeiter() {
		return test;
	}
	public static EinlesenUndSpeichern getEinlesenUndSpeichern() {
		return parser;
	}
	
	public static ZeitRechner getZeitRechner() {
	        return zr;
	    }
	
	public static String getDatum() {
		
			String s = geburtstag.toString();
			return s;
	}
}
