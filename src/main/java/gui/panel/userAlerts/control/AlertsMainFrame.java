package gui.panel.userAlerts.control;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.JTableHeader;

import gui.panel.userAlerts.data.Alert;
import gui.panel.userAlerts.data.NewsAlert;
import gui.panel.userAlerts.data.NewsAlert.Expression;
import gui.panel.userAlerts.data.NewsAlert.FilterExclude;
import gui.panel.userAlerts.data.NewsAlert.FilterKey;
import gui.panel.userAlerts.data.QuotesAlert.DirectionExpression;
import gui.panel.userAlerts.data.QuotesAlert.DirectionName;
import gui.panel.userAlerts.data.QuotesAlert;
import gui.panel.userAlerts.overridden.model.AlertsNewsTableModel;
import gui.panel.userAlerts.overridden.model.AlertsQuotesTableModel;
import gui.panel.userAlerts.overridden.renderer.TableHeaderRenderer;
import gui.panel.userAlerts.overridden.renderer.TableMainRenderer;
import gui.panel.userAlerts.data.Stock;
import gui.panel.userAlerts.parent.PrimaryFrame;
import gui.panel.userAlerts.parent.SwixFrame;
import gui.panel.userAlerts.remote.NewsTreeDownloader;

@SuppressWarnings({ "serial", "unused" })
public class AlertsMainFrame extends SwixFrame implements PrimaryFrame {

	public AlertsMainFrame() {
		instance = this;
		stock = new Stock();

		frame.setTitle("Алерты");
		renderPrimary("userAlerts/AlertsMainFrame");
	}

	@Override
	protected void afterRenderInit() {
		initNewsTable();
		initQuotesTable();

		/**
		 * News
		 */
		createAlert(new NewsAlert("Alert_1", "BusinessNews:*:0:*,Comments:*:0:*,DJ_ForexStock:*:0:*", false, "key1",
				"key2", Expression.OR, FilterKey.BY_RELEVANCE, "exclude1", "exclude2", Expression.OR,
				FilterExclude.EVERYWERE, true, "1@mail.ru", true, "111111", true, "mp3", true, Color.RED, true));

		createAlert(new NewsAlert("Alert_2", "BusinessNews:4;19:0:*,Comments:*:0:*", true, null, null, Expression.NOT,
				FilterKey.BY_RELEVANCE, null, null, Expression.NOT, FilterExclude.TITLES_ONLY, false, "2@mail.ru",
				false, "222222", true, "mp3", false, Color.green, false));

		createAlert(new NewsAlert("Alert_3"));

		/**
		 * Quotes
		 */
		createAlert(new QuotesAlert("Alert 4", "instrument_4", "marketplace_4", DirectionName.LAST,
				DirectionExpression.LESS_EQUALS, "400.0", true, "quote4@mail.ru", true, "+7quote4", true, "quote4.mp3",
				true));

		createAlert(new QuotesAlert("Alert 5", "instrument_5", "marketplace_5", DirectionName.BID,
				DirectionExpression.LESS, "500.0", true, "quote5@mail.ru", true, "+7quote5", true, "quote5.mp3", true));

		createAlert(new QuotesAlert("Alert 6"));
	}

	private void initNewsTable() {
		newsModel = new AlertsNewsTableModel();
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
				NewsAlert alert = newsModel.getAlertByRowNumber(selectedNewsRowNumber);
				new EditNewsFrame(instance, alert).show();
			}
		}
	};

	public Action EDIT_QUOTES_ALERT = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			if (e != null) {
				QuotesAlert alert = quotesModel.getAlertByRowNumber(selectedQuotesRowNumber);
				new EditQuotesFrame(instance, alert).show();
			}
		}
	};
	public Action REMOVE_NEWS_ALERT = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			if (e != null) {
				NewsAlert alert = newsModel.getAlertByRowNumber(selectedNewsRowNumber);
				removeNewsAlert(alert);
				setSelectedNewsRowNumber(-1);
			}
		}
	};

	public Action REMOVE_QUOTES_ALERT = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			if (e != null) {
				QuotesAlert alert = quotesModel.getAlertByRowNumber(selectedQuotesRowNumber);
				removeQuotesAlert(alert);
				setSelectedQuotesRowNumber(-1);
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
	public void createAlert(Alert alert) {
		stock.addAlert(alert);

		if (alert instanceof NewsAlert) {
			newsModel.update(stock.getAllNewsAlerts());
		} else if (alert instanceof QuotesAlert) {
			quotesModel.update(stock.getAllQuotesAlerts());
		}
	}

	@Override
	public void updateAlert(Alert alert) {
		stock.removeAlertById(alert.getId());
		stock.addAlert(alert);

		if (alert instanceof NewsAlert) {
			newsModel.update(stock.getAllNewsAlerts());
		} else if (alert instanceof QuotesAlert) {
			quotesModel.update(stock.getAllQuotesAlerts());
		}
	}

	private void removeNewsAlert(NewsAlert alert) {
		if (alert != null) {
			stock.removeAlertById(alert.getId());
			newsModel.update(stock.getAllNewsAlerts());
		}
	}

	private void removeQuotesAlert(QuotesAlert alert) {
		if (alert != null) {
			stock.removeAlertById(alert.getId());
			quotesModel.update(stock.getAllQuotesAlerts());
		}
	}

	@Override
	public Stock getStock() {
		return stock;
	}

	private Stock stock;
	private AlertsMainFrame instance;

	private JTable newsTable;
	private JTableHeader newsHeader;
	private AlertsNewsTableModel newsModel;

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
