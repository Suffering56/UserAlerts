package gui.panel.userAlerts.control;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import gui.panel.userAlerts.App;
import gui.panel.userAlerts.constants.AlertsGeneralConstants;
import gui.panel.userAlerts.data.NewsAlert;
import gui.panel.userAlerts.data.NewsAlert.Expression;
import gui.panel.userAlerts.data.NewsAlert.FilterExclude;
import gui.panel.userAlerts.data.NewsAlert.FilterKey;
import gui.panel.userAlerts.overridden.model.NewsExpressionComboModel;
import gui.panel.userAlerts.overridden.model.NewsTreeModel;
import gui.panel.userAlerts.overridden.model.NewsTreeNode;
import gui.panel.userAlerts.overridden.renderer.CheckableTreeRenderer;
import gui.panel.userAlerts.data.Stock;
import gui.panel.userAlerts.parent.PrimaryFrame;
import gui.panel.userAlerts.parent.SwixFrame;
import gui.panel.userAlerts.parent.TreeUpdateListener;
import gui.panel.userAlerts.remote.NewsTreeDownloader;
import gui.panel.userAlerts.util.ExtendColorChooser;
import gui.panel.userAlerts.util.IOHelper;
import gui.panel.userAlerts.util.SwingHelper;

@SuppressWarnings({ "serial", "unused", "deprecation" })
public class EditNewsFrame extends SwixFrame implements TreeUpdateListener {

	public EditNewsFrame(PrimaryFrame primaryFrame) {
		this(primaryFrame, null);
	}

	public EditNewsFrame(PrimaryFrame primaryFrame, NewsAlert alert) {
		this.primaryFrame = primaryFrame;
		stock = primaryFrame.getStock();

		if (alert == null) {
			this.alert = new NewsAlert();
			TYPE = TYPE_CREATE;
		} else {
			this.alert = alert;
			TYPE = TYPE_EDIT;
		}

		frame.setTitle("Настройка алерта для новостей");
		render("userAlerts/EditNewsFrame");

		primaryFrame.disable();
	}

	@Override
	protected void beforeRenderInit() {
	}

	@Override
	protected void afterRenderInit() {
		tree.setCellRenderer(treeRenderer);

		initComboBoxModels();
		initComboBoxListeners();
		initCheckBoxListeners();
		initTreeListeners();
		initOtherListeners();

		if (TYPE == TYPE_EDIT) {
			extractAlertData();
		}

		if (newsColor == null) {
			newsColorTextField.setBackground(AlertsGeneralConstants.NULL_COLOR);
		} else {
			newsColorTextField.setBackground(newsColor);
		}

		updateTreeModel();
	}

	@Override
	/**
	 * Эта функция ждет, когда данные придут и обновляет модель дерева. На
	 * случай если на момент открытия этой формы - данные для дерева (tree) еще
	 * не были получены с сервера.
	 */
	public void treeUpdateEvent() {
		updateTreeModel();
	}

	void updateTreeModel() {
		NewsTreeNode root = (NewsTreeNode) stock.getNewsRootNode();
		if (root != null) {
			/**
			 * Выключаем ожидающий поток updateTreeEvent(), чтобы лишний раз не
			 * перерисовывать дерево, если в момент первой отрисовки данные для
			 * него уже были загружены.
			 */
			stock.stopUpdateNewsTree();

			root = root.clone();
			NewsTreeModel model = new NewsTreeModel(root);
			tree.setModel(model);

			if (TYPE == TYPE_EDIT) {
				model.fillFromNewsLine(alert.getNewsLine());
			}
			showTree();
		} else {
			/**
			 * Если во время создания формы данные для дерева категорий новостей
			 * (tree) еще не были загружены - запускаем слушатель.
			 */
			stock.setNewsTreeUpdateListener(this);
		}
	}

	private void showTree() {
		// SwingHelper.expandAllTreeNodes(tree, 0, tree.getRowCount());
		treeLoadingPanel.setVisible(false);
		treePanel.setVisible(true);
		tree.repaint();
		pack();
	}

	private void initTreeListeners() {
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

		tree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				TreePath path = tree.getPathForLocation(e.getX(), e.getY());
				if (path != null) {
					NewsTreeNode node = (NewsTreeNode) path.getLastPathComponent();
					node.setSelected(!node.isSelected());
					tree.repaint();
				}
			}
		});
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
		SwingHelper.addUniqueComboItem(alertNameComboBox, alertItem.getName(), enableChecking);

		SwingHelper.addUniqueComboItem(firstKeyWordComboBox, alertItem.getFirstKeyWord(), enableChecking);
		SwingHelper.addUniqueComboItem(secondKeyWordComboBox, alertItem.getSecondKeyWord(), enableChecking);

		SwingHelper.addUniqueComboItem(firstExcludeWordComboBox, alertItem.getFirstExcludeWord(), enableChecking);
		SwingHelper.addUniqueComboItem(secondExcludeWordComboBox, alertItem.getSecondExcludeWord(), enableChecking);

		SwingHelper.addUniqueComboItem(emailComboBox, alertItem.getEmail(), enableChecking);
		SwingHelper.addUniqueComboItem(phoneComboBox, alertItem.getPhoneSms(), enableChecking);
		SwingHelper.addUniqueComboItem(melodyComboBox, alertItem.getMelody(), enableChecking);
	}

	private void initComboBoxListeners() {
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

		newsColorTextField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				showColorChooser();
			}
		});
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

	private void setSecondComboBoxState(JComboBox expressionBox, JComboBox secondBox) {
		boolean enabled = !expressionBox.getSelectedItem().equals(NewsExpressionComboModel.not);
		secondBox.setEnabled(enabled);
	}

	private void extractAlertData() {
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

		newsColor = alert.getNewsColor();
		notifyWindowCheckBox.setSelected(alert.isWindowPopupOn());
	}

	private void setAlertData() {
		alert.setName(SwingHelper.getComboText(alertNameComboBox));
		alert.setOnlyRedNews(onlyRedNewsCheckBox.isSelected());

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

		alert.setWindowPopupOn(notifyWindowCheckBox.isSelected());

		// записать данные из дерева
		TreeModel model = tree.getModel();
		if (model != null && model instanceof NewsTreeModel) {
			NewsTreeModel newsTreeModel = (NewsTreeModel) model;
			alert.setNewsLine(newsTreeModel.convertToNewsLine());
		}

		if (TYPE == TYPE_CREATE) {
			alert.setCreationDate(Calendar.getInstance().getTime().toLocaleString());
		}
	}

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

	private boolean apply() {
		boolean success = inputValidation();
		if (success) {
			setAlertData();

			if (TYPE == TYPE_CREATE) {
				primaryFrame.createAlert(alert);
			} else {
				primaryFrame.updateAlert(alert);
			}

		} else {
			App.appLogger.info("error validation");
		}
		return success;
	}

	private PrimaryFrame primaryFrame;
	private NewsAlert alert;
	private Stock stock;

	private int TYPE;
	private static final int TYPE_CREATE = 0;
	private static final int TYPE_EDIT = 1;

	private JTree tree;
	private JPanel treePanel;
	private JPanel treeLoadingPanel;
	private CheckableTreeRenderer treeRenderer = new CheckableTreeRenderer();

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
	private JTextField newsColorTextField;
	private Color newsColor;

	private JCheckBox notifyWindowCheckBox;

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
}
