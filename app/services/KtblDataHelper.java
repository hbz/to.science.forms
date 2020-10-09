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

import models.ZettelModel;

/**
 * @author aquast
 *
 */
public class KtblDataHelper {
	

	public static LinkedHashMap<String,String> getLivestockType() {
		LinkedHashMap<String,String> livestock = new LinkedHashMap<String,String>();
		
		Properties livestockProp = loadPropertiesFile("ktbl.livestock.properties", new Properties());
		
		livestock.put(ZettelModel.ZETTEL_NULL, "Bitte wählen Sie...");
		
		Enumeration<Object> livestockEnum = livestockProp.keys();
		while(livestockEnum.hasMoreElements()) {
			String key = (String) livestockEnum.nextElement();
			livestock.put(key, livestockProp.getProperty(key));
		}
		
		return livestock;
	}

	public static Properties loadPropertiesFile(String fName, Properties properties) {

		properties = new Properties();
		InputStream propStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fName);
		if(propStream !=null) {
			try{		
				properties.load(propStream);
			} catch (IOException e) {
				System.out.println(e.toString());
			}
		}
		return properties;
	}



}
