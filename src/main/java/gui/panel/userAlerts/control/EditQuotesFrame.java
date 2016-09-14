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
import gui.panel.userAlerts.data.ClientQuotesAlert;
import gui.panel.userAlerts.overridden.model.AlertStatusComboModel;
import gui.panel.userAlerts.overridden.model.QuotesDirectionExpressionComboModel;
import gui.panel.userAlerts.overridden.model.QuotesDirectionNameComboModel;
import gui.panel.userAlerts.parent.PrimaryFrame;
import gui.panel.userAlerts.util.ExtendColorChooser;
import gui.panel.userAlerts.util.ExtendOptionPane;
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

		primaryFrame.disable();
	}

	@Override
	protected void afterRenderInit() {
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

		lifetimeTextField.setText(alert.getLifetimeString());
		keepHistoryCheckBox.setSelected(alert.isKeepHistory());

		QuotesDirectionNameComboModel.setValue(directionNameComboBox, alert.getDirectionName());
		QuotesDirectionExpressionComboModel.setValue(directionExpressionComboBox, alert.getDirectionExpression());
		directionValueTextField.setText(alert.getDirectionValue());

		lineColor = (alert.getLineColor() == null) ? DEFAULT_LINE_COLOR : alert.getLineColor();
		lineColorTextField.setBackground(lineColor);

		emailCheckBox.setSelected(alert.isEmailOn());
		phoneCheckBox.setSelected(alert.isPhoneSmsOn());
		melodyCheckBox.setSelected(alert.isMelodyOn());
		notifyWindowCheckBox.setSelected(alert.isPopupWindowOn());

		AlertStatusComboModel.setValue(statusComboBox, alert.isStatusOn());
	}

	private void addUniqueComboItems(ClientQuotesAlert alertItem, boolean isEmptyChecking) {
		SwingHelper.addComboItem(instrumentComboBox, alertItem.getInstrument(), isEmptyChecking);
		SwingHelper.addComboItem(marketPlaceComboBox, alertItem.getMarketPlace(), isEmptyChecking);
	}

	@Override
	protected void fillAlertFromComponents() {
		alert.setName(SwingHelper.getComboText(alertNameComboBox));
		alert.setLifetime(lifetimeTextField.getText());
		alert.setKeepHistory(keepHistoryCheckBox.isSelected());

		alert.setInstrument(SwingHelper.getComboText(instrumentComboBox));
		alert.setMarketPlace(SwingHelper.getComboText(marketPlaceComboBox));
		alert.setDirectionName(QuotesDirectionNameComboModel.getDirectionValue(directionNameComboBox));
		alert.setDirectionExpression(QuotesDirectionExpressionComboModel.getDirectionValue(directionExpressionComboBox));
		alert.setDirectionValue(directionValueTextField.getText());

		alert.setEmailOn(emailCheckBox.isSelected());
		alert.setEmail(SwingHelper.getComboText(emailComboBox));

		alert.setPhoneSmsOn(phoneCheckBox.isSelected());
		alert.setPhoneSms(SwingHelper.getComboText(phoneComboBox));

		alert.setMelodyOn(melodyCheckBox.isSelected());
		alert.setMelody(SwingHelper.getComboText(melodyComboBox));

		alert.setLineColor(lineColor);
		alert.setPopupWindowOn(notifyWindowCheckBox.isSelected());
	}

	public Action SHOW_CHART = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			chartJPanel.setVisible(!chartJPanel.isVisible());
			pack();
		}
	};

	public Action APPLY = new AbstractAction() {
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

			try {
				Integer.valueOf(lifetimeTextField.getText());
			} catch (NumberFormatException e) {
				errorText = "Некорректное значение поля \"Количество срабатываний\" (должно быть целым числом).";
				return false;
			}

			return true;
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

	private ClientQuotesAlert alert;

	private JComboBox instrumentComboBox;
	private JComboBox marketPlaceComboBox;

	private JComboBox directionNameComboBox;
	private JComboBox directionExpressionComboBox;
	private JTextField directionValueTextField;

	private JPanel chartJPanel;
	private JTextField lineColorTextField;

	private JComboBox statusComboBox;
	private String errorText;
	private Color lineColor;

	private static final Color DEFAULT_LINE_COLOR = Color.YELLOW;
}
