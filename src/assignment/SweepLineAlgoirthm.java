package assignment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class SweepLineAlgoirthm
{
    public static class Point
    {
        public int x;
        public int y;
 
        public Point(int x, int y)
        {
            this.x = x;
            this.y = y;
        }
    }
 
    private static enum EventType
    {
        EVENT_END,
        EVENT_VERTICAL,
        EVENT_START
    }
 
    public static class Line
    {
        public int x1, y1, x2, y2;
 
        public Line(int x1, int y1, int x2, int y2)
        {
            this.x1 = x1; this.y1 = y1;
            this.x2 = x2; this.y2 = y2;
        }
    }
 
    private static class Event implements Comparable<Event>
    {
        public EventType type;
        public int x;
        public Line line;
 
        public Event(EventType type, int x, Line line)
        {
            this.type = type;
            this.x = x;
            this.line = line;
        }
 
        // sorts by x then by type.
        public int compareTo(Event b)
        {
            if (x != b.x) return x - b.x;
            else return type.compareTo(b.type);
        }
    }
 
    public static List<Point> hvIntersections(List<Line> lines)
    {
        ArrayList<Point> ans = new ArrayList<Point>();
        // number of active horizontal lines with each y value
        SortedMap<Integer, Integer> active = new TreeMap<Integer, Integer>();
        ArrayList<Event> events = new ArrayList<Event>();
 
        for (Line line : lines)
        {
            if (line.y1 != line.y2)
                events.add(new Event(EventType.EVENT_VERTICAL, line.x1, line));
            else
            {
                events.add(new Event(EventType.EVENT_START, line.x1, line));
                events.add(new Event(EventType.EVENT_END, line.x2, line));
            }
        }
        Collections.sort(events);
 
        for (Event e : events)
        {
            switch (e.type)
            {
                case EVENT_START:
                    {
                        // Increment count of lines with y == e.line.y1
                        Integer count = active.get(e.line.y1);
                        active.put(e.line.y1, count == null ? 1 : count + 1);
                    }
                    break;
                case EVENT_END:
                    {
                        // Decrement count of lines with y == e.line.y1
                        int count = active.get(e.line.y1) - 1;
                        if (count > 0) active.put(e.line.y1, count);
                        else active.remove(e.line.y1);
                    }
                    break;
                case EVENT_VERTICAL:
                    {
                        // Iterate over active horizontal lines with suitable y values
                        SortedMap<Integer, Integer> view = active.subMap(e.line.y1 + 1, e.line.y2);
                        for (Map.Entry<Integer, Integer> i : view.entrySet())
                        {
                            for (int j = 0; j < i.getValue(); j++)
                                ans.add(new Point(e.line.x1, i.getKey()));
                        }
                    }
                    break;
            }
        }
        return ans;
    }
}
