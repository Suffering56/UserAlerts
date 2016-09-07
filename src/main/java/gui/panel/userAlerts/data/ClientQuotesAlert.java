package gui.panel.userAlerts.data;

import gui.panel.userAlerts.util.StringHelper;

public class ClientQuotesAlert extends ClientAlert {

	public ClientQuotesAlert() {
		this(null);
	}

	public ClientQuotesAlert(String name) {
		this(name, null, null, null, null, null, false, null, false, null, false, null, true);
	}

	public ClientQuotesAlert(String name, String instrument, String marketPlace, DirectionName directionName, DirectionExpression directionExpression,
			String directionValue, boolean emailOn, String email, boolean phoneSmsOn, String phoneSms, boolean melodyOn, String melody,
			boolean notifyWindowOn) {

		super(name, null, null, emailOn, email, phoneSmsOn, phoneSms, melodyOn, melody, notifyWindowOn);

		this.instrument = (instrument == null) ? StringHelper.EMPTY : instrument;
		this.marketPlace = (marketPlace == null) ? StringHelper.EMPTY : marketPlace;
		this.directionName = (directionName == null) ? DirectionName.LAST : directionName;
		this.directionExpression = (directionExpression == null) ? DirectionExpression.MORE : directionExpression;
		this.directionValue = (directionValue == null) ? "0.0" : directionValue;
		try {
			Double.valueOf(this.directionValue);
		} catch (NumberFormatException ex) {
			System.out.println("Некорректное значение (dobule) <directionValue == " + this.directionValue + ">!");
			this.directionValue = "0.0";
		}
	}

	public String getInstrument() {
		return instrument;
	}

	public void setInstrument(String instrument) {
		this.instrument = instrument;
	}

	public String getMarketPlace() {
		return marketPlace;
	}

	public void setMarketPlace(String marketPlace) {
		this.marketPlace = marketPlace;
	}

	public DirectionName getDirectionName() {
		return directionName;
	}

	public void setDirectionName(DirectionName directionName) {
		this.directionName = directionName;
	}

	public DirectionExpression getDirectionExpression() {
		return directionExpression;
	}

	public void setDirectionExpression(DirectionExpression directionExpression) {
		this.directionExpression = directionExpression;
	}

	public String getDirectionValue() {
		return directionValue;
	}

	public void setDirectionValue(String directionValue) {
		this.directionValue = directionValue;
	}

	// Инструмент
	private String instrument;
	// Площадка
	private String marketPlace;
	// Показатель - имя (Last/Close/Bid/Ask)
	private DirectionName directionName;
	// Показатель - выражение (more/less/more_equals/less_equals)
	private DirectionExpression directionExpression;
	// Показатель - значение
	private String directionValue;

	public enum DirectionName {
		LAST, CLOSE, BID, ASK
	}

	public enum DirectionExpression {
		MORE, LESS, MORE_EQUALS, LESS_EQUALS
	}
}
