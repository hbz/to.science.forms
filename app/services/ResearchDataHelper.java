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
	public static LinkedHashMap<String, String> getLicenseMap() {
    LinkedHashMap<String, String> map = new LinkedHashMap<>();
    map.put(null, "Bitte wählen Sie...");
    GenericPropertiesLoader GenProp = new GenericPropertiesLoader();
    map.putAll(GenProp.loadVocabMap("license-de.properties"));
    return map;
  }
	
	/**
	 * @return a map that can be used in an html select
	 */
	public static LinkedHashMap<String, String> getFunderMap() {
    LinkedHashMap<String, String> map = new LinkedHashMap<>();
    map.put(null, "Bitte wählen Sie...");
    GenericPropertiesLoader GenProp = new GenericPropertiesLoader();
    map.putAll(GenProp.loadVocabMap("funder-de.properties"));
    return map;
  }
	
	/**
	 * @return a map that can be used in an html select
	 */
	public static LinkedHashMap<String, String> getDepartmentMap() {
	    LinkedHashMap<String, String> map = new LinkedHashMap<>();
	    map.put(null, "Bitte wählen Sie...");
	    GenericPropertiesLoader GenProp = new GenericPropertiesLoader();
	    map.putAll(GenProp.loadVocabMap("department-de.properties"));
	    return map;
	  }
	/**
	 * @return a map that can be used in an html select
	 */
	//TODO check if unnecessary duplicate from ArticleHelper
	public static LinkedHashMap<String, String> getLanguageMap() {
    LinkedHashMap<String, String> map = new LinkedHashMap<>();
    map.put(null, "Bitte wählen Sie...");
    GenericPropertiesLoader GenProp = new GenericPropertiesLoader();
    map.putAll(GenProp.loadVocabMap("language-de.properties"));
    return map;
  }

  /**
   * @return a map of NRW-Hochschulen that can be used in an html select
   */
  public static LinkedHashMap<String, String> getAffiliationMap() {
    LinkedHashMap<String, String> map = new LinkedHashMap<>();
    map.put(null, "Bitte wählen Sie...");
    GenericPropertiesLoader GenProp = new GenericPropertiesLoader();
    map.putAll(GenProp.loadVocabMap("affiliation-de.properties"));
    return map;
  }
  
  /**
   * @return a map of NRW-Hochschulen that can be used in an html select
   */
  public static LinkedHashMap<String, String> getAgentAffiliationMap(String agentType) {
    LinkedHashMap<String, String> map = new LinkedHashMap<>();
    map.put("unbekannt", "Bitte wählen Sie...");
    GenericPropertiesLoader GenProp = new GenericPropertiesLoader();
    map.putAll(GenProp.loadVocabMap(agentType + "ResearchOrganizationsRegistry-de.properties"));
    return map;
  }
  

  
  /**
   * @return a map of NRW-Hochschulen that can be used in an html select
   */
  public static LinkedHashMap<String, String> getMediumMap() {
    LinkedHashMap<String, String> map = new LinkedHashMap<>();
    map.put(null, "Bitte wählen Sie...");
    GenericPropertiesLoader GenProp = new GenericPropertiesLoader();
    map.putAll(GenProp.loadVocabMap("medium-de.properties"));
    return map;
  }

  
  /**
   * @return a map that can be used in an html select
   */
  public static LinkedHashMap<String, String> getAcademicDegreeMap() {
    LinkedHashMap<String, String> map = new LinkedHashMap<>();
    map.put("https://d-nb.info/standards/elementset/gnd#academicDegree/unkown", "Bitte wählen Sie...");
    GenericPropertiesLoader GenProp = new GenericPropertiesLoader();
    map.putAll(GenProp.loadVocabMap("academicDegree-de.properties"));
    return map;
  }
 
  /**
   * @return a map that can be used in an html select
   */
  public static LinkedHashMap<String, String> getAgentAcademicDegreeMap(String agentType) {
    LinkedHashMap<String, String> map = new LinkedHashMap<>();
    map.put("Keine Angabe", "Bitte wählen Sie...");
    GenericPropertiesLoader GenProp = new GenericPropertiesLoader();
    map.putAll(GenProp.loadVocabMap(agentType + "AcademicDegree-de.properties"));
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