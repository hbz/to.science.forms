/*Copyright (c) 2016 "hbz"

This file is part of zettel.

etikett is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package services;

import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvMapReader;
import org.supercsv.io.ICsvMapReader;
import org.supercsv.prefs.CsvPreference;

/**
 * @author Jan Schnasse
 *
 */

@SuppressWarnings("javadoc")
public class ArticleHelper {

	public static Map<String, String> collectionOne =
			readCsv("collectionOne.csv");

	public static Map<String, String> ddc = readCsv("ddc.csv");
	public static Map<String, String> professionalGroup =
			readCsv("professionalGroup.csv");

	private static CellProcessor[] getProcessors() {
		final CellProcessor[] processors = new CellProcessor[] { new NotNull(), // URI
				new NotNull(), // Label
		};
		return processors;
	}

	private static Map<String, String> readCsv(String resource) {
		String path = play.Play.application().resource(resource).getPath();
		play.Logger.info("Read " + resource + " from " + path);
		Map<String, String> result = new LinkedHashMap<>();
		try (ICsvMapReader reader = new CsvMapReader(new FileReader(path),
				new CsvPreference.Builder('"', ',', "\n").build());) {
			String[] header = reader.getHeader(true);
			CellProcessor[] processors = getProcessors();
			Map<String, Object> rec;
			while ((rec = reader.read(header, processors)) != null) {
				result.put(rec.get("URI").toString(), rec.get("Label").toString());
			}
			play.Logger.info(result + "");
			return result;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static LinkedHashMap<String, String> getPublicationStatusMap() {
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put(null, "Bitte wählen Sie...");
		map.put("http://hbz-nrw.de/regal#original", "Postprint Verlagsversion");
		map.put("http://hbz-nrw.de/regal#postprint", "Postprint Autorenmanuskript");
		map.put("http://hbz-nrw.de/regal#preprint", "Preprint");
		return map;
	}

	public static LinkedHashMap<String, String> getTypeMap() {
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put(null, "Bitte wählen Sie...");
		map.put("http://purl.org/ontology/bibo/Article", "Zeitschriftenartikel");
		map.put("http://purl.org/ontology/bibo/Chapter", "Buchkapitel");
		map.put("http://purl.org/ontology/bibo/Proceedings", "Kongressbeitrag");
		return map;
	}

	public static LinkedHashMap<String, String> getReviewStatusMap() {
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put(null, "Bitte wählen Sie...");
		map.put("http://hbz-nrw.de/regal#reviewed", "begutachtet");
		map.put("http://hbz-nrw.de/regal#peerReviewed",
				"begutachtet (Peer-reviewed)");
		return map;
	}

	/**
	 * @return a map that can be used in an html select
	 */
	public static LinkedHashMap<String, String> getDataOriginMap() {
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put(null, "Bitte wählen Sie...");
		map.put("http://hbz-nrw.de/regal#Andere", "Andere");
		map.put("http://hbz-nrw.de/regal#Interview", "Interview");
		map.put("http://hbz-nrw.de/regal#Umfrage", "Umfrage");
		map.put("http://hbz-nrw.de/regal#Anamnese", "Anamnese");
		map.put("http://hbz-nrw.de/regal#Exploration", "Exploration");
		map.put("http://hbz-nrw.de/regal#Probe", "Probe");
		map.put("http://hbz-nrw.de/regal#Gewebeprobe", "Gewebeprobe");
		map.put("http://hbz-nrw.de/regal#Flaechenmischprobe", "Flächenmischprobe");
		map.put("http://hbz-nrw.de/regal#Bodenbohrung", "Bodenbohrung");
		map.put("http://hbz-nrw.de/regal#apparativeUntersuchung",
				"apparative Untersuchung");
		map.put("http://hbz-nrw.de/regal#koerperlicheUntersuchung",
				"körperliche Untersuchung");
		map.put("http://hbz-nrw.de/regal#Feldbeobachtung", "Feldbeobachtung");
		map.put("http://hbz-nrw.de/regal#Laborbeobachtung", "Laborbeobachtung");
		map.put("http://hbz-nrw.de/regal#Analyse", "Analyse");
		map.put("http://hbz-nrw.de/regal#Genomsequenzierung", "Genomsequenzierung");
		map.put("http://hbz-nrw.de/regal#Messung", "Messung");
		map.put("http://hbz-nrw.de/regal#Berechnung", "Berechnung");
		map.put("http://hbz-nrw.de/regal#Evaluation", "Evaluation");
		map.put("http://hbz-nrw.de/regal#Querschnittstudie", "Querschnittstudie");
		map.put("http://hbz-nrw.de/regal#Langzeitstudie", "Langzeitstudie");
		map.put("http://hbz-nrw.de/regal#Interventionsstudie",
				"Interventionsstudie");
		map.put("http://hbz-nrw.de/regal#Kohortenstudie", "Kohortenstudie");
		map.put("http://hbz-nrw.de/regal#Simulation", "Simulation");
		return map;
	}

	/**
	 * @return a map that can be used in an html select
	 */
	public static LinkedHashMap<String, String> getMediumMap() {
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put(null, "Bitte wählen Sie...");
		map.put("http://purl.org/lobid/lv#fulltextOnline", "Volltext");
		map.put("http://purl.org/dc/terms/LicenseDocument", "Autorenvertrag");
		map.put("http://id.loc.gov/ontologies/bibframe/supplement", "Beilage");
		map.put("http://purl.org/lobid/lv#Miscellaneous", "Andere");
		return map;
	}

	/**
	 * @return a map that can be used in an html select
	 */
	public static LinkedHashMap<String, String> getLicenseMap() {
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put("http://opendatacommons.org/licenses/by/1.0/",
				"ODC By - Open Data Commons (empfohlen) ");
		map.put("https://creativecommons.org/licenses/by/4.0", "CC BY 4.0");
		map.put("https://creativecommons.org/publicdomain/zero/1.0/", "CC0 1.0");
		map.put("http://opendatacommons.org/licenses/odbl/1-0/",
				"ODbL (Open Database License)");
		map.put("http://opendatacommons.org/licenses/pddl/1.0/",
				"PDDL (Public Domain Dedication and License)");
		map.put("http://www.gnu.org/licenses/gpl-3.0.de.html",
				"GNU GPL (GNU General Public Licence)");
		return map;
	}

	/**
	 * @return a map that can be used in an html select
	 */
	public static LinkedHashMap<String, String> getLanguageMap() {
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put(null, "Bitte wählen Sie...");
		map.put("http://id.loc.gov/vocabulary/iso639-2/ger", "Deutsch");
		map.put("http://id.loc.gov/vocabulary/iso639-2/eng", "Englisch");
		map.put("http://id.loc.gov/vocabulary/iso639-2/fra", "Französisch");
		map.put("http://id.loc.gov/vocabulary/iso639-2/spa", "Spanisch");
		map.put("http://id.loc.gov/vocabulary/iso639-2/ita", "Italienisch");
		return map;
	}

	/**
	 * @return a map of yyyy strings with the last hundred years descending from
	 *         today
	 */
	public static LinkedHashMap<String, String> getCopyrightYear() {
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put(null, "Bitte wählen Sie...");
		List<LocalDate> years =
				Stream.iterate(LocalDate.now(), date -> date.minusYears(1)).limit(100)
						.collect(Collectors.toList());
		for (LocalDate d : years) {
			String ds = d.format(DateTimeFormatter.ofPattern("yyyy"));
			map.put(ds, ds);
		}

		return map;
	}

	public static LinkedHashMap<String, String> getPersonLookupEndpoints() {
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put("/tools/zettel/localAutocomplete", "Lokal");
		map.put("/tools/zettel/orcidAutocomplete", "ORCiD");
		map.put("/tools/zettel/personAutocomplete", "GND (Personen)");
		map.put("/tools/zettel/corporateBodyAutocomplete", "GND (Körperschaften)");
		return map;
	}

	public static LinkedHashMap<String, String> getCollectionTwoEndpoints() {
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put("/tools/zettel/conferenceAutocomplete", "GND (Kongress)");
		map.put("/tools/zettel/localAutocomplete", "Lokal");
		return map;
	}

	public static LinkedHashMap<String, String> getInstitutionLookupEndpoints() {
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put("/tools/zettel/corporateBodyAutocomplete", "GND (Körperschaften)");
		map.put("/tools/zettel/personAutocomplete", "GND (Personen)");
		map.put("/tools/zettel/localAutocomplete", "Lokal");
		return map;
	}

	public static LinkedHashMap<String, String> getSubjectLookupEndpoints() {
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put("/tools/zettel/localAutocomplete", "Lokal");
		map.put("/tools/zettel/subjectAutocomplete", "GND");
		map.put("/tools/skos-lookup/autocomplete", "Agrovoc");
		return map;
	}

	public static LinkedHashMap<String, String> getLobidLookupEndpoints() {
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put("/tools/zettel/lobidAutocomplete", "Lobid 2");
		map.put("/tools/zettel/localAutocomplete", "Lokal");
		return map;
	}

	public static LinkedHashMap<String, String> getFundingLookupEndpoints() {
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put("/tools/zettel/crossrefAutocomplete", "CrossRef Funders Registry");
		map.put("/tools/zettel/localAutocomplete", "Lokal");
		return map;
	}

	public static LinkedHashMap<String, String> getEmptyLookupEndpoints() {
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		return map;
	}

	public static LinkedHashMap<String, String> getTitleLookupEndpoints() {
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put(null, "Bitte wählen Sie...");
		map.put("/tools/zettel/journalAutocomplete", "Zeitschrift");
		map.put("/tools/zettel/bookAutocomplete", "Buch");
		map.put("/tools/zettel/conferenceAutocomplete", "Kongress");
		map.put("/tools/zettel/seriesAutocomplete", "Monogr.ÜO");
		map.put("/tools/zettel/localAutocomplete", "Lokal");
		map.put("/tools/zettel/allAutocomplete", "Aleph");
		return map;
	}

	public static LinkedHashMap<String, String> getRoleMap() {
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put("http://id.loc.gov/vocabulary/relators/cre", "Autor/in");
		map.put("http://id.loc.gov/vocabulary/relators/clb", "Mitarbeit");
		map.put("http://id.loc.gov/vocabulary/relators/edt", "Herausgeber/in");
		map.put("http://id.loc.gov/vocabulary/relators/red", "Redaktor");
		map.put("http://id.loc.gov/vocabulary/relators/act", "Schauspieler/in");
		map.put("http://id.loc.gov/vocabulary/relators/aft",
				"Autor des Nachwortes");
		map.put("http://id.loc.gov/vocabulary/relators/ant",
				"Basiert auf das Werk von");
		map.put("http://id.loc.gov/vocabulary/relators/aui", "Einleitung");
		map.put("http://id.loc.gov/vocabulary/relators/aus", "Drehbuch");
		map.put("http://id.loc.gov/vocabulary/relators/cmp", "Komponist");
		map.put("http://id.loc.gov/vocabulary/relators/cnd", "Dirigent/in");
		map.put("http://id.loc.gov/vocabulary/relators/cng", "Kamera");
		map.put("http://id.loc.gov/vocabulary/relators/col", "Sammler/in");
		map.put("http://id.loc.gov/vocabulary/relators/ctb", "Mitwirkende");
		map.put("http://id.loc.gov/vocabulary/relators/ctg", "Kartographie");
		map.put("http://id.loc.gov/vocabulary/relators/drt", "Regie");
		map.put("http://id.loc.gov/vocabulary/relators/dte", "Gewidmet");
		map.put("http://id.loc.gov/vocabulary/relators/egr", "Stecher/in");
		map.put("http://id.loc.gov/vocabulary/relators/hnr", "Gefeierte Person");
		map.put("http://id.loc.gov/vocabulary/relators/ill", "Illustration");
		map.put("http://id.loc.gov/vocabulary/relators/ive", "Interviewte/r");
		map.put("http://id.loc.gov/vocabulary/relators/ivr", "Interviewer/in");
		map.put("http://id.loc.gov/vocabulary/relators/mus", "Musik");
		map.put("http://id.loc.gov/vocabulary/relators/pht", "Fotografie");
		map.put("http://id.loc.gov/vocabulary/relators/prf", "Interpret");
		map.put("http://id.loc.gov/vocabulary/relators/pro", "Produzent");
		map.put("http://id.loc.gov/vocabulary/relators/sng", "Gesang");
		return map;
	}

}
