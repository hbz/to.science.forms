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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.typesafe.config.ConfigFactory;

import play.data.validation.ValidationError;
import services.URLUtil;
import services.ZettelHelper;

/**
 * @author Jan Schnasse
 *
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ResearchData extends ZettelModel {
	/**
	 * The id under which this model is registered in the ZettelRegister
	 */
	public final static String id = "katalog:researchData";

	public List<String> getType() {
		return Arrays.asList("http://hbz-nrw.de/regal#ResearchData");
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

	@Override
	public List<ValidationError> validate() {
		List<ValidationError> errors = new ArrayList<>();
		urlEncodeLinkFields();
		validateMandatoryFields(errors);
		validateURLs(errors);
		return errors.isEmpty() ? null : errors;
	}

	private void urlEncodeLinkFields() {
		setPublisherVersion(urlEncode(getPublisherVersion()));
		setFulltextVersion(urlEncode(getFulltextVersion()));
		setAdditionalMaterial(urlEncode(getAdditionalMaterial()));
		setInternalReference(urlEncode(getInternalReference()));
	}

	private static List<String> urlEncode(List<String> urls) {
		List<String> encodedUrls = new ArrayList<>();
		urls.forEach(url -> {
			try {
				encodedUrls.add(URLUtil.saveEncode(url));
			} catch (Exception e) {

			}
		});
		return encodedUrls;
	}

	private void validateMandatoryFields(List<ValidationError> errors) {
		// Titel
		mandatoryField("title", getTitle(), errors);
		// Autoren
		validateAuthorship(errors);
		// Sprache der Publikation
		mandatoryField("language", getLanguage(), errors);
		// Fächerklassifikation
		mandatoryField("ddc", getDdc(), errors);
	}

	private void validateURLs(List<ValidationError> errors) {
		validateUrl("license", Arrays.asList(getLicense()), errors);
		validateUrl("creator", getCreator(), errors);
		validateUrl("contributor", getContributor(), errors);
		validateUrl("other", getOther(), errors);
		validateUrl("ddc", getDdc(), errors);
		validateUrl("reference", getReference(), errors);
		validateUrl("associatedPublication", getAssociatedPublication(), errors);
		validateUrl("associatedDataset", getAssociatedDataset(), errors);
		validateUrl("nextVersion", Arrays.asList(getNextVersion()), errors);
		validateUrl("previousVersion", Arrays.asList(getPreviousVersion()), errors);
		validateUrl("urn", getUrn(), errors);
		validateUrl("isLike", getIsLike(), errors);
	}

	private void validateUrl(String fieldName, List<String> fieldContent,
			List<ValidationError> errors) {
		play.Logger.debug("Validiere " + fieldName);
		if (fieldContent == null || fieldContent.isEmpty())
			return;
		for (int i = 0; i < fieldContent.size(); i++) {
			String v = fieldContent.get(i);
			if (v != null && !v.isEmpty() && !isValidUrl(v)) {
				errors.add(new ValidationError(fieldName + "[" + i + "]",
						String.format("Bitte verknüpfen Sie Ihre Eingabe. Die Eingabe \""
								+ v + "\" hat nicht die Form einer URL.", fieldName)));
				errors.add(new ValidationError(fieldName,
						String.format("Bitte verknüpfen Sie Ihre Eingabe. Die Eingabe \""
								+ v + "\" hat nicht die Form einer URL.", fieldName)));
			}
		}

	}

	@SuppressWarnings({ "javadoc", "unused" })
	public boolean isValidUrl(String addr) {
		try {
			if (ZettelModel.ZETTEL_NULL.equals(addr)) {
				// this is a valid value and will be handled properly
				return true;
			}
			new URL(addr);// throws Exception? return false
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private void validateAuthorship(List<ValidationError> errors) {
		if (containsNothing(getCreator())) {
			setCreator(new ArrayList<>());
		}
		if (containsNothing(getContributor())) {
			setContributor(new ArrayList<>());
		}

		if (getCreator().isEmpty() && getContributor().isEmpty()) {
			errors.add(new ValidationError("creator",
					"Bitte machen sie in einem der folgenden Felder mindestens eine Angabe: \"Autor/in\", \"Mitwirkende/r\"!"));
			errors.add(new ValidationError("creator[0]",
					"Bitte machen sie in einem der folgenden Felder mindestens eine Angabe: \"Autor/in\", \"Mitwirkende/r\"!"));
		}
	}

	@SuppressWarnings("static-method")
	private void mandatoryField(String fieldName, List<String> fieldContent,
			List<ValidationError> errors) {
		// containsNothing will also check for pseudo Null values likel ZETTEL_NULL
		if (containsNothing(fieldContent)) {
			errors.add(new ValidationError(fieldName,
					String.format(
							"Es ist mindestens ein Eintrag im Feld \"%s\" erforderlich!",
							ZettelHelper.etikett.getLabel(fieldName))));
			errors.add(new ValidationError(fieldName + "[0]",
					String.format(
							"Es ist mindestens ein Eintrag im Feld \"%s\" erforderlich!",
							ZettelHelper.etikett.getLabel(fieldName))));
		}
	}

	@SuppressWarnings("static-method")
	private void mandatoryField(String fieldName, String fieldContent,
			List<ValidationError> errors) {
		// containsNothing will also check for pseudo Null values likel ZETTEL_NULL
		if (containsNothing(fieldContent)) {
			errors.add(new ValidationError(fieldName,
					String.format("Bitte füllen Sie das Feld \"%s\" aus!",
							ZettelHelper.etikett.getLabel(fieldName))));

		}
	}
}
