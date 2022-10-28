package org.fxapps.javafx.fatjar;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import javax.xml.datatype.Duration;






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
    	
//    	DateTimeFormatter formatters  = DateTimeFormatter.ofPattern("dd.MM.uuuu");
//    	
//    	LocalDateTime startL = LocalDateTime.parse(start, formatters);
//    	LocalDateTime endeL = LocalDateTime.parse(ende, formatters);
    	
    	//System.out.println("Das hier ist der Starttag: " + startL);
    	
    	//long daysBetween = ChronoUnit.DAYS.between(startL, endeL);
    	
    	//System.out.println("Das hier sind die Urlaubstage mit long: " + daysBetween);
    	
//    	Period period = Period.between(startL, endeL);
//        int days = period.getDays();
//         
//         this.tage = days;
       //  System.out.println("Das sind die Urlaubstage: " + tage);
         tage = 5;
         return tage;
         
        
    	
    	
    	
    }

}
