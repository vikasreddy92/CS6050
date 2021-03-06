package project;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JToolBar;
import javax.swing.border.Border;

public class ToolBox extends JToolBar implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	public static final String NODE = "Node";
	public static final String LINE = "Line";
	public static final String CIRCLE = "Circle";
	public static final String RECTANGLE = "Rectangle";
	public static final String POLYGON = "Ploygon";
	public static final String BRUSH_SIZE = "Size";
	public static final String BRUSH_COLOR = "Colors";
	public static final String FILL_COLOR = "Fill Color";
	
	private static ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

	public static String action = NODE;

	private JComboBox<String> brushSizes;

	JButton nodeBtn;
	private JButton lineBtn;
	private JButton circleBtn;
	private JButton rectangleBtn;
	private JButton polygonBtn; 
	
	private JButton brushColorBtn;
	private JButton fillColorBtn;
	
	private Image nodeImage;
	private Image lineImage;
	private Image circleImage;
	private Image rectangleImage;
	private Image polygonImage;
	
	private Image brushColorImage;
	private Image fillColorImage;
	
	private Dimension btnDimension;
	
	String[] sizeOptions = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20" };
	Editor editor;

	public ToolBox(Editor editor) {
		this.editor = editor;

		btnDimension = new Dimension(20, 20);
		Border empty = BorderFactory.createEmptyBorder(5, 5, 5, 5);
	
		nodeImage = new ImageIcon(classLoader.getResource("resources/node.png")).getImage();
		nodeImage = nodeImage.getScaledInstance(btnDimension.width, btnDimension.height, java.awt.Image.SCALE_SMOOTH);
		
		lineImage = new ImageIcon(classLoader.getResource("resources/line.png")).getImage();
		lineImage = lineImage.getScaledInstance(btnDimension.width, btnDimension.height, java.awt.Image.SCALE_SMOOTH);

		circleImage = new ImageIcon(classLoader.getResource("resources/circle.png")).getImage();
		circleImage = circleImage.getScaledInstance(btnDimension.width, btnDimension.height,
				java.awt.Image.SCALE_SMOOTH);

		rectangleImage = new ImageIcon(classLoader.getResource("resources/rectangle.png")).getImage();
		rectangleImage = rectangleImage.getScaledInstance(btnDimension.width, btnDimension.height,
				java.awt.Image.SCALE_SMOOTH);

		polygonImage = new ImageIcon(classLoader.getResource("resources/polygon.png")).getImage();
		polygonImage = polygonImage.getScaledInstance(btnDimension.width, btnDimension.height, java.awt.Image.SCALE_SMOOTH);
		
		brushColorImage = new ImageIcon(classLoader.getResource("resources/color-picker.png")).getImage();
		brushColorImage = brushColorImage.getScaledInstance(btnDimension.width, btnDimension.height, java.awt.Image.SCALE_SMOOTH);
		
		fillColorImage = new ImageIcon(classLoader.getResource("resources/fill-color.png")).getImage();
		fillColorImage = fillColorImage.getScaledInstance(btnDimension.width, btnDimension.height, java.awt.Image.SCALE_SMOOTH);
		
		nodeBtn = new JButton(NODE, new ImageIcon(nodeImage));
		lineBtn = new JButton(LINE, new ImageIcon(lineImage));
		circleBtn = new JButton(CIRCLE, new ImageIcon(circleImage));
		rectangleBtn = new JButton(RECTANGLE, new ImageIcon(rectangleImage));
		polygonBtn = new JButton(POLYGON, new ImageIcon(polygonImage));

		brushColorBtn = new JButton(new ImageIcon(brushColorImage));
		brushColorBtn.setActionCommand(BRUSH_COLOR);
		
		fillColorBtn = new JButton(new ImageIcon(fillColorImage));
		fillColorBtn.setActionCommand(FILL_COLOR);

		brushSizes = new JComboBox<>(sizeOptions);
		brushSizes.setActionCommand(BRUSH_SIZE);
		
		nodeBtn.setSize(btnDimension);
		lineBtn.setSize(btnDimension);
		circleBtn.setSize(btnDimension);
		rectangleBtn.setSize(btnDimension);
		polygonBtn.setSize(btnDimension);
		
		brushColorBtn.setSize(btnDimension);
		brushColorBtn.setBorder(empty);
		brushColorBtn.setContentAreaFilled(false);

		fillColorBtn.setSize(btnDimension);
		fillColorBtn.setBorder(empty);
		fillColorBtn.setContentAreaFilled(false);
		
		brushSizes.setMaximumSize(brushSizes.getPreferredSize());

		addSeparator(btnDimension);
		add(new JLabel("Shapes: "));
		add(nodeBtn);
		add(lineBtn);
		add(circleBtn);
		add(rectangleBtn);
		add(polygonBtn);
		addSeparator(btnDimension);
		add(new JLabel("Pen Size: "));
		add(brushSizes);
		addSeparator(btnDimension);
		add(new JLabel("Pen Color: "));
		add(brushColorBtn);
		addSeparator(btnDimension);
		add(new JLabel("Fill Color: "));
		add(fillColorBtn);
		addSeparator(btnDimension);
		
		nodeBtn.addActionListener(this);
		lineBtn.addActionListener(this);
		circleBtn.addActionListener(this);
		rectangleBtn.addActionListener(this);
		polygonBtn.addActionListener(this);
		brushSizes.addActionListener(this);
		
		brushColorBtn.addActionListener(this);
		fillColorBtn.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		action = e.getActionCommand();

		editor.window.box.validate();
		editor.window.box.revalidate();
		
		switch (action) {
		case NODE:
			editor.window.box.removeAll();
			editor.window.box.an.setSelected(true);
			editor.window.box.mode = editor.window.box.AN;
			editor.window.box.add(editor.window.box.an);
			editor.window.box.add(editor.window.box.sn);
			editor.window.box.add(editor.window.box.mn);
			editor.window.box.add(editor.window.box.rn);
			editor.window.box.add(editor.window.box.un);
			break;
		case LINE:
			editor.window.box.removeAll();
			editor.window.box.al.setSelected(true);
			editor.window.box.mode = editor.window.box.AL;
			editor.window.box.add(editor.window.box.al);
			editor.window.box.add(editor.window.box.sl);
			editor.window.box.add(editor.window.box.rl);
			editor.window.box.add(editor.window.box.ul);
			break;
		case CIRCLE:
			editor.window.box.removeAll();
			editor.window.box.ac.setSelected(true);
			editor.window.box.mode = editor.window.box.AC;
			editor.window.box.add(editor.window.box.ac);
			editor.window.box.add(editor.window.box.sc);
			editor.window.box.add(editor.window.box.mc);
			editor.window.box.add(editor.window.box.rc);
			editor.window.box.add(editor.window.box.uc);
			break;
		case RECTANGLE:
			editor.window.box.removeAll();
			editor.window.box.ar.setSelected(true);
			editor.window.box.mode = editor.window.box.AR;
			editor.window.box.add(editor.window.box.ar);
			editor.window.box.add(editor.window.box.sr);
			editor.window.box.add(editor.window.box.mr);
			editor.window.box.add(editor.window.box.rr);
			editor.window.box.add(editor.window.box.ur);
			break;
		case POLYGON:
			editor.window.box.removeAll();
			editor.window.box.ap.setSelected(true);
			editor.window.box.mode = editor.window.box.AP;
			editor.window.box.add(editor.window.box.ap);
			editor.window.box.add(editor.window.box.sp);
			editor.window.box.add(editor.window.box.rp);
			editor.window.box.add(editor.window.box.up);
			break;
		case BRUSH_SIZE:
			JComboBox<?> cb = (JComboBox<?>)e.getSource();
			editor.window.board.setBrushSize(Integer.parseInt((String) cb.getSelectedItem()));
			break;
		case BRUSH_COLOR:
			Color color = JColorChooser.showDialog(this, "Pen color", Color.BLACK);
			if(color != null) {
				editor.window.board.setBrushColor(color);
				System.out.println("Brush color changed to: " + editor.window.board.getBrushColor().toString() + "\tAlpha: " + color.getAlpha());
			}
			break;
		case FILL_COLOR:
			Color color1 = JColorChooser.showDialog(this, "Brush color", Color.BLACK);
			if(color1 != null) {
				editor.window.board.setFillColor(color1);
				System.out.println("Brush color changed to: " + editor.window.board.getBrushColor().toString());
			}
		}
		editor.window.box.validate();
		editor.window.box.repaint();
	}
}