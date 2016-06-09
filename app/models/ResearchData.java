package models;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import play.data.validation.Constraints.Required;

public class ResearchData {
	@Required
	private String title;
	@Required
	private List<String> author;
	@Required
	private String yearOfCopyright;
	@Required
	private String license;
	@Required
	private List<String> doi;
	@Required
	private String abstractText;
	@Required
	private String professionalGroup;
	@Required
	private String embargo;
	@Required
	List<String> ddc;
	@Required
	String language;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<String> getAuthor() {
		return author;
	}

	public void setAuthor(List<String> author) {
		this.author = author;
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

	public String toString() {
		try {
			return new ObjectMapper().writeValueAsString(this);
		} catch (Exception e) {
			return "To String failed " + e.getMessage();
		}
	}
}
