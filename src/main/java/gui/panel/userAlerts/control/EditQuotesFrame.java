package gui.panel.userAlerts.control;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPanel;

import gui.panel.userAlerts.parent.SwixFrame;

public class EditQuotesFrame extends SwixFrame {

	public EditQuotesFrame() {
		frame.setTitle("Настройка алерта для котировок");
		render("userAlerts/EditQuotesFrame");
	}

	@Override
	protected void beforeRenderInit() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void afterRenderInit() {
	}

	public Action SHOW_CHART = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			if (e != null) {
				chartJPanel.setVisible(!chartJPanel.isVisible());
				pack();
			}
		}
	};

	private JPanel chartJPanel;
}
