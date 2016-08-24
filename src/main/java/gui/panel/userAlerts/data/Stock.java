package gui.panel.userAlerts.data;

import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.TreeNode;

import gui.panel.userAlerts.parent.TreeUpdateListener;
import gui.panel.userAlerts.remote.NewsTreeDownloader;
import gui.panel.userAlerts.util.IOHelper;

public class Stock {

	public Stock() {
		new NewsTreeDownloader(this);
	}

	public void add(NewsAlert alert) {
		int id = alert.getId();

		if (alert.getId() == -1) {
			id = newsAlertsList.isEmpty() ? 0 : newsAlertsList.get(newsAlertsList.size() - 1).getId();
			while (containsId(id)) {
				id++;
			}
		} else {

			while (containsId(id)) {
				id++;
			}
		}

		alert.setId(id);
		newsAlertsList.add(alert);
	}

	public boolean removeById(int id) {
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

	public boolean containsId(int id) {
		for (NewsAlert alert : newsAlertsList) {
			if (alert.getId() == id) {
				return true;
			}
		}
		return false;
	}

	public List<NewsAlert> getNewsAlertsList() {
		return newsAlertsList;
	}

	public List<QuotesAlert> getQuotesAlertsList() {
		return quotesAlertsList;
	}
	
	public List<Alert> getAlertsList() {
		List<Alert> result = new ArrayList<Alert>();
		result.addAll(newsAlertsList);
		result.addAll(quotesAlertsList);
		return result;
	}

	public void updateNewsTree(final TreeNode root) {
		this.newsRootNode = root;
		new Runnable() {
			public void run() {
				while (true) {
					if (newsTreeUpdateListener != null) {
						newsTreeUpdateListener.treeUpdateEvent();
						break;
					}
					if (stop) {
						break;
					}
					IOHelper.sleep(1000);
				}
			}
		}.run();
	}

	public void setNewsTreeUpdateListener(TreeUpdateListener newsTreeUpdateListener) {
		this.newsTreeUpdateListener = newsTreeUpdateListener;
	}

	public TreeNode getNewsRootNode() {
		return newsRootNode;
	}

	public void stopUpdateNewsTree() {
		this.stop = true;
	}

	private TreeNode newsRootNode;
	private TreeUpdateListener newsTreeUpdateListener;
	private boolean stop = false;
	private final List<NewsAlert> newsAlertsList = new ArrayList<NewsAlert>();
	private final List<QuotesAlert> quotesAlertsList = new ArrayList<QuotesAlert>();
}
