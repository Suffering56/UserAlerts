package gui.panel.userAlerts.control;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import gui.panel.userAlerts.data.Alert;
import gui.panel.userAlerts.data.NewsAlert;
import gui.panel.userAlerts.data.QuotesAlert;
import gui.panel.userAlerts.data.Stock;
import gui.panel.userAlerts.overridden.model.QuotesDirectionExpressionComboModel;
import gui.panel.userAlerts.overridden.model.QuotesDirectionNameComboModel;
import gui.panel.userAlerts.parent.PrimaryFrame;
import gui.panel.userAlerts.parent.SwixFrame;
import gui.panel.userAlerts.util.SwingHelper;

@SuppressWarnings({ "serial", "unused" })
public class EditQuotesFrame extends SwixFrame {

	public EditQuotesFrame(PrimaryFrame primaryFrame) {
		this(primaryFrame, null);
	}

	public EditQuotesFrame(PrimaryFrame primaryFrame, QuotesAlert alert) {
		this.primaryFrame = primaryFrame;
		stock = primaryFrame.getStock();

		if (alert == null) {
			this.alert = new QuotesAlert();
			TYPE = TYPE_CREATE;
		} else {
			this.alert = alert;
			TYPE = TYPE_EDIT;
		}

		frame.setTitle("Настройка алерта для котировок");
		render("userAlerts/EditQuotesFrame");
	}

	@Override
	protected void beforeRenderInit() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void afterRenderInit() {
		initComboBoxModels();
		extractAlertData();
	}

	private void initComboBoxModels() {
		addCommonComboItems(alert, false);
		addUniqueComboItems(alert, false);

		for (Alert commonAlert : stock.getAlertsList()) {
			if (alert != commonAlert) {
				addCommonComboItems(commonAlert, true);
				if (commonAlert instanceof QuotesAlert) {
					QuotesAlert newsAlert = (QuotesAlert) commonAlert;
					addUniqueComboItems(newsAlert, true);
				}
			}
		}
	}

	private void addCommonComboItems(Alert alertItem, boolean isEmptyChecking) {
		SwingHelper.addComboItem(alertNameComboBox, alertItem.getName(), isEmptyChecking);
		SwingHelper.addComboItem(emailComboBox, alertItem.getEmail(), isEmptyChecking);
		SwingHelper.addComboItem(phoneComboBox, alertItem.getPhoneSms(), isEmptyChecking);
		SwingHelper.addComboItem(melodyComboBox, alertItem.getMelody(), isEmptyChecking);
	}

	private void addUniqueComboItems(QuotesAlert alertItem, boolean isEmptyChecking) {
		SwingHelper.addComboItem(instrumentComboBox, alertItem.getInstrument(), isEmptyChecking);
		SwingHelper.addComboItem(marketPlaceComboBox, alertItem.getMarketPlace(), isEmptyChecking);
	}

	public Action SHOW_CHART = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			if (e != null) {
				chartJPanel.setVisible(!chartJPanel.isVisible());
				pack();
			}
		}
	};

	private void extractAlertData() {
		QuotesDirectionNameComboModel.setValue(directionNameComboBox, alert.getDirectionName());
		QuotesDirectionExpressionComboModel.setValue(directionExpressionComboBox, alert.getDirectionExpression());
		directionValueTextField.setText(alert.getDirectionValue());

		emailCheckBox.setSelected(alert.isEmailOn());
		phoneCheckBox.setSelected(alert.isPhoneSmsOn());
		melodyCheckBox.setSelected(alert.isMelodyOn());
		notifyWindowCheckBox.setSelected(alert.isNotifyWindowOn());
	}

	private Stock stock;
	private QuotesAlert alert;
	protected PrimaryFrame primaryFrame;

	private JPanel chartJPanel;

	private JComboBox alertNameComboBox;
	private JComboBox instrumentComboBox;
	private JComboBox marketPlaceComboBox;

	private JComboBox directionNameComboBox;
	private JComboBox directionExpressionComboBox;
	private JTextField directionValueTextField;

	private JCheckBox emailCheckBox;
	private JComboBox emailComboBox;

	private JCheckBox phoneCheckBox;
	private JComboBox phoneComboBox;

	private JCheckBox melodyCheckBox;
	private JComboBox melodyComboBox;

	private JCheckBox notifyWindowCheckBox;

	private int TYPE;
	private static final int TYPE_CREATE = 0;
	private static final int TYPE_EDIT = 1;
}
