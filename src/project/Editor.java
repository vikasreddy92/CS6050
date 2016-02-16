package project;

public class Editor implements Runnable {
	Window window;
	Data data;

	Editor() {
		window = new Window(this);
		data = new Data(this);
	}

	void refresh() {
		window.board.repaint();
		window.board.revalidate();
		window.text.setText(data.toText());
	}

	public void run() {
		window.pack();
		window.setVisible(true);
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Editor());
	}
}