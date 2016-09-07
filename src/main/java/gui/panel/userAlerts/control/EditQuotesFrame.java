package gui.panel.userAlerts.control;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import gui.panel.userAlerts.data.ClientAlert;
import gui.panel.userAlerts.data.ClientQuotesAlert;
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

	public EditQuotesFrame(PrimaryFrame primaryFrame, ClientQuotesAlert alert) {
		super(primaryFrame);

		if (alert == null) {
			this.alert = new ClientQuotesAlert();
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

		for (ClientAlert commonAlert : stock.getAllAlerts()) {
			if (alert != commonAlert) {
				addCommonComboItems(commonAlert, true);
				if (commonAlert instanceof ClientQuotesAlert) {
					ClientQuotesAlert newsAlert = (ClientQuotesAlert) commonAlert;
					addUniqueComboItems(newsAlert, true);
				}
			}
		}
	}

	private void addUniqueComboItems(ClientQuotesAlert alertItem, boolean isEmptyChecking) {
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
		notifyWindowCheckBox.setSelected(alert.isPopupWindowOn());
	}
	
	@Override
	protected void fillAlertFromComponents() {

	}

	private ClientQuotesAlert alert;

	private JComboBox instrumentComboBox;
	private JComboBox marketPlaceComboBox;

	private JComboBox directionNameComboBox;
	private JComboBox directionExpressionComboBox;
	private JTextField directionValueTextField;
	
	private JPanel chartJPanel;
}
