package project;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Editor implements Runnable {
	public Window window;
	public static int board_height;
	public static int board_width;
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
		window.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		window.pack();
		window.setVisible(true);
		board_height = window.board.getHeight();
		board_width = window.board.getWidth();
//		System.out.println("Editor.java H:" + board_height + ", W: " + board_width);
	}

	public static void main(String[] args) {
		Editor editor = new Editor();
		editor.refresh();
		editor.window.toolBox.nodeBtn.doClick();
		javax.swing.SwingUtilities.invokeLater(editor);
	}
}