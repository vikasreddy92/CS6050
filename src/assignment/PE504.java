package assignment;

public class PE504
{
	public static void main(String[] args)
	{
		double start = System.currentTimeMillis();
		int m = 20;
		int count = 0;
		for (int i = 1; i <= m; i++)
		{
			for (int j = 1; j <= m; j++)
			{
				for (int k = 1; k <= m; k++)
				{
					for (int l = 1; l <= m; l++)
					{

						int area = i * j + j * k + k * l + l * i;

						int boundPoints = gcd(i, j) + gcd(j, k) + gcd(k, l) + gcd(l, i);

						int pointsInside = (area + 2 - boundPoints) / 2;

						int temp = (int) Math.sqrt(pointsInside);
						if (((temp * temp) == pointsInside))
							count++;
					}
				}
			}
		}
		System.out.println(count);
		System.out.println((System.currentTimeMillis() - start)/1000);
	}

	static int gcd(int a, int b)
	{
		if (b == 0)
			return a;
		return gcd(b, a % b);
	}
}
