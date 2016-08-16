package gui.panel.userAlerts.overridden.model;

public class PTNewsTreeNode extends CheckableTreeNode {

	public PTNewsTreeNode(NodeType type, String displayText, int id) {
		super(displayText);
		this.type = type;
		this.id = id;
	}

	public PTNewsTreeNode(NodeType type, String displayText) {
		this(type, displayText, -1);
	}

	public NodeType getType() {
		return type;
	}

	public int getId() {
		return id;
	}

	private final NodeType type;
	private final int id;

	public enum NodeType {
		ROOT, DATABASE, DIVISION, TOPIC
	}
}
