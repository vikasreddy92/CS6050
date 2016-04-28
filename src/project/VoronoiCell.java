package project;

import java.awt.Color;
import java.util.ArrayList;

public class VoronoiCell
{
	ArrayList<Vertex> vertices;
	Color color;

	public VoronoiCell(ArrayList<Vertex> vertices, Color color)
	{
		this.vertices = vertices;
		this.color = color;
	}
	
}
