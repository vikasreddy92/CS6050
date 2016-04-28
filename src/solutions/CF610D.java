package solutions;

import java.io.*;
import java.util.*;

public class CF610D
{
	public static class Event
	{
		int x, y, type;

		public Event(int x, int y, int type)
		{
			super();
			this.x = x;
			this.y = y;
			this.type = type;
		}
		
		public static Comparator<Event> eventComparator = new Comparator<CF610D.Event>()
		{
			@Override
			public int compare(Event o1, Event o2)
			{
				if (o1.y != o2.y)
				{
					return Integer.compare(o1.y, o2.y);
				}
				if (o1.type != o2.type)
				{
					return Integer.compare(o2.type, o1.type);
				}
				return Integer.compare(o1.x, o2.x);
			}
		};
		
		public static Comparator<Event> xComparator = new Comparator<CF610D.Event>()
		{
			@Override
			public int compare(Event o1, Event o2)
			{
				return Integer.compare(o1.x, o2.x);
			}
		};
		
		public static Comparator<Event> yComparator = new Comparator<CF610D.Event>()
		{
			@Override
			public int compare(Event o1, Event o2)
			{
				return Integer.compare(o1.y, o2.y);
			}
		};
	}

	public static class Fenwick
	{
		int[] data;

		Fenwick(int n)
		{
			data = new int[n+1];
		}
		
		public void add(int at, int by)
		{
			at += 1;
			while (at < data.length)
			{
				data[at] += by;
				at |= (at + 1);
			}
		}

		public int sum(int at)
		{
			int res = 0;
			while (at >= 0)
			{
				res += data[at];
				at = (at & (at + 1)) - 1;
			}
			return res;
		}
		
		public int sum(int l, int r)
		{
			return sum(r) - sum(l);
		}
	}

	public static void main(String[] args) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		TreeMap<Integer, ArrayList<Event>> hors = new TreeMap<Integer, ArrayList<Event>>(),
				vers = new TreeMap<Integer, ArrayList<Event>>();
		TreeSet<Integer> xs = new TreeSet<>();
		for (int i = 0; i < n; i++)
		{
			StringTokenizer st = new StringTokenizer(br.readLine());
			int x1 = Integer.parseInt(st.nextToken()), 
					y1 = Integer.parseInt(st.nextToken()),
					x2 = Integer.parseInt(st.nextToken()), 
					y2 = Integer.parseInt(st.nextToken());
			int xl = Math.min(x1, x2), 
					xr = Math.max(x1, x2), 
					yl = Math.min(y1, y2), 
					yr = Math.max(y1, y2);
			xs.add(xl);
			xs.add(xr + 1);
			if (xl == xr)
			{
				if (!vers.containsKey(xl))
					vers.put(xl, new ArrayList<>());
				vers.get(xl).add(new Event(xl, yl, 0));
				vers.get(xl).add(new Event(xl, yr + 1, 1));
			}
			else
			{
				if (!hors.containsKey(yl))
					hors.put(yl, new ArrayList<>());
				hors.get(yl).add(new Event(xl, yl, 0));
				hors.get(yl).add(new Event(xr + 1, yl, 1));
			}
		}
		TreeSet<Event> events = new TreeSet<>(Event.eventComparator);
		long count = 0;
		for (int x : vers.keySet())
		{
			ArrayList<Event> xEvents = vers.get(x);
			xEvents.sort(Event.yComparator);
			int bal = 0;
			int last = 0;
			for (Event e : xEvents)
			{
				if (e.type == 0)
				{
					bal++;
					if (bal == 1)
					{
						events.add(e);
						last = e.y;
					}
				}
				else
				{
					bal--;
					if (bal == 0)
					{
						events.add(e);
						count += (e.y - last);
					}
				}
			}
		}
		TreeMap<Integer, Integer> xToNum = new TreeMap<>();
		int xIdx = 0;
		for (int x : xs)
			xToNum.put(x, xIdx++);
		Fenwick fenwick = new Fenwick(xIdx);
		for (int y : hors.keySet())
		{
			while (!events.isEmpty() && events.first().y <= y)
			{
				Event e = events.pollFirst();
				int x = xToNum.get(e.x);
				fenwick.add(x, e.type == 0 ? 1 : -1);
			}
			int last = 0;
			int bal = 0;
			ArrayList<Event> yEvents = hors.get(y);
			yEvents.sort(Event.xComparator);
			for (Event e : yEvents)
			{
				if (e.type == 0)
				{
					bal++;
					if (bal == 1)
						last = e.x;
				}
				else
				{
					bal--;
					if (bal == 0)
					{
						count += (e.x - last);
						count -= fenwick.sum(xToNum.get(last), xToNum.get(e.x));
					}
				}
			}
		}
		System.out.println(count);
	}
}
