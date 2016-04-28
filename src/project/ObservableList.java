package project;

import java.util.ArrayList;

public class ObservableList
{
	private ArrayList<Vertex> list;
	private Editor editor;

	public ObservableList(Editor editor)
	{
		this.editor = editor;
		this.list = new ArrayList<Vertex>();
	}

	public void add(Vertex t)
	{
		list.add(t);
		editor.data.convexHull = AlgorithmsCreator.createConvexHull(this.list);
		editor.refresh();
	}

	public void addAll(ArrayList<Vertex> t)
	{
		list.addAll(t);
		editor.data.convexHull = AlgorithmsCreator.createConvexHull(this.list);
		editor.refresh();
	}

	public void addAll(Vertex...t)
	{
		for(Vertex v : t)
			this.add(v);
	}

	public void remove(Vertex t)
	{
		this.list.remove(t);
		editor.data.convexHull = AlgorithmsCreator.createConvexHull(this.list);
		editor.refresh();
	}

	public void removeAll(ArrayList<Vertex> t)
	{
		list.removeAll(t);
		editor.data.convexHull = AlgorithmsCreator.createConvexHull(this.list);
		editor.refresh();
	}
	
	public int size()
	{
		return list.size();
	}

	public void clear()
	{
		this.list.clear();
	}

	public ArrayList<Vertex> getList()
	{
		return this.list;
	}

	public void add(Node n)
	{
		this.add(new Vertex(n.x, n.y));
	}

	public void add(Edge e)
	{
//		this.addAll(e.u, e.v);
		this.add(e.u);
		this.add(e.v);
	}

	public void add(Rectangle rect)
	{
		this.add(new Vertex(rect.origin.x, rect.origin.y));
		this.add(new Vertex(rect.origin.x + rect.width, rect.origin.y));
		this.add(new Vertex(rect.origin.x + rect.width, rect.origin.y + rect.height));
		this.add(new Vertex(rect.origin.x, rect.origin.y + rect.height));
	}

	public void add(Polygon polygon)
	{
		this.addAll(polygon.vertices);
	}

	public void remove(Node nearestNode)
	{
		this.remove(new Vertex(nearestNode.x, nearestNode.y));
	}

	public void remove(Edge nearestEdge)
	{
		this.remove(nearestEdge.u);
		this.remove(nearestEdge.v);
	}

	public void remove(Rectangle nearestRectangle)
	{
		this.remove(new Vertex(nearestRectangle.origin.x, nearestRectangle.origin.y));
		this.remove(new Vertex(nearestRectangle.origin.x + nearestRectangle.width, nearestRectangle.origin.y));
		this.remove(new Vertex(nearestRectangle.origin.x + nearestRectangle.width,
				nearestRectangle.origin.y + nearestRectangle.height));
		this.remove(new Vertex(nearestRectangle.origin.x, nearestRectangle.origin.y + nearestRectangle.height));
	}

	public void remove(Polygon nearestPolygon)
	{
		this.removeAll(nearestPolygon.vertices);
	}

}
