package class_porgrams;

import java.io.*;
import java.util.*;

public class CF610D
{
	static class Segment
	{
		int a, b, c;

		Segment(int a, int b, int c)
		{
			if (a > b)
			{
				int t = a;
				a = b;
				b = t;
			}
			this.a = a;
			this.b = b;
			this.c = c;
		}
	}

	static int merge(Segment[] ss, int n)
	{
		if (n == 0)
			return 0;
		Arrays.sort(ss, 0, n, (s, t) -> s.c != t.c ? s.c - t.c : s.a - t.a);
		int m = 0;
		for (int i = 1; i < n; i++)
			if (ss[m].c == ss[i].c && ss[m].b >= ss[i].a)
				ss[m].b = Math.max(ss[m].b, ss[i].b);
			else
				ss[++m] = ss[i];
		return m + 1;
	}

	static long count(Segment[] ss, int n)
	{
		long cnt = 0;
		for (int i = 0; i < n; i++)
			cnt += ss[i].b - ss[i].a + 1;
		return cnt;
	}

	static class Endpoint
	{
		int x, y, t;

		Endpoint(int x, int y, int t)
		{
			this.x = x;
			this.y = y;
			this.t = t;
		}
	}

	// Fenwick tree
	static void update(int[] aa, int i, int a)
	{
		while (i < aa.length)
		{
			aa[i] += a;
			i |= i + 1;
		}
	}

	static int query(int[] aa, int i)
	{
		int sum = 0;
		while (i >= 0)
		{
			sum += aa[i];
			i &= i + 1;
			i--;
		}
		return sum;
	}

	public static void main(String[] args) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		Segment[] hh = new Segment[n];
		Segment[] vv = new Segment[n];
		int h = 0, v = 0;
		for (int i = 0; i < n; i++)
		{
			StringTokenizer st = new StringTokenizer(br.readLine());
			int x1 = Integer.parseInt(st.nextToken());
			int y1 = Integer.parseInt(st.nextToken());
			int x2 = Integer.parseInt(st.nextToken());
			int y2 = Integer.parseInt(st.nextToken());
			if (y1 == y2)
				hh[h++] = new Segment(x1, x2, y1);
			else
				vv[v++] = new Segment(y1, y2, x1);
		}
		h = merge(hh, h);
		v = merge(vv, v);
		long cnt = count(hh, h) + count(vv, v);
		long tc = cnt;
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (int i = 0; i < h; i++)
		{
			map.put(hh[i].a, 0);
			map.put(hh[i].b, 0);
		}
		for (int i = 0; i < v; i++)
			map.put(vv[i].c, 0);
		int[] mm = new int[map.size()];
		int m = 0;
		for (int key : map.keySet())
			mm[m++] = key;
		Arrays.sort(mm);
		for (int i = 0; i < mm.length; i++)
		{
			map.put(mm[i], i);
			mm[i] = 0;
		}

		Endpoint[] ee = new Endpoint[v * 2];
		for (int i = 0; i < v; i++)
		{
			ee[i * 2] = new Endpoint(vv[i].c, vv[i].a, 0);
			ee[i * 2 + 1] = new Endpoint(vv[i].c, vv[i].b, 1);
		}
		Arrays.sort(ee, (e, f) -> e.y != f.y ? e.y - f.y : e.t - f.t);

		int i = 0, j = 0;
		while (i < h)
			if (j < v * 2 && (ee[j].y < hh[i].c || ee[j].y == hh[i].c && ee[j].t == 0))
			{
				if (ee[j].t == 0)
					update(mm, map.get(ee[j].x), 1);
				else
					update(mm, map.get(ee[j].x), -1);
				j++;
			}
			else
			{
				cnt -= query(mm, map.get(hh[i].b)) - query(mm, map.get(hh[i].a) - 1);
				i++;
			}
		System.out.println("Total Count: " + tc);
		System.out.println("Intersections: " + (tc - cnt));
		System.out.println(cnt);
	}
}
