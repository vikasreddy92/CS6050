package project;

public class Vertex {

	int x, y;

	Vertex(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int hashCode() {
		return x + y;
	}

	public boolean equals(Object o) {
		Vertex p = (Vertex) o;
		return x == p.x && y == p.y;
	}

	public String toString() {
		return x + " " + y;
	}

}
