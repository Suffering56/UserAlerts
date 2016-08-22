package gui.panel.userAlerts.util;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JColorChooser;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

@SuppressWarnings("serial")
public class ExtendColorChooser extends JColorChooser {

	public Color showBasicLookAndFeelDialog(Component component, String title, Color initialColor) {

		LookAndFeel old = UIManager.getLookAndFeel();
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable ex) {
			old = null;
		}

		super.updateUI();

		Color selectedColor = showDialog(component, title, initialColor);

		if (old != null) {
			try {
				UIManager.setLookAndFeel(old);
			} catch (UnsupportedLookAndFeelException ignored) {
			}
		}

		return selectedColor;
	}
}
