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


/**
 * @author aquast
 * 
 *
 */
public class GenericDataHelper {
	
	private static String lang = "de";
	
	
	public static LinkedHashMap<String,String> getFieldSelectValues(String fileName, String fieldPattern) {
		LinkedHashMap<String,String> genericFieldMap = new LinkedHashMap<String,String>();

		Properties fieldProp = loadPropertiesFile(new Properties(), fileName);
		
		genericFieldMap.put("ZettelModel.ZETTEL_NULL", "Bitte wählen Sie...");
		
		Enumeration<Object> fieldPropertiesEnum = fieldProp.keys();
		while(fieldPropertiesEnum.hasMoreElements()) {
			String key = (String) fieldPropertiesEnum.nextElement();
			if( key.startsWith(fieldPattern + "." + lang)) {
				genericFieldMap.put(key, fieldProp.getProperty(key));
			}
		}
		
		return genericFieldMap;
	}
	
	public static Properties loadPropertiesFile(Properties properties, String fileName) {

		properties = new Properties();
		InputStream propStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
		if(propStream !=null) {
			try{		
				properties.load(propStream);
			} catch (IOException e) {
				System.out.println(e.toString());
			}
		}
		return properties;
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
