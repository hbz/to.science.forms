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

import static services.ZettelFields.alternativeTitleZF;
import static services.ZettelFields.associatedDatasetZF;
import static services.ZettelFields.associatedPublicationZF;
import static services.ZettelFields.contributorNameZF;
import static services.ZettelFields.contributorOrderZF;
import static services.ZettelFields.contributorZF;
import static services.ZettelFields.creatorNameZF;
import static services.ZettelFields.creatorZF;
import static services.ZettelFields.dataOriginZF;
import static services.ZettelFields.ddcZF;
import static services.ZettelFields.descriptionZF;
import static services.ZettelFields.doiZF;
import static services.ZettelFields.embargoZF;
import static services.ZettelFields.fundingProgramZF;
import static services.ZettelFields.fundingZF;
import static services.ZettelFields.isLikeZF;
import static services.ZettelFields.languageZF;
import static services.ZettelFields.licenseZF;
import static services.ZettelFields.mediumZF;
import static services.ZettelFields.nextVersionZF;
import static services.ZettelFields.previousVersionZF;
import static services.ZettelFields.professionalGroupZF;
import static services.ZettelFields.projectIdZF;
import static services.ZettelFields.recordingCoordinatesZF;
import static services.ZettelFields.recordingLocationZF;
import static services.ZettelFields.recordingPeriodZF;
import static services.ZettelFields.referenceZF;
import static services.ZettelFields.subjectNameZF;
import static services.ZettelFields.subjectOrderZF;
import static services.ZettelFields.subjectZF;
import static services.ZettelFields.titleLanguageZF;
import static services.ZettelFields.titleZF;
import static services.ZettelFields.urnZF;
import static services.ZettelFields.usageManualZF;
import static services.ZettelFields.yearOfCopyrightZF;
import static services.ZettelFields.reviewStatusZF;
import static services.ZettelFields.congressTitleZF;
import static services.ZettelFields.congressLocationZF;
import static services.ZettelFields.congressDurationZF;
import static services.ZettelFields.publisherZF;
import static services.ZettelFields.isbnZF;
import static services.ZettelFields.abstractTextZF;
import static services.ZettelFields.publicationPlaceZF;

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

import play.data.validation.ValidationError;

/**
 * ZettelModel provides an abstract base class for all models used in model
 * based forms. The main purpose of the ZettelModel is the provision of two
 * methods. </br>
 * The method serializeToMap provides a display of the models data in form of
 * Java Map that contains all necessary values for a JSON-Ld compliant
 * serialization.</br>
 * The method deserializeFromRdf consumes an rdf InputStream and deserializes
 * its content to the derived model.</br>
 * The method getMappingForSerialization asks for a map that maps json field
 * names to an appropriate getter method.</br>
 * Example:</br>
 * 
 * <pre>
 * Map<String, Supplier<Object>> dict = new LinkedHashMap<>();
 * // serialize the return of getTitle() in a field named "title"
 * dict.put("title", () -> getTitle());
 * </pre>
 * 
 * 
 * The method getMappingForDeserialization asks for a map that maps URIs to an
 * appropriate setter method. At the current version it supports only String and
 * List<String> as input types for the setter.</br>
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
@SuppressWarnings("javadoc")
public abstract class ZettelModel {

	static final String TYPE = "type";
	static final String IS_PRIMARY_TOPIC_OF = "isPrimaryTopicOf";
	static final String PRIMARY_TOPIC = "primaryTopic";
	static final String ID = "@id";
	static final String LABEL = "prefLabel";

	private String documentId = "_:foo";
	private String topicId = "http://localhost/resource/add/researchData";

	/**
	 * @return the type will be included as rdf type of the resource under field
	 *         name TYPE
	 */
	protected abstract String getType();

	protected abstract List<ValidationError> validate();

	private String title;
	private String titleLanguage;
	private String alternativeTitle;
	private List<String> creator;
	private List<String> contributor;
	private String yearOfCopyright;
	private String license;
	private String description;
	private String professionalGroup;
	private String embargo;
	private List<String> ddc;
	private String language;
	private String medium;
	private List<String> dataOrigin;
	private List<String> subject;
	private List<String> doi;
	private List<String> urn;
	private List<String> isLike;
	private List<String> funding;
	private List<String> projectId;
	private List<String> fundingProgram;
	private List<String> recordingLocation;
	private List<String> recordingCoordinates;
	private String recordingPeriod;
	private List<String> previousVersion;
	private List<String> nextVersion;
	private List<String> contributorOrder;
	private List<String> subjectOrder;
	private List<String> associatedPublications;
	private List<String> associatedDatasets;
	private List<String> references;
	private List<String> creatorName;
	private List<String> subjectName;
	private List<String> contributorName;
	private String usageManual;
	private String reviewStatus;
	private String congressTitle;
	private String congressLocation;
	private String congressDuration;
	private String isbn;
	private String publisher;
	private String publicationPlace;
	private String abstractText;

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

	public List<String> getDataOrigin() {
		return dataOrigin;
	}

	public void setDataOrigin(List<String> dataOrigin) {
		this.dataOrigin = dataOrigin;
	}

	public void setDataOrigin(String in) {
		if (dataOrigin == null || dataOrigin.isEmpty())
			dataOrigin = new ArrayList<>();
		dataOrigin.add(in);
	}

	public List<String> getContributor() {
		return contributor;
	}

	public void setContributor(List<String> contributor) {
		this.contributor = contributor;
	}

	public List<String> getFunding() {
		return funding;
	}

	public void setFunding(List<String> funding) {
		this.funding = funding;
	}

	public List<String> getRecordingLocation() {
		return recordingLocation;
	}

	public void setRecordingLocation(List<String> recordingLocation) {
		this.recordingLocation = recordingLocation;
	}

	public String getRecordingPeriod() {
		return recordingPeriod;
	}

	public void setRecordingPeriod(String recordingPeriod) {
		this.recordingPeriod = recordingPeriod;
	}

	public List<String> getPreviousVersion() {
		return previousVersion;
	}

	public void setPreviousVersion(List<String> previousVersion) {
		this.previousVersion = previousVersion;
	}

	public List<String> getNextVersion() {
		return nextVersion;
	}

	public void setNextVersion(List<String> nextVersion) {
		this.nextVersion = nextVersion;
	}

	public List<String> getRecordingCoordinates() {
		return recordingCoordinates;
	}

	public void setRecordingCoordinates(List<String> recordingCoordinates) {
		this.recordingCoordinates = recordingCoordinates;
	}

	public List<String> getUrn() {
		return urn;
	}

	public void setUrn(List<String> urn) {
		this.urn = urn;
	}

	public List<String> getIsLike() {
		return isLike;
	}

	public void setIsLike(List<String> isLike) {
		this.isLike = isLike;
	}

	public void setSubjectOrder(String in) {
		if (subjectOrder == null || subjectOrder.isEmpty())
			subjectOrder = new ArrayList<>();
		subjectOrder.add(in);
	}

	public void setContributorOrder(String in) {
		if (contributorOrder == null || contributorOrder.isEmpty())
			contributorOrder = new ArrayList<>();
		contributorOrder.add(in);
	}

	public void setIsLike(String in) {
		if (isLike == null || isLike.isEmpty())
			isLike = new ArrayList<>();
		isLike.add(in);
	}

	public void setUrn(String in) {
		if (urn == null || urn.isEmpty())
			urn = new ArrayList<>();
		urn.add(in);
	}

	public void setDoi(String in) {
		if (doi == null || doi.isEmpty())
			doi = new ArrayList<>();
		doi.add(in);
	}

	public void setPreviousVersion(String in) {
		if (previousVersion == null || previousVersion.isEmpty())
			previousVersion = new ArrayList<>();
		previousVersion.add(in);
	}

	public void setNextVersion(String in) {
		if (nextVersion == null || nextVersion.isEmpty())
			nextVersion = new ArrayList<>();
		nextVersion.add(in);
	}

	public void setRecordingCoordinates(String in) {
		if (recordingCoordinates == null || recordingCoordinates.isEmpty())
			recordingCoordinates = new ArrayList<>();
		recordingCoordinates.add(in);
	}

	public void setRecordingLocation(String in) {
		if (recordingLocation == null || recordingLocation.isEmpty())
			recordingLocation = new ArrayList<>();
		recordingLocation.add(in);
	}

	public void setFunding(String in) {
		if (funding == null || funding.isEmpty())
			funding = new ArrayList<>();
		funding.add(in);
	}

	public void setDdc(String in) {
		if (ddc == null || ddc.isEmpty())
			ddc = new ArrayList<>();
		ddc.add(in);
	}

	public void setSubject(String in) {
		if (subject == null || subject.isEmpty())
			subject = new ArrayList<>();
		subject.add(in);
	}

	public void setContributor(String in) {
		if (contributor == null || contributor.isEmpty())
			contributor = new ArrayList<>();
		contributor.add(in);
	}

	public void setCreator(String in) {
		if (creator == null || creator.isEmpty())
			creator = new ArrayList<>();
		creator.add(in);
	}

	public void setSubjectOrder(List<String> in) {
		subjectOrder = in;
	}

	public void setContributorOrder(List<String> in) {
		contributorOrder = in;
	}

	public List<String> getContributorOrder() {
		contributorOrder = new ArrayList<>();
		StringBuffer buf = new StringBuffer();
		if (creator != null) {
			for (String str : creator) {
				buf.append(str + "|");
			}
		}
		if (buf.length() == 1) {
			buf.deleteCharAt(buf.length() - 1);
			contributorOrder.add(buf.toString());
		}
		if (contributor != null) {
			for (String str : contributor) {
				buf.append(str + "|");
			}
		}
		if (buf.length() > 0) {
			buf.deleteCharAt(buf.length() - 1);
			contributorOrder.add(buf.toString());
		}
		return contributorOrder;
	}

	public List<String> getSubjectOrder() {
		subjectOrder = new ArrayList<>();
		StringBuffer buf = new StringBuffer();
		if (subject != null) {
			for (String str : subject) {
				buf.append(str + "|");
			}
		}
		if (buf.length() > 0) {
			buf.deleteCharAt(buf.length() - 1);
			subjectOrder.add(buf.toString());
		}
		return subjectOrder;
	}

	public String getTitleLanguage() {
		return titleLanguage;
	}

	public void setTitleLanguage(String titleLanguage) {
		this.titleLanguage = titleLanguage;
	}

	public String getAlternativeTitle() {
		return alternativeTitle;
	}

	public void setAlternativeTitle(String alternativeTitle) {
		this.alternativeTitle = alternativeTitle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getProjectId() {
		return projectId;
	}

	public void setProjectId(List<String> projectId) {
		this.projectId = projectId;
	}

	public void setProjectId(String in) {
		if (projectId == null || projectId.isEmpty())
			projectId = new ArrayList<>();
		projectId.add(in);
	}

	public List<String> getFundingProgram() {
		return fundingProgram;
	}

	public void setFundingProgram(List<String> fundingProgram) {
		this.fundingProgram = fundingProgram;
	}

	public void setFundingProgram(String in) {
		if (fundingProgram == null || fundingProgram.isEmpty())
			fundingProgram = new ArrayList<>();
		fundingProgram.add(in);
	}

	public List<String> getAssociatedPublications() {
		return associatedPublications;
	}

	public void setAssociatedPublications(List<String> associatedPublications) {
		this.associatedPublications = associatedPublications;
	}

	public void setAssociatedPublications(String in) {
		if (associatedPublications == null || associatedPublications.isEmpty())
			associatedPublications = new ArrayList<>();
		associatedPublications.add(in);
	}

	public List<String> getAssociatedDatasets() {
		return associatedDatasets;
	}

	public void setAssociatedDatasets(List<String> associatedDatasets) {
		this.associatedDatasets = associatedDatasets;
	}

	public void setAssociatedDatasets(String in) {
		if (associatedDatasets == null || associatedDatasets.isEmpty())
			associatedDatasets = new ArrayList<>();
		associatedDatasets.add(in);
	}

	public List<String> getReferences() {
		return references;
	}

	public void setReferences(List<String> references) {
		this.references = references;
	}

	public void setReferences(String in) {
		if (references == null || references.isEmpty())
			references = new ArrayList<>();
		references.add(in);
	}

	public List<String> getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(List<String> creatorName) {
		this.creatorName = creatorName;
	}

	public void setCreatorName(String in) {
		if (creatorName == null || creatorName.isEmpty())
			creatorName = new ArrayList<>();
		creatorName.add(in);
	}

	public List<String> getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(List<String> subjectName) {
		this.subjectName = subjectName;
	}

	public void setSubjectName(String in) {
		if (subjectName == null || subjectName.isEmpty())
			subjectName = new ArrayList<>();
		subjectName.add(in);
	}

	public String getUsageManual() {
		return usageManual;
	}

	public void setUsageManual(String subjusageManualectName) {
		this.usageManual = usageManual;
	}

	public List<String> getContributorName() {
		return contributorName;
	}

	public void setContributorName(List<String> contributorName) {
		this.contributorName = contributorName;
	}

	public void setContributorName(String in) {
		if (contributorName == null || contributorName.isEmpty())
			contributorName = new ArrayList<>();
		contributorName.add(in);
	}

	public String getReviewStatus() {
		return reviewStatus;
	}

	public void setReviewStatus(String reviewStatus) {
		this.reviewStatus = reviewStatus;
	}

	public String getCongressTitle() {
		return congressTitle;
	}

	public void setCongressTitle(String congressTitle) {
		this.congressTitle = congressTitle;
	}

	public String getCongressLocation() {
		return congressLocation;
	}

	public void setCongressLocation(String congressLocation) {
		this.congressLocation = congressLocation;
	}

	public String getCongressDuration() {
		return congressDuration;
	}

	public void setCongressDuration(String congressDuration) {
		this.congressDuration = congressDuration;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getPublicationPlace() {
		return publicationPlace;
	}

	public void setPublicationPlace(String publicationPlace) {
		this.publicationPlace = publicationPlace;
	}

	public String getAbstractText() {
		return abstractText;
	}

	public void setAbstractText(String abstractText) {
		this.abstractText = abstractText;
	}

	public String toString() {
		return ZettelHelper.objectToString(serializeToMap());
	}

	/**
	 * @return a map that maps a json name to a getter method
	 */
	protected Map<String, Supplier<Object>> getMappingForSerialization() {
		Map<String, Supplier<Object>> dict = new LinkedHashMap<>();
		addFieldToMap(dict, titleZF.name, () -> getTitle());
		addFieldToMap(dict, creatorZF.name, () -> getCreator());
		addFieldToMap(dict, contributorZF.name, () -> getContributor());
		addFieldToMap(dict, dataOriginZF.name, () -> getDataOrigin());
		addFieldToMap(dict, embargoZF.name, () -> getEmbargo());
		addFieldToMap(dict, languageZF.name, () -> getLanguage());
		addFieldToMap(dict, licenseZF.name, () -> getLicense());
		addFieldToMap(dict, mediumZF.name, () -> getMedium());
		addFieldToMap(dict, professionalGroupZF.name, () -> getProfessionalGroup());
		addFieldToMap(dict, subjectZF.name, () -> getSubject());
		addFieldToMap(dict, yearOfCopyrightZF.name, () -> getYearOfCopyright());
		addFieldToMap(dict, ddcZF.name, () -> getDdc());
		addFieldToMap(dict, fundingZF.name, () -> getFunding());
		addFieldToMap(dict, recordingPeriodZF.name, () -> getRecordingPeriod());
		addFieldToMap(dict, recordingLocationZF.name, () -> getRecordingLocation());
		addFieldToMap(dict, recordingCoordinatesZF.name,
				() -> getRecordingCoordinates());
		addFieldToMap(dict, nextVersionZF.name, () -> getNextVersion());
		addFieldToMap(dict, previousVersionZF.name, () -> getPreviousVersion());
		addFieldToMap(dict, doiZF.name, () -> getDoi());
		addFieldToMap(dict, urnZF.name, () -> getUrn());
		addFieldToMap(dict, isLikeZF.name, () -> getIsLike());
		addFieldToMap(dict, contributorOrderZF.name, () -> getContributorOrder());
		addFieldToMap(dict, subjectOrderZF.name, () -> getSubjectOrder());
		addFieldToMap(dict, alternativeTitleZF.name, () -> getAlternativeTitle());
		addFieldToMap(dict, titleLanguageZF.name, () -> getTitleLanguage());
		addFieldToMap(dict, descriptionZF.name, () -> getDescription());
		addFieldToMap(dict, projectIdZF.name, () -> getProjectId());
		addFieldToMap(dict, fundingProgramZF.name, () -> getFundingProgram());
		addFieldToMap(dict, associatedPublicationZF.name,
				() -> getAssociatedPublications());
		addFieldToMap(dict, associatedDatasetZF.name,
				() -> getAssociatedDatasets());
		addFieldToMap(dict, referenceZF.name, () -> getReferences());
		addFieldToMap(dict, creatorNameZF.name, () -> getCreatorName());
		addFieldToMap(dict, subjectNameZF.name, () -> getSubjectName());
		addFieldToMap(dict, usageManualZF.name, () -> getUsageManual());
		addFieldToMap(dict, contributorNameZF.name, () -> getContributorName());
		addFieldToMap(dict, reviewStatusZF.name, () -> getReviewStatus());
		addFieldToMap(dict, congressTitleZF.name, () -> getCongressTitle());
		addFieldToMap(dict, congressLocationZF.name, () -> getCongressLocation());
		addFieldToMap(dict, congressDurationZF.name, () -> getCongressDuration());
		addFieldToMap(dict, isbnZF.name, () -> getIsbn());
		addFieldToMap(dict, publisherZF.name, () -> getPublisher());
		addFieldToMap(dict, publicationPlaceZF.name, () -> getPublicationPlace());
		addFieldToMap(dict, abstractTextZF.name, () -> getAbstractText());

		return dict;
	}

	private void addFieldToMap(Map<String, Supplier<Object>> dict, String name,
			Supplier<Object> c) {
		Object val = c.get();
		if (val == null)
			return;
		if (val instanceof String) {
			String str = (String) val;
			if (!str.isEmpty()) {
				dict.put(name, c);
			}
		}
		if (val instanceof List) {
			List<String> list = (List<String>) val;
			if (!list.isEmpty() && !containsOnlyNullValues(list)) {
				dict.put(name, c);
			}
		}
	}

	/**
	 * @return a map that maps a uri to a setter method
	 */
	@SuppressWarnings("unchecked")
	protected Map<String, Consumer<Object>> getMappingForDeserialization() {
		Map<String, Consumer<Object>> dict = new LinkedHashMap<>();
		dict.put(titleZF.uri, (in) -> setTitle((String) in));
		dict.put(creatorZF.uri, (in) -> setCreator((String) in));
		dict.put(contributorZF.uri, (in) -> setContributor((String) in));
		dict.put(dataOriginZF.uri, (in) -> setDataOrigin((String) in));
		dict.put(embargoZF.uri, (in) -> setEmbargo((String) in));
		dict.put(languageZF.uri, (in) -> setLanguage((String) in));
		dict.put(licenseZF.uri, (in) -> setLicense((String) in));
		dict.put(mediumZF.uri, (in) -> setMedium((String) in));
		dict.put(professionalGroupZF.uri,
				(in) -> setProfessionalGroup((String) in));
		dict.put(subjectZF.uri, (in) -> setSubject((String) in));
		dict.put(yearOfCopyrightZF.uri, (in) -> setYearOfCopyright((String) in));
		dict.put(ddcZF.uri, (in) -> setDdc((String) in));
		dict.put(fundingZF.uri, (in) -> setFunding((String) in));
		dict.put(recordingPeriodZF.uri, (in) -> setRecordingPeriod((String) in));
		dict.put(recordingLocationZF.uri,
				(in) -> setRecordingLocation((String) in));
		dict.put(recordingCoordinatesZF.uri,
				(in) -> setRecordingCoordinates((String) in));
		dict.put(nextVersionZF.uri, (in) -> setNextVersion((String) in));
		dict.put(previousVersionZF.uri, (in) -> setPreviousVersion((String) in));
		dict.put(doiZF.uri, (in) -> setDoi((String) in));
		dict.put(urnZF.uri, (in) -> setUrn((String) in));
		dict.put(isLikeZF.uri, (in) -> setIsLike((String) in));
		dict.put(contributorOrderZF.uri, (in) -> setContributorOrder((String) in));
		dict.put(subjectOrderZF.uri, (in) -> setSubjectOrder((String) in));
		dict.put(alternativeTitleZF.uri, (in) -> setAlternativeTitle((String) in));
		dict.put(titleLanguageZF.uri, (in) -> setTitleLanguage((String) in));
		dict.put(descriptionZF.uri, (in) -> setDescription((String) in));
		dict.put(projectIdZF.uri, (in) -> setProjectId((String) in));
		dict.put(fundingProgramZF.uri, (in) -> setFundingProgram((String) in));
		dict.put(associatedPublicationZF.uri,
				(in) -> setAssociatedPublications((String) in));
		dict.put(associatedDatasetZF.uri,
				(in) -> setAssociatedDatasets((String) in));
		dict.put(referenceZF.uri, (in) -> setReferences((String) in));
		dict.put(usageManualZF.uri, (in) -> setUsageManual((String) in));
		dict.put(subjectNameZF.uri, (in) -> setSubjectName((String) in));
		dict.put(creatorNameZF.uri, (in) -> setCreatorName((String) in));
		dict.put(contributorNameZF.uri, (in) -> setContributorName((String) in));
		dict.put(reviewStatusZF.uri, (in) -> setReviewStatus((String) in));
		dict.put(congressTitleZF.uri, (in) -> setCongressTitle((String) in));
		dict.put(congressLocationZF.uri, (in) -> setCongressLocation((String) in));
		dict.put(congressDurationZF.uri, (in) -> setCongressDuration((String) in));
		dict.put(isbnZF.uri, (in) -> setIsbn((String) in));
		dict.put(publisherZF.uri, (in) -> setPublisher((String) in));
		dict.put(publicationPlaceZF.uri, (in) -> setPublicationPlace((String) in));
		dict.put(abstractTextZF.uri, (in) -> setAbstractText((String) in));
		return dict;
	}

	protected static void addErrorMessage(String fieldName, String message,
			Supplier<String> getValue, List<ValidationError> errors) {
		if (getValue.get() == null || getValue.get().isEmpty()) {
			errors.add(new ValidationError(fieldName, message));
		}
	}

	protected static boolean containsOnlyNullValues(List<String> list) {
		if (list == null || list.isEmpty())
			return true;
		for (String i : list) {
			if (i != null && !i.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @param in input stream with rdf data
	 * @param format format of the rdf serialization
	 * @return the rdf data loaded to a ZettelModel
	 */
	public ZettelModel deserializeFromRdf(InputStream in, RDFFormat format) {
		Map<String, Consumer<Object>> dict = getMappingForDeserialization();
		Graph graph = RdfUtils.readRdfToGraph(in, format, getDocumentId());
		graph.forEach((st) -> {
			// play.Logger.debug(st + "");
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
		if (name == null) {
			play.Logger.debug("Null key in" + o);
			return;
		}
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
