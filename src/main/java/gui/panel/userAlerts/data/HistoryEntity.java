package gui.panel.userAlerts.data;

import java.util.Date;

import gui.panel.userAlerts.util.DateHelper;
import gui.panel.userAlerts.util.StringHelper;
import p.alerts.client_api.NewsFireAlert;

public class HistoryEntity {

	public HistoryEntity(NewsFireAlert serverAlert) {
		setId(serverAlert.getAlertID());
		setName(serverAlert.getName());
		setTriggerTime(serverAlert.getLastFireEventDate());
		setTheme(serverAlert.getFiredNewsHeadline().getHeader());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = (name == null) ? StringHelper.EMPTY : name;
	}

	public Date getTriggerTime() {
		return triggerTime;
	}

	public String getTriggerTimeString() {
		return (triggerTime == null) ? StringHelper.EMPTY : DateHelper.convertFull(triggerTime);
	}

	public void setTriggerTime(Date triggerTime) {
		this.triggerTime = triggerTime;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = (theme == null) ? StringHelper.EMPTY : theme;
	}

	@Override
	public String toString() {
		return "HistoryEntity [name=" + name + ", triggerTime=" + getTriggerTimeString() + "]";
	}

	private String id;
	private String name;
	private Date triggerTime;
	private String theme;
}
