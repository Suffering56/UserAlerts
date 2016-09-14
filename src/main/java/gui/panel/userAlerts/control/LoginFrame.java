package gui.panel.userAlerts.control;

import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import gui.panel.userAlerts.data.remote.Stock;
import gui.panel.userAlerts.parent.SwixFrame;
import gui.panel.userAlerts.util.ExtendOptionPane;

@SuppressWarnings("serial")
public class LoginFrame extends SwixFrame {

	public LoginFrame(Stock stock) {
		this.stock = stock;
		renderPrimary("userAlerts/LoginFrame");

		frame.setTitle("Авторизация");
	}

	@Override
	protected void afterRenderInit() {
		initListeners();

		// test
		usernameField.setText("test");
		passField.setText("123");
	}

	private void initListeners() {
		KeyListener enterListener = new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 10) {
					if (usernameField.hasFocus()) {
						usernameField.transferFocus();
					} else {
						APPLY.actionPerformed(null);
					}
				}
			}
		};

		frame.addKeyListener(enterListener);
		usernameField.addKeyListener(enterListener);
		passField.addKeyListener(enterListener);
	}

	@SuppressWarnings("deprecation")
	public Action APPLY = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			if (inputValidation()) {
				stock.login(usernameField.getText(), passField.getText());
				dispose();
			}
		}

		private boolean inputValidation() {
			boolean result = true;
			String errorMsg = "Пожалуйста заполните все обязательные поля";

			if (usernameField.getText().isEmpty())
				result = false;
			if (passField.getText().isEmpty())
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

	public Action REGISTRATION = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			dispose();
			new RegistrationBasicFrame(stock).show();
		}
	};

	private final Stock stock;

	private JTextField usernameField;
	private JPasswordField passField;
}
