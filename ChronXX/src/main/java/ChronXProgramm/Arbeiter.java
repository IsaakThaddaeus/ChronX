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
		public List<String> zeitarbeitsTag= new ArrayList<>();

		@JsonProperty
		public double[] monatsZeit =new double[12];
	}

}
//		@JsonProperty("class")
//		public String clazz;
//
//		@JsonProperty
//		public String threadName;
//
//		@JsonProperty
//		public Mdc mdc;
//
//		public class Mdc {
//
//			@JsonProperty("TRACE_ID")
//			public String traceId;
//		}
//
//		@JsonProperty
//		public Exception exception;
//
//		public class Exception {
//			@JsonProperty
//			public String stacktrace;
//
//			@JsonProperty
//			public String exception_class;
//
//			@JsonProperty
//			public String exception_message;
//		}
