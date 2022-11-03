package org.fxapps.javafx.fatjar;

// Dies Klasse wird verwendet um ein Objekt dieser Klasse zu erstellen und damit einen Eintrag in die Gleitzeittabelle hinzuzufuegen

public class ZeiterfassungEintrag {
	public String kommen;
	public String gegangen;
	public double stunden;

	public ZeiterfassungEintrag() {
	}

	public ZeiterfassungEintrag(String kommen, String gegangen, double stunden) {
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
	
	// Diese Methoden berechnet die Stunden zwischen Start- und Endzeit
	// Es wird nichts uebergen
	// Es werden die Stunden zureuck gegeben

	public double getStunden() {

		String[] kommenA = kommen.split(":");
		String[] gehenA = gegangen.split(":");

		double hKommen = Double.parseDouble(kommenA[0]);
		double sKommen = Double.parseDouble(kommenA[1]);

		double hGehen = Double.parseDouble(gehenA[0]);
		double sGehen = Double.parseDouble(gehenA[1]);

		double r;

		if (hKommen == 0.0 && sKommen == 0.0 && hGehen == 0.0 && sGehen == 0.0) {
			stunden = 0.0;
			r = 0.0;
		}

		else {

			if (hKommen < 6.0) {
				hKommen = 6.0;
			}
			if (hGehen >= 22.0) {
				hGehen = 22.0;
			}

			r = (hGehen + (sGehen / 60)) - (hKommen + (sKommen / 60));
			r = Math.round(r * 100.0) / 100.0;
			stunden = r;

		}
		return r;

	}

}