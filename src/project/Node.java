package project;

import java.awt.Color;

public class Node {

	int x, y;
	public int getX()
	{
		return x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	public Color getBrushColor()
	{
		return brushColor;
	}

	public void setBrushColor(Color brushColor)
	{
		this.brushColor = brushColor;
	}
	Color brushColor;

	Node(int x, int y, Color color) {
		this.x = x;
		this.y = y;
		this.brushColor = color;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((brushColor == null) ? 0 : brushColor.hashCode());
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (brushColor == null)
		{
			if (other.brushColor != null)
				return false;
		}
		else if (!brushColor.equals(other.brushColor))
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return this.x + " " + this.y + " " + toString(this.brushColor);
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