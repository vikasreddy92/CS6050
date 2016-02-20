package project;

import javax.swing.SwingUtilities;

public class Editor implements Runnable {
	public Window window;
	Data data;

	Editor() {
		window = new Window(this);
		data = new Data(this);
	}

	void refresh() {
		window.board.invalidate();
		window.board.validate();
		window.board.repaint();
		
		window.toolBox.invalidate();
		window.toolBox.validate();
		window.toolBox.repaint();
		
		window.box.invalidate();
		window.box.validate();
		window.box.repaint();
		
		SwingUtilities.updateComponentTreeUI(window);
		window.text.setText(data.toText());
	}

	public void run() {
		window.pack();
		window.setVisible(true);
	}

	public static void main(String[] args) {
		Editor editor = new Editor();
		editor.refresh();
		editor.window.toolBox.lineBtn.doClick();
		javax.swing.SwingUtilities.invokeLater(editor);
	}
}