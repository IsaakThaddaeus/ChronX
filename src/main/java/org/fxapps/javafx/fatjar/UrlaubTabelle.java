package org.fxapps.javafx.fatjar;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class UrlaubTabelle {
	
	static String start;
	static String ende;
	static int tage;
	
	public UrlaubTabelle (String s, String e, int tage) {
		
			this.start = s;
			this.ende = e;
			this.tage = tage;
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
    
    public static void getTage() {
    	
    	DateTimeFormatter formatters  = DateTimeFormatter.ofPattern("dd.MM.uuuu");
    	
    	LocalDate startL = LocalDate.parse(start, formatters);
    	LocalDate endeL = LocalDate.parse(ende, formatters);
    	
    	 Period diffDays = Period.between(startL, endeL);
         int days = diffDays.getDays();
         
         tage = days;
         
        
    	
    	
    	
    }

}
