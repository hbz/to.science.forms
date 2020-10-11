/**
 * 
 */
package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Properties;

/**
 * @author aquast
 *
 */
public class PropertiesFileUtil {
	
	
	/**
	 * Loads key-values into Properties instance from a file identified by fileName. Returns empty Properties 
	 * instance if file is not accessible.
	 * Be aware that sequence of key / values is not predictable since method uses Properties.load()-method.   
	 * @param properties
	 * @param fileName
	 * @return
	 */
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
	 * Loads key-values into LinkedHashMap instance from a file identified by fileName. File is thought to use the Properties file format with = as separator.
	 * Returns empty LinkedHashMap instance if file is not accessible.
	 * Use this method if the sequence of key-values should be predictable since method uses a readline-implementation to load key-values.   
	 * @param lhMap
	 * @param fileName
	 * @return
	 */
	public static LinkedHashMap<String, String> loadPropertiesFileSequence(LinkedHashMap<String, String> lhMap, String fileName) {

		
		lhMap = new LinkedHashMap<String, String>();
		InputStream propStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
		
		if(propStream !=null) {
			try{
				InputStreamReader propStreamReader = new InputStreamReader(propStream, StandardCharsets.UTF_8);
				BufferedReader bR = new BufferedReader(propStreamReader);
				String line = null;
				while((line = bR.readLine()) != null) {
					if(! line.startsWith("#") && line.contains("=")) {
						String[] splitLine = line.split("=");
						lhMap.put(splitLine[0], splitLine[1]);
					}
				}
				
			} catch (IOException e) {
				System.out.println(e.toString());
			}
		}
		return lhMap;
	}

	
	
}
