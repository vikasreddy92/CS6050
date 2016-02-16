package project;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Export {
	static int xmin = Integer.MAX_VALUE;
	static int ymin = Integer.MAX_VALUE;
	static int xmax = Integer.MIN_VALUE;
	static int ymax = Integer.MIN_VALUE;
	
	static void check(int x, int y) {
		if (xmin > x)
			xmin = x;
		if (xmax < x)
			xmax = x;
		if (ymin > y)
			ymin = y;
		if (ymax < y)
			ymax = y;
	}
	
	public static void export(String text, String filePath) {
		
		StringBuilder sb = new StringBuilder();
		sb.append("%!PS-Adobe-3.0 EPSF-3.0");
		sb.append("\n");
		sb.append("\n");
		sb.append("/vertex { newpath 4 0 360 arc 0 setgray fill } def");
		sb.append("\n");
		sb.append("/edge { newpath moveto lineto stroke } def");
		sb.append("\n");
		String[] lines = text.split("\n");
		for(String line : lines) {
			StringTokenizer st = new StringTokenizer(line);
			line = st.nextToken();
			if (line.equals("edge")) {
				int x1 = Integer.parseInt(st.nextToken());
				int y1 = Integer.parseInt(st.nextToken());
				int x2 = Integer.parseInt(st.nextToken());
				int y2 = Integer.parseInt(st.nextToken());
				check(x1, y1);
				check(x2, y2);
				sb.append(x1 + " " + y1 + " " + x2 + " " + y2 + " " + line + "\n");
			} else if (line.equals("vertex")) {
				int x = Integer.parseInt(st.nextToken());
				int y = Integer.parseInt(st.nextToken());
				check(x, y);
				sb.append(x + " " + y + " " + line + "\n");
			}
		}
		
		try {
			FileWriter fw = new FileWriter(new File(filePath));
			fw.write(sb.toString());
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
