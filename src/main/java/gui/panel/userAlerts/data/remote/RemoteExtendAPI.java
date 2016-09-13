package gui.panel.userAlerts.data.remote;

import javax.swing.JOptionPane;

import gui.panel.userAlerts.App;
import gui.panel.userAlerts.data.ClientNewsAlert;
import gui.panel.userAlerts.data.HistoryEntity;
import gui.panel.userAlerts.util.ExtendOptionPane;
import p.alerts.client_api.AlertsSMSandMailAPI;
import p.alerts.client_api.NewsAlert;
import p.alerts.client_api.NewsFireAlert;
import p.alerts.client_api.QuoteAlert;
import p.alerts.client_api.extension.AlertsExtensionAPI;
import p.alerts.client_api.extension.IExtensionOperation;
import p.alerts.client_api.interf.IAlertEventsCallBack;
import p.alerts.client_api.interf.IDownloadNewsAlertsCallBack;
import p.alerts.client_api.interf.IDownloadNewsFireAlertsHistoryCallBack;
import p.alerts.client_api.interf.ILoginCallbackListener;
import p.alerts.client_api.interf.ILogoutCallBackListener;

public class RemoteExtendAPI extends RemoteBasicAPI {

	public RemoteExtendAPI(Stock stock) {
		super(stock);
		alertsAPI = new AlertsSMSandMailAPI();
		extendAlertsAPI = new AlertsExtensionAPI(alertsAPI);
		connectionClientAPI.assignSink(alertsAPI.getNetworkInterface());
		login();
	}

	@Override
	protected void initAdditionalInterfaces() {
		initLoginAndLogoutListeners();
		initTriggerListeners();
		initNewsAlertListeners();
		initHistoryListeners();
	}

	private void initLoginAndLogoutListeners() {
		loginListener = new ILoginCallbackListener() {
			public void loginSuccessful() {
				App.appLogger.info("login Successful");
				afterLoginHandle();
			}

			public void loginOperationFailed(String reason) {
				App.appLogger.error("Ошибка авторизации: " + reason);
				new ExtendOptionPane().showBasicLookAndFeelMessageDialog(null, reason, "Ошибка авторизации", JOptionPane.ERROR_MESSAGE);
			}
		};

		logoutListener = new ILogoutCallBackListener() {
			public void logoutSuccessful() {
				App.appLogger.info("logout Successful");
			}

			public void logoutFailed(String reason) {
				App.appLogger.error("logout Failed =" + reason);
			}
		};
	}

	private void initTriggerListeners() {
		alertActionListener = new IAlertEventsCallBack() {
			@Override
			public void falureOccured(String error) {
				App.appLogger.error("falure Occured: " + error);
			}

			@Override
			public void eventOccured(NewsFireAlert[] arg0, QuoteAlert[] arg1) {
				App.appLogger.info("Алерт сработал!");
			}
		};
	}

	private void initNewsAlertListeners() {
		newsAlertDownloadListener = new IDownloadNewsAlertsCallBack() {
			@Override
			public void alertsDownloaded(NewsAlert[] newsAlerts) {
				try {
					App.appLogger.info("alertsDownloaded: " + newsAlerts.length);
					stock.updateNewsAlertsTable(newsAlerts);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void alertsDownloadFailed(String error) {
				App.appLogger.error("alerts Download Failed: " + error);
			}
		};

		newsAlertListener = new IExtensionOperation<NewsAlert>() {
			@Override
			public void operationSucceed(NewsAlert[] newsAlerts) {
				try {
					App.appLogger.error("newsAlertListener: " + newsAlerts.length + " (please ignore red color)");
					stock.updateNewsAlertsTable(newsAlerts);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void operationFailed(String error) {
				App.appLogger.error("alert Creation Failed: " + error);
			}
		};
	}

	private void afterLoginHandle() {
		alertsAPI.subscribeToAlertEvents(alertActionListener);
		alertsAPI.downloadActiveNewsAlerts(newsAlertDownloadListener);
	}

	public void login() {
		alertsAPI.login("test", "123", loginListener);
		//		alertsAPI.login("doom", "doom_pass", loginListener);
	}

	public void logout() {
		alertsAPI.logout(logoutListener);
	}

	// =========================== NewsAlert ===========================
	public void createNewsAlert(ClientNewsAlert alert) {
		extendAlertsAPI.createAlertAsync(alert.convertToServerNewsAlert(true), newsAlertListener);
	}

	public void removeNewsAlert(ClientNewsAlert alert) {
		extendAlertsAPI.removeNewsAlertsAsync(new String[] { alert.getServerId() }, newsAlertListener);
	}

	public void updateNewsAlert(ClientNewsAlert alert) {
		extendAlertsAPI.updateAlertAsync(alert.convertToServerNewsAlert(false), newsAlertListener);
	}

	// =========================== History ===========================
	public void downloadHistory() {
		alertsAPI.getNewsFireAlertsHistory(LIMIT, historyDownloadListener);
	}

	public void removeAllHistory() {
		System.out.println("ExtendRemoteAPI: removeAllHistory");
		extendAlertsAPI.removeNewsFireAlertsAsync(LIMIT, null, historyRemoveListener);
	}

	public void removeSingleHistory(HistoryEntity entity) {
		System.out.println("ExtendRemoteAPI: removeHistoryById = " + entity.getId());
		extendAlertsAPI.removeNewsFireAlertsAsync(LIMIT, new String[] { entity.getId() }, historyRemoveListener);
	}

	private void initHistoryListeners() {
		historyDownloadListener = new IDownloadNewsFireAlertsHistoryCallBack() {
			@Override
			public void newsFireAlertsHistoryArrived(NewsFireAlert[] history) {
				App.appLogger.info("historyDownloadListener: " + history.length);
				stock.updateHistoryTable(history);
			}

			@Override
			public void failedToDownload(String error) {
				App.appLogger.error("historyDownloadListener error: " + error);
			}
		};

		historyRemoveListener = new IExtensionOperation<NewsFireAlert>() {
			@Override
			public void operationSucceed(NewsFireAlert[] history) {
				App.appLogger.info("historyRemoveListener: " + history.length);
				stock.updateHistoryTable(history);
			}

			@Override
			public void operationFailed(String error) {
				App.appLogger.error("historyRemoveListener error: " + error);
			}
		};
	}

	private final AlertsSMSandMailAPI alertsAPI;
	private final AlertsExtensionAPI extendAlertsAPI;
	private ILoginCallbackListener loginListener;
	private ILogoutCallBackListener logoutListener;
	private IAlertEventsCallBack alertActionListener;
	
	private IDownloadNewsAlertsCallBack newsAlertDownloadListener;
	private IDownloadNewsFireAlertsHistoryCallBack historyDownloadListener;
	private IExtensionOperation<NewsAlert> newsAlertListener;
	private IExtensionOperation<NewsFireAlert> historyRemoveListener;
	
	private static final int LIMIT = 100;
}
