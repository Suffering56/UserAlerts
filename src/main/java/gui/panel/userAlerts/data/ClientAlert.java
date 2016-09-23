package gui.panel.userAlerts.data;

import java.util.Date;

import gui.panel.userAlerts.util.DateHelper;
import gui.panel.userAlerts.util.StringHelper;
import p.alerts.client_api.AlertsSMSandMailAPI;

public abstract class ClientAlert {

	public ClientAlert() {
		this(null, true, false, true, null, null, false, null, false, null, false, null, true);
	}

	public ClientAlert(String name, boolean statusOn, boolean afterTriggerRemove, boolean keepHistory, Date creationDate, Date lastEventDate,
			boolean emailOn, String email, boolean phoneSmsOn, String phoneSms, boolean melodyOn, String melody, boolean popupWindowOn) {

		setName(name);
		setStatusOn(statusOn);
		setAfterTriggerRemove(afterTriggerRemove);
		setKeepHistory(keepHistory);

		setCreationDate(creationDate);
		setLastEventDate(lastEventDate);

		setEmailOn(emailOn);
		setEmail(email);
		setPhoneSmsOn(phoneSmsOn);
		setPhoneSms(phoneSms);
		setMelodyOn(melodyOn);
		setMelody(melody);
		setPopupWindowOn(popupWindowOn);
	}

	public String getId() {
		return id;
	}

	public void setId(String newsID) {
		this.id = newsID;
	}

	public boolean isAfterTriggerRemove() {
		return afterTriggerRemove;
	}

	public void setAfterTriggerRemove(boolean afterTriggerRemove) {
		this.afterTriggerRemove = afterTriggerRemove;
	}

	public boolean isStatusOn() {
		return statusOn;
	}

	public void setStatusOn(boolean status) {
		this.statusOn = status;
	}

	public boolean isKeepHistory() {
		return keepHistory;
	}

	public void setKeepHistory(boolean keepHistory) {
		this.keepHistory = keepHistory;
	}

	// Not null!
	public String getName() {
		return name;
	}

	// Not null!
	public void setName(String name) {
		this.name = (name == null) ? StringHelper.EMPTY : name;
	}

	public String getCreationDateString() {
		return (creationDate == null) ? StringHelper.EMPTY : DateHelper.convertDate(creationDate);
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getLastEventDateString() {
		return (lastEventDate == null) ? StringHelper.EMPTY : DateHelper.convertFull(lastEventDate);
	}

	public Date getLastEventDate() {
		return lastEventDate;
	}

	public void setLastEventDate(Date lastEventDate) {
		this.lastEventDate = lastEventDate;
	}

	public boolean isEmailOn() {
		return emailOn;
	}

	public void setEmailOn(boolean emailOn) {
		this.emailOn = emailOn;
	}

	// Not null!
	public String getEmail() {
		return email;
	}

	// Not null!
	public void setEmail(String email) {
		this.email = (email == null) ? StringHelper.EMPTY : email;
	}

	public boolean isPhoneSmsOn() {
		return phoneSmsOn;
	}

	public void setPhoneSmsOn(boolean phoneSmsOn) {
		this.phoneSmsOn = phoneSmsOn;
	}

	// Not null!
	public String getPhoneSms() {
		return phoneSms;
	}

	// Not null!
	public void setPhoneSms(String phoneSms) {
		this.phoneSms = (phoneSms == null) ? StringHelper.EMPTY : phoneSms;
	}

	public boolean isMelodyOn() {
		return melodyOn;
	}

	public void setMelodyOn(boolean melodyOn) {
		this.melodyOn = melodyOn;
	}

	// Not null!
	public String getMelody() {
		return melody;
	}

	// Not null!
	public void setMelody(String melody) {
		this.melody = (melody == null) ? StringHelper.EMPTY : melody;
	}

	public boolean isPopupWindowOn() {
		return popupWindowOn;
	}

	public void setPopupWindowOn(boolean popupWindowOn) {
		this.popupWindowOn = popupWindowOn;
	}

	@Override
	public String toString() {
		return "ClientAlert [name=" + name + ", lastEventDate=" + getLastEventDateString() + "]";
	}

	// id алерта
	protected String id;
	protected boolean statusOn;
	protected boolean afterTriggerRemove;

	// Название алерта
	protected String name;
	// Хранить историю
	protected boolean keepHistory;
	// Дата создания
	protected Date creationDate;
	// Дата последнего срабатывания
	protected Date lastEventDate;

	// Информировать по e-mail
	protected boolean emailOn;
	protected String email;
	// Информировать по SMS
	protected boolean phoneSmsOn;
	protected String phoneSms;
	// Звуковой сигнал
	protected boolean melodyOn;
	protected String melody;
	// Всплывающее окно
	protected boolean popupWindowOn;

	protected static final String NONE = AlertsSMSandMailAPI.NOT_SPECIFIED;
}
