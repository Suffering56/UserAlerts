package gui.panel.userAlerts.overridden.renderer;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import gui.panel.userAlerts.constants.AlertsStylesConstants;

@SuppressWarnings("serial")
public class TableMainRenderer extends JLabel implements TableCellRenderer {

	public TableMainRenderer() {
		this.setName(AlertsStylesConstants.TABLE_MAIN_RENDERER);
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int col) {
		
		if (isSelected) {
			this.setName(AlertsStylesConstants.TABLE_SELECTED_RENDERER);
		} else {
			this.setName(AlertsStylesConstants.TABLE_MAIN_RENDERER);
		}

		if (value == null) {
			this.setText("null");
			return this;
		}

		this.setText(value.toString());
		return this;
	}
}
