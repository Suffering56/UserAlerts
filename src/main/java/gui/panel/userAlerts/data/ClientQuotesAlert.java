package gui.panel.userAlerts.data;

import java.awt.Color;

import gui.panel.userAlerts.util.StringHelper;

public class ClientQuotesAlert extends ClientAlert {

	public ClientQuotesAlert() {
		this(null);
	}

	public ClientQuotesAlert(String name) {
		this(name, ETERNITY_LIFETIME, true, null, null, null, null, null, false, null, false, null, false, null, null, true);
	}

	public ClientQuotesAlert(String name, int lifetime, boolean keepHistory, String instrument, String marketPlace, DirectionName directionName,
			DirectionExpression directionExpression, String directionValue, boolean emailOn, String email, boolean phoneSmsOn, String phoneSms,
			boolean melodyOn, String melody, Color lineColor, boolean notifyWindowOn) {

		super(name, lifetime, keepHistory, null, null, emailOn, email, phoneSmsOn, phoneSms, melodyOn, melody, notifyWindowOn);

		setInstrument(instrument);
		setMarketPlace(marketPlace);
		setDirectionName(directionName);
		setDirectionExpression(directionExpression);
		setDirectionValue(directionValue);
		setLineColor(lineColor);
	}

	public String getInstrument() {
		return instrument;
	}

	public void setInstrument(String instrument) {
		this.instrument = (instrument == null) ? StringHelper.EMPTY : instrument;
	}

	public String getMarketPlace() {
		return marketPlace;
	}

	public void setMarketPlace(String marketPlace) {
		this.marketPlace = (marketPlace == null) ? StringHelper.EMPTY : marketPlace;
	}

	public DirectionName getDirectionName() {
		return directionName;
	}

	public void setDirectionName(DirectionName directionName) {
		this.directionName = (directionName == null) ? DirectionName.LAST : directionName;
	}

	public DirectionExpression getDirectionExpression() {
		return directionExpression;
	}

	public void setDirectionExpression(DirectionExpression directionExpression) {
		this.directionExpression = (directionExpression == null) ? DirectionExpression.MORE : directionExpression;
	}

	public String getDirectionValue() {
		return directionValue;
	}

	public void setDirectionValue(String directionValue) {
		this.directionValue = (directionValue == null) ? "0.0" : directionValue;
		try {
			Double.valueOf(this.directionValue);
		} catch (NumberFormatException ex) {
			System.out.println("Некорректное значение (dobule) <directionValue == " + this.directionValue + ">!");
			this.directionValue = "0.0";
		}
	}

	public Color getLineColor() {
		return lineColor;
	}

	public void setLineColor(Color lineColor) {
		this.lineColor = lineColor;
	}

	// Инструмент
	private String instrument;
	// Площадка
	private String marketPlace;
	// Показатель - имя (Last/Close/Bid/Ask)
	private DirectionName directionName;
	// Показатель - выражение (>,<,>=,<=)
	private DirectionExpression directionExpression;
	// Показатель - значение
	private String directionValue;
	// Цвет линии алерта на графике
	private Color lineColor;

	public enum DirectionName {
		LAST, CLOSE, BID, ASK
	}

	public enum DirectionExpression {
		MORE, LESS, MORE_EQUALS, LESS_EQUALS
	}
}
