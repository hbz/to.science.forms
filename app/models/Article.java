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
import java.util.regex.Pattern;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.typesafe.config.ConfigFactory;

import play.data.validation.ValidationError;
import services.ZettelFields;
import services.ZettelHelper;

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
		List<ValidationError> errors = new ArrayList<>();
		validateStatus(errors);
		validateTitle(errors);
		validateAuthorship(errors);
		validateResource(errors);
		// validateCollection(errors);
		// validateUpload(errors);
		validateCataloging(errors);
		// validateIdentifiers(errors);
		// validateFunding(errors);
		validateURLs(errors);
		return errors.isEmpty() ? null : errors;
	}

	private void validateURLs(List<ValidationError> errors) {
		List<String> l = getPublisherVersion();
		validate(getLabel("publisherVersion"), l, errors);
		l = getFulltextVersion();
		validate(getLabel("fulltextVersion"), l, errors);
		l = getAdditionalMaterial();
		validate(getLabel("additionalMaterial"), l, errors);
		l = getAdditionalMaterial();
		validate(getLabel("internalReference"), l, errors);
		l = Arrays.asList(getLicense());
		validate(getLabel("license"), l, errors);
	}

	private void validate(String name, List<String> l,
			List<ValidationError> errors) {
		if (l == null || l.isEmpty())
			return;
		l.forEach(v -> {
			if (v != null && !v.isEmpty() && !isValid(v)) {
				errors.add(new ValidationError(name,
						"Die Eingabe \"" + v + "\" hat nicht die Form einer URL."));
			}
		});

	}

	private void validateStatus(List<ValidationError> errors) {
		addErrorMessage(getLabel("publicationStatus"),
				String.format("Bitte vergeben Sie einen %s!",
						getLabel("publicationStatus")),
				() -> getPublicationStatus(), errors);
		// reviewStatus is optional
	}

	private void validateTitle(List<ValidationError> errors) {
		addErrorMessage(getLabel("title"),
				String.format("Bitte vergeben Sie einen %s!", getLabel("title")),
				() -> getTitle(), errors);
		// alternativeTitle is optional
	}

	private void validateAuthorship(List<ValidationError> errors) {
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
		// editor and redaktor are optional
	}

	private void validateResource(List<ValidationError> errors) {
		if (containsNothing(getContainedIn())) {
			setContainedIn(new ArrayList<>());
			errors.add(new ValidationError(getLabel("containedIn"),
					"Bitte geben Sie eine Quelle an."));
		}
		if (containsNothing(getPublicationYear())) {
			setPublicationYear("");
			errors.add(new ValidationError("publicationYear",
					String.format("Bitte vergeben Sie ein %s!",
							ZettelFields.publicationYearZF.getLabel())));
		}
	}

	// private void validateCollection(List<ValidationError> errors) {
	// // currently no required fields
	// }

	// private void validateUpload(List<ValidationError> errors) {
	// // addErrorMessage("medium", String.format("Bitte wählen Sie ein %s aus!",
	// // ZettelFields.mediumZF.getLabel()), () -> getMedium(), errors);
	// // yearOfCopyright and license are optional
	// // TODO: embargo should be filled. If it is not, pop up a reminder.
	// }

	private void validateCataloging(List<ValidationError> errors) {
		addErrorMessage(getLabel("language"),
				"Welche Sprache passt am ehesten zu Ihrer Eingabe?",
				() -> getLanguage(), errors);
		if (containsNothing(getDdc())) {
			setDdc(new ArrayList<>());
			errors.add(new ValidationError(getLabel("ddc"),
					"Bitte orden Sie Ihre Daten einem Dewey Schlagwort zu!"));
		}
		// abstract and subject tags are optional
	}

	// private void validateIdentifiers(List<ValidationError> errors) {
	// // if (containsNothing(getUrn())) {
	// // setUrn(new ArrayList<>());
	// // errors.add(new ValidationError("urn",
	// // String.format("Bitte geben Sie eine %s an.",
	// // ZettelFields.urnZF.getLabel())));
	// // }
	// // else{
	// // for (String urn : getUrn()){
	// // if (!URN_PATTERN.matcher(urn).matches()){
	// // errors.add(new ValidationError("urn",
	// // String.format("Bitte formatieren Sie die %s %s korrekt!",
	// // ZettelFields.urnZF.getLabel(), urn)));
	// // }
	// // }
	// // }
	// // // TODO: DOI should be filled. If it is not, pop up a reminder.
	// }

	// private void validateFunding(List<ValidationError> errors) {
	// // TODO: funding, projectId and fundingProgram should be filled. If they
	// are
	// // not, pop up a reminder.
	// }

	public boolean isValid(String addr) {
		try {
			URL url = new URL(addr);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}