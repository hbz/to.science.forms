/**
 * 
 */
package services;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import zetteltest.PropertiesFileUtil;


/**
 * @author aquast
 * 
 *
 */
public class GenericDataHelper {
	
	private static String lang = "de";
	
	public static LinkedHashMap<String,String> getFieldSelectValues(String fileName, String fieldPattern) {
		LinkedHashMap<String,String> genericFieldMap = new LinkedHashMap<String,String>();

		genericFieldMap.put("ZettelModel.ZETTEL_NULL", "Bitte wählen Sie...");
		genericFieldMap.putAll(PropertiesFileUtil.loadPropertiesFileSequence(genericFieldMap, fileName));
		
		return genericFieldMap;
	}

	/**
	 * @return the lang
	 */
	public static String getLang() {
		return lang;
	}



	/**
	 * @param lang the lang to set
	 */
	public static void setLang(String lang) {
		GenericDataHelper.lang = lang;
	}


}
