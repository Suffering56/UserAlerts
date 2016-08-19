package gui.panel.userAlerts.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import gui.panel.userAlerts.App;

public class IOHelper {
	
	public static void closeStream(InputStream in) {
		if (in != null) {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void closeStream(OutputStream out) {
		if (out != null) {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static Properties loadPropertiesFile(String propertyFileName) {
		try {
			Properties property = new Properties();
			property.load(new FileInputStream(propertyFileName));
			return property;
		} catch (IOException e) {
			App.appLogger.error("File: " + propertyFileName + " is not found. EX: ", e);
		}
		return null;
	}
	
	public static void sleep(long delay) {
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
