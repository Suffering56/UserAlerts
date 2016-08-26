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
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import gui.panel.userAlerts.App;
import gui.panel.userAlerts.data.Alert;
import gui.panel.userAlerts.data.NewsAlert;
import gui.panel.userAlerts.data.NewsAlert.Expression;
import gui.panel.userAlerts.data.NewsAlert.FilterExclude;
import gui.panel.userAlerts.data.NewsAlert.FilterKey;
import gui.panel.userAlerts.overridden.model.NewsExpressionComboModel;
import gui.panel.userAlerts.overridden.model.NewsTreeModel;
import gui.panel.userAlerts.overridden.model.NewsTreeNode;
import gui.panel.userAlerts.overridden.renderer.CheckableTreeRenderer;
import gui.panel.userAlerts.parent.AbstractEditFrame;
import gui.panel.userAlerts.parent.PrimaryFrame;
import gui.panel.userAlerts.util.ExtendColorChooser;
import gui.panel.userAlerts.util.SwingHelper;

@SuppressWarnings({ "serial" })
public class EditNewsFrame extends AbstractEditFrame implements Observer {

	public EditNewsFrame(PrimaryFrame primaryFrame) {
		this(primaryFrame, null);
	}

	public EditNewsFrame(PrimaryFrame primaryFrame, NewsAlert alert) {
		super(primaryFrame);

		if (alert == null) {
			this.alert = new NewsAlert();
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
		initListeners();
		fillComponentsFromAlert();
		updateTreeModel();
	}

	private void updateTreeModel() {
		NewsTreeNode root = (NewsTreeNode) stock.getNewsRoot();
		if (root != null) {
			root = root.clone();
			NewsTreeModel model = new NewsTreeModel(root);
			tree.setModel(model);
			tree.setCellRenderer(treeRenderer);

			if (TYPE == Type.EDIT) {
				model.fillFromNewsLine(alert.getNewsLine());
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
	}

	@Override
	protected void fillComponentsFromAlert() {
		addCommonComboItems(alert, false);
		addUniqueComboItems(alert, false);

		for (Alert commonAlert : stock.getAllAlerts()) {
			if (alert != commonAlert) {
				addCommonComboItems(commonAlert, true);
				if (commonAlert instanceof NewsAlert) {
					NewsAlert newsAlert = (NewsAlert) commonAlert;
					addUniqueComboItems(newsAlert, true);
				}
			}
		}

		onlyRedNewsCheckBox.setSelected(alert.isOnlyRedNewsOn());
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
		notifyWindowCheckBox.setSelected(alert.isNotifyWindowOn());

		newsColor = alert.getNewsColor();
		newsColorTextField.setBackground(newsColor);
	}

	@Override
	protected void fillAlertFromComponents() {
		alert.setName(SwingHelper.getComboText(alertNameComboBox));
		alert.setOnlyRedNewsOn(onlyRedNewsCheckBox.isSelected());

		alert.setFirstKeyWord(SwingHelper.getComboText(firstKeyWordComboBox));
		alert.setSecondKeyWord(SwingHelper.getComboText(secondKeyWordComboBox));

		Expression keyExpression = NewsExpressionComboModel.getExpressionValue(keyWordExpressionComboBox);
		alert.setKeyWordExpression(keyExpression);

		if (byRelevanceRadioBtn.isSelected()) {
			alert.setKeyWordFilterType(FilterKey.BY_RELEVANCE);
		} else {
			alert.setKeyWordFilterType(FilterKey.EXACT_MATCH);
		}

		alert.setFirstExcludeWord(SwingHelper.getComboText(firstExcludeWordComboBox));
		alert.setSecondExcludeWord(SwingHelper.getComboText(secondExcludeWordComboBox));
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
		alert.setEmail(SwingHelper.getComboText(emailComboBox));

		alert.setPhoneSmsOn(phoneCheckBox.isSelected());
		alert.setPhoneSms(SwingHelper.getComboText(phoneComboBox));

		alert.setMelodyOn(melodyCheckBox.isSelected());
		alert.setMelody(SwingHelper.getComboText(melodyComboBox));

		alert.setNewsColorOn(newsColorCheckBox.isEnabled());
		alert.setNewsColor(newsColor);

		alert.setNotifyWindowOn(notifyWindowCheckBox.isSelected());

		// записать данные из дерева
		TreeModel model = tree.getModel();
		if (model != null && model instanceof NewsTreeModel) {
			NewsTreeModel newsTreeModel = (NewsTreeModel) model;
			alert.setNewsLine(newsTreeModel.convertToNewsLine());
		}
	}

	private void addUniqueComboItems(NewsAlert alertItem, boolean isEmptyChecking) {
		SwingHelper.addComboItem(firstKeyWordComboBox, alertItem.getFirstKeyWord(), isEmptyChecking);
		SwingHelper.addComboItem(secondKeyWordComboBox, alertItem.getSecondKeyWord(), isEmptyChecking);
		SwingHelper.addComboItem(firstExcludeWordComboBox, alertItem.getFirstExcludeWord(), isEmptyChecking);
		SwingHelper.addComboItem(secondExcludeWordComboBox, alertItem.getSecondExcludeWord(), isEmptyChecking);
	}

	private void setOnlyRedNews() {
		final boolean enabled = !onlyRedNewsCheckBox.isSelected();

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
		public void actionPerformed(ActionEvent e) {
			if (e != null) {
				if (inputValidation()) {

					fillAlertFromComponents();

					if (TYPE == Type.CREATE) {
						primaryFrame.createAlert(alert);
					} else {
						primaryFrame.updateAlert(alert);
					}

					dispose();
					primaryFrame.enable();
				} else {
					App.appLogger.info("error validation");
				}
			}
		}
	};

	private boolean inputValidation() {
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

		if (emailCheckBox.isSelected())
			if (SwingHelper.isEmptyComboText(emailComboBox))
				return false;
		if (phoneCheckBox.isSelected())
			if (SwingHelper.isEmptyComboText(phoneComboBox))
				return false;
		if (melodyCheckBox.isSelected())
			if (SwingHelper.isEmptyComboText(melodyComboBox))
				return false;

		return true;
	}

	private NewsAlert alert;

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

	private JCheckBox newsColorCheckBox;
	private JTextField newsColorTextField;
	private Color newsColor;

	private JTree tree;
	private JPanel treePanel;
	private JPanel treeLoadingPanel;
	private CheckableTreeRenderer treeRenderer = new CheckableTreeRenderer();
}
