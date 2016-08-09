package gui.panel.userAlerts.data;

public class ExpressionEntity {

	public ExpressionEntity(String value, String displayValue) {
		this.value = value;
		this.displayValue = displayValue;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDisplayValue() {
		return displayValue;
	}

	public void setDisplayValue(String displayValue) {
		this.displayValue = displayValue;
	}

	private String value;
	private String displayValue;
}