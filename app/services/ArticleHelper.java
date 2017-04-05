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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Jan Schnasse
 *
 */
public class ArticleHelper {

	/**
	 * @return a map that can be used in an html select
	 */
	public static LinkedHashMap<String, String> getDeweyMap() {
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put(null, "Bitte wählen Sie...");
		map.put("http://dewey.info/class/720/", "Architektur (720)");
		map.put("http://dewey.info/class/020/",
				"Bibliotheks- und Informationswissenschaft (020)");
		map.put("http://dewey.info/class/570/",
				"Biowissenschaften, Biologie (570)");
		map.put("http://dewey.info/class/540/", "Chemie (540)");
		map.put("http://dewey.info/class/660/",
				"Chemische Verfahrenstechnik (660)");
		map.put("http://dewey.info/class/560/", "Fossilien/Paläontologie (560)");
		map.put("http://dewey.info/class/550/", "Geowissenschaften (550)");
		map.put("http://dewey.info/class/943/", "Geschichte Deutschlands (943)");
		map.put("http://dewey.info/class/940/", "Geschichte Europas (940)");
		map.put("http://dewey.info/class/640/", "Hauswirtschaft/Familie (640)");
		map.put("http://dewey.info/class/624/",
				"Ingenieurbau und Umwelttechnik (624)");
		map.put("http://dewey.info/class/710/", "Landschaftsgestaltung (710)");
		map.put("http://dewey.info/class/630/",
				"Landwirtschaft, Veterinärmedizin (630)");
		map.put("http://dewey.info/class/650/", "Management (650)");
		map.put("http://dewey.info/class/610/", "Medizin & Gesundheit (610)");
		map.put("http://dewey.info/class/333/7/",
				"Natürliche Ressourcen,Energie & Umwelt (333.7)");
		map.put("http://dewey.info/class/500/", "Naturwissenschaften (500)");
		map.put("http://dewey.info/class/580/", "Pflanzen (Botanik) (580)");
		map.put("http://dewey.info/class/530/", "Physik (530)");
		map.put("http://dewey.info/class/320/", "Politik (320)");
		map.put("http://dewey.info/class/150/", "Psychologie (150)");
		map.put("http://dewey.info/class/340/", "Recht (340)");
		map.put("http://dewey.info/class/360/",
				"Soziale Probleme, Sozialdienste, Versicherungen (360)");
		map.put("http://dewey.info/class/300/",
				"Sozialwissenschaften,Soziologie, Athropologie (300)");
		map.put("http://dewey.info/class/600/", "Technik (600)");
		map.put("http://dewey.info/class/590/", "Tiere (Zoologie) (590)");
		map.put("http://dewey.info/class/330/", "Wirtschaft (330)");
		return map;
	}

	/**
	 * @return a map that can be used in an html select
	 */
	public static LinkedHashMap<String, String> getProfessionalGroupMap() {
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put(null, "Bitte wählen Sie...");
		map.put("http://skos.um.es/unescothes/C02464", "Medizin");
		map.put("http://skos.um.es/unescothes/C01771", "Gesundheitswesen");
		map.put("http://skos.um.es/unescothes/C02780", "Ernährungswissenschaften");
		map.put("http://skos.um.es/unescothes/C00106", "Agrarwissenschaften");
		map.put("http://skos.um.es/unescothes/C01397", "Umweltwissenschaften");
		map.put("http://skos.um.es/unescothes/COL270", "Biologie");
		map.put("http://skos.um.es/unescothes/C02286",
				"Bibliotheks- und Informationswissenschaften");
		map.put("http://skos.um.es/unescothes/C02053", "Interdisziplinär");
		return map;
	}

	public static LinkedHashMap<String, String> getPublicationStatusMap() {
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put(null, "Bitte wählen Sie...");
		map.put("http://hbz-nrw.de/regal#original", "Veröffentlichungsversion");
		map.put("http://hbz-nrw.de/regal#postprint", "Postprint");
		map.put("http://hbz-nrw.de/regal#preprint", "Preprint");
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
		map.put("http://purl.org/lobid/lv#fulltextOnline",
				"Volltext");
                map.put("http://purl.org/dc/terms/LicenseDocument","Autorenvertrag");
                map.put("http://id.loc.gov/ontologies/bibframe/supplement","Beilage");
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
		map.put("https://lobid.org/person", "GND");
		map.put("/tools/zettel/orcidAutocomplete", "ORCID");
		return map;
	}

	public static LinkedHashMap<String, String> getSubjectLookupEndpoints() {
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put("https://lobid.org/subject", "GND");
		map.put("/tools/skos-lookup/autocomplete", "Agrovoc");
		return map;
	}

	public static LinkedHashMap<String, String> getEmptyLookupEndpoints() {
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		return map;
	}

	public static LinkedHashMap<String, String> getTitleLookupEndpoints() {
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put("/tools/zettel/journalAutocomplete", "ZDB");
		map.put("https://lobid.org/resource", "Aleph");
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

	public static Map<String, String> getCollectionOneMap() {
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put(null, "Bitte wählen Sie...");
		map.put("info:regal/zettel/leibniz/org/BIPS",
				"BIPS - Leibniz-Institut für Präventionsforschung und Epidemiologie");
		map.put("info:regal/zettel/leibniz/org/BNITM",
				"BNITM - Bernhard-Nocht-Institut für Tropenmedizin");
		map.put("info:regal/zettel/leibniz/org/DDZ",
				"DDZ - Deutsches Diabetes-Zentrum - Leibniz-Zentrum für Diabetes-Forschung an der Heinrich-Heine-Universität Düsseldorf");
		map.put("info:regal/zettel/leibniz/org/DFA",
				"DFA - Deutsche Forschungsanstalt für Lebensmittelchemie");
		map.put("info:regal/zettel/leibniz/org/DIfE",
				"DIfE - Deutsches Institut für Ernährungsforschung Potsdam-Rehbrücke");
		map.put("info:regal/zettel/leibniz/org/DPZ",
				"DPZ - Deutsches Primatenzentrum GmbH - Leibniz-Institut für Primatenforschung");
		map.put("info:regal/zettel/leibniz/org/DRFZ",
				"DRFZ - Deutsches Rheuma-Forschungszentrum Berlin");
		map.put("info:regal/zettel/leibniz/org/FZB",
				"DSM - Forschungszentrum Borstel - Leibniz-Zentrum für Medizin und Biowissenschaften");
		map.put("info:regal/zettel/leibniz/org/DSMZ",
				"DSMZ - Leibniz-Institut DSMZ-Deutsche Sammlung von Mikroorganismen und Zellkulturen GmbH");
		map.put("info:regal/zettel/leibniz/org/FBN",
				"FBN - Leibniz-Institut für Nutztierbiologie");
		map.put("info:regal/zettel/leibniz/org/FLI",
				"FLI - Leibniz-Institut für Alternsforschung - Fritz-Lipmann-Institut");
		map.put("info:regal/zettel/leibniz/org/FMP",
				"FMP - Leibniz-Institut für Molekulare Pharmakologie");
		map.put("info:regal/zettel/leibniz/org/HKI",
				"HKI - Leibniz-Institut für Naturstoff-Forschung und Infektionsbiologie - Hans-Knöll-Institut");
		map.put("info:regal/zettel/leibniz/org/HPI",
				"HPI - Heinrich-Pette-Institut - Leibniz-Institut für Experimentelle Virologie");
		map.put("info:regal/zettel/leibniz/org/IGB",
				"IGB - Leibniz-Institut für Gewässerökologie und Binnenfischerei");
		map.put("info:regal/zettel/leibniz/org/IfADo",
				"IfADo - Leibniz-Institut für Arbeitsforschung an der TU Dortmund");
		map.put("info:regal/zettel/leibniz/org/IGZ",
				"IGZ - Leibniz-Institut für Gemüse- und Zierpflanzenbau");
		map.put("info:regal/zettel/leibniz/org/IOW",
				"IOW - Leibniz-Institut für Ostseeforschung Warnemünde");
		map.put("info:regal/zettel/leibniz/org/IPB",
				"IPB - Leibniz-Institut für Pflanzenbiochemie");
		map.put("info:regal/zettel/leibniz/org/IPK",
				"IPK - Leibniz-Institut für Pflanzengenetik und Kulturpflanzenforschung");
		map.put("info:regal/zettel/leibniz/org/IUF",
				"IUF - Leibniz-Institut für umweltmedizinische Forschung");
		map.put("info:regal/zettel/leibniz/org/IZW",
				"IZW - Leibniz-Institut für Zoo- und Wildtierforschung");
		map.put("info:regal/zettel/leibniz/org/LIN",
				"LIN - Leibniz-Institut für Neurobiologie");
		map.put("info:regal/zettel/leibniz/org/MfN",
				"MfN - Museum für Naturkunde - Leibniz-Institut für Evolutions- und Biodiversitätsforschung");
		map.put("info:regal/zettel/leibniz/org/SGN",
				"SGN - Senckenberg Gesellschaft für Naturforschung");
		map.put("info:regal/zettel/leibniz/org/ZALF",
				"ZALF - Leibniz-Zentrum für Agrarlandschaftsforschung");
		map.put("info:regal/zettel/leibniz/org/ZB MED",
				"ZB MED - Informationszentrum Lebenswissenschaften");
		map.put("info:regal/zettel/leibniz/org/ZFMK",
				"ZFMK - Zoologisches Forschungsmuseum Alexander Koenig - Leibniz-Institut für Biodiversität der Tiere");
		map.put("info:regal/zettel/leibniz/org/ZMT",
				"ZMT - Leibniz-Zentrum für Marine Tropenforschung GmbH");

		return map;
	}

}
