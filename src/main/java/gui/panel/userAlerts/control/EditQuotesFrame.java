package gui.panel.userAlerts.control;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import gui.panel.userAlerts.data.QuotesAlert;
import gui.panel.userAlerts.parent.PrimaryFrame;
import gui.panel.userAlerts.parent.SwixFrame;

@SuppressWarnings("serial")
public class EditQuotesFrame extends SwixFrame {

	public EditQuotesFrame(PrimaryFrame primaryFrame) {
		this(primaryFrame, null);
	}

	public EditQuotesFrame(PrimaryFrame primaryFrame, QuotesAlert alert) {
		this.primaryFrame = primaryFrame;

		if (alert == null) {
			this.alert = new QuotesAlert();
			TYPE = TYPE_CREATE;
		} else {
			this.alert = alert;
			TYPE = TYPE_EDIT;
		}

		frame.setTitle("Настройка алерта для котировок");
		render("userAlerts/EditQuotesFrame");
	}

	@Override
	protected void beforeRenderInit() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void afterRenderInit() {
		extractAlertData();
	}

	public Action SHOW_CHART = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			if (e != null) {
				chartJPanel.setVisible(!chartJPanel.isVisible());
				pack();
			}
		}
	};

	private void extractAlertData() {
		// onlyRedNewsCheckBox.setSelected(alert.isOnlyRedNews());
		// setOnlyRedNews();
		//
		// NewsExpressionComboModel.setValue(keyWordExpressionComboBox,
		// alert.getKeyWordExpression());
		//
		// if (alert.getKeyWordFilterType() == FilterKey.BY_RELEVANCE) {
		// byRelevanceRadioBtn.setSelected(true);
		// } else {
		// exactMatchRadioBtn.setSelected(true);
		// }
		//
		// NewsExpressionComboModel.setValue(excludeWordExpressionComboBox,
		// alert.getExcludeWordExpression());
		//
		// if (alert.getExcludeWordFilterType() == FilterExclude.EVERYWERE) {
		// everywhereRadioBtn.setSelected(true);
		// } else if (alert.getExcludeWordFilterType() ==
		// FilterExclude.TITLES_ONLY) {
		// titlesOnlyRadioBtn.setSelected(true);
		// } else {
		// redNewsOnlyRadioBtn.setSelected(true);
		// }
		//
		// emailCheckBox.setSelected(alert.isEmailOn());
		// phoneCheckBox.setSelected(alert.isPhoneSmsOn());
		// melodyCheckBox.setSelected(alert.isMelodyOn());
		// newsColorCheckBox.setSelected(alert.isNewsColorOn());
		//
		// newsColor = alert.getNewsColor();
		// notifyWindowCheckBox.setSelected(alert.isWindowPopupOn());
	}

	private QuotesAlert alert;
	protected PrimaryFrame primaryFrame;

	private JPanel chartJPanel;

	private JComboBox alertNameComboBox;
	private JComboBox instrumentComboBox;
	private JComboBox marketPlaceComboBox;

	private JComboBox dirComboBox;
	private JComboBox dirExpressionComboBox;
	private JTextField dirValueTextField;

	private JCheckBox emailCheckBox;
	private JComboBox emailComboBox;

	private JCheckBox phoneCheckBox;
	private JComboBox phoneComboBox;

	private JCheckBox melodyCheckBox;
	private JComboBox melodyComboBox;

	private JCheckBox notifyWindowCheckBox;

	private int TYPE;
	private static final int TYPE_CREATE = 0;
	private static final int TYPE_EDIT = 1;
}
