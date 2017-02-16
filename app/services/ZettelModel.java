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

import static services.ZettelFields.*;

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
import org.openrdf.model.Statement;
import org.openrdf.model.Value;
import org.openrdf.rio.RDFFormat;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import models.Agent;
import models.Contribution;
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
	@JsonProperty(ID)
	private String documentId;
	@JsonProperty(IS_PRIMARY_TOPIC_OF)
	private String topicId;

	/**
	 * @return the type will be included as rdf type of the resource under field
	 *         name TYPE
	 */
	protected abstract String getType();

	protected abstract List<ValidationError> validate();

	private String id;
	private String label;
	private String role;

	private String title;
	private String titleLanguage;
	private String alternative;
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
	private String previousVersion;
	private String nextVersion;
	private List<String> contributorOrder;
	private List<String> subjectOrder;
	private List<String> associatedPublication;
	private List<String> associatedDataset;
	private List<String> reference;
	private List<String> creatorName;
	private List<String> subjectName;
	private List<String> contributorName;
	private String usageManual;
	private String reviewStatus;
	private String congressTitle;
	private String congressLocation;
	private List<String> congressDuration;
	private String congressNumber;
	private List<String> congressHost;
	private String isbn;
	private String publisher;
	private String publicationPlace;
	private String abstractText;
	private List<String> containedIn;
	private String bibliographicCitation;
	private String volumeIn;
	private String issue;
	private String pages;
	private String articleNumber;
	private String publicationStatus;
	private String issn;
	private List<String> editor;
	private List<String> redaktor;
	private List<String> institution;
	private String publicationYear;
	private List<String> affiliation;
	private List<Contribution> contribution;
	private Agent agent;
	private String affiliationIndex;

	public String getPublicationYear() {
		return publicationYear;
	}

	public void setPublicationYear(String publicationYear) {
		this.publicationYear = publicationYear;
	}

	public String getCongressNumber() {
		return congressNumber;
	}

	public void setCongressNumber(String congressNumber) {
		this.congressNumber = congressNumber;
	}

	public List<String> getCongressHost() {
		return congressHost;
	}

	public void setCongressHost(List<String> congressHost) {
		this.congressHost = congressHost;
	}

	public void setCongressHost(String in) {
		if (congressHost == null || congressHost.isEmpty())
			congressHost = new ArrayList<>();
		congressHost.add(in);
	}

	public String getVolumeIn() {
		return volumeIn;
	}

	public void setVolumeIn(String volumeIn) {
		this.volumeIn = volumeIn;
	}

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	public String getPages() {
		return pages;
	}

	public void setPages(String pages) {
		this.pages = pages;
	}

	public String getArticleNumber() {
		return articleNumber;
	}

	public void setArticleNumber(String articleNumber) {
		this.articleNumber = articleNumber;
	}

	public String getPublicationStatus() {
		return publicationStatus;
	}

	public void setPublicationStatus(String publicationStatus) {
		this.publicationStatus = publicationStatus;
	}

	public String getIssn() {
		return issn;
	}

	public void setIssn(String issn) {
		this.issn = issn;
	}

	public List<String> getContainedIn() {
		return containedIn;
	}

	public void setContainedIn(List<String> in) {
		containedIn = in;
	}

	public void setContainedIn(String in) {
		if (containedIn == null || containedIn.isEmpty())
			containedIn = new ArrayList<>();
		containedIn.add(in);
	}

	public String getBibliographicCitation() {
		return bibliographicCitation;
	}

	public void setBibliographicCitation(String bibliographicCitation) {
		this.bibliographicCitation = bibliographicCitation;
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

	public String getPreviousVersion() {
		return previousVersion;
	}

	public void setPreviousVersion(String previousVersion) {
		this.previousVersion = previousVersion;
	}

	public String getNextVersion() {
		return nextVersion;
	}

	public void setNextVersion(String nextVersion) {
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
		play.Logger.debug("Add creator " + in);
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

	public String getAlternative() {
		return alternative;
	}

	public void setAlternative(String alternativeTitle) {
		this.alternative = alternativeTitle;
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

	public List<String> getAssociatedPublication() {
		return associatedPublication;
	}

	public void setAssociatedPublication(List<String> associatedPublication) {
		this.associatedPublication = associatedPublication;
	}

	public void setAssociatedPublication(String in) {
		if (associatedPublication == null || associatedPublication.isEmpty())
			associatedPublication = new ArrayList<>();
		associatedPublication.add(in);
	}

	public List<String> getAssociatedDataset() {
		return associatedDataset;
	}

	public void setAssociatedDataset(List<String> associatedDataset) {
		this.associatedDataset = associatedDataset;
	}

	public void setAssociatedDataset(String in) {
		if (associatedDataset == null || associatedDataset.isEmpty())
			associatedDataset = new ArrayList<>();
		associatedDataset.add(in);
	}

	public List<String> getReference() {
		return reference;
	}

	public void setReference(List<String> reference) {
		this.reference = reference;
	}

	public void setReference(String in) {
		if (reference == null || reference.isEmpty())
			reference = new ArrayList<>();
		reference.add(in);
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

	public void setUsageManual(String usageManual) {
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

	public List<String> getCongressDuration() {
		return congressDuration;
	}

	public void setCongressDuration(List<String> congressDuration) {
		this.congressDuration = congressDuration;
	}

	public void setCongressDuration(String in) {
		if (congressDuration == null || congressDuration.isEmpty())
			congressDuration = new ArrayList<>();
		congressDuration.add(in);
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

	public List<String> getEditor() {
		return editor;
	}

	public void setEditor(List<String> editor) {
		this.editor = editor;
	}

	public void setEditor(String in) {
		if (editor == null || editor.isEmpty())
			editor = new ArrayList<>();
		editor.add(in);
	}

	public List<String> getRedaktor() {
		return redaktor;
	}

	public void setRedaktor(List<String> redaktor) {
		this.redaktor = redaktor;
	}

	public void setRedaktor(String in) {
		if (redaktor == null || redaktor.isEmpty())
			redaktor = new ArrayList<>();
		redaktor.add(in);
	}

	public List<String> getInstitution() {
		return institution;
	}

	public void setInstitution(List<String> in) {
		institution = in;
	}

	public void setInstitution(String in) {
		if (institution == null || institution.isEmpty())
			institution = new ArrayList<>();
		institution.add(in);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<String> getAffiliation() {
		return affiliation;
	}

	public void setAffiliation(List<String> affiliation) {
		this.affiliation = affiliation;
	}

	public void setAffiliation(String in) {
		if (affiliation == null || affiliation.isEmpty())
			affiliation = new ArrayList<>();
		affiliation.add(in);
	}

	public List<Contribution> getContribution() {
		return contribution;
	}

	public void setContribution(List<Contribution> contribution) {
		this.contribution = contribution;
	}

	public String getAffiliationIndex() {
		return affiliationIndex;
	}

	public void setAffiliationIndex(String affiliationIndex) {
		this.affiliationIndex = affiliationIndex;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	@Override
	public String toString() {
		return ZettelHelper.objectToString(serializeToMap());
	}

	public String print() {
		Map<String, Object> m = serializeToMap();
		m.remove("@context");
		return ZettelHelper.objectToString(m);
	}

	private static void addFieldToMap(Map<String, Supplier<Object>> dict,
			String name, Supplier<Object> c) {
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
			@SuppressWarnings("unchecked")
			List<String> list = (List<String>) val;
			if (!list.isEmpty() && !containsOnlyNullValues(list)) {
				dict.put(name, c);
			}
		}
	}

	/**
	 * @return a map that maps a uri to a setter method
	 */
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
		dict.put(alternativeTitleZF.uri, (in) -> setAlternative((String) in));
		dict.put(titleLanguageZF.uri, (in) -> setTitleLanguage((String) in));
		dict.put(descriptionZF.uri, (in) -> setDescription((String) in));
		dict.put(projectIdZF.uri, (in) -> setProjectId((String) in));
		dict.put(fundingProgramZF.uri, (in) -> setFundingProgram((String) in));
		dict.put(associatedPublicationZF.uri,
				(in) -> setAssociatedPublication((String) in));
		dict.put(associatedDatasetZF.uri,
				(in) -> setAssociatedDataset((String) in));
		dict.put(referenceZF.uri, (in) -> setReference((String) in));
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
		dict.put(containedInZF.uri, (in) -> setContainedIn((String) in));
		dict.put(bibliographicCitationZF.uri,
				(in) -> setBibliographicCitation((String) in));
		dict.put(congressHostZF.uri, (in) -> setCongressHost((String) in));
		dict.put(volumeInZF.uri, (in) -> setVolumeIn((String) in));
		dict.put(issueZF.uri, (in) -> setIssue((String) in));
		dict.put(pagesZF.uri, (in) -> setPages((String) in));
		dict.put(articleNumberZF.uri, (in) -> setArticleNumber((String) in));
		dict.put(publicationStatusZF.uri,
				(in) -> setPublicationStatus((String) in));
		dict.put(issnZF.uri, (in) -> setIssn((String) in));
		dict.put(editorZF.uri, (in) -> setEditor((String) in));
		dict.put(redaktorZF.uri, (in) -> setRedaktor((String) in));
		dict.put(institutionZF.uri, (in) -> setInstitution((String) in));
		dict.put(publicationYearZF.uri, (in) -> setPublicationYear((String) in));
		dict.put(affiliationZF.uri, (in) -> setAffiliation((String) in));
		dict.put(affiliationIndexZF.uri, (in) -> setAffiliationIndex((String) in));
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
	public ZettelModel deserializeFromRdf(InputStream in, RDFFormat format,
			String documentId, String topicId) {
		this.documentId = documentId;
		this.topicId = topicId;
		Map<String, Consumer<Object>> dict = getMappingForDeserialization();
		Graph graph = RdfUtils.readRdfToGraph(in, format, getDocumentId());
		graph.forEach((st) -> {
			if (!"".equals(st.getObject().stringValue())) {
				String rdf_P = st.getPredicate().stringValue();
				if (dict.containsKey(rdf_P)) {
					processField(graph, st, dict.get(rdf_P));
				}
			}
		});
		return this;
	}

	/**
	 * @return json ld map for this model
	 */
	public Map<String, Object> serializeToMap() {
		Map<String, Object> jsonMap =
				new ObjectMapper().convertValue(this, HashMap.class);
		addIsPrimaryTopicOf(jsonMap);
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

	private static void processField(Graph graph, Statement st,
			Consumer<Object> consumer) {
		Value rdf_O = st.getObject();
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
