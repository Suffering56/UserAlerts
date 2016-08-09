package gui.panel.userAlerts.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.text.JTextComponent;

import gui.panel.userAlerts.data.combomodels.NewsExpressionComboModel;
import gui.panel.userAlerts.parent.SwixFrame;
import gui.panel.userAlerts.util.ComboBoxUtils;
import gui.panel.userAlerts.util.ComboBoxUtils.Handler;

public class EditNewsFrame extends SwixFrame {

	public EditNewsFrame() {
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
		ComboBoxUtils.addTextChangeListener(alertNameComboBox, new Handler() {
			@Override
			public void handle(JTextComponent textComponent) {
				System.out.println("docUpdateText: " + textComponent.getText());
			}
		});

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

	private void apply() {
		System.out.println("apply");
	}

	private JComboBox alertNameComboBox;
	private JCheckBox onlyRedNewsCheckBox;

	private JComboBox firstKeyWordComboBox;
	private JComboBox secondKeyWordComboBox;
	private JComboBox keyWordExpressionComboBox;

	private ButtonGroup keyWordButtonGroup;
	private JRadioButton byRelevanceRadioBtn;
	private JRadioButton exactMatchRadioBtn;

	private JComboBox firstExcludeWordComboBox;
	private JComboBox secondExcludeWordComboBox;
	private JComboBox excludeWordExpressionComboBox;

	private ButtonGroup excludeWordButtonGroup;
	private JRadioButton everywhereRadioBtn;
	private JRadioButton titlesOnlyRadioBtn;
	private JRadioButton redNewsOnlyRadioBtn;

	private JCheckBox emailCheckBox;
	private JCheckBox phoneCheckBox;
	private JCheckBox melodyCheckBox;
	private JCheckBox newsLineColorCheckBox;
	private JCheckBox popupWindowCheckBox;

	private JComboBox emailComboBox;
	private JComboBox phoneComboBox;
	private JComboBox melodyComboBox;

	public Action APPLY = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			if (e != null) {
				apply();
				dispose();
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
