package gui.panel.userAlerts.util;

import java.util.Arrays;
import java.util.List;

import javax.swing.JTree;

import gui.panel.userAlerts.overridden.model.PTNewsTreeNode;

public class TreeUtils {

	/**
	 * Конвертирует выделенные в дереве значения в строку для отправки на
	 * сервер.
	 * 
	 * @param root - корневой элемент дерева
	 * @return - строку для отправки на сервер
	 */
	public static String rootToLine(PTNewsTreeNode root) {
		String line = "";

		for (PTNewsTreeNode databaseNode : root.getChildNodesList()) {

			StringBuilder topicListBuilder = new StringBuilder();
			int selectedDivisionCounter = 0;

			for (PTNewsTreeNode divisionNode : databaseNode.getChildNodesList()) {

				int selectedTopicCounter = 0;
				for (PTNewsTreeNode topicNode : divisionNode.getChildNodesList()) {
					if (topicNode.isSelected()) {
						topicListBuilder.append(topicNode.getId() + TOPIC_SEPARATOR);
						selectedTopicCounter++;
					}
				}

				if (selectedTopicCounter == divisionNode.getChildCount()) {
					selectedDivisionCounter++;
				}
			}

			String topicList = topicListBuilder.toString();

			if (selectedDivisionCounter == databaseNode.getChildCount()) {
				line = line + databaseNode.getDbName() + PARAM_SEPARATOR + ALL + PARAM_SEPARATOR + DELAY
						+ PARAM_SEPARATOR + DIR + DB_SEPARATOR;
			} else {
				if (!topicList.isEmpty()) {
					topicList = StringHelper.removeLastSymbol(topicList);
					line = line + databaseNode.getDbName() + PARAM_SEPARATOR + topicList + PARAM_SEPARATOR + DELAY
							+ PARAM_SEPARATOR + DIR + DB_SEPARATOR;
				}
			}
		}

		return StringHelper.removeLastSymbol(line);
	}

	/**
	 * Заполняет элементы дерева, на основе данных, полученных с сервера в виде
	 * строки (line)
	 * 
	 * @param root - корневой элемент дерева, который будет заполнен
	 * @param line - строка полученная с сервера
	 */
	public static void lineToRoot(PTNewsTreeNode root, String line) {
		if (line != null && root != null) {
			String[] databases = line.split(DB_SEPARATOR);
			for (String db : databases) {

				String[] params = db.split(PARAM_SEPARATOR);
				if (params.length == PARAMS_COUNT) {
					paramsHandle(root, params);
				}
			}
		}
	}

	private static void paramsHandle(PTNewsTreeNode root, String[] params) {
		for (PTNewsTreeNode dbNode : root.getChildNodesList()) {
			if (dbNode.getDbName().equals(params[PARAM_DBNAME])) {
				if (params[PARAM_TOPICS].equals(ALL)) {
					dbNode.setSelected(true);
				} else {
					String[] topicsIdArray = params[PARAM_TOPICS].split(TOPIC_SEPARATOR);
					List<String> idList = Arrays.asList(topicsIdArray);

					for (PTNewsTreeNode div : dbNode.getChildNodesList()) {
						for (PTNewsTreeNode topic : div.getChildNodesList()) {
							if (idList.contains(String.valueOf(topic.getId()))) {
								topic.setSelected(true);
							}
						}
					}
				}
			}
		}
	}
	
	public static void expandAllNodes(JTree tree, int startingIndex, int rowCount) {
		for (int i = startingIndex; i < rowCount; ++i) {
			tree.expandRow(i);
		}
		if (tree.getRowCount() != rowCount) {
			expandAllNodes(tree, rowCount, tree.getRowCount());
		}
	}

	private static final String DB_SEPARATOR = ",";
	private static final String TOPIC_SEPARATOR = ";";
	private static final String PARAM_SEPARATOR = ":";
	private static final String ALL = "*";
	private static final String DELAY = "0";
	private static final String DIR = "*";

	private static final int PARAMS_COUNT = 4;
	private static final int PARAM_DBNAME = 0;
	private static final int PARAM_TOPICS = 1;
}
