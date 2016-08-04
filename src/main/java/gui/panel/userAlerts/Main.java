package gui.panel.userAlerts;

import java.net.URL;

import javax.swing.UIManager;
import javax.swing.plaf.synth.SynthLookAndFeel;

import gui.panel.userAlerts.control.AlertsFrame;

public class Main {
	public static void main(String[] args) {
		setLookAndFeel();
		new AlertsFrame().show();
	}

	private static void setLookAndFeel() {
		try {
			SynthLookAndFeel synth = new SynthLookAndFeel();
			URL resource = Main.class.getResource("/configs/laf.xml");
			synth.load(resource);
			UIManager.setLookAndFeel(synth);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
