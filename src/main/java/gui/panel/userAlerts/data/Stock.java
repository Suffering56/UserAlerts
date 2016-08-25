package gui.panel.userAlerts.data;

import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.TreeNode;

import gui.panel.userAlerts.remote.NewsTreeDownloader;

public class Stock {

	public Stock() {
		new NewsTreeDownloader(this);
	}

	public void addNewsAlert(NewsAlert newsAlert) {
		int id = newsAlert.getId();

		if (newsAlert.getId() == -1) {
			id = newsAlertsList.isEmpty() ? 0 : newsAlertsList.get(newsAlertsList.size() - 1).getId();
			while (containsNewsId(id)) {
				id++;
			}
		} else {

			while (containsNewsId(id)) {
				id++;
			}
		}

		newsAlert.setId(id);
		newsAlertsList.add(newsAlert);
	}
	
	public void addQuotesAlert(QuotesAlert quotesAlert) {
		int id = quotesAlert.getId();
		
		if (quotesAlert.getId() == -1) {
			id = quotesAlertsList.isEmpty() ? 0 : quotesAlertsList.get(quotesAlertsList.size() - 1).getId();
			while (containsQuotesId(id)) {
				id++;
			}
		} else {

			while (containsQuotesId(id)) {
				id++;
			}
		}
		
		quotesAlert.setId(id);
		quotesAlertsList.add(quotesAlert);
	}

	public boolean removeNewsAlertById(int id) {
		int removeIndex = -1;
		for (int i = 0; i < newsAlertsList.size(); i++) {
			if (newsAlertsList.get(i).getId() == id) {
				removeIndex = i;
			}
		}
		if (removeIndex != -1) {
			newsAlertsList.remove(removeIndex);
			return true;
		}
		return false;
	}
	
	public boolean removeQuotesAlertById(int id) {
		int removeIndex = -1;
		for (int i = 0; i < quotesAlertsList.size(); i++) {
			if (quotesAlertsList.get(i).getId() == id) {
				removeIndex = i;
			}
		}
		if (removeIndex != -1) {
			quotesAlertsList.remove(removeIndex);
			return true;
		}
		return false;
	}

	public boolean containsNewsId(int id) {
		for (NewsAlert alert : newsAlertsList) {
			if (alert.getId() == id) {
				return true;
			}
		}
		return false;
	}
	
	public boolean containsQuotesId(int id) {
		for (QuotesAlert alert : quotesAlertsList) {
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

	public TreeNode getNewsRoot() {
		return newsRoot;
	}
	
	public void setNewsRoot(final TreeNode newsRoot) {
		this.newsRoot = newsRoot;
	}
	
	public List<Alert> getAlertsList() {
		List<Alert> result = new ArrayList<Alert>();
		result.addAll(newsAlertsList);
		result.addAll(quotesAlertsList);
		return result;
	}

	private TreeNode newsRoot;
	private final List<NewsAlert> newsAlertsList = new ArrayList<NewsAlert>();
	private final List<QuotesAlert> quotesAlertsList = new ArrayList<QuotesAlert>();
}
