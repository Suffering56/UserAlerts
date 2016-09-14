package gui.panel.userAlerts.control;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import gui.panel.userAlerts.data.ClientAlert;
import gui.panel.userAlerts.data.remote.Stock;
import gui.panel.userAlerts.overridden.model.NewsExpressionComboModel;
import gui.panel.userAlerts.parent.PrimaryFrame;
import gui.panel.userAlerts.parent.SwixFrame;
import gui.panel.userAlerts.util.SwingHelper;

@SuppressWarnings("serial")
public abstract class AbstractEditFrame extends SwixFrame {

	public AbstractEditFrame(PrimaryFrame primaryFrame) {
		this.primaryFrame = primaryFrame;
		stock = primaryFrame.getStock();
	}

	protected void setSecondComboBoxState(JComboBox expressionBox, JComboBox secondBox) {
		boolean enabled = !expressionBox.getSelectedItem().equals(NewsExpressionComboModel.not);
		secondBox.setEnabled(enabled);
	}

	protected void addCommonComboItems(ClientAlert alertItem, boolean isEmptyChecking) {
		SwingHelper.addComboItem(alertNameComboBox, alertItem.getName(), isEmptyChecking);
		SwingHelper.addComboItem(emailComboBox, alertItem.getEmail(), isEmptyChecking);
		SwingHelper.addComboItem(phoneComboBox, alertItem.getPhoneSms(), isEmptyChecking);
		SwingHelper.addComboItem(melodyComboBox, alertItem.getMelody(), isEmptyChecking);
	}

	abstract protected void fillComponentsFromAlert();

	abstract protected void fillAlertFromComponents();

	public Action CANCEL = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			dispose();
			primaryFrame.enable();
		}
	};

	protected Type TYPE;
	protected final Stock stock;
	protected final PrimaryFrame primaryFrame;

	protected JTextField lifetimeTextField;
	protected JCheckBox keepHistoryCheckBox;

	protected JComboBox alertNameComboBox;
	protected JCheckBox emailCheckBox;
	protected JComboBox emailComboBox;
	protected JCheckBox phoneCheckBox;
	protected JComboBox phoneComboBox;
	protected JCheckBox melodyCheckBox;
	protected JComboBox melodyComboBox;
	protected JCheckBox notifyWindowCheckBox;

	public enum Type {
		CREATE, EDIT
	}
}