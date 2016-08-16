package gui.panel.userAlerts.overridden.renderer;

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JTree;
import javax.swing.tree.TreeCellRenderer;

import gui.panel.userAlerts.overridden.model.CheckableTreeNode;

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

		if (value instanceof CheckableTreeNode) {
			CheckableTreeNode node = (CheckableTreeNode) value;
			this.setText(node.getText());
			this.setSelected(node.isSelected());
		} else {
			this.setText(value.toString());
			this.setSelected(false);
		}

		return this;
	}
}
