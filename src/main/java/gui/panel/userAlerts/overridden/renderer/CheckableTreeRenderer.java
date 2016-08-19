package gui.panel.userAlerts.overridden.renderer;

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JTree;
import javax.swing.tree.TreeCellRenderer;

import gui.panel.userAlerts.parent.Checkable;

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

		if (value instanceof Checkable) {
			Checkable node = (Checkable) value;
			this.setText(node.getDisplayText());
			this.setSelected(node.isSelected());
		} else {
			this.setText(value.toString());
			this.setSelected(false);
		}

		return this;
	}
}
