/**
 * 
 */
package services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Map;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Properties;


/**
 * @author aquast
 * Class is to facilitate and standardize loading Vocabularys form local 
 * files - If remote loading is not adviced
 */
public class GenericPropertiesLoader {

	public Map<String, String> loadVocabMap(String propertiesFileName) {
		Map<String, String> vocabMap = new LinkedHashMap<>();
		String propertiesPath = play.Play.application().resource(propertiesFileName).getPath();
		try {
			vocabMap = loadMapFromFile(propertiesPath);
			return vocabMap;
		} catch (Exception e) {
			play.Logger.error(e.getMessage());
		}
		return null;
	}

	private Properties loadPropertiesFromFile(String propertiesPath) {
		Properties prop = new Properties();
		try (Reader propReader = new FileReader(propertiesPath)) {
			prop.load(propReader);
			return prop;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	};
	private LinkedHashMap<String,String> loadMapFromFile(String propertiesPath) {
		LinkedHashMap<String,String> map = new LinkedHashMap<>();
		try {
			Reader propReader = new FileReader(propertiesPath);
			BufferedReader br = new BufferedReader(propReader);
			String line = null;
			while((line=br.readLine())!=null) {
				play.Logger.info("eingelesene Zeile: " + line);
				String[] keyValue = line.split("=");
				map.put(keyValue[0], keyValue[1]);
				play.Logger.info(map.get(keyValue[0]));
			}
			br.close();
			return map;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	};
}
