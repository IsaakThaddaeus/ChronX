package ChronXProgramm;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Arbeiter {
	@JsonProperty("@email")
	public String email;

	@JsonProperty("@passwort")
	public String passwort;

	@JsonProperty("@leitenderAngestellter")
	public boolean leitenderAngestellter;

	@JsonProperty("@sprache")
	public boolean sprache;

	@JsonProperty("@Zeit")
	public Zeit zeit;

	public class Zeit {

		@JsonProperty
		public double aktuelleGleitzeit;

		@JsonProperty
		public double wochenstunden;

		@JsonProperty
		public double gleitzeitWarngrenze;

		@JsonProperty
		List<List<String>> zeitarbeitsTag = new ArrayList<List<String>>();

		@JsonProperty
		public double[] monatsZeit =new double[12];
	}

}
