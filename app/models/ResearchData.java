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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.typesafe.config.ConfigFactory;

import play.data.validation.Constraints.Required;
import static services.ZettelFields.*;

import services.ValidUrl;
import services.ZettelHelper;
import services.ZettelModel;

/**
 * @author Jan Schnasse
 *
 */
@SuppressWarnings("javadoc")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ResearchData extends ZettelModel {
	/**
	 * The id under which this model is registered in the ZettelRegister
	 */
	public final static String id = "katalog:data";

	@Required(message = "Bitte vergeben Sie einen Titel!")
	private String title;
	private String titleLanguage;
	private String alternativeTitle;

	private List<String> creator;
	private List<String> contributor;

	@Required(message = "Bitte geben Sie das Jahr zum Copyright an.")
	private String yearOfCopyright;

	@Required(message = "Bitte vergeben Sie eine Lizenz!")
	@ValidUrl(message = "Bitte geben	Sie einen gültigen URL an!")
	private String license;

	@Required(message = "Bitte erstellen Sie eine kurze Inhaltsangabe!")
	private String description;

	@Required(message = "Bitte orden Sie Ihre Daten einer Fachgruppe zu!")
	@ValidUrl(message = "Bitte orden Sie Ihre Daten einer Fachgruppe zu!")
	private String professionalGroup;
	private String embargo;

	@Required(message = "Bitte orden Sie Ihre Daten einem Dewey Schlagwort zu!")
	@ValidUrl(message = "Bitte orden Sie Ihre Daten einem Dewey Schlagwort zu!")
	private List<String> ddc;

	@Required(message = "Welche Sprache passt am ehesten zu Ihrer Eingabe?")
	@ValidUrl(message = "Welche Sprache passt am ehesten zu Ihrer Eingabe?")
	private String language;

	@Required(message = "Bitte ordnen Sie ihre Eingabe einem Medium zu!")
	@ValidUrl(message = "Bitte ordnen Sie ihre Eingabe einem Medium zu!")
	private String medium;

	private String dataOrigin;
	private List<String> subject;
	private List<String> doi;
	private List<String> urn;
	private List<String> isLike;

	private List<String> funding;
	private List<String> projectId;
	private List<String> fundingProgram;

	private List<String> recordingLocation;

	private List<String> recordingCoordinates;

	// private List<String> recordingCoordinatesPolygon;

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
	private String usageManual;

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

	public String getDataOrigin() {
		return dataOrigin;
	}

	public void setDataOrigin(String dataOrigin) {
		this.dataOrigin = dataOrigin;
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

	@Override
	public String toString() {
		return ZettelHelper.objectToString(serializeToMap());
	}

	@Override
	protected Map<String, Supplier<Object>> getMappingForSerialization() {
		Map<String, Supplier<Object>> dict = new LinkedHashMap<>();
		dict.put(titleZF.name, () -> getTitle());
		dict.put(creatorZF.name, () -> getCreator());
		dict.put(contributorZF.name, () -> getContributor());
		dict.put(dataOriginZF.name, () -> getDataOrigin());
		dict.put(embargoZF.name, () -> getEmbargo());
		dict.put(languageZF.name, () -> getLanguage());
		dict.put(licenseZF.name, () -> getLicense());
		dict.put(mediumZF.name, () -> getMedium());
		dict.put(professionalGroupZF.name, () -> getProfessionalGroup());
		dict.put(subjectZF.name, () -> getSubject());
		dict.put(yearOfCopyrightZF.name, () -> getYearOfCopyright());
		dict.put(ddcZF.name, () -> getDdc());
		dict.put(fundingZF.name, () -> getFunding());
		dict.put(recordingPeriodZF.name, () -> getRecordingPeriod());
		dict.put(recordingLocationZF.name, () -> getRecordingLocation());
		dict.put(recordingCoordinatesZF.name, () -> getRecordingCoordinates());
		dict.put(nextVersionZF.name, () -> getNextVersion());
		dict.put(previousVersionZF.name, () -> getPreviousVersion());
		dict.put(doiZF.name, () -> getDoi());
		dict.put(urnZF.name, () -> getUrn());
		dict.put(isLikeZF.name, () -> getIsLike());
		dict.put(contributorOrderZF.name, () -> getContributorOrder());
		dict.put(subjectOrderZF.name, () -> getSubjectOrder());
		dict.put(alternativeTitleZF.name, () -> getAlternativeTitle());
		dict.put(titleLanguageZF.name, () -> getTitleLanguage());
		dict.put(descriptionZF.name, () -> getDescription());
		dict.put(projectIdZF.name, () -> getProjectId());
		dict.put(fundingProgramZF.name, () -> getFundingProgram());
		dict.put(associatedPublicationZF.name, () -> getAssociatedPublications());
		dict.put(associatedDatasetZF.name, () -> getAssociatedDatasets());
		dict.put(referenceZF.name, () -> getReferences());
		dict.put(creatorNameZF.name, () -> getCreatorName());
		dict.put(subjectNameZF.name, () -> getSubjectName());
		dict.put(usageManualZF.name, () -> getUsageManual());
		return dict;
	}

	@SuppressWarnings("unchecked")
	@Override
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
		return dict;
	}

	@Override
	protected String getType() {
		return "http://hbz-nrw.de/regal#ResearchData";
	}

	/**
	 * The help text url must provide help texts in a certain html form. For each
	 * field a <div id="fieldName"> must be provided to contain the help text for
	 * the corresponding field.
	 * 
	 * @return return a url with help texts for the form
	 */
	public static String getHelpTextUrl() {
		String url = ConfigFactory.load().getString("zettel.researchData.helpText");
		return url;
	}

	public String validate() {
		if (containsOnlyNullValues(creator)) {
			creator = new ArrayList<>();
		}
		if (containsOnlyNullValues(contributor)) {
			contributor = new ArrayList<>();
		}
		if (containsOnlyNullValues(creatorName)) {
			creatorName = new ArrayList<>();
		}
		if (creator.isEmpty() && contributor.isEmpty() && creatorName.isEmpty()) {
			return "Bitte geben Sie einen Autor oder Beteiligten an!";
		}
		return null;
	}

	private static boolean containsOnlyNullValues(List<String> list) {
		if (list == null || list.isEmpty())
			return true;
		for (String i : list) {
			if (i != null && !i.isEmpty()) {
				return false;
			}
		}
		return true;
	}

}
