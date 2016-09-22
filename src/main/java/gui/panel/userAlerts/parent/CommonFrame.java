package gui.panel.userAlerts.parent;

import gui.panel.userAlerts.data.ClientNewsAlert;
import gui.panel.userAlerts.data.ClientQuotesAlert;
import gui.panel.userAlerts.data.remote.Stock;

public interface CommonFrame extends Disablable {

	public void createNewsAlert(ClientNewsAlert alert);

	public void updateNewsAlert(ClientNewsAlert alert);

	public void createQuotesAlert(ClientQuotesAlert alert);

	public void updateQuotesAlert(ClientQuotesAlert alert);

	public void updateNewsAlertsTableFromStock();

	public void updateQuotesAlertsTableFromStock();

	public Stock getStock();
}
