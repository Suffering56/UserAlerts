package gui.panel.userAlerts.overridden.model;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

@SuppressWarnings("serial")
public class AlertStatusComboModel extends DefaultComboBoxModel {

	public AlertStatusComboModel() {
		addElement(enable);
		addElement(disable);
	}

	public static boolean getBooleanValue(JComboBox box) {
		String displayValue = box.getSelectedItem().toString();
		return displayValue.equals(enable);
	}

	public static void setValue(JComboBox box, boolean status) {
		if (status == true) {
			box.setSelectedIndex(enable_index);
		} else {
			box.setSelectedIndex(disable_index);
		}
	}

	public static final String enable = "Активен";
	public static final String disable = "Не активен";

	public static final int enable_index = 0;
	public static final int disable_index = 1;
}
