/*Copyright (c) 2016 "hbz"

This file is part of zettel.

zettel is free software: you can redistribute it and/or modify
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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.openrdf.model.BNode;
import org.openrdf.model.Graph;
import org.openrdf.model.Value;
import org.openrdf.rio.RDFFormat;

/**
 * ZettelModel provides an abstract base class for all models used in model
 * based forms. The main purpose of the ZettelModel is the provision of two
 * methods. </br>
 * The method serializeToMap provides a display of the models data in form of
 * Java Map that contains all necessary values for a JSON-Ld compliant
 * serialization.</br>
 * The method deserializeFromRdf consumes an rdf InputStream and deserializes
 * its content to the derived model.</br>
 * The derived model class must implement two methods in order to support
 * serialization and deserialization.</br>
 * The abstract method getMappingForSerialization asks for a map that maps json
 * field names to an appropriate getter method.</br>
 * Example:</br>
 * 
 * <pre>
 * Map<String, Supplier<Object>> dict = new LinkedHashMap<>();
 * // serialize the return of getTitle() in a field named "title"
 * dict.put("title", () -> getTitle());
 * </pre>
 * 
 * 
 * The abstract method getMappingForDeserialization asks for a map that maps
 * URIs to an appropriate setter method. At the current version it supports only
 * String and List<String> as input types for the setter.</br>
 * Example:</br>
 * 
 * <pre>
 * Map<String, Consumer<Object>> dict = new LinkedHashMap<>();
 * // deserialize the rdf data referenced by dc:title using the setter method
 * // setTitle.
 * dict.put("http://purl.org/dc/terms/title", (in) -> setTitle((String) in));
 * </pre>
 * 
 * @author Jan Schnasse
 *
 */
public abstract class ZettelModel {

	static final String TYPE = "type";
	static final String IS_PRIMARY_TOPIC_OF = "isPrimaryTopicOf";
	static final String PRIMARY_TOPIC = "primaryTopic";
	static final String ID = "@id";
	static final String LABEL = "prefLabel";

	private String documentId = "_:foo";
	private String topicId = "http://localhost/resource/add/researchData";

	/**
	 * @return a map that maps a json name to a getter method
	 */
	protected abstract Map<String, Supplier<Object>> getMappingForSerialization();

	/**
	 * @return a map that maps a uri to a setter method
	 */
	protected abstract Map<String, Consumer<Object>> getMappingForDeserialization();

	/**
	 * @return the type will be included as rdf type of the resource under field
	 *         name TYPE
	 */
	protected abstract String getType();

	/**
	 * @param in input stream with rdf data
	 * @param format format of the rdf serialization
	 * @return the rdf data loaded to a ZettelModel
	 */
	public ZettelModel deserializeFromRdf(InputStream in, RDFFormat format) {
		Map<String, Consumer<Object>> dict = getMappingForDeserialization();
		Graph graph = RdfUtils.readRdfToGraph(in, format, getDocumentId());
		graph.forEach((st) -> {
			play.Logger.debug(st + "");
			if (!"".equals(st.getObject().stringValue())) {
				String rdf_P = st.getPredicate().stringValue();
				if (dict.containsKey(rdf_P)) {
					initListField(graph, st.getObject(), dict.get(rdf_P));
				}
			}
		});
		return this;
	}

	/**
	 * @return json ld map for this model
	 */
	public Map<String, Object> serializeToMap() {
		Map<String, Supplier<Object>> dict = getMappingForSerialization();
		Map<String, Object> jsonMap = new LinkedHashMap<>();
		jsonMap.put(ID, documentId);
		jsonMap.put(TYPE, getType());
		addIsPrimaryTopicOf(jsonMap);
		dict.entrySet().stream().forEach((entry) -> ZettelModel.addField(jsonMap,
				entry.getKey(), entry.getValue().get()));
		jsonMap.put("@context", ZettelHelper.etikett.getContext().get("@context"));
		return jsonMap;
	}

	/**
	 * @return the id or uri of the document that is described by the form
	 */
	public String getDocumentId() {
		return documentId;
	}

	/**
	 * @param documentId provide your id as URIto get proper rdf.
	 */
	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	/**
	 * @return the id of the actual thing that is described by the resource. Must
	 *         be an URI.
	 */
	public String getTopicId() {
		return topicId;
	}

	/**
	 * @param topicId provide an id of what is described by your resource as URI.
	 */
	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}

	private static void addField(Map<String, Object> jsonMap, String name,
			Object o) {
		if (o == null)
			return;
		if (o instanceof String) {
			if (!((String) o).isEmpty())
				jsonMap.put(name, o);
		} else if (o instanceof List<?>) {
			if (!((List<?>) o).isEmpty())
				jsonMap.put(name, o);
		} else {
			play.Logger.error("Unknown type found in " + name + "! Value is " + o);
		}
	}

	private static void initListField(Graph graph, Value rdf_O,
			Consumer<Object> consumer) {
		if (rdf_O instanceof BNode) {
			RdfUtils.traverseList(graph, ((BNode) rdf_O).getID(), RdfUtils.first,
					consumer);
		} else {
			try {
				consumer.accept(rdf_O.stringValue());
			} catch (ClassCastException e) {
				List<String> list = new ArrayList<>();
				list.add(rdf_O.stringValue());
				consumer.accept(list);
			}
		}
	}

	private void addIsPrimaryTopicOf(Map<String, Object> jsonMap) {
		Map<String, Object> topicMap = new HashMap<>();
		topicMap.put(ZettelModel.ID, topicId);
		topicMap.put(ZettelModel.PRIMARY_TOPIC, documentId);
		jsonMap.put(ZettelModel.IS_PRIMARY_TOPIC_OF, topicMap);
	}
}
