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
	static int BORDER = 100;
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
			if (line.equals("node"))
			{
				float rComp = Float.parseFloat(st.nextToken());
				float gComp = Float.parseFloat(st.nextToken());
				float bComp = Float.parseFloat(st.nextToken());
				int x = Integer.parseInt(st.nextToken());
				int y = Integer.parseInt(st.nextToken());
				y = ymax - y;
				check(x, y);
				data.append(x + " " + y + " " + rComp + " " + gComp + " " + bComp + " " + line + "\n");
			}
			else if (line.equals("edge"))
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
				check(x1, y1);
				check(x2, y2);
				data.append(x1 + " " + y1 + " " + x2 + " " + y2 + " " + thickness + " " + rComp + " " + gComp
						+ " " + bComp + " " + line + "\n");
			}
			else if (line.equals("circle"))
			{
				float rBrushComp = Float.parseFloat(st.nextToken()); // for
				float gBrushComp = Float.parseFloat(st.nextToken());
				float bBrushComp = Float.parseFloat(st.nextToken());
				/*
				 * float rFillComp = Float.parseFloat(st.nextToken()); // for float gFillComp = Float.parseFloat(st.nextToken()); float bFillComp =
				 * Float.parseFloat(st.nextToken());
				 */
				int thickness = Integer.parseInt(st.nextToken());
				int x = Integer.parseInt(st.nextToken());
				int y = Integer.parseInt(st.nextToken());
				int radius = Integer.parseInt(st.nextToken());
				y = ymax - y;
				check(x + radius, y + radius);
				check(x - radius, y - radius);
				data.append(x + " " + y + " " + radius + " " + thickness + " " + rBrushComp + " " + gBrushComp
						+ " " + bBrushComp + " " + line + "\n");
			}
			else if (line.equals("rectangle"))
			{
				float rBrushComp = Float.parseFloat(st.nextToken()); // for
				float gBrushComp = Float.parseFloat(st.nextToken());
				float bBrushComp = Float.parseFloat(st.nextToken());
				int thickness = Integer.parseInt(st.nextToken());
				int startX = Integer.parseInt(st.nextToken());
				int startY = Integer.parseInt(st.nextToken());
				startY = ymax - startY;
				data.append("%rectangle" + "\n");
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
					check(x1, y1);
					check(x1, y1);
					data.append(x1 + " " + y1 + " " + x2 + " " + y2 + " " + "lineto" + "\n");
				}
				data.append("closepath\n");
				data.append(rBrushComp + " " + gBrushComp + " " + bBrushComp + " " + "setrgbcolor" + " "
						+ thickness + " " + "setlinewidth" + " " + "stroke\n");
				/*
				 * float rBrushComp = Float.parseFloat(st.nextToken()); // for float gBrushComp = Float.parseFloat(st.nextToken()); float bBrushComp =
				 * Float.parseFloat(st.nextToken()); float rFillComp = Float.parseFloat(st.nextToken()); // for float gFillComp = Float.parseFloat(st.nextToken()); float bFillComp
				 * = Float.parseFloat(st.nextToken()); int thickness = Integer.parseInt(st.nextToken()); int startX = Integer.parseInt(st.nextToken()); int startY =
				 * Integer.parseInt(st.nextToken()); startY = ymax - startY; data.append("%rectangle " + "\n"); data.append("newpath\n"); data.append(startX + " " + startY + " " +
				 * "moveto" + "\n"); for (int i = 0; i < 4; i++) { int x1 = Integer.parseInt(st.nextToken()); int y1 = Integer.parseInt(st.nextToken()); int x2 =
				 * Integer.parseInt(st.nextToken()); int y2 = Integer.parseInt(st.nextToken()); y1 = ymax - y1; y2 = ymax - y2; data.append(x1 + " " + y1 + " " + x2 + " " + y2 +
				 * " " + "lineto" + "\n"); } data.append("closepath\n"); data.append("gsave" + " " + rFillComp + " " + gFillComp + " " + bFillComp + " " + "setrgbcolor" + " " +
				 * "fill" + " " + "grestore\n"); data.append(rBrushComp + " " + gBrushComp + " " + bBrushComp + " " + "setrgbcolor" + " " + thickness + " " + "setlinewidth" + " " +
				 * "stroke\n");
				 */
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
					check(x1, y1);
					check(x2, y2);
					data.append(x1 + " " + y1 + " " + x2 + " " + y2 + " " + "lineto" + "\n");
				}
				data.append("closepath\n");
				data.append("gsave" + " " + rFillComp + " " + gFillComp + " " + bFillComp + " "
						+ "setrgbcolor" + " " + "fill" + " " + "grestore\n");
				data.append(rBrushComp + " " + gBrushComp + " " + bBrushComp + " " + "setrgbcolor" + " "
						+ thickness + " " + "setlinewidth" + " " + "stroke\n");
			}
		}
		sb.append("%!PS-Adobe-3.0 EPSF-3.0\n");
		sb.append("%%BoundingBox: " + (xmin - BORDER) + " " + (ymin - BORDER) + " " + (xmax + BORDER) + " "
				+ (ymax + BORDER) + "\n");
		sb.append("/node { setrgbcolor newpath 15 0 360 arc fill } def\n");
		sb.append("/edge { setrgbcolor setlinewidth newpath moveto lineto stroke } def\n");
		sb.append("/circle { setrgbcolor setlinewidth newpath 0 360 arc stroke } def\n");
		sb.append("/rectangle { setrgbcolor setlinewidth newpath rectstroke } def\n");
		sb.append(data.toString());
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
			String str = null;
			br.readLine();
			String bb = br.readLine();
			int ymax = Integer.parseInt(bb.split(" ")[4]) - BORDER;
			br.readLine();
			br.readLine();
			br.readLine();
			br.readLine();
			while ((str = br.readLine()) != null)
			{
				StringTokenizer st = new StringTokenizer(str);
				if (str.endsWith("node"))
				{
					System.out.println("Creating node!");
					int x = Integer.parseInt(st.nextToken());
					int y = Integer.parseInt(st.nextToken());
					y = ymax - y;
					float redComp = Float.parseFloat(st.nextToken());
					float greenComp = Float.parseFloat(st.nextToken());
					float blueComp = Float.parseFloat(st.nextToken());
					Color color = new Color(redComp, greenComp, blueComp);
					editor.data.add(new Node(x, y, color));
				}
				if (str.endsWith("edge"))
				{
					System.out.println("Creating edge!");
					int x1 = Integer.parseInt(st.nextToken());
					int y1 = Integer.parseInt(st.nextToken());
					int x2 = Integer.parseInt(st.nextToken());
					int y2 = Integer.parseInt(st.nextToken());
					y1 = ymax - y1;
					y2 = ymax - y2;
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
					editor.data.add(new Circle(new Vertex(x, y), radius, thickness, color));
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
					/*
					 * float rFillComp = Float.parseFloat(st.nextToken()); // for float gFillComp = Float.parseFloat(st.nextToken()); float bFillComp =
					 * Float.parseFloat(st.nextToken());
					 */
					st = new StringTokenizer(br.readLine()); // 8
					float rBrushComp = Float.parseFloat(st.nextToken()); // for
					float gBrushComp = Float.parseFloat(st.nextToken());
					float bBrushComp = Float.parseFloat(st.nextToken());
					st.nextToken();
					int thickness = Integer.parseInt(st.nextToken());
					// Color fillColor = new Color(rFillComp, gFillComp,
					// bFillComp);
					Color brushColor = new Color(rBrushComp, gBrushComp, bBrushComp);
					editor.data.add(
							new Rectangle(new Vertex(xP[0], yP[0]), width, height, thickness, brushColor));
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
	}

	public static void WriteVoronoiToFile(ArrayList<VoronoiCell> vd)
	{
		try
		{
			StringBuffer sb = new StringBuffer();
			File f = new File("C:/Users/vikas/Desktop/Exports/vor1.eps");
			FileWriter fw = new FileWriter(f);
			HashMap<String, Color> map = new HashMap<>();
			for (VoronoiCell v : vd)
			{
				for (Vertex u : v.vertices)
					map.put(u.x + "\\_" + u.y, v.color);
			}
			sb.append("%!PS-Adobe-3.0 EPSF-3.0\n");
			sb.append("%%BoundingBox: 0 0 1200 750\n");
			sb.append("10 10 translate\n");
			sb.append("1105 690 scale");
			sb.append("/picstr 6 string def\n");
			sb.append("1105 690 8 [1105 0 0 -690 0 690]\n");
			sb.append("{ currentfile picstr readhexstring pop}\n");
			sb.append("false 3\n");
			sb.append("colorimage\n");
			for (int i = 0; i < 1105; i++)
			{
				for (int j = 0; j < 690; j++)
				{
					Color col = map.get(i + "\\_" + j);
//					fw.append(String.valueOf(createRGBA(col.getRed(), col.getGreen(), col.getBlue())));
//					sb.append(Integer.valueOf(String.valueOf(createRGBA(col.getRed(), col.getGreen(), col.getBlue())), 16));
//					sb.append(Integer.toHexString(createRGBA(col.getRed(), col.getGreen(), col.getBlue())));
					sb.append(Integer.toHexString(col.getRGB()).substring(2));
//					sb.append(String.format("%02x%02x%02x", col.getRed(), col.getGreen(), col.getBlue()));
				}
			}
			sb.append("\nshowpage\n");
			fw.write(sb.toString());
			fw.flush();
			fw.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static int createRGBA(int r, int g, int b)// , int a)
	{
		return ((r & 0xff) << 24) + ((g & 0xff) << 16) + ((b & 0xff) << 8);
		// + (a & 0xff);
	}
}