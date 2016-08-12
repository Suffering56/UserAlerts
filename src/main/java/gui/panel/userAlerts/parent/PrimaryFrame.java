package gui.panel.userAlerts.parent;

import gui.panel.userAlerts.data.NewsAlert;
import gui.panel.userAlerts.data.Stock;

public interface PrimaryFrame {

	public void createAlert(NewsAlert alert);

	public void updateAlert(NewsAlert alert);

	public Stock getStock();

	public void enable();

	public void disable();

}
