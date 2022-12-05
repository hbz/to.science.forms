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

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.rdf4j.rio.RDFFormat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import models.JsonMessage;
import models.ZettelModel;
import play.data.Form;
import play.data.validation.ValidationError;

/**
 * @author Jan Schnasse
 *
 */
public class ZettelHelper {

	/**
	 * Provides access to useful config data for transformation and display of rdf
	 * uris
	 */
	public static MyEtikettMaker etikett = new MyEtikettMaker();

	/**
	 * To post a dynamic number of repeated values a simple convention is used.
	 * <br/>
	 * Example: <br/>
	 * Name of the list: author <br/>
	 * Name of the first entry: author[0]<br/>
	 * Name of the next entriy: author[1] <br/>
	 * .....<br/ This method gives all names that exists in the current form for a
	 * given field with repeated values.
	 * 
	 * @param form a form to drive a html formular
	 * @param jsonMap a map with all data
	 * @param fieldName the fieldname that is used to post data
	 * @return a list of valid fieldnames for post data.
	 */
	public static List<String> getFieldWithIndex(Form<ZettelModel> form,
			Map<String, Object> jsonMap, String fieldName) {
		List<String> result = new ArrayList<>();
		if (form.hasErrors()) {
			result = getFieldNamesWithIndexFromFormData(form, fieldName);
		} else if (form.value().isPresent()) {
			result = getFieldNamesWithIndexFromJsonLd(jsonMap, fieldName);
		}
		if (result.isEmpty()) {
			result.add(fieldName + "[0]");
		}
		return result;
	}

	public static String getFieldWithoutIndex(String fieldWithIndex) {
		try {
			return fieldWithIndex.substring(fieldWithIndex.indexOf("["));
		} catch (Exception e) {
			return fieldWithIndex;
		}
	}

	public static List<String> getIndex(Form<ZettelModel> form,
			Map<String, Object> jsonMap, String fieldName) {
		List<String> result = new ArrayList<>();
		if (form.hasErrors()) {
			result = getIndexFromFormData(form, fieldName);
		} else if (form.value().isPresent()) {
			result = getIndexFromJsonLd(jsonMap, fieldName);
		}
		if (result.isEmpty()) {
			result.add("0");
		}
		return result;
	}

	private static List<String> getIndexFromFormData(Form<ZettelModel> form,
			String fieldName) {
		List<String> result = new ArrayList<>();
		Map<String, String> formData = form.data();
		String id = formData.get(fieldName);
		if (id != null) {
			result.add(fieldName);
		} else {
			for (int i = 0; i < Integer.MAX_VALUE; i++) {
				id = formData.get(fieldName + "[" + i + "]");
				if (id == null)
					break;
				result.add("" + i);
			}
		}
		if (result.isEmpty()) {
			result.add("");
		}
		return result;
	}

	private static List<String> getIndexFromJsonLd(Map<String, Object> jsonMap,
			String fieldName) {
		List<String> result = new ArrayList<>();
		Object data = jsonMap.get(fieldName);
		if (data != null) {
			if (data instanceof Collection<?>) {
				@SuppressWarnings("unchecked")
				List<String> dataList = (List<String>) data;
				for (int i = 0; i < dataList.size(); i++) {
					result.add("" + i);
				}
			} else {
				play.Logger.debug("No index added to " + fieldName + " with class "
						+ data.getClass());
			}
		} else {
			play.Logger.debug("No data found for " + fieldName);
		}
		return result;
	}

	private static List<String> getFieldNamesWithIndexFromFormData(
			Form<ZettelModel> form, String fieldName) {
		List<String> result = new ArrayList<>();
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

	private static List<String> getFieldNamesWithIndexFromJsonLd(
			Map<String, Object> jsonMap, String fieldName) {
		List<String> result = new ArrayList<>();
		Object data = jsonMap.get(fieldName);
		if (data != null) {
			if (data instanceof Collection<?>) {
				@SuppressWarnings("unchecked")
				List<String> dataList = (List<String>) data;
				for (int i = 0; i < dataList.size(); i++) {
					result.add(fieldName + "[" + i + "]");
				}
			} else {
				play.Logger.debug("No index added to " + fieldName + " with class "
						+ data.getClass());
			}
		} else {
			play.Logger.debug("No data found for " + fieldName);
		}
		return result;
	}

	/**
	 * Returns a html fragment like <code>
	 * <div id="embeddedJson" 
	 * 	class="success-true" 
	 * 	style=
	"display:none">{"some":"json","data":"from","your":"form","model":"end"}</div>
	 * </code> If form validation has been successful class attribute is set to
	 * 'success-true' or 'success-false' if not. In case of success the div
	 * contains a json-ld representation of the forms underlying model. In case of
	 * errors, the error message is returned as json string.
	 * 
	 * @param form the forms data will be returned in a html div
	 * @param format can be xml or json
	 * @return an html div containing json data
	 */
	public static JsonMessage getEmbeddedJson(Form<?> form, String format) {
		JsonMessage result = null;
		// play.Logger.debug("Write " + format);
		try {
			if (form.hasErrors()) {
				result = new JsonMessage(form.errorsAsJson(), 400);
			} else {
				if (form.get() != null) {
					String jsonldString = form.get().toString();
					jsonldString = jsonldString.replace("%", "%25");
					try (InputStream in =
							new ByteArrayInputStream(jsonldString.getBytes("utf-8"))) {
						if ("xml".equals(format)) {
							String rdfString = RdfUtils.readRdfToString(in, RDFFormat.JSONLD,
									RDFFormat.RDFXML, "");
							// play.Logger.debug(rdfString);
							result = new JsonMessage(rdfString, 200);
						} else if ("ntriples".equals(format)) {
							String rdfString = RdfUtils.readRdfToString(in, RDFFormat.JSONLD,
									RDFFormat.NTRIPLES, "");
							result = new JsonMessage(rdfString, 200);
						} else {
							result = new JsonMessage(
									((ZettelModel) form.get()).serializeToMap(), 200);
						}
					}
				}
			}
		} catch (Exception e) {
			play.Logger.debug("", e);
		}
		return result;

	}

	/**
	 * @param object a java object
	 * @return a json serialization of the object as string
	 */
	public static String objectToString(Object object) {
		try {
			return new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT)
					.writeValueAsString(object);
		} catch (Exception e) {
			return "To String failed " + e.getMessage();
		}
	}

	/**
	 * @param object a java object
	 * @return a json serialization of the object
	 */
	public static JsonNode objectToJson(Object object) {
		try {
			return new ObjectMapper().readTree(objectToString(object));
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * @param form the form to get values from
	 * @param fieldNameWithIndex an fieldName with index, e.g. creator[index]
	 * @return data for repeated form field
	 */
	public static String getData(Form<ZettelModel> form,
			String fieldNameWithIndex) {
		return getData(form, fieldNameWithIndex, "");
	}

	public static String getData(Form<ZettelModel> form,
			String fieldNameWithIndex, String defaultValue) {
		String result = defaultValue;
		try {
			if (form.value().isPresent() || form.hasErrors()) {
				int i = parseIndex(fieldNameWithIndex);
				String f = parseFieldName(fieldNameWithIndex);
				if (form.hasErrors()) {
					result = getDataFromMap(form, f, i);
				} else {
					result = getDataFromJson(form, f, i);
				}
			}
		} catch (Exception e) {
			// this can happen. Return empty string in that case.
		}
		return result;
	}

	private static String getDataFromMap(Form<ZettelModel> form, String f,
			int i) {
		String result = "";
		if (i != -1) {
			result = form.data().get(f + "[" + i + "]");
		} else {
			result = form.data().get(f);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	private static String getDataFromJson(Form<ZettelModel> form, String f,
			int i) {
		String result = "";
		if (i != -1) {
			result =
					((List<String>) form.value().get().serializeToMap().get(f)).get(i);
		} else {
			result = form.value().get().serializeToMap().get(f).toString();
		}
		return result;
	}

	private static String parseFieldName(String fieldNameWithIndex) {
		try {
			int rb = fieldNameWithIndex.indexOf('[');
			String result = fieldNameWithIndex.substring(0, rb);
			return result;
		} catch (Exception e) {
			return fieldNameWithIndex;
		}
	}

	private static int parseIndex(String fieldNameWithIndex) {
		try {
			int rb = fieldNameWithIndex.indexOf('[');
			int re = fieldNameWithIndex.indexOf(']');
			int result = Integer.parseInt(fieldNameWithIndex.substring(rb + 1, re));
			return result;
		} catch (Exception e) {
			return -1;
		}
	}

	/**
	 * @param form a play2 form
	 * @return a jsonlike map
	 */
	public static Map<String, Object> getJsonMap(Form<ZettelModel> form) {
		try {
			return form.value().get().serializeToMap();
		} catch (Exception e) {
			return new HashMap<>();
		}
	}

	public static LinkedHashMap<String, List<ValidationError>> sortErrors(
			Map<String, List<ValidationError>> errors) {
		LinkedHashMap<String, List<ValidationError>> result = new LinkedHashMap<>();
		addError(result, errors, "rdftype");
		addError(result, errors, "publicationStatus");
		addError(result, errors, "reviewStatus");
		addError(result, errors, "title");
		addError(result, errors, "alternative");
		addError(result, errors, "creator");
		addError(result, errors, "contributor");
		addError(result, errors, "editor");
		addError(result, errors, "other");
		addError(result, errors, "containedIn");
		addError(result, errors, "bibliographicCitation");
		addError(result, errors, "issued");
		addError(result, errors, "publicationYear");
		addError(result, errors, "institution");
		addError(result, errors, "collectionTwo");
		addError(result, errors, "collectionOne");
		addError(result, errors, "yearOfCopyright");
		addError(result, errors, "license");
		addError(result, errors, "embargoTime");
		addError(result, errors, "abstractText");
		addError(result, errors, "language");
		addError(result, errors, "ddc");
		addError(result, errors, "subject");
		addError(result, errors, "publisherVersion");
		addError(result, errors, "fulltextVersion");
		addError(result, errors, "additionalMaterial");
		addError(result, errors, "internalReference");
		addError(result, errors, "fundingId");
		addError(result, errors, "projectId");
		addError(result, errors, "fundingProgram");
		addError(result, errors, "additionalNotes");
		return result;
	}

	public static LinkedHashMap<String, List<ValidationError>> sortErrorsForResearchData(
			Map<String, List<ValidationError>> errors) {
		LinkedHashMap<String, List<ValidationError>> result = new LinkedHashMap<>();
		addError(result, errors, "title");
		addError(result, errors, "alternative");
		addError(result, errors, "creator");
		addError(result, errors, "contributor");
		addError(result, errors, "other");
		addError(result, errors, "medium");
		addError(result, errors, "yearOfCopyright");
		addError(result, errors, "license");
		addError(result, errors, "embargoTime");
		addError(result, errors, "language");
		addError(result, errors, "description");
		addError(result, errors, "usageManual");
		addError(result, errors, "ddc");
		addError(result, errors, "subject");
		addError(result, errors, "fundingId");
		addError(result, errors, "projectId");
		addError(result, errors, "fundingProgram");
		addError(result, errors, "dataOrigin");
		addError(result, errors, "recordingPeriod");
		addError(result, errors, "recordingLocation");
		addError(result, errors, "recordingCoordinates");
		addError(result, errors, "reference");
		addError(result, errors, "associatedPublication");
		addError(result, errors, "associatedDataset");
		addError(result, errors, "nextVersion");
		addError(result, errors, "previousVersion");
		addError(result, errors, "urn");
		addError(result, errors, "doi");
		addError(result, errors, "isLike");

		return result;
	}

	private static void addError(
			LinkedHashMap<String, List<ValidationError>> result,
			Map<String, List<ValidationError>> errors, String field) {

		List<ValidationError> error = errors.get(field);
		if (error != null && !error.isEmpty()) {
			result.put(field, error);
		}
	}
}
