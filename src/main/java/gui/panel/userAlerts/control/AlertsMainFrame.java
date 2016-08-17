package gui.panel.userAlerts.control;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.JTableHeader;

import gui.panel.userAlerts.data.NewsAlert;
import gui.panel.userAlerts.data.NewsAlert.Expression;
import gui.panel.userAlerts.data.NewsAlert.FilterExclude;
import gui.panel.userAlerts.data.NewsAlert.FilterKey;
import gui.panel.userAlerts.overridden.model.AlertsNewsTableModel;
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
	protected void beforeRenderInit() {
		// do nothing
	}

	@Override
	protected void afterRenderInit() {
		initTable();

		createAlert(new NewsAlert(1, "Alert_1", false, "key1", "key2", Expression.OR, FilterKey.BY_RELEVANCE,
				"exclude1", "exclude2", Expression.OR, FilterExclude.EVERYWERE, true, "1@mail.ru", true, "111111", true,
				"mp3", true, "null_color", true));

		createAlert(new NewsAlert(2, "Alert_2", true, "", "", Expression.NOT, FilterKey.BY_RELEVANCE, "", "",
				Expression.NOT, FilterExclude.TITLES_ONLY, false, "2@mail.ru", false, "222222", true, "mp3", false,
				"null_color", false));

		createAlert(new NewsAlert(3, "Alert_3", false, "key111", "", Expression.NOT, FilterKey.EXACT_MATCH, "", "",
				Expression.NOT, FilterExclude.RED_ONLY, false, "", false, "", true, "mp3", false, "null_color", false));

		// for (NewsAlert alert : stock.getNewsAlertsList()) {
		// App.appLogger.info(alert);
		// }
	}

	@Override
	public void createAlert(NewsAlert alert) {
		stock.add(alert);
		newsModel.update(stock.getNewsAlertsList());

		// App.appLogger.info(alert);
	}

	@Override
	public void updateAlert(NewsAlert alert) {
		stock.removeById(alert.getId());
		stock.add(alert);
		newsModel.update(stock.getNewsAlertsList());
	}

	public void removeAlert(NewsAlert alert) {
		if (alert != null) {
			stock.removeById(alert.getId());
			newsModel.update(stock.getNewsAlertsList());
		}
	}

	public Stock getStock() {
		return stock;
	}

	private void initTable() {
		newsModel = new AlertsNewsTableModel();
		newsHeader = newsTable.getTableHeader();

		newsHeader.setDefaultRenderer(new TableHeaderRenderer(newsHeader));

		newsTable.setDefaultRenderer(Object.class, new TableMainRenderer());
		newsTable.setModel(newsModel);

		newsTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (SwingUtilities.isLeftMouseButton(e)) {
					setSelectedRowNumber(newsTable.rowAtPoint(e.getPoint()));
				}
			}
		});
	}

	public Action EDIT_NEWS_ALERT = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			if (e != null) {
				NewsAlert alert = newsModel.getAlertByRowNumber(selectedRowNumber);
				new EditNewsFrame(instance, alert).show();
			}
		}
	};

	public Action REMOVE_NEWS_ALERT = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			if (e != null) {
				NewsAlert alert = newsModel.getAlertByRowNumber(selectedRowNumber);
				removeAlert(alert);
				setSelectedRowNumber(-1);
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

	public Action REMOVE_QUOTES_ALERT = new AbstractAction() {
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

	public void setSelectedRowNumber(int selectedRowNumber) {
		this.selectedRowNumber = selectedRowNumber;
		boolean enabled = selectedRowNumber != -1;
		removeNewsAlertBtn.setEnabled(enabled);
		editNewsAlertBtn.setEnabled(enabled);
	}

	private AlertsMainFrame instance;
	private Stock stock;

	private JTable newsTable;
	private JTableHeader newsHeader;
	private AlertsNewsTableModel newsModel;

	private int selectedRowNumber = -1;

	private JButton editNewsAlertBtn;
	private JButton removeNewsAlertBtn;
	private JButton createNewsAlertBtn;
	private JButton editQuotesAlertBtn;
	private JButton removeQuotesAlertBtn;
	private JButton createQuotesAlertBtn;
}
