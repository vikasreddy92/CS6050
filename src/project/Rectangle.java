package project;

public class Rectangle {
	Vertex origin;
	int width;
	int height;
	int thickness;

	public Rectangle(Vertex origin, int width, int height, int thickness) {
		this.origin = origin;
		this.width = width;
		this.height = height;
		this.thickness = thickness;
	}

	public Rectangle(Vertex origin, int width, int height) {
		this.origin = origin;
		this.width = width;
		this.height = height;
		this.thickness = 1;
	}

	public Rectangle(int x, int y, int width, int height) {
		this.origin = new Vertex(x, y);
		this.width = width;
		this.height = height;
	}

	@Override
	public int hashCode() {
		return this.origin.hashCode() + width + height;
	}

	@Override
	public boolean equals(Object o) {
		Rectangle r = (Rectangle) o;
		return this.origin.equals(r.origin) && this.width == r.width && this.height == r.height;
	}

	@Override
	public String toString() {
		return origin + " " + width + " " + height;
	}
}
