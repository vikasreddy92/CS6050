package project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

public class Window extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Editor editor;
	public Board board;
	public TextArea text;
	public Box box;
	public ToolBox toolBox;
	public ObjectProperties objectProps;
	public TextBoxComponent textBoxComponent;
	public Algorithms algorithms;
	public JLabel message;
	
	Window(Editor editor) {
		super("Vector Graphics Editor");
		this.editor = editor;

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Border emptyBorder = BorderFactory.createEmptyBorder(8, 8, 8, 8);
		
		toolBox = new ToolBox(editor);
		toolBox.setFloatable(false);
		toolBox.setBorder(emptyBorder);
		
		board = new Board(editor);
		board.setPreferredSize(new Dimension(923, 1474));
//		System.out.println("Window.java H:" + this.getHeight() + ", W: " + this.getWidth());
		board.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));
		board.setOpaque(true);
		board.setBackground(Color.white);
		board.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		
		box = new Box(editor);
		box.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), "Options"));
		
		algorithms = new Algorithms(editor);
		algorithms.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), "Algorithms"));

		objectProps = new ObjectProperties(editor);
		objectProps.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), "Properties"));
		
		textBoxComponent = new TextBoxComponent(editor);
		textBoxComponent.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), "Text"));
		
		text = new TextArea(editor);
		text.setEditable(false);
		text.setBorder(emptyBorder);
		
		JPanel side = new JPanel();
		side.setLayout(new BorderLayout());
//		side.add(box, BorderLayout.PAGE_START);
//		side.add(textBoxComponent, BorderLayout.CENTER);
//		side.add(algorithms, BorderLayout.PAGE_END);
		side.setLayout(new GridLayout(4, 1));
		side.add(box);
		side.add(objectProps);
		side.add(textBoxComponent);
		side.add(algorithms);

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
		
		setJMenuBar(new MenuBar(editor));
//		this.pack();
	}
}