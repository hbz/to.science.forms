/**
 * 
 */
package services;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

/**
 * @author aquast
 * Class is to facilitate and standardize loading Vocabularys form local 
 * files - If remote loading is not adviced
 */
public class GenericPropertiesLoader {

	public Map<String, String> loadVocabMap(String propertiesFileName) {
		Map<String, String> vocabMap = null;
		Properties vocabProp = new Properties();
		// InputStream is = get;
		try {
			vocabProp.load(loadPropertiesFromFile(propertiesFileName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		vocabProp.putAll(vocabMap);
		return vocabMap;

	}

	private InputStream loadPropertiesFromFile(String propertiesFileName) {
		try (InputStream propStream = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("conf/" + propertiesFileName)) {
			return propStream;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	};
}
