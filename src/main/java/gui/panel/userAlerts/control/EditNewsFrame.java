package gui.panel.userAlerts.control;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.tree.TreePath;

import gui.panel.userAlerts.data.ClientAlert;
import gui.panel.userAlerts.data.ClientNewsAlert;
import gui.panel.userAlerts.data.ClientQuotesAlert;
import gui.panel.userAlerts.overridden.model.AlertStatusComboModel;
import gui.panel.userAlerts.overridden.model.NewsExpressionComboModel;
import gui.panel.userAlerts.overridden.model.NewsTreeModel;
import gui.panel.userAlerts.overridden.model.NewsTreeNode;
import gui.panel.userAlerts.overridden.renderer.CheckableTreeRenderer;
import gui.panel.userAlerts.parent.CommonFrame;
import gui.panel.userAlerts.util.ExtendColorChooser;
import gui.panel.userAlerts.util.ExtendOptionPane;
import gui.panel.userAlerts.util.StringHelper;
import gui.panel.userAlerts.util.SwingHelper;
import p.alerts.client_api.NewsAlert.SEARCH_NEWS_TYPE;

@SuppressWarnings({ "serial" })
public class EditNewsFrame extends AbstractEditFrame implements Observer {

	public EditNewsFrame(CommonFrame primaryFrame) {
		this(primaryFrame, null);
	}

	public EditNewsFrame(CommonFrame primaryFrame, ClientNewsAlert alert) {
		super(primaryFrame);

		if (alert == null) {
			this.alert = new ClientNewsAlert();
			TYPE = Type.CREATE;
		} else {
			this.alert = alert;
			TYPE = Type.EDIT;
		}

		frame.setTitle("Настройка алерта для новостей");
		render("userAlerts/EditNewsFrame");

		primaryFrame.disable();
	}

	@Override
	public void update(Observable o, Object arg) {
		updateTreeModel();
		stock.deleteObserver(this);
	}

	@Override
	protected void afterRenderInit() {
		initCommonListeners();
		initListeners();
		fillComponentsFromAlert();
		updateTreeModel();
	}

	private void updateTreeModel() {
		NewsTreeNode root = (NewsTreeNode) stock.getNewsRoot();
		if (root != null) {
			root = root.clone();
			treeModel = new NewsTreeModel(root);
			tree.setModel(treeModel);
			tree.setCellRenderer(treeRenderer);

			if (TYPE == Type.EDIT) {
				treeModel.fillFromNewsLine(alert.getNewsLine());
			}

			SwingHelper.expandAllTreeNodes(tree, 0, tree.getRowCount());
			treeLoadingPanel.setVisible(false);
			treePanel.setVisible(true);
			tree.repaint();
			pack();
		} else {
			/**
			 * Если во время инициализации root == null (т.е. данные еще не
			 * загрузились с сервера) => Подписываемся на обновления.
			 */
			stock.addObserver(this);
		}
	}

	private void initListeners() {
		/**************************************************************
		 * Tree
		 **************************************************************/
		tree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				TreePath path = tree.getPathForLocation(e.getX(), e.getY());
				if (path != null) {
					if (path.getLastPathComponent() instanceof NewsTreeNode) {
						NewsTreeNode node = (NewsTreeNode) path.getLastPathComponent();
						node.setSelected(!node.isSelected());
						tree.repaint();
					}
				}
			}
		});

		tree.addTreeExpansionListener(new TreeExpansionListener() {
			@Override
			public void treeExpanded(TreeExpansionEvent e) {
				pack();
			}

			@Override
			public void treeCollapsed(TreeExpansionEvent e) {
				pack();
			}
		});

		/**************************************************************
		 * ComboBox
		 **************************************************************/
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

		/**************************************************************
		 * CheckBox
		 **************************************************************/
		onlyRedNewsCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setOnlyRedNews();
			}
		});

		newsColorTextField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				showColorChooser();
			}
		});

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				primaryFrame.enable();
			}
		});

		afterTriggerRemoveCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lifetimeTextField.setEnabled(!afterTriggerRemoveCheckBox.isSelected());
			}
		});
	}

	@Override
	protected void fillComponentsFromAlert() {
		addCommonComboItems(alert, false);
		addNewsComboItems(alert, false);

		for (ClientNewsAlert newsAlert : stock.getAllNewsAlerts()) {
			if (alert != newsAlert) {
				addCommonComboItems(newsAlert, true);
				addNewsComboItems(newsAlert, true);
			}
		}

		for (ClientQuotesAlert quotesAlert : stock.getAllQuotesAlerts()) {
			addCommonComboItems(quotesAlert, true);
		}

		addEmailPhoneAndMelodyComboItems();

		onlyRedNewsCheckBox.setSelected(alert.isOnlyRedNewsOn());
		setOnlyRedNews();

		NewsExpressionComboModel.setValue(keyWordExpressionComboBox, alert.getKeyWordExpression());
		NewsExpressionComboModel.setValue(excludeWordExpressionComboBox, alert.getExcludeWordExpression());

		if (alert.getEverywhereFilterType() == SEARCH_NEWS_TYPE.EVERYWERE) {
			everywhereRadioBtn.setSelected(true);
		} else if (alert.getEverywhereFilterType() == SEARCH_NEWS_TYPE.HEADLINES) {
			titlesOnlyRadioBtn.setSelected(true);
		} else {
			redNewsOnlyRadioBtn.setSelected(true);
		}

		emailCheckBox.setSelected(alert.isEmailOn());
		phoneCheckBox.setSelected(alert.isPhoneSmsOn());
		melodyCheckBox.setSelected(alert.isMelodyOn());
		
		emailCheckBoxListener.actionPerformed(null);
		melodyCheckBoxListener.actionPerformed(null);
		phoneCheckBoxListener.actionPerformed(null);

		newsColorCheckBox.setSelected(alert.getNewsColor() != null);
		newsColor = (alert.getNewsColor() == null) ? DEFAULT_NEWS_COLOR : alert.getNewsColor();
		newsColorTextField.setBackground(newsColor);
		notifyWindowCheckBox.setSelected(alert.isPopupWindowOn());

		keepHistoryCheckBox.setSelected(alert.isKeepHistory());
		afterTriggerRemoveCheckBox.setSelected(alert.isAfterTriggerRemove());
		lifetimeTextField.setText(alert.getLifetimeString());
		lifetimeTextField.setEnabled(!afterTriggerRemoveCheckBox.isSelected());
		AlertStatusComboModel.setValue(statusComboBox, alert.isStatusOn());
	}

	private void addCommonComboItems(ClientAlert alertItem, boolean isEmptyChecking) {
		SwingHelper.addComboItem(alertNameComboBox, alertItem.getName(), isEmptyChecking);
	}

	private void addNewsComboItems(ClientNewsAlert alertItem, boolean isEmptyChecking) {
		SwingHelper.addComboItem(firstKeyWordComboBox, alertItem.getFirstKeyWord(), isEmptyChecking);
		SwingHelper.addComboItem(secondKeyWordComboBox, alertItem.getSecondKeyWord(), isEmptyChecking);
		SwingHelper.addComboItem(firstExcludeWordComboBox, alertItem.getFirstExcludeWord(), isEmptyChecking);
		SwingHelper.addComboItem(secondExcludeWordComboBox, alertItem.getSecondExcludeWord(), isEmptyChecking);
	}

	@Override
	protected void fillAlertFromComponents() {
		alert.setName(SwingHelper.getComboText(alertNameComboBox));

		// записать данные из дерева
		if (treeModel != null) {
			alert.setNewsLine(treeModel.convertToNewsLine());
		}

		alert.setOnlyRedNewsOn(onlyRedNewsCheckBox.isSelected());

		alert.setFirstKeyWord(SwingHelper.getComboText(firstKeyWordComboBox));
		alert.setSecondKeyWord(SwingHelper.getComboText(secondKeyWordComboBox));
		alert.setKeyWordExpression(NewsExpressionComboModel.getExpressionValue(keyWordExpressionComboBox));

		alert.setFirstExcludeWord(SwingHelper.getComboText(firstExcludeWordComboBox));
		alert.setSecondExcludeWord(SwingHelper.getComboText(secondExcludeWordComboBox));
		alert.setExcludeWordExpression(NewsExpressionComboModel.getExpressionValue(excludeWordExpressionComboBox));

		if (everywhereRadioBtn.isSelected()) {
			alert.setEverywhereFilterType(SEARCH_NEWS_TYPE.EVERYWERE);
		} else if (titlesOnlyRadioBtn.isSelected()) {
			alert.setEverywhereFilterType(SEARCH_NEWS_TYPE.HEADLINES);
		} else {
			alert.setEverywhereFilterType(SEARCH_NEWS_TYPE.IMPORTANT_ONLY);
		}

		alert.setEmailOn(emailCheckBox.isSelected());
		alert.setEmail(emailComboBox.getSelectedItem().toString()); 
		alert.setPhoneSmsOn(phoneCheckBox.isSelected());
		alert.setPhoneSms(phoneComboBox.getSelectedItem().toString());
		alert.setMelodyOn(melodyCheckBox.isSelected());
		alert.setMelody(melodyComboBox.getSelectedItem().toString());

		alert.setNewsColor(!newsColorCheckBox.isSelected() ? null : newsColor);

		alert.setPopupWindowOn(notifyWindowCheckBox.isSelected());

		alert.setKeepHistory(keepHistoryCheckBox.isSelected());
		alert.setAfterTriggerRemove(afterTriggerRemoveCheckBox.isSelected());
		alert.setLifetime(lifetimeTextField.getText());
		alert.setStatusOn(AlertStatusComboModel.getBooleanValue(statusComboBox));
	}

	private void setOnlyRedNews() {
		final boolean enabled = !onlyRedNewsCheckBox.isSelected();

		firstKeyWordComboBox.setEnabled(enabled);
		keyWordExpressionComboBox.setEnabled(enabled);
		firstExcludeWordComboBox.setEnabled(enabled);
		excludeWordExpressionComboBox.setEnabled(enabled);

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

	public Action CHOOSE_COLOR = new AbstractAction() {
		@Override
		public void actionPerformed(ActionEvent e) {
			showColorChooser();
		}
	};

	private void showColorChooser() {
		ExtendColorChooser chooser = new ExtendColorChooser();
		Color resultColor = chooser.showBasicLookAndFeelDialog(null, "Выберите цвет для строки новости", newsColor);
		if (resultColor != null) {
			newsColor = resultColor;
			newsColorTextField.setBackground(newsColor);
		}
	}

	public Action APPLY = new AbstractAction() {
		private boolean inputValidation() {
			errorText = "Пожалуйста, заполните все обязательные поля.";

			if (SwingHelper.isEmptyComboText(alertNameComboBox))
				return false;

			if (firstKeyWordComboBox.isEnabled() && SwingHelper.isEmptyComboText(firstKeyWordComboBox))
				return false;
			if (secondKeyWordComboBox.isEnabled() && SwingHelper.isEmptyComboText(secondKeyWordComboBox))
				return false;
			if (secondExcludeWordComboBox.isEnabled() && SwingHelper.isEmptyComboText(firstExcludeWordComboBox))
				return false;
			if (secondExcludeWordComboBox.isEnabled() && SwingHelper.isEmptyComboText(secondExcludeWordComboBox))
				return false;

			if (treeModel == null || treeModel.convertToNewsLine().equals(StringHelper.EMPTY)) {
				return false;
			}

			try {
				Integer.valueOf(lifetimeTextField.getText());
			} catch (NumberFormatException e) {
				errorText = "Некорректное значение поля \"Количество срабатываний\" (должно быть целым числом).";
				return false;
			}

			return true;
		}

		/**
		 * APPLY
		 */
		public void actionPerformed(ActionEvent e) {
			if (inputValidation()) {

				fillAlertFromComponents();

				if (TYPE == Type.CREATE) {
					primaryFrame.createNewsAlert(alert);
				} else {
					primaryFrame.updateNewsAlert(alert);
				}

				dispose();
				primaryFrame.enable();
			} else {
				new ExtendOptionPane().showBasicLookAndFeelMessageError(errorText, "Validation error!");
			}
		}
	};

	private ClientNewsAlert alert;

	private JCheckBox onlyRedNewsCheckBox;

	private JComboBox firstKeyWordComboBox;
	private JComboBox secondKeyWordComboBox;
	private JComboBox keyWordExpressionComboBox;

	private JComboBox firstExcludeWordComboBox;
	private JComboBox secondExcludeWordComboBox;
	private JComboBox excludeWordExpressionComboBox;

	private JRadioButton everywhereRadioBtn;
	private JRadioButton titlesOnlyRadioBtn;
	private JRadioButton redNewsOnlyRadioBtn;

	private JCheckBox newsColorCheckBox;
	private JTextField newsColorTextField;
	private Color newsColor;

	private JTextField lifetimeTextField;

	private JTree tree;
	private NewsTreeModel treeModel;
	private JPanel treePanel;
	private JPanel treeLoadingPanel;
	private final CheckableTreeRenderer treeRenderer = new CheckableTreeRenderer();
	private String errorText;

	private static final Color DEFAULT_NEWS_COLOR = Color.YELLOW;
}
