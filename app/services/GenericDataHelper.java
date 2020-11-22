/**
 * 
 */
package services;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

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
		
		LinkedHashMap<String,String> fieldMap = new LinkedHashMap<String,String>();
		fieldMap.putAll(PropertiesFileUtil.loadPropertiesFileSequence(genericFieldMap, fileName));
		
		Set<String> fieldSet = fieldMap.keySet();
		Iterator<String> fit = fieldSet.iterator();
		while(fit.hasNext()) {
			String key = fit.next();
			if( key.startsWith(fieldPattern + ".") && key.endsWith("@" + lang)) {
				genericFieldMap.put(key, fieldMap.get(key));
			}
		}
		
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
