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
import scala.xml.PrettyPrinter.Item;
import services.URLUtil;

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
		// Publikationstyp
		mandatoryField(getLabel("rdftype"), getRdftype(), errors);
		// Publikationsstatus
		mandatoryField(getLabel("publicationStatus"), getPublicationStatus(),
				errors);
		// Titel
		mandatoryField(getLabel("title"), getTitle(), errors);
		// Autoren
		validateAuthorship(errors);
		// Erschienen in
		mandatoryField(getLabel("containedIn"), getContainedIn(), errors);
		// Online veröffentlicht
		mandatoryField(getLabel("publicationYear"), getPublicationYear(), errors);
		// Sprache der Publikation
		mandatoryField(getLabel("language"), getLanguage(), errors);
		// Fächerklassifikation
		mandatoryField(getLabel("ddc"), getDdc(), errors);
	}

	private void validateURLs(List<ValidationError> errors) {
		validateUrl(getLabel("license"), Arrays.asList(getLicense()), errors);
		validateUrl(getLabel("creator"), getCreator(), errors);
		validateUrl(getLabel("contributor"), getContributor(), errors);
		validateUrl(getLabel("editor"), getEditor(), errors);
		validateUrl(getLabel("Other"), getOther(), errors);
		validateUrl(getLabel("containedIn"), getContainedIn(), errors);
		validateUrl(getLabel("institution"), getInstitution(), errors);
		validateUrl(getLabel("collectionOne"), getCollectionOne(), errors);
		validateUrl(getLabel("collectionTwo"), getCollectionTwo(), errors);
		validateUrl(getLabel("ddc"), getDdc(), errors);
		validateUrl(getLabel("publisherVersion"), getPublisherVersion(), errors);
		validateUrl(getLabel("fulltextVersion"), getFulltextVersion(), errors);
		validateUrl(getLabel("additionalMaterial"), getAdditionalMaterial(),
				errors);
		validateUrl(getLabel("internalReference"), getInternalReference(), errors);
		validateUrl(getLabel("fundingId"), getFundingId(), errors);
	}

	private void validateUrl(String fieldLabel, List<String> fieldContent,
			List<ValidationError> errors) {
		play.Logger.debug("Validiere " + fieldLabel);
		if (fieldContent == null || fieldContent.isEmpty())
			return;
		fieldContent.forEach(v -> {
			if (v != null && !v.isEmpty() && !isValidUrl(v)) {
				errors.add(new ValidationError(fieldLabel,
						String.format("Bitte verknüpfen Sie Ihre Eingabe. Die Eingabe \""
								+ v + "\" hat nicht die Form einer URL.", fieldLabel)));
			}
		});

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
			errors.add(new ValidationError(getLabel("creator"),
					"Bitte machen sie in einem der folgenden Felder mindestens eine Angabe: \"Autor/in\", \"Mitwirkende/r\"!"));
			errors.add(new ValidationError(getLabel("contributor"),
					"Bitte machen sie in einem der folgenden Felder mindestens eine Angabe: \"Autor/in\", \"Mitwirkende/r\"!"));
		}
	}

	@SuppressWarnings("static-method")
	private void mandatoryField(String fieldLabel, List<String> fieldContent,
			List<ValidationError> errors) {

		if (containsNothing(fieldContent)) {
			errors.add(new ValidationError(fieldLabel,
					String.format(
							"Es ist mindestens ein Eintrag im Feld \"%s\" erforderlich!",
							fieldLabel)));
		}
	}

	@SuppressWarnings("static-method")
	private void mandatoryField(String fieldLabel, String fieldContent,
			List<ValidationError> errors) {

		if (fieldContent == null || fieldContent.isEmpty()) {
			errors.add(new ValidationError(fieldLabel,
					String.format("Bitte füllen Sie das Feld \"%s\" aus!", fieldLabel)));
		}
	}

}