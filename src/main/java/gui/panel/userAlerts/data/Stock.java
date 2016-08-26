package gui.panel.userAlerts.data;

import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.TreeNode;

import gui.panel.userAlerts.remote.NewsTreeDownloader;

public class Stock {

	public Stock() {
		new NewsTreeDownloader(this);
	}

	public void addAlert(Alert alert) {
		int id = alert.getId();

		if (id == -1) {
			id = alertsList.isEmpty() ? 0 : alertsList.get(alertsList.size() - 1).getId();
		}

		while (containsId(id)) {
			id++;
		}

		alert.setId(id);
		alertsList.add(alert);

		if (alert instanceof NewsAlert) {
			newsAlertsList.add((NewsAlert) alert);
		} else if (alert instanceof QuotesAlert) {
			quotesAlertsList.add((QuotesAlert) alert);
		}
		
//		System.out.println("a id = " + alert.getId());
//		System.out.println("a alertsList size: " + alertsList.size());
//		System.out.println("a newsAlertsList size: " + newsAlertsList.size());
//		System.out.println("a quotesAlertsList size: " + quotesAlertsList.size());
//		System.out.println("-------------------------------------------");
	}

	public void removeAlertById(int id) {
		int removeIndex = -1;
		Alert removeObject = null;

		for (int i = 0; i < alertsList.size(); i++) {
			if (alertsList.get(i).getId() == id) {
				removeIndex = i;
				removeObject = alertsList.get(i);
			}
		}

		if (removeIndex != -1) {
			alertsList.remove(removeIndex);
			if (removeObject instanceof NewsAlert) {
				newsAlertsList.remove(removeObject);
			} else if (removeObject instanceof QuotesAlert) {
				quotesAlertsList.remove(removeObject);
			}
//			return true;
		}
//		return false;
//		System.out.println("r id = " + id);
//		System.out.println("r alertsList size: " + alertsList.size());
//		System.out.println("r newsAlertsList size: " + newsAlertsList.size());
//		System.out.println("r quotesAlertsList size: " + quotesAlertsList.size());
//		System.out.println("-------------------------------------------");
	}

	private boolean containsId(int id) {
		for (Alert alert : alertsList) {
			if (alert.getId() == id) {
				return true;
			}
		}
		return false;
	}

	public List<NewsAlert> getAllNewsAlerts() {
		return newsAlertsList;
	}

	public List<QuotesAlert> getAllQuotesAlerts() {
		return quotesAlertsList;
	}

	public List<Alert> getAllAlerts() {
		return alertsList;
	}

	public TreeNode getNewsRoot() {
		return newsRoot;
	}

	public void setNewsRoot(final TreeNode newsRoot) {
		this.newsRoot = newsRoot;
	}
	
	private TreeNode newsRoot;
	private final List<NewsAlert> newsAlertsList = new ArrayList<NewsAlert>();
	private final List<QuotesAlert> quotesAlertsList = new ArrayList<QuotesAlert>();
	private final List<Alert> alertsList = new ArrayList<Alert>();
}
