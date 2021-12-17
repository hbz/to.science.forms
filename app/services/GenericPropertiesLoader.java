/**
 * 
 */
package services;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
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
		Properties vocabProp = new Properties();
		String propertiesPath = play.Play.application().resource(propertiesFileName).getPath();

		try {
			vocabProp.load(loadPropertiesFromFile(propertiesPath));
			vocabProp.putAll(vocabMap);
			return vocabMap;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private InputStream loadPropertiesFromFile(String propertiesPath) {
		try (InputStream propStream = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(propertiesPath)) {
			return propStream;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	};
}
