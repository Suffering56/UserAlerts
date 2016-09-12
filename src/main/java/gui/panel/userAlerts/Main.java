package gui.panel.userAlerts;

import java.net.URL;

import javax.swing.UIManager;
import javax.swing.plaf.synth.SynthLookAndFeel;

import gui.panel.userAlerts.constants.AlertsResourcesConstants;
import gui.panel.userAlerts.control.AlertsCommonFrame;

public class Main {

	public static void main(String[] args) {
		setLookAndFeel();
		new AlertsCommonFrame().show();
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
