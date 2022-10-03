package test;

import java.time.LocalDate;

public class javatest {

    public static void main(String[] args){


        String todaysDate = LocalDate.now().toString();
        System.out.println(todaysDate);

        String[] datenH = todaysDate.split("-");

        int tagH = Integer.parseInt(datenH[2]);
        int monatH = Integer.parseInt(datenH[1]);
        int jahrH =Integer.parseInt(datenH[0]);

    }
    
}
