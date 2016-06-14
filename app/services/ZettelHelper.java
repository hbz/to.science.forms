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
package services;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.typesafe.config.ConfigFactory;

import play.data.Form;
import play.libs.ws.WSAuthScheme;
import play.libs.ws.WSClient;
import play.libs.ws.WSResponse;

/**
 * @author Jan Schnasse
 *
 */
public class ZettelHelper {
	@Inject
	static WSClient ws;

	/**
	 * The function calls a deployment of https://github.com/hbz/etikett to
	 * provide a label for a given uri. If no deployment is available the plain
	 * uri is returned.
	 * 
	 * @param uri a uri somewhere in the net
	 * @return a label for the uri
	 */
	public static String getLinkedDataLabel(String uri) {
		try {
			if (uri == null || uri.isEmpty())
				return uri;
			String etikettUrl = ConfigFactory.load().getString("etikettService");
			String etikettUser = ConfigFactory.load().getString("etikettUser");
			String etikettPwd = ConfigFactory.load().getString("etikettPwd");
			play.Logger.debug(etikettUrl + "?url=" + uri + "&column=label");
			WSResponse response =
					play.libs.ws.WS.url(etikettUrl + "?url=" + uri + "&column=label")
							.setAuth(etikettUser, etikettPwd, WSAuthScheme.BASIC)
							.setFollowRedirects(true).get().toCompletableFuture().get();
			try (InputStream input = response.getBodyAsStream()) {
				String content =
						CharStreams.toString(new InputStreamReader(input, Charsets.UTF_8));
				play.Logger.debug(content);
				return content;
			}
		} catch (Exception e) {
			return uri;
		}
	}

	/**
	 * To post a dynamic number of repeated values a simple convention is used.
	 * <br/>
	 * Example: <br/>
	 * Name of the list: author <br/>
	 * Name of the first entry: author[0]<br/>
	 * Name of the next entriy: author[1] <br/>
	 * .....<br/ This method gives all names that exists in the current form for a
	 * given field with repeated values.
	 * 
	 * @param form a form to drive a html formular
	 * @param fieldName the fieldname that is used to post data
	 * @return a list of valid fieldnames for post data.
	 */
	public static List<String> getFieldWithIndex(Form<?> form, String fieldName) {
		List<String> result = new ArrayList<>();
		Map<String, String> formData = form.data();
		String id = formData.get(fieldName);
		if (id != null) {
			result.add(fieldName);
		} else {
			for (int i = 0; i < Integer.MAX_VALUE; i++) {
				id = formData.get(fieldName + "[" + i + "]");
				if (id == null)
					break;
				result.add(fieldName + "[" + i + "]");
			}
		}
		if (result.isEmpty()) {
			result.add(fieldName);
		}
		return result;
	}
}
