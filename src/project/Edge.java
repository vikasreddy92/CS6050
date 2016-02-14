package project;

public class Edge {

    Vertex u, v;
    
    Edge(Vertex u, Vertex v) {
        this.u = u;
        this.v = v;
    }
    
    public int hashCode() {
        return u.hashCode() + v.hashCode();
    }
    
    public boolean equals(Object o) {
        Edge e = (Edge) o;
        return u == e.u && v == e.v || u == e.v && v == e.u;
    }
    
    public String toString() {
        return u + " " + v;
    }

}
