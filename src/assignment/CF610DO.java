package assignment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class CF610DO {
	static Map<Point, Integer> mPoints = new HashMap<Point, Integer>();
	static int mapCount = 0;
	static int mapValue = 0;
	
	static class Segment {
		int a, b, c;
		public Segment(String s) {
			StringTokenizer st = new StringTokenizer(s);
			int ta = Integer.parseInt(st.nextToken());
			int tb = Integer.parseInt(st.nextToken());
			int tc = Integer.parseInt(st.nextToken());
			int td = Integer.parseInt(st.nextToken());
			if(ta == tc) {
				this.a = ta;
				this.b = tb;
				this.c = td;
				//new Segment(ta, tb, td);
			} else if(tb == td){
				this.a = tb;
				this.b = ta;
				this.c = tc;
				//new Segment(tb, ta, tc);
			}
		}
		public Segment(int a, int b, int c) {
			this.a = a;
			this.b = b;
			this.c = c;
		}
		
		@Override 
		public String toString() {
			return this.a + " " + this.b + " " + this.c;
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
		
		@Override
		public String toString() {
			return this.x+ " " + this.y;
		}
	}
	
	public static void main(String[] args) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter writer = new PrintWriter(System.out);
		try {
			int n = Integer.parseInt(reader.readLine());
			for (int i = 0; i < n; i++) {
				Segment seg = new Segment(reader.readLine());
				computeSquares(seg);
			}
			writer.println(mPoints.size());
			reader.close();
			writer.close();
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void computeSquares(Segment seg) {
		int start, end;
		if(seg.b < seg.c) {
			start = seg.b;
			end = seg.c;
		} else {
			start = seg.c;
			end = seg.b;
		}
		System.out.println("Start: " + start + "\t" + "End: " + end);
		for (int i = start; i <= end; i++) {
			Point p = new Point(seg.a, i);
			if(!mPoints.containsKey(p)) {
				mPoints.put(p, mapValue);
				mapValue++; 
			}
		}
	}
}
