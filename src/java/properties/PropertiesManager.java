package properties;

import java.io.IOException;
import java.util.Properties;

public class PropertiesManager {

	private static PropertiesManager instance = null;
	private Properties properties;

	protected PropertiesManager() throws IOException {
		properties = new Properties();
		properties.load(getClass().getResourceAsStream("/resources/config.properties"));
	}

	public static PropertiesManager getInstance() {
		if (instance == null) {
			try {
				instance = new PropertiesManager();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		return instance;
	}

	public String getValue(String key) {
		return properties.getProperty(key);
	}
}
