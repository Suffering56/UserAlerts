package gui.panel.userAlerts.util;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JTree;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

public class SwingHelper {

	public static String getComboText(JComboBox box) {
		final JTextComponent textComponent = (JTextComponent) box.getEditor().getEditorComponent();
		return textComponent.getText();
	}

	public static void setComboText(JComboBox box, String text) {
		final JTextComponent textComponent = (JTextComponent) box.getEditor().getEditorComponent();
		textComponent.setText(text);
	}

	public static boolean isEmptyComboText(JComboBox box) {
		return getComboText(box).isEmpty();
	}
	
	public static void addUniqueComboItem(JComboBox box, String value, boolean enableChecking) {
		List<Object> itemsList = getComboItemsList(box);
		if (!itemsList.contains(value)) {
			if (enableChecking) {
				if (!value.isEmpty()) {
					box.addItem(value);
				}
			} else {
				box.addItem(value);
			}
		}
	}

	public static List<Object> getComboItemsList(JComboBox box) {
		List<Object> itemsList = new ArrayList<Object>();
		for (int i = 0; i < box.getItemCount(); i++) {
			itemsList.add(box.getItemAt(i));
		}
		return itemsList;
	}

	public static void addComboTextChangeListener(JComboBox box, final Handler handler) {
		final JTextComponent textComponent = (JTextComponent) box.getEditor().getEditorComponent();
		textComponent.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				handler.handle(textComponent);
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				handler.handle(textComponent);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				handler.handle(textComponent);
			}
		});
	}

	public static void expandAllTreeNodes(JTree tree, int startingIndex, int rowCount) {
		for (int i = startingIndex; i < rowCount; ++i) {
			tree.expandRow(i);
		}
		if (tree.getRowCount() != rowCount) {
			expandAllTreeNodes(tree, rowCount, tree.getRowCount());
		}
	}

	public interface Handler {
		public void handle(JTextComponent textComponent);
	}
}
