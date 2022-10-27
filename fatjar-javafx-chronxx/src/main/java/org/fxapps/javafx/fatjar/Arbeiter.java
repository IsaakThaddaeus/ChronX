package org.fxapps.javafx.fatjar;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
public class Arbeiter {
	@JsonProperty
	public String email;

	@JsonProperty
	public String passwort;

	@JsonProperty 
	public boolean artDesAngestellten;
	
	@JsonProperty
	public LocalDate geburtstag;

	@JsonProperty
	public boolean sprache;



		@JsonProperty
		public double aktuelleGleitzeit; //gibt die verfaegbare Gesamtgleitzeit an

		@JsonProperty
		public double wochenstunden; //gibt an wie lange der Arbeiter in der Woche Arbeiten muss

		@JsonProperty
		public double gleitzeitWarngrenze; // Individuelle Warngrenze nur faer UI interessant

		@JsonProperty
		List<List<LocalDateTime>> zeitarbeitsTag = new ArrayList<List<LocalDateTime>>(); // Monat, Tag, einzelne Zeiten. Dennoch sind alle Zeitsaetze mit allen Infos ausgestattet

		@JsonProperty
		List<LocalDate> urlaubsUndKrankheitsTage = new ArrayList<LocalDate>();	// noch nicht benutzt
		
	

}

