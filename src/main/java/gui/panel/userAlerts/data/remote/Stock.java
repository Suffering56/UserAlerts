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
import gui.panel.userAlerts.parent.PrimaryFrame;
import p.alerts.client_api.NewsAlert;
import p.alerts.client_api.NewsFireAlert;

public class Stock extends Observable {

	public Stock(PrimaryFrame primaryFrame) {
		this.primaryFrame = primaryFrame;
		remoteAPI = new RemoteExtendAPI(this);
	}

	public void logout() {
		remoteAPI.logout();
	}

	public void createNewsAlert(ClientNewsAlert alert) {
		remoteAPI.createNewsAlert(alert);
	}

	public void createQuotesAlert(ClientQuotesAlert alert) {
		int id = alert.getId();

		if (id == -1) {
			id = alertsList.isEmpty() ? 0 : alertsList.get(alertsList.size() - 1).getId();
		}

		while (containsId(id)) {
			id++;
		}

		alert.setId(id);
		alertsList.add(alert);
		quotesAlertsList.add((ClientQuotesAlert) alert);
	}

	public void updateNewsAlert(ClientNewsAlert alert) {
		remoteAPI.updateNewsAlert(alert);
	}

	public void removeNewsAlert(ClientNewsAlert alert) {
		remoteAPI.removeNewsAlert(alert);
	}

	public void removeQuotesAlert(int id) {
		int removeIndex = -1;
		ClientAlert removeObject = null;

		for (int i = 0; i < alertsList.size(); i++) {
			if (alertsList.get(i).getId() == id) {
				removeIndex = i;
				removeObject = alertsList.get(i);
			}
		}

		if (removeIndex != -1) {
			if (removeObject instanceof ClientQuotesAlert) {
				quotesAlertsList.remove(removeObject);
				alertsList.remove(removeIndex);
			}
		}
	}

	public void updateQuotesAlertsTable() {
		//to be continued.....
	}

	private boolean containsId(int id) {
		for (ClientAlert alert : alertsList) {
			if (alert.getId() == id) {
				return true;
			}
		}
		return false;
	}

	public List<ClientNewsAlert> getAllNewsAlerts() {
		return newsAlertsList;
	}

	public List<ClientQuotesAlert> getAllQuotesAlerts() {
		return quotesAlertsList;
	}

	public List<ClientAlert> getAllAlerts() {
		return alertsList;
	}

	public TreeNode getNewsRoot() {
		return newsRoot;
	}

	public void setNewsRoot(TreeNode newsRoot) {
		this.newsRoot = newsRoot;
		setChanged();
		notifyObservers();
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

	public void updateNewsAlertsTable(NewsAlert[] alerts) {
		newsAlertsList.clear();
		for (NewsAlert serverAlert : alerts) {
			ClientNewsAlert clientNewsAlert = new ClientNewsAlert(serverAlert);
			newsAlertsList.add(clientNewsAlert);
			alertsList.add(clientNewsAlert);
		}

		if (primaryFrame != null) {
			primaryFrame.updateNewsAlertsTableFromStock();
		}
	}

	public List<HistoryEntity> getAllHistory() {
		return historyList;
	}

	public void setHistoryFrame(HistoryFrame historyFrame) {
		this.historyFrame = historyFrame;
		remoteAPI.downloadHistory();
	}

	public void removeAllHistory() {
		remoteAPI.removeAllHistory();
	}

	public void removeSingleHistory(HistoryEntity entity) {
		remoteAPI.removeSingleHistory(entity);
	}

	private final PrimaryFrame primaryFrame;
	private HistoryFrame historyFrame;

	private TreeNode newsRoot;
	private final RemoteExtendAPI remoteAPI;
	private final List<ClientNewsAlert> newsAlertsList = new ArrayList<ClientNewsAlert>();
	private final List<ClientQuotesAlert> quotesAlertsList = new ArrayList<ClientQuotesAlert>();
	private final List<ClientAlert> alertsList = new ArrayList<ClientAlert>();

	private final List<HistoryEntity> historyList = new ArrayList<HistoryEntity>();
}
