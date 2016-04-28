package assignment;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class CC_MARBLEGF
{
	static long[] data;

	public static void main(String[] args) throws IOException
	{
		Scanner sc = new Scanner(System.in);
		PrintWriter pw = new PrintWriter(System.out);
		int N = sc.nextInt();
		int A = sc.nextInt();
		data = new long[N];
		for (int i = 0; i < N; i++)
			add(i, sc.nextInt());
		for (int i = 0; i < A; i++)
		{
			String TASK = sc.next();
			int l = sc.nextInt();
			int m = sc.nextInt();
			if (TASK.equals("S"))
				pw.println(sum(l, m));
			else if (TASK.equals("G"))
				add(l, m);
			else if (TASK.equals("T"))
				add(l, -m);
		}
		sc.close();
		pw.close();
	}

	static void add(int at, int by)
	{
		while (at < data.length)
		{
			data[at] += by;
			at |= (at + 1);
		}
	}

	static long sum(int at)
	{
		long res = 0;
		while (at >= 0)
		{
			res += data[at];
			at = (at & (at + 1)) - 1;
		}
		return res;
	}

	static long sum(int l, int r)
	{
		if (l == 0)
			return sum(r);
		return sum(r) - sum(l - 1);
	}
}
