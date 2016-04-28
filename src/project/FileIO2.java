package project;

import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileIO2
{
	private static int xmin = Integer.MAX_VALUE;
	private static int ymin = Integer.MAX_VALUE;
	private static int xmax = Integer.MIN_VALUE;
	private static int ymax = Integer.MIN_VALUE;
	private static int BORDER = 200;
	private static String[] fontStyles = { "Plain", "Bold", "Italic" };
	Editor editor;

	public FileIO2(Editor editor)
	{
		this.editor = editor;
	}

	public void exportToEPS(String filePath)
	{
		setBoundingBox();

		System.out.println("Bounding box: (" + xmin + ", " + ymin + "), (" + xmax + ", " + ymax + ")");

		StringBuilder sb = new StringBuilder();
		StringBuilder data = new StringBuilder();

		for (Node n : editor.data.nodes)
		{
			int y = ymax - n.y;
			check(n.x, y);
			data.append(n.x + " " + y + " " + toString(n.brushColor) + " node\n");
		}

		for (Edge e : editor.data.edges)
		{
			int y1 = ymax - e.u.y;
			int y2 = ymax - e.v.y;
			check(e.u.x, y1);
			check(e.v.x, y2);
			data.append(e.u.x + " " + y1 + " " + e.v.x + " " + y2 + " " + e.thickness + " "
					+ toString(e.color) + " edge\n");
		}
		for (Circle c : editor.data.circles)
		{
			int x = c.getCenter().getX(), y = c.getCenter().getY(), radius = c.getRadius(),
					thickness = c.getThickness();
			y = ymax - y;
			check(x + radius + thickness, y + radius + thickness);
			check(x - radius - thickness, y - radius - thickness);
			data.append(x + " " + y + " " + radius + " " + thickness + " " + toString(c.brushColor)
					+ " draw_circle\n");
			data.append(x + " " + y + " " + (radius - thickness / 2) + " " + toString(c.fillColor)
					+ " fill_circle\n");
		}
		for (Rectangle r : editor.data.rectangles)
		{

			int x = r.getAllVertices().get(0).getX(), y = r.getAllVertices().get(0).getY(),
					thickness = r.getThickness();
			y = ymax - y;
			check(x, y);
			data.append("newpath\n");
			data.append(x + " " + y + " moveto\n");
			data.append(toString(r.getAllVertices()));
			data.append("closepath\n");
			data.append("gsave\n");
			data.append(toString(r.getFillColor()) + " setrgbcolor\nfill\ngrestore\n");
			data.append(thickness + " setlinewidth\n");
			data.append(toString(r.getBrushColor()) + " setrgbcolor\nstroke\n");

		}
		for (Polygon p : editor.data.polygons)
		{
			int x = p.vertices.get(0).getX(), y = p.vertices.get(0).getY(), thickness = p.getThickness();
			y = ymax - y;
			check(x, y);
			data.append("newpath\n");
			data.append(x + " " + y + " moveto\n");
			data.append(toString(p.getVertices()));
			data.append("closepath\n");
			data.append("gsave\n");
			data.append(toString(p.getFillColor()) + " setrgbcolor\nfill\ngrestore\n");
			data.append(thickness + " setlinewidth\n");
			data.append(toString(p.getBrushColor()) + " setrgbcolor\nstroke\n");
		}
		for (TextBox t : editor.data.textBoxes)
		{
			int x = t.getTextLocation().getX();
			int y = t.getTextLocation().getY();
			y = ymax - y;
			check(x, y);
			data.append("/" + t.getTextFont().getFontName().replace(" ", "-") + "-"
					+ fontStyles[t.getTextFont().getStyle()] + " findfont\n");
			data.append(t.getTextSize() + " scalefont\nsetfont\n");
			data.append(toString(t.getTextColor()) + " setrgbcolor\nnewpath\n");
			data.append(x + " " + y + " moveto\n");
			data.append("(" + t.getText() + ") show\n");
		}
		if (editor.window.board.convexHull)
		{
			Vertex[] hull = editor.data.convexHull;
			int len = hull.length;
			int x = hull[0].getX();
			int y = hull[0].getY();
			y = ymax - y;
			data.append("newpath\n");
			data.append(x + " " + y + " moveto\n");
			for (int i = 0; i < len - 1; i++)
			{
				int x1 = hull[i].getX(), y1 = hull[i].getY();
				int x2 = hull[i + 1].getX(), y2 = hull[i + 1].getY();
				y1 = ymax - y1;
				y2 = ymax - y2;
				data.append(x1 + " " + y1 + " " + x2 + " " + y2 + " 2 " + toString(Color.RED) + " edge\n");
			}
			int x1 = hull[len - 1].getX(), y1 = hull[len - 1].getY();
			int x2 = hull[0].getX(), y2 = hull[0].getY();
			y1 = ymax - y1;
			y2 = ymax - y2;
			data.append(x1 + " " + y1 + " " + x2 + " " + y2 + " 2 " + toString(Color.RED) + " edge\n");
		}
		sb.append("%!PS-Adobe-3.0 EPSF-3.0\n");
		sb.append("%%BoundingBox: " + (xmin - BORDER) + " " + (ymin - BORDER) + " " + (xmax + BORDER) + " "
				+ (ymax + BORDER) + "\n");
		sb.append(editor.data.toText());
		sb.append("/node { setrgbcolor newpath 15 0 360 arc fill } def\n");
		sb.append("/edge { setrgbcolor setlinewidth newpath moveto lineto stroke } def\n");
		sb.append("/draw_circle { setrgbcolor setlinewidth newpath 0 360 arc stroke } def\n");
		sb.append("/fill_circle { setrgbcolor newpath 0 360 arc fill } def\n");
		sb.append(data.toString());
		System.out.println(editor.data.toText());
		try
		{
			FileWriter fw = new FileWriter(new File(filePath));
			fw.write(sb.toString());
			fw.close();
			editor.window.message.setText("Saved successfully.");
		}
		catch (IOException e)
		{
			editor.window.message.setText("Saving failed.");
			e.printStackTrace();
		}
	}

	public void importToEditor(String filePath)
	{
		if (filePath == "" || !filePath.endsWith(".eps"))
		{
			System.out.println("Illegal file.");
			return;
		}
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(new File(filePath)));
			br.readLine();
			br.readLine();
			String str = br.readLine();
			int n = Integer.parseInt(str.substring(1, str.length()));
			System.out.println(n);
			for (int i = 0; i < n; i++)
			{
				str = br.readLine();
				if (str.startsWith("%node"))
					editor.data.add(getNode(str));
				else if (str.startsWith("%edge"))
					editor.data.add(getEdge(str));
				else if (str.startsWith("%circle"))
					editor.data.add(getCircle(str));
				else if (str.startsWith("%rectangle"))
					editor.data.add(getRectangle(str));
				else if (str.startsWith("%polygon"))
					editor.data.add(getPolygon(str));
				else if (str.startsWith("%text"))
					editor.data.add(getTextBox(str));
				else if (str.startsWith("%convexHull"))
					editor.window.board.convexHull = Boolean.parseBoolean(str.split(" ")[1]);
			}
			br.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private TextBox getTextBox(String str)
	{
		String[] arr = str.split(" ");
		String text = arr[1].replace("\\_", " ");
		int textSize = Integer.parseInt(arr[2]);
		int r = Integer.parseInt(arr[3]);
		int g = Integer.parseInt(arr[4]);
		int b = Integer.parseInt(arr[5]);
		int a = Integer.parseInt(arr[6]);
		String fontName = arr[7].replace("-", " ");
		int fontStyle = Integer.parseInt(arr[8]);
		int x = Integer.parseInt(arr[9]);
		int y = Integer.parseInt(arr[10]);
		return new TextBox(text, textSize, new Color(r, g, b, a), new Font(fontName, fontStyle, textSize),
				new Vertex(x, y));
	}

	private Polygon getPolygon(String str)
	{
		String[] arr = str.split(" ");
		int n = 2 * Integer.parseInt(arr[1]) + 2;
		ArrayList<Vertex> vertices = new ArrayList<Vertex>();
		int i = 2;
		while (i < n)
		{
			int x = Integer.parseInt(arr[i]);
			int y = Integer.parseInt(arr[i + 1]);
			System.out.println("FileIO2.java: " + x + ", " + y);
			vertices.add(new Vertex(x, y));
			i += 2;
		}
		int thickness = Integer.parseInt(arr[i++]);
		int br = Integer.parseInt(arr[i++]);
		int bg = Integer.parseInt(arr[i++]);
		int bb = Integer.parseInt(arr[i++]);
		int ba = Integer.parseInt(arr[i++]);
		int fr = Integer.parseInt(arr[i++]);
		int fg = Integer.parseInt(arr[i++]);
		int fb = Integer.parseInt(arr[i++]);
		int fa = Integer.parseInt(arr[i++]);
		return new Polygon(vertices, thickness, new Color(br, bg, bb, ba), new Color(fr, fg, fb, fa));
	}

	private Rectangle getRectangle(String str)
	{
		String[] arr = str.split(" ");
		int x = Integer.parseInt(arr[1]);
		int y = Integer.parseInt(arr[2]);
		int width = Integer.parseInt(arr[3]);
		int height = Integer.parseInt(arr[4]);
		int thickness = Integer.parseInt(arr[5]);
		int br = Integer.parseInt(arr[6]);
		int bg = Integer.parseInt(arr[7]);
		int bb = Integer.parseInt(arr[8]);
		int ba = Integer.parseInt(arr[9]);
		int fr = Integer.parseInt(arr[10]);
		int fg = Integer.parseInt(arr[11]);
		int fb = Integer.parseInt(arr[12]);
		int fa = Integer.parseInt(arr[13]);
		return new Rectangle(new Vertex(x, y), width, height, thickness, new Color(br, bg, bb, ba),
				new Color(fr, fg, fb, fa));
	}

	private Circle getCircle(String str)
	{
		String[] arr = str.split(" ");
		int x = Integer.parseInt(arr[1]);
		int y = Integer.parseInt(arr[2]);
		int radius = Integer.parseInt(arr[3]);
		int thickness = Integer.parseInt(arr[4]);
		int br = Integer.parseInt(arr[5]);
		int bg = Integer.parseInt(arr[6]);
		int bb = Integer.parseInt(arr[7]);
		int ba = Integer.parseInt(arr[8]);
		int fr = Integer.parseInt(arr[9]);
		int fg = Integer.parseInt(arr[10]);
		int fb = Integer.parseInt(arr[11]);
		int fa = Integer.parseInt(arr[12]);
		return new Circle(new Vertex(x, y), radius, thickness, new Color(br, bg, bb, ba),
				new Color(fr, fg, fb, fa));
	}

	private Edge getEdge(String str)
	{
		String[] arr = str.split(" ");
		int x1 = Integer.parseInt(arr[1]);
		int y1 = Integer.parseInt(arr[2]);
		int x2 = Integer.parseInt(arr[3]);
		int y2 = Integer.parseInt(arr[4]);
		int thickness = Integer.parseInt(arr[5]);
		;
		int r = Integer.parseInt(arr[6]);
		int g = Integer.parseInt(arr[7]);
		int b = Integer.parseInt(arr[8]);
		int a = Integer.parseInt(arr[9]);
		return new Edge(new Vertex(x1, y1), new Vertex(x2, y2), thickness, new Color(r, g, b, a));
	}

	private Node getNode(String str)
	{
		String[] arr = str.split(" ");
		int x = Integer.parseInt(arr[1]);
		int y = Integer.parseInt(arr[2]);
		int r = Integer.parseInt(arr[3]);
		int g = Integer.parseInt(arr[4]);
		int b = Integer.parseInt(arr[5]);
		int a = Integer.parseInt(arr[6]);
		return new Node(x, y, new Color(r, g, b, a));
	}

	private String toString(ArrayList<Vertex> allVertices)
	{
		StringBuffer sb = new StringBuffer();
		int n = allVertices.size();
		int x1, y1, x2, y2;
		for (int i = 0; i < n - 1; i++)
		{
			x1 = allVertices.get(i).getX();
			y1 = allVertices.get(i).getY();
			x2 = allVertices.get(i + 1).getX();
			y2 = allVertices.get(i + 1).getY();
			y1 = ymax - y1;
			y2 = ymax - y2;
			check(x1, y1);
			check(x2, y2);
			sb.append(x1 + " " + y1 + " " + x2 + " " + y2 + " lineto\n");
		}
		x1 = allVertices.get(n - 1).getX();
		y1 = allVertices.get(n - 1).getY();
		x2 = allVertices.get(0).getX();
		y2 = allVertices.get(0).getY();
		y1 = ymax - y1;
		y2 = ymax - y2;
		check(x1, y1);
		check(x2, y2);
		sb.append(x1 + " " + y1 + " " + x2 + " " + y2 + " lineto\n");
		return sb.toString();
	}

	private void check(int x, int y)
	{
		if (xmin > x)
			xmin = x;
		if (xmax < x)
			xmax = x;
		if (ymin > y)
			ymin = y;
		if (ymax < y)
			ymax = y;
	}

	private float[] RGBAtoRGB(Color color)
	{
		float[] source = color.getComponents(null);
		float[] target = new float[3];
		target[0] = ((1 - source[3]) * 1) + (source[3] * source[0]);
		target[1] = ((1 - source[3]) * 1) + (source[3] * source[1]);
		target[2] = ((1 - source[3]) * 1) + (source[3] * source[2]);
		return target;
	}

	private String toString(Color color)
	{
		float[] comp = RGBAtoRGB(color);
		return comp[0] + " " + comp[1] + " " + comp[2];
	}

	public void setBoundingBox()
	{
		for (Node n : editor.data.nodes)
		{
			check(n.x, n.y);
		}
		for (Edge e : editor.data.edges)
		{
			check(e.u.x, e.u.y);
			check(e.v.x, e.v.y);
		}
		for (Circle c : editor.data.circles)
		{
			check((c.center.x + c.radius), (c.center.y + c.radius));
			check((c.center.x - c.radius), (c.center.y - c.radius));
		}
		for (Rectangle r : editor.data.rectangles)
		{
			check(r.origin.x + r.width, r.origin.y + r.height);
			// check(r.origin.x - r.width, r.origin.y - r.height);
		}
		for (Polygon p : editor.data.polygons)
		{
			for (Vertex v : p.vertices)
			{
				check(v.x, v.y);
				check(v.x, v.y);
			}
		}
		int maxStr = Integer.MIN_VALUE;
		for (TextBox t : editor.data.textBoxes)
		{
			if (maxStr < t.getText().length())
				maxStr = t.getText().length();
			check(t.getTextLocation().getX(), t.getTextLocation().getY());
			check(t.getTextLocation().getX(), t.getTextLocation().getY());
		}
		if (editor.data.textBoxes.size() != 0)
		{
			xmin = xmin - maxStr * 15;
			xmax = xmax + maxStr * 15;
		}
	}

	@SuppressWarnings("unused")
	private float[] RGBtoHSB(Color color)
	{
		int r = color.getRed();
		int g = color.getGreen();
		int b = color.getBlue();
		float[] hsbvals = new float[3];
		Color.RGBtoHSB(r, g, b, hsbvals);
		return hsbvals;
	}
}
