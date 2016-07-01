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
import play.data.validation.Constraints.Required;
import static services.ZettelFields.*;
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

	@Required(message = "Please provide a title")
	private String title;
	private List<String> creator;
	private List<String> contributor;
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
	private List<String> funding;
	private List<String> recordingLocation;
	private List<String> recordingPeriod;
	private List<String> previousVersion;
	private List<String> nextVersion;

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

	public List<String> getRecordingPeriod() {
		return recordingPeriod;
	}

	public void setRecordingPeriod(List<String> recordingPeriod) {
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
		dict.put(nextVersionZF.name, () -> getNextVersion());
		dict.put(previousVersionZF.name, () -> getPreviousVersion());
		return dict;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Map<String, Consumer<Object>> getMappingForDeserialization() {
		Map<String, Consumer<Object>> dict = new LinkedHashMap<>();
		dict.put(titleZF.uri, (in) -> setTitle((String) in));
		dict.put(creatorZF.uri, (in) -> setCreator((List<String>) in));
		dict.put(contributorZF.uri, (in) -> setContributor((List<String>) in));
		dict.put(abstractTextZF.uri, (in) -> setAbstractText((String) in));
		dict.put(dataOriginZF.uri, (in) -> setDataOrigin((String) in));
		dict.put(embargoZF.uri, (in) -> setEmbargo((String) in));
		dict.put(languageZF.uri, (in) -> setLanguage((String) in));
		dict.put(licenseZF.uri, (in) -> setLicense((String) in));
		dict.put(mediumZF.uri, (in) -> setMedium((String) in));
		dict.put(professionalGroupZF.uri,
				(in) -> setProfessionalGroup((String) in));
		dict.put(subjectZF.uri, (in) -> setSubject((List<String>) in));
		dict.put(yearOfCopyrightZF.uri, (in) -> setYearOfCopyright((String) in));
		dict.put(ddcZF.uri, (in) -> setDdc((List<String>) in));
		dict.put(fundingZF.uri, (in) -> setFunding((List<String>) in));
		dict.put(recordingPeriodZF.uri,
				(in) -> setRecordingPeriod((List<String>) in));
		dict.put(recordingLocationZF.uri,
				(in) -> setRecordingLocation((List<String>) in));
		dict.put(nextVersionZF.uri, (in) -> setNextVersion((List<String>) in));
		dict.put(previousVersionZF.uri,
				(in) -> setPreviousVersion((List<String>) in));
		return dict;
	}

	@Override
	protected String getType() {
		return "http://hbz-nrw.de/regal#ResearchData";
	}
}
