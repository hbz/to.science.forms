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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResearchData extends ZettelModel {
	/**
	 * The id under which this model is registered in the ZettelRegister
	 */
	public final static String id = "katalog:data";

	@Required(message = "Bitte vergeben Sie einen Titel!")
	private String title;
	@ValidUrl(message = "Bitte nennen Sie einen Autor oder Ersteller!")
	private List<String> creator;
	@ValidUrl(message = "Bitte nennen Sie einen Autor oder Ersteller!")
	private List<String> contributor;

	@Required(message = "Bitte geben Sie das Jahr zum Copyright an.")
	private String yearOfCopyright;

	@Required(message = "Bitte vergeben Sie eine Lizenz!")
	@ValidUrl(message = "Bitte geben	Sie einen gültigen URL an!")
	private String license;

	@Required(message = "Bitte erstellen Sie eine kurze Inhaltsangabe!")
	private String abstractText;

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

	private List<String> recordingLocation;

	private List<String> recordingCoordinates;

	private String recordingPeriod;

	private List<String> previousVersion;

	private List<String> nextVersion;

	private List<String> contributorOrder;

	private List<String> subjectOrder;

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
		dict.put(abstractTextZF.name, () -> getAbstractText());
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
		return dict;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Map<String, Consumer<Object>> getMappingForDeserialization() {
		Map<String, Consumer<Object>> dict = new LinkedHashMap<>();
		dict.put(titleZF.uri, (in) -> setTitle((String) in));
		dict.put(creatorZF.uri, (in) -> setCreator((String) in));
		dict.put(contributorZF.uri, (in) -> setContributor((String) in));
		dict.put(abstractTextZF.uri, (in) -> setAbstractText((String) in));
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
		return dict;
	}

	private void setSubjectOrder(String in) {
		if (subjectOrder == null || subjectOrder.isEmpty())
			subjectOrder = new ArrayList<>();
		subjectOrder.add(in);
	}

	private void setContributorOrder(String in) {
		if (contributorOrder == null || contributorOrder.isEmpty())
			contributorOrder = new ArrayList<>();
		contributorOrder.add(in);
	}

	private void setIsLike(String in) {
		if (isLike == null || isLike.isEmpty())
			isLike = new ArrayList<>();
		isLike.add(in);
	}

	private void setUrn(String in) {
		if (urn == null || urn.isEmpty())
			urn = new ArrayList<>();
		urn.add(in);
	}

	private void setDoi(String in) {
		if (doi == null || doi.isEmpty())
			doi = new ArrayList<>();
		doi.add(in);
	}

	private void setPreviousVersion(String in) {
		if (previousVersion == null || previousVersion.isEmpty())
			previousVersion = new ArrayList<>();
		previousVersion.add(in);
	}

	private void setNextVersion(String in) {
		if (nextVersion == null || nextVersion.isEmpty())
			nextVersion = new ArrayList<>();
		nextVersion.add(in);
	}

	private void setRecordingCoordinates(String in) {
		if (recordingCoordinates == null || recordingCoordinates.isEmpty())
			recordingCoordinates = new ArrayList<>();
		recordingCoordinates.add(in);
	}

	private void setRecordingLocation(String in) {
		if (recordingLocation == null || recordingLocation.isEmpty())
			recordingLocation = new ArrayList<>();
		recordingLocation.add(in);
	}

	private void setFunding(String in) {
		if (funding == null || funding.isEmpty())
			funding = new ArrayList<>();
		funding.add(in);
	}

	private void setDdc(String in) {
		if (ddc == null || ddc.isEmpty())
			ddc = new ArrayList<>();
		ddc.add(in);
	}

	private void setSubject(String in) {
		if (subject == null || subject.isEmpty())
			subject = new ArrayList<>();
		subject.add(in);
	}

	private void setContributor(String in) {
		if (contributor == null || contributor.isEmpty())
			contributor = new ArrayList<>();
		contributor.add(in);
	}

	private void setCreator(String in) {
		if (creator == null || creator.isEmpty())
			creator = new ArrayList<>();
		creator.add(in);
	}

	private void setSubjectOrder(List<String> in) {
		subjectOrder = in;
	}

	private void setContributorOrder(List<String> in) {
		contributorOrder = in;
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
		if (creator.isEmpty() && contributor.isEmpty()) {
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

}
