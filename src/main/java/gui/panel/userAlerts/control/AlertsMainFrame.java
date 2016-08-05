package gui.panel.userAlerts.control;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;

import gui.panel.userAlerts.control.tableRenderers.HeaderRenderer;
import gui.panel.userAlerts.data.AlertEntity;
import gui.panel.userAlerts.data.AlertsNewsTableModel;
import gui.panel.userAlerts.parent.SwixFrame;

@SuppressWarnings("serial")
public class AlertsMainFrame extends SwixFrame {

	public AlertsMainFrame() {
		frame.setTitle("Алерты");
		renderPrimary("userAlerts/AlertsMainFrame");
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

	public Action EDIT_NEWS_ALERT = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			if (e != null) {
				new EditNewsFrame().show();
			}
		}
	};

	public Action DELETE_NEWS_ALERT = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			if (e != null) {
				// do something
			}
		}
	};

	public Action CREATE_NEWS_ALERT = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			if (e != null) {
				new EditNewsFrame().show();
			}
		}
	};

	public Action EDIT_QUOTES_ALERT = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			if (e != null) {
				// do something
			}
		}
	};

	public Action DELETE_QUOTES_ALERT = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			if (e != null) {
				// do something
			}
		}
	};

	public Action CREATE_QUOTES_ALERT = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			if (e != null) {
				// do something
			}
		}
	};

	private JTable newsTable;
	private JTableHeader newsHeader;
	private AlertsNewsTableModel newsModel;
}
