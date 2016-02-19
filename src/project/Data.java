package project;

import java.util.ArrayList;
import java.util.Arrays;

public class Data {

	ArrayList<Vertex> vertices = new ArrayList<Vertex>();
	ArrayList<Edge> edges = new ArrayList<Edge>();
	ArrayList<Circle> circles = new ArrayList<Circle>();
	ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>();

	Vertex p;
	Circle tempCircle;
	Rectangle tempRectangle;
	Edge tempEdge;
	Editor editor;

	Data(Editor editor) {
		this.editor = editor;
	}

	// Adds a vertex
	public void add(int x, int y) {
		vertices.add(new Vertex(x, y));
	}

	// Adds an edge
	public void add(Vertex start, Vertex end) {
		edges.add(new Edge(start, end));
	}

	// Adds a circle
	public void add(Vertex center, int radius) {
		circles.add(new Circle(center, radius));
	}

	// Adds a rectangle
	public void add(Vertex origin, int width, int height) {
		rectangles.add(new Rectangle(origin, width, height));
	}

	public void add(Rectangle rect) {
		rectangles.add(rect);
	}

	public void move(int x, int y) {
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
	
	public Circle moveCircle(int x, int y) {
		Circle circle = findCircle(x, y);
		tempCircle = circle;
		return circle;
	}

	public Rectangle moveRectangle(int x, int y) {
		Rectangle rectangle = findRectangle(x, y);
		tempRectangle = rectangle;
		return rectangle;
	}
	
	public void remove(int x, int y) {
		Vertex p = findVertex(x, y);
		if (p != null) {
			for (Edge e : edges)
				if (e.u == p || e.v == p)
					edges.remove(e);
			vertices.remove(p);
		}
	}
	
	public void removeLine(int x, int y) {
		Edge nearestEdge = findNearestEdge(x, y);
		if (tempEdge != null && tempEdge.equals(nearestEdge)) {
			edges.remove(nearestEdge);
			tempEdge = null;
		} else {
			tempEdge = nearestEdge;
		}
	}

	public void removeCircle(int x, int y) {
		Circle nearestCircle = findCircle(x, y);
		if (tempCircle != null && tempCircle.equals(nearestCircle)) {
			circles.remove(nearestCircle);
			tempCircle = null;
		} else {
			tempCircle = nearestCircle;
		}
	}

	public void removeRectangle(int x, int y) {
		Rectangle nearestRectangle = findRectangle(x, y);
		if(tempRectangle != null && tempRectangle.equals(nearestRectangle)) {
			rectangles.remove(nearestRectangle);
			tempRectangle = null;
		} else {
			tempRectangle = nearestRectangle;
		}
	}

	public void mark(int x, int y) {
		Vertex q = findVertex(x, y);
		if (p == null)
			p = q;
		else if (q != null) {
			if (editor.window.box.mode == editor.window.box.AE)
				edges.add(new Edge(p, q));
			else if (editor.window.box.mode == editor.window.box.RE)
				edges.remove(new Edge(p, q));
			p = null;
		}
	}
	
	public Vertex findVertex(int x, int y) {
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

	public Rectangle findRectangle(int x, int y) {
		int minDistance = Integer.MAX_VALUE;
		Rectangle rectangle = null;
		for(Rectangle r : rectangles) {
			int dist1 = dist2(x, y, r.origin.x, r.origin.y);
			int dist2 = dist2(x, y, r.origin.x + r.width, r.origin.y);
			int dist3 = dist2(x, y, r.origin.x, r.origin.y);
			int dist4 = dist2(x, y, r.origin.x, r.origin.y);
			
			int min = min(dist1, dist2, dist3, dist4);
			
			if(minDistance > min) {
				minDistance = min;
				rectangle = r;
			}
		}
		return rectangle;
	}
	
	public Edge findNearestEdge(int x, int y) {
		Edge nearestEdge = null;
		int minDist = Integer.MAX_VALUE;
		for (Edge e : edges) {
			int dist1 = dist2(e.u.x, e.u.y, x, y);
			int dist2 = dist2(e.u.x, e.u.y, x, y);
			int dist = Math.min(dist1, dist2);
			if(minDist > dist) {
				minDist = dist;
				nearestEdge = e;
			}
		}
		return nearestEdge;
	}

	public int dist2(int x1, int y1, int x2, int y2) {
		int x = x1 - x2;
		int y = y1 - y2;
		return x * x + y * y;
	}

	public int dist2line(Edge e, int x, int y) {
		int x1 = e.u.x;
		int y1 = e.u.y;
		int x2 = e.v.x;
		int y2 = e.v.y;
		int temp = Math.abs(((y2 - y1) * x - (x2 - x1) * y + x2 * y1 + y2 * x1));
		return (int) (temp / Math.sqrt(dist2(x1, y1, x2, y2)));
	}
	
	public String toString() {
		return "Vertices: " + vertices.size() + " Edges: " + edges.size() + " Circles: " + circles.size()
				+ " Rectangles: " + rectangles.size();
	}

	public String toText() {
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
	
	public int min(int a, int b, int c, int d) {
		int nums[] = {a, b, c, d};
		Arrays.sort(nums);
		return nums[0];
	}
}