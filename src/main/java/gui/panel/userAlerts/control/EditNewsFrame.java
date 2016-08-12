package gui.panel.userAlerts.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Calendar;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;

import gui.panel.userAlerts.data.NewsAlert;
import gui.panel.userAlerts.data.NewsAlert.Expression;
import gui.panel.userAlerts.data.NewsAlert.FilterExclude;
import gui.panel.userAlerts.data.NewsAlert.FilterKey;
import gui.panel.userAlerts.data.Stock;
import gui.panel.userAlerts.data.combomodels.NewsExpressionComboModel;
import gui.panel.userAlerts.parent.PrimaryFrame;
import gui.panel.userAlerts.parent.SwixFrame;
import gui.panel.userAlerts.util.ComboBoxUtils;

@SuppressWarnings({ "unused" })
public class EditNewsFrame extends SwixFrame {

	public EditNewsFrame(PrimaryFrame primaryFrame) {
		this(primaryFrame, null);
	}

	public EditNewsFrame(PrimaryFrame primaryFrame, NewsAlert alert) {
		this.primaryFrame = primaryFrame;
		this.stock = primaryFrame.getStock();
		if (alert == null) {
			this.alert = new NewsAlert();
			TYPE = TYPE_CREATE;
		} else {
			this.alert = alert;
			TYPE = TYPE_EDIT;
		}

		frame.setTitle("Настройка алерта для новостей");
		render("userAlerts/EditNewsFrame");
	}

	@Override
	protected void beforeRenderInit() {
		// do nothing
	}

	@Override
	protected void afterRenderInit() {
		initComboBoxModels();
		initComboBoxListeners();
		initCheckBoxListeners();
		initOtherListeners();

		if (TYPE == TYPE_EDIT) {
			fillFields();
		}
	}

	private void initComboBoxModels() {
		addComboItems(alert, false);

		for (NewsAlert alertItem : stock.getNewsAlertsList()) {
			if (alert != alertItem) {
				addComboItems(alertItem, true);
			}
		}
	}

	private void addComboItems(NewsAlert alertItem, boolean enableChecking) {
		ComboBoxUtils.addItem(alertNameComboBox, alertItem.getName(), enableChecking);

		ComboBoxUtils.addItem(firstKeyWordComboBox, alertItem.getFirstKeyWord(), enableChecking);
		ComboBoxUtils.addItem(secondKeyWordComboBox, alertItem.getSecondKeyWord(), enableChecking);

		ComboBoxUtils.addItem(firstExcludeWordComboBox, alertItem.getFirstExcludeWord(), enableChecking);
		ComboBoxUtils.addItem(secondExcludeWordComboBox, alertItem.getSecondExcludeWord(), enableChecking);

		ComboBoxUtils.addItem(emailComboBox, alertItem.getEmail(), enableChecking);
		ComboBoxUtils.addItem(phoneComboBox, alertItem.getPhoneSms(), enableChecking);
		ComboBoxUtils.addItem(melodyComboBox, alertItem.getMelody(), enableChecking);
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

	private void initCheckBoxListeners() {
		onlyRedNewsCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setOnlyRedNews();
			}
		});
	}

	private void initOtherListeners() {
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				primaryFrame.enable();
			}
		});
	}

	private void setOnlyRedNews() {
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
			secondKeyWordComboBox.setEnabled(false);
			secondExcludeWordComboBox.setEnabled(false);
		}
	}

	private void setSecondComboBoxState(JComboBox expressionBox, JComboBox secondBox) {
		boolean enabled = !expressionBox.getSelectedItem().equals(NewsExpressionComboModel.not);
		secondBox.setEnabled(enabled);
	}

	private void fillFields() {
		onlyRedNewsCheckBox.setSelected(alert.isOnlyRedNews());
		setOnlyRedNews();

		NewsExpressionComboModel.setValue(keyWordExpressionComboBox, alert.getKeyWordExpression());

		if (alert.getKeyWordFilterType() == FilterKey.BY_RELEVANCE) {
			byRelevanceRadioBtn.setSelected(true);
		} else {
			exactMatchRadioBtn.setSelected(true);
		}

		NewsExpressionComboModel.setValue(excludeWordExpressionComboBox, alert.getExcludeWordExpression());

		if (alert.getExcludeWordFilterType() == FilterExclude.EVERYWERE) {
			everywhereRadioBtn.setSelected(true);
		} else if (alert.getExcludeWordFilterType() == FilterExclude.TITLES_ONLY) {
			titlesOnlyRadioBtn.setSelected(true);
		} else {
			redNewsOnlyRadioBtn.setSelected(true);
		}

		emailCheckBox.setSelected(alert.isEmailOn());
		phoneCheckBox.setSelected(alert.isPhoneSmsOn());
		melodyCheckBox.setSelected(alert.isMelodyOn());
		newsColorCheckBox.setSelected(alert.isNewsColorOn());
		// set color
		windowPopupCheckBox.setSelected(alert.isWindowPopupOn());
	}

	private boolean apply() {
		boolean success = inputValidation();
		if (success) {
			fillAlert();

			if (TYPE == TYPE_CREATE) {
				primaryFrame.createAlert(alert);
			} else {
				primaryFrame.updateAlert(alert);
			}

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

	private void fillAlert() {
		alert.setName(ComboBoxUtils.getText(alertNameComboBox));
		alert.setOnlyRedNews(onlyRedNewsCheckBox.isSelected());

		alert.setFirstKeyWord(ComboBoxUtils.getText(firstKeyWordComboBox));
		alert.setSecondKeyWord(ComboBoxUtils.getText(secondKeyWordComboBox));

		Expression keyExpression = NewsExpressionComboModel.getExpressionValue(keyWordExpressionComboBox);
		alert.setKeyWordExpression(keyExpression);

		if (byRelevanceRadioBtn.isSelected()) {
			alert.setKeyWordFilterType(FilterKey.BY_RELEVANCE);
		} else {
			alert.setKeyWordFilterType(FilterKey.EXACT_MATCH);
		}

		alert.setFirstExcludeWord(ComboBoxUtils.getText(firstExcludeWordComboBox));
		alert.setSecondExcludeWord(ComboBoxUtils.getText(secondExcludeWordComboBox));
		Expression excludeExpression = NewsExpressionComboModel.getExpressionValue(excludeWordExpressionComboBox);
		alert.setExcludeWordExpression(excludeExpression);
		if (everywhereRadioBtn.isSelected()) {
			alert.setExcludeWordFilterType(FilterExclude.EVERYWERE);
		} else if (titlesOnlyRadioBtn.isSelected()) {
			alert.setExcludeWordFilterType(FilterExclude.TITLES_ONLY);
		} else {
			alert.setExcludeWordFilterType(FilterExclude.RED_ONLY);
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

		if (TYPE == TYPE_CREATE) {
			alert.setCreationDate(Calendar.getInstance().getTime().toLocaleString());
		}
	}

	private PrimaryFrame primaryFrame;
	private NewsAlert alert;
	private Stock stock;

	private int TYPE;
	private static final int TYPE_CREATE = 0;
	private static final int TYPE_EDIT = 1;

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
					primaryFrame.enable();
				}
			}
		}
	};

	public Action CANCEL = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			if (e != null) {
				dispose();
				primaryFrame.enable();
			}
		}
	};
}
