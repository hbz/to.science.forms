package services;

import com.google.common.net.UrlEscapers;

public class MyURLEncoding {
	public static String percentEncode(String encodeMe) {
		if (encodeMe == null) {
			return "";
		}
		String encoded = UrlEscapers.urlFragmentEscaper().escape(encodeMe);
		encoded = encoded.replace("%", "%25");
		encoded = encoded.replace(" ", "%20");
		encoded = encoded.replace("!", "%21");
		encoded = encoded.replace("#", "%23");
		encoded = encoded.replace("$", "%24");
		encoded = encoded.replace("&", "%26");
		encoded = encoded.replace("'", "%27");
		encoded = encoded.replace("(", "%28");
		encoded = encoded.replace(")", "%29");
		encoded = encoded.replace("*", "%2A");
		encoded = encoded.replace("+", "%2B");
		encoded = encoded.replace(",", "%2C");
		encoded = encoded.replace("/", "");
		encoded = encoded.replace(":", "%3A");
		encoded = encoded.replace(";", "%3B");
		encoded = encoded.replace("=", "%3D");
		encoded = encoded.replace("?", "%3F");
		encoded = encoded.replace("@", "%40");
		encoded = encoded.replace("[", "%5B");
		encoded = encoded.replace("]", "%5D");
		return encoded;
	}

	public static String percentDecode(String decodeMe) {
		if (decodeMe == null) {
			return "";
		}
		String decoded = decodeMe.replace("%21", "!");
		decoded = decoded.replace("%20", " ");
		decoded = decoded.replace("%23", "#");
		decoded = decoded.replace("%24", "$");
		decoded = decoded.replace("%26", "&");
		decoded = decoded.replace("%27", "'");
		decoded = decoded.replace("%28", "(");
		decoded = decoded.replace("%29", ")");
		decoded = decoded.replace("%2A", "*");
		decoded = decoded.replace("%2B", "+");
		decoded = decoded.replace("%2C", ",");
		decoded = decoded.replace("%2F", "/");
		decoded = decoded.replace("%3A", ":");
		decoded = decoded.replace("%3B", ";");
		decoded = decoded.replace("%3D", "=");
		decoded = decoded.replace("%3F", "?");
		decoded = decoded.replace("%40", "@");
		decoded = decoded.replace("%5B", "[");
		decoded = decoded.replace("%5D", "]");
		decoded = decoded.replace("%25", "%");
		return decoded;
	}
}
