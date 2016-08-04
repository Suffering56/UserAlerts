package gui.panel.userAlerts;

public class App {
	public static AppLogger appLogger;

	public class AppLogger {
		public void error(String errorMsg, Exception e) {
			System.out.println(errorMsg);
			e.printStackTrace();
		}
	}
}
