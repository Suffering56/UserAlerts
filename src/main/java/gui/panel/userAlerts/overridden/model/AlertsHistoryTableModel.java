package gui.panel.userAlerts.overridden.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JTextArea;
import javax.swing.table.AbstractTableModel;

import gui.panel.userAlerts.App;
import gui.panel.userAlerts.data.HistoryEntity;
import gui.panel.userAlerts.data.remote.Stock;

@SuppressWarnings("serial")
public class AlertsHistoryTableModel extends AbstractTableModel {

	public AlertsHistoryTableModel(Stock stock) {
		this.stock = stock;
	}

	public void update() {
		rows = stock.getAllHistory();
		fireTableDataChanged();
	}

	@Override
	public int getRowCount() {
		return (rows == null) ? 0 : rows.size();
	}

	@Override
	public int getColumnCount() {
		return columnHeadingsMap.size();
	}

	public String getColumnName(int columnIndex) {
		return columnHeadingsMap.get(columnIndex);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case THEME:
			return JTextArea.class;
		}
		return Object.class;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	public HistoryEntity getHistoryEntityByRowNumber(int rowNumber) {
		if ((rowNumber < rows.size()) && (rowNumber >= 0)) {
			return rows.get(rowNumber);
		} else {
			App.appLogger.error(getClass().getName() + ": uncorrect rowNumber");
			return null;
		}
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (rowIndex < getRowCount()) {
			switch (columnIndex) {
			case NAME:
				return rows.get(rowIndex).getName();
			case TRIGGER_TIME:
				return rows.get(rowIndex).getTriggerTimeString();
			case THEME:
				return rows.get(rowIndex).getTheme();
			}
		}
		return "";
	}

	private final Stock stock;
	private List<HistoryEntity> rows;

	public static final int NAME = 0;
	public static final int TRIGGER_TIME = 1;
	public static final int THEME = 2;

	public static final Map<Integer, String> columnHeadingsMap = new HashMap<Integer, String>();
	static {
		columnHeadingsMap.put(NAME, "<html><p align='center'>Название</p></html>");
		columnHeadingsMap.put(TRIGGER_TIME, "<html><p align='center'>Время срабатывания</p></html>");
		columnHeadingsMap.put(THEME, "<html><p align='center'>Тема</p></html>");
	}
}
