package gui.panel.userAlerts.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javax.swing.tree.TreeNode;

import gui.panel.userAlerts.parent.PrimaryFrame;
import p.alerts.client_api.NewsAlert;

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

	public void updateNewsAlertsTable(List<NewsAlert> serverNewsAlertsList) {
		newsAlertsList.clear();
		for (NewsAlert serverAlert : serverNewsAlertsList) {
			ClientNewsAlert clientNewsAlert = new ClientNewsAlert(serverAlert);
			newsAlertsList.add(clientNewsAlert);
			alertsList.add(clientNewsAlert);
		}

		primaryFrame.updateNewsAlertsTableFromStock();
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

	private final PrimaryFrame primaryFrame;
	private TreeNode newsRoot;
	private final RemoteExtendAPI remoteAPI;
	private final List<ClientNewsAlert> newsAlertsList = new ArrayList<ClientNewsAlert>();
	private final List<ClientQuotesAlert> quotesAlertsList = new ArrayList<ClientQuotesAlert>();
	private final List<ClientAlert> alertsList = new ArrayList<ClientAlert>();
}
