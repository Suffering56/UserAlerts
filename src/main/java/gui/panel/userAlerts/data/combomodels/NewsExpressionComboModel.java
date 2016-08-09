package gui.panel.userAlerts.data.combomodels;

import javax.swing.DefaultComboBoxModel;

import gui.panel.userAlerts.data.AlertEntity;

@SuppressWarnings("serial")
public class NewsExpressionComboModel extends DefaultComboBoxModel {

	public NewsExpressionComboModel() {
		addElement(not);
		addElement(or);
		addElement(and);
	}

	public String convertToNormalValue(String displayValue) {
		if (displayValue.equals(not)) {
			return AlertEntity.NOT;
		} else if (displayValue.equals(or)) {
			return AlertEntity.OR;
		} else if (displayValue.equals(and)) {
			return AlertEntity.AND;
		} else {
			//default
			return AlertEntity.NOT;
		}
	}

	public static final String not = "нет";
	public static final String or = "или";
	public static final String and = "и";
}
