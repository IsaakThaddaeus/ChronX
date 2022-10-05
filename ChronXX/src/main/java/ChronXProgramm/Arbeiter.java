package ChronXProgramm;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
//Hier nichts ver�ndern!!
public class Arbeiter {
	@JsonProperty
	public String email;

	@JsonProperty
	public String passwort;

	@JsonProperty 
	public int artDesAngestellten;
	
	@JsonProperty
	LocalDate geburtstag;

	@JsonProperty
	public boolean sprache;

	@JsonProperty
	public Zeit zeit;

	public class Zeit {

		@JsonProperty
		public double aktuelleGleitzeit; //gibt die verf�gbare Gesamtgleitzeit an

		@JsonProperty
		public double wochenstunden; //gibt an wie lange der Arbeiter in der Woche Arbeiten muss

		@JsonProperty
		public double gleitzeitWarngrenze; // Individuelle Warngrenze nur f�r UI interessant

		@JsonProperty
		List<List<LocalDateTime>> zeitarbeitsTag = new ArrayList<List<LocalDateTime>>(); // Monat, Tag, einzelne Zeiten. Dennoch sind alle Zeits�tze mit allen Infos ausgestattet

		@JsonProperty
		List<LocalDate> urlaubsUndKrankheitsTage = new ArrayList<LocalDate>();	// noch nicht benutzt
		}
	

}

