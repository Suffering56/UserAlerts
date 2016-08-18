package gui.panel.userAlerts.data;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import gui.panel.userAlerts.App;
import gui.panel.userAlerts.constants.AlertsGeneralConstants;
import gui.panel.userAlerts.util.IOHelper;

public class NewsProperties {

	/**
	 * Загружает список новостей, которые не нужно показывать пользователю на
	 * форме создания/редактирования новостных алертов из конфигурационного
	 * файла.
	 */
	private NewsProperties() {
		Properties props = IOHelper.loadPropertiesFile(AlertsGeneralConstants.NEWS_CONFIG);
		String excludeNewsLine = props.getProperty("excludeNewsLine");
		List<String> tempList = parse(excludeNewsLine);
		excludeNewsSet.addAll(tempList);

		if (excludeNewsSet.isEmpty()) {
			App.appLogger.info("NewsProperties.excludeNewsSet is empty. Please check the property file <"
					+ AlertsGeneralConstants.NEWS_CONFIG + ">");
		}
	}

	private List<String> parse(String excludeNewsLine) {
		List<String> result = Arrays.asList(excludeNewsLine.split(separator));
		return result;
	}

	public static NewsProperties getInstance() {
		if (instance == null) {
			instance = new NewsProperties();
		}
		return instance;
	}

	/**
	 * Список новостей, которые не нужно показывать пользователю на форме
	 * создания/редактирования новостных алертов
	 */
	public Set<String> getExcludeNewsSet() {
		return excludeNewsSet;
	}

	private static NewsProperties instance = null;
	private Set<String> excludeNewsSet = new HashSet<String>();
	private static final String separator = ";";
}
