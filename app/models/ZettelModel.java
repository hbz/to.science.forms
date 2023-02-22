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
package models;

import static services.ZettelFields.typeZF; 
import static services.ZettelFields.abstractTextZF;
import static services.ZettelFields.additionalMaterialZF;
import static services.ZettelFields.affiliationIndexZF;
import static services.ZettelFields.affiliationZF;
import static services.ZettelFields.alternativeTitleZF;
import static services.ZettelFields.articleNumberZF;
import static services.ZettelFields.associatedDatasetZF;
import static services.ZettelFields.associatedPublicationZF;
import static services.ZettelFields.bibliographicCitationZF;
import static services.ZettelFields.citationReferenceZF;
import static services.ZettelFields.collectionOneZF;
import static services.ZettelFields.congressDurationZF;
import static services.ZettelFields.congressHostZF;
import static services.ZettelFields.congressLocationZF;
import static services.ZettelFields.congressTitleZF;
import static services.ZettelFields.containedInZF;
import static services.ZettelFields.contributorNameZF;
import static services.ZettelFields.contributorOrderZF;
import static services.ZettelFields.contributorZF; 
import static services.ZettelFields.creatorNameZF;
import static services.ZettelFields.creatorZF;
import static services.ZettelFields.dataOriginZF;
import static services.ZettelFields.ddcZF;
import static services.ZettelFields.descriptionZF;
import static services.ZettelFields.doiZF;
import static services.ZettelFields.editorZF;
import static services.ZettelFields.embargoTimeZF;
import static services.ZettelFields.fulltextVersionZF;
import static services.ZettelFields.fundingProgramZF;
import static services.ZettelFields.fundingZF;
import static services.ZettelFields.fundingIdZF;
import static services.ZettelFields.institutionZF;
import static services.ZettelFields.isLikeZF;
import static services.ZettelFields.isbnZF;
import static services.ZettelFields.issnZF;
import static services.ZettelFields.issueZF;
import static services.ZettelFields.issuedZF;
import static services.ZettelFields.languageZF;
import static services.ZettelFields.licenseZF;
import static services.ZettelFields.mediumZF;
import static services.ZettelFields.nextVersionZF;
import static services.ZettelFields.pagesZF;
import static services.ZettelFields.previousVersionZF;
import static services.ZettelFields.professionalGroupZF;
import static services.ZettelFields.projectIdZF;
import static services.ZettelFields.publicationPlaceZF;
import static services.ZettelFields.publicationStatusZF;
import static services.ZettelFields.publicationYearZF;
import static services.ZettelFields.publisherVersionZF;
import static services.ZettelFields.publisherZF;
import static services.ZettelFields.recordingCoordinatesZF;
import static services.ZettelFields.recordingLocationZF;
import static services.ZettelFields.recordingPeriodZF;
import static services.ZettelFields.otherZF;
import static services.ZettelFields.referenceZF;
import static services.ZettelFields.reviewStatusZF;
import static services.ZettelFields.subjectNameZF;
import static services.ZettelFields.subjectZF;
import static services.ZettelFields.titleLanguageZF;
import static services.ZettelFields.titleZF;
import static services.ZettelFields.urnZF;
import static services.ZettelFields.usageManualZF;
import static services.ZettelFields.volumeInZF;
import static services.ZettelFields.yearOfCopyrightZF;
import static services.ZettelFields.parallelEditionZF;
import static services.ZettelFields.collectionTwoZF;
import static services.ZettelFields.internalReferenceZF;
import static services.ZettelFields.additionalNotesZF;

import static services.ZettelFields.livestockZF;
import static services.ZettelFields.treatmentZF;
import static services.ZettelFields.housingZF;
import static services.ZettelFields.treatmentdetailZF;
import static services.ZettelFields.ventilationZF;
import static services.ZettelFields.emissionprobeZF;
import static services.ZettelFields.emissionZF;
import static services.ZettelFields.emissionreduceZF;
import static services.ZettelFields.projecttitleZF; 

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.rdf4j.model.BNode;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.rio.RDFFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import play.Play;
import play.data.validation.ValidationError;
import services.MyURLEncoding;
import services.RdfUtils;
import services.ZettelHelper;

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
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public abstract class ZettelModel {

	public static final String ZETTEL_NULL = "info:regal/zettel/null";
	static final String TYPE = "type";
	static final String IS_PRIMARY_TOPIC_OF = "isPrimaryTopicOf";
	static final String PRIMARY_TOPIC = "primaryTopic";
	static final String ID = "@id";
	static final String LABEL = "prefLabel";
	@JsonProperty(ID)
	private String documentId;
	private String topicId;

	public abstract List<ValidationError> validate();

	private String id;
	private String [] label = new String[2];
	private String role;

	private String title;
	private String titleLanguage;
	private String alternative;
	private List<String> creator = new ArrayList<>();
	private List<String> contributor = new ArrayList<>();
	private String yearOfCopyright;
	private String license;
	private String description;
	private List<String> professionalGroup;
	private String embargoTime;
	private List<String> ddc = new ArrayList<>();
	private String language;
	private String medium;
	private List<String> dataOrigin = new ArrayList<>();
	private List<String> subject = new ArrayList<>();
	private List<String> doi = new ArrayList<>();
	private List<String> urn = new ArrayList<>();
	private List<String> isLike = new ArrayList<>();
	private List<String> funding = new ArrayList<>();
	private List<String> fundingId = new ArrayList<>();
	private List<String> projectId = new ArrayList<>();
	private List<String> fundingProgram = new ArrayList<>();
	private List<String> recordingLocation = new ArrayList<>();
	private List<String> recordingCoordinates = new ArrayList<>();
	private String recordingPeriod;
	private String previousVersion;
	private String nextVersion;
	private List<String> contributorOrder = new ArrayList<>();
	private List<String> associatedPublication = new ArrayList<>();
	private List<String> associatedDataset = new ArrayList<>();
	private List<String> reference = new ArrayList<>();
	private List<String> subjectName;
	private String usageManual;
	private String reviewStatus;
	private String congressTitle;
	private String congressLocation;
	private List<String> congressDuration = new ArrayList<>();
	private String congressNumber;
	private List<String> congressHost = new ArrayList<>();
	private String isbn;
	private String publisher;
	private String publicationPlace;
	private List<String> abstractText = new ArrayList<>();
	private List<String> containedIn = new ArrayList<>();
	private String bibliographicCitation;
	private String citationReference;
	private String volumeIn;
	private String issue;
	private String pages;
	private String articleNumber;
	private String publicationStatus;
	private String issn;
	private List<String> editor = new ArrayList<>();
	private List<String> other = new ArrayList<>();
	private List<String> institution = new ArrayList<>();
	private String publicationYear;
	private String issued;
	private List<String> affiliation = new ArrayList<>();
	private String affiliationIndex;
	private List<String> collectionOne = new ArrayList<>();
	private List<String> publisherVersion = new ArrayList<>();
	private List<String> fulltextVersion = new ArrayList<>();
	private List<String> additionalMaterial = new ArrayList<>();
	private String parallelEdition;
	private List<String> type = new ArrayList<>();
	private List<String> collectionTwo = new ArrayList<>();
	private List<String> internalReference = new ArrayList<>();
	private String additionalNotes;

	//ktbl associated variables for ResearchDataForm 
	private String livestock;
	private List<String> treatment = new ArrayList<String>();
	private List<String> housing = new ArrayList<String>();
	private List<String> treatmentdetail = new ArrayList<String>();
	private String ventilation;
	private List<String> emissionprobe = new ArrayList<String>();
	private List<String> emission = new ArrayList<String>();
	private List<String> emissionreduce = new ArrayList<String>();
	private String projecttitle;
	

	public List<String> getRdftype() {
		return type;
	}

	public void setRdftype(List<String> in) {
		type = in;
	}

	public void addType(String in) {
		if (type == null || type.isEmpty())
			type = new ArrayList<>();
		type.add(in);
	}
	
	public String getIssued() {
		return issued;
	}

	public void setIssued(String issued) {
		this.issued = issued;
	}

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

	public void addCongressHost(String in) {
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

	public void addContainedIn(String in) {
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
	
	public String getCitationReference() {
		return citationReference;
	}
	
	public void setCitationReference(String citationReference) {
		this.citationReference = citationReference;
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

	public void setProfessionalGroup(List<String> professionalGroup) {
		this.professionalGroup = professionalGroup;
	}

	public void addProfessionalGroup(String in) {
		if (professionalGroup == null || professionalGroup.isEmpty())
			professionalGroup = new ArrayList<>();
		professionalGroup.add(in);
	}

	public List<String> getProfessionalGroup() {
		return professionalGroup;
	}

	public String getEmbargoTime() {
		return embargoTime;
	}

	public void setEmbargoTime(String embargo) {
		this.embargoTime = embargo;
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

	public void addDataOrigin(String in) {
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

	public List<String> getFundingId() {
		return fundingId;
	}

	public void setFundingId(List<String> fundingId) {
		this.fundingId = fundingId;
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

	public void addContributorOrder(String in) {
		if (contributorOrder == null || contributorOrder.isEmpty())
			contributorOrder = new ArrayList<>();
		contributorOrder.add(in);
	}

	public void addIsLike(String in) {
		if (isLike == null || isLike.isEmpty())
			isLike = new ArrayList<>();
		isLike.add(in);
	}

	public void addUrn(String in) {
		if (urn == null || urn.isEmpty())
			urn = new ArrayList<>();
		urn.add(in);
	}

	public void addDoi(String in) {
		if (doi == null || doi.isEmpty())
			doi = new ArrayList<>();
		doi.add(in);
	}

	public void addRecordingCoordinates(String in) {
		if (recordingCoordinates == null || recordingCoordinates.isEmpty())
			recordingCoordinates = new ArrayList<>();
		recordingCoordinates.add(in);
	}

	public void addRecordingLocation(String in) {
		if (recordingLocation == null || recordingLocation.isEmpty())
			recordingLocation = new ArrayList<>();
		recordingLocation.add(in);
	}

	public void addFunding(String in) {
		if (funding == null || funding.isEmpty())
			funding = new ArrayList<>();
		funding.add(in);
	}

	public void addFundingId(String in) {
		if (fundingId == null || fundingId.isEmpty())
			fundingId = new ArrayList<>();
		fundingId.add(in);
	}

	public void addDdc(String in) {
		if (ddc == null || ddc.isEmpty())
			ddc = new ArrayList<>();
		ddc.add(in);
	}

	public void addSubject(String in) {
		if (subject == null || subject.isEmpty())
			subject = new ArrayList<>();
		subject.add(in);
	}

	public void addContributor(String in) {
		if (contributor == null || contributor.isEmpty())
			contributor = new ArrayList<>();
		if (in != null && !in.isEmpty())
			contributor.add(in);
	}

	public void addCreator(String in) {
		// play.Logger.debug("Add creator " + in);
		if (creator == null || creator.isEmpty())
			creator = new ArrayList<>();
		if (in != null && !in.isEmpty())
			creator.add(in);
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

	public void addProjectId(String in) {
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

	public void addFundingProgram(String in) {
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

	public void addAssociatedPublication(String in) {
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

	public void addAssociatedDataset(String in) {
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

	public void addReference(String in) {
		if (reference == null || reference.isEmpty())
			reference = new ArrayList<>();
		reference.add(in);
	}

	public void setCreatorName(List<String> creatorName) {
		this.creator = creatorName;
	}

	public void addCreatorName(String in) {
		if (creator == null || creator.isEmpty())
			creator = new ArrayList<>();
		creator.add(in);
	}

	public List<String> setCreatorName() {
		return creator;
	}

	public void setSubjectName(List<String> subjectName) {
		this.subjectName = subjectName;
	}

	public void setSubjectName(String in) {
		if (subjectName == null || subjectName.isEmpty())
			subjectName = new ArrayList<>();
		subjectName.add(in);
	}

	public List<String> getSubjectName() {
		return subjectName;
	}

	public String getUsageManual() {
		return usageManual;
	}

	public void setUsageManual(String usageManual) {
		this.usageManual = usageManual;
	}

	public void setContributorName(List<String> contributorName) {
		this.contributor = contributorName;
	}

	public void addContributorName(String in) {
		if (contributor == null || contributor.isEmpty())
			contributor = new ArrayList<>();
		contributor.add(in);
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

	public void addCongressDuration(String in) {
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

	public List<String> getAbstractText() {
		return abstractText;
	}

	public void setAbstractText(List<String> abstractText) {
		this.abstractText = abstractText;
	}

	public void addAbstractText(String in) {
		if (abstractText == null || abstractText.isEmpty())
			abstractText = new ArrayList<>();
		abstractText.add(in);
	}

	public List<String> getEditor() {
		return editor;
	}

	public void setEditor(List<String> editor) {
		this.editor = editor;
	}

	public void addEditor(String in) {
		if (editor == null || editor.isEmpty())
			editor = new ArrayList<>();
		editor.add(in);
	}

	public List<String> getOther() {
		return other;
	}

	public void setOther(List<String> other) {
		this.other = other;
	}

	public void addOther(String in) {
		if (other == null || other.isEmpty())
			other = new ArrayList<>();
		other.add(in);
	}

	public List<String> getInstitution() {
		return institution;
	}

	public void setInstitution(List<String> in) {
		institution = in;
	}

	public void addInstitution(String in) {
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

	public String getLabel(int i) {
		return label[i];
	}

	public void setLabel(String[] label) {
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

	public void addAffiliation(String in) {
		if (affiliation == null || affiliation.isEmpty())
			affiliation = new ArrayList<>();
		affiliation.add(in);
	}

	public String getAffiliationIndex() {
		return affiliationIndex;
	}

	public void setAffiliationIndex(String affiliationIndex) {
		this.affiliationIndex = affiliationIndex;
	}

	public String getParallelEdition() {
		return parallelEdition;
	}

	public void setParallelEdition(String parallelEdition) {
		this.parallelEdition = parallelEdition;
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

	public List<String> getCollectionOne() {
		return collectionOne;
	}

	public void setCollectionOne(List<String> collectionOne) {
		this.congressHost = collectionOne;
	}

	public void addCollectionOne(String in) {
		if (collectionOne == null || collectionOne.isEmpty())
			collectionOne = new ArrayList<>();
		collectionOne.add(in);
	}

	public List<String> getAdditionalMaterial() {
		return additionalMaterial;
	}

	public void setAdditionalMaterial(List<String> in) {
		additionalMaterial = in;
	}

	public void addAdditionalMaterial(String in) {
		if (additionalMaterial == null || additionalMaterial.isEmpty())
			additionalMaterial = new ArrayList<>();
		additionalMaterial.add(in);
	}

	public List<String> getInternalReference() {
		return internalReference;
	}

	public void setInternalReference(List<String> in) {
		internalReference = in;
	}

	public void addInternalReference(String in) {
		if (internalReference == null || internalReference.isEmpty())
			internalReference = new ArrayList<>();
		internalReference.add(in);
	}

	public List<String> getPublisherVersion() {
		return publisherVersion;
	}

	public void setPublisherVersion(List<String> in) {
		publisherVersion = in;
	}

	public void addPublisherVersion(String in) {
		if (publisherVersion == null || publisherVersion.isEmpty())
			publisherVersion = new ArrayList<>();
		publisherVersion.add(in);
	}

	public List<String> getFulltextVersion() {
		return fulltextVersion;
	}

	public void setFulltextVersion(List<String> in) {
		fulltextVersion = in;
	}

	public void addFulltextVersion(String in) {
		if (fulltextVersion == null || fulltextVersion.isEmpty())
			fulltextVersion = new ArrayList<>();
		fulltextVersion.add(in);
	}

	public List<String> getCollectionTwo() {
		return collectionTwo;
	}

	public void setCollectionTwo(List<String> collectionTwo) {
		this.congressHost = collectionTwo;
	}

	public void addCollectionTwo(String in) {
		if (collectionTwo == null || collectionTwo.isEmpty())
			collectionTwo = new ArrayList<>();
		collectionTwo.add(in);
	}

	public String getAdditionalNotes() {
		return additionalNotes;
	}

	public void setAdditionalNotes(String notes) {
		this.additionalNotes = notes;
	}
	
	public String getLivestock() {
		return livestock;
	}

	public void setLivestock(String livestock) {
		this.livestock = livestock;
	}

	
	public void addTreatment(String in) {
		if (treatment == null || treatment.isEmpty())
			treatment = new ArrayList<>();
		treatment.add(in);
	}

	public List<String> getTreatment(){
		return this.treatment;
	}
	
	public void setTreatment(List<String> treatment) {
		this.treatment = treatment;
	}

	public void addHousing(String in) {
		if (housing == null || housing.isEmpty())
			housing = new ArrayList<>();
		housing.add(in);
	}

	public List<String> getHousing() {
		return housing;
	}

	public void setHousing(List<String> housing) {
		this.housing = housing;
	}
	
	public void addTreatmentdetail(String in) {
		if (treatmentdetail == null || treatmentdetail.isEmpty())
			treatmentdetail = new ArrayList<>();
		treatmentdetail.add(in);
	}

	public List<String> getTreatmentdetail() {
		return treatmentdetail;
	}

	public void setTreatmentdetail(List<String> treatmentdetail) {
		this.treatmentdetail = treatmentdetail;
	}

	public String getVentilation() {
		return ventilation;
	}

	public void setVentilation(String ventilation) {
		this.ventilation = ventilation;
	}

	public List<String> getEmissionprobe() {
		return emissionprobe;
	}

	public void setEmissionprobe(List<String> emissionprobe) {
		this.emissionprobe = emissionprobe;
	}

	public void addEmissionprobe(String in) {
		if (emissionprobe == null || emissionprobe.isEmpty())
			emissionprobe = new ArrayList<>();
		emissionprobe.add(in);
	}

	public List<String> getEmission() {
		return emission;
	}

	public void setEmission(List<String> emission) {
		this.emission = emission;
	}

	public void addEmission(String in) {
		if (emission == null || emission.isEmpty())
			emission = new ArrayList<>();
		emission.add(in);
	}
	
	public List<String> getEmissionreduce() {
		return emissionreduce;
	}

	public void setEmissionreduce(List<String> emissionreduce) {
		this.emissionreduce = emissionreduce;
	}

	public void addEmissionreduce(String in) {
		if (emissionreduce == null || emissionreduce.isEmpty())
			emissionreduce = new ArrayList<>();
		emissionreduce.add(in);
	}
	
	public String getProjecttitle() {
		return projecttitle;
	}

	public void setProjecttitle(String projecttitle) {
		this.projecttitle = projecttitle;
	}


	/**
	 * @return a map that maps a uri to a setter method
	 */
	protected Map<String, Consumer<Object>> getMappingForDeserialization() {
		String regalApi = Play.application().configuration().getString("regalApi");
		Map<String, Consumer<Object>> dict = new LinkedHashMap<>();
		dict.put(titleZF.uri, (in) -> setTitle((String) in));
		dict.put(creatorZF.uri, (in) -> addCreator((String) in));
		dict.put(contributorZF.uri, (in) -> addContributor((String) in));
		dict.put(dataOriginZF.uri, (in) -> addDataOrigin((String) in));
		dict.put(embargoTimeZF.uri, (in) -> setEmbargoTime((String) in));
		dict.put(languageZF.uri, (in) -> setLanguage((String) in));
		dict.put(licenseZF.uri, (in) -> setLicense((String) in));
		dict.put(mediumZF.uri, (in) -> setMedium((String) in));
		dict.put(professionalGroupZF.uri,
				(in) -> addProfessionalGroup((String) in));
		dict.put(subjectZF.uri, (in) -> addSubject((String) in));
		dict.put(yearOfCopyrightZF.uri, (in) -> setYearOfCopyright((String) in));
		dict.put(ddcZF.uri, (in) -> addDdc((String) in));
		dict.put(fundingZF.uri, (in) -> addFunding((String) in));
		dict.put(fundingIdZF.uri, (in) -> addFundingId((String) in));
		dict.put(recordingPeriodZF.uri, (in) -> setRecordingPeriod((String) in));
		dict.put(recordingLocationZF.uri,
				(in) -> addRecordingLocation((String) in));
		dict.put(recordingCoordinatesZF.uri,
				(in) -> addRecordingCoordinates((String) in));
		dict.put(nextVersionZF.uri, (in) -> setNextVersion((String) in));
		dict.put(previousVersionZF.uri, (in) -> setPreviousVersion((String) in));
		dict.put(doiZF.uri, (in) -> addDoi((String) in));
		dict.put(urnZF.uri, (in) -> addUrn((String) in));
		dict.put(isLikeZF.uri, (in) -> addIsLike((String) in));
		dict.put(contributorOrderZF.uri, (in) -> addContributorOrder((String) in));
		dict.put(alternativeTitleZF.uri, (in) -> setAlternative((String) in));
		dict.put(titleLanguageZF.uri, (in) -> setTitleLanguage((String) in));
		dict.put(descriptionZF.uri, (in) -> setDescription((String) in));
		dict.put(projectIdZF.uri, (in) -> addProjectId((String) in));
		dict.put(fundingProgramZF.uri, (in) -> addFundingProgram((String) in));
		dict.put(associatedPublicationZF.uri,
				(in) -> addAssociatedPublication((String) in));
		dict.put(associatedDatasetZF.uri,
				(in) -> addAssociatedDataset((String) in));
		dict.put(referenceZF.uri, (in) -> addReference((String) in));
		dict.put(usageManualZF.uri, (in) -> setUsageManual((String) in));
		dict.put(subjectNameZF.uri, (in) -> setSubjectName((String) in));
		dict.put(creatorNameZF.uri, (in) -> addCreatorName(
				regalApi + "/adhoc/creator/" + MyURLEncoding.encode((String) in)));
		dict.put(contributorNameZF.uri, (in) -> addContributorName(
				regalApi + "/adhoc/contributor/" + MyURLEncoding.encode((String) in)));
		dict.put(reviewStatusZF.uri, (in) -> setReviewStatus((String) in));
		dict.put(congressTitleZF.uri, (in) -> setCongressTitle((String) in));
		dict.put(congressLocationZF.uri, (in) -> setCongressLocation((String) in));
		dict.put(congressDurationZF.uri, (in) -> addCongressDuration((String) in));
		dict.put(isbnZF.uri, (in) -> setIsbn((String) in));
		dict.put(publisherZF.uri, (in) -> setPublisher((String) in));
		dict.put(publicationPlaceZF.uri, (in) -> setPublicationPlace((String) in));
		dict.put(abstractTextZF.uri, (in) -> addAbstractText((String) in));
		dict.put(containedInZF.uri, (in) -> addContainedIn((String) in));
		dict.put(bibliographicCitationZF.uri,
				(in) -> setBibliographicCitation((String) in));
		dict.put(citationReferenceZF.uri, (in) -> setCitationReference((String) in));
		dict.put(congressHostZF.uri, (in) -> addCongressHost((String) in));
		dict.put(volumeInZF.uri, (in) -> setVolumeIn((String) in));
		dict.put(issueZF.uri, (in) -> setIssue((String) in));
		dict.put(pagesZF.uri, (in) -> setPages((String) in));
		dict.put(articleNumberZF.uri, (in) -> setArticleNumber((String) in));
		dict.put(publicationStatusZF.uri,
				(in) -> setPublicationStatus((String) in));
		dict.put(issnZF.uri, (in) -> setIssn((String) in));
		dict.put(editorZF.uri, (in) -> addEditor((String) in));
		dict.put(otherZF.uri, (in) -> addOther((String) in));
		dict.put(institutionZF.uri, (in) -> addInstitution((String) in));
		dict.put(issuedZF.uri, (in) -> setIssued((String) in));
		dict.put(publicationYearZF.uri, (in) -> setPublicationYear((String) in));
		dict.put(affiliationZF.uri, (in) -> addAffiliation((String) in));
		dict.put(affiliationIndexZF.uri, (in) -> setAffiliationIndex((String) in));
		dict.put(collectionOneZF.uri, (in) -> addCollectionOne((String) in));
		dict.put(fulltextVersionZF.uri, (in) -> addFulltextVersion((String) in));
		dict.put(publisherVersionZF.uri, (in) -> addPublisherVersion((String) in));
		dict.put(additionalMaterialZF.uri,
				(in) -> addAdditionalMaterial((String) in));
		dict.put(parallelEditionZF.uri, (in) -> setParallelEdition((String) in));
		dict.put(typeZF.uri, (in) -> addType((String) in));
		dict.put(collectionTwoZF.uri, (in) -> addCollectionTwo((String) in));
		dict.put(internalReferenceZF.uri,
				(in) -> addInternalReference((String) in));

		dict.put(additionalNotesZF.uri, (in) -> setAdditionalNotes((String) in));

		dict.put(livestockZF.uri, (in) -> setLivestock((String) in));
		dict.put(treatmentZF.uri, (in) -> addTreatment((String) in)); 
		dict.put(treatmentdetailZF.uri, (in) -> addTreatmentdetail((String) in));
		dict.put(housingZF.uri, (in) -> addHousing((String) in));
		dict.put(ventilationZF.uri, (in) -> setVentilation((String) in));
		dict.put(emissionprobeZF.uri, (in) -> addEmissionprobe((String) in));
		dict.put(emissionZF.uri, (in) -> addEmission((String) in));
		dict.put(emissionreduceZF.uri, (in) -> addEmissionreduce((String) in));
		dict.put(projecttitleZF.uri, (in) -> setProjecttitle((String) in));
		return dict;
	}

	protected static boolean containsNothing(List<String> list) {
		if (list == null || list.isEmpty())
			return true;
		for (String i : list) {
			if (ZETTEL_NULL.equals(i)) {
				return true;
			}
			if (!StringUtils.isEmpty(i)) {
				return false;
			}
		}
		return true;
	}

	protected static boolean containsNothing(String value) {
		return StringUtils.isEmpty(value) || ZETTEL_NULL.equals(value);
	}

	/**
	 * @param in input stream with rdf data
	 * @param format format of the rdf serialization
	 * @return the rdf data loaded to a ZettelModel
	 */
	public ZettelModel deserializeFromRdf(InputStream in, RDFFormat format,
			String myDocumentId, String myTopicId) {
		this.documentId = myDocumentId;
		this.topicId = myTopicId;
		Map<String, Consumer<Object>> dict = getMappingForDeserialization();
		Collection<Statement> graph =
				RdfUtils.readRdfToGraph(in, format, getDocumentId());
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
		jsonMap.put(ZettelModel.IS_PRIMARY_TOPIC_OF, getIsPrimaryTopicOf());
		// jsonMap.put("rdftype", getType());
		removeEmptyCollections(jsonMap);
		jsonMap.put("@context", ZettelHelper.etikett.getContext().get("@context"));
		return jsonMap;
	}

	@SuppressWarnings("unchecked")
	private boolean removeEmptyCollections(Map<String, Object> jsonMap) {
		Iterator<Map.Entry<String, Object>> it = jsonMap.entrySet().iterator();
		while (it.hasNext()) {
			boolean r = false;
			Map.Entry<String, Object> e = it.next();
			if (e.getValue() instanceof java.util.Map)
				r = removeEmptyCollections(
						(java.util.Map<String, Object>) e.getValue());
			else if (e.getValue() instanceof java.util.List<?>)
				r = removeEmptyCollections((java.util.List<Object>) e.getValue());
			else if (e.getValue() instanceof String) {
				r = removeEmptyCollections((String) e.getValue());
			}

			if (r)
				it.remove();
		}
		return jsonMap.isEmpty();
	}

	private boolean removeEmptyCollections(String value) {
		if (value == null || value.isEmpty() || ZETTEL_NULL.equals(value))
			return true;
		return false;
	}

	private boolean removeEmptyCollections(List<Object> value) {
		Iterator<Object> it = value.iterator();
		while (it.hasNext()) {
			boolean r = false;
			Object e = it.next();
			if (e instanceof java.util.Map)
				r = removeEmptyCollections((java.util.Map<String, Object>) e);
			else if (e instanceof java.util.List<?>)
				r = removeEmptyCollections((java.util.List<Object>) e);
			else if (e instanceof String) {
				r = removeEmptyCollections((String) e);
			}
			if (r)
				it.remove();
		}
		return value.isEmpty();
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

	private static void processField(Collection<Statement> graph, Statement st,
			Consumer<Object> consumer) {
		Value rdf_O = st.getObject();
		if (rdf_O instanceof BNode) {
			RdfUtils.traverseList(graph, ((BNode) rdf_O).getID(), RdfUtils.first,
					consumer);
		} else if (RdfUtils.nil.equals(rdf_O)) {
			return;
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

	public void setIsPrimaryTopicOf(Map<String, Object> jsonMap) {
		topicId = (String) jsonMap.get(ZettelModel.ID);
		documentId = (String) jsonMap.get(ZettelModel.PRIMARY_TOPIC);
	}

	public Map<String, Object> getIsPrimaryTopicOf() {
		Map<String, Object> topicMap = new HashMap<>();
		topicMap.put(ZettelModel.ID, topicId);
		topicMap.put(ZettelModel.PRIMARY_TOPIC, documentId);
		return topicMap;
	}

	protected String getLabel(String name) {
		return ZettelHelper.etikett.getLabel(name);
	}

}