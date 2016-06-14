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
package models;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.google.common.io.Closeables;
import com.typesafe.config.ConfigFactory;

import play.data.Form;
import play.libs.ws.WSAuthScheme;
import play.libs.ws.WSResponse;

/**
 * @author Jan Schnasse
 *
 */
public class ResearchDataHelper {

	public static LinkedHashMap<String, String> getDeweyMap() {
		LinkedHashMap<String, String> map = new LinkedHashMap();
		map.put("http://dewey.info/class/333/7/",
				"333.7 Natürliche Ressourcen,Energie & Umwelt");
		map.put("http://dewey.info/class/570", "570 Biowissenschaften, Biologie");
		map.put("http://dewey.info/class/610", "610 Medizin & Gesundheit");
		map.put("http://dewey.info/class/630", "630 Landwirtschaft");
		map.put("http://dewey.info/class/640", "640 Hauswirtschaft & Familie");
		map.put("http://dewey.info/class/660", "660 Chemische Verfahrenstechnik");
		return map;
	}

	public static LinkedHashMap<String, String> getProfessionalGroupMap() {
		LinkedHashMap<String, String> map = new LinkedHashMap();
		map.put("http://d-nb.info/gnd/4038243-6", "Medizin");
		map.put("http://d-nb.info/gnd/4020775-4", "Gesundheitswesen");
		map.put("http://d-nb.info/gnd/4152829-3", "Ernährungswissenschaften");
		map.put("http://d-nb.info/gnd/4068473-8", "Agrarwissenschaften");
		map.put("http://d-nb.info/gnd/4137364-9", "Umweltwissenschaften");
		map.put("http://d-nb.info/gnd/4006851-1", "Biologie");
		return map;
	}

	public static LinkedHashMap<String, String> getDataOriginMap() {
		LinkedHashMap<String, String> map = new LinkedHashMap();
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
		map.put("http://hbz-nrw.de/regal#Andere", "Andere");
		return map;
	}

	public static LinkedHashMap<String, String> getMediumMap() {
		LinkedHashMap<String, String> map = new LinkedHashMap();
		map.put("http://rdvocab.info/termList/RDAproductionMethod/1010", "Print");
		map.put("http://rdvocab.info/termList/RDACarrierType/1018", "Online");
		map.put("http://purl.org/ontology/bibo/AudioDocument", "Audio");
		map.put("http://rdvocab.info/termList/RDACarrierType/1050", "Video");
		map.put("http://purl.org/ontology/bibo/Image", "Bild");
		map.put("http://pbcore.org/vocabularies/instantiationMediaType#text ",
				"Text");
		map.put("http://pbcore.org/vocabularies/instantiationMediaType#software",
				"Software");
		map.put("http://purl.org/lobid/lv%23Miscellaneous", "Andere");
		return map;
	}

	public static LinkedHashMap<String, String> getLicenseMap() {
		LinkedHashMap<String, String> map = new LinkedHashMap();
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

	public static LinkedHashMap<String, String> getLanguageMap() {
		LinkedHashMap<String, String> map = new LinkedHashMap();
		map.put("http://id.loc.gov/vocabulary/iso639-2/ger", "Deutsch");
		map.put("http://id.loc.gov/vocabulary/iso639-2/eng", "Englisch");
		map.put("http://id.loc.gov/vocabulary/iso639-2/fra", "Französisch");
		map.put("http://id.loc.gov/vocabulary/iso639-2/spa", "Spanisch");
		map.put("http://id.loc.gov/vocabulary/iso639-2/ita", "Italienisch");
		return map;
	}

	public static String getLinkedDataLabel(String uri) {
		try {
			if (uri == null || uri.isEmpty())
				return uri;
			String etikettUrl = ConfigFactory.load().getString("etikettService");
			String etikettUser = ConfigFactory.load().getString("etikettUser");
			String etikettPwd = ConfigFactory.load().getString("etikettPwd");
			play.Logger.debug(etikettUrl + "?url=" + uri + "&column=label");
			WSResponse response = play.libs.ws.WS.client()
					.url(etikettUrl + "?url=" + uri + "&column=label")
					.setAuth(etikettUser, etikettPwd, WSAuthScheme.BASIC)
					.setFollowRedirects(true).get().toCompletableFuture().get();
			try (InputStream input = response.getBodyAsStream()) {
				String content =
						CharStreams.toString(new InputStreamReader(input, Charsets.UTF_8));
				play.Logger.debug(content);
				return content;
			}
		} catch (Exception e) {
			return uri;
		}
	}

	public static List<String> getFieldWithIndex(Form<?> form, String fieldName) {
		List<String> result = new ArrayList();
		Map<String, String> formData = form.data();
		String id = formData.get(fieldName);
		if (id != null) {
			result.add(fieldName);
		} else {
			for (int i = 0; i < Integer.MAX_VALUE; i++) {
				id = formData.get(fieldName + "[" + i + "]");
				if (id == null)
					break;
				result.add(fieldName + "[" + i + "]");
			}
		}
		if (result.isEmpty()) {
			result.add(fieldName);
		}
		return result;
	}
}