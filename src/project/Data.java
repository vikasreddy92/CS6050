package project;

import java.util.HashSet;

public class Data {

    Editor editor;
    HashSet<Vertex> vertices = new HashSet<>();
    HashSet<Edge> edges = new HashSet<>();
    Vertex p;
    
    Data(Editor editor) {
        this.editor = editor;
    }
    
    void add(int x, int y) {
        vertices.add(new Vertex(x, y));
    }
    
    void remove(int x, int y) {
        Vertex p = findVertex(x, y);
        if (p != null) {
            for (Edge e : edges)
                if (e.u == p || e.v == p)
                    edges.remove(e);
            vertices.remove(p);
        }
    }
    
    void move(int x, int y) {
        if (p == null)
            p = findVertex(x, y);
        else {
            Vertex q = findVertex(x, y);
            if (q != null && q.x == x && q.y == y) {	// merge?
            } else {
                vertices.remove(p);
                p.x = x;
                p.y = y;
                vertices.add(p);
            }
            p = null;
        }
    }
    
    void mark(int x, int y) {
        Vertex q = findVertex(x, y);
        if (p == null)
            p = q;
        else if (q != null) {
            if (editor.window.box.mode == Box.AE)
                edges.add(new Edge(p, q));
            else if (editor.window.box.mode == Box.RE)
                edges.remove(new Edge(p, q));
            p = null;
        }
    }
    
    int dist2(int x1, int y1, int x2, int y2) {
        int x = x1 - x2;
        int y = y1 - y2;
        return x * x + y * y;
    }
    
    Vertex findVertex(int x, int y) {
        int min = Integer.MAX_VALUE;
        Vertex q = null;
        for (Vertex p : vertices) {
            int d = dist2(p.x, p.y, x, y);
            if (min > d) {
                min = d;
                q = p;
            }
        }
        return q;
    }
    
    public String toString() {
        return vertices.size() + " " + edges.size();
    }
    
    String toText() {
        StringBuilder sb = new StringBuilder();
        for (Vertex v : vertices)
            sb.append("vertex " + v + "\n");
        for (Edge e : edges)
            sb.append("edge " + e + "\n");
        return sb.toString();
    }

}
