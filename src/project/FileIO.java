package project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class FileIO {
	static int xmin = Integer.MAX_VALUE;
	static int ymin = Integer.MAX_VALUE;
	static int xmax = Integer.MIN_VALUE;
	static int ymax = Integer.MIN_VALUE;
	Editor editor;

	public FileIO(Editor editor) {
		this.editor = editor;
	}

	void check(int x, int y) {
		if (xmin > x)
			xmin = x;
		if (xmax < x)
			xmax = x;
		if (ymin > y)
			ymin = y;
		if (ymax < y)
			ymax = y;
	}

	public void export(String text, String filePath) {
		StringBuilder sb = new StringBuilder();
		StringBuilder data = new StringBuilder();

		String[] lines = text.split("\n");
		for (String line : lines) {
			StringTokenizer st = new StringTokenizer(line);
			line = st.nextToken();
			if (line.equals("edge")) {
				int x1 = Integer.parseInt(st.nextToken());
				int y1 = Integer.parseInt(st.nextToken());
				int x2 = Integer.parseInt(st.nextToken());
				int y2 = Integer.parseInt(st.nextToken());
				check(x1, y1);
				check(x2, y2);
				data.append(x1 + " " + y1 + " " + x2 + " " + y2 + " " + line + "\n");
			} else if (line.equals("vertex")) {
				int x = Integer.parseInt(st.nextToken());
				int y = Integer.parseInt(st.nextToken());
				check(x, y);
				data.append(x + " " + y + " " + line + "\n");
			} else if (line.equals("circle")) {
				int x = Integer.parseInt(st.nextToken());
				int y = Integer.parseInt(st.nextToken());
				int radius = Integer.parseInt(st.nextToken());
				check(x + radius, y + radius);
				data.append(x + " " + y + " " + radius + " " + line + "\n");
			} else if (line.equals("rectangle")) {
				int x = Integer.parseInt(st.nextToken());
				int y = Integer.parseInt(st.nextToken());
				int width = Integer.parseInt(st.nextToken());
				int height = Integer.parseInt(st.nextToken());
				check(x + height, y + width);
				data.append(x + " " + y + " " + width + " " + height + " " + line + "\n");
			}
		}
		sb.append("%!PS-Adobe-3.0 EPSF-3.0\n");
		sb.append("%%BoundingBox: " + (xmin - 10) + " " + (ymin - 10) + " " + (xmax + 10) + " " + (ymax + 10) + "\n");
		sb.append("/vertex { newpath 4 0 360 arc 0 setgray fill } def\n");
		sb.append("/edge { newpath moveto lineto stroke } def\n");
		sb.append("/circle { newpath 4 0 360 arc 0 setgray fill } def\n");
		sb.append("/rectangle { newpath rectstroke setgray fill } def\n");
		sb.append(data.toString());
		try {
			FileWriter fw = new FileWriter(new File(filePath));
			fw.write(sb.toString());
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void importToEditor(String filePath) {
		if (filePath == "" || !filePath.endsWith(".eps")) {
			System.out.println("Illegal file.");
			return;
		}
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(filePath)));
			String str = null;
			br.readLine();
			br.readLine();
			br.readLine();
			br.readLine();
			br.readLine();
			while ((str = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(str);
				if (str.endsWith("vertex")) {
					int x = Integer.parseInt(st.nextToken());
					int y = Integer.parseInt(st.nextToken());
					editor.data.add(x, y);
				} else if (str.endsWith("edge")) {
					int x1 = Integer.parseInt(st.nextToken());
					int y1 = Integer.parseInt(st.nextToken());
					int x2 = Integer.parseInt(st.nextToken());
					int y2 = Integer.parseInt(st.nextToken());
					editor.data.add(new Vertex(x1, y1), new Vertex(x2, y2));
				} else if (str.endsWith("circle")) {
					int x = Integer.parseInt(st.nextToken());
					int y = Integer.parseInt(st.nextToken());
					int radius = Integer.parseInt(st.nextToken());
					editor.data.add(new Vertex(x, y), radius);
				} else if (str.endsWith("rectangle")) {
					int x = Integer.parseInt(st.nextToken());
					int y = Integer.parseInt(st.nextToken());
					int width = Integer.parseInt(st.nextToken());
					int height = Integer.parseInt(st.nextToken());
					editor.data.add(new Vertex(x, y), width, height);
				}
				st.nextToken();
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}