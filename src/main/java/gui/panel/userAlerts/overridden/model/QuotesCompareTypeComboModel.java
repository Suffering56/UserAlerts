package gui.panel.userAlerts.overridden.model;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import p.alerts.client_api.QuoteAlert.COMPARE_TYPE;

@SuppressWarnings("serial")
public class QuotesCompareTypeComboModel extends DefaultComboBoxModel {

	public QuotesCompareTypeComboModel() {
		addElement(more);
		addElement(less);
		addElement(more_equals);
		addElement(less_equals);
	}

	public static COMPARE_TYPE getDirectionValue(JComboBox box) {
		String displayValue = box.getSelectedItem().toString();

		if (displayValue.equals(more)) {
			return COMPARE_TYPE.GREATER;
		} else if (displayValue.equals(less)) {
			return COMPARE_TYPE.LESSER;
		} else if (displayValue.equals(more_equals)) {
			return COMPARE_TYPE.GREATER_EQUALS;
		} else if (displayValue.equals(less_equals)) {
			return COMPARE_TYPE.LESSER_EQUALS;
		} else {
			return COMPARE_TYPE.GREATER;
		}
	}

	public static void setValue(JComboBox box, COMPARE_TYPE directionValue) {
		if (directionValue == COMPARE_TYPE.GREATER) {
			box.setSelectedIndex(more_index);
		} else if (directionValue == COMPARE_TYPE.LESSER) {
			box.setSelectedIndex(less_index);
		} else if (directionValue == COMPARE_TYPE.GREATER_EQUALS) {
			box.setSelectedIndex(more_equals_index);
		} else if (directionValue == COMPARE_TYPE.LESSER_EQUALS) {
			box.setSelectedIndex(less_equals_index);
		} else {
			box.setSelectedIndex(more_index);
		}
	}

	public static final String more = ">";
	public static final String less = "<";
	public static final String more_equals = ">=";
	public static final String less_equals = "<=";

	public static final int more_index = 0;
	public static final int less_index = 1;
	public static final int more_equals_index = 2;
	public static final int less_equals_index = 3;
}
