package gui.panel.userAlerts.data.tree;

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JTree;
import javax.swing.tree.TreeCellRenderer;

@SuppressWarnings("serial")
public class CheckableTreeRenderer extends JCheckBox implements TreeCellRenderer {

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
			boolean leaf, int row, boolean hasFocus) {

		if (value == null) {
			this.setText("null");
			this.setSelected(false);
			return this;
		}

		CheckableTreeNode node = (CheckableTreeNode) value;
		this.setText(node.getText() + " : " + node.getLevel());
		this.setSelected(node.isSelected());

		return this;
	}
}
