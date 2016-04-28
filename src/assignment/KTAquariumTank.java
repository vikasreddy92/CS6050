package assignment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class KTAquariumTank
{

	public static void main(String[] args) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		short t = Short.parseShort(br.readLine());
		String[] dl = br.readLine().split(" ");
		int depth = Integer.parseInt(dl[0]), water = Integer.parseInt(dl[1]);
		Point[] pp = new Point[t];
		for (int i = 0; i < t; i++)
		{
			String[] xy = br.readLine().split(" ");
			pp[i] = new Point(Integer.parseInt(xy[0]), Integer.parseInt(xy[1]));
		}
		double volume = (getArea(pp) * depth);
		volume /= 1000d;
		System.out.println(volume - water);
		// System.out.println(Arrays.toString(pp));
		br.close();
	}

	static long getArea(Point[] pp)
	{
		long xSum = 0, ySum = 0, area = 0;
		for (int i = 1; i < pp.length; i++)
		{
			xSum += pp[i - 1].x * pp[i].y;
			ySum += pp[i - 1].y * pp[i].x;
		}
		xSum += pp[pp.length - 1].x * pp[0].y;
		ySum += pp[pp.length - 1].y * pp[0].x;
		area = xSum - ySum;
		System.out.println("Area is : " + area);
		return Math.abs(area);
	}

	static class Point
	{
		int x, y;

		public Point(int x, int y)
		{
			this.x = x;
			this.y = y;
		}

		@Override
		public String toString()
		{
			return "(" + this.x + ", " + this.y + ")";
		}
	}
}
