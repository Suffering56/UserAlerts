package gui.panel.userAlerts.util;

public class StringHelper {

	public static String removeLastSymbol(String str) {
		if (str.isEmpty()) {
			return str;
		}
		return str.substring(0, str.length() - 1);
	}

}
