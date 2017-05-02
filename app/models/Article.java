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
import java.util.regex.Pattern;

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
		validateIdentifiers(errors);
		validateFunding(errors);
		return errors.isEmpty() ? null : errors;
	}

	private void validateStatus(List<ValidationError> errors) {
		addErrorMessage("publicationStatus",
				String.format("Bitte vergeben Sie einen %s!",
						ZettelFields.publicationStatusZF.getLabel()),
				() -> getPublicationStatus(), errors);
		// reviewStatus is optional
	}

	private void validateTitle(List<ValidationError> errors) {
		addErrorMessage("title", String.format("Bitte vergeben Sie einen %s!",
				ZettelFields.titleZF.getLabel()), () -> getTitle(), errors);
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
		if (containsNothing(getPublicationYear())) {
			setPublicationYear("");
			errors.add(new ValidationError("publicationYear",
					String.format("Bitte vergeben Sie ein %s!",
							ZettelFields.publicationYearZF.getLabel())));
		}
	}

	private void validateCollection(List<ValidationError> errors) {
		// currently no required fields
	}

	private void validateUpload(List<ValidationError> errors) {
		addErrorMessage("medium", String.format("Bitte wählen Sie ein %s aus!",
				ZettelFields.mediumZF.getLabel()), () -> getMedium(), errors);
		// yearOfCopyright and license are optional
		// TODO: embargo should be filled. If it is not, pop up a reminder.
	}

	private void validateCataloging(List<ValidationError> errors) {
		addErrorMessage("language",
				"Welche Sprache passt am ehesten zu Ihrer Eingabe?",
				() -> getLanguage(), errors);
		if (containsNothing(getProfessionalGroup())) {
			setProfessionalGroup(new ArrayList<>());
			errors.add(new ValidationError("professionalGroup",
					"Bitte orden Sie Ihre Daten einer Fachgruppe zu!"));
		}
		if (containsNothing(getDdc())) {
			setDdc(new ArrayList<>());
			errors.add(new ValidationError("ddc",
					"Bitte orden Sie Ihre Daten einem Dewey Schlagwort zu!"));
		}
		// abstract and subject tags are optional
	}

	private void validateIdentifiers(List<ValidationError> errors) {
		// if (containsNothing(getUrn())) {
		// setUrn(new ArrayList<>());
		// errors.add(new ValidationError("urn",
		// String.format("Bitte geben Sie eine %s an.",
		// ZettelFields.urnZF.getLabel())));
		// }
		// else{
		// for (String urn : getUrn()){
		// if (!URN_PATTERN.matcher(urn).matches()){
		// errors.add(new ValidationError("urn",
		// String.format("Bitte formatieren Sie die %s %s korrekt!",
		// ZettelFields.urnZF.getLabel(), urn)));
		// }
		// }
		// }
		// // TODO: DOI should be filled. If it is not, pop up a reminder.
	}

	private void validateFunding(List<ValidationError> errors) {
		// TODO: funding, projectId and fundingProgram should be filled. If they are
		// not, pop up a reminder.
	}

}