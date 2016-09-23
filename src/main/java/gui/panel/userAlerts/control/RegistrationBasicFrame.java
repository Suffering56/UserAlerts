package gui.panel.userAlerts.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import gui.panel.userAlerts.data.remote.Stock;
import gui.panel.userAlerts.parent.SwixFrame;
import gui.panel.userAlerts.util.ExtendOptionPane;

@SuppressWarnings("serial")
public class RegistrationBasicFrame extends SwixFrame {

	public RegistrationBasicFrame(Stock stock) {
		this.stock = stock;
		renderPrimary("userAlerts/RegistrationBasicFrame");

		frame.setTitle("Регистрация");
	}

	@Override
	protected void afterRenderInit() {
		agreeCheckBoxListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				applyBtn.setEnabled(agreeCheckBox.isSelected());
			}
		};

		agreeCheckBox.addActionListener(agreeCheckBoxListener);

		agreeCheckBoxListener.actionPerformed(null);
	}

	@SuppressWarnings("deprecation")
	public Action APPLY = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			if (inputValidation()) {
				stock.registerUser(usernameField.getText(), passField.getText(), phoneField.getText(), emailField.getText());
				dispose();

				new RegistrationConfirmFrame(stock, usernameField.getText()).show();
			}
		}

		private boolean inputValidation() {
			boolean result = true;
			String errorMsg = "Пожалуйста заполните все обязательные поля";

			if (usernameField.getText().isEmpty())
				result = false;
			if (passField.getText().isEmpty())
				result = false;
			if (passConfirmField.getText().isEmpty())
				result = false;
			if (phoneField.getText().isEmpty())
				result = false;
			if (emailField.getText().isEmpty())
				result = false;

			if (!passField.getText().equals(passConfirmField.getText())) {
				errorMsg = "Введенные пароли не совпадают";
				result = false;
			}

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

	private Stock stock;

	private JTextField usernameField;
	private JPasswordField passField;
	private JPasswordField passConfirmField;
	private JTextField phoneField;
	private JTextField emailField;

	private JCheckBox agreeCheckBox;
	private JButton applyBtn;
	private ActionListener agreeCheckBoxListener;
}
