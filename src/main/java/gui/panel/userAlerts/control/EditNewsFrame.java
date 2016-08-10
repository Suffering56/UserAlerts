package gui.panel.userAlerts.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;

import gui.panel.userAlerts.data.AlertEntity;
import gui.panel.userAlerts.data.combomodels.NewsExpressionComboModel;
import gui.panel.userAlerts.parent.PrimaryFrame;
import gui.panel.userAlerts.parent.SwixFrame;
import gui.panel.userAlerts.util.ComboBoxUtils;

public class EditNewsFrame extends SwixFrame {

	public EditNewsFrame(PrimaryFrame primaryFrame) {
		this(primaryFrame, new AlertEntity(AlertEntity.TYPE_NEWS));
	}

	public EditNewsFrame(PrimaryFrame primaryFrame, AlertEntity alert) {
		this.primaryFrame = primaryFrame;
		this.alert = alert;

		frame.setTitle("Настройка алерта для новостей");
		render("userAlerts/EditNewsFrame");
	}

	@Override
	protected void beforeRenderInit() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void afterRenderInit() {
		initComboBoxListeners();

		onlyRedNewsCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean enabled = !onlyRedNewsCheckBox.isSelected();
				firstKeyWordComboBox.setEnabled(enabled);
				keyWordExpressionComboBox.setEnabled(enabled);
				firstExcludeWordComboBox.setEnabled(enabled);
				excludeWordExpressionComboBox.setEnabled(enabled);

				byRelevanceRadioBtn.setEnabled(enabled);
				exactMatchRadioBtn.setEnabled(enabled);
				everywhereRadioBtn.setEnabled(enabled);
				titlesOnlyRadioBtn.setEnabled(enabled);
				redNewsOnlyRadioBtn.setEnabled(enabled);

				if (enabled) {
					setSecondComboBoxState(keyWordExpressionComboBox, secondKeyWordComboBox);
					setSecondComboBoxState(excludeWordExpressionComboBox, secondExcludeWordComboBox);
				} else {
					secondKeyWordComboBox.setEnabled(enabled);
					secondExcludeWordComboBox.setEnabled(enabled);
				}
			}
		});

	}

	private void initComboBoxListeners() {
		// ComboBoxUtils.addTextChangeListener(alertNameComboBox, new Handler()
		// {
		// @Override
		// public void handle(JTextComponent textComponent) {
		// System.out.println("docUpdateText: " + textComponent.getText());
		// }
		// });

		keyWordExpressionComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setSecondComboBoxState(keyWordExpressionComboBox, secondKeyWordComboBox);
			}
		});

		excludeWordExpressionComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setSecondComboBoxState(excludeWordExpressionComboBox, secondExcludeWordComboBox);
			}
		});
	}

	private boolean setSecondComboBoxState(JComboBox expressionBox, JComboBox secondBox) {
		if (expressionBox.getSelectedItem().equals(NewsExpressionComboModel.not)) {
			secondBox.setEnabled(false);
		} else {
			secondBox.setEnabled(true);
		}
		return secondBox.isEnabled();
	}

	private boolean apply() {
		boolean success = inputValidation();
		if (success) {
			System.out.println("success validation");
			AlertEntity alert = createAlert();
			primaryFrame.addAlert(alert);
		} else {
			System.out.println("error validation");
		}
		return success;
	}
	
	private boolean inputValidation() {
		if (ComboBoxUtils.isEmpty(alertNameComboBox)) {
			return false;
		}

		if (firstKeyWordComboBox.isEnabled() && ComboBoxUtils.isEmpty(firstKeyWordComboBox)) {
			return false;
		}
		if (secondKeyWordComboBox.isEnabled() && ComboBoxUtils.isEmpty(secondKeyWordComboBox)) {
			return false;
		}
		if (secondExcludeWordComboBox.isEnabled() && ComboBoxUtils.isEmpty(firstExcludeWordComboBox)) {
			return false;
		}
		if (secondExcludeWordComboBox.isEnabled() && ComboBoxUtils.isEmpty(secondExcludeWordComboBox)) {
			return false;
		}

		if (emailCheckBox.isSelected()) {
			if (ComboBoxUtils.isEmpty(emailComboBox)) {
				return false;
			}
		}
		if (phoneCheckBox.isSelected()) {
			if (ComboBoxUtils.isEmpty(phoneComboBox)) {
				return false;
			}
		}
		if (melodyCheckBox.isSelected()) {
			if (ComboBoxUtils.isEmpty(melodyComboBox)) {
				return false;
			}
		}

		return true;
	}

	private AlertEntity createAlert() {
		alert.setName(ComboBoxUtils.getText(alertNameComboBox));
		alert.setOnlyRedNews(onlyRedNewsCheckBox.isSelected());

		alert.setFirstKeyWord(ComboBoxUtils.getText(firstKeyWordComboBox));
		alert.setSecondKeyWord(ComboBoxUtils.getText(secondKeyWordComboBox));

		String keyExpression = NewsExpressionComboModel.getNormalValue(keyWordExpressionComboBox);
		alert.setKeyWordExpression(keyExpression);

		if (byRelevanceRadioBtn.isSelected()) {
			alert.setKeyWordFilterType(AlertEntity.KEY_FILTER_BY_RELEVANCE);
		} else {
			alert.setKeyWordFilterType(AlertEntity.KEY_FILTER_EXACT_MATCH);
		}

		alert.setFirstExcludeWord(ComboBoxUtils.getText(firstExcludeWordComboBox));
		alert.setSecondExcludeWord(ComboBoxUtils.getText(secondExcludeWordComboBox));
		String excludeExpression = NewsExpressionComboModel.getNormalValue(excludeWordExpressionComboBox);
		alert.setExcludeWordExpression(excludeExpression);
		if (everywhereRadioBtn.isSelected()) {
			alert.setExcludeWordFilterType(AlertEntity.EXCLUDE_FILTER_EVERYWHERE);
		} else if (titlesOnlyRadioBtn.isSelected()) {
			alert.setExcludeWordFilterType(AlertEntity.EXCLUDE_FILTER_TITLES_ONLY);
		} else {
			alert.setExcludeWordFilterType(AlertEntity.EXCLUDE_FILTER_RED_NEWS_ONLY);
		}

		alert.setEmailOn(emailCheckBox.isSelected());
		alert.setEmail(ComboBoxUtils.getText(emailComboBox));

		alert.setPhoneSmsOn(phoneCheckBox.isSelected());
		alert.setPhoneSms(ComboBoxUtils.getText(phoneComboBox));

		alert.setMelodyOn(melodyCheckBox.isSelected());
		alert.setMelody(ComboBoxUtils.getText(melodyComboBox));

		alert.setNewsColorOn(newsColorCheckBox.isEnabled());
		alert.setNewsColor("Пока не реализовано");

		alert.setWindowPopupOn(windowPopupCheckBox.isSelected());

		return alert;
	}

	private PrimaryFrame primaryFrame;
	private AlertEntity alert;

	private JComboBox alertNameComboBox;
	private JCheckBox onlyRedNewsCheckBox;

	private JComboBox firstKeyWordComboBox;
	private JComboBox secondKeyWordComboBox;
	private JComboBox keyWordExpressionComboBox;

	private JRadioButton byRelevanceRadioBtn;
	private JRadioButton exactMatchRadioBtn;

	private JComboBox firstExcludeWordComboBox;
	private JComboBox secondExcludeWordComboBox;
	private JComboBox excludeWordExpressionComboBox;

	private JRadioButton everywhereRadioBtn;
	private JRadioButton titlesOnlyRadioBtn;
	private JRadioButton redNewsOnlyRadioBtn;

	private JCheckBox emailCheckBox;
	private JComboBox emailComboBox;

	private JCheckBox phoneCheckBox;
	private JComboBox phoneComboBox;

	private JCheckBox melodyCheckBox;
	private JComboBox melodyComboBox;

	private JCheckBox newsColorCheckBox;
	private JButton newsColorChooseBtn;

	private JCheckBox windowPopupCheckBox;

	public Action APPLY = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			if (e != null) {
				boolean success = apply();
				if (success) {
					dispose();
				}
			}
		}
	};

	public Action CANCEL = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			if (e != null) {
				dispose();
			}
		}
	};
}
