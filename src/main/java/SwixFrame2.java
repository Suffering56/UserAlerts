

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;

import org.swixml.SwingEngine;

import gui.panel.userAlerts.App;

public abstract class SwixFrame2 {

	public SwixFrame2() {
		this(false);
	}

	public SwixFrame2(boolean isInternal) {
		this.isInternal = isInternal;

		swix = new SwingEngine(this);
		if (isInternal) {
			internalFrame = new JInternalFrame();
		} else {
			frame = new JFrame();
		}
	}

	abstract protected void beforeRenderInit();

	abstract protected void afterRenderInit();

	protected void render(String frameName) {
		render(frameName, true);
	}

	protected void render(String frameName, boolean pack) {
		beforeRenderInit();

		swixRender(frameName);

		afterRenderInit();

		if (pack) {
			pack();
		}
	}

	protected void swixRender(String frameName) {
		try {
			if (!isInternal) {
				Container container = swix.render(frameName);
				frame.setContentPane(container);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setResizable(true);
				frame.setTitle("title");
			} else {
				Container container = swix.render(frameName);
				internalFrame.setContentPane(container);
				internalFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				internalFrame.setResizable(true);
				internalFrame.setTitle("title");
			}

		} catch (Exception e) {
			App.appLogger.error("Frame creating error", e);
		}
	}

	@SuppressWarnings("unused")
	private void initFrame(JInternalFrame frame, String frameName) throws Exception {
		Container container = swix.render(frameName);
		frame.setContentPane(container);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setTitle("title");
	}

	protected void pack() {
		if (!isInternal) {
			frame.pack();
			frame.setMinimumSize(frame.getSize());
			frame.setLocationRelativeTo(null);
		} else {
			internalFrame.pack();
			internalFrame.setMinimumSize(internalFrame.getSize());
			// internalFrame.setLocationRelativeTo(null);
		}

	}

	public void show() {
		if (!isInternal) {
			frame.setVisible(true);
		} else {
			internalFrame.setVisible(true);
		}
	}

	public void hide() {
		if (!isInternal) {
			frame.setVisible(false);
		} else {
			internalFrame.setVisible(false);
		}
	}

	public void enable() {
		if (!isInternal) {
			frame.setEnabled(true);
		} else {
			internalFrame.setEnabled(true);
		}
	}

	public void disable() {
		if (!isInternal) {
			frame.setEnabled(false);
		} else {
			internalFrame.setEnabled(false);
		}
	}

	// main
	protected SwingEngine swix;
	private boolean isInternal;

	protected JInternalFrame internalFrame;
	protected JFrame frame;
}
