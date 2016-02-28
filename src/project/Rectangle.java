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
		this.origin = origin;
		this.width = width;
		this.height = height;
		this.thickness = thickness;
		this.brushColor = brushColor;
		this.fillColor = fillColor;
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

	@Override
	public String toString()
	{
		float[] brush = getComponents(brushColor);
		float[] fill = getComponents(fillColor);
		StringBuffer sb = new StringBuffer();
		ArrayList<Vertex> vertices = new ArrayList<>();
		vertices.add(new Vertex(origin.x, origin.y));
		vertices.add(new Vertex(origin.x + width, origin.y));
		vertices.add(new Vertex(origin.x + width, origin.y + height));
		vertices.add(new Vertex(origin.x, origin.y + height));
		sb.append(brush[0]);
		sb.append(" ");
		sb.append(brush[1]);
		sb.append(" ");
		sb.append(brush[2]);
		sb.append(" ");
		sb.append(fill[0]);
		sb.append(" ");
		sb.append(fill[1]);
		sb.append(" ");
		sb.append(fill[2]);
		sb.append(" ");
		sb.append(thickness);
		sb.append(" ");
		sb.append(vertices.get(0));
		sb.append(" ");
		for (int i = 0; i < 4 - 1; i++)
			sb.append(vertices.get(i) + " " + vertices.get(i + 1) + " ");
		sb.append(vertices.get(4 - 1) + " " + vertices.get(0));
		return sb.toString();
	}

	private float[] getComponents(Color color)
	{
		float[] colors = new float[3];
		float MAX_COLOR = 255f;
		colors[0] = ((float) color.getRed()) / MAX_COLOR;
		colors[1] = ((float) color.getGreen()) / MAX_COLOR;
		colors[2] = ((float) color.getBlue()) / MAX_COLOR;
		return colors;
	}
}
