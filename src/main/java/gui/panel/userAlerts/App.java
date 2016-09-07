package gui.panel.userAlerts;

public class App {
	public static AppLogger appLogger = new AppLogger();

	public static class AppLogger {

		public void info(Object msg) {
			System.out.println(msg);
		}

		public void error(String errorMsg) {
			System.err.println(errorMsg);
		}

		public void error(String errorMsg, Exception e) {
			error(errorMsg);
			e.printStackTrace();
		}
	}
}
