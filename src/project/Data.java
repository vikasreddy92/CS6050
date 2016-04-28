package project;

import java.util.ArrayList;
import java.util.Arrays;

public class Data
{

	ArrayList<Node> nodes = new ArrayList<Node>();
	ArrayList<Edge> edges = new ArrayList<Edge>();
	ArrayList<Circle> circles = new ArrayList<Circle>();
	ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>();
	ArrayList<Polygon> polygons = new ArrayList<Polygon>();
	ArrayList<TextBox> textBoxes = new ArrayList<TextBox>();

	ObservableList vertices;
	Vertex[] convexHull;
	ArrayList<VoronoiCell> voronoiDiagram;

	Vertex p;
	Editor editor;

	Node tempNode;
	Circle tempCircle;
	Rectangle tempRectangle;
	Edge tempEdge;
	Polygon tempPolygon;

	Data(Editor editor)
	{
		this.editor = editor;
		this.vertices = new ObservableList(this.editor);
	}

	// Adds a vertex
	public void add(int x, int y)
	{
		vertices.add(new Vertex(x, y));
	}

	public void add(Node n)
	{
		vertices.add(n);
		nodes.add(n);
	}
	// Adds an edge

	public void add(Edge e)
	{
		vertices.add(e);
		edges.add(e);
	}

	// Adds a circle
	public void add(Circle circle)
	{
		circles.add(circle);
	}

	// Adds a rectangle
	public void add(Rectangle rect)
	{
		vertices.add(rect);
		rectangles.add(rect);
	}

	public void add(Polygon polygon)
	{
		vertices.add(polygon);
		polygons.add(polygon);
	}

	public void add(TextBox textBox)
	{
		textBoxes.add(textBox);
	}

	public Node moveNode(int x, int y)
	{
		Node node = findNode(x, y);
		if (tempNode != null && tempNode.equals(node))
		{
			tempNode = null;
			return node;
		}
		tempNode = node;
		return null;
	}

	public Circle moveCircle(int x, int y)
	{
		Circle circle = findCircle(x, y);
		tempCircle = circle;
		return circle;
	}

	public Rectangle moveRectangle(int x, int y)
	{
		Rectangle rectangle = findRectangle(x, y);
		tempRectangle = rectangle;
		return rectangle;
	}

	public void removeNode(Node n)
	{
		nodes.remove(n);
	}

	public void removeNode(int x, int y)
	{
		Node nearestNode = findNode(x, y);
		if (tempNode != null && tempNode.equals(nearestNode))
		{
			vertices.remove(nearestNode);
			nodes.remove(nearestNode);
			tempNode = null;
		}
		else
		{
			tempNode = nearestNode;
		}
	}

	public void removeLine(int x, int y)
	{
		Edge nearestEdge = findNearestEdge(x, y);
		if (tempEdge != null && tempEdge.equals(nearestEdge))
		{
			edges.remove(nearestEdge);
			vertices.remove(nearestEdge);
			tempEdge = null;
		}
		else
		{
			tempEdge = nearestEdge;
		}
	}

	public void removeCircle(int x, int y)
	{
		Circle nearestCircle = findCircle(x, y);
		if (tempCircle != null && tempCircle.equals(nearestCircle))
		{
			circles.remove(nearestCircle);
			tempCircle = null;
		}
		else
		{
			tempCircle = nearestCircle;
		}
	}

	public void removeRectangle(int x, int y)
	{
		Rectangle nearestRectangle = findRectangle(x, y);
		if (tempRectangle != null && tempRectangle.equals(nearestRectangle))
		{
			rectangles.remove(nearestRectangle);
			vertices.remove(nearestRectangle);
			tempRectangle = null;
		}
		else
		{
			tempRectangle = nearestRectangle;
		}
	}

	public void removePolygon(int x, int y)
	{
		Polygon nearestPolygon = findPolygon(x, y);
		if (tempPolygon != null && tempPolygon.equals(nearestPolygon))
		{
			polygons.remove(nearestPolygon);
			vertices.remove(nearestPolygon);
			tempPolygon = null;
		}
		else
		{
			tempPolygon = nearestPolygon;
		}
	}

	public Node findNode(int x, int y)
	{
		Node nearestNode = null;
		int distToNearestNode = Integer.MAX_VALUE;
		for (Node n : nodes)
		{
			int d = dist2(n.x, n.y, x, y);
			if (distToNearestNode > d)
			{
				distToNearestNode = d;
				nearestNode = n;
			}
		}
		if (nearestNode != null)
			editor.window.objectProps.updateProperties(nearestNode);
		return nearestNode;
	}

	public Edge findNearestEdge(int x, int y)
	{
		Edge nearestEdge = null;
		int minDist = Integer.MAX_VALUE;
		for (Edge e : edges)
		{
			int dist = dist2line(e, x, y);
			if (minDist > dist)
			{
				minDist = dist;
				nearestEdge = e;
			}
		}
		return nearestEdge;
	}

	public Circle findCircle(int x, int y)
	{
		int min = Integer.MAX_VALUE;
		Circle circle = null;
		for (Circle c : circles)
		{
			int distanceFromOrigin = dist2(c.center.x, c.center.y, x, y);
			int distanceFromCircumference = Math.abs(c.radius - distanceFromOrigin);
			if (min > distanceFromCircumference)
			{
				min = distanceFromCircumference;
				circle = c;
			}
		}
		if (circle != null)
			editor.window.objectProps.updateProperties(circle);
		return circle;
	}

	public Rectangle findRectangle(int x, int y)
	{
		int minDistance = Integer.MAX_VALUE;
		Rectangle rectangle = null;
		for (Rectangle r : rectangles)
		{
			int dist1 = dist2(x, y, r.origin.x, r.origin.y);
			int dist2 = dist2(x, y, r.origin.x + r.width, r.origin.y);
			int dist3 = dist2(x, y, r.origin.x, r.origin.y);
			int dist4 = dist2(x, y, r.origin.x, r.origin.y);

			int min = min(dist1, dist2, dist3, dist4);

			if (minDistance > min)
			{
				minDistance = min;
				rectangle = r;
			}
		}
		if (rectangle != null)
			editor.window.objectProps.updateProperties(rectangle);
		return rectangle;
	}

	public Polygon findPolygon(int x, int y)
	{
		int minDistance = Integer.MAX_VALUE;
		Polygon polygon = null;
		for (Polygon p : polygons)
		{
			Vertex nearestVertex = findNearestVertex(x, y, p.vertices);
			int min = dist2(x, y, nearestVertex.x, nearestVertex.y);
			if (minDistance > min)
			{
				polygon = p;
				minDistance = min;
			}
		}
		return polygon;
	}

	public void createConvexHull()
	{
		vertices.clear();
		for (Node n : nodes)
			vertices.add(n);
		for (Edge e : edges)
			vertices.add(e);
		for (Rectangle r : rectangles)
			vertices.add(r);
		for (Polygon p : polygons)
			vertices.add(p);
		this.convexHull = AlgorithmsCreator.createConvexHull(vertices.getList());
	}

	public void createVoronoiDiagram()
	{
		this.voronoiDiagram = VoronoiDiagram.getVoronoiDiagram(nodes);
//		FileIO.WriteVoronoiToFile(voronoiDiagram);
	}

	/*
	 * public void move(int x, int y) { if (p == null) p = findVertex(x, y); else { Vertex q = findVertex(x, y); if (q != null && q.x == x && q.y == y) { // merge? } else {
	 * vertices.remove(p); p.x = x; p.y = y; vertices.add(p); } p = null; } }
	 */

	/*
	 * public void remove(int x, int y) { Vertex p = findVertex(x, y); if (p != null) { for (Edge e : edges) if (e.u == p || e.v == p) edges.remove(e); vertices.remove(p); } }
	 */

	/*
	 * public void mark(int x, int y) { Vertex q = findVertex(x, y); if (p == null) p = q; else if (q != null) { if (editor.window.box.mode == editor.window.box.AE) edges.add(new
	 * Edge(p, q)); else if (editor.window.box.mode == editor.window.box.RE) edges.remove(new Edge(p, q)); p = null; } }
	 */
	/*
	 * public Vertex findVertex(int x, int y) { Vertex nearestVertex = null; int distToNearestVertex = Integer.MAX_VALUE; for (Vertex p : vertices) { int d = dist2(p.x, p.y, x, y);
	 * if (distToNearestVertex > d) { distToNearestVertex = d; nearestVertex = p; } } return nearestVertex; }
	 */

	public Vertex findNearestVertex(int x, int y, ArrayList<Vertex> vertices)
	{
		Vertex nearestVertex = null;
		int minDist = Integer.MAX_VALUE;
		for (Vertex v : vertices)
		{
			int dist = dist2(v.x, v.y, x, y);
			if (minDist > dist)
			{
				minDist = dist;
				nearestVertex = v;
			}
		}
		return nearestVertex;
	}

	public int dist2(int x1, int y1, int x2, int y2)
	{
		int x = x1 - x2;
		int y = y1 - y2;
		return (int) Math.sqrt((x * x) + (y * y));
	}

	public int dist2line(Edge e, int x, int y)
	{
		int x1 = e.u.x;
		int y1 = e.u.y;
		int x2 = e.v.x;
		int y2 = e.v.y;
		int d1 = dist2(x, y, x1, y1);
		int d2 = dist2(x, y, x2, y2);
		return d1 < d2 ? d1 : d2;
	}

	public String toString()
	{
		return "Vertices: " + vertices.size() + " Nodes: " + nodes.size() + " Edges: " + edges.size()
				+ " Circles: " + circles.size() + " Rectangles: " + rectangles.size() + " Polygons: "
				+ polygons.size();
	}

	public String toText()
	{
		int size = nodes.size() + edges.size() + circles.size() + rectangles.size() + polygons.size()
				+ textBoxes.size() + 1;
		StringBuilder sb = new StringBuilder();
		sb.append("%" + size + "\n");
		for (Node v : nodes)
			sb.append("%node " + v + "\n");
		for (Edge e : edges)
			sb.append("%edge " + e + "\n");
		for (Circle c : circles)
			sb.append("%circle " + c + "\n");
		for (Rectangle r : rectangles)
			sb.append("%rectangle " + r + "\n");
		for (Polygon p : polygons)
			sb.append("%polygon " + p + "\n");
		for (TextBox t : textBoxes)
			sb.append("%text " + t + "\n");
		sb.append("%convexHull " + editor.window.board.convexHull + "\n");
		return sb.toString();
	}

	public int min(int a, int b, int c, int d)
	{
		int nums[] = { a, b, c, d };
		Arrays.sort(nums);
		return nums[0];
	}

	public void clearAll()
	{
		editor.window.board.convexHull = false;
		editor.window.board.voronoiDiagram = false;
		if (nodes != null)
			nodes.clear();
		if (edges != null)
			edges.clear();
		if (circles != null)
			circles.clear();
		if (rectangles != null)
			rectangles.clear();
		if (polygons != null)
			polygons.clear();
		if (vertices != null)
			vertices.clear();
		if (convexHull != null)
			convexHull = null;
		if (voronoiDiagram != null)
		{
			for(VoronoiCell vc : voronoiDiagram)
				vc.vertices.clear();
			voronoiDiagram.clear();
			VoronoiDiagram.clear();
		}
		if (textBoxes != null)
			textBoxes.clear();
	}
}