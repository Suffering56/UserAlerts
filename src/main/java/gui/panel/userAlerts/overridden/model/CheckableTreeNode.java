package gui.panel.userAlerts.overridden.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.TreeNode;

public class CheckableTreeNode implements TreeNode, Cloneable {

	public CheckableTreeNode(String text) {
		this.text = text;
	}

	@Override
	public TreeNode getChildAt(int childIndex) {
		return childNodesList.get(childIndex);
	}

	@Override
	public int getChildCount() {
		return childNodesList.size();
	}

	@Override
	public int getIndex(TreeNode node) {
		for (int i = 0; i < childNodesList.size(); i++) {
			TreeNode item = childNodesList.get(i);
			if (item == node) {
				return i;
			}
		}
		return -1;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public Enumeration children() {
		return Collections.enumeration(childNodesList);
	}

	public void add(CheckableTreeNode childNode) {
		if (allowsChildren) {
			childNodesList.add(childNode);
			childNode.setParent(this);
			leaf = false;
			updateLevel(childNode);
		}
	}

	@Override
	public CheckableTreeNode getParent() {
		return parent;
	}

	public void setParent(CheckableTreeNode parent) {
		this.parent = parent;
	}

	@Override
	public boolean getAllowsChildren() {
		return allowsChildren;
	}

	public void setAllowsChildren(boolean allowsChildren) {
		this.allowsChildren = allowsChildren;
	}

	@Override
	public boolean isLeaf() {
		return leaf;
	}

	public String getText() {
		return text;
	}

	public boolean isSelected() {
		return selected;
	}

	/**
	 * Помимо установки значения selected у текущего объекта - устанавливает
	 * аналогичные значения selected у дочерних элементов (и далее -
	 * рекурсивно).
	 * 
	 * @param selected
	 */
	public void setSelected(boolean selected) {
		for (CheckableTreeNode child : childNodesList) {
			child.setSelected(selected);
		}

		setSelectedWithoutChildActions(selected);
	}

	/**
	 * Устанавливает значение поля selected у текущего объекта. А так же
	 * проверяет всех братьев и устанавливает значение selected у родителя в
	 * true, если selected ВСЕХ "братьев" тоже равен true. Если хотя бы один
	 * "брат" не выбран, то ставит selected родителя в false.
	 * 
	 * @param selected
	 */
	private void setSelectedWithoutChildActions(boolean selected) {
		this.selected = selected;

		if (parent != null) {

			int counter = 0;
			for (CheckableTreeNode brother : parent.getChildNodesList()) {
				if (brother.isSelected()) {
					counter++;
				}
			}

			if (counter == parent.getChildNodesList().size()) {
				parent.setSelectedWithoutChildActions(true);
			} else {
				parent.setSelectedWithoutChildActions(false);
			}
		}
	}

	public List<CheckableTreeNode> getChildNodesList() {
		return childNodesList;
	}

	@Override
	public String toString() {
		return text;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * Создает список с названиями (поле text) выбранных (selected) в дереве
	 * элементов. РЕКУРСИЯ!
	 * 
	 * @param node
	 *            - корневой элемент дерева (root node)
	 * @param topicList
	 *            - пустой список, который будет заполнен
	 */
	public static void createSelectedTopicList(CheckableTreeNode node, List<String> topicList) {
		for (CheckableTreeNode child : node.getChildNodesList()) {
			if (child.isLeaf()) {
				if (child.isSelected()) {
					topicList.add(child.getText());
				}
			} else {
				createSelectedTopicList(child, topicList);
			}
		}
	}

	/**
	 * Заполняет корневой (root) элемент дерева (устанавливает значение поля
	 * selected) на основе данных из списка topicList.
	 * 
	 * @param root
	 *            - корневой элемент дерева, который нужно заполнить
	 * @param topicList
	 *            - список элементов, которым нужно установить значение
	 *            selected=true
	 */
	public static void fillRootNodeFromTopicList(CheckableTreeNode root, List<String> topicList) {
		if (root != null) {
			for (String topic : topicList) {
				findTopicAndSetSelected(root, topic);
			}
		}
	}

	/**
	 * Вспомогательный метод для "fillRootNodeFromTopicList()". Предназначен для
	 * РЕКУРСИВНОЙ! обработки единичного элемента CheckableTreeNode.
	 * 
	 * @param node
	 *            - единичный элемент CheckableTreeNode.
	 * @param topic
	 *            - элемент topicList-а
	 */
	private static void findTopicAndSetSelected(CheckableTreeNode node, String topic) {
		if (!node.isLeaf()) {
			for (CheckableTreeNode child : node.getChildNodesList()) {
				findTopicAndSetSelected(child, topic);
			}
		} else {
			if (node.getText().equals(topic)) {
				node.setSelected(true);
			}
		}
	}

	/**
	 * Устанавливает уровень (глубину) для элемента дерева (node). Уровень
	 * корневого элемента должен быть равен нулю (0). Его дочерних элементов -
	 * единице (1) и т.д. РЕКУРСИЯ!
	 * 
	 * @param node
	 */
	public void updateLevel(CheckableTreeNode node) {
		int parentLevel = (node.getParent() == null) ? -1 : node.getParent().getLevel();
		node.setLevel(parentLevel + 1);
		for (CheckableTreeNode childNode : node.getChildNodesList()) {
			updateLevel(childNode);
		}
	}

	@Override
	public Object clone() {
		CheckableTreeNode clone = new CheckableTreeNode(text);
		clone.setSelected(selected);
		clone.setAllowsChildren(allowsChildren);
		clone.setLevel(level);

		for (CheckableTreeNode child : childNodesList) {
			CheckableTreeNode childClone = (CheckableTreeNode) child.clone();
			clone.add(childClone);
		}
		return clone;
	}

	private final List<CheckableTreeNode> childNodesList = new ArrayList<CheckableTreeNode>();

	private final String text;
	private boolean selected;

	private CheckableTreeNode parent;
	private int level = 0;
	private boolean allowsChildren = true;
	private boolean leaf = true;
}
