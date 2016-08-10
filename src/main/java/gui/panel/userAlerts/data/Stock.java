package gui.panel.userAlerts.data;

import java.util.ArrayList;
import java.util.List;

public class Stock {

	public void add(AlertEntity alert) {
		int id = alert.getId();
		
		if (alert.getId() == -1) {
			id = alertsList.isEmpty() ? 0 : alertsList.get(alertsList.size() - 1).getId();
			while (containsId(id)) {
				id++;
			}
		} else {
			
			while (containsId(id)) {
				id++;
			}
		}

		alert.setId(id);
		alertsList.add(alert);
		
//		System.out.println(alert);
	}

	public void removeByIndex(int index) {
		alertsList.remove(index);
	}

	public boolean removeById(int id) {
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

	public List<AlertEntity> getAlertsList() {
		return alertsList;
	}

	private final List<AlertEntity> alertsList = new ArrayList<AlertEntity>();
}
