package gui.panel.userAlerts.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import p.alerts.client_api.util.FormatFactory;

public class DateHelper {

	public static String convertFull(Date date) {
		return fullDateFormat.format(date);
	}

	public static String convertDate(Date date) {
		return onlyDateFormat.format(date);
	}

	public static String convertTime(Date date) {
		return onlyTimeFormat.format(date);
	}

	private static final SimpleDateFormat fullDateFormat = FormatFactory.createDateFormat("dd.MM.yyyy HH:mm");

	private static final SimpleDateFormat onlyDateFormat = FormatFactory.createDateFormat("dd.MM.yyyy");

	private static final SimpleDateFormat onlyTimeFormat = FormatFactory.createDateFormat("HH:mm");
}
