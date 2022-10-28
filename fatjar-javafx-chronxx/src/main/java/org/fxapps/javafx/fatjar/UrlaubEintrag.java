package org.fxapps.javafx.fatjar;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class UrlaubEintrag {
	
	 public String start;
	 public String ende;
	 public int tage;
	
    public UrlaubEintrag () {}

	public UrlaubEintrag (String s, String e, int t) {
		
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
    
    public int getTage() {
    	
    	DateTimeFormatter formatters  = DateTimeFormatter.ofPattern("dd.MM.uuuu");
    	
    	LocalDate startL = LocalDate.parse(start, formatters);
    	LocalDate endeL = LocalDate.parse(ende, formatters);
    	
    	 Period diffDays = Period.between(startL, endeL);
         int days = diffDays.getDays();
         
         this.tage = days;
         System.out.println("Das sind die Urlaubstage: " + tage);
         return tage;
         
        
    	
    	
    	
    }

}
