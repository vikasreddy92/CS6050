package project;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class FileIO
{
	static int xmin = Integer.MAX_VALUE;
	static int ymin = Integer.MAX_VALUE;
	static int xmax = Integer.MIN_VALUE;
	static int ymax = Integer.MIN_VALUE;
	Editor editor;

	public FileIO(Editor editor)
	{
		this.editor = editor;
	}

	void check(int x, int y)
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

	public void export(String text, String filePath)
	{
		setBoundingBox();
		System.out.println("Bounding box: (" + xmin + ", " + ymin + "), (" + xmax + ", " + ymax + ")");
		StringBuilder sb = new StringBuilder();
		StringBuilder data = new StringBuilder();

		String[] lines = text.split("\n");
		for (String line : lines)
		{
			StringTokenizer st = new StringTokenizer(line);
			line = st.nextToken();
			if (line.equals("edge"))
			{
				float rComp = Float.parseFloat(st.nextToken());
				float gComp = Float.parseFloat(st.nextToken());
				float bComp = Float.parseFloat(st.nextToken());
				int thickness = Integer.parseInt(st.nextToken());
				int x1 = Integer.parseInt(st.nextToken());
				int y1 = Integer.parseInt(st.nextToken());
				int x2 = Integer.parseInt(st.nextToken());
				int y2 = Integer.parseInt(st.nextToken());
				y1 = ymax - y1;
				y2 = ymax - y2;
				data.append(x1 + " " + y1 + " " + x2 + " " + y2 + " " + thickness + " " + rComp + " " + gComp + " "
						+ bComp + " " + line + "\n");
			}
			else if (line.equals("circle"))
			{
				float rComp = Float.parseFloat(st.nextToken());
				float gComp = Float.parseFloat(st.nextToken());
				float bComp = Float.parseFloat(st.nextToken());
				int thickness = Integer.parseInt(st.nextToken());
				int x = Integer.parseInt(st.nextToken());
				int y = Integer.parseInt(st.nextToken());
				int radius = Integer.parseInt(st.nextToken());
				y = ymax - y;
				data.append(x + " " + y + " " + radius + " " + thickness + " " + rComp + " " + gComp + " " + bComp + " "
						+ line + "\n");
			}
			else if (line.equals("rectangle"))
			{
				float rBrushComp = Float.parseFloat(st.nextToken()); // for
				float gBrushComp = Float.parseFloat(st.nextToken());
				float bBrushComp = Float.parseFloat(st.nextToken());
				float rFillComp = Float.parseFloat(st.nextToken()); // for
				float gFillComp = Float.parseFloat(st.nextToken());
				float bFillComp = Float.parseFloat(st.nextToken());
				int thickness = Integer.parseInt(st.nextToken());
				int startX = Integer.parseInt(st.nextToken());
				int startY = Integer.parseInt(st.nextToken());
				startY = ymax - startY;
				data.append("%rectangle " + "\n");
				data.append("newpath\n");
				data.append(startX + " " + startY + " " + "moveto" + "\n");
				for (int i = 0; i < 4; i++)
				{
					int x1 = Integer.parseInt(st.nextToken());
					int y1 = Integer.parseInt(st.nextToken());
					int x2 = Integer.parseInt(st.nextToken());
					int y2 = Integer.parseInt(st.nextToken());
					y1 = ymax - y1;
					y2 = ymax - y2;
					data.append(x1 + " " + y1 + " " + x2 + " " + y2 + " " + "lineto" + "\n");
				}
				data.append("closepath\n");
				data.append("gsave" + " " + rFillComp + " " + gFillComp + " " + bFillComp + " " + "setrgbcolor" + " "
						+ "fill" + " " + "grestore\n");
				data.append(rBrushComp + " " + gBrushComp + " " + bBrushComp + " " + "setrgbcolor" + " " + thickness
						+ " " + "setlinewidth" + " " + "stroke\n");
			}
			else if (line.equals("polygon"))
			{
				float rBrushComp = Float.parseFloat(st.nextToken()); // for
				float gBrushComp = Float.parseFloat(st.nextToken());
				float bBrushComp = Float.parseFloat(st.nextToken());
				float rFillComp = Float.parseFloat(st.nextToken()); // for
				float gFillComp = Float.parseFloat(st.nextToken());
				float bFillComp = Float.parseFloat(st.nextToken());
				int thickness = Integer.parseInt(st.nextToken());
				int numOfVertices = Integer.parseInt(st.nextToken());
				int startX = Integer.parseInt(st.nextToken());
				int startY = Integer.parseInt(st.nextToken());
				startY = ymax - startY;
				data.append("%polygon " + numOfVertices + "\n");
				data.append("newpath\n");
				data.append(startX + " " + startY + " " + "moveto" + "\n");
				for (int i = 0; i < numOfVertices; i++)
				{
					int x1 = Integer.parseInt(st.nextToken());
					int y1 = Integer.parseInt(st.nextToken());
					int x2 = Integer.parseInt(st.nextToken());
					int y2 = Integer.parseInt(st.nextToken());
					y1 = ymax - y1;
					y2 = ymax - y2;
					data.append(x1 + " " + y1 + " " + x2 + " " + y2 + " " + "lineto" + "\n");
				}
				data.append("closepath\n");
				data.append("gsave" + " " + rFillComp + " " + gFillComp + " " + bFillComp + " " + "setrgbcolor" + " "
						+ "fill" + " " + "grestore\n");
				data.append(rBrushComp + " " + gBrushComp + " " + bBrushComp + " " + "setrgbcolor" + " " + thickness
						+ " " + "setlinewidth" + " " + "stroke\n");
			}
		}
		sb.append("%!PS-Adobe-3.0 EPSF-3.0\n");
		sb.append("%%BoundingBox: " + (xmin - 40) + " " + (ymin - 40) + " " + (xmax + 40) + " " + (ymax + 40) + "\n");
		sb.append("/edge { setrgbcolor setlinewidth newpath moveto lineto stroke } def\n");
		sb.append("/circle { setrgbcolor setlinewidth newpath 0 360 arc stroke } def\n");
		sb.append(data.toString());
		try
		{
			FileWriter fw = new FileWriter(new File(filePath));
			fw.write(sb.toString());
			fw.close();
		}
		catch (IOException e)
		{
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
			String str = null;
			br.readLine();
			String bb = br.readLine();
			int ymax = Integer.parseInt(bb.split(" ")[4]);
			br.readLine();
			br.readLine();
			while ((str = br.readLine()) != null)
			{
				StringTokenizer st = new StringTokenizer(str);
				if (str.endsWith("edge"))
				{
					System.out.println("Creating edge!");
					int x1 = Integer.parseInt(st.nextToken());
					int y1 = Integer.parseInt(st.nextToken());
					int x2 = Integer.parseInt(st.nextToken());
					int y2 = Integer.parseInt(st.nextToken());
					y1 = ymax - y1 - 40;
					y2 = ymax - y2 - 40;
					int thickness = Integer.parseInt(st.nextToken());
					float redComp = Float.parseFloat(st.nextToken());
					float greenComp = Float.parseFloat(st.nextToken());
					float blueComp = Float.parseFloat(st.nextToken());
					Color color = new Color(redComp, greenComp, blueComp);
					editor.data.add(new Edge(new Vertex(x1, y1), new Vertex(x2, y2), thickness, color));
				}
				else if (str.endsWith("circle"))
				{
					System.out.println("Creating circle!");
					int x = Integer.parseInt(st.nextToken());
					int y = Integer.parseInt(st.nextToken());
					y = ymax - y;
					int radius = Integer.parseInt(st.nextToken());
					int thickness = Integer.parseInt(st.nextToken());
					float redComp = Float.parseFloat(st.nextToken());
					float greenComp = Float.parseFloat(st.nextToken());
					float blueComp = Float.parseFloat(st.nextToken());
					Color color = new Color(redComp, greenComp, blueComp);
					editor.data.add(new Circle(new Vertex(x, y), radius, thickness, color, Color.WHITE));
				}
				else if (str.startsWith("%rectangle"))
				{
					br.readLine(); // 1
					br.readLine();
					System.out.println("Creating rectangle!"); // 2
					int[] xP = new int[4];
					int[] yP = new int[4];
					for (int i = 0; i < 4; i++) // 6
					{
						st = new StringTokenizer(br.readLine());
						xP[i] = Integer.parseInt(st.nextToken());
						yP[i] = Integer.parseInt(st.nextToken());
						yP[i] = ymax - yP[i];
						st.nextToken();
						st.nextToken();
					}
					int width = Math.abs(xP[2] - xP[0]);
					int height = Math.abs(yP[2] - yP[0]);
					br.readLine(); // 7
					st = new StringTokenizer(br.readLine());
					st.nextToken();
					float rFillComp = Float.parseFloat(st.nextToken()); // for
					float gFillComp = Float.parseFloat(st.nextToken());
					float bFillComp = Float.parseFloat(st.nextToken());
					st = new StringTokenizer(br.readLine()); // 8
					float rBrushComp = Float.parseFloat(st.nextToken()); // for
					float gBrushComp = Float.parseFloat(st.nextToken());
					float bBrushComp = Float.parseFloat(st.nextToken());
					st.nextToken();
					int thickness = Integer.parseInt(st.nextToken());
					Color fillColor = new Color(rFillComp, gFillComp, bFillComp);
					Color brushColor = new Color(rBrushComp, gBrushComp, bBrushComp);
					editor.data.add(
							new Rectangle(new Vertex(xP[0], yP[0]), width, height, thickness, brushColor, fillColor));
				}
				else if (str.startsWith("%polygon"))
				{
					st.nextToken();
					int temp = Integer.parseInt(st.nextToken());
					br.readLine(); // 1
					br.readLine();
					System.out.println("Creating polygon!"); // 2
					int[] xP = new int[temp];
					int[] yP = new int[temp];
					for (int i = 0; i < temp; i++) // 6
					{
						st = new StringTokenizer(br.readLine());
						xP[i] = Integer.parseInt(st.nextToken());
						yP[i] = Integer.parseInt(st.nextToken());
						yP[i] = ymax - yP[i];
					}
					br.readLine(); // 7
					st = new StringTokenizer(br.readLine());
					st.nextToken();
					float rFillComp = Float.parseFloat(st.nextToken()); // for
					float gFillComp = Float.parseFloat(st.nextToken());
					float bFillComp = Float.parseFloat(st.nextToken());
					st = new StringTokenizer(br.readLine()); // 8
					float rBrushComp = Float.parseFloat(st.nextToken()); // for
					float gBrushComp = Float.parseFloat(st.nextToken());
					float bBrushComp = Float.parseFloat(st.nextToken());
					st.nextToken();
					int thickness = Integer.parseInt(st.nextToken());
					Color fillColor = new Color(rFillComp, gFillComp, bFillComp);
					Color brushColor = new Color(rBrushComp, gBrushComp, bBrushComp);
					editor.data.add(new Polygon(xP, yP, thickness, brushColor, fillColor));
				}
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

	public void setBoundingBox()
	{
		for (Edge e : editor.data.edges)
		{
			check(e.u.x + e.thickness, e.u.y + e.thickness);
			check(e.v.x + e.thickness, e.v.y + e.thickness);
			check(e.u.x - e.thickness, e.u.y - e.thickness);
			check(e.v.x - e.thickness, e.v.y - e.thickness);
		}
		for (Circle c : editor.data.circles)
		{
			check(c.center.x + c.radius + c.thickness, c.center.y + c.radius + c.thickness);
			check(c.center.x - c.radius - c.thickness, c.center.y - c.radius - c.thickness);
		}
		for (Rectangle r : editor.data.rectangles)
		{
			check(r.origin.x + r.width + r.thickness, r.origin.y + r.height + r.thickness);
			check(r.origin.x - r.width - r.thickness, r.origin.y + -r.height - r.thickness);
		}
		for (Polygon p : editor.data.polygons)
		{
			for (Vertex v : p.vertices)
			{
				check(v.x - p.thickness, v.y - p.thickness);
				check(v.x + p.thickness, v.y + p.thickness);
			}
		}

	}
}