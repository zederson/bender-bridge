package br.com.bender;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuration {

	private static final Configuration config = buildCondiguration();

	private Configuration() {
	}

	private Configuration(Properties prop) {
		this.port = prop.getProperty("port");
		this.name = prop.getProperty("name");
		this.timeOut = new Integer(prop.getProperty("timeOut"));
		this.dataRate = new Integer(prop.getProperty("dataRate"));
		this.properties = prop;
		this.urlMqtt = prop.getProperty("urlMqtt");
	}

	private String port;
	private String name;
	private int timeOut;
	private int dataRate;
	private Properties properties;
	private String urlMqtt;

	public int getDataRate() {
		return dataRate;
	}

	public void setDataRate(int dataRate) {
		this.dataRate = dataRate;
	}

	public int getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getValue(String key) {
		return properties.getProperty(key);
	}

	@Override
	public String toString() {
		return "Config [port=" + port + ", name=" + name + ", timeOut=" + timeOut + ", dataRate=" + dataRate + "]";
	}

	public static Configuration getInstance() {
		return config;
	}

	private static Configuration buildCondiguration() {
		try {

			Configuration cnf = new Configuration(getProperties());
			System.out.println(cnf);
			return cnf;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static Properties getProperties() {
		InputStream stream = getFile();
		Properties prop = new Properties();

		try {
			if (stream != null)
				prop.load(stream);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return prop;
	}

	private static InputStream getFile() {
		ClassLoader classLoader = Configuration.class.getClassLoader();
		return classLoader.getResourceAsStream("bender.properties");
	}

	public String getUrlMqtt() {
		return urlMqtt;
	}
}
