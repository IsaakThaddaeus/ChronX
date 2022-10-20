package org.fxapps.javafx.fatjar;


public class Eintrag {
    public String kommen;
    public String gegangen;
    public double stunden;

    public Eintrag() {}
    
    public Eintrag(String kommen, String gegangen, double stunden) {
        this.kommen = kommen;
        this.gegangen = gegangen;
        this.stunden = stunden;
    }

    public String getKommen() {
        return kommen;
    }

    public String getGegangen() {
        return gegangen;
    }

    public void setKommen(String kommen) {
        this.kommen = kommen;
    }

    public void setGegangen(String gegangen) {
        this.gegangen = gegangen;
    }

    public double getStunden () {

        System.out.println(kommen + gegangen);
      
        String[] kommenA = kommen.split(":");
        String[] gehenA = gegangen.split(":");
      
        double hKommen = Double.parseDouble(kommenA[0]);
        double sKommen = Double.parseDouble(kommenA[1]);
      
        double hGehen = Double.parseDouble(gehenA[0]);
        double sGehen = Double.parseDouble(gehenA[1]);
      
        System.out.println(hKommen + " " + sKommen +  " " + hGehen + " " + sGehen);
      
        double r = (hGehen + (sGehen / 60)) - (hKommen + (sKommen / 60));
        r = Math.round(r*100.0)/100.0;
      
        System.out.println(r + "doublestunden");
        stunden = r;
        return r;
      
      
      
      }
}
