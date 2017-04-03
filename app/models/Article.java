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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.typesafe.config.ConfigFactory;
import play.data.validation.ValidationError;
import services.ZettelFields;
import services.ZettelModel;

import java.util.ArrayList;
import java.util.List;

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

	@Override
	protected String getType() {
		return "http://hbz-nrw.de/regal#Article";
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
		List<ValidationError> errors = new ArrayList<>();
		validateStatus(errors);
		validateTitle(errors);
		validateAuthorship(errors);
		validateResource(errors);
		validateCollection(errors);
		validateUpload(errors);
		validateCataloging(errors);
		validateSimpleFields(errors);
		validateListFields(errors);
		return errors.isEmpty() ? null : errors;
	}

	private void validateStatus(List<ValidationError> errors) {
		addErrorMessage("publicationStatus",
				String.format("Bitte vergeben Sie einen %s!", ZettelFields.publicationStatusZF.getLabel()),
				() -> getPublicationStatus(), errors);
		// reviewStatus is optional
	}

	private void validateTitle(List<ValidationError> errors) {
		addErrorMessage("title",
				String.format("Bitte vergeben Sie einen %s!", ZettelFields.titleZF.getLabel()),
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
		if (containsNothing(getCreatorName())) {
			setCreatorName(new ArrayList<>());
		}
		if (containsNothing(getContributorName())) {
			setContributorName(new ArrayList<>());
		}
		if (getCreator().isEmpty() && getContributor().isEmpty()
				&& getCreatorName().isEmpty() && getContributorName().isEmpty()) {
			errors.add(new ValidationError("creator",
					"Bitte geben Sie einen Autor oder Beteiligten an!"));
			errors.add(new ValidationError("contributor",
					"Bitte geben Sie einen Autor oder Beteiligten an!"));
			errors.add(new ValidationError("creatorName",
					"Bitte geben Sie einen Autor oder Beteiligten an!"));
			errors.add(new ValidationError("contributorName",
					"Bitte geben Sie einen Autor oder Beteiligten an!"));
		}
		// editor and redaktor are optional
	}

	private void validateResource(List<ValidationError> errors) {
		if (containsNothing(getContainedIn())) {
			setContainedIn(new ArrayList<>());
			errors.add(new ValidationError("containedIn",
					"Bitte geben Sie eine Quelle an."));
		}
		addErrorMessage("publicationYear",
				String.format("Bitte vergeben Sie einen %s!", ZettelFields.publicationYearZF.getLabel()),
				() -> getPublicationYear(), errors);
		// issue, articleNumber, pages and issn are optional
	}

	private void validateCollection(List<ValidationError> errors) {
		// currently no required fields
	}

	private void validateUpload(List<ValidationError> errors) {
		addErrorMessage("medium",
				String.format("Bitte wählen Sie ein %s aus!", ZettelFields.mediumZF.getLabel()),
				() -> getMedium(), errors);
		// yearOfCopyright is optional
		addErrorMessage("license",
				String.format("Bitte wählen Sie eine %s aus!", ZettelFields.mediumZF.getLabel()),
				() -> getLicense(), errors);
		// TODO: embargo should be filled. If it is not, pop up a reminder.
	}

	private void validateCataloging(List<ValidationError> errors) {
		addErrorMessage("language",
				"Welche Sprache passt am ehesten zu Ihrer Eingabe?",
				() -> getLanguage(), errors);
		addErrorMessage("professionalGroup",
				"Bitte orden Sie Ihre Daten einer Fachgruppe zu!",
				() -> getProfessionalGroup(), errors);
		if (containsNothing(getDdc())) {
			setDdc(new ArrayList<>());
			errors.add(new ValidationError("ddc",
					"Bitte orden Sie Ihre Daten einem Dewey Schlagwort zu!"));
		}
		// abstract and subject tags are optional
	}

}