package gui.panel.userAlerts.control;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.JTableHeader;

import gui.panel.userAlerts.data.ClientAlert;
import gui.panel.userAlerts.data.ClientNewsAlert;
import gui.panel.userAlerts.data.ClientNewsAlert.Expression;
import gui.panel.userAlerts.data.ClientNewsAlert.RelevanceFilterType;
import gui.panel.userAlerts.data.ClientQuotesAlert.DirectionExpression;
import gui.panel.userAlerts.data.ClientQuotesAlert.DirectionName;
import gui.panel.userAlerts.data.remote.RemoteBasicAPI;
import gui.panel.userAlerts.data.remote.Stock;
import gui.panel.userAlerts.data.ClientQuotesAlert;
import gui.panel.userAlerts.overridden.model.AlertsNewsTableModel;
import gui.panel.userAlerts.overridden.model.AlertsQuotesTableModel;
import gui.panel.userAlerts.overridden.renderer.TableHeaderRenderer;
import gui.panel.userAlerts.overridden.renderer.TableMainRenderer;
import gui.panel.userAlerts.parent.PrimaryFrame;
import gui.panel.userAlerts.parent.SwixFrame;
import gui.panel.userAlerts.util.IOHelper;
import p.alerts.client_api.NewsAlert.SEARCH_NEWS_TYPE;

@SuppressWarnings({ "serial", "unused" })
public class AlertsCommonFrame extends SwixFrame implements PrimaryFrame {

	public AlertsCommonFrame() {
		instance = this;
		stock = new Stock(this);

		frame.setTitle("Алерты");
		renderPrimary("userAlerts/AlertsCommonFrame");

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				instance.stock.logout();
				IOHelper.sleep(2000);
			}
		});
	}

	@Override
	public void updateNewsAlertsTableFromStock() {
		newsModel.update();
		newsAlertsLoadingPanel.setVisible(false);
		newsAlertsPanel.setVisible(true);
	}

	@Override
	protected void afterRenderInit() {
		initNewsTable();
		initQuotesTable();

		/**
		 * Quotes
		 */
		createQuotesAlert(new ClientQuotesAlert("Alert 4", ClientAlert.ETERNITY_LIFETIME, true, "instrument_4", "marketplace_4", DirectionName.LAST,
				DirectionExpression.LESS_EQUALS, "400.0", true, "quote4@mail.ru", true, "+7quote4", true, "quote4.mp3", true));

		createQuotesAlert(new ClientQuotesAlert("Alert 5", ClientAlert.ETERNITY_LIFETIME, true, "instrument_5", "marketplace_5", DirectionName.BID,
				DirectionExpression.LESS, "500.0", true, "quote5@mail.ru", true, "+7quote5", true, "quote5.mp3", true));

		createQuotesAlert(new ClientQuotesAlert("Alert 6"));
	}

	private void initNewsTable() {
		newsModel = new AlertsNewsTableModel(stock);
		newsHeader = newsTable.getTableHeader();

		newsHeader.setDefaultRenderer(new TableHeaderRenderer(newsHeader));

		newsTable.setDefaultRenderer(Object.class, new TableMainRenderer());
		newsTable.setModel(newsModel);

		newsTable.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				if (SwingUtilities.isLeftMouseButton(e)) {
					setSelectedNewsRowNumber(newsTable.rowAtPoint(e.getPoint()));
				}
			}
		});
	}

	private void initQuotesTable() {
		quotesModel = new AlertsQuotesTableModel();
		quotesHeader = quotesTable.getTableHeader();

		quotesHeader.setDefaultRenderer(new TableHeaderRenderer(quotesHeader));

		quotesTable.setDefaultRenderer(Object.class, new TableMainRenderer());
		quotesTable.setModel(quotesModel);

		quotesTable.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				if (SwingUtilities.isLeftMouseButton(e)) {
					setSelectedQuotesRowNumber(quotesTable.rowAtPoint(e.getPoint()));
				}
			}
		});

	}

	public Action CREATE_NEWS_ALERT = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			if (e != null) {
				new EditNewsFrame(instance).show();
			}
		}
	};

	public Action CREATE_QUOTES_ALERT = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			if (e != null) {
				new EditQuotesFrame(instance).show();
			}
		}
	};

	public Action EDIT_NEWS_ALERT = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			if (e != null) {
				ClientNewsAlert alert = newsModel.getAlertByRowNumber(selectedNewsRowNumber);
				new EditNewsFrame(instance, alert).show();
			}
		}
	};

	public Action EDIT_QUOTES_ALERT = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			if (e != null) {
				ClientQuotesAlert alert = quotesModel.getAlertByRowNumber(selectedQuotesRowNumber);
				new EditQuotesFrame(instance, alert).show();
			}
		}
	};
	public Action REMOVE_NEWS_ALERT = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			if (e != null) {
				ClientNewsAlert alert = newsModel.getAlertByRowNumber(selectedNewsRowNumber);
				removeNewsAlert(alert);
				setSelectedNewsRowNumber(-1);
			}
		}
	};

	public Action REMOVE_QUOTES_ALERT = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			if (e != null) {
				ClientQuotesAlert alert = quotesModel.getAlertByRowNumber(selectedQuotesRowNumber);
				removeQuotesAlert(alert);
				setSelectedQuotesRowNumber(-1);
			}
		}
	};

	public Action SHOW_HISTORY = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			if (e != null) {
				new AlertsHistoryFrame(stock).show();
			}
		}
	};

	private void setSelectedNewsRowNumber(int row) {
		this.selectedNewsRowNumber = row;
		boolean enabled = row != -1;
		removeNewsAlertBtn.setEnabled(enabled);
		editNewsAlertBtn.setEnabled(enabled);
	}

	private void setSelectedQuotesRowNumber(int row) {
		this.selectedQuotesRowNumber = row;
		boolean enabled = row != -1;
		removeQuotesAlertBtn.setEnabled(enabled);
		editQuotesAlertBtn.setEnabled(enabled);
	}

	@Override
	public void createNewsAlert(ClientNewsAlert alert) {
		stock.createNewsAlert(alert);
	}

	@Override
	public void createQuotesAlert(ClientQuotesAlert alert) {
		stock.createQuotesAlert(alert);
		quotesModel.update(stock.getAllQuotesAlerts());
	}

	@Override
	public void updateNewsAlert(ClientNewsAlert alert) {
		stock.updateNewsAlert(alert);
	}

	@Override
	public void updateQuotesAlert(ClientQuotesAlert alert) {
		stock.removeQuotesAlert(alert.getId());
		stock.createQuotesAlert(alert);
		quotesModel.update(stock.getAllQuotesAlerts());
	}

	private void removeNewsAlert(ClientNewsAlert alert) {
		if (alert != null) {
			stock.removeNewsAlert(alert);
			newsModel.update();
		}
	}

	private void removeQuotesAlert(ClientQuotesAlert alert) {
		if (alert != null) {
			stock.removeQuotesAlert(alert.getId());
			quotesModel.update(stock.getAllQuotesAlerts());
		}
	}

	@Override
	public Stock getStock() {
		return stock;
	}

	private Stock stock;
	private AlertsCommonFrame instance;

	private JTable newsTable;
	private JTableHeader newsHeader;
	private AlertsNewsTableModel newsModel;
	private JPanel newsAlertsPanel;
	private JPanel newsAlertsLoadingPanel;

	private JTable quotesTable;
	private JTableHeader quotesHeader;
	private AlertsQuotesTableModel quotesModel;

	private int selectedNewsRowNumber = -1;
	private int selectedQuotesRowNumber = -1;

	private JButton createNewsAlertBtn;
	private JButton editNewsAlertBtn;
	private JButton removeNewsAlertBtn;

	private JButton createQuotesAlertBtn;
	private JButton editQuotesAlertBtn;
	private JButton removeQuotesAlertBtn;
}
