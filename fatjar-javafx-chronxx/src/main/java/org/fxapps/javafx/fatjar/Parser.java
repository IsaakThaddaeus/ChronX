package org.fxapps.javafx.fatjar;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Parser {

	ObjectMapper mapper = new ObjectMapper();

	Set<File> datein = new HashSet<>();

	public Set<File> leseDateiAusOrdner(File ordner) throws IOException {
		System.out.println(ordner);
		for (final File datei : ordner.listFiles()) {
			if (datei.getName().contains(".json")) {

				System.out.println("ist json");
				datein.add(datei);
			} else if (datei.isDirectory()) {

				System.out.println("Es befindet sich ein weiterer Ordner im Zielordner");
				leseDateiAusOrdner(datei);
			}

			else {
				System.out.println(datei.getName() + " Ist keine lesbarer Dateintyp");
			}
		}
		return datein;
	}

	public List<Arbeiter> jsonDateiMappen(File zielDatei) throws IOException {

		List<Arbeiter> zuDruckendeWerte = new ArrayList<>();

		try (MappingIterator<Arbeiter> alleEinträgeDerJsonDatei = mapper.readerFor(Arbeiter.class)
				.readValues(zielDatei)) {

			while (alleEinträgeDerJsonDatei.hasNextValue()) {
				Arbeiter eineZeile = alleEinträgeDerJsonDatei.nextValue();
				zuDruckendeWerte.add(eineZeile);
			}

			return zuDruckendeWerte;
		}
	}
}
