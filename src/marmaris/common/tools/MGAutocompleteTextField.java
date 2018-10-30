package marmaris.common.tools;

import java.awt.event.ActionEvent;
import java.util.Collections;
import java.util.Locale;
import java.util.Optional;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

/**
 * An extension of JTextField with auto-complete function
 */
public class MGAutocompleteTextField extends JTextField implements DocumentListener {

	private static final long serialVersionUID = -6949039728288995532L;
	
	private static final String COMMIT_ACTION = "commit";

	private static enum Mode {
		INSERT, COMPLETION
	};

	private Vector<String> autoCompleteItems = new Vector<>();
	private Mode mode = Mode.INSERT;

	public MGAutocompleteTextField(Vector<String> autoCompleteItems) {
		setAutoCompleteItems(autoCompleteItems);
		setFocusTraversalKeysEnabled(false);
		getDocument().addDocumentListener(this);
		getInputMap().put(KeyStroke.getKeyStroke("TAB"), COMMIT_ACTION);
		getActionMap().put(COMMIT_ACTION, new CommitAction());
	}
	
	public void setAutoCompleteItems(Vector<String> autoCompleteItems) {
		if(autoCompleteItems != null) {
			this.autoCompleteItems = autoCompleteItems;
			Collections.sort(this.autoCompleteItems);
		}
	}
	
	@Override
	public void changedUpdate(DocumentEvent ev) {
	}

	@Override
	public void removeUpdate(DocumentEvent ev) {
	}

	@Override
	public void insertUpdate(DocumentEvent ev) {
		if (ev.getLength() != 1)
			return;

		int pos = ev.getOffset();
		String content = null;
		try {
			content = getText(0, pos + 1);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}

		// Find where the word starts
		int w;
		for (w = pos; w >= 0; w--) {
			if ( !( Character.isLetter(content.charAt(w)) || Character.isDigit(content.charAt(w)) ) ) {
				break;
			}
		}

		String prefix = content.substring(w + 1).toLowerCase();

		Optional<String> test = autoCompleteItems.stream().filter(
				s -> ((String) s).toLowerCase(Locale.getDefault()).startsWith(prefix.toLowerCase(Locale.getDefault())))
				.findFirst();
		if (test.isPresent()) {
			String completion = test.get().substring(pos - w);
			SwingUtilities.invokeLater(new CompletionTask(completion, pos + 1));
		} else {
			// Nothing found
			mode = Mode.INSERT;
		}
	}
	

	public class CommitAction extends AbstractAction {

		private static final long serialVersionUID = 5794543109646743416L;

		@Override
		public void actionPerformed(ActionEvent ev) {
			if (mode == Mode.COMPLETION) {
				int pos = getSelectionEnd();
				StringBuffer sb = new StringBuffer(getText());
				sb.insert(pos, " ");
				setText(sb.toString());
				setCaretPosition(pos + 1);
				mode = Mode.INSERT;
			} else {
				replaceSelection("\t");
			}
		}
	}

	private class CompletionTask implements Runnable {
		private String completion;
		private int position;

		CompletionTask(String completion, int position) {
			this.completion = completion;
			this.position = position;
		}

		public void run() {
			StringBuffer sb = new StringBuffer(getText());
			sb.insert(position, completion);
			setText(sb.toString());
			setCaretPosition(position + completion.length());
			moveCaretPosition(position);
			mode = Mode.COMPLETION;
		}
	}

}