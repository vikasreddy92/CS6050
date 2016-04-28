package project;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class AlgorithmsCreator
{
	static Vertex origin;

	public static Vertex[] createConvexHull(ArrayList<Vertex> vertices)
	{
		int n = vertices.size();
		if (n == 0)
			return null;
		Vertex[] pp = new Vertex[n];
		pp = (Vertex[]) vertices.toArray(pp);
		if (n == 1)
			return pp;
		int j = 0;
		for (int i = 1; i < n; i++)
		{
			Vertex p = pp[j];
			Vertex q = pp[i];
			if (q.y < p.y)
				j = i;
		}
		Vertex t = pp[0];
		pp[0] = pp[j];
		pp[j] = t;
		origin = pp[0];
		Arrays.sort(pp, vertexComp);

		Vertex[] qq = new Vertex[n];
		qq[0] = pp[0];
		qq[1] = pp[1];
		int size = 2;
		for (int i = 2; i < n; i++)
		{
			while (size >= 2 && cross(qq[size - 2], qq[size - 1], pp[i]) >= 0)
				size--;
			qq[size++] = pp[i];
		}
		return Arrays.copyOf(qq, size);
	}

	@SuppressWarnings("unchecked")
	public static ArrayList<VoronoiCell> createVoronoiDiagram(ArrayList<Node> nodes)
	{
		// p = v, q = u
		ArrayList<Vertex> vertices = new ArrayList<>();
		for (Node n : nodes)
			vertices.add(new Vertex(n.x, n.y));

//		int SIZE = 10;

		int WIDTH = 1474;
		int HEIGHT = 923;
		
//		int WIDTH = Window.board_width;
//		int HEIGHT = Window.board_height;
		
		Vertex[][] nearest = new Vertex[WIDTH][HEIGHT];
		
//		Vertex[][] nearest = new Vertex[SIZE][SIZE];
		ArrayList<Vertex> vd = new ArrayList<>();
		ArrayList<VoronoiCell> voronoi = new ArrayList<>();
		for (Vertex v : vertices)
		{
			for (int i = 0; i < WIDTH; i++)
			{
				for (int j = 0; j < HEIGHT; j++)
				{
					Vertex u = new Vertex(i, j);
					if ((nearest[i][j] == null) || dist2(u, v) < dist2(u, nearest[i][j]))
					{
						nearest[i][j] = v;
						vd.add(new Vertex(i, j));
					}
				}
			}
			voronoi.add(new VoronoiCell((ArrayList<Vertex>)vd.clone(), Color.getHSBColor((float) Math.random(), 0.5f, 0.5f)));
			vd.clear();
		}
		return voronoi;
	}

	private static double dist2(Vertex u, Vertex v)
	{
		double dx = u.x - v.x;
		double dy = u.y - v.y;
		return Math.sqrt(dx * dx + dy * dy);
	}

	private static int cross(Vertex p1, Vertex p2, Vertex p3)
	{
		return (int) ((p2.x - p1.x) * (p3.y - p1.y) - (p3.x - p1.x) * (p2.y - p1.y));
	}

	private static Comparator<Vertex> vertexComp = new Comparator<Vertex>()
	{
		@Override
		public int compare(Vertex o1, Vertex o2)
		{
			return cross(origin, o1, o2);
		}
	};
}
