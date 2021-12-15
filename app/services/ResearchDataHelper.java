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
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Jan Schnasse
 *
 */

@SuppressWarnings("javadoc")
public class ResearchDataHelper {

	/**
	 * @return a map that can be used in an html select
	 */
	public static LinkedHashMap<String, String> getDeweyMap() {
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put(null, "Bitte wählen Sie...");

		map.put("http://dewey.info/class/020/",
				"020 Bibliotheks- und Informationswissenschaft");
		map.put("http://dewey.info/class/150/", "150 Psychologie");
		map.put("http://dewey.info/class/300/",
				"300 Sozialwissenschaften,Soziologie, Athropologie");
		map.put("http://dewey.info/class/320/", "320 Politik");
		map.put("http://dewey.info/class/330/", "330 Wirtschaft");
		map.put("http://dewey.info/class/333/7/",
				"333.7 Natürliche Ressourcen,Energie & Umwelt");
		map.put("http://dewey.info/class/340/", "340 Recht");
		map.put("http://dewey.info/class/360/",
				"360 Soziale Probleme, Sozialdienste, Versicherungen");
		map.put("http://dewey.info/class/500/", "500 Naturwissenschaften");
		map.put("http://dewey.info/class/530/", "530 Physik");
		map.put("http://dewey.info/class/540/", "540 Chemie");
		map.put("http://dewey.info/class/550/", "550 Geowissenschaften");
		map.put("http://dewey.info/class/560/", "560 Fossilien/Paläontologie");
		map.put("http://dewey.info/class/570/", "570 Biowissenschaften, Biologie");
		map.put("http://dewey.info/class/580/", "580 Pflanzen (Botanik)");
		map.put("http://dewey.info/class/590/", "590 Tiere (Zoologie)");
		map.put("http://dewey.info/class/600/", "600 Technik");
		map.put("http://dewey.info/class/610/", "610 Medizin & Gesundheit");
		map.put("http://dewey.info/class/624/",
				"624 Ingenieurbau und Umwelttechnik");
		map.put("http://dewey.info/class/630/",
				"630 Landwirtschaft, Veterinärmedizin");
		map.put("http://dewey.info/class/640/", "640 Hauswirtschaft/Familie");
		map.put("http://dewey.info/class/650/", "650 Management");
		map.put("http://dewey.info/class/660/", "660 Chemische Verfahrenstechnik");
		map.put("http://dewey.info/class/710/", "710 Landschaftsgestaltung");
		map.put("http://dewey.info/class/720/", "720 Architektur");
		map.put("http://dewey.info/class/940/", "940 Geschichte Europas");
		map.put("http://dewey.info/class/943/", "943 Geschichte Deutschlands");
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
		map.put("http://purl.org/ontology/bibo/AudioDocument", "Audio");
		map.put("http://rdvocab.info/termList/RDACarrierType/1050", "Video");
		map.put("http://purl.org/ontology/bibo/Image", "Bild");
		map.put("http://pbcore.org/vocabularies/instantiationMediaType#text",
				"Text");
		map.put("http://purl.org/dc/dcmitype/Dataset", "Datensatz");
		map.put("http://pbcore.org/vocabularies/instantiationMediaType#software",
				"Software");
		map.put("http://purl.org/lobid/lv#Miscellaneous", "Andere");
		return map;
	}

	/**
	 * @return a map that can be used in an html select
	 */
	public static LinkedHashMap<String, String> getLicenseMap() {
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
    map.put("https://creativecommons.org/publicdomain/zero/1.0/",
        "CC0 1.0 Universal (Public Domain Dedication)");
		map.put("https://creativecommons.org/licenses/by/4.0/", "CC BY 4.0");
		map.put("https://creativecommons.org/licenses/by-sa/4.0/", "CC BY-SA 4.0");
    map.put("https://creativecommons.org/licenses/by-nc/4.0/", "CC BY-NC 4.0");
    map.put("https://creativecommons.org/licenses/by-nc-sa/4.0/", "CC BY-NC-SA 4.0");
    map.put("https://creativecommons.org/licenses/by-nd/4.0/", "CC BY-ND 4.0");
    map.put("https://creativecommons.org/licenses/by-nc-nd/4.0/", "CC BY-NC-ND 4.0");	
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
   * @return a map that can be used in an html select
   */
  public static LinkedHashMap<String, String> getAffiliationMap() {
    LinkedHashMap<String, String> map = new LinkedHashMap<>();
    map.put(null, "Bitte wählen Sie...");
    map.put("https://ror.org/00613ak93", "Bergische Universität Wuppertal");
    map.put("https://ror.org/0189raq88", "Deutsche Sporthochschule Köln");
    map.put("https://ror.org/03vz3qc29", "Evangelische Hochschule Rheinland-Westfalen-Lippe");
    map.put("https://ror.org/00edvg943", "Fachhochschule Bielefeld");
    map.put("https://ror.org/03dv91853", "Fachhochschule Dortmund");
    map.put("https://ror.org/04t5phd24", "Fachhochschule Südwestfalen");
    map.put("https://ror.org/04tkkr536", "FernUniversität in Hagen");
    map.put("https://ror.org/04tqgg260", "FH Aachen");
    map.put("https://ror.org/00pv45a02", "FH Münster");
    map.put("https://ror.org/03gf02c22", "Folkwang Universität der Künste");
    map.put("https://ror.org/024z2rq82", "Heinrich-Heine-Universität Düsseldorf");
    map.put("https://ror.org/04x02q560", "Hochschule Bochum");
    map.put("https://ror.org/04m2anh63", "Hochschule Bonn-Rhein-Sieg");
    map.put("https://ror.org/00ftx0026", "Hochschule Düsseldorf");
    map.put("https://ror.org/03hj8rz96", "Hochschule für Gesundheit");
    map.put("https://ror.org/03y02pg02", "Hochschule für Musik Detmold");
    map.put("https://ror.org/05256tw11", "Hochschule für Musik und Tanz Köln");
    map.put("https://ror.org/001rdde17", "Hochschule Hamm-Lippstadt");
    map.put("https://ror.org/027b9qx26", "Hochschule Niederrhein");
    map.put("https://ror.org/04wdt0z89", "Hochschule Rhein-Waal");
    map.put("https://ror.org/02nkxrq89", "Hochschule Ruhr West");
    map.put("https://ror.org/024nr0776", "Katholische Hochschule Nordrhein-Westfalen");
    map.put("https://ror.org/040d40q10", "Kunstakademie Düsseldorf");
    map.put("https://ror.org/00ztzxf77", "Kunstakademie Münster");
    map.put("https://ror.org/00f2wje32", "Kunsthochschule für Medien Köln");
    map.put("https://ror.org/00txhpa83", "Rheinische Fachhochschule Köln");
    map.put("https://ror.org/041nas322", "Rheinische Friedrich-Wilhelms-Universität Bonn");
    map.put("https://ror.org/04e1yzk82", "Robert-Schumann-Hochschule Düsseldorf");
    map.put("https://ror.org/04tsk2644", "Ruhr-Universität Bochum");
    map.put("https://ror.org/04xfq0f34", "RWTH Aachen");
    map.put("https://ror.org/033jd5r25", "Technische Hochschule Georg Agricola");
    map.put("https://ror.org/014nnvj65", "Technische Hochschule Köln");
    map.put("https://ror.org/04eka8j06", "Technische Hochschule Ostwestfalen-Lippe");
    map.put("https://ror.org/01k97gp34", "Technische Universität Dortmund");
    map.put("https://ror.org/02hpadn98", "Universität Bielefeld");
    map.put("https://ror.org/04mz5ra38", "Universität Duisburg-Essen");
    map.put("https://ror.org/058kzsd48", "Universität Paderborn");
    map.put("https://ror.org/02azyry73", "Universität Siegen");
    map.put("https://ror.org/00yq55g44", "Universität Witten/Herdecke");
    map.put("https://ror.org/00rcxh774", "Universität zu Köln");
    map.put("https://ror.org/04p7ekn23", "Westfälische Hochschule Gelsenkirchen Bocholt Recklinghausen");
    map.put("https://ror.org/00pd74e08", "Westfälische Wilhelms-Universität Münster");

    return map;
  }

  /**
   * @return a map that can be used in an html select
   */
  public static LinkedHashMap<String, String> getHonoricPrefixMap() {
    LinkedHashMap<String, String> map = new LinkedHashMap<>();
    map.put(null, "Bitte wählen Sie...");
    map.put("http://hbz-nrw.de/toscience#noPrefix", "keine Angabe");
    map.put("http://hbz-nrw.de/toscience#dr", "Dr. ");
    map.put("http://hbz-nrw.de/toscience#prof", "Prof. ");
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
}