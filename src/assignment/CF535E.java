package assignment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class CF535E
{
	public static void main(String[] args) throws NumberFormatException, IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		Point[] pp = new Point[n];
		TreeMap<Point, ArrayList<Integer>> map = new TreeMap<Point, ArrayList<Integer>>();
		for (int i = 0; i < n; i++)
		{
			StringTokenizer st = new StringTokenizer(br.readLine());
			int s = Integer.parseInt(st.nextToken());
			int r = Integer.parseInt(st.nextToken());
			pp[i] = new Point(1.0d / s, 1.0d / r, (i + 1));
			if (!map.containsKey(pp[i]))
			{
				ArrayList<Integer> list = new ArrayList<Integer>();
				list.add(i + 1);
				map.put(pp[i], list);
			}
			else
				map.get(pp[i]).add(i + 1);
		}
		Point[] hull = convexHull(pp);
		// L be the leftmost point on this convex hull (if there are more than one, choose the one with minimum y component).
		Point L = hull[0];
		// D be the point with minimum y component on this convex hull (if there are more than one, consider the leftmost).
		Point D = hull[0];
		ArrayList<Integer> winners = new ArrayList<Integer>();
		double ymin = Double.MAX_VALUE;
		double xmin = Double.MAX_VALUE;
		for (int i = 0; i < hull.length; i++)
		{
			Point p = hull[i];
			if (p.x < xmin)
			{
				xmin = p.x;
				L = p;
			}
			if (p.y < ymin)
			{
				ymin = p.y;
				D = p;
			}
			if (p.x == xmin && p.y < L.y)
				L = p;
			if (p.y == ymin && p.x < D.x)
				D = p;
		}
		// Adding points from L to D to the answer
		boolean flag = false;
		for (Point p : hull)
		{
			if (p.idx == L.idx)
				flag = true;
			if (flag)
				winners.addAll(map.get(p));
			if (p.idx == D.idx)
				flag = false;
		}
		Collections.sort(winners);
		for (int i : winners)
			System.out.print(i + " ");
	}

	public static double cross(Point p1, Point p2, Point p3)
	{
		return (p2.x - p1.x) * (p3.y - p1.y) - (p2.y - p1.y) * (p3.x - p1.x);
	}

	public static Point[] convexHull(Point[] pp)
	{
		if (pp.length <= 1)
			return pp;
		else if (pp.length > 1)
		{
			int n = pp.length, k = 0;
			Point[] hull = new Point[2 * n];
			Arrays.sort(pp);
			for (int i = 0; i < n; ++i)
			{
				while (k >= 2 && cross(hull[k - 2], hull[k - 1], pp[i]) <= 0)
					k--;
				hull[k++] = pp[i];
			}
			for (int i = n - 2, t = k + 1; i >= 0; i--)
			{
				while (k >= t && cross(hull[k - 2], hull[k - 1], pp[i]) <= 0)
					k--;
				hull[k++] = pp[i];
			}
			if (k > 1)
				hull = Arrays.copyOfRange(hull, 0, k - 1);
			return hull;
		}
		else
			return null;
	}

	static class Point implements Comparable<Point>
	{
		double x;
		double y;
		int idx;

		Point(double x, double y, int id)
		{
			this.x = x;
			this.y = y;
			this.idx = id;
		}

		@Override
		public String toString()
		{
			return "(" + x + "," + y + ")";
		}

		@Override
		public int compareTo(Point arg0)
		{
			int res = Double.compare(this.x, arg0.x);
			if (res == 0)
				return Double.compare(this.y, arg0.y);
			return res;

		}

		public double distancePoint(Point p1)
		{
			return Math.sqrt((this.x - p1.x) * (this.x - p1.x) + (this.y - p1.y) * (this.y - p1.y));
		}

		public double distanceToLine(Point p1, Point p2)
		{
			return Math.abs(CF535E.cross(p1, this, p2) / (p2.distancePoint(p1)));
		}
	}
}