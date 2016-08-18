package gui.panel.userAlerts.overridden.renderer;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import gui.panel.userAlerts.constants.AlertsFrameStyles;

public class TableHeaderRenderer implements TableCellRenderer {

	public TableHeaderRenderer(JTableHeader header) {
		header.setName(AlertsFrameStyles.TABLE_HEADER_RENDERER);
		renderer = (DefaultTableCellRenderer) header.getDefaultRenderer();
		renderer.setHorizontalAlignment(JLabel.CENTER);
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int col) {
		return renderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
	}

	private DefaultTableCellRenderer renderer;
}
