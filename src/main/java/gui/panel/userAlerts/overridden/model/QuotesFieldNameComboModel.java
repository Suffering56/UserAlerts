package gui.panel.userAlerts.overridden.model;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import gui.panel.userAlerts.data.ClientQuotesAlert.Field;

@SuppressWarnings("serial")
public class QuotesFieldNameComboModel extends DefaultComboBoxModel {

	public QuotesFieldNameComboModel() {
		addElement(last);
		addElement(close);
		addElement(bid);
		addElement(ask);
	}

	public static Field getDirectionValue(JComboBox box) {
		String displayValue = box.getSelectedItem().toString();

		if (displayValue.equals(last)) {
			return Field.LAST;
		} else if (displayValue.equals(close)) {
			return Field.CLOSE;
		} else if (displayValue.equals(bid)) {
			return Field.BID;
		} else if (displayValue.equals(ask)) {
			return Field.ASK;
		} else {
			return Field.LAST;
		}
	}

	public static void setValue(JComboBox box, Field directionValue) {
		if (directionValue == Field.LAST) {
			box.setSelectedIndex(last_index);
		} else if (directionValue == Field.CLOSE) {
			box.setSelectedIndex(close_index);
		} else if (directionValue == Field.BID) {
			box.setSelectedIndex(bid_index);
		} else if (directionValue == Field.ASK) {
			box.setSelectedIndex(ask_index);
		} else {
			box.setSelectedIndex(last_index);
		}
	}

	public static final String last = "Last";
	public static final String close = "Close";
	public static final String bid = "Bid";
	public static final String ask = "Ask";

	public static final int last_index = 0;
	public static final int close_index = 1;
	public static final int bid_index = 2;
	public static final int ask_index = 3;
}
