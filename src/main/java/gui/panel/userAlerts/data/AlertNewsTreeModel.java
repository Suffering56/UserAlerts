package gui.panel.userAlerts.data;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

@SuppressWarnings("serial")
public class AlertNewsTreeModel extends DefaultTreeModel {

	public AlertNewsTreeModel(DefaultMutableTreeNode root) {
		super(root);
	}

	public AlertNewsTreeModel(Stock stock) {
		super(stock.getNewsTreeRoot());
	}
}
