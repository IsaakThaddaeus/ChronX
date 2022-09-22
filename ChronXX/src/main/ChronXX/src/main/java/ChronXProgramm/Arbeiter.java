package ChronXProgramm;

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
	public int artDesAngestellten;

	@JsonProperty
	public boolean sprache;

	@JsonProperty
	public Zeit zeit;

	public class Zeit {

		@JsonProperty
		public double aktuelleGleitzeit;

		@JsonProperty
		public double wochenstunden;

		@JsonProperty
		public double gleitzeitWarngrenze;

		@JsonProperty
		List<List<LocalDateTime>> zeitarbeitsTag = new ArrayList<List<LocalDateTime>>();

		@JsonProperty
		public Double[][][] gleitzeit ;
	}
	

}

