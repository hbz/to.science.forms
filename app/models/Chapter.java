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
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.typesafe.config.ConfigFactory;

import play.data.validation.ValidationError;

import services.ZettelModel;

/**
 * @author Jan Schnasse
 *
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Chapter extends ZettelModel {

	/**
	 * The id under which this model is registered in the ZettelRegister
	 */
	public final static String id = "katalog:chapter";

	@Override
	protected String getType() {
		return "http://hbz-nrw.de/regal#Chapter";
	}

	/**
	 * The help text url must provide help texts in a certain html form. For each
	 * field a <div id="fieldName"> must be provided to contain the help text for
	 * the corresponding field.
	 * 
	 * @return return a url with help texts for the form
	 */
	public static String getHelpTextUrl() {
		String url = ConfigFactory.load().getString("zettel.chapter.helpText");
		return url;
	}

	@Override
	public List<ValidationError> validate() {
		List<ValidationError> errors = new ArrayList<>();
		validateAuthorship(errors);
		validateSimpleFields(errors);
		validateListFields(errors);
		return errors.isEmpty() ? null : errors;
	}

	private void validateListFields(List<ValidationError> errors) {
		if (containsNothing(getDdc())) {
			setDdc(new ArrayList<String>());
		}
		if (getDdc().isEmpty()) {
			errors.add(new ValidationError("ddc",
					"Bitte orden Sie Ihre Daten einem Dewey Schlagwort zu!"));
		}
		if (containsNothing(getProfessionalGroup())) {
			setProfessionalGroup(new ArrayList<>());
			errors.add(new ValidationError("professionalGroup",
					"Bitte orden Sie Ihre Daten einer Fachgruppe zu!"));
		}
	}

	private void validateSimpleFields(List<ValidationError> errors) {
		addErrorMessage("title", "Bitte vergeben Sie einen Titel!",
				() -> getTitle(), errors);
		addErrorMessage("license", "Bitte vergeben Sie eine Lizenz!",
				() -> getLicense(), errors);
		addErrorMessage("copyright", "Bitte geben Sie das Jahr zum Copyright an.",
				() -> getYearOfCopyright(), errors);
		addErrorMessage("language",
				"Welche Sprache passt am ehesten zu Ihrer Eingabe?",
				() -> getLanguage(), errors);
		addErrorMessage("medium", "Bitte ordnen Sie ihre Eingabe einem Medium zu!",
				() -> getMedium(), errors);
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
}