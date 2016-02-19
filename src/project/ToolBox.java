package project;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JToolBar;
import javax.swing.border.Border;

public class ToolBox extends JToolBar implements ActionListener {
	private static final long serialVersionUID = 1L;
	public static final String LINE = "Line";
	public static final String CIRCLE = "Circle";
	public static final String RECTANGLE = "Rectangle";
	public static final String SIZE_LIST = "Size";
	private static ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

	public static String action = LINE;

	private JComboBox<String> sizesList;

	JButton lineBtn;
	private JButton circleBtn;
	private JButton rectangleBtn;
	
	private JButton grayBtn;
	private JButton blackBtn;
	private JButton redBtn;
	private JButton greenBtn;
	private JButton blueBtn;
	
	private Image lineImage;
	private Image circleImage;
	private Image rectangleImage;
	
	private Image grayImage;
	private Image blackImage;
	private Image redImage;
	private Image greenImage;
	private Image blueImage;
	
	private Dimension btnDimension;

	String[] sizeOptions = { "1", "5" };
	Editor editor;

	public ToolBox(Editor editor) {
		this.editor = editor;

		btnDimension = new Dimension(20, 20);
		Border empty = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		
		lineImage = new ImageIcon(classLoader.getResource("resources/line.png")).getImage();
		lineImage = lineImage.getScaledInstance(btnDimension.width, btnDimension.height, java.awt.Image.SCALE_SMOOTH);

		circleImage = new ImageIcon(classLoader.getResource("resources/circle.png")).getImage();
		circleImage = circleImage.getScaledInstance(btnDimension.width, btnDimension.height,
				java.awt.Image.SCALE_SMOOTH);

		rectangleImage = new ImageIcon(classLoader.getResource("resources/rectangle.png")).getImage();
		rectangleImage = rectangleImage.getScaledInstance(btnDimension.width, btnDimension.height,
				java.awt.Image.SCALE_SMOOTH);

		grayImage = new ImageIcon(classLoader.getResource("resources/gray.png")).getImage();
		grayImage = grayImage.getScaledInstance(btnDimension.width, btnDimension.height, java.awt.Image.SCALE_SMOOTH);
		
		blackImage = new ImageIcon(classLoader.getResource("resources/black.png")).getImage();
		blackImage = blackImage.getScaledInstance(btnDimension.width, btnDimension.height, java.awt.Image.SCALE_SMOOTH);
		
		redImage = new ImageIcon(classLoader.getResource("resources/red.png")).getImage();
		redImage = redImage.getScaledInstance(btnDimension.width, btnDimension.height, java.awt.Image.SCALE_SMOOTH);

		greenImage = new ImageIcon(classLoader.getResource("resources/green.png")).getImage();
		greenImage = greenImage.getScaledInstance(btnDimension.width, btnDimension.height, java.awt.Image.SCALE_SMOOTH);

		blueImage = new ImageIcon(classLoader.getResource("resources/blue.png")).getImage();
		blueImage = blueImage.getScaledInstance(btnDimension.width, btnDimension.height, java.awt.Image.SCALE_SMOOTH);
		
		
		lineBtn = new JButton(LINE, new ImageIcon(lineImage));
		circleBtn = new JButton(CIRCLE, new ImageIcon(circleImage));
		rectangleBtn = new JButton(RECTANGLE, new ImageIcon(rectangleImage));

		grayBtn = new JButton(new ImageIcon(grayImage));
		blackBtn = new JButton(new ImageIcon(blackImage));
		redBtn = new JButton(new ImageIcon(redImage));
		greenBtn = new JButton(new ImageIcon(greenImage));
		blueBtn = new JButton(new ImageIcon(blueImage));

		sizesList = new JComboBox<>(sizeOptions);
		sizesList.setActionCommand(SIZE_LIST);
		
		lineBtn.setSize(btnDimension);
		circleBtn.setSize(btnDimension);
		rectangleBtn.setSize(btnDimension);
		
		grayBtn.setSize(btnDimension);
		grayBtn.setBorder(empty);
		grayBtn.setContentAreaFilled(false);
		
		blackBtn.setSize(btnDimension);
		blackBtn.setBorder(empty);
		blackBtn.setContentAreaFilled(false);
		
		redBtn.setSize(btnDimension);
		redBtn.setBorder(empty);
		redBtn.setContentAreaFilled(false);
		
		greenBtn.setSize(btnDimension);
		greenBtn.setBorder(empty);
		greenBtn.setContentAreaFilled(false);
		
		blueBtn.setSize(btnDimension);
		blueBtn.setBorder(empty);
		blueBtn.setContentAreaFilled(false);
		
		sizesList.setMaximumSize(sizesList.getPreferredSize());

		addSeparator(btnDimension);
		add(new JLabel("Shapes: "));
		add(lineBtn);
		add(circleBtn);
		add(rectangleBtn);
		addSeparator(btnDimension);
		add(new JLabel("Brush Size: "));
		add(sizesList);
		addSeparator(btnDimension);
		add(new JLabel("Colors: "));
		add(grayBtn);
		add(blackBtn);
		add(redBtn);
		add(greenBtn);
		add(blueBtn);
		addSeparator(btnDimension);

		lineBtn.addActionListener(this);
		circleBtn.addActionListener(this);
		rectangleBtn.addActionListener(this);
		sizesList.addActionListener(this);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		action = e.getActionCommand();

		editor.window.box.validate();
		editor.window.box.revalidate();
		switch (action) {
		case LINE:
			editor.window.box.removeAll();
			editor.window.box.al.setSelected(true);
			editor.window.box.mode = editor.window.box.AL;
			editor.window.box.add(editor.window.box.al);
			editor.window.box.add(editor.window.box.rl);
			break;
		case CIRCLE:
			editor.window.box.removeAll();
			editor.window.box.ac.setSelected(true);
			editor.window.box.mode = editor.window.box.AC;
			editor.window.box.add(editor.window.box.ac);
			editor.window.box.add(editor.window.box.mc);
			editor.window.box.add(editor.window.box.rc);
			break;
		case RECTANGLE:
			editor.window.box.removeAll();
			editor.window.box.ar.setSelected(true);
			editor.window.box.mode = editor.window.box.AR;
			editor.window.box.add(editor.window.box.ar);
			editor.window.box.add(editor.window.box.mr);
			editor.window.box.add(editor.window.box.rr);
			break;
		case SIZE_LIST:
			JComboBox<?> cb = (JComboBox<?>)e.getSource();
			editor.window.board.setBrushSize(Integer.parseInt((String) cb.getSelectedItem()));
			break;
		}
		editor.window.box.validate();
		editor.window.box.repaint();
	}
}