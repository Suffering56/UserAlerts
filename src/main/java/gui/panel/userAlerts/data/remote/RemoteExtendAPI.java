package gui.panel.userAlerts.data.remote;

import javax.swing.JOptionPane;

import gui.panel.userAlerts.App;
import gui.panel.userAlerts.control.AlertsCommonFrame;
import gui.panel.userAlerts.data.ClientNewsAlert;
import gui.panel.userAlerts.data.ClientQuotesAlert;
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
import p.alerts.client_api.interf.IDownloadQuotesAlertsCallBack;
import p.alerts.client_api.interf.ILoginCallbackListener;
import p.alerts.client_api.interf.ILogoutCallBackListener;
import p.alerts.client_api.interf.IUserRegConformationCallBack;
import p.alerts.client_api.interf.IUserRegistrationCallBackListener;

public class RemoteExtendAPI extends RemoteBasicAPI {

	public RemoteExtendAPI(Stock stock) {
		super(stock);
		alertsAPI = new AlertsSMSandMailAPI();
		extendAlertsAPI = new AlertsExtensionAPI(alertsAPI);
		connectionClientAPI.assignSink(alertsAPI.getNetworkInterface());
	}

	public void _login(String userName, String password) {
		App.appLogger.info("_login: userName=" + userName + ", pass=" + password);
		alertsAPI.login(userName, password, new ILoginCallbackListener() {
			public void loginSuccessful() {
				App.appLogger.info("loginListener->success");
				alertsAPI.subscribeToAlertEvents(alertActionListener);
				downloadAlerts();
			}

			public void loginOperationFailed(String error) {
				App.appLogger.error("loginListener->error: " + error);
				new ExtendOptionPane().showBasicLookAndFeelMessageDialog(null, error, "Ошибка авторизации", JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
		});
	}

	@Override
	protected void initAdditionalInterfaces() {
		initLoginAndLogoutListeners();
		initTriggerListeners();
		initNewsAlertListeners();
		initQuotesAlertListeners();
		initHistoryListeners();
	}

	private void initLoginAndLogoutListeners() {
		loginListener = new ILoginCallbackListener() {
			public void loginSuccessful() {
				App.appLogger.info("loginListener->success");
				afterLoginHandle();
			}

			public void loginOperationFailed(String error) {
				App.appLogger.error("loginListener->error: " + error);
				new ExtendOptionPane().showBasicLookAndFeelMessageDialog(null, error, "Ошибка авторизации", JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
		};

		logoutListener = new ILogoutCallBackListener() {
			public void logoutSuccessful() {
				App.appLogger.info("logoutListener->success");
			}

			public void logoutFailed(String reason) {
				App.appLogger.error("logoutListener->error: " + reason);
			}
		};

		registrationBasicListener = new IUserRegistrationCallBackListener() {
			@Override
			public void registrationFirstStagePassConfirmEmailAndSMSSended() {
				App.appLogger.info("registrationBasicListener->success");
			}

			@Override
			public void registrationFailed(String error) {
				App.appLogger.error("registrationBasicListener->error: " + error);
			}
		};

		registrationConfirmListener = new IUserRegConformationCallBack() {
			@Override
			public void confirmationSuccessful() {
				App.appLogger.info("registrationConfirmListener->success");
			}

			@Override
			public void confirmationFailed(String error) {
				App.appLogger.error("registrationConfirmListener->error: " + error);
			}
		};
	}

	private void initTriggerListeners() {
		alertActionListener = new IAlertEventsCallBack() {
			@Override
			public void eventOccured(NewsFireAlert[] arg0, QuoteAlert[] arg1) {
				App.appLogger.info("Алерт сработал!");
			}

			@Override
			public void falureOccured(String error) {
				App.appLogger.error("alertActionListener: error = " + error);
			}
		};
	}

	private void initNewsAlertListeners() {
		newsAlertDownloadListener = new IDownloadNewsAlertsCallBack() {
			@Override
			public void alertsDownloaded(NewsAlert[] newsAlerts) {
				try {
					App.appLogger.info("newsAlertDownloadListener->success: " + newsAlerts.length);
					stock.updateNewsAlertsTable(newsAlerts);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void alertsDownloadFailed(String error) {
				App.appLogger.error("newsAlertDownloadListener->error: " + error);
			}
		};

		newsAlertExtensionListener = new IExtensionOperation<NewsAlert>() {
			@Override
			public void operationSucceed(NewsAlert[] newsAlerts) {
				try {
					App.appLogger.error("newsAlertExtensionListener->success: " + newsAlerts.length + " (please ignore red color)");
					stock.updateNewsAlertsTable(newsAlerts);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void operationFailed(String error) {
				App.appLogger.error("newsAlertExtensionListener->error: " + error);
			}
		};
	}

	private void initQuotesAlertListeners() {
		quotesAlertDownloadListener = new IDownloadQuotesAlertsCallBack() {
			@Override
			public void alertsDownloaded(QuoteAlert[] quotesAlerts) {
				try {
					App.appLogger.info("quotesAlertDownloadListener->success: " + quotesAlerts.length);
					stock.updateQuotesAlertsTable(quotesAlerts);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void alertsDownloadFailed(String error) {
				App.appLogger.error("quotesAlertDownloadListener->error: " + error);
			}
		};

		quotesAlertExtensionListener = new IExtensionOperation<QuoteAlert>() {
			@Override
			public void operationSucceed(QuoteAlert[] quotesAlerts) {
				try {
					App.appLogger.error("quotesAlertExtensionListener->success: " + quotesAlerts.length + " (please ignore red color)");
					stock.updateQuotesAlertsTable(quotesAlerts);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void operationFailed(String error) {
				App.appLogger.error("quotesAlertExtensionListener->error: " + error);
			}
		};
	}

	// ============================ Basic =============================
	public void login(String userName, String password) {
		App.appLogger.info("do_login: userName=" + userName + ", pass=" + password);
		alertsAPI.login(userName, password, loginListener);
	}

	private void afterLoginHandle() {
		alertsAPI.subscribeToAlertEvents(alertActionListener);
		new AlertsCommonFrame(stock).show();
	}

	public void logout() {
		alertsAPI.logout(logoutListener);
	}

	public void registerUser(String userName, String pass, String phone, String email) {
		App.appLogger.info("registerUser: userName=" + userName + ", pass=" + pass + ", phone=" + phone + ", email=" + email);
		//		alertsAPI.registerUser(userName, pass, phone, email, registrationBasicListener);
	}

	public void confirmRegistration(String userName, String emailCode, String smsCode) {
		App.appLogger.info("confirmRegistration: userName=" + userName + ", emailCode=" + emailCode + ", smsCode=" + smsCode);
		//		alertsAPI.performConfirmUserRegistration(userName, emailCode, smsCode, registrationConfirmListener);
	}

	public void downloadAlerts() {
		alertsAPI.downloadActiveNewsAlerts(newsAlertDownloadListener);
		alertsAPI.downloadActiveQuotesAlerts(quotesAlertDownloadListener);
	}

	// =========================== NewsAlert ===========================
	public void createNewsAlert(ClientNewsAlert alert) {
		extendAlertsAPI.createAlertAsync(alert.convertToServerAlert(false), newsAlertExtensionListener);
	}

	public void updateNewsAlert(ClientNewsAlert alert) {
		extendAlertsAPI.updateAlertAsync(alert.convertToServerAlert(true), newsAlertExtensionListener);
	}

	public void removeNewsAlert(ClientNewsAlert alert) {
		extendAlertsAPI.removeNewsAlertsAsync(new String[] { alert.getId() }, newsAlertExtensionListener);
	}

	// =========================== QuotesAlert ===========================
	public void createQuotesAlert(ClientQuotesAlert alert) {
		extendAlertsAPI.createAlertAsync(alert.convertToServerAlert(false), quotesAlertExtensionListener);
	}

	public void updateQuotesAlert(ClientQuotesAlert alert) {
		extendAlertsAPI.updateAlertAsync(alert.convertToServerAlert(true), quotesAlertExtensionListener);
	}

	public void removeQuotesAlert(ClientQuotesAlert alert) {
		extendAlertsAPI.removeQuotesAlertsAsync(new String[] { alert.getId() }, quotesAlertExtensionListener);
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
	@SuppressWarnings("unused")
	private IUserRegistrationCallBackListener registrationBasicListener;
	@SuppressWarnings("unused")
	private IUserRegConformationCallBack registrationConfirmListener;

	private IAlertEventsCallBack alertActionListener;

	private IDownloadNewsAlertsCallBack newsAlertDownloadListener;
	private IDownloadQuotesAlertsCallBack quotesAlertDownloadListener;
	private IDownloadNewsFireAlertsHistoryCallBack historyDownloadListener;
	private IExtensionOperation<NewsAlert> newsAlertExtensionListener;
	private IExtensionOperation<QuoteAlert> quotesAlertExtensionListener;

	private IExtensionOperation<NewsFireAlert> historyRemoveListener;

	private static final int LIMIT = 100;
}
