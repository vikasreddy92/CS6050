package assignment;

import java.awt.Font;
import java.awt.GraphicsEnvironment;

public class test
{
	public static void main(String[] args)
	{
		// float[] point = getLineIntersection(5, 5, 5, -5, 0, 0, 3, 0);
		// if(point != null)
		// System.out.println("Intersection point: " + point[0] + ", " + point[1]);
		// else System.out.println("No Intersection!");
		// System.out.println(dist2(5, 5, 5, -5));
		// double num1 = 2;
		// double num2 = 99;
		// double ratio = (num1 < num2) ? num1 / num2 : num2 / num1;
		// System.out.println(ratio);
		// System.out.println(angleBetween2Lines(0, 5, 10, 5, 0, 5, 10, 10));
		double start = System.nanoTime();
		printSystemFonts();
		System.out.println((System.nanoTime() - start) / 1000000000);
	}

	public static double[] getLineIntersection(double x1, double y1, double x2, double y2, double x3,
			double y3, double x4, double y4)
	{
		double[] point = new double[2];
		double s_x, s_y, t_x, t_y, s, t;
		s_x = x2 - x1;
		s_y = y2 - y1;
		t_x = x4 - x3;
		t_y = y4 - y3;

		s = (-s_y * (x1 - x3) + s_x * (y1 - y3)) / (-t_x * s_y + s_x * t_y);
		t = (t_x * (y1 - y3) - t_y * (x1 - x3)) / (-t_x * s_y + s_x * t_y);

		if (s >= 0 && s <= 1 && t >= 0 && t <= 1)
		{
			point[0] = x1 + (t * s_x);
			point[1] = y1 + (t * s_y);
			return point;
		}
		// System.out.println("Lines did not intersect!");
		return null;
	}

	public static float dist2(float x1, float y1, float x2, float y2)
	{
		return (float) Math.sqrt(((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1)));
	}

	public static double angleBetween2Lines(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4)
	{
		double angle1 = Math.atan2(y2 - y1, x2 - x1);
		double angle2 = Math.atan2(y4 - y3, x4 - x3);
		double angle = Math.toDegrees(Math.abs(angle1 - angle2));
		// if(angle > 180)
		// angle -= 180;
		return angle;
	}

	public static void benchmark()
	{
		System.out.println(Integer.MAX_VALUE);
		for (int k = 0; k < 1; k++)
			for (int i = 0; i < Integer.MAX_VALUE; i++)
			{
				for (int j = 0; j < Integer.MAX_VALUE; j++)
				{
					Math.sqrt((i + j));
				}
			}
	}
	
	public static void printSystemFonts()
	{
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		String[] fontNames = ge.getAvailableFontFamilyNames();
		Font[] fonts = ge.getAllFonts();
//		for(String fn : fontNames)
//			System.out.println(fn);
		Font f = new Font("Times New Roman", Font.BOLD, 30);
		System.out.println(f.getName());
		System.out.println(f.getStyle());
		
	}
}
