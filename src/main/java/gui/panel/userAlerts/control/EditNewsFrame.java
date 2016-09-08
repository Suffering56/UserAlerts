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
import gui.panel.userAlerts.data.ClientNewsAlert.RelevanceFilterType;
import gui.panel.userAlerts.overridden.model.NewsExpressionComboModel;
import gui.panel.userAlerts.overridden.model.NewsTreeModel;
import gui.panel.userAlerts.overridden.model.NewsTreeNode;
import gui.panel.userAlerts.overridden.renderer.CheckableTreeRenderer;
import gui.panel.userAlerts.parent.AbstractEditFrame;
import gui.panel.userAlerts.parent.PrimaryFrame;
import gui.panel.userAlerts.util.ExtendColorChooser;
import gui.panel.userAlerts.util.ExtendOptionPane;
import gui.panel.userAlerts.util.StringHelper;
import gui.panel.userAlerts.util.SwingHelper;
import p.alerts.client_api.NewsAlert.SEARCH_NEWS_TYPE;

@SuppressWarnings({ "serial" })
public class EditNewsFrame extends AbstractEditFrame implements Observer {

	public EditNewsFrame(PrimaryFrame primaryFrame) {
		this(primaryFrame, null);
	}

	public EditNewsFrame(PrimaryFrame primaryFrame, ClientNewsAlert alert) {
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
	}

	@Override
	protected void fillComponentsFromAlert() {
		addCommonComboItems(alert, false);
		addUniqueComboItems(alert, false);

		for (ClientAlert commonAlert : stock.getAllAlerts()) {
			if (alert != commonAlert) {
				addCommonComboItems(commonAlert, true);
				if (commonAlert instanceof ClientNewsAlert) {
					ClientNewsAlert newsAlert = (ClientNewsAlert) commonAlert;
					addUniqueComboItems(newsAlert, true);
				}
			}
		}

		onlyRedNewsCheckBox.setSelected(alert.isOnlyRedNewsOn());
		setOnlyRedNews();

		NewsExpressionComboModel.setValue(keyWordExpressionComboBox, alert.getKeyWordExpression());

		if (alert.getRelevanceFilterType() == RelevanceFilterType.BY_RELEVANCE) {
			byRelevanceRadioBtn.setSelected(true);
		} else {
			exactMatchRadioBtn.setSelected(true);
		}

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
		newsColorCheckBox.setSelected(alert.getNewsColor() != null);
		notifyWindowCheckBox.setSelected(alert.isPopupWindowOn());

		newsColor = (alert.getNewsColor() == null) ? DEFAULT_NEWS_COLOR : alert.getNewsColor();
		newsColorTextField.setBackground(newsColor);
	}

	@Override
	protected void fillAlertFromComponents() {
		alert.setName(SwingHelper.getComboText(alertNameComboBox));
		alert.setOnlyRedNewsOn(onlyRedNewsCheckBox.isSelected());

		alert.setFirstKeyWord(SwingHelper.getComboText(firstKeyWordComboBox));
		alert.setSecondKeyWord(SwingHelper.getComboText(secondKeyWordComboBox));
		alert.setKeyWordExpression(NewsExpressionComboModel.getExpressionValue(keyWordExpressionComboBox));

		alert.setFirstExcludeWord(SwingHelper.getComboText(firstExcludeWordComboBox));
		alert.setSecondExcludeWord(SwingHelper.getComboText(secondExcludeWordComboBox));
		alert.setExcludeWordExpression(NewsExpressionComboModel.getExpressionValue(excludeWordExpressionComboBox));

		if (byRelevanceRadioBtn.isSelected()) {
			alert.setRelevanceFilterType(RelevanceFilterType.BY_RELEVANCE);
		} else {
			alert.setRelevanceFilterType(RelevanceFilterType.EXACT_MATCH);
		}

		if (everywhereRadioBtn.isSelected()) {
			alert.setEverywhereFilterType(SEARCH_NEWS_TYPE.EVERYWERE);
		} else if (titlesOnlyRadioBtn.isSelected()) {
			alert.setEverywhereFilterType(SEARCH_NEWS_TYPE.HEADLINES);
		} else {
			alert.setEverywhereFilterType(SEARCH_NEWS_TYPE.IMPORTANT_ONLY);
		}

		alert.setEmailOn(emailCheckBox.isSelected());
		alert.setEmail(SwingHelper.getComboText(emailComboBox));

		alert.setPhoneSmsOn(phoneCheckBox.isSelected());
		alert.setPhoneSms(SwingHelper.getComboText(phoneComboBox));

		alert.setMelodyOn(melodyCheckBox.isSelected());
		alert.setMelody(SwingHelper.getComboText(melodyComboBox));

		alert.setNewsColor(!newsColorCheckBox.isSelected() ? null : newsColor);

		alert.setPopupWindowOn(notifyWindowCheckBox.isSelected());

		// записать данные из дерева
		if (treeModel != null) {
			alert.setNewsLine(treeModel.convertToNewsLine());
		}
	}

	private void addUniqueComboItems(ClientNewsAlert alertItem, boolean isEmptyChecking) {
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
						primaryFrame.createNewsAlert(alert);
					} else {
						primaryFrame.updateNewsAlert(alert);
					}

					dispose();
					primaryFrame.enable();
				} else {
					new ExtendOptionPane().showBasicLookAndFeelMessageError("Заполните обязательные поля!", "Validation error!");
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

		if (treeModel == null || treeModel.convertToNewsLine().equals(StringHelper.EMPTY)) {
			return false;
		}

		return true;
	}

	private ClientNewsAlert alert;

	private JCheckBox onlyRedNewsCheckBox;

	private JComboBox firstKeyWordComboBox;
	private JComboBox secondKeyWordComboBox;
	private JComboBox keyWordExpressionComboBox;

	private JComboBox firstExcludeWordComboBox;
	private JComboBox secondExcludeWordComboBox;
	private JComboBox excludeWordExpressionComboBox;

	private JRadioButton byRelevanceRadioBtn;
	private JRadioButton exactMatchRadioBtn;

	private JRadioButton everywhereRadioBtn;
	private JRadioButton titlesOnlyRadioBtn;
	private JRadioButton redNewsOnlyRadioBtn;

	private JCheckBox newsColorCheckBox;
	private JTextField newsColorTextField;
	private Color newsColor;

	private JTree tree;
	private NewsTreeModel treeModel;
	private JPanel treePanel;
	private JPanel treeLoadingPanel;
	private final CheckableTreeRenderer treeRenderer = new CheckableTreeRenderer();

	private static final Color DEFAULT_NEWS_COLOR = Color.YELLOW;
}
