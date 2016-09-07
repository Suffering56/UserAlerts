package gui.panel.userAlerts.data;

import java.util.Date;

import gui.panel.userAlerts.util.StringHelper;

public abstract class ClientAlert {

	public ClientAlert() {
		this(null, null, null, false, null, false, null, false, null, false);
	}

	public ClientAlert(String name, Date creationDate, Date lastEventDate, boolean emailOn, String email, boolean phoneSmsOn, String phoneSms,
			boolean melodyOn, String melody, boolean popupWindowOn) {

		this.id = -1;
		setNewsID("temp");
		
		setName(name);
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNewsID() {
		return newsID;
	}

	public void setNewsID(String newsID) {
		this.newsID = newsID;
	}

	// Not null!
	public String getName() {
		return name;
	}

	// Not null!
	public void setName(String name) {
		this.name = (name == null) ? StringHelper.EMPTY : name;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
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
		return "NewsAlert [id=" + id + ", name=" + name + ", creationDate=" + creationDate + "]";
	}

	// id алерта
	protected int id;
	protected String newsID;

	// Название алерта
	protected String name;
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
}
