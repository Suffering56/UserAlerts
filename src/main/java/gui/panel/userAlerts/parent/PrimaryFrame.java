package gui.panel.userAlerts.parent;

import gui.panel.userAlerts.data.NewsAlert;
import gui.panel.userAlerts.data.QuotesAlert;
import gui.panel.userAlerts.data.Stock;

public interface PrimaryFrame extends Disablable {

	public void createNewsAlert(NewsAlert alert);

	public void updateNewsAlert(NewsAlert alert);

	public void createQuotesAlert(QuotesAlert alert);

	public void updateQuotesAlert(QuotesAlert alert);

	public Stock getStock();

}
