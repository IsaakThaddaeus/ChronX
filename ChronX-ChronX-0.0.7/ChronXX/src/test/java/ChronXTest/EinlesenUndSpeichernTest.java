package ChronXTest;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import org.junit.Test;

import ChronXProgramm.Arbeiter;
import ChronXProgramm.EinlesenUndSpeichern;
import ChronXProgramm.ZeitRechner;

public class EinlesenUndSpeichernTest {
	@Test
	public void zuordnungDesEingeloggtenArbeiters_Jan_richtig_zuordnen() throws IOException {
		String email = "jan.holzhausen@bbqgmbh.de" , passwort = "123qwertz";
		Arbeiter testArbeiter = new Arbeiter();
		testArbeiter.email="jan.holzhausen@bbqgmbh.de";
		EinlesenUndSpeichern testparser = new EinlesenUndSpeichern("src/main/resources/jsonfiles");
		assertEquals(testArbeiter.email,testparser.zuordnungDesEingeloggtenArbeiters(email, passwort).email);
	}
	@Test
	public void zuordnungDesEingeloggtenArbeiters_Justin_richtig_zuordnen() throws IOException {
		String email = "justin.schick@bbqgmbh.de" , passwort = "qwertz123";
		Arbeiter testArbeiter = new Arbeiter();
		testArbeiter.email="justin.schick@bbqgmbh.de";
		EinlesenUndSpeichern testparser = new EinlesenUndSpeichern("src/main/resources/jsonfiles");
		assertEquals(testArbeiter.email,testparser.zuordnungDesEingeloggtenArbeiters(email, passwort).email);
	}
	@Test
	public void zuordnungDesEingeloggtenArbeiters_Justins_falsches_Passwort_erkennen() throws IOException {
		String email = "justin.schick@bbqgmbh.de" , passwort = "qwertz124";
		Arbeiter testArbeiter = new Arbeiter();
		testArbeiter.email="justin.schick@bbqgmbh.de";
		EinlesenUndSpeichern testparser = new EinlesenUndSpeichern("src/main/resources/jsonfiles");
		assertEquals(null,testparser.zuordnungDesEingeloggtenArbeiters(email, passwort));
	}
	@Test
	public void zuordnungDesEingeloggtenArbeiters_Emailadresse_Großgeschrieben_verarbeiten() throws IOException {
		String email = "JUSTIN.schick@bbqgmbh.de" , passwort = "qwertz123";
		Arbeiter testArbeiter = new Arbeiter();
		testArbeiter.email="justin.schick@bbqgmbh.de";
		EinlesenUndSpeichern testparser = new EinlesenUndSpeichern("src/main/resources/jsonfiles");
		assertEquals(testArbeiter.email,testparser.zuordnungDesEingeloggtenArbeiters(email, passwort).email);
	}
	@Test
	public void zuordnungDesEingeloggtenArbeiters_Emailadresse_falsch_geschrieben() throws IOException {
		String email = "jahn.holzhausen@bbqgmbh.de" , passwort = "123qwertz";
		Arbeiter testArbeiter = new Arbeiter();
		testArbeiter.email="jan.holzhausen@bbqgmbh.de";
		EinlesenUndSpeichern testparser = new EinlesenUndSpeichern("src/main/resources/jsonfiles");
		assertEquals(null,testparser.zuordnungDesEingeloggtenArbeiters(email, passwort));
	}
	
}
