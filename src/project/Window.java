package project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;

public class Window extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Editor editor;
	Board board;
	JTextArea text;
	Box box;
	JLabel message;
	ToolBar toolbar;
	
	Window(Editor editor) {
		super("Vector Graphics Editor");
		this.editor = editor;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Border empty = BorderFactory.createEmptyBorder(5, 5, 5, 5);

		board = new Board(editor);
		board.setPreferredSize(new Dimension(800, 800));
		board.setOpaque(true);
		board.setBackground(Color.WHITE);
		
		box = new Box(editor);
		box.setBorder(empty);

		text = new JTextArea(0, 15);
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
		
		toolbar = new ToolBar(editor);
		
		Container pane = getContentPane();
		pane.setLayout(new BorderLayout());
		pane.add(toolbar, BorderLayout.PAGE_START);
		pane.add(board, BorderLayout.CENTER);
		pane.add(side, BorderLayout.LINE_END);
		pane.add(message, BorderLayout.PAGE_END);
	}

}