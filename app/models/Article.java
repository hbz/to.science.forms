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

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.regex.Pattern;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.typesafe.config.ConfigFactory;

import play.data.validation.ValidationError;
import services.ZettelFields;
import services.ZettelModel;

/**
 * @author Jan Schnasse
 *
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Article extends ZettelModel {

	/**
	 * The id under which this model is registered in the ZettelRegister
	 */
	public final static String id = "katalog:article";

	/**
	 * Thanks Simon G.! http://stackoverflow.com/a/5492927/4420271
	 */
	public final static Pattern URN_PATTERN = Pattern.compile(
			"^urn:[a-z0-9][a-z0-9-]{0,31}:([a-z0-9()+,\\-.:=@;$_!*']|%[0-9a-f]{2})+$",
			Pattern.CASE_INSENSITIVE);

	@Override
	protected String getType() {
		return "http://purl.org/ontology/bibo/Article";
	}

	/**
	 * The help text url must provide help texts in a certain html form. For each
	 * field a <div id="fieldName"> must be provided to contain the help text for
	 * the corresponding field.
	 * 
	 * @return return a url with help texts for the form
	 */
	public static String getHelpTextUrl() {
		String url = ConfigFactory.load().getString("zettel.article.helpText");
		return url;
	}

	@Override
	public List<ValidationError> validate() {
		validateStatus();
		validateTitle();
		validateAuthorship();
		validateResource();
		validateURLs();
		return errors.isEmpty() ? null : errors;

	}

	private void validateURLs() {
		List<String> l = getPublisherVersion();
		validate(getLabel("publisherVersion"), l);
		l = getFulltextVersion();
		validate(getLabel("fulltextVersion"), l);
		l = getAdditionalMaterial();
		validate(getLabel("additionalMaterial"), l);
		l = Arrays.asList(getLicense());
		validate(getLabel("license"), l);
	}

	private void validate(String name, List<String> l) {
		if (l == null || l.isEmpty())
			return;
		l.forEach(v -> {
			if (v != null && !v.isEmpty() && !isValid(v)) {
				errors.add(new ValidationError(name,
						"Die Eingabe \"" + v + "\" hat nicht die Form einer URL."));
			}
		});

	}

	private void validateStatus() {
		addErrorMessage(getLabel("publicationStatus"), String
				.format("Bitte vergeben Sie einen %s!", getLabel("publicationStatus")),
				() -> getPublicationStatus());
	}

	private void validateTitle() {
		addErrorMessage(getLabel("title"),
				String.format("Bitte vergeben Sie einen %s!", getLabel("title")),
				() -> getTitle());
	}

	private void validateAuthorship() {
		if (containsNothing(getCreator())) {
			setCreator(new ArrayList<>());
		}
		if (containsNothing(getContributor())) {
			setContributor(new ArrayList<>());
		}

		if (getCreator().isEmpty() && getContributor().isEmpty()) {
			errors.add(new ValidationError(getLabel("creator"),
					"Bitte geben Sie einen Autor oder Beteiligten an!"));
			errors.add(new ValidationError(getLabel("contributor"),
					"Bitte geben Sie einen Autor oder Beteiligten an!"));
		}
	}

	private void validateResource() {
		if (containsNothing(getContainedIn())) {
			setContainedIn(new ArrayList<>());
			errors.add(new ValidationError(getLabel("containedIn"),
					"Bitte geben Sie eine Quelle an."));
		}
		if (containsNothing(getPublicationYear())) {
			setPublicationYear("");
			errors.add(new ValidationError(getLabel("publicationYear"),
					String.format("Bitte vergeben Sie ein %s!",
							ZettelFields.publicationYearZF.getLabel())));
		}
	}

	void addErrorMessage(String fieldName, String message,
			Supplier<String> getValue) {
		if (getValue.get() == null || getValue.get().isEmpty()) {
			errors.add(new ValidationError(fieldName, message));
		}
	}

	public boolean isValid(String addr) {
		try {
			URL url = new URL(addr);
			play.Logger.debug("Is Valid: " + url.toExternalForm());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * private void validateCataloging() { addErrorMessage(getLabel("language"),
	 * "Welche Sprache passt am ehesten zu Ihrer Eingabe?", () -> getLanguage());
	 * if (containsNothing(getProfessionalGroup())) { setProfessionalGroup(new
	 * ArrayList<>()); errors.add(new
	 * ValidationError(getLabel("professionalGroup"),
	 * "Bitte orden Sie Ihre Daten einer Fachgruppe zu!")); } if
	 * (containsNothing(getDdc())) { setDdc(new ArrayList<>()); errors.add(new
	 * ValidationError(getLabel("ddc"),
	 * "Bitte orden Sie Ihre Daten einem Dewey Schlagwort zu!")); } }
	 */
}