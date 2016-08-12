package gui.panel.userAlerts.data.combomodels;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import gui.panel.userAlerts.data.NewsAlert.Expression;

@SuppressWarnings("serial")
public class NewsExpressionComboModel extends DefaultComboBoxModel {

	public NewsExpressionComboModel() {
		addElement(not);
		addElement(or);
		addElement(and);
	}

	public static Expression getExpressionValue(JComboBox box) {
		String displayValue = box.getSelectedItem().toString();

		if (displayValue.equals(not)) {
			return Expression.NOT;
		} else if (displayValue.equals(or)) {
			return Expression.OR;
		} else if (displayValue.equals(and)) {
			return Expression.AND;
		} else {
			// default
			return Expression.NOT;
		}
	}

	public static void setValue(JComboBox box, Expression expressionValue) {
		if (expressionValue == Expression.NOT) {
			box.setSelectedIndex(not_index);
		} else if (expressionValue == Expression.OR) {
			box.setSelectedIndex(or_index);
		} else if (expressionValue == Expression.AND) {
			box.setSelectedIndex(and_index);
		} else {
			box.setSelectedIndex(not_index);
		}
	}

	public static final String not = "нет";
	public static final String or = "или";
	public static final String and = "и";

	public static final int not_index = 0;
	public static final int or_index = 1;
	public static final int and_index = 2;
}
