package class_porgrams;

import java.io.*;
import java.util.*;

public class Convex
{
	static Point origin;

	static int cross(Point p1, Point p2, Point p3)
	{
		return (int) ((p2.x - p1.x) * (p3.y - p1.y) - (p3.x - p1.x) * (p2.y - p1.y));
	}

	static class Point implements Comparable<Point>
	{
		float x, y;

		int idx;

		Point(float x, float y, int idx)
		{
			this.x = x;
			this.y = y;
			this.idx = idx;
		}

		@Override
		public int compareTo(Point q)
		{
			return cross(origin, this, q);
		}

		@Override
		public String toString()
		{
			return "Point [x=" + x + ", y=" + y + ", idx=" + idx + "]";
		}
	}

	public static void main(String[] args) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter pw = new PrintWriter(System.out);
		int n = Integer.parseInt(br.readLine());
		Point[] pp = new Point[n];
		for (int i = 0; i < n; i++)
		{
			StringTokenizer st = new StringTokenizer(br.readLine());
			float x = Integer.parseInt(st.nextToken());
			float y = Integer.parseInt(st.nextToken());
			pp[i] = new Point(1 / x, 1 / y, i);
		}
		if (n == 1)
		{
			System.out.println(1);
			return;
		}
		int j = 0;
		for (int i = 1; i < n; i++)
		{
			Point p = pp[j];
			Point q = pp[i];
			if (q.y < p.y)
				j = i;
		}
		Point t = pp[0];
		pp[0] = pp[j];
		pp[j] = t;
		origin = pp[0];
		Arrays.sort(pp, 1, n);

		Point[] qq = new Point[n];
		qq[0] = pp[0];
		qq[1] = pp[1];
		int size = 2;
		for (int i = 2; i < n; i++)
		{
			while (size >= 2 && cross(qq[size - 2], qq[size - 1], pp[i]) >= 0)
				size--;
			qq[size++] = pp[i];
		}
		System.out.println(Arrays.toString(qq));
		System.out.println(qq.length + "\tsize: " + size);

		int l = 0;
		n = size;
		for (int i = 0; i < n; i++)
		{
			Point p = qq[l];
			Point q = qq[i];
			if (q.y <= p.y)
			{
				if (q.y == p.y)
					if (q.x < p.x)
						l = i;
				if (q.y < p.y)
					l = i;
			}
		}
		// Point D = pp[j];
		int k = 0;
		for (int i = 0; i < n; i++)
		{
			Point p = qq[k];
			Point q = qq[i];
			if (q.x <= p.x)
			{
				if (q.x < q.x)
					k = i;
				if (q.x == q.x)
					if (q.y < p.y)
						k = i;
			}
		}
		for (int i = k; i <= l + 1; i++)
		{
			System.out.print(qq[i].idx + 1 + " ");
		}

		// pw.println("newpath");
		// pw.println(qq[0].x + " " + qq[0].y + " m");
		// for (int i = 1; i < size; i++)
		// pw.println(qq[i].x + " " + qq[i].y + " l");
		// pw.println("closepath");
		// pw.println("stroke");
		// for (int i = 0; i < n; i++)
		// pw.println(pp[i].x + " " + pp[i].y + " point");
		pw.close();
	}
}
