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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openrdf.model.BNode;
import org.openrdf.model.Graph;
import org.openrdf.rio.RDFFormat;

import com.fasterxml.jackson.annotation.JsonInclude;
import play.data.validation.Constraints.Required;
import services.RdfUtils;
import static services.ZettelFields.*;
import services.ZettelHelper;
import services.ZettelModel;

/**
 * @author Jan Schnasse
 *
 */
@SuppressWarnings("javadoc")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResearchData implements ZettelModel

{

	/**
	 * The id under which this model is registered in the ZettelRegister
	 */
	public final static String id = "katalog:data";

	private static final String ID = "@id";

	@SuppressWarnings("unused")
	private static final String LABEL = "prefLabel";

	@Required(message = "Please provide a title")
	private String title;

	private List<String> creator;

	private String yearOfCopyright;

	private String license;

	private String abstractText;

	private String professionalGroup;

	private String embargo;

	List<String> ddc;

	String language;

	String medium;

	String dataOrigin;

	private List<String> subject;
	private List<String> doi;

	private String documentId;
	private String topicId;

	/**
	 * Create an empty ResearchData model
	 */
	public ResearchData() {
		this.title = new String();
		this.creator = new ArrayList<>();
		this.yearOfCopyright = new String();
		this.license = new String();
		this.abstractText = new String();
		this.professionalGroup = new String();
		this.embargo = new String();
		this.ddc = new ArrayList<>();
		this.language = new String();
		this.medium = new String();
		this.dataOrigin = new String();
		this.subject = new ArrayList<>();
		this.doi = new ArrayList<>();
		documentId = "_:foo";
		topicId = "http://localhost/resource/add/researchData";
	}

	/**
	 * Initialize all values at once
	 */
	public ResearchData(String title, List<String> author, String yearOfCopyright,
			String license, String abstractText, String professionalGroup,
			String embargo, List<String> ddc, String language, String medium,
			String dataOrigin, List<String> subject, List<String> doi) {
		super();
		this.title = title;
		this.creator = author;
		this.yearOfCopyright = yearOfCopyright;
		this.license = license;
		this.abstractText = abstractText;
		this.professionalGroup = professionalGroup;
		this.embargo = embargo;
		this.ddc = ddc;
		this.language = language;
		this.medium = medium;
		this.dataOrigin = dataOrigin;
		this.subject = subject;
		this.doi = doi;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<String> getCreator() {
		return creator;
	}

	public void setCreator(List<String> author) {
		this.creator = author;
	}

	public String getYearOfCopyright() {
		return yearOfCopyright;
	}

	public void setYearOfCopyright(String yearOfCopyright) {
		this.yearOfCopyright = yearOfCopyright;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public List<String> getDoi() {
		return doi;
	}

	public void setDoi(List<String> doi) {
		this.doi = doi;
	}

	public String getAbstractText() {
		return abstractText;
	}

	public void setAbstractText(String abstractText) {
		this.abstractText = abstractText;
	}

	public String getProfessionalGroup() {
		return professionalGroup;
	}

	public void setProfessionalGroup(String professionalGroup) {
		this.professionalGroup = professionalGroup;
	}

	public String getEmbargo() {
		return embargo;
	}

	public void setEmbargo(String embargo) {
		this.embargo = embargo;
	}

	public List<String> getDdc() {
		return ddc;
	}

	public void setDdc(List<String> ddc) {
		this.ddc = ddc;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public List<String> getSubject() {
		return subject;
	}

	public void setSubject(List<String> subject) {
		this.subject = subject;
	}

	public String getMedium() {
		return medium;
	}

	public void setMedium(String medium) {
		this.medium = medium;
	}

	public String getDataOrigin() {
		return dataOrigin;
	}

	public void setDataOrigin(String dataOrigin) {
		this.dataOrigin = dataOrigin;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public String getTopicId() {
		return topicId;
	}

	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}

	@Override
	public String toString() {
		return ZettelHelper.objectToString(getJsonLdMap());
	}

	@Override
	public Map<String, Object> getJsonLdMap() {
		Map<String, Object> jsonMap = new HashMap<>();
		jsonMap.put(ID, documentId);
		Map<String, Object> topicMap = new HashMap<>();
		topicMap.put(ID, topicId);
		topicMap.put("primaryTopic", documentId);
		jsonMap.put("isPrimaryTopicOf", topicMap);
		jsonMap.put("type", "http://hbz-nrw.de/regal#ResearchData");
		jsonMap.put(titleZF.name, getTitle());
		if (creator != null && !creator.isEmpty())
			jsonMap.put(creatorZF.name, creator);
		if (abstractText != null && !abstractText.isEmpty())
			jsonMap.put(abstractTextZF.name, abstractText);
		if (dataOrigin != null && !dataOrigin.isEmpty())
			jsonMap.put(dataOriginZF.name, dataOrigin);
		if (embargo != null && !embargo.isEmpty())
			jsonMap.put(embargoZF.name, embargo);
		if (language != null && !language.isEmpty())
			jsonMap.put(languageZF.name, language);
		if (license != null && !license.isEmpty())
			jsonMap.put(licenseZF.name, license);
		if (medium != null && !medium.isEmpty())
			jsonMap.put(mediumZF.name, medium);
		if (professionalGroup != null && !professionalGroup.isEmpty())
			jsonMap.put(professionalGroupZF.name, professionalGroup);
		if (subject != null && !subject.isEmpty())
			jsonMap.put(subjectZF.name, subject);
		if (yearOfCopyright != null && !yearOfCopyright.isEmpty())
			jsonMap.put(yearOfCopyrightZF.name, yearOfCopyright);
		if (ddc != null && !ddc.isEmpty())
			jsonMap.put(ddcZF.name, ddc);
		jsonMap.put("@context", ZettelHelper.etikett.getContext().get("@context"));
		return jsonMap;
	}

	@Override
	public ZettelModel loadRdf(InputStream in, RDFFormat format) {

		Graph graph = RdfUtils.readRdfToGraph(in, format, this.documentId);

		graph.forEach((st) -> {
			String rdf_P = st.getPredicate().stringValue();
			String rdf_O = st.getObject().stringValue();
			if (rdf_P.equals(titleZF.uri)) {
				setTitle(rdf_O);
			} else if (rdf_P.equals(creatorZF.uri)) {
				List<String> list =
						RdfUtils.traverseList(graph, ((BNode) st.getObject()).getID(),
								RdfUtils.first, new ArrayList<>());
				setCreator(list);
			} else if (rdf_P.equals(abstractTextZF.uri)) {
				setAbstractText(rdf_O);
			} else if (rdf_P.equals(professionalGroupZF.uri)) {
				play.Logger.debug("Set professionalGroup");
				setProfessionalGroup(rdf_O);
			} else if (rdf_P.equals(ddcZF.uri)) {
				List<String> list =
						RdfUtils.traverseList(graph, ((BNode) st.getObject()).getID(),
								RdfUtils.first, new ArrayList<>());
				setDdc(list);
			} else if (rdf_P.equals(subjectZF.uri)) {
				List<String> list =
						RdfUtils.traverseList(graph, ((BNode) st.getObject()).getID(),
								RdfUtils.first, new ArrayList<>());
				setSubject(list);
			} else if (rdf_P.equals(mediumZF.uri)) {
				setMedium(rdf_O);
			} else if (rdf_P.equals(dataOriginZF.uri)) {
				setDataOrigin(rdf_O);
			} else if (rdf_P.equals(yearOfCopyrightZF.uri)) {
				setYearOfCopyright(rdf_O);
			} else if (rdf_P.equals(embargoZF.uri)) {
				setEmbargo(rdf_O);
			} else if (rdf_P.equals(languageZF.uri)) {
				setLanguage(rdf_O);
			} else if (rdf_P.equals(doiZF.uri)) {
				List<String> list =
						RdfUtils.traverseList(graph, ((BNode) st.getObject()).getID(),
								RdfUtils.first, new ArrayList<>());
				setDoi(list);
			}

		});

		return this;
	}
}
