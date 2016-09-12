package gui.panel.userAlerts.data;

import java.util.Date;

import gui.panel.userAlerts.util.DateHelper;
import gui.panel.userAlerts.util.StringHelper;

public abstract class ClientAlert {

	public ClientAlert() {
		this(null, ETERNITY_LIFETIME, true, null, null, false, null, false, null, false, null, false);
	}

	public ClientAlert(String name, int lifetime, boolean keepHistory, Date creationDate, Date lastEventDate, boolean emailOn, String email,
			boolean phoneSmsOn, String phoneSms, boolean melodyOn, String melody, boolean popupWindowOn) {

		this.id = -1;
		setServerId("temp");
		setStatusOn(true);

		setName(name);
		setLifetime(lifetime);
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String newsID) {
		this.serverId = newsID;
	}

	public boolean isStatusOn() {
		return statusOn;
	}

	public void setStatusOn(boolean status) {
		this.statusOn = status;
	}

	public int getLifetime() {
		return lifetime;
	}

	public String getLifetimeString() {
		return String.valueOf(lifetime);
	}

	public void setLifetime(String lifetime) {
		try {
			int temp = Integer.valueOf(lifetime);
			this.lifetime = temp;
		} catch (NumberFormatException e) {
			this.lifetime = ETERNITY_LIFETIME;
		}
	}

	public void setLifetime(int lifetime) {
		this.lifetime = lifetime;
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
	protected int id;
	protected String serverId;
	protected boolean statusOn;

	// Название алерта
	protected String name;
	// Количество срабатываний алерта
	protected int lifetime;
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

	public static final int ETERNITY_LIFETIME = 99999;
}
