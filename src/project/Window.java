package project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.PopupMenu;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;

public class Window extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Editor editor;
	Board board;
	TextArea text;
	Box box;
	JLabel message;
	
	Window(Editor editor) {
		super("Vector Graphics Editor");
		this.editor = editor;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		Border empty = BorderFactory.createEmptyBorder(5, 5, 5, 5);

		board = new Board(editor);
		board.setPreferredSize(new Dimension(800, 800));
		board.setOpaque(true);
		board.setBackground(Color.WHITE);
		
		box = new Box(editor);
		box.setBorder(empty);

		text = new TextArea(editor);
		text.setEditable(false);
		text.setBorder(empty);

		JScrollPane scroll = new JScrollPane(text);

		JPanel side = new JPanel();
		side.setLayout(new BorderLayout());
		side.add(scroll, BorderLayout.CENTER);
		side.add(box, BorderLayout.PAGE_END);

		message = new JLabel("Hello World");
		message.setBorder(empty);
		message.setOpaque(true);
		message.setBackground(Color.WHITE);
		
		
		Container pane = getContentPane();
		pane.setLayout(new BorderLayout());
		pane.add(board, BorderLayout.CENTER);
		pane.add(side, BorderLayout.LINE_END);
		pane.add(message, BorderLayout.PAGE_END);
	}

}