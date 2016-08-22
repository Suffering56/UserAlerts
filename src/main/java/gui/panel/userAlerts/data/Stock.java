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
			id = alertsList.isEmpty() ? 0 : alertsList.get(alertsList.size() - 1).getId();
			while (containsId(id)) {
				id++;
			}
		} else {

			while (containsId(id)) {
				id++;
			}
		}

		alert.setId(id);
		alertsList.add(alert);
	}

	public boolean removeById(int id) {
		int removeIndex = -1;
		for (int i = 0; i < alertsList.size(); i++) {
			if (alertsList.get(i).getId() == id) {
				removeIndex = i;
			}
		}
		if (removeIndex != -1) {
			alertsList.remove(removeIndex);
			return true;
		}
		return false;
	}

	public boolean containsId(int id) {
		for (NewsAlert alert : alertsList) {
			if (alert.getId() == id) {
				return true;
			}
		}
		return false;
	}

	public List<NewsAlert> getNewsAlertsList() {
		return alertsList;
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
	private final List<NewsAlert> alertsList = new ArrayList<NewsAlert>();
}
