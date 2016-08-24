package gui.panel.userAlerts.overridden.model;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import gui.panel.userAlerts.data.QuotesAlert.DirectionName;

@SuppressWarnings("serial")
public class QuotesDirectionNameComboModel extends DefaultComboBoxModel {

	public QuotesDirectionNameComboModel() {
		addElement(last);
		addElement(close);
		addElement(bid);
		addElement(ask);
	}

	public static DirectionName getDirectionValue(JComboBox box) {
		String displayValue = box.getSelectedItem().toString();

		if (displayValue.equals(last)) {
			return DirectionName.LAST;
		} else if (displayValue.equals(close)) {
			return DirectionName.CLOSE;
		} else if (displayValue.equals(bid)) {
			return DirectionName.BID;
		} else if (displayValue.equals(ask)) {
			return DirectionName.ASK;
		} else {
			return DirectionName.LAST;
		}
	}

	public static void setValue(JComboBox box, DirectionName directionValue) {
		if (directionValue == DirectionName.LAST) {
			box.setSelectedIndex(last_index);
		} else if (directionValue == DirectionName.CLOSE) {
			box.setSelectedIndex(close_index);
		} else if (directionValue == DirectionName.BID) {
			box.setSelectedIndex(bid_index);
		} else if (directionValue == DirectionName.ASK) {
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
