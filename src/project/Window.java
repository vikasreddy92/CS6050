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
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

public class Window extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Editor editor;
	Board board;
	TextArea text;
	Box box;
	ToolBox toolBox;
	JLabel message;
	
	Window(Editor editor) {
		super("Vector Graphics Editor");
		this.editor = editor;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Border emptyBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		
		board = new Board(editor);
		board.setPreferredSize(new Dimension(800, 800));
		board.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), "Tools"));
		board.setOpaque(true);
		board.setBackground(Color.WHITE);
		
		toolBox = new ToolBox(editor);
		toolBox.setFloatable(false);
		toolBox.setBorder(emptyBorder);
		
		box = new Box(editor);
		box.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), "Tools"));

		text = new TextArea(editor);
		text.setEditable(false);
		text.setBorder(emptyBorder);

		JScrollPane scroll = new JScrollPane(text);

		JPanel side = new JPanel();
		side.setLayout(new BorderLayout());
		side.add(scroll, BorderLayout.CENTER);
		side.add(box, BorderLayout.PAGE_END);

		message = new JLabel("Hello World");
		message.setBorder(emptyBorder);
		message.setOpaque(true);
		message.setBackground(Color.LIGHT_GRAY);
		
		Container pane = getContentPane();

		pane.setLayout(new BorderLayout());
		pane.add(toolBox, BorderLayout.PAGE_START);
		pane.add(board, BorderLayout.CENTER);
		pane.add(side, BorderLayout.LINE_END);
		pane.add(message, BorderLayout.PAGE_END);
		
		this.pack();
	}
}