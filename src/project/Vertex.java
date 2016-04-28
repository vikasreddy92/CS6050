package project;

public class Vertex {

	public int getX()
	{
		return x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	int x, y;

	Vertex(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public int hashCode() {
		return x + y;
	}

	@Override
	public boolean equals(Object o) {
		Vertex p = (Vertex) o;
		return x == p.x && y == p.y;
	}

	@Override
	public String toString() {
		return x + " " + y;
	}
}