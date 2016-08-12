package gui.panel.userAlerts.util;

import javax.swing.JComboBox;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

public class ComboBoxUtils {

	public static void addTextChangeListener(JComboBox box, final Handler handler) {
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

	public static String getText(JComboBox box) {
		final JTextComponent textComponent = (JTextComponent) box.getEditor().getEditorComponent();
		return textComponent.getText();
	}

	public static void setText(JComboBox box, String text) {
		final JTextComponent textComponent = (JTextComponent) box.getEditor().getEditorComponent();
		textComponent.setText(text);
	}

	public static boolean isEmpty(JComboBox box) {
		return getText(box).isEmpty();
	}

	public interface Handler {
		public void handle(JTextComponent textComponent);
	}

	public static void addItem(JComboBox box, String value) {
		addItem(box, value, true);
	}

	public static void addItem(JComboBox box, String value, boolean enableChecking) {
		if (enableChecking) {
			if (!value.isEmpty()) {
				box.addItem(value);
			}
		} else {
			box.addItem(value);
		}
	}
}
