package gui.panel.userAlerts;

public class App {
	public static AppLogger appLogger = new AppLogger();

	public static class AppLogger {
		public void error(String errorMsg, Exception e) {
			System.out.println(errorMsg);
			e.printStackTrace();
		}

		public void info(Object msg) {
			System.out.println(msg);
		}

		public void error(String errorMsg) {
			System.out.println(errorMsg);
		}
	}
}
