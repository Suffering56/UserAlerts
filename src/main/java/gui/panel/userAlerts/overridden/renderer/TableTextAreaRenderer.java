package gui.panel.userAlerts.overridden.renderer;

import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellRenderer;

import gui.panel.userAlerts.constants.AlertsStylesConstants;

@SuppressWarnings("serial")
public class TableTextAreaRenderer extends JTextArea implements TableCellRenderer {

	public TableTextAreaRenderer() {
		this.setLineWrap(true);
		this.setWrapStyleWord(true);
		this.setBorder(BorderFactory.createEmptyBorder(6, 5, 6, 50));
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		String text = (value == null) ? "" : value.toString();
		setText(text);
		setToolTipText(text);
		
		if (isSelected) {
			this.setName(AlertsStylesConstants.TABLE_SELECTED_RENDERER);
		} else {
			this.setName(AlertsStylesConstants.TABLE_MAIN_RENDERER);
		}

		this.setSize(table.getColumnModel().getColumn(column).getWidth(), getPreferredSize().height);
		table.setRowHeight(row, this.getPreferredSize().height);

		return this;
	}

}
