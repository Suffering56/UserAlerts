package gui.panel.userAlerts.data;

public class QuotesAlert extends Alert {

	public QuotesAlert() {
		// default
	}

	public QuotesAlert(int id, String name, String instrument, String marketPlace, DirectionName directionName,
			DirectionExpression directionExpression, String directionValue, boolean emailOn, String email,
			boolean phoneSmsOn, String phoneSms, boolean melodyOn, String melody, boolean windowPopupOn) {
		this.id = id;
		this.name = name;
		this.instrument = instrument;
		this.marketPlace = marketPlace;
		this.directionName = directionName;
		this.directionExpression = directionExpression;
		this.directionValue = directionValue;
		this.emailOn = emailOn;
		this.email = email;
		this.phoneSmsOn = phoneSmsOn;
		this.phoneSms = phoneSms;
		this.melodyOn = melodyOn;
		this.melody = melody;
		this.notifyWindowOn = windowPopupOn;
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
