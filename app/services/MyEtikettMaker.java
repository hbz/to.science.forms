/*
 * Copyright 2016 hbz NRW (http://www.hbz-nrw.de/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package services;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.typesafe.config.ConfigFactory;

import de.hbz.lobid.helper.Etikett;
import de.hbz.lobid.helper.EtikettMaker;
import de.hbz.lobid.helper.EtikettMakerInterface;
import play.libs.ws.WS;
import play.libs.ws.WSAuthScheme;
import play.libs.ws.WSClient;
import play.libs.ws.WSResponse;

/**
 * 
 * @author jan schnasse
 *
 */
public class MyEtikettMaker implements EtikettMakerInterface {

	private final String etikettUrl =
			ConfigFactory.load().getString("etikettService");
	private final String etikettUser =
			ConfigFactory.load().getString("etikettUser");
	private final String etikettPwd =
			ConfigFactory.load().getString("etikettPwd");
	private static final String PREF_LABEL = "prefLabel";
	private static final String TYPE = "@type";
	private static final String ID = "@id";
	EtikettMaker maker;

	/**
	 * Creates a new EtikettMaker to provide labels from etikett webservice
	 */
	public MyEtikettMaker() {

		String url = null;
		try {
			url = etikettUrl + "/labels.json";
			play.Logger.info("Reload labels from " + url);
			URL u = new URL(url);
			maker = new EtikettMaker(u.openStream());
		} catch (Exception e) {
			play.Logger.info("Reload labels from Url:'" + url
					+ "' failed! Load local labels.json");
			maker = new EtikettMaker(
					play.Play.application().resourceAsStream("labels.json"));
		}
	}

	@Override
	public Map<String, Object> getContext() {
		return getAnnotatedContext();
	}

	@Override
	public Etikett getEtikett(String uri) {
		if (uri == null) {
			throw new RuntimeException("Do not pass null!");
		}
		Etikett result = null;
		try {
			result = maker.getEtikett(uri);
		} catch (RuntimeException e) {
			// local lookup might fail
		}
		if (result == null) {
			result = new Etikett(uri);
			result.setName(getJsonName(result));
		}
		if (result.getLabel() == null || result.getLabel().isEmpty()) {
			result = new Etikett(uri);
			result.setLabel(getLabelFromEtikettWs(uri));
		}
		if (result.getLabel() == null || result.getLabel().isEmpty()) {
			result.setLabel(result.getUri());
		}
		return result;
	}

	@Override
	public Etikett getEtikettByName(String name) {
		return maker.getEtikettByName(name);
	}

	/**
	 * @param e an etikett to create a json name from
	 * @return The short name of the e.uri uses String.split on first index of '#'
	 *         or last index of '/'
	 */
	public String getJsonName(Etikett e) {
		String result = null;
		String uri = e.getUri();

		if (e.getName() != null) {
			result = e.getName();
		}
		if (result == null || result.isEmpty()) {
			String prefix = "";
			if (uri.startsWith("http://purl.org/dc/elements"))
				prefix = "dc:";
			if (uri.contains("#"))
				return prefix + uri.split("#")[1];
			else if (uri.startsWith("http")) {
				int i = uri.lastIndexOf("/");
				return prefix + uri.substring(i + 1);
			}
			result = prefix + uri;
		}
		return result;
	}

	private Map<String, Object> getAnnotatedContext() {
		Map<String, Object> pmap;
		Map<String, Object> cmap = new HashMap<>();
		for (Etikett l : getValues()) {
			if ("class".equals(l.getReferenceType()) || l.getReferenceType() == null
					|| l.getName() == null)
				continue;
			pmap = new HashMap<>();
			pmap.put("@id", l.getUri());
			addFieldToMap(pmap, "label", l.getLabel());
			addFieldToMap(pmap, "icon", l.getIcon());
			addFieldToMap(pmap, "weight", l.getWeight());
			addFieldToMap(pmap, "comment", l.getComment());
			if (!"String".equals(l.getReferenceType())) {
				addFieldToMap(pmap, "@type", l.getReferenceType());
			}
			addFieldToMap(pmap, "@container", l.getContainer());
			cmap.put(l.getName(), pmap);
		}
		Map<String, Object> contextObject = new HashMap<>();
		addAliases(cmap);
		// Workaround to support old lobid api.
		Map<String, Object> crdef = (Map<String, Object>) cmap.get("creator");
		Map<String, Object> codef = (Map<String, Object>) cmap.get("contributor");
		codef.put("@container", "@set");
		crdef.put("@container", "@set");
		cmap.put("creator", crdef);
		cmap.put("contributor", codef);
		// Workaround end
		contextObject.put("@context", cmap);
		return contextObject;
	}

	private static void addFieldToMap(Map<String, Object> map, String key,
			String value) {
		if (value != null && !value.isEmpty()) {
			map.put(key, value);
		}
	}

	/**
	 * place to define alias for json ld context
	 * 
	 * @param cmap
	 */
	private void addAliases(Map<String, Object> cmap) {
		// No aliases;
	}

	@Override
	public Collection<Etikett> getValues() {
		return maker.getValues();
	}

	@Override
	public boolean supportsLabelsForValues() {
		return true;
	}

	@Override
	public String getIdAlias() {
		return ID;
	}

	@Override
	public String getLabelKey() {
		return PREF_LABEL;
	}

	@Override
	public String getTypeAlias() {
		return TYPE;
	}

	/**
	 * The function calls a deployment of https://github.com/hbz/etikett to
	 * provide a label for a given uri. If no deployment is available the plain
	 * uri is returned.
	 * 
	 * @param uri a uri somewhere in the net
	 * @return a label for the uri
	 */
	public String getLabelFromEtikettWs(String uri) {
		try {
			if (uri == null || uri.isEmpty())
				return uri;
			// play.Logger.debug(etikettUrl + "?url=" + uri + "&column=label");
			@SuppressWarnings("deprecation")
			WSClient client = WS.newClient(80);
			WSResponse response = client.url(etikettUrl)
					.setAuth(etikettUser, etikettPwd, WSAuthScheme.BASIC)
					.setQueryParameter("column", "label").setQueryParameter("url", uri)
					.setFollowRedirects(true).get().toCompletableFuture().get();
			try (InputStream input = response.getBodyAsStream()) {
				String content =
						CharStreams.toString(new InputStreamReader(input, Charsets.UTF_8));
				// play.Logger.debug(content);
				return content;
			}
		} catch (Exception e) {
			play.Logger.warn("Not able to get label for " + uri);
			// play.Logger.debug("", e.getMessage());
			return uri;
		}
	}
}
