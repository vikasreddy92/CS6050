package assignment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
	
public class PE102 {
	static Point origin;
	static int cross(Point p1, Point p2, Point p3) {
		return (p2.x - p1.x) * (p3.y - p1.y) - (p3.x - p1.x) * (p2.y - p1.y);
	}
	static class Point implements Comparable<Point> {
		int x, y;
		Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
		public int compareTo(Point q) {
			return cross(origin, this, q);
		}
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(new File("C:/Users/vikas/workspace/CS6050/src/assignment/p102_triangles.txt")));
		String vertices = null;
		String[] points = null;
		Point origin = new Point(0, 0);
		int count = 0;
		while((vertices =  br.readLine()) != null) {
			points = vertices.split(",");
			Point mA = new Point(Integer.parseInt(points[0]), Integer.parseInt(points[1]));
			Point mB = new Point(Integer.parseInt(points[2]), Integer.parseInt(points[3]));
			Point mC = new Point(Integer.parseInt(points[4]), Integer.parseInt(points[5]));
			int oab = cross(origin, mA, mB);
			int obc = cross(origin, mB, mC);
			int oca = cross(origin, mC, mA);
			if (oab >= 0 && obc >= 0 && oca >= 0)
				count++;
			else if (oab <= 0 && obc <= 0 && oca <= 0)
				count++;
		}
		System.out.println("Number of triangles which have origin inside: " + count);
		br.close();
	}
}