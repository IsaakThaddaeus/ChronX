package ChronXProgramm;

import java.time.LocalDateTime;

public class ZeitRechner {
	public static String aktuelleZeit() {
		 LocalDateTime date = LocalDateTime.now();
		return date.getYear()+"."+date.getMonthValue()+"."+date.getDayOfMonth()+"-"+date.getHour()+":"+date.getMinute();
	}
//	public static void zeitNutzerZuweisen(Arbeiter.) { // Zuerst muss Json geladen werden, dann muss Einlogdatei geschrieben werden wo hier eine Email weotergeleitet wird. 
//		
//	}
}
