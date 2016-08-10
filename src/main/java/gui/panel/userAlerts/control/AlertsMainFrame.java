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
import gui.panel.userAlerts.data.Stock;
import gui.panel.userAlerts.parent.PrimaryFrame;
import gui.panel.userAlerts.parent.SwixFrame;

@SuppressWarnings("serial")
public class AlertsMainFrame extends SwixFrame implements PrimaryFrame {

	public AlertsMainFrame() {
		instance = this;
		stock = new Stock();

		frame.setTitle("Алерты");
		renderPrimary("userAlerts/AlertsMainFrame");
	}

	@Override
	protected void beforeRenderInit() {
		stock = new Stock();
	}

	@Override
	protected void afterRenderInit() {
		initTable();
		
		addAlert(new AlertEntity(AlertEntity.TYPE_NEWS));
		addAlert(new AlertEntity(AlertEntity.TYPE_NEWS));
		addAlert(new AlertEntity(AlertEntity.TYPE_NEWS));
		
		for (AlertEntity a : stock.getAlertsList()) {
			System.out.println(a);
		}
	}

	public void addAlert(AlertEntity alert) {
		stock.add(alert);
		newsModel.update(stock.getAlertsList());
	}

	private void initTable() {
		newsModel = new AlertsNewsTableModel();
		newsHeader = newsTable.getTableHeader();

		newsHeader.setDefaultRenderer(new HeaderRenderer(newsHeader));

		newsTable.setModel(newsModel);
	}

	public Action EDIT_NEWS_ALERT = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			if (e != null) {
				new EditNewsFrame(instance).show();
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
				new EditNewsFrame(instance).show();
			}
		}
	};

	public Action EDIT_QUOTES_ALERT = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			if (e != null) {
				new EditQuotesFrame().show();
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
				new EditQuotesFrame().show();
			}
		}
	};

	private AlertsMainFrame instance;
	private JTable newsTable;
	private JTableHeader newsHeader;
	private AlertsNewsTableModel newsModel;

	private Stock stock;
}
