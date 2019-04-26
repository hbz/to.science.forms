/*******************************************************************************
 * Copyright 2018 Jan Schnasse
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/

package services;

import java.net.IDN;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import com.google.common.base.CharMatcher;

/**
 * 
 * @author Jan Schnasse
 *
 */
public class URLUtil {
	/*
	 * This method will only encode an URL if it is not encoded already. It will
	 * also replace '+'-encoded spaces with percent encoding.
	 */
	public static String saveEncode(String url) {
		try {
			String passedUrl = url.replaceAll("\\+", "%20");
			if (!isAlreadyEncoded(passedUrl)) {
				return encode(passedUrl);
			}
			if(!CharMatcher.ASCII.matchesAllOf(passedUrl)) {
				return encode(passedUrl);
			}
			if(passedUrl.endsWith(":/")) {
				return encode(passedUrl);
			}
			if(Character.isUpperCase(passedUrl.charAt(0))) {
				return encode(passedUrl);
			}
			return passedUrl;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static boolean isAlreadyEncoded(String passedUrl) {
		boolean isEncoded = true;
		if (passedUrl.matches(".*[\\ \"\\<\\>\\{\\}|\\\\^~\\[\\]].*")) {
			isEncoded = false;
		}
		return isEncoded;
	}

	public static String encode(String url) {
		try {
			URL u = new URL(url);
			URI uri = new URI(u.getProtocol(), u.getUserInfo(), IDN.toASCII(u.getHost()), u.getPort(), u.getPath(),
							u.getQuery(), u.getRef());
			String correctEncodedURL = uri.toASCIIString();
			return correctEncodedURL;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String decode(String url) {
		try {
			URL u = new URL(url);
			String protocol = u.getProtocol();
			String userInfo = u.getUserInfo();
			String host = u.getHost() != null ? IDN.toUnicode(u.getHost()) : null;
			int port = u.getPort();
			String path = u.getPath() != null ? URLDecoder.decode(u.getPath(), StandardCharsets.UTF_8.name()) : null;
			String ref = u.getRef();
			String query = u.getQuery() != null ? URLDecoder.decode(u.getQuery(), StandardCharsets.UTF_8.name()) : null;

			protocol = protocol != null ? protocol + "://" : "";
			userInfo = userInfo != null ? userInfo : "";
			host = host != null ? host : "";
			String portStr = port != -1 ? ":" + port : "";
			path = path != null ? path : "";
			query = query != null ? "?" + query : "";
			ref = ref != null ? "#" + ref : "";

			return String.format("%s%s%s%s%s%s%s", protocol, userInfo, host, portStr, path, ref, query);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private static int decode(char c) {
		if ((c >= '0') && (c <= '9'))
			return c - '0';
		if ((c >= 'a') && (c <= 'f'))
			return c - 'a' + 10;
		if ((c >= 'A') && (c <= 'F'))
			return c - 'A' + 10;
		assert false;
		return -1;
	}
}
