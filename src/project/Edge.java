package project;

import java.awt.Color;

public class Edge
{

	Vertex u, v;
	int thickness;
	Color color;

	Edge(Vertex u, Vertex v, int thickness, Color color)
	{
			this.u = u;
			this.v = v;
			this.thickness = thickness;
			this.color = color;
	}

	private boolean check(Vertex origin2)
	{
		if (origin2.x == Integer.MIN_VALUE || origin2.y == Integer.MIN_VALUE || origin2.x == Integer.MAX_VALUE
				|| origin2.y == Integer.MAX_VALUE)
			return false;
		return true;
	}
	
	@Override
	public int hashCode()
	{
		return u.hashCode() + v.hashCode();
	}

	@Override
	public boolean equals(Object o)
	{
		Edge e = (Edge) o;
		return u == e.u && v == e.v || u == e.v && v == e.u;
	}

	@Override
	public String toString()
	{
		return u + " " + v + " " + thickness + " " + toString(color);
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