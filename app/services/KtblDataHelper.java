/**
 * 
 */
package services;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author aquast
 *
 */
public class KtblDataHelper {
	

	public LinkedHashMap<String,String> getLivestock() {
		LinkedHashMap<String,String> livestockMap = new LinkedHashMap<String,String>();
		
		Map livestock = loadPropertiesFile("ktbl.livestock.properties", new Properties());
		
		livestockMap.putAll(livestock);
		
		return livestockMap;
	}

	public static Properties loadPropertiesFile(String fName, Properties properties) {
		try (InputStream propStream = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("conf/" + fName)) {
			properties.load(propStream);
		} catch (IOException e) {
			return null;
		}
		return properties;
	}



}
