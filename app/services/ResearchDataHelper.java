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
	
	// KTBL-Maps

		public static LinkedHashMap<String, String> getLivestockCategoryMap() {
			LinkedHashMap<String, String> map = new LinkedHashMap<>();
			//map.put(null, "Please select...");
			map.put("cattle", "cattle");
			map.put("pig", "pig");
			map.put("chicken", "chicken");
			map.put("turkey", "turkey");
			map.put("duck", "duck");
			return map;	
		}
		public static LinkedHashMap<String, String> getLivestockProductionMap() {
			LinkedHashMap<String, String> map = new LinkedHashMap<>();
			map.put("dairy_cattle_farming", "dairy cattle farming");
			map.put("young_cattle_farming", "young cattle farming");
			map.put("calf_raising", "calf raising");
			map.put("calf_fattening", "calf fattening");
			map.put("cattle_fattening", "cattle fattening");
			map.put("suckler_cow_farming", "suckler cow farming");
			map.put("piglet_production", "piglet production");
			map.put("piglet_raising", "piglet raising");
			map.put("pig_fattening", "pig fattening");
			map.put("laying_hen_farming", "laying hen farming"); 
			map.put("young_hen_farming", "young hen farming");
			map.put("broiler_fattening", "broiler fattening");
			map.put("turkey_fattening", "turkey fattening"); 
			map.put("turkey_raising", "turkey raising");
			map.put("duck_fattening", "duck fattening");
			return map;	
		}
		
		public static LinkedHashMap<String, String> getVentilationSystemMap() {
			LinkedHashMap<String, String> map = new LinkedHashMap<>();
			map.put("natural_ventilation_system", "natural ventilation");
			map.put("forced_ventilation_system", "forced ventilation");
			map.put("hybrid_ventilation_system", "hybrid ventilation");
			return map;	
		}
			
		public static LinkedHashMap<String, String> getHousing_systemstMap() {
			LinkedHashMap<String, String> map = new LinkedHashMap<>();
			map.put("cubicle_h", "cubicle housing");
			map.put("loose_h_1_type_floor", "loose housing system with single type flooring");
			map.put("loose_h_2_type_floor", "loose housing system with two type flooring");
			map.put("calf_boxes_h", "calf boxes");
			map.put("igloo_or_hut_h", "igloo or hut housing");
			map.put("1_area_pen", "single area pen");
			map.put("mult_area_pen", "multiple area pen");
			map.put("farrow_area_1_area_pen", "farrowing area, single area pen");
			map.put("farrow_area_mult_area_pen", "farrowing area, multiple area pen");
			map.put("wait_mate_area_mult_area_pen", "waiting and mating area, multiple area pen");
			map.put("floor_h", "floor housing");
			map.put("floor_h_with_aviaries", "floor housing with aviaries");
			map.put("mobile_h", "mobile housing");
			map.put("others", "others");			
			return map;	
		}
		public static LinkedHashMap<String, String> getAdditionalHousingSystemsMap() {
			LinkedHashMap<String, String> map = new LinkedHashMap<>();
			map.put("solid_manure", "solid manure");
			map.put("liquid_manure", "liquid manure");
			map.put("solid_floor", "solid floor");
			map.put("perforated_floor", "perforated floor");
			map.put("manure_belt", "manure belt");
			map.put("veranda", "veranda");
			map.put("pastures", "pastures");
			map.put("yard", "yard");
			map.put("others", "others");
			return map;	
		}
		

		public static LinkedHashMap<String, String> getEmissionsMap() {
			LinkedHashMap<String, String> map = new LinkedHashMap<>();
			map.put("nh3", "NH3 (ammonia)");
			map.put("co2", "CO2 (carbon dioxide)");
			map.put("n2o", "N2O (nitrous oxide)");
			map.put("CH4", "CH4 (methane)");
			map.put("odour", "ODOUR");
			map.put("particles", "Particles (dust)");
			map.put("bioaerosols", "Bioaerosols");
			map.put("others", "others");	
			return map;	
		}
		
		public static LinkedHashMap<String, String> getEmissionReducingMethodsMap() {
			LinkedHashMap<String, String> map = new LinkedHashMap<>();
			
			map.put("feed_supplements", "feed supplements (FS)");
			map.put("manure_belt", "manure belt (MB)");
			map.put("slurry_cooling", "slurry cooling (SC)");			
			map.put("underfl_scrap_faeces_urine_sep", "underfloor scraper with faeces and urine separation (USFUS)");			
			map.put("grooved_floor_finger_scraper", "grooved floor with finger scraper (GFFS)");			
			map.put("slurry_channel_reduction", "slurry channel reduction (SCR)");			
			map.put("litter_aeration", "litter aeration (LA)");
			map.put("slurry_treatment_whey", "slurry treatment whey (W)");			
			map.put("urease_inhibitor", "urease inhibitor (UI)");			
			map.put("nutrition_reduced_feeding", "nutrition reduced feeding (NRF)");			
			map.put("reduced_slat_ratio", "reduced slat ratio (RSR)");			
			map.put("faeces_urine_separation", "faeces urine separation (FUS)");			
			map.put("underfloor_scraper", "underfloor scraper (US)");						
			map.put("above_floor_scraper", "above floor scraper (AFS)");		
			map.put("incr_manure_removal_interval", "increased manure removal interval (IMRI)");		
			map.put("slurry_channel_flushing_system", "slurry channel flushing system (SCFS)");
			map.put("slurry_acidification", "slurry acidification (SA)");		
			map.put("air_cleaning", "air cleaning (AC)");			
			map.put("incoming_air_cooling", "incoming air cooling (IAC)");
			map.put("manure_drying", "manure drying (MD)");			
			map.put("others", "others");
			
			return map;	
		}
					
		public static LinkedHashMap<String, String> getEmiMeasurementTechniquesMap() {
			LinkedHashMap<String, String> map = new LinkedHashMap<>();
			map.put("concentration_measurement", "Concentration Measurement");
			map.put("volume_flow_measurement", "Volume Flow Measurement");
			map.put("tracer_gas_method", "Tracer Gas Method");
			map.put("static_chamber", "Static Chamber Measurements");
			map.put("dynamic_chamber", "Dynamic Chamber Measurements");
			return map;
		}
		
		
		public static LinkedHashMap<String, String> getProjectTitleMap() {
			LinkedHashMap<String, String> map = new LinkedHashMap<>();
			map.put("emimin", "EmiMin");
			map.put("emidat", "EmiDat");
			map.put("emimod", "EmiMod");
			return map;
		}
		
		public static LinkedHashMap<String, String> getTestDesignMap() {
			LinkedHashMap<String, String> map = new LinkedHashMap<>();
			map.put("case_control", "case control");
			map.put("multi_site", "multi site");
			return map;
		}
		
		
		
		
		
	
}