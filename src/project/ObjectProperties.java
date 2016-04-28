package project;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ObjectProperties extends JPanel implements ActionListener, KeyListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Editor editor;

	private final String X = "X: ";
	private final String Y = "Y: ";

	private final String R = "Red: ";
	private final String G = "Green: ";
	private final String B = "Blue: ";
	private final String A = "Alpha: ";

	private final String BRUSH_SIZE = "Brush Size: ";
	private final String BRUSH_COLOR = "Brush Color: ";
	private final String FILL_COLOR = "Fill Color: ";

	private final String RADIUS = "Radius: ";
	private final String WIDTH = "Width: ";
	private final String HEIGHT = "Height: ";

	private JLabel xLabel, yLabel, brushSizeLabel, fillColorLabel, brushColorLabel, brLabel, bgLabel, bbLabel,
			baLabel, frLabel, fgLabel, fbLabel, faLabel, radiusLabel, widthLabel, heightLabel;

	private JTextField xTextField, yTextField, brushSizeTextField, brTextField, bgTextField, bbTextField,
			baTextField, frTextField, fgTextField, fbTextField, faTextField, radiusTextField, widthTextField,
			heightTextField;

	private Node tempNode;
	private Circle tempCircle;
	private Rectangle tempRectangle;
	private Edge tempEdge;
	private Polygon tempPolygon;

	public ObjectProperties(Editor editor)
	{
		this.editor = editor;
		xLabel = new JLabel(X);
		yLabel = new JLabel(Y);

		brLabel = new JLabel(R);
		bgLabel = new JLabel(G);
		bbLabel = new JLabel(B);
		baLabel = new JLabel(A);

		frLabel = new JLabel(R);
		fgLabel = new JLabel(G);
		fbLabel = new JLabel(B);
		faLabel = new JLabel(A);

		brushSizeLabel = new JLabel(BRUSH_SIZE);
		brushColorLabel = new JLabel(BRUSH_COLOR);
		fillColorLabel = new JLabel(FILL_COLOR);

		radiusLabel = new JLabel(RADIUS);
		widthLabel = new JLabel(WIDTH);
		heightLabel = new JLabel(HEIGHT);

		xTextField = new JTextField(4);
		yTextField = new JTextField(4);

		brushSizeTextField = new JTextField(2);

		brTextField = new JTextField(3);
		bgTextField = new JTextField(3);
		bbTextField = new JTextField(3);
		baTextField = new JTextField(3);

		frTextField = new JTextField(3);
		fgTextField = new JTextField(3);
		fbTextField = new JTextField(3);
		faTextField = new JTextField(3);

		radiusTextField = new JTextField(3);
		widthTextField = new JTextField(3);
		heightTextField = new JTextField(3);

		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.25;
		c.anchor = GridBagConstraints.EAST;
		c.insets = new Insets(0, 10, 0, 0);
		this.add(xLabel, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 0.25;
		c.anchor = GridBagConstraints.CENTER;
		this.add(xTextField, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 0;
		c.weightx = 0.25;
		c.anchor = GridBagConstraints.EAST;
		this.add(yLabel, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 3;
		c.gridy = 0;
		c.weightx = 0.25;
		c.anchor = GridBagConstraints.CENTER;
		this.add(yTextField, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 4;
		c.gridy = 0;
		c.weightx = 0.25;
		c.anchor = GridBagConstraints.EAST;
		this.add(brushSizeLabel, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 5;
		c.gridy = 0;
		c.weightx = 0.25;
		c.anchor = GridBagConstraints.CENTER;
		this.add(brushSizeTextField, c);

		// *******************************************************************//
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 1;
		c.insets = new Insets(10, 10, 0, 0);
		c.anchor = GridBagConstraints.CENTER;
		this.add(brushColorLabel, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 1;
		c.weightx = 1;
		c.anchor = GridBagConstraints.CENTER;
		this.add(brLabel, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 1;
		c.weightx = 1;
		c.anchor = GridBagConstraints.CENTER;
		this.add(brTextField, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 3;
		c.gridy = 1;
		c.weightx = 1;
		c.anchor = GridBagConstraints.CENTER;
		this.add(bgLabel, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 4;
		c.gridy = 1;
		c.weightx = 1;
		c.anchor = GridBagConstraints.CENTER;
		this.add(bgTextField, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 5;
		c.gridy = 1;
		c.weightx = 1;
		c.anchor = GridBagConstraints.CENTER;
		this.add(bbLabel, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 6;
		c.gridy = 1;
		c.weightx = 1;
		c.anchor = GridBagConstraints.CENTER;
		this.add(bbTextField, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 7;
		c.gridy = 1;
		c.weightx = 1;
		c.anchor = GridBagConstraints.CENTER;
		this.add(baLabel, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 8;
		c.gridy = 1;
		c.weightx = 1;
		c.anchor = GridBagConstraints.CENTER;
		this.add(baTextField, c);

		/*******************************************************************/

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		c.weightx = 1;
		c.anchor = GridBagConstraints.CENTER;
		this.add(fillColorLabel, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 2;
		c.weightx = 1;
		c.anchor = GridBagConstraints.CENTER;
		this.add(frLabel, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 2;
		c.weightx = 1;
		c.anchor = GridBagConstraints.CENTER;
		this.add(frTextField, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 3;
		c.gridy = 2;
		c.weightx = 1;
		c.anchor = GridBagConstraints.CENTER;
		this.add(fgLabel, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 4;
		c.gridy = 2;
		c.weightx = 1;
		c.anchor = GridBagConstraints.CENTER;
		this.add(fgTextField, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 5;
		c.gridy = 2;
		c.weightx = 1;
		c.anchor = GridBagConstraints.CENTER;
		this.add(fbLabel, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 6;
		c.gridy = 2;
		c.weightx = 1;
		c.anchor = GridBagConstraints.CENTER;
		this.add(fbTextField, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 7;
		c.gridy = 2;
		c.weightx = 1;
		c.anchor = GridBagConstraints.CENTER;
		this.add(faLabel, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 8;
		c.gridy = 2;
		c.weightx = 1;
		c.anchor = GridBagConstraints.CENTER;
		this.add(faTextField, c);

		/********************************************************************************/

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 3;
		c.weightx = 1;
		c.anchor = GridBagConstraints.CENTER;
		this.add(radiusLabel, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 3;
		c.weightx = 1;
		c.anchor = GridBagConstraints.CENTER;
		this.add(radiusTextField, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 3;
		c.weightx = 1;
		// c.insets = new Insets(20, 30, 20, 20);
		c.anchor = GridBagConstraints.CENTER;
		this.add(widthLabel, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 3;
		c.gridy = 3;
		c.weightx = 1;
		c.anchor = GridBagConstraints.CENTER;
		this.add(widthTextField, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 4;
		c.gridy = 3;
		c.weightx = 1;
		c.anchor = GridBagConstraints.CENTER;
		this.add(heightLabel, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 5;
		c.gridy = 3;
		c.weightx = 1;
		c.anchor = GridBagConstraints.CENTER;
		this.add(heightTextField, c);

		xTextField.addActionListener(this);
		yTextField.addActionListener(this);

		xTextField.addKeyListener(this);
		yTextField.addKeyListener(this);
		brushSizeTextField.addKeyListener(this);
		brTextField.addKeyListener(this);
		bgTextField.addKeyListener(this);
		bbTextField.addKeyListener(this);
		baTextField.addKeyListener(this);
		frTextField.addKeyListener(this);
		fgTextField.addKeyListener(this);
		fbTextField.addKeyListener(this);
		faTextField.addKeyListener(this);

		radiusTextField.addKeyListener(this);
		widthTextField.addKeyListener(this);
		heightTextField.addKeyListener(this);

		radiusTextField.setEnabled(false);
		widthTextField.setEnabled(false);
		heightTextField.setEnabled(false);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{

	}

	public void updateProperties(Object obj)
	{
		System.out.println(obj.getClass().getName());
		xTextField.requestFocus();
		if (obj.getClass().getName().contains(Node.class.getName()))
		{
			tempCircle = null;
			tempRectangle = null;
			tempEdge = null;
			tempPolygon = null;

			radiusTextField.setEnabled(false);
			widthTextField.setEnabled(false);
			heightTextField.setEnabled(false);

			tempNode = (Node) obj;

			xTextField.setText(String.valueOf(tempNode.getX()));
			yTextField.setText(String.valueOf(tempNode.getY()));

			setBrushColorTextField(tempNode.getBrushColor());

		}
		else if (obj.getClass().getName().contains(Circle.class.getName()))
		{
			tempNode = null;
			tempRectangle = null;
			tempEdge = null;
			tempPolygon = null;

			tempCircle = (Circle) obj;

			radiusTextField.setEnabled(true);
			widthTextField.setEnabled(false);
			heightTextField.setEnabled(false);

			xTextField.setText(String.valueOf(tempCircle.getCenter().getX()));
			yTextField.setText(String.valueOf(tempCircle.getCenter().getY()));

			radiusTextField.setText(String.valueOf(tempCircle.getRadius()));

			setBrushColorTextField(tempCircle.getBrushColor());
			setFillColorTextField(tempCircle.getFillColor());

			brushSizeTextField.setText(String.valueOf(tempCircle.getThickness()));
		}
		else if (obj.getClass().getName().contains(Rectangle.class.getName()))
		{
			tempNode = null;
			tempCircle = null;
			tempEdge = null;
			tempPolygon = null;

			radiusTextField.setEnabled(false);
			widthTextField.setEnabled(true);
			heightTextField.setEnabled(true);

			tempRectangle = (Rectangle) obj;

			xTextField.setText(String.valueOf(tempRectangle.getOrigin().getX()));
			yTextField.setText(String.valueOf(tempRectangle.getOrigin().getY()));

			widthTextField.setText(String.valueOf(tempRectangle.getWidth()));
			heightTextField.setText(String.valueOf(tempRectangle.getHeight()));

			setBrushColorTextField(tempRectangle.getBrushColor());
			setFillColorTextField(tempRectangle.getFillColor());

			brushSizeTextField.setText(String.valueOf(tempRectangle.getThickness()));
		}
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		// System.out.println("KT" + e.getKeyCode());
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		if (e.getKeyCode() == KeyEvent.VK_UP)
			updateTextField((JTextField) e.getComponent(), 1);
		else if (e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			JTextField tf = (JTextField) e.getComponent();
			int currV = Integer.parseInt(tf.getText());
			if (currV != 0)
				updateTextField((JTextField) e.getComponent(), -1);
			else
				JOptionPane.showMessageDialog(editor.window, "Value cannot be negative", "Out of Range Error",
						JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		if (tempNode != null)
		{
			tempNode.setX(Integer.parseInt(xTextField.getText()));
			tempNode.setY(Integer.parseInt(yTextField.getText()));

			Color brushColor = getBrushColor();
			if (brushColor != null)
				tempNode.setBrushColor(brushColor);
		}
		else if (tempEdge != null)
		{

		}
		else if (tempCircle != null)
		{
			tempCircle.setCenter(new Vertex(Integer.parseInt(xTextField.getText()),
					Integer.parseInt(yTextField.getText())));
			tempCircle.setThickness(Integer.parseInt(brushSizeTextField.getText()));

			tempCircle.setRadius(Integer.parseInt(radiusTextField.getText()));

			Color brushColor = getBrushColor();
			if (brushColor != null)
				tempCircle.setBrushColor(brushColor);

			Color fillColor = getFillColor();
			if (fillColor != null)
				tempCircle.setFillColor(fillColor);
		}
		else if (tempRectangle != null)
		{
			tempRectangle.setOrigin(new Vertex(Integer.parseInt(xTextField.getText()),
					Integer.parseInt(yTextField.getText())));
			tempRectangle.setThickness(Integer.parseInt(brushSizeTextField.getText()));

			tempRectangle.setWidth(Integer.parseInt(widthTextField.getText()));
			tempRectangle.setHeight(Integer.parseInt(heightTextField.getText()));

			Color brushColor = getBrushColor();
			if (brushColor != null)
				tempRectangle.setBrushColor(brushColor);

			Color fillColor = getFillColor();
			if (fillColor != null)
				tempRectangle.setFillColor(fillColor);

		}
		else if (tempPolygon != null)
		{

		}
		editor.refresh();
	}

	private void setBrushColorTextField(Color brushColor)
	{
		brTextField.setText(String.valueOf(brushColor.getRed()));
		bgTextField.setText(String.valueOf(brushColor.getGreen()));
		bbTextField.setText(String.valueOf(brushColor.getBlue()));
		baTextField.setText(String.valueOf(brushColor.getAlpha()));
	}

	private void setFillColorTextField(Color fillColor)
	{
		frTextField.setText(String.valueOf(fillColor.getRed()));
		fgTextField.setText(String.valueOf(fillColor.getGreen()));
		fbTextField.setText(String.valueOf(fillColor.getBlue()));
		faTextField.setText(String.valueOf(fillColor.getAlpha()));
	}

	private Color getBrushColor()
	{
		int r = Integer.parseInt(brTextField.getText());
		int g = Integer.parseInt(bgTextField.getText());
		int b = Integer.parseInt(bbTextField.getText());
		int a = Integer.parseInt(baTextField.getText());
		if (r < 256 && g < 256 && b < 256 && a < 256)
			return new Color(r, g, b, a);
		else
			return null;
	}

	private Color getFillColor()
	{
		int r = Integer.parseInt(frTextField.getText());
		int g = Integer.parseInt(fgTextField.getText());
		int b = Integer.parseInt(fbTextField.getText());
		int a = Integer.parseInt(faTextField.getText());
		if (r < 256 && g < 256 && b < 256 && a < 256)
			return new Color(r, g, b, a);
		else
			return null;
	}

	private void updateTextField(JTextField textField, int by)
	{
		textField.setText(String.valueOf(Integer.parseInt(textField.getText()) + by));
	}
}
