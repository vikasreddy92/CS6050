package project;

public class Editor implements Runnable {
	Window window;
	Data data;
	Shape circles;

	Editor() {
		window = new Window(this);
		data = new Data(this);
		circles = new Shape(this);
		
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