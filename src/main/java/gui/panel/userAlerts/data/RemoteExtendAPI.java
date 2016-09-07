package gui.panel.userAlerts.data;

import java.util.Arrays;

import javax.swing.JOptionPane;

import gui.panel.userAlerts.App;
import gui.panel.userAlerts.util.ExtendOptionPane;
import p.alerts.client_api.AlertsSMSandMailAPI;
import p.alerts.client_api.NewsAlert;
import p.alerts.client_api.NotFullyInitilazedException;
import p.alerts.client_api.QuoteAlert;
import p.alerts.client_api.interf.IAlertEventsCallBack;
import p.alerts.client_api.interf.ICreateAlertCallBack;
import p.alerts.client_api.interf.IDownloadNewsAlertsCallBack;
import p.alerts.client_api.interf.ILoginCallbackListener;
import p.alerts.client_api.interf.ILogoutCallBackListener;
import p.alerts.client_api.interf.IRemoveAlertCallBack;

public class RemoteExtendAPI extends RemoteBasicAPI {

	public RemoteExtendAPI(Stock stock) {
		super(stock);
		alertsAPI = new AlertsSMSandMailAPI();

		connectionClientAPI.assignSink(alertsAPI.getNetworkInterface());
		alertsAPI.login("doom", "doom_pass", loginListener);
	}

	@Override
	protected void initAdditionalInterfaces() {
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

		alertActionListener = new IAlertEventsCallBack() {
			@Override
			public void falureOccured(String error) {
				App.appLogger.error("falure Occured: " + error);
			}

			@Override
			public void eventOccured(NewsAlert[] arg0, QuoteAlert[] arg1) {
				App.appLogger.info("Алерт сработал!");
			}
		};

		newsAlertDownloadListener = new IDownloadNewsAlertsCallBack() {
			@Override
			public void alertsDownloaded(NewsAlert[] newsAlerts) {
				try {
					App.appLogger.info("alertsDownloaded: " + newsAlerts.length);
					stock.updateNewsAlertsTable(Arrays.asList(newsAlerts));
					//					for (NewsAlert alert : newsAlerts) {
					//						System.out.println("NewsAlert: name = <" + alert.getName() + ">, id = <" + alert.getAlertID() + ">");
					//					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void alertsDownloadFailed(String error) {
				App.appLogger.error("alerts Download Failed: " + error);
			}
		};

		createAlertListener = new ICreateAlertCallBack() {
			@Override
			public void alertCreationSuccessful(String info) {
				App.appLogger.info("alert Creation Succsessful: " + info);
				alertsAPI.downloadActiveNewsAlerts(newsAlertDownloadListener);
			}

			@Override
			public void alertCreationFailed(String error) {
				App.appLogger.error("alert Creation Failed: " + error);
			}
		};

		removeAlertListener = new IRemoveAlertCallBack() {
			@Override
			public void removeAlertSuccessful() {
				App.appLogger.info("remove Alert Successful");
				alertsAPI.downloadActiveNewsAlerts(newsAlertDownloadListener);
			}

			@Override
			public void removeAlertFailed(String error) {
				App.appLogger.error("remove Alert Failed: " + error);
			}
		};
	}

	private void afterLoginHandle() {
		alertsAPI.subscribeToAlertEvents(alertActionListener);
		alertsAPI.downloadActiveNewsAlerts(newsAlertDownloadListener);

		//		this.createNewsAlert(new ClientNewsAlert("Alert_3", "BusinessNews:4;19:0:*,Comments:*:0:*", false, "key1", "",
		//				Expression.NOT, RelevanceFilterType.BY_RELEVANCE, "exclude1", "", Expression.NOT, SEARCH_NEWS_TYPE.EVERYWERE, false,
		//				"", false, "", false, "", false, null, true));

		//				alertsAPI.removeNewsAlert("doom_2016_09_07__13_10_49", removeAlertListener);
	}

	public void logout() {
		alertsAPI.logout(logoutListener);
	}

	public void createNewsAlert(ClientNewsAlert alert) {
		try {
			alertsAPI.createNewsAlert(alert.convertToServerNewsAlert(), createAlertListener);
		} catch (NotFullyInitilazedException e) {
			e.printStackTrace();
		}
	}

	public void removeNewsAlert(ClientNewsAlert alert) {
		alertsAPI.removeNewsAlert(alert.getNewsID(), removeAlertListener);
	}

	public void updateNewsAlert(ClientNewsAlert alert) {
		//		alertsAPI.updateNewsAlert(alert.convertToServerNewsAlert(), updateAlertListener);
		System.out.println("ExtendRemoteAPI. updateNewsAlert: " + alert.getName());
	}

	private final AlertsSMSandMailAPI alertsAPI;
	private ILoginCallbackListener loginListener;
	private ILogoutCallBackListener logoutListener;
	private IAlertEventsCallBack alertActionListener;
	private IDownloadNewsAlertsCallBack newsAlertDownloadListener;
	private ICreateAlertCallBack createAlertListener;
	private IRemoveAlertCallBack removeAlertListener;

}
