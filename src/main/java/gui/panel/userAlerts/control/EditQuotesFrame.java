package gui.panel.userAlerts.control;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import gui.panel.userAlerts.data.Alert;
import gui.panel.userAlerts.data.QuotesAlert;
import gui.panel.userAlerts.overridden.model.QuotesDirectionExpressionComboModel;
import gui.panel.userAlerts.overridden.model.QuotesDirectionNameComboModel;
import gui.panel.userAlerts.parent.AbstractEditFrame;
import gui.panel.userAlerts.parent.PrimaryFrame;
import gui.panel.userAlerts.util.SwingHelper;

@SuppressWarnings({ "serial" })
public class EditQuotesFrame extends AbstractEditFrame {

	public EditQuotesFrame(PrimaryFrame primaryFrame) {
		this(primaryFrame, null);
	}

	public EditQuotesFrame(PrimaryFrame primaryFrame, QuotesAlert alert) {
		super(primaryFrame);

		if (alert == null) {
			this.alert = new QuotesAlert();
			TYPE = Type.CREATE;
		} else {
			this.alert = alert;
			TYPE = Type.EDIT;
		}

		frame.setTitle("Настройка алерта для котировок");
		render("userAlerts/EditQuotesFrame");
	}

	@Override
	protected void afterRenderInit() {
		initComboBoxModels();
		fillComponentsFromAlert();
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

	@Override
	protected void fillComponentsFromAlert() {
		QuotesDirectionNameComboModel.setValue(directionNameComboBox, alert.getDirectionName());
		QuotesDirectionExpressionComboModel.setValue(directionExpressionComboBox, alert.getDirectionExpression());
		directionValueTextField.setText(alert.getDirectionValue());

		emailCheckBox.setSelected(alert.isEmailOn());
		phoneCheckBox.setSelected(alert.isPhoneSmsOn());
		melodyCheckBox.setSelected(alert.isMelodyOn());
		notifyWindowCheckBox.setSelected(alert.isNotifyWindowOn());
	}
	
	@Override
	protected void fillAlertFromComponents() {

	}

	private QuotesAlert alert;

	private JComboBox instrumentComboBox;
	private JComboBox marketPlaceComboBox;

	private JComboBox directionNameComboBox;
	private JComboBox directionExpressionComboBox;
	private JTextField directionValueTextField;
	
	private JPanel chartJPanel;
}
