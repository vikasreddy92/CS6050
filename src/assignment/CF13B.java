package assignment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class CF13B
{
	static Segment[] segments = new Segment[3];
	static int x = 0, y = 0;
	static int first, second, third;
	static final double THRESH = 16;

	public static void main(String[] args)
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n;
		try
		{
			n = Integer.parseInt(br.readLine());
			if (n <= 0)
				return;
			for (int i = 0; i < n; i++)
			{
				for (int j = 0; j < 3; j++)
				{
					StringTokenizer st = new StringTokenizer(br.readLine());
					int x1 = Integer.parseInt(st.nextToken());
					int y1 = Integer.parseInt(st.nextToken());
					int x2 = Integer.parseInt(st.nextToken());
					int y2 = Integer.parseInt(st.nextToken());
					segments[j] = new Segment(x1, y1, x2, y2);
				}
				if (check())
					System.out.println("YES");
				else
					System.out.println("NO");
			}
		}
		catch (NumberFormatException | IOException e)
		{
			e.printStackTrace();
		}
	}

	private static boolean check()
	{
		if (haveCommonPoint())
		{
			updateSegments();
			if (!angleBetween2Lines(segments[first], segments[second]))
				return false;
			boolean b1 = areCollinear(segments[third].x1, segments[third].y1, segments[first]) && areCollinear(segments[third].x2, segments[third].y2, segments[second]);
			boolean b2 = areCollinear(segments[third].x1, segments[third].y1, segments[second]) && areCollinear(segments[third].x2, segments[third].y2, segments[first]);
			if (b1 || b2)
			{
				if (b1)
				{
					if (segments[third].x1 >= Math.min(segments[first].x1, segments[first].x2) && segments[third].x1 <= Math.max(segments[first].x1, segments[first].x2))
						if (segments[third].x2 >= Math.min(segments[second].x1, segments[second].x2) && segments[third].x2 <= Math.max(segments[second].x1, segments[second].x2))
							if (getDividingRatio(segments[third].x1, segments[third].y1, segments[first])
									&& getDividingRatio(segments[third].x2, segments[third].y2, segments[second]))
								return true;
							else
								return false;
				}
				else if (b2)
				{
					if (segments[third].x1 >= Math.min(segments[second].x1, segments[second].x2) && segments[third].x1 <= Math.max(segments[second].x1, segments[second].x2))
						if (segments[third].x2 >= Math.min(segments[first].x1, segments[first].x2) && segments[third].x2 <= Math.max(segments[first].x1, segments[first].x2))
							if (getDividingRatio(segments[third].x1, segments[third].y1, segments[second])
									&& getDividingRatio(segments[third].x2, segments[third].y2, segments[first]))
								return true;
							else
								return false;
				}
			}
			else
				return false;
		}
		return false;
	}

	public static void updateSegments()
	{
		if (segments[first].x2 == x && segments[first].y2 == y)
		{
			segments[first].x2 = segments[first].x1;
			segments[first].y2 = segments[first].y1;
			segments[first].x1 = x;
			segments[first].y1 = y;
		}
		if (segments[second].x2 == x && segments[second].y2 == y)
		{
			segments[second].x2 = segments[second].x1;
			segments[second].y2 = segments[second].y1;
			segments[second].x1 = x;
			segments[second].y1 = y;
		}
	}

	private static boolean haveCommonPoint()
	{
		for (int i = 0; i < 2; i++)
		{
			for (int j = i + 1; j < 3; j++)
			{
				first = i;
				second = j;
				third = 3 - (i + j);
				if ((segments[i].x1 == segments[j].x1 && segments[i].y1 == segments[j].y1) || (segments[i].x1 == segments[j].x2 && segments[i].y1 == segments[j].y2))
				{
					x = segments[i].x1;
					y = segments[i].y1;
					return true;
				}
				else if ((segments[i].x2 == segments[j].x1 && segments[i].y2 == segments[j].y1) || (segments[i].x2 == segments[j].x2 && segments[i].y2 == segments[j].y2))
				{
					x = segments[i].x2;
					y = segments[i].y2;
					return true;
				}
			}
		}
		return false;
	}

	private static boolean getDividingRatio(int x1, int y1, Segment s1)
	{

		long num1 = dist2(s1.x1, s1.y1, x1, y1);
		long num2 = dist2(x1, y1, s1.x2, s1.y2);
		boolean ratio = (num1 < num2) ? (num1 * THRESH >= num2) : (num2 * 16 >= num1);
		return ratio;
	}

	static boolean areCollinear(int x1, int y1, Segment s)
	{
		int a_x = s.x1 - s.x2;
		int a_y = s.y1 - s.y2;
		int b_x = s.x1 - x1;
		int b_y = s.y1 - y1;
		return ((a_x * b_y) - (a_y * b_x)) == 0;
	}

	private static long dist2(long x1, long y1, long x2, long y2)
	{
		return ((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
	}

	public static boolean angleBetween2Lines(Segment line1, Segment line2)
	{
		long a_x = line1.x1 - line1.x2;
		long a_y = line1.y1 - line1.y2;
		long b_x = line2.x1 - line2.x2;
		long b_y = line2.y1 - line2.y2;
		long dp = ((a_x * b_x) + (a_y * b_y));
		long cp = ((a_x * b_y) - (a_y * b_x));
		return ((dp >= 0) && (cp != 0));
	}
}

class Segment
{
	int x1, y1, x2, y2;

	public Segment(int x1, int y1, int x2, int y2)
	{
		super();
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}

	@Override
	public String toString()
	{
		return "Segment (" + x1 + ",  " + y1 + "), (" + x2 + ", " + y2 + ")";
	}
}