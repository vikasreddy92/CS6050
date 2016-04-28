package project;

import java.util.ArrayList;
import java.awt.Color;

public class Polygon
{
	ArrayList<Vertex> vertices;
	public ArrayList<Vertex> getVertices()
	{
		return vertices;
	}

	public void setVertices(ArrayList<Vertex> vertices)
	{
		this.vertices = vertices;
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

	int thickness;
	Color brushColor;
	Color fillColor;

	@SuppressWarnings("unchecked")
	public Polygon(Object vertices, int thickness, Color brushColor, Color fillColor)
	{
		super();
		this.vertices = (ArrayList<Vertex>) vertices;
		this.thickness = thickness;
		this.brushColor = brushColor;
		this.fillColor = fillColor;
	}

	public Polygon(int[] xP, int[] yP, int thickness2, Color brushColor2, Color fillColor2)
	{
		vertices = new ArrayList<>();
		for(int i = 0; i < xP.length; i++) 
			vertices.add(new Vertex(xP[i], yP[i]));
		this.thickness = thickness2;
		this.brushColor = brushColor2;
		this.fillColor = fillColor2;
	}

	public int[] getXPoints()
	{
		int[] xPoints = new int[vertices.size()];
		int i = 0;
		for (Vertex v : vertices)
			xPoints[i++] = v.x;
		return xPoints;
	}

	public int[] getYPoints()
	{
		int[] yPoints = new int[vertices.size()];
		int i = 0;
		for (Vertex v : this.vertices)
			yPoints[i++] = v.y;
		return yPoints;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((brushColor == null) ? 0 : brushColor.hashCode());
		result = prime * result + ((vertices == null) ? 0 : vertices.hashCode());
		result = prime * result + thickness;
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
		Polygon other = (Polygon) obj;
		if (brushColor == null)
		{
			if (other.brushColor != null)
				return false;
		}
		else if (!brushColor.equals(other.brushColor))
			return false;
		if (vertices == null)
		{
			if (other.vertices != null)
				return false;
		}
		else if (!vertices.equals(other.vertices))
			return false;
		if (thickness != other.thickness)
			return false;
		return true;
	}

	public static boolean isPolygone(ArrayList<Vertex> vertices)
	{
		if (vertices == null)
			return false;
		if (vertices.size() < 3)
			return false;
		Vertex u = vertices.get(0);
		Vertex v = vertices.get(vertices.size() - 1);
		int dist = dist2(u.x, u.y, v.x, v.y);
		System.out.println("Distance between two end poinsts: " + dist);
		if (dist <= 300)
		{
			vertices.set(vertices.size() - 1, u);
			return true;
		}
		return false;
	}

	private static int dist2(int x1, int y1, int x2, int y2)
	{
		int x = x1 - x2;
		int y = y1 - y2;
		return x * x + y * y;
	}
	
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(vertices.size());
		sb.append(" ");
		for(Vertex v : vertices)
		{
			sb.append(v);
			sb.append(" ");
		}
		sb.append(thickness);
		sb.append(" ");
		sb.append(toString(brushColor));
		sb.append(" ");
		sb.append(toString(fillColor));
		return sb.toString();
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