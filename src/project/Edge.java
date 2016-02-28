package project;

import java.awt.Color;

public class Edge {

	Vertex u, v;
	int thickness;
	Color color;

	Edge(Vertex u, Vertex v, int thickness, Color color) {
		this.u = u;
		this.v = v;
		this.thickness = thickness;
		this.color = color;
	}

	@Override
	public int hashCode() {
		return u.hashCode() + v.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		Edge e = (Edge) o;
		return u == e.u && v == e.v || u == e.v && v == e.u;
	}

	@Override
	public String toString() {
		float MAX_COLOR = 255f;
		float redComp = ((float) color.getRed())/MAX_COLOR;
		float greenComp = ((float) color.getGreen())/MAX_COLOR;
		float blueComp = ((float) color.getBlue())/MAX_COLOR;
		return redComp + " " + greenComp + " " + blueComp + " " + thickness + " " + u + " " + v + " ";
	}
}