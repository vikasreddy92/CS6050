package project;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class VoronoiDiagram
{
	private static int WIDTH = 1105; // 1474;
	private static int HEIGHT = 690; // 923;
	private static Vertex[][] nearest = new Vertex[WIDTH][HEIGHT];
	private static ArrayList<VoronoiCell> voronoi = new ArrayList<VoronoiCell>();

	public static ArrayList<VoronoiCell> getVoronoiDiagram(ArrayList<Node> nodes)
	{
		
		if (voronoi.isEmpty())
			return createVoronoiDiagram(nodes);
		else
			return updateVoronoiDiagram(nodes.get(nodes.size() - 1));
	}

	@SuppressWarnings("unchecked")
	public static ArrayList<VoronoiCell> createVoronoiDiagram(ArrayList<Node> nodes)
	{
		// p = v, q = u
		ArrayList<Vertex> vertices = new ArrayList<>();
		for (Node n : nodes)
			vertices.add(new Vertex(n.x, n.y));

		ArrayList<Vertex> vd = new ArrayList<>();
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
			Random rand = new Random();
			float r = (float) (rand.nextFloat() / 2f + 0.4);
			float g = (float) (rand.nextFloat() / 2f + 0.4);
			float b = (float) (rand.nextFloat() / 2f + 0.4);
			Color col = new Color(r, g, b);
			voronoi.add(new VoronoiCell((ArrayList<Vertex>) vd.clone(), col));
			vd.clear();
		}
		return voronoi;
	}

	@SuppressWarnings("unchecked")
	public static ArrayList<VoronoiCell> updateVoronoiDiagram(Node n)
	{
		Vertex v = new Vertex(n.x, n.y);
		ArrayList<Vertex> vd = new ArrayList<>();
		for (int i = 0; i < WIDTH; i++)
		{
			for (int j = 0; j < HEIGHT; j++)
			{
				Vertex u = new Vertex(i, j);
				if (nearest[i][j] == null || dist2(u, v) < dist2(u, nearest[i][j]))
				{
					nearest[i][j] = v;
					vd.add(new Vertex(i, j));
				}
			}
		}
		Random rand = new Random();
		float r = (float) (rand.nextFloat() / 2f + 0.4);
		float g = (float) (rand.nextFloat() / 2f + 0.4);
		float b = (float) (rand.nextFloat() / 2f + 0.4);
		Color col = new Color(r, g, b);
		voronoi.add(new VoronoiCell((ArrayList<Vertex>) vd.clone(), col));
		vd.clear();
		return voronoi;
	}

	private static double dist2(Vertex u, Vertex v)
	{
		double dx = u.x - v.x;
		double dy = u.y - v.y;
		return Math.sqrt(dx * dx + dy * dy);
	}
	
	public static void clear()
	{
		for(VoronoiCell vc : voronoi)
			vc.vertices.clear();
		voronoi.clear();
		nearest = null;
		nearest = new Vertex[WIDTH][HEIGHT];
	}
}
