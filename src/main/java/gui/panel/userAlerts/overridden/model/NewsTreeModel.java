package gui.panel.userAlerts.overridden.model;

import java.util.Arrays;
import java.util.List;

import javax.swing.tree.DefaultTreeModel;

import gui.panel.userAlerts.util.StringHelper;

@SuppressWarnings("serial")
public class NewsTreeModel extends DefaultTreeModel {

	public NewsTreeModel(NewsTreeNode root) {
		super(root);
	}

	/**
	 * Конвертирует выделенные в дереве значения в строку для отправки на
	 * сервер.
	 * 
	 * @return - строка для отправки на сервер
	 */
	public String convertToNewsLine() {
		String line = "";

		for (NewsTreeNode databaseNode : ((NewsTreeNode) root).getChilds()) {

			StringBuilder topicListBuilder = new StringBuilder();
			int selectedDivisionCounter = 0;

			for (NewsTreeNode divisionNode : databaseNode.getChilds()) {

				int selectedTopicCounter = 0;
				for (NewsTreeNode topicNode : divisionNode.getChilds()) {
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
	 * @param line - строка полученная с сервера
	 */
	public void fillFromNewsLine(String line) {
		String[] databases = line.split(DB_SEPARATOR);
		for (String db : databases) {

			String[] params = db.split(PARAM_SEPARATOR);
			if (params.length == PARAMS_COUNT) {
				paramsHandle(params);
			}
		}
	}

	private void paramsHandle(String[] params) {
		for (NewsTreeNode dbNode : ((NewsTreeNode) root).getChilds()) {
			if (dbNode.getDbName().equals(params[PARAM_DBNAME])) {
				if (params[PARAM_TOPICS].equals(ALL)) {
					dbNode.setSelected(true);
				} else {
					String[] topicsIdArray = params[PARAM_TOPICS].split(TOPIC_SEPARATOR);
					List<String> idList = Arrays.asList(topicsIdArray);

					for (NewsTreeNode div : dbNode.getChilds()) {
						for (NewsTreeNode topic : div.getChilds()) {
							if (idList.contains(String.valueOf(topic.getId()))) {
								topic.setSelected(true);
							}
						}
					}
				}
			}
		}
	}

	private static final String DB_SEPARATOR = ",";
	private static final String PARAM_SEPARATOR = ":";
	private static final String TOPIC_SEPARATOR = ";";

	private static final String ALL = "*";

	private static final int PARAM_DBNAME = 0;
	private static final int PARAM_TOPICS = 1;

	private static final int PARAMS_COUNT = 4;
	private static final String DELAY = "0";
	private static final String DIR = "*";
}
