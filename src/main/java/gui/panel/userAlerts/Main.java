package gui.panel.userAlerts;

import java.net.URL;

import javax.swing.UIManager;
import javax.swing.plaf.synth.SynthLookAndFeel;

import gui.panel.userAlerts.constants.AlertsResourcesConstants;
import gui.panel.userAlerts.data.remote.Stock;

public class Main {

	public static void main(String[] args) {
		setLookAndFeel();
		Stock stock = new Stock();
		//		new gui.panel.userAlerts.control.LoginFrame(stock).show();
		new gui.panel.userAlerts.control.AlertsCommonFrame(stock, true).show();
	}

	private static void setLookAndFeel() {
		try {
			SynthLookAndFeel synth = new SynthLookAndFeel();
			URL resource = Main.class.getResource(AlertsResourcesConstants.LAF_PATH);
			synth.load(resource);
			UIManager.setLookAndFeel(synth);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
