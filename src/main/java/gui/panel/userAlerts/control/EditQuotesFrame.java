package gui.panel.userAlerts.control;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import gui.panel.userAlerts.data.ClientAlert;
import gui.panel.userAlerts.data.ClientNewsAlert;
import gui.panel.userAlerts.data.ClientQuotesAlert;
import gui.panel.userAlerts.overridden.model.AlertStatusComboModel;
import gui.panel.userAlerts.overridden.model.QuotesCompareTypeComboModel;
import gui.panel.userAlerts.overridden.model.QuotesFieldNameComboModel;
import gui.panel.userAlerts.parent.CommonFrame;
import gui.panel.userAlerts.util.ExtendColorChooser;
import gui.panel.userAlerts.util.ExtendOptionPane;
import gui.panel.userAlerts.util.SwingHelper;

@SuppressWarnings({ "serial" })
public class EditQuotesFrame extends AbstractEditFrame {

	public EditQuotesFrame(CommonFrame primaryFrame) {
		this(primaryFrame, null);
	}

	public EditQuotesFrame(CommonFrame primaryFrame, ClientQuotesAlert alert) {
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

		primaryFrame.disable();
	}

	@Override
	protected void afterRenderInit() {
		initCommonListeners();
		initListeners();
		fillComponentsFromAlert();
	}

	private void initListeners() {
		lineColorTextField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				showColorChooser();
			}
		});

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				primaryFrame.enable();
			}
		});
	}

	@Override
	protected void fillComponentsFromAlert() {
		addCommonComboItems(alert, false);
		addQuotesComboItems(alert, false);

		for (ClientQuotesAlert quotesAlert : stock.getAllQuotesAlerts()) {
			if (alert != quotesAlert) {
				addCommonComboItems(quotesAlert, true);
				addQuotesComboItems(quotesAlert, true);
			}
		}

		for (ClientNewsAlert newsAlert : stock.getAllNewsAlerts()) {
			addCommonComboItems(newsAlert, true);
		}

		addEmailPhoneAndMelodyComboItems();

		QuotesFieldNameComboModel.setValue(directionNameComboBox, alert.getField());
		QuotesCompareTypeComboModel.setValue(directionExpressionComboBox, alert.getCompareType());
		directionValueTextField.setText(alert.getValue());

		lineColor = (alert.getLineColor() == null) ? DEFAULT_LINE_COLOR : alert.getLineColor();
		lineColorTextField.setBackground(lineColor);

		emailCheckBox.setSelected(alert.isEmailOn());
		phoneCheckBox.setSelected(alert.isPhoneSmsOn());
		melodyCheckBox.setSelected(alert.isMelodyOn());

		emailCheckBoxListener.actionPerformed(null);
		melodyCheckBoxListener.actionPerformed(null);
		phoneCheckBoxListener.actionPerformed(null);

		notifyWindowCheckBox.setSelected(alert.isPopupWindowOn());

		keepHistoryCheckBox.setSelected(alert.isKeepHistory());
		afterTriggerRemoveCheckBox.setSelected(alert.isAfterTriggerRemove());
		AlertStatusComboModel.setValue(statusComboBox, alert.isStatusOn());
	}

	private void addCommonComboItems(ClientAlert alertItem, boolean isEmptyChecking) {
		SwingHelper.addComboItem(alertNameComboBox, alertItem.getName(), isEmptyChecking);
	}

	private void addQuotesComboItems(ClientQuotesAlert alertItem, boolean isEmptyChecking) {
		SwingHelper.addComboItem(instrumentComboBox, alertItem.getInstrument(), isEmptyChecking);
		SwingHelper.addComboItem(marketPlaceComboBox, alertItem.getMarketPlace(), isEmptyChecking);
	}

	@Override
	protected void fillAlertFromComponents() {
		alert.setName(SwingHelper.getComboText(alertNameComboBox));

		alert.setInstrument(SwingHelper.getComboText(instrumentComboBox));
		alert.setMarketPlace(SwingHelper.getComboText(marketPlaceComboBox));
		alert.setField(QuotesFieldNameComboModel.getDirectionValue(directionNameComboBox));
		alert.setCompareType(QuotesCompareTypeComboModel.getDirectionValue(directionExpressionComboBox));
		alert.setValue(directionValueTextField.getText());

		alert.setEmailOn(emailCheckBox.isSelected());
		alert.setEmail(emailComboBox.getSelectedItem().toString());

		alert.setPhoneSmsOn(phoneCheckBox.isSelected());
		alert.setPhoneSms(phoneComboBox.getSelectedItem().toString());

		alert.setMelodyOn(melodyCheckBox.isSelected());
		alert.setMelody(melodyComboBox.getSelectedItem().toString());

		alert.setLineColor(lineColor);
		alert.setPopupWindowOn(notifyWindowCheckBox.isSelected());

		alert.setKeepHistory(keepHistoryCheckBox.isSelected());
		alert.setAfterTriggerRemove(afterTriggerRemoveCheckBox.isSelected());
		alert.setStatusOn(AlertStatusComboModel.getBooleanValue(statusComboBox));
	}

	public Action SHOW_CHART = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			chartJPanel.setVisible(!chartJPanel.isVisible());
			pack();
		}
	};

	public Action CHOOSE_COLOR = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			showColorChooser();
		}
	};

	private void showColorChooser() {
		ExtendColorChooser chooser = new ExtendColorChooser();
		Color resultColor = chooser.showBasicLookAndFeelDialog(null, "Выберите цвет для строки новости", lineColor);
		if (resultColor != null) {
			lineColor = resultColor;
			lineColorTextField.setBackground(lineColor);
		}
	}

	public Action APPLY = new AbstractAction() {

		private boolean inputValidation() {
			errorText = "Пожалуйста, заполните все обязательные поля.";

			if (SwingHelper.isEmptyComboText(alertNameComboBox))
				return false;
			try {
				Double.valueOf(directionValueTextField.getText());
			} catch (NumberFormatException e) {
				errorText = "Некорректное значение поля \"Показатель\" (сравниваемое значение) (должно быть числом).";
				return false;
			}

			return true;
		}

		/**
		 * APPLY
		 */
		public void actionPerformed(ActionEvent e) {
			if (inputValidation()) {
				fillAlertFromComponents();
				if (TYPE == Type.CREATE) {
					primaryFrame.createQuotesAlert(alert);
				} else {
					primaryFrame.updateQuotesAlert(alert);
				}
				dispose();
				primaryFrame.enable();
			} else {
				new ExtendOptionPane().showBasicLookAndFeelMessageError(errorText, "Validation error!");
			}
		}
	};

	private ClientQuotesAlert alert;

	private JComboBox instrumentComboBox;
	private JComboBox marketPlaceComboBox;

	private JComboBox directionNameComboBox;
	private JComboBox directionExpressionComboBox;
	private JTextField directionValueTextField;

	private JPanel chartJPanel;
	private JTextField lineColorTextField;

	private String errorText;
	private Color lineColor;

	private static final Color DEFAULT_LINE_COLOR = Color.YELLOW;
}
