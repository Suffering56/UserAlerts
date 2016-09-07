package gui.panel.userAlerts.overridden.model;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import gui.panel.userAlerts.data.ClientQuotesAlert.DirectionExpression;

@SuppressWarnings("serial")
public class QuotesDirectionExpressionComboModel extends DefaultComboBoxModel {
	
	public QuotesDirectionExpressionComboModel() {
		addElement(more);
		addElement(less);
		addElement(more_equals);
		addElement(less_equals);
	}

	public static DirectionExpression getDirectionValue(JComboBox box) {
		String displayValue = box.getSelectedItem().toString();

		if (displayValue.equals(more)) {
			return DirectionExpression.MORE;
		} else if (displayValue.equals(less)) {
			return DirectionExpression.LESS;
		} else if (displayValue.equals(more_equals)) {
			return DirectionExpression.MORE_EQUALS;
		} else if (displayValue.equals(less_equals)) {
			return DirectionExpression.LESS_EQUALS;
		} else {
			return DirectionExpression.MORE;
		}
	}

	public static void setValue(JComboBox box, DirectionExpression directionValue) {
		if (directionValue == DirectionExpression.MORE) {
			box.setSelectedIndex(more_index);
		} else if (directionValue == DirectionExpression.LESS) {
			box.setSelectedIndex(less_index);
		} else if (directionValue == DirectionExpression.MORE_EQUALS) {
			box.setSelectedIndex(more_equals_index);
		} else if (directionValue == DirectionExpression.LESS_EQUALS) {
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
