package project;

import java.awt.Color;
import java.awt.Font;

public class TextBox
{
	String text;
	int textSize;
	Color textColor;
	Font textFont;
	Vertex textLocation;

	public TextBox(String text, int textSize, Color textColor, Font textFont, Vertex textLocation)
	{
		super();
		this.text = text;
		this.textSize = textSize;
		this.textColor = textColor;
		this.textFont = textFont;
		this.textLocation = textLocation;
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public int getTextSize()
	{
		return textSize;
	}

	public void setTextSize(int textSize)
	{
		this.textSize = textSize;
	}

	public Color getTextColor()
	{
		return textColor;
	}

	public void setTextColor(Color textColor)
	{
		this.textColor = textColor;
	}

	public Font getTextFont()
	{
		return textFont;
	}

	public void setTextFont(Font textFont)
	{
		this.textFont = textFont;
	}

	public Vertex getTextLocation()
	{
		return textLocation;
	}

	public void setTextLocation(Vertex textLocation)
	{
		this.textLocation = textLocation;
	}

	@Override
	public String toString()
	{
		return text.replace(" ", "\\_") + " " + textSize + " " + toString(textColor) + " " + textFont.getName().replace(" ", "-") + " "
				+ textFont.getStyle() + " " + textLocation;
	}

	private String toString(Color color)
	{
		int[] comp = getComponents(color);
		return comp[0] + " " + comp[1] + " " + comp[2] + " " + comp[3];
	}

	private int[] getComponents(Color color)
	{
		int[] comp = new int[4];
		comp[0] = color.getRed();
		comp[1] = color.getGreen();
		comp[2] = color.getBlue();
		comp[3] = color.getAlpha();
		return comp;
	}
}
