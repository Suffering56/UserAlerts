package gui.panel.userAlerts.control;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JTextField;

import gui.panel.userAlerts.data.remote.Stock;
import gui.panel.userAlerts.parent.SwixFrame;
import gui.panel.userAlerts.util.ExtendOptionPane;

@SuppressWarnings("serial")
public class RegistrationConfirmFrame extends SwixFrame {

	public RegistrationConfirmFrame(Stock stock, String userName) {
		this.stock = stock;
		this.userName = userName;
		renderPrimary("userAlerts/RegistrationConfirmFrame");

		frame.setTitle("Подтверждение регистрации");
	}

	@Override
	protected void afterRenderInit() {
		// do nothing...
	}

	public Action APPLY = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			if (inputValidation()) {
				stock.confirmRegistration(userName, emailCodeField.getText(), smsCodeField.getText());
				dispose();
				new LoginFrame(stock).show();
			}
		}

		private boolean inputValidation() {
			boolean result = true;
			String errorMsg = "Пожалуйста заполните все обязательные поля";

			if (smsCodeField.getText().isEmpty())
				result = false;
			if (emailCodeField.getText().isEmpty())
				result = false;

			if (!result)
				new ExtendOptionPane().showBasicLookAndFeelMessageError(errorMsg, "Validation error!");

			return result;
		}
	};

	public Action CANCEL = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			dispose();
			System.exit(0);
		}
	};

	private final Stock stock;
	private final String userName;

	private JTextField smsCodeField;
	private JTextField emailCodeField;
}
