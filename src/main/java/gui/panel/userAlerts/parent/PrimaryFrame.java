package gui.panel.userAlerts.parent;

import gui.panel.userAlerts.data.Alert;
import gui.panel.userAlerts.data.Stock;

public interface PrimaryFrame extends Disablable {

	public void createAlert(Alert alert);

	public void updateAlert(Alert alert);

	public Stock getStock();

}
