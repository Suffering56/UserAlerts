package gui.panel.userAlerts.data;

import java.util.ArrayList;
import java.util.List;

public class Stock {

	public void addAlert(AlertEntity alert) {
		if (alert.getId() == -1) {
			int id = alertsList.size();
			while (containsId(id)) {
				id++;
			}
			alert.setId(id);
		}
		alertsList.add(alert);
	}

	public void removeAlertByIndex(int index) {
		alertsList.remove(index);
	}

	public boolean removeAlertById(int id) {
		int removeIndex = -1;
		for (int i = 0; i < alertsList.size(); i++) {
			if (alertsList.get(i).getId() == id) {
				removeIndex = i;
			}
		}
		if (removeIndex != -1) {
			alertsList.remove(removeIndex);
			return true;
		}
		return false;
	}

	public boolean containsId(int id) {
		for (AlertEntity alert : alertsList) {
			if (alert.getId() == id) {
				return true;
			}
		}
		return false;
	}

	public boolean containsIndex(int index) {
		return index < alertsList.size();
	}

	private final List<AlertEntity> alertsList = new ArrayList<AlertEntity>();

}
