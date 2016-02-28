package class_porgrams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

class KdTree2
{
	KdTree2 left, right;
	Point p;

	public KdTree2(Point[] pp, int depth)
	{
		if (depth % 2 == 0)
			Arrays.sort(pp, (p, q) -> p.xCompareTo(q));
		else
			Arrays.sort(pp, (p, q) -> p.yCompareTo(q));
		int n = pp.length;
		if (n == 1)
		{
			p = pp[0];
			return;
		}
		int m = pp.length / 2;
		p = pp[m - 1];
		left = new KdTree2(Arrays.copyOfRange(pp, 0, m), depth + 1);
		right = new KdTree2(Arrays.copyOfRange(pp, m, n), depth + 1);
	}

	ArrayList<Point> query(Window window)
	{
		Point xmin = new Point(Integer.MIN_VALUE, Integer.MIN_VALUE);
		Point xmax = new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);
		Point ymin = new Point(Integer.MIN_VALUE, Integer.MIN_VALUE);
		Point ymax = new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);
		Window plane = new Window(xmin, xmax, ymin, ymax);
		ArrayList<Point> list = new ArrayList<>();
		query(list, plane, window, 0);
		return list;
	}

	void query(ArrayList<Point> list, Window plane, Window window, int depth)
	{
		if (left == null && right == null)
		{
			if (window.contains(p))
			{
				list.add(p);
			}
			return;
		}
		if (!plane.intersects(window))
			return;
		if (depth % 2 == 0)
		{
			Window wl = new Window(plane.xmin, p, plane.ymin, plane.ymax);
			Window wr = new Window(p, plane.xmax, plane.ymin, plane.ymax);			
		}
		else
		{
			Window wl = new Window(plane.xmin, plane.xmax, plane.ymin, p);
			Window wr = new Window(plane.xmin, plane.xmax, p, plane.ymax);	
		}
//		query(wl, )
	}
}

class RangeTree2
{

}

class DummyTree
{

	Point[] pp;

	public DummyTree(Point[] pp)
	{
		this.pp = pp;
	}

	public ArrayList<Point> search(Point xmin, Point xmax, Point ymin, Point ymax)
	{
		ArrayList<Point> list = new ArrayList<>();
		for (Point p : pp)
		{
			if (p.xCompareTo(xmin) > 0 && p.xCompareTo(xmax) < 0 && p.yCompareTo(ymin) > 0 && p.xCompareTo(ymax) < 0)
			{
				list.add(p);
			}

		}
		return list;
	}
}

class Window
{
	Point xmin, xmax, ymin, ymax;

	public Window(Point xmin, Point xmax, Point ymin, Point ymax)
	{
		super();
		this.xmin = xmin;
		this.xmax = xmax;
		this.ymin = ymin;
		this.ymax = ymax;
	}

	boolean contains(Point p)
	{
		return (p.xCompareTo(xmin) > 0 && p.xCompareTo(xmax) < 0 && p.yCompareTo(ymin) > 0 && p.xCompareTo(ymax) < 0);
	}

	boolean overlap(Point p1, Point p2, Point q1, Point q2)
	{

		return true;
	}

	boolean intersects(Window window)
	{
		// return overlap(xmin, xmax, window.xmin, window.xmax) && overlap(xmin,
		// xmax, window.xmin, window.xmax);
		if (xmax.xCompareTo(window.xmin) <= 0 || xmin.xCompareTo(window.xmax) >= 0 || ymax.yCompareTo(window.ymin) <= 0
				|| ymin.yCompareTo(window.ymax) >= 0)
			return false;
		else
			return true;

	}
}

class Point
{
	int x, y;

	public Point(int x, int y)
	{
		super();
		this.x = x;
		this.y = y;
	}

	int xCompareTo(Point p)
	{
		return x != p.x ? x - p.x : y - p.y;
	}

	int yCompareTo(Point p)
	{
		return y != p.y ? y - p.y : x - p.x;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point other = (Point) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "Point [x=" + x + ", y=" + y + "]";
	};

}

public class Range
{
	static void print(String title, ArrayList<Point> pp)
	{
		System.out.println(title + " returns " + pp.size() + " points");
		for (Point p : pp)
		{
			System.out.println(p);
		}
	}

	public static void main(String[] args)
	{
		int n = Integer.parseInt(args[0]);
		int X = Integer.parseInt(args[1]);
		int Y = Integer.parseInt(args[2]);
		int m = Integer.parseInt(args[3]);
		System.out.println("Number of points: n = " + n + " x = " + X + " y = " + Y + " m = " + m);
		Point[] pp = new Point[n];
		HashSet<Point> set = new HashSet<>();
		Random rand = new Random();
		for (int i = 0; i < n; i++)
		{
			Point p = null;
			do
			{
				int x = rand.nextInt(X);
				int y = rand.nextInt(Y);
				p = new Point(x, y);
			} while (set.contains(p));
			set.add(p);
			pp[i] = p;
			System.out.println(i + ": " + pp[i]);
		}

		DummyTree dummy = new DummyTree(pp);

		for (int k = 0; k < m; k++)
		{
			int x1 = rand.nextInt(X);
			int x2 = rand.nextInt(X);
			int y1 = rand.nextInt(Y);
			int y2 = rand.nextInt(Y);
			if (x1 > x2)
			{
				int temp = x1;
				x1 = x2;
				x2 = temp;
			}
			if (y1 > y2)
			{
				int temp = y1;
				y1 = y2;
				y2 = temp;
			}

			Point pxmin = new Point(x1, Integer.MIN_VALUE);
			Point pxmax = new Point(x2, Integer.MAX_VALUE);

			Point pymin = new Point(Integer.MIN_VALUE, y1);
			Point pymax = new Point(Integer.MAX_VALUE, y2);

			System.out.println("Range: (" + x1 + "," + y1 + "), " + "(" + x2 + "," + y2 + ")");
			ArrayList<Point> dummyList = dummy.search(pxmin, pxmax, pymin, pymax);

			print("dummy list", dummyList);
		}
	}

}
