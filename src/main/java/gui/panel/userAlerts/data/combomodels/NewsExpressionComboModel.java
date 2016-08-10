package gui.panel.userAlerts.data.combomodels;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import gui.panel.userAlerts.data.AlertEntity;

@SuppressWarnings("serial")
public class NewsExpressionComboModel extends DefaultComboBoxModel {

	public NewsExpressionComboModel() {
		addElement(not);
		addElement(or);
		addElement(and);
	}

	public static String getNormalValue(JComboBox box) {
		String displayValue = box.getSelectedItem().toString();

		if (displayValue.equals(not)) {
			return AlertEntity.EXPRESSION_NOT;
		} else if (displayValue.equals(or)) {
			return AlertEntity.EXPRESSION_OR;
		} else if (displayValue.equals(and)) {
			return AlertEntity.EXPRESSION_AND;
		} else {
			// default
			return AlertEntity.EXPRESSION_NOT;
		}
	}

	public static final String not = "нет";
	public static final String or = "или";
	public static final String and = "и";
}
