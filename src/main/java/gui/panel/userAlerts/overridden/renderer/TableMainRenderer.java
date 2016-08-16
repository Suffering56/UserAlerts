package gui.panel.userAlerts.overridden.renderer;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import gui.panel.userAlerts.styles.AlertsFrameStyles;

@SuppressWarnings("serial")
public class TableMainRenderer extends JLabel implements TableCellRenderer {

	public TableMainRenderer() {
		this.setName(AlertsFrameStyles.TABLE_MAIN_RENDERER);
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int col) {

		if (value == null) {
			this.setText("null");
			return this;
		}

		this.setText(value.toString());
		return this;
	}
}
