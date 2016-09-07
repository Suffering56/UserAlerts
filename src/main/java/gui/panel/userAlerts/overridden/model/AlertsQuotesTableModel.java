package gui.panel.userAlerts.overridden.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import gui.panel.userAlerts.App;
import gui.panel.userAlerts.data.ClientQuotesAlert;
import gui.panel.userAlerts.util.StringHelper;

@SuppressWarnings("serial")
public class AlertsQuotesTableModel extends AbstractTableModel {
	
	public void update(List<ClientQuotesAlert> rows) {
		this.rows = rows;
		fireTableDataChanged();
	}

	@SuppressWarnings("deprecation")
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (rowIndex < getRowCount()) {
			switch (columnIndex) {
			case NAME:
				return rows.get(rowIndex).getName();
			case INSTRUMENT:
				return rows.get(rowIndex).getInstrument();
			case CREATION_DATE:
				return (rows.get(rowIndex).getCreationDate() == null) ? StringHelper.EMPTY : rows.get(rowIndex).getCreationDate().toLocaleString();
			case LAST_EVENT_DATE:
				return (rows.get(rowIndex).getLastEventDate() == null) ? StringHelper.EMPTY : rows.get(rowIndex).getLastEventDate().toLocaleString();
			}
		}
		return "";
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

	public Class<?> getColumnClass(int columnIndex) {
		return super.getColumnClass(columnIndex);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	public ClientQuotesAlert getAlertByRowNumber(int rowNumber) {
		if ((rowNumber < rows.size()) && (rowNumber >= 0)) {
			return rows.get(rowNumber);
		} else {
			App.appLogger.info("uncorrect rowNumber");
			return null;
		}
	}


	private List<ClientQuotesAlert> rows;
	
	public static final int NAME = 0;
	public static final int INSTRUMENT = 1;
	public static final int CREATION_DATE = 2;
	public static final int LAST_EVENT_DATE = 3;

	public static final Map<Integer, String> columnHeadingsMap = new HashMap<Integer, String>();
	static {
		columnHeadingsMap.put(NAME, "<html><p align='center'>Название</p></html>");
		columnHeadingsMap.put(INSTRUMENT, "<html><p align='center'>Инструмент</p></html>");
		columnHeadingsMap.put(CREATION_DATE, "<html><p align='center'>Дата создания</p></html>");
		columnHeadingsMap.put(LAST_EVENT_DATE, "<html><p align='center'>Последнее<br/>событие</p></html>");
	}
}
