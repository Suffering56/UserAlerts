package gui.panel.userAlerts.overridden.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.TreeNode;

public class NewsTreeNode extends NewsTreeNodeAbstract implements Cloneable {

	public NewsTreeNode(NodeType type, String displayText) {
		super(type, displayText);
	}

	public NewsTreeNode(NodeType type, String displayText, int id) {
		super(type, displayText, id);
	}

	public NewsTreeNode(NodeType type, String displayText, String dbName) {
		super(type, displayText, dbName);
	}

	public void add(NewsTreeNode childNode) {
		if (allowsChildren) {
			childNodesList.add(childNode);
			childNode.setParent(this);
			leaf = false;
			updateLevel(childNode);
		}
	}

	/**
	 * Помимо установки значения selected у текущего объекта - устанавливает
	 * аналогичные значения selected у дочерних элементов (и далее -
	 * рекурсивно).
	 * 
	 * @param selected
	 */
	public void setSelected(boolean selected) {
		for (NewsTreeNode child : childNodesList) {
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
	protected void setSelectedWithoutChildActions(boolean selected) {
		this.selected = selected;

		if (parent != null) {

			int counter = 0;
			for (NewsTreeNode brother : parent.getChildNodesList()) {
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

	/**
	 * Устанавливает уровень (глубину) для элемента дерева (node). Уровень
	 * корневого элемента должен быть равен нулю (0). Его дочерних элементов -
	 * единице (1) и т.д. РЕКУРСИЯ!
	 * 
	 * @param node
	 */
	public void updateLevel(NewsTreeNode node) {
		int parentLevel = (node.getParent() == null) ? -1 : node.getParent().getLevel();
		node.setLevel(parentLevel + 1);
		for (NewsTreeNode childNode : node.getChildNodesList()) {
			updateLevel(childNode);
		}
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

	@Override
	public NewsTreeNode getParent() {
		return parent;
	}

	public void setParent(NewsTreeNode parent) {
		this.parent = parent;
	}

	public List<NewsTreeNode> getChildNodesList() {
		return childNodesList;
	}

	@Override
	public NewsTreeNode clone() {
		NewsTreeNode clone = new NewsTreeNode(type, displayText);
		clone.setId(id);
		clone.setDbName(dbName);

		clone.setSelected(selected);
		clone.setAllowsChildren(allowsChildren);
		clone.setLevel(level);

		for (NewsTreeNode child : childNodesList) {
			NewsTreeNode childClone = (NewsTreeNode) child.clone();
			clone.add(childClone);
		}
		return clone;
	}

	protected NewsTreeNode parent;
	protected final List<NewsTreeNode> childNodesList = new ArrayList<NewsTreeNode>();
}
