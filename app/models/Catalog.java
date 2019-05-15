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

import play.data.validation.ValidationError;

/**
 * @author Jan Schnasse
 *
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Catalog extends ZettelModel {

	/**
	 * The id under which this model is registered in the ZettelRegister
	 */
	public final static String id = "katalog:catalog";

	public String getType() {
		return "Catalog";
	}

	@Override
	public List<ValidationError> validate() {
		List<ValidationError> errors = new ArrayList<>();
		validateURLs(errors);
		return errors.isEmpty() ? null : errors;
	}

	private void validateURLs(List<ValidationError> errors) {
		validateUrl("parallelEdition", Arrays.asList(getParallelEdition()), errors);

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

}