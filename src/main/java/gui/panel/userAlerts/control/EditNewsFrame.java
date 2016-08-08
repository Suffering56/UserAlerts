package gui.panel.userAlerts.control;

import javax.swing.JComboBox;
import javax.swing.text.JTextComponent;

import gui.panel.userAlerts.parent.SwixFrame;
import gui.panel.userAlerts.util.ComboBoxUtils;
import gui.panel.userAlerts.util.ComboBoxUtils.Handler;

public class EditNewsFrame extends SwixFrame {

	public EditNewsFrame() {
		frame.setTitle("Настройка алерта для новостей");
		render("userAlerts/EditNewsFrame");
	}

	@Override
	protected void beforeRenderInit() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void afterRenderInit() {
		initComboBoxListeners();
	}

	private void initComboBoxListeners() {
        ComboBoxUtils.addTextChangeListener(alertNameComboBox, new Handler() {
			@Override
			public void handle(JTextComponent textComponent) {
				System.out.println("docUpdateText: " + textComponent.getText());
			}
		});
	}
	

	private JComboBox alertNameComboBox;
}
