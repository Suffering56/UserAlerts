package gui.panel.userAlerts.parent;

import java.awt.Dimension;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.swixml.SwingEngine;

import gui.panel.userAlerts.App;
import gui.panel.userAlerts.Main;
import gui.panel.userAlerts.constants.AlertsResourcesConstants;

public abstract class SwixFrame implements Disablable {

	public SwixFrame() {
		swix = new SwingEngine(this);
		frame = new JFrame();
	}

	abstract protected void afterRenderInit();

	protected void render(String xmlPath) {
		render(xmlPath, true);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}

	protected void renderPrimary(String xmlPath) {
		render(xmlPath, true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void render(String xmlPath, boolean pack) {
		try {
			rootPanel = (JPanel) swix.render(xmlPath + ".xml");
			frame.setContentPane(rootPanel);
			frame.setResizable(true);

			setPrimeIcon();

		} catch (Exception e) {
			App.appLogger.error("Frame creating error", e);
		}

		afterRenderInit();

		if (pack) {
			pack();
		}
		frame.setLocationRelativeTo(null);
	}

	private void setPrimeIcon() {
		try {
			Image iconImage = ImageIO.read(Main.class.getResource(AlertsResourcesConstants.PRIME_ICON_PATH));
			frame.setIconImage(iconImage);
		} catch (IOException e) {
			App.appLogger.error("Prime icon image download error", e);
		}
	}

	protected void pack() {
		frame.setMinimumSize(new Dimension(0, 0));
		frame.pack();
		frame.setMinimumSize(frame.getSize());
	}

	public void show() {
		frame.setVisible(true);
	}

	public void hide() {
		frame.setVisible(false);
	}

	public void enable() {
		frame.setEnabled(true);
		frame.setVisible(true);
	}

	public void disable() {
		frame.setEnabled(false);
	}

	public void dispose() {
		frame.dispose();
	}

	// main
	protected SwingEngine swix;
	protected JFrame frame;
	protected JPanel rootPanel;
}
