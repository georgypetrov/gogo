package rest.api.test.sanity.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import io.restassured.RestAssured;

public class ConfigurationCore {
	public static Map<String, String> dataCollector = new HashMap<>();

	private static final String DEFAULT_SERVER_HOST = "http://localhost";

	private static final String DEFAULT_SERVER_BASE = "/";

	private static final String DEFAULT_SERVER_PORT = "3001";

	/**
	 * Setup connection to server. If properties are not provided in
	 * config.properties file, default values will be applied.
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void setupConnection() throws FileNotFoundException, IOException {
		String port = getProperty("server.port");
		if (port == null) {
			RestAssured.port = Integer.valueOf(DEFAULT_SERVER_PORT);
		} else {
			RestAssured.port = Integer.valueOf(port);
		}

		String basePath = getProperty("server.base");
		if (basePath == null) {
			basePath = DEFAULT_SERVER_BASE;
		} else {
			RestAssured.basePath = basePath;
		}

		String baseHost = getProperty("server.host");
		if (baseHost == null) {
			baseHost = DEFAULT_SERVER_HOST;
		} else {
			RestAssured.baseURI = baseHost;
		}

	}

	/**
	 * Add value to data collector and are kept during test run.
	 * 
	 * @param key
	 * @param value
	 */
	public static void addValueToCollector(String key, String value) {
		dataCollector.put(key, value);
	}

	/**
	 * Get value to data collector and use during test run in other methods or test
	 * scenarios
	 * 
	 * @param key
	 * @return String value
	 */
	public static String getValueFromCollector(String key) {
		return dataCollector.get(key);
	}

	/**
	 * Get property from config file, located in
	 * src/main/resources/config.properties
	 * 
	 * @param propertyValue Provide parameter key. Example: server.host
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static String getProperty(String propertyValue) throws FileNotFoundException, IOException {

		Properties getValueConfig = new Properties();
		getValueConfig.load(new FileReader(new File("src/main/resources/config.properties")));

		return getValueConfig.getProperty(propertyValue);
	}
}
