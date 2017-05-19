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

import java.util.List;

import javax.validation.ConstraintValidator;

import org.apache.commons.validator.routines.UrlValidator;

import play.libs.F.Tuple;

/**
 * Checks if a string is a valid absolute URL. If it is not, try to repair the
 * URL. Returns an empty string if it can't be repaired.
 * 
 * @author Pascal Christoph (dr0i)
 * @author Jan Schnasse
 */
public final class MyUrlValidator
		extends play.data.validation.Constraints.Validator<Object>
		implements ConstraintValidator<ValidUrl, Object> {

	/**
	 * Is used as a default message when communicating with the calling instance
	 */
	final static public String message = "error.UrlValidator";

	static UrlValidator urlValidator =
			new UrlValidator(UrlValidator.ALLOW_2_SLASHES);

	/**
	 * @param value a url string
	 * @return a normalized url string
	 */
	public static String process(final String value) {
		return sanitizeUrl(value);
	}

	private static String sanitizeUrl(final String value) {
		String url = value.trim();
		// unwise characters (rfc2396) :
		url = url.replace("\\", "%5C").replace("|", "%7C");
		if (url.matches(".*#.*#.*")) {// allow only one fragment
			url = url.substring(0, (url.indexOf("#", url.indexOf("#") + 1)));
		}
		url = url.replace(" ", "%20");// space in URI
		if (!urlValidator.isValid(url)) {
			if (!urlValidator.isValid(url)) {
				for (String urlSplitter : url.split("%20")) {
					if (urlValidator.isValid(urlSplitter))
						return urlSplitter;
				}
				if (!urlValidator.isValid(url)) {
					if (url.matches(".*:/[^/].*")) // only one slash following the scheme
						url = url.replace(":/", "://");
					// assuming scheme is missing
					if (!urlValidator.isValid(url)) {
						url = "http://" + url;
						if (!urlValidator.isValid(url)) {
							throw new RuntimeException(
									"No absolute URI could be generated from '" + value + "'");
						}
					}
				}
			}
		}
		return url;
	}

	@Override
	public boolean isValid(Object object) {
		play.Logger.debug("Validate " + object + "");

		if (object == null)
			return true;
		if ((object instanceof String)) {
			try {
				@SuppressWarnings("unused")
				String s = sanitizeUrl(object.toString());
				return true;
			} catch (Exception e) {
				return false;
			}
		}
		if ((object instanceof List<?>)) {
			try {
				@SuppressWarnings("unchecked")
				List<String> list = (List<String>) object;
				if (list.isEmpty())
					return true;
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
	public void initialize(ValidUrl constraintAnnotation) {
		// No need to implement
	}

	@Override
	public Tuple<String, Object[]> getErrorMessageKey() {
		return null;
	}
}
