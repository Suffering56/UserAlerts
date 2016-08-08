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

	public interface Handler {
		public void handle(JTextComponent textComponent);
	}
}
