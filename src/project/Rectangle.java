package project;

import java.awt.Color;
import java.util.ArrayList;

public class Rectangle
{
	Vertex origin;
	int width;
	int height;
	int thickness;
	Color brushColor;
	Color fillColor;

	public Rectangle(Vertex origin, int width, int height, int thickness, Color brushColor, Color fillColor)
	{
		if (check(origin))
		{
			this.origin = origin;
			this.width = width;
			this.height = height;
			this.thickness = thickness;
			this.brushColor = brushColor;
			this.fillColor = fillColor;
		}
	}

	private boolean check(Vertex origin2)
	{
		if (origin2.x == Integer.MIN_VALUE || origin2.y == Integer.MIN_VALUE || origin2.x == Integer.MAX_VALUE
				|| origin2.y == Integer.MAX_VALUE)
			return false;
		return true;
	}

	public Vertex getOrigin()
	{
		return origin;
	}

	public void setOrigin(Vertex origin)
	{
		this.origin = origin;
	}

	public int getWidth()
	{
		return width;
	}

	public void setWidth(int width)
	{
		this.width = width;
	}

	public int getHeight()
	{
		return height;
	}

	public void setHeight(int height)
	{
		this.height = height;
	}

	public int getThickness()
	{
		return thickness;
	}

	public void setThickness(int thickness)
	{
		this.thickness = thickness;
	}

	public Color getBrushColor()
	{
		return brushColor;
	}

	public void setBrushColor(Color brushColor)
	{
		this.brushColor = brushColor;
	}

	public Color getFillColor()
	{
		return fillColor;
	}

	public void setFillColor(Color fillColor)
	{
		this.fillColor = fillColor;
	}

	public Rectangle(Vertex origin, int width, int height, int thickness, Color brushColor)
	{
		this.origin = origin;
		this.width = width;
		this.height = height;
		this.thickness = thickness;
		this.brushColor = brushColor;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((brushColor == null) ? 0 : brushColor.hashCode());
		result = prime * result + height;
		result = prime * result + ((origin == null) ? 0 : origin.hashCode());
		result = prime * result + thickness;
		result = prime * result + width;
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
		Rectangle other = (Rectangle) obj;
		if (brushColor == null)
		{
			if (other.brushColor != null)
				return false;
		}
		else if (!brushColor.equals(other.brushColor))
			return false;
		if (height != other.height)
			return false;
		if (origin == null)
		{
			if (other.origin != null)
				return false;
		}
		else if (!origin.equals(other.origin))
			return false;
		if (thickness != other.thickness)
			return false;
		if (width != other.width)
			return false;
		return true;
	}

	public ArrayList<Vertex> getAllVertices()
	{
		ArrayList<Vertex> vertices = new ArrayList<>();
		vertices.add(new Vertex(origin.x, origin.y));
		vertices.add(new Vertex(origin.x + width, origin.y));
		vertices.add(new Vertex(origin.x + width, origin.y + height));
		vertices.add(new Vertex(origin.x, origin.y + height));
		return vertices;
	}

	@Override
	public String toString()
	{
		return origin + " " + width + " " + height + " " + thickness + " " + toString(brushColor) + " "
				+ toString(fillColor);
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
