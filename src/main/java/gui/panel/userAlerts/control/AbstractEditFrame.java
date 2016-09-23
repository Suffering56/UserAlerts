package gui.panel.userAlerts.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;

import gui.panel.userAlerts.data.remote.Stock;
import gui.panel.userAlerts.parent.CommonFrame;
import gui.panel.userAlerts.parent.SwixFrame;
import gui.panel.userAlerts.util.SwingHelper;

@SuppressWarnings("serial")
public abstract class AbstractEditFrame extends SwixFrame {

	public AbstractEditFrame(CommonFrame primaryFrame) {
		this.primaryFrame = primaryFrame;
		stock = primaryFrame.getStock();
	}

	protected void initCommonListeners() {
		emailCheckBoxListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				emailComboBox.setEnabled(emailCheckBox.isSelected());
			}
		};

		phoneCheckBoxListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				phoneComboBox.setEnabled(phoneCheckBox.isSelected());
			}
		};

		melodyCheckBoxListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				melodyComboBox.setEnabled(melodyCheckBox.isSelected());
			}
		};

		emailCheckBox.addActionListener(emailCheckBoxListener);
		phoneCheckBox.addActionListener(phoneCheckBoxListener);
		melodyCheckBox.addActionListener(melodyCheckBoxListener);
	}

	protected void addEmailPhoneAndMelodyComboItems() {
		for (String email : stock.getEmailList()) {
			SwingHelper.addComboItem(emailComboBox, email, true);
		}
		for (String phone : stock.getPhoneList()) {
			SwingHelper.addComboItem(phoneComboBox, phone, true);
		}
		for (String melody : stock.getMelodyList()) {
			SwingHelper.addComboItem(melodyComboBox, melody, true);
		}
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
	protected final CommonFrame primaryFrame;

	protected JCheckBox keepHistoryCheckBox;
	protected JCheckBox afterTriggerRemoveCheckBox;
	protected JComboBox statusComboBox;

	protected JComboBox alertNameComboBox;
	protected JCheckBox emailCheckBox;
	protected JComboBox emailComboBox;
	protected ActionListener emailCheckBoxListener;
	protected JCheckBox phoneCheckBox;
	protected JComboBox phoneComboBox;
	protected ActionListener phoneCheckBoxListener;
	protected JCheckBox melodyCheckBox;
	protected JComboBox melodyComboBox;
	protected ActionListener melodyCheckBoxListener;
	protected JCheckBox notifyWindowCheckBox;

	public enum Type {
		CREATE, EDIT
	}
}
