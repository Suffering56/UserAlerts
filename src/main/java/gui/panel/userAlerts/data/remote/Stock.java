package gui.panel.userAlerts.data.remote;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javax.swing.tree.TreeNode;

import gui.panel.userAlerts.data.ClientAlert;
import gui.panel.userAlerts.data.ClientNewsAlert;
import gui.panel.userAlerts.data.ClientQuotesAlert;
import gui.panel.userAlerts.data.HistoryEntity;
import gui.panel.userAlerts.parent.HistoryFrame;
import gui.panel.userAlerts.parent.CommonFrame;
import p.alerts.client_api.NewsAlert;
import p.alerts.client_api.NewsFireAlert;
import p.alerts.client_api.QuoteAlert;

public class Stock extends Observable {

	public Stock() {
		remoteAPI = new RemoteExtendAPI(this);
	}

	// ================= Initialisation ===================
	public void setCommonFrame(CommonFrame commonFrame) {
		this.commonFrame = commonFrame;
		remoteAPI.downloadAlerts();
	}

	public void setHistoryFrame(HistoryFrame historyFrame) {
		this.historyFrame = historyFrame;
		remoteAPI.downloadHistory();
	}

	// ===================== Basic ==========================
	public void login(String userName, String password) {
		remoteAPI.login(userName, password);
	}

	public void _login(String userName, String password) {
		remoteAPI._login(userName, password);
	}

	public void logout() {
		remoteAPI.logout();
	}

	public void registerUser(String userName, String pass, String phone, String email) {
		remoteAPI.registerUser(userName, pass, phone, email);
	}

	public void confirmRegistration(String userName, String emailCode, String smsCode) {
		remoteAPI.confirmRegistration(userName, emailCode, smsCode);
	}

	public List<ClientAlert> getAllAlerts() {
		return alertsList;
	}

	// ==================== NewsAlert ======================
	public List<ClientNewsAlert> getAllNewsAlerts() {
		return newsAlertsList;
	}

	public void createNewsAlert(ClientNewsAlert alert) {
		remoteAPI.createNewsAlert(alert);
	}

	public void updateNewsAlert(ClientNewsAlert alert) {
		remoteAPI.updateNewsAlert(alert);
	}

	public void removeNewsAlert(ClientNewsAlert alert) {
		remoteAPI.removeNewsAlert(alert);
	}

	public void updateNewsAlertsTable(NewsAlert[] newsAlerts) {
		alertsList.clear();
		newsAlertsList.clear();
		for (NewsAlert serverAlert : newsAlerts) {
			ClientNewsAlert clientNewsAlert = new ClientNewsAlert(serverAlert);
			newsAlertsList.add(clientNewsAlert);
			alertsList.add(clientNewsAlert);
		}
		alertsList.addAll(newsAlertsList);

		if (commonFrame != null) {
			commonFrame.updateNewsAlertsTableFromStock();
		}
	}

	// ==================== QuotesAlert ======================
	public List<ClientQuotesAlert> getAllQuotesAlerts() {
		return quotesAlertsList;
	}

	public void createQuotesAlert(ClientQuotesAlert alert) {
		remoteAPI.createQuotesAlert(alert);
	}

	public void updateQuotesAlert(ClientQuotesAlert alert) {
		remoteAPI.updateQuotesAlert(alert);
	}

	public void removeQuotesAlert(ClientQuotesAlert alert) {
		remoteAPI.removeQuotesAlert(alert);
	}

	public void updateQuotesAlertsTable(QuoteAlert[] quotesAlerts) {
		alertsList.clear();
		quotesAlertsList.clear();
		for (QuoteAlert serverAlert : quotesAlerts) {
			ClientQuotesAlert clientQuotesAlert = new ClientQuotesAlert(serverAlert);
			quotesAlertsList.add(clientQuotesAlert);
			alertsList.add(clientQuotesAlert);
		}
		alertsList.addAll(newsAlertsList);

		if (commonFrame != null) {
			commonFrame.updateQuotesAlertsTableFromStock();
		}
	}

	// ======================== History ==========================
	public List<HistoryEntity> getAllHistory() {
		return historyList;
	}

	public void removeAllHistory() {
		remoteAPI.removeAllHistory();
	}

	public void removeSingleHistory(HistoryEntity entity) {
		remoteAPI.removeSingleHistory(entity);
	}

	public void updateHistoryTable(NewsFireAlert[] alerts) {
		historyList.clear();
		for (NewsFireAlert serverAlert : alerts) {
			HistoryEntity historyEntity = new HistoryEntity(serverAlert);
			historyList.add(historyEntity);
		}

		if (historyFrame != null) {
			historyFrame.updateHistoryTableFromStock();
		}
	}

	// ======================= Root =======================
	public TreeNode getNewsRoot() {
		return newsRoot;
	}

	public void setNewsRoot(TreeNode newsRoot) {
		this.newsRoot = newsRoot;
		setChanged();
		notifyObservers();
	}

	// ===================================================
	private CommonFrame commonFrame;
	private HistoryFrame historyFrame;

	private TreeNode newsRoot;
	private final RemoteExtendAPI remoteAPI;
	private final List<ClientNewsAlert> newsAlertsList = new ArrayList<ClientNewsAlert>();
	private final List<ClientQuotesAlert> quotesAlertsList = new ArrayList<ClientQuotesAlert>();
	private final List<ClientAlert> alertsList = new ArrayList<ClientAlert>();

	private final List<HistoryEntity> historyList = new ArrayList<HistoryEntity>();
}
