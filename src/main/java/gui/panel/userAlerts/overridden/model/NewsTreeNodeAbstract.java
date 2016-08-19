package gui.panel.userAlerts.overridden.model;

import javax.swing.tree.TreeNode;

import gui.panel.userAlerts.parent.Checkable;

public abstract class NewsTreeNodeAbstract implements TreeNode, Checkable {

	public NewsTreeNodeAbstract(NodeType type, String displayText) {
		this.displayText = displayText;
		this.type = type;
	}

	public NewsTreeNodeAbstract(NodeType type, String displayText, int id) {
		this(type, displayText);
		this.id = id;
	}

	public NewsTreeNodeAbstract(NodeType type, String displayText, String dbName) {
		this(type, displayText);
		this.dbName = dbName;
	}

	public NodeType getType() {
		return type;
	}

	public String getDisplayText() {
		return displayText;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public boolean isSelected() {
		return selected;
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

	@Override
	public String toString() {
		return displayText;
	}

	protected final NodeType type;
	protected final String displayText;
	protected int id = -1;
	protected String dbName;

	protected boolean selected;

	protected boolean allowsChildren = true;
	protected boolean leaf = true;

	public enum NodeType {
		ROOT, DATABASE, DIVISION, TOPIC
	}
}
