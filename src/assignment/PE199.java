package assignment;

public class PE199
{
	public static void main(String[] args)
	{
		int depth = 10;
		int r = 1; // radius of inside circles
		double k = 1 / (double) r; // curvature of inside circles
		double K = ((k + k + k) - (2 * Math.sqrt(3 * k * k))); // curvature of big circle

		double total_area = 1 / (K * K); // area of the big circle
		double inside_area = 3 * (1 / k * k) + 3 * getArea(K, k, k, depth) + getArea(k, k, k, depth); // total occupied area
		double result = (total_area - inside_area) / total_area;
		System.out.println(new java.text.DecimalFormat("#.########").format(result));
	}

	public static double getArea(double k1, double k2, double k3, int depth)
	{
		if (depth == 0)
			return 0d;
		double k4 = k1 + k2 + k3 + 2 * Math.sqrt(k1 * k2 + k2 * k3 + k3 * k1);
		double area = 1 / (k4 * k4);
		area += getArea(k1, k2, k4, depth - 1);
		area += getArea(k2, k3, k4, depth - 1);
		area += getArea(k3, k1, k4, depth - 1);
		return area;
	}
}
