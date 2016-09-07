package gui.panel.userAlerts.parent;

import gui.panel.userAlerts.data.ClientNewsAlert;
import gui.panel.userAlerts.data.ClientQuotesAlert;
import gui.panel.userAlerts.data.Stock;

public interface PrimaryFrame extends Disablable {

	public void createNewsAlert(ClientNewsAlert alert);

	public void updateNewsAlert(ClientNewsAlert alert);

	public void createQuotesAlert(ClientQuotesAlert alert);

	public void updateQuotesAlert(ClientQuotesAlert alert);

	public void updateNewsAlertsTableFromStock();

	public Stock getStock();

}
