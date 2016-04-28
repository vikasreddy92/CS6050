package project;

import java.awt.Color;

public class Circle
{

	Vertex center;
	int radius;
	int thickness;
	Color brushColor;
	Color fillColor;

	Circle(Vertex center, int radius, int thickness, Color brushColor, Color fillColor)
	{
		this.center = center;
		this.radius = radius;
		this.thickness = thickness;
		this.brushColor = brushColor;
		this.fillColor = fillColor;
	}

	public Vertex getCenter()
	{
		return center;
	}

	public void setCenter(Vertex center)
	{
		this.center = center;
	}

	public int getRadius()
	{
		return radius;
	}

	public void setRadius(int radius)
	{
		this.radius = radius;
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

	Circle(Vertex center, int radius, int thickness, Color brushColor)
	{
		this.center = center;
		this.radius = radius;
		this.thickness = thickness;
		this.brushColor = brushColor;
	}

	@Override
	public int hashCode()
	{
		return this.center.hashCode() + this.radius;
	}

	@Override
	public boolean equals(Object o)
	{
		Circle c = (Circle) o;
		return (this.center.equals(c.center) && this.radius == c.radius);
	}

	@Override
	public String toString()
	{
		return center + " " + radius + " " + thickness + " " + toString(brushColor) + " "
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
