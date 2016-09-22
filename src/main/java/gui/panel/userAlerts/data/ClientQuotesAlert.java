package gui.panel.userAlerts.data;

import java.awt.Color;

import gui.panel.userAlerts.util.StringHelper;
import p.alerts.client_api.QuoteAlert;
import p.alerts.client_api.QuoteAlert.COMPARE_TYPE;

public class ClientQuotesAlert extends ClientAlert {

	public ClientQuotesAlert() {
		this(StringHelper.EMPTY);
	}

	public ClientQuotesAlert(String name) {
		this(name, true, false, true, null, null, null, null, null, false, null, false, null, false, null, null, true);
	}

	public ClientQuotesAlert(String name, boolean statusOn, boolean afterTriggerRemove, boolean keepHistory, String instrument, String marketPlace,
			Field directionName, COMPARE_TYPE directionExpression, String directionValue, boolean emailOn, String email, boolean phoneSmsOn,
			String phoneSms, boolean melodyOn, String melody, Color lineColor, boolean notifyWindowOn) {

		super(name, statusOn, afterTriggerRemove, keepHistory, null, null, emailOn, email, phoneSmsOn, phoneSms, melodyOn, melody, notifyWindowOn);

		setLineColor(lineColor);

		setInstrument(instrument);
		setMarketPlace(marketPlace);
		setField(directionName);
		setCompareType(directionExpression);
		setValue(directionValue);
	}

	public ClientQuotesAlert(QuoteAlert serverAlert) {
		setCreationDate(serverAlert.getCreationDate());
		setLastEventDate(serverAlert.getLastFireEventDate());

		setId(serverAlert.getAlertID());
		setName(serverAlert.getName());

		setKeepHistory(serverAlert.getKeepHistory());
		setAfterTriggerRemove(serverAlert.getRemoveAfterFireUp());
		setStatusOn(!serverAlert.getDisabledAlert());

		setEmail(serverAlert.getEmail());
		setEmailOn(!email.isEmpty());
		setPhoneSms(serverAlert.getPhone());
		setPhoneSmsOn(!phoneSms.isEmpty());
		setMelody(serverAlert.getSoundSetup());
		setMelodyOn(!melody.isEmpty());
		setPopupWindowOn(serverAlert.getPopupWindow());

		setLineColor(serverAlert.getColor());

		setInstrument(serverAlert.getInstr());
		setMarketPlace(serverAlert.getSheet());
		setField(serverAlert.getField());
		setCompareType(serverAlert.get_COMPARE_TYPE());
		setValue(serverAlert.getValue());
	}

	public QuoteAlert convertToServerAlert(boolean isUpdate) {
		QuoteAlert serverAlert = new QuoteAlert();

		if (isUpdate)
			serverAlert.setAlertID(id);

		serverAlert.setName(name);

		serverAlert.setKeepHistory(keepHistory);
		serverAlert.setRemoveAfterFireUp(afterTriggerRemove);
		serverAlert.disableAlert(!statusOn);

		serverAlert.setEmail((emailOn == false) ? StringHelper.EMPTY : email);
		serverAlert.setPhone((phoneSmsOn == false) ? StringHelper.EMPTY : phoneSms);
		serverAlert.setSoundSetup((melodyOn == false) ? StringHelper.EMPTY : melody);
		serverAlert.setPopupWindow(popupWindowOn);

		serverAlert.setColor(lineColor);

		serverAlert.setInstr(instrument);
		serverAlert.setSheet(marketPlace);
		serverAlert.setField(field.toString());
		serverAlert.set_COMPARE_TYPE(compareType);
		serverAlert.setValue(Double.valueOf(value));

		//		serverAlert.setPeriod("");
		return serverAlert;
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

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = (field == null) ? Field.LAST : field;
	}

	public void setField(String field) {
		try {
			this.field = Field.valueOf(field);
		} catch (Exception e) {
			this.field = Field.LAST;
		}
	}

	public COMPARE_TYPE getCompareType() {
		return compareType;
	}

	public void setCompareType(COMPARE_TYPE compareType) {
		this.compareType = (compareType == null) ? COMPARE_TYPE.GREATER : compareType;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = (value == null) ? "0.0" : value;
		try {
			Double.valueOf(this.value);
		} catch (NumberFormatException ex) {
			System.out.println("Некорректное значение (dobule) <directionValue == " + this.value + ">!");
			this.value = "0.0";
		}
	}

	public void setValue(double value) {
		this.value = String.valueOf(value);
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
	// Поле - имя (Last/Close/Bid/Ask)
	private Field field;
	// Поле - выражение (>,<,>=,<=)
	private COMPARE_TYPE compareType;
	// Поле - значение
	private String value;
	// Цвет линии алерта на графике
	private Color lineColor;

	public enum Field {
		LAST, CLOSE, BID, ASK
	}
}
