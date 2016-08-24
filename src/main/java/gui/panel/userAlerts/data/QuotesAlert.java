package gui.panel.userAlerts.data;

public class QuotesAlert extends Alert {

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
	private String instrument = "";
	// Площадка
	private String marketPlace = "";
	// Показатель - имя (Last/Close/Bid/Ask)
	private DirectionName directionName = DirectionName.LAST;
	// Показатель - выражение (more/less/more_equals/less_equals)
	private DirectionExpression directionExpression = DirectionExpression.MORE;
	// Показатель - значение
	private String directionValue = "0.0";

	public enum DirectionName {
		LAST, CLOSE, BID, ASK
	}

	public enum DirectionExpression {
		MORE, LESS, MORE_EQUALS, LESS_EQUALS
	}
}
