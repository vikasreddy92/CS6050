package project;

import java.util.ArrayList;

public class Data {

	ArrayList<Vertex> vertices = new ArrayList<Vertex>();
	ArrayList<Edge> edges = new ArrayList<Edge>();
	ArrayList<Circle> circles = new ArrayList<Circle>();
	ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>();
	Vertex p;
	Circle c;
	Editor editor;

	Data(Editor editor) {
		this.editor = editor;
	}

	void add(int x, int y) {
		vertices.add(new Vertex(x, y));
	}

	void add(Vertex center, int radius) {
		circles.add(new Circle(center, radius));
	}

	void add(Vertex origin, int width, int height) {
		rectangles.add(new Rectangle(origin, width, height));
	}
	
	void add(Rectangle rect) {
		rectangles.add(rect);
	}
	
	void remove(int x, int y) {
		Vertex p = findVertex(x, y);
		if (p != null) {
			for (Edge e : edges)
				if (e.u == p || e.v == p)
					edges.remove(e);
			vertices.remove(p);
		}
	}

	public void removeCircle(int x, int y) {
		Circle circle = findCircle(x, y);
		if (circle != null)
			circles.remove(circle);
	}

	public Circle moveCircle(int x, int y) {
		Circle circle = findCircle(x, y);
		c = circle;
		return circle;
	}

	void move(int x, int y) {
		if (p == null)
			p = findVertex(x, y);
		else {
			Vertex q = findVertex(x, y);
			if (q != null && q.x == x && q.y == y) { // merge?
			} else {
				vertices.remove(p);
				p.x = x;
				p.y = y;
				vertices.add(p);
			}
			p = null;
		}
	}

	void mark(int x, int y) {
		Vertex q = findVertex(x, y);
		if (p == null)
			p = q;
		else if (q != null) {
			if (Box.mode == Box.AE)
				edges.add(new Edge(p, q));
			else if (Box.mode == Box.RE)
				edges.remove(new Edge(p, q));
			p = null;
		}
	}

	int dist2(int x1, int y1, int x2, int y2) {
		int x = x1 - x2;
		int y = y1 - y2;
		return x * x + y * y;
	}

	Vertex findVertex(int x, int y) {
		Vertex nearestVertex = null;
		int distToNearestVertex = Integer.MAX_VALUE;

		for (Vertex p : vertices) {
			int d = dist2(p.x, p.y, x, y);
			if (distToNearestVertex > d) {
				distToNearestVertex = d;
				nearestVertex = p;
			}
		}

		return nearestVertex;
	}

	public Circle findCircle(int x, int y) {
		int min = Integer.MAX_VALUE;
		Circle circle = null;
		for (Circle c : circles) {
			int distanceFromOrigin = dist2(c.center.x, c.center.y, x, y);
			int distanceFromCircumference = Math.abs(c.radius - distanceFromOrigin);
			if (min > distanceFromCircumference) {
				min = distanceFromCircumference;
				circle = c;
			}
		}
		return circle;
	}

	public String toString() {
		return "Vertices: " + vertices.size() + " Edges: " + edges.size() + " Circles: " + circles.size() + " Rectangles: " + rectangles.size();
	}

	String toText() {
		StringBuilder sb = new StringBuilder();
		for (Vertex v : vertices)
			sb.append("vertex " + v + "\n");
		for (Edge e : edges)
			sb.append("edge " + e + "\n");
		for (Circle c : circles)
			sb.append("circle " + c + "\n");
		for (Rectangle r : rectangles)
			sb.append("rectangle " + r + "\n");
		return sb.toString();
	}

}