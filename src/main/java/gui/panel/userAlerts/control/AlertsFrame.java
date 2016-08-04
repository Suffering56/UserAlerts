package gui.panel.userAlerts.control;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.JTableHeader;

import gui.panel.userAlerts.control.tableRenderers.HeaderRenderer;
import gui.panel.userAlerts.data.AlertEntity;
import gui.panel.userAlerts.data.AlertsNewsTableModel;
import gui.panel.userAlerts.parent.SwixFrame;

public class AlertsFrame extends SwixFrame {

	public AlertsFrame() {
		frame.setTitle("Алерты");
		renderPrimary("userAlerts/AlertsFrame.xml");
	}

	@Override
	protected void beforeRenderInit() {
		// do nothing
	}

	@Override
	protected void afterRenderInit() {
		initTable();
	}

	List<AlertEntity> rows = new ArrayList<AlertEntity>();
	{
		AlertEntity entity = new AlertEntity();
		rows.add(entity);
		rows.add(entity);
		rows.add(entity);
	}

	private void initTable() {
		newsModel = new AlertsNewsTableModel();
		newsHeader = newsTable.getTableHeader();

		newsHeader.setDefaultRenderer(new HeaderRenderer(newsHeader));

		newsTable.setModel(newsModel);
		newsModel.update(rows);
	}

	private JTable newsTable;
	private JTableHeader newsHeader;
	private AlertsNewsTableModel newsModel;
}
