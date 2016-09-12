package gui.panel.userAlerts.control;

import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.table.JTableHeader;

import gui.panel.userAlerts.data.HistoryEntity;
import gui.panel.userAlerts.data.remote.Stock;
import gui.panel.userAlerts.overridden.model.AlertsHistoryTableModel;
import gui.panel.userAlerts.overridden.renderer.TableHeaderRenderer;
import gui.panel.userAlerts.overridden.renderer.TableMainRenderer;
import gui.panel.userAlerts.overridden.renderer.TableTextAreaRenderer;
import gui.panel.userAlerts.parent.HistoryFrame;
import gui.panel.userAlerts.parent.SwixFrame;
import gui.panel.userAlerts.util.IOHelper;

@SuppressWarnings("serial")
public class AlertsHistoryFrame extends SwixFrame implements HistoryFrame {

	public AlertsHistoryFrame(Stock stock) {
		this.stock = stock;
		this.stock.setHistoryFrame(this);

		frame.setTitle("История срабатываний");
		render("userAlerts/AlertsHistoryFrame");
		// renderPrimary("userAlerts/AlertsHistoryFrame");
		// initLogoutStarter();
	}

	@Override
	public void updateHistoryTableFromStock() {
		historyModel.update();
		historyLoadingPanel.setVisible(false);
		historyScrollPane.setVisible(true);
	}

	@Override
	protected void afterRenderInit() {
		setSelectedRowNumber(-1);
		initHistoryTable();
	}

	private void initHistoryTable() {
		historyModel = new AlertsHistoryTableModel(stock);
		historyHeader = historyTable.getTableHeader();

		historyHeader.setDefaultRenderer(new TableHeaderRenderer(historyHeader));

		historyTable.setDefaultRenderer(Object.class, new TableMainRenderer());
		historyTable.setDefaultRenderer(JTextArea.class, new TableTextAreaRenderer());
		
		historyTable.setModel(historyModel);

		updateColumnsWidth();

		historyTable.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				if (SwingUtilities.isLeftMouseButton(e)) {
					setSelectedRowNumber(historyTable.rowAtPoint(e.getPoint()));
				}
			}
		});

		historyTable.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				updateColumnsWidth();
			}
		});
	}

	private void updateColumnsWidth() {
		historyTable.getColumnModel().getColumn(AlertsHistoryTableModel.NAME).setWidth(frame.getWidth() * 10 / 100);
		historyTable.getColumnModel().getColumn(AlertsHistoryTableModel.TRIGGER_TIME).setWidth(frame.getWidth() * 20 / 100);
		historyTable.getColumnModel().getColumn(AlertsHistoryTableModel.THEME).setWidth(frame.getWidth() * 70 / 100);

		historyTable.getColumnModel().getColumn(AlertsHistoryTableModel.NAME).setMinWidth(frame.getWidth() * 10 / 100);
		historyTable.getColumnModel().getColumn(AlertsHistoryTableModel.TRIGGER_TIME).setMinWidth(frame.getWidth() * 20 / 100);
		historyTable.getColumnModel().getColumn(AlertsHistoryTableModel.THEME).setMinWidth(frame.getWidth() * 70 / 100);
	}

	private void setSelectedRowNumber(int rowNumber) {
		this.selectedRowNumber = rowNumber;
		boolean enabled = rowNumber != -1;
		removeSelectedBtn.setEnabled(enabled);
	}

	@SuppressWarnings("unused")
	private void initLogoutStarter() {
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				stock.logout();
				IOHelper.sleep(2000);
			}
		});
	}

	public Action REMOVE_SELECTED = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			if (e != null) {
				HistoryEntity entity = historyModel.getHistoryEntityByRowNumber(selectedRowNumber);
				if (entity != null) {
					stock.removeSingleHistory(entity);
				}
				setSelectedRowNumber(-1);
			}
		}
	};

	public Action REMOVE_ALL = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			if (e != null) {
				stock.removeAllHistory();
			}
		}
	};

	private final Stock stock;
	private int selectedRowNumber = -1;

	private JTable historyTable;
	private JTableHeader historyHeader;
	private AlertsHistoryTableModel historyModel;
	private JPanel historyLoadingPanel;
	private JScrollPane historyScrollPane;
	private JButton removeSelectedBtn;
}
