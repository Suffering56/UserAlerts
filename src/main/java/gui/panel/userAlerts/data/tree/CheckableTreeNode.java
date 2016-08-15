package gui.panel.userAlerts.data.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.TreeNode;

public class CheckableTreeNode implements TreeNode {

	public CheckableTreeNode(String name) {
		this.text = name;
		level = 0;
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

			childNode.setLevel(level + 1);
			for (CheckableTreeNode grandChild : childNode.getChildNodesList()) {
				grandChild.setLevel(level + 2);
			}
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

	public void setSelected(boolean selected) {
		this.selected = selected;
		for (CheckableTreeNode child : childNodesList) {
			child.setSelected(selected);
		}
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Override
	public String toString() {
		return text;
	}

	public List<CheckableTreeNode> getChildNodesList() {
		return childNodesList;
	}

	private final List<CheckableTreeNode> childNodesList = new ArrayList<CheckableTreeNode>();

	private final String text;
	private boolean selected;

	private int level;

	private CheckableTreeNode parent;
	private boolean allowsChildren = true;
	private boolean leaf = true;
}
