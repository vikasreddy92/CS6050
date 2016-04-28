package assignment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class KTTrackSmoothing
{
	public static void main(String[] args) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		short t = Short.parseShort(br.readLine());
		for (short i = 0; i < t; i++)
		{
			String[] temp = br.readLine().trim().split(" ");
			int minRadius = Integer.parseInt(temp[0]);
			int numPoints = Integer.parseInt(temp[1]);
			temp = br.readLine().trim().split(" ");
			int sX = Integer.parseInt(temp[0]);
			int sY = Integer.parseInt(temp[1]);
			int cX = sX, cY = sY;
			double perimeter = 0;
			for (int j = 1; j < numPoints; j++)
			{
				temp = br.readLine().trim().split(" ");
				int tX = Integer.parseInt(temp[0]);
				int tY = Integer.parseInt(temp[1]);
				perimeter += dist2(cX, cY, tX, tY);
				cX = tX;
				cY = tY;
			}
			perimeter += dist2(sX, sY, cX, cY);
			double circum = 2 * Math.PI * minRadius;
			if (circum > perimeter)
				System.out.println("Not possible");
			else
				System.out.println((perimeter - circum) / perimeter);
		}
		br.close();
	}

	private static double dist2(int cX, int cY, int tX, int tY)
	{
		return Math.sqrt(((tX - cX) * (tX - cX)) + ((tY - cY) * (tY - cY)));
	}
}