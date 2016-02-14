package assignment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import java.util.HashMap;
import java.util.Map;

public class CF610D {
	static Map<Integer, Point> mPoints = new HashMap<Integer, Point>();
	static int mapCount = 0;

	static class Segment {
		int x1, y1, x2, y2;

		Segment(String s) {
			StringTokenizer st = new StringTokenizer(s);
			this.x1 = Integer.parseInt(st.nextToken());
			this.y1 = Integer.parseInt(st.nextToken());
			this.x2 = Integer.parseInt(st.nextToken());
			this.y2 = Integer.parseInt(st.nextToken());
		}
	}

	static class Point {
		int x, y;

		Point(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public boolean equals(Object o) {
			Point p = (Point) o;
			return (this.x == p.x && this.y == p.y);
		}
	}

	public static void main(String[] args) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter writer = new PrintWriter(System.out);

		try {
			int n = Integer.parseInt(reader.readLine());
			for (int i = 0; i < n; i++) {
				Segment seg = new Segment(reader.readLine());
				getSquares(seg);
			}
			writer.println(mapCount);
			reader.close();
			writer.close();
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
	}

	private static void getSquares(Segment seg) {
		int start, end;
		if (seg.x1 == seg.x2) {
			if (seg.y1 < seg.y2) {
				start = seg.y1;
				end = seg.y2;
			} else {
				start = seg.y2;
				end = seg.y1;
			}
			for (int i = start; i <= end; i++) {
				Point p = new Point(seg.x1, i);
				if (!mPoints.containsValue(p))
				{
					mPoints.put(mapCount, p);
					mapCount++;
				}
			}
		}
		if (seg.y1 == seg.y2) {
			if (seg.x1 < seg.x2) {
				start = seg.x1;
				end = seg.x2;
			} else {
				start = seg.x2;
				end = seg.x1;
			}
			for (int i = start; i <= end; i++) {
				Point p = new Point(i, seg.y1);
				if (!mPoints.containsValue(p))
				{
					mPoints.put(mapCount, p);
					mapCount++;
				}
			}
		}
	}

}
