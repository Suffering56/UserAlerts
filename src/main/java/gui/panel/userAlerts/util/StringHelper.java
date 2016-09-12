package gui.panel.userAlerts.util;

import java.nio.charset.Charset;

public class StringHelper {

	public static String removeLastSymbol(String str) {
		if (str.isEmpty()) {
			return str;
		}
		return str.substring(0, str.length() - 1);
	}

	public static String convertTo1251(String text) {
		return new String(text.getBytes(), Charset.forName("windows-1251"));
		// Run Configurations -> VM arguments: -Dfile.encoding=cp1251
		// return text;
	}

	public static final String EMPTY = "";
}
