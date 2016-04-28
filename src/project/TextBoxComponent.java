package project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TextBoxComponent extends JPanel implements FocusListener, ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String TEXTBOX = "Text: ";
	private static final JLabel lable = new JLabel(TEXTBOX);

	private static final String DEFAULT_TEXT = "Click here to add text";
	private static final int DEFAULT_TEXT_SIZE = 20;
	private static final Font DEFAULT_TEXT_FONT = new Font("Arial", Font.PLAIN, DEFAULT_TEXT_SIZE);
	// private static final Color DEFAULT_TEXT_COLOR = Color.BLACK;

	private static final String FONT_FACE = "Font Face";
	private static final String FONT_FAMILY = "Font Family";
	private static final String FONT_SIZE = "Font Size";

	private JTextField textField;

	private JComboBox<Integer> fontSize;
	private JComboBox<String> fontFamily;
	private JComboBox<String> fontFace;

	private int currentFontFace = Font.PLAIN;
	private String currentFontFamily = DEFAULT_TEXT_FONT.getName();
	private int currentFontSize = DEFAULT_TEXT_SIZE;

	Integer[] fontSizes = new Integer[80];
	// = { 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 36, 40, 45, 50 };
	// String[] fontFamilies = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
	String[] fontFamilies = { "Arial", "Bodoni", "Century Schoolbook", "Cooper Black", "Copperplate Gothic",
			"Courier New", "Gill Sans MT", "Goudy Old Style", "Helvetica", "Letter Gothic Std", "Garamond",
			"Tekton Pro", "Times New Roman" };

	String[] fontFaces = { "Plain", "Bold", "Italic" };

	private Editor editor;

	public TextBoxComponent(Editor editor)
	{
		this.editor = editor;

		textField = new JTextField(DEFAULT_TEXT, 20);
		textField.setHorizontalAlignment(JTextField.HORIZONTAL);
		textField.setFont(TextBoxComponent.DEFAULT_TEXT_FONT);
		for (int i = 0; i < 80; i++)
			fontSizes[i] = 15 + i;
		
		fontSize = new JComboBox<Integer>(fontSizes);
		fontSize.setSelectedIndex(5);
		fontFamily = new JComboBox<String>(fontFamilies);
		fontFace = new JComboBox<String>(fontFaces);

		textField.addFocusListener(this);
		textField.addActionListener(this);

		fontSize.setActionCommand(FONT_SIZE);
		fontFamily.setActionCommand(FONT_FAMILY);
		fontFace.setActionCommand(FONT_FACE);

		fontSize.addActionListener(this);
		fontFamily.addActionListener(this);
		fontFace.addActionListener(this);

		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		this.add(new JLabel("Font Family "), c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 0.5;
		c.insets = new Insets(10, 0, 0, 0);
		this.add(fontFamily, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		this.add(new JLabel("Font Size "), c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 1;
		c.weightx = 0.5;
		c.insets = new Insets(10, 0, 0, 0);
		this.add(fontSize, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		this.add(new JLabel("Font Face "), c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 2;
		c.weightx = 0.5;
		c.insets = new Insets(10, 0, 0, 0);
		this.add(fontFace, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 3;
		this.add(lable, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 3;
		c.weightx = 0.5;
		c.ipady = 40;
		c.insets = new Insets(10, 0, 0, 0);
		this.add(textField, c);
	}

	@Override
	public void focusGained(FocusEvent e)
	{
		if (textField.getText().equals(DEFAULT_TEXT))
			textField.setText("");
		else
			textField.selectAll();
		editor.window.board.setCursor(new Cursor(Cursor.TEXT_CURSOR));
		Board.textField = true;
	}

	@Override
	public void focusLost(FocusEvent e)
	{
		textField.selectAll();
	}

	public TextBox getTextBox()
	{
		textField.transferFocus();
		return new TextBox(textField.getText(), currentFontSize, editor.window.board.getBrushColor(),
				new Font(currentFontFamily, currentFontFace, currentFontSize), new Vertex(0, 0));
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		String mode = e.getActionCommand();
		System.out.println(mode);
		if (mode.equals(FONT_FACE))
			currentFontFace = (int) fontFace.getSelectedIndex();
		else if (mode.equals(FONT_FAMILY))
			currentFontFamily = (String) fontFamily.getSelectedItem();
		else if (mode.equals(FONT_SIZE))
			currentFontSize = (int) fontSize.getSelectedItem();
		textField.setFont(new Font(currentFontFamily, currentFontFace, DEFAULT_TEXT_SIZE));
	}
}
