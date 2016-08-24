package gui.panel.userAlerts.data;

import java.util.Calendar;

@SuppressWarnings("deprecation")
public class Alert {
	
	protected Alert() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getLastEventDate() {
		return lastEventDate;
	}

	public void setLastEventDate(String lastEventDate) {
		this.lastEventDate = lastEventDate;
	}
	
	public boolean isEmailOn() {
		return emailOn;
	}

	public void setEmailOn(boolean emailOn) {
		this.emailOn = emailOn;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isPhoneSmsOn() {
		return phoneSmsOn;
	}

	public void setPhoneSmsOn(boolean phoneSmsOn) {
		this.phoneSmsOn = phoneSmsOn;
	}

	public String getPhoneSms() {
		return phoneSms;
	}

	public void setPhoneSms(String phoneSms) {
		this.phoneSms = phoneSms;
	}

	public boolean isMelodyOn() {
		return melodyOn;
	}

	public void setMelodyOn(boolean melodyOn) {
		this.melodyOn = melodyOn;
	}

	public String getMelody() {
		return melody;
	}

	public void setMelody(String melody) {
		this.melody = melody;
	}

	public boolean isNotifyWindowOn() {
		return notifyWindowOn;
	}

	public void setNotifyWindowOn(boolean windowPopupOn) {
		this.notifyWindowOn = windowPopupOn;
	}

	// id алерта
	protected int id = -1;
	// Название алерта
	protected String name = "";
	// Дата создания
	protected String creationDate = Calendar.getInstance().getTime().toLocaleString();
	// Дата последнего срабатывания
	protected String lastEventDate = "-";

	// Информировать по e-mail
	protected boolean emailOn;
	protected String email = "";
	// Информировать по SMS
	protected boolean phoneSmsOn;
	protected String phoneSms = "";
	// Звуковой сигнал
	protected boolean melodyOn;
	protected String melody = "";
	// Всплывающее окно
	protected boolean notifyWindowOn = true;
}
