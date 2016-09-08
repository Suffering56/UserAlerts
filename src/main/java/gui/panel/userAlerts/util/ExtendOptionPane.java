package gui.panel.userAlerts.util;

import java.awt.Component;
import java.awt.HeadlessException;

import javax.swing.JOptionPane;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

@SuppressWarnings("serial")
public class ExtendOptionPane extends JOptionPane {

	public void showBasicLookAndFeelMessageError(Object message) throws HeadlessException {
		showBasicLookAndFeelMessageError(message, "Error!");
	}

	public void showBasicLookAndFeelMessageError(Object message, String title) throws HeadlessException {
		showBasicLookAndFeelMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
	}

	public void showBasicLookAndFeelMessageDialog(Component parentComponent, Object message, String title, int messageType) throws HeadlessException {

		LookAndFeel old = UIManager.getLookAndFeel();
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable ex) {
			old = null;
		}

		super.updateUI();

		showMessageDialog(parentComponent, message, title, messageType, null);

		if (old != null) {
			try {
				UIManager.setLookAndFeel(old);
			} catch (UnsupportedLookAndFeelException ignored) {
			}
		}
	}
}
