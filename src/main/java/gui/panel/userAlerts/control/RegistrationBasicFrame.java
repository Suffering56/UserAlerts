package gui.panel.userAlerts.control;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import gui.panel.userAlerts.data.remote.Stock;
import gui.panel.userAlerts.parent.SwixFrame;

@SuppressWarnings("serial")
public class RegistrationBasicFrame extends SwixFrame {

	public RegistrationBasicFrame(Stock stock) {
		this.stock = stock;

		renderPrimary("userAlerts/RegistrationBasicFrame");
	}

	@Override
	protected void afterRenderInit() {
		// TODO Auto-generated method stub
	}
	
	public Action APPLY = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	};

	public Action CANCEL = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	};

	private Stock stock;

	private JTextField usernameField;
	private JPasswordField passField;
	private JPasswordField passConfirmField;
	private JTextField phoneField;
	private JTextField emailField;
}
