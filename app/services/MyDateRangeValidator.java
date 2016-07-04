/*
 *  Copyright 2016 hbz, Pascal Christoph
 *
 *  Licensed under the Apache License, Version 2.0 the "License";
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.validation.ConstraintValidator;

import play.libs.F.Tuple;

/**
 * @author Jan Schnasse
 */
public final class MyDateRangeValidator
		extends play.data.validation.Constraints.Validator<Object>
		implements ConstraintValidator<ValidDateRange, Object> {

	final static public String message = "error.DateValidator";

	public String process(final String value) {
		return parseDate(value);
	}

	private static String parseDate(final String value) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
			return LocalDate.parse(value, formatter).toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public boolean isValid(Object object) {

		if (object == null)
			return false;
		if ((object instanceof String)) {
			try {
				String s = parseDate(object.toString());
				return true;
			} catch (Exception e) {
				return false;
			}
		}
		if ((object instanceof List<?>)) {
			try {
				List<String> list = (List<String>) object;
				for (String s : list) {
					if (!isValid(s))
						return false;
				}
				return true;
			} catch (Exception e) {
				return false;
			}
		}
		play.Logger.error("Unexpected data format " + object);
		return false;
	}

	@Override
	public void initialize(ValidDateRange constraintAnnotation) {
	}

	@Override
	public Tuple<String, Object[]> getErrorMessageKey() {

		return null;
	}
}
