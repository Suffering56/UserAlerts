package gui.panel.userAlerts.parent;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.swixml.SwingEngine;

import gui.panel.userAlerts.App;

public abstract class SwixFrame {

	public SwixFrame() {
		swix = new SwingEngine(this);
		frame = new JFrame();
	}

	abstract protected void beforeRenderInit();

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

		beforeRenderInit();

		try {
			rootPanel = (JPanel) swix.render(xmlPath + ".xml");
			frame.setContentPane(rootPanel);
			frame.setResizable(true);

		} catch (Exception e) {
			App.appLogger.error("Frame creating error", e);
		}

		afterRenderInit();

		if (pack) {
			pack();
		}
		frame.setLocationRelativeTo(null);
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
