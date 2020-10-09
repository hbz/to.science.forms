/**
 * 
 */
package services;

import java.io.IOException;
import java.io.InputStream;
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
		
		Map livestockProp = loadPropertiesFile("ktbl.livestock.properties", new Properties());
		
			livestock.put(ZettelModel.ZETTEL_NULL, "Bitte wählen Sie...");
			livestock.put("info.ktbl.livestock.cattle.de", "Rind");
			livestock.put("info.ktbl.livestock.pork.de", "Schwein");
			livestock.put("info.ktbl.livestock.chicken.de", "Huhn");
			livestock.put("info.ktbl.livestock.turkey.de", "Pute");
			livestock.put("info.ktbl.livestock.duck.de", "Ente");
		
		
		
		return livestock;
	}

	public static Properties loadPropertiesFile(String fName, Properties properties) {
		try (InputStream propStream = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("conf/" + fName)) {
			properties.load(propStream);
		} catch (IOException e) {
			System.out.println(e.getStackTrace());
			return null;
		}
		return properties;
	}



}
