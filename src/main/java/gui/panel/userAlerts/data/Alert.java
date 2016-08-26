package gui.panel.userAlerts.data;

import java.util.Calendar;

@SuppressWarnings("deprecation")
public abstract class Alert {

	public Alert(String name, String creationDate, String lastEventDate, boolean emailOn, String email,
			boolean phoneSmsOn, String phoneSms, boolean melodyOn, String melody, boolean notifyWindowOn) {

		this.id = -1;
		this.name = (name == null) ? "" : name;
		this.creationDate = (creationDate == null) ? Calendar.getInstance().getTime().toLocaleString() : creationDate;
		this.lastEventDate = (lastEventDate == null) ? "-" : lastEventDate;

		this.emailOn = emailOn;
		this.email = (email == null) ? "" : email;
		this.phoneSmsOn = phoneSmsOn;
		this.phoneSms = (phoneSms == null) ? "" : phoneSms;
		this.melodyOn = melodyOn;
		this.melody = (melody == null) ? "" : melody;
		this.notifyWindowOn = notifyWindowOn;
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

	@Override
	public String toString() {
		return "NewsAlert [id=" + id + ", name=" + name + ", creationDate=" + creationDate + "]";
	}

	// id алерта
	protected int id;
	// Название алерта
	protected String name;
	// Дата создания
	protected String creationDate;
	// Дата последнего срабатывания
	protected String lastEventDate;

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
	protected boolean notifyWindowOn;
}
