package project;

public class Circle {

	Vertex center;
	int radius;

	Circle(Vertex center, int radius) {
		this.center = center;
		this.radius = radius;
	}

	@Override
	public int hashCode() {
		return this.center.hashCode() + this.radius;
	}

	@Override
	public boolean equals(Object o) {
		Circle c = (Circle) o;
		return (this.center.equals(c.center) && this.radius == c.radius);
	}

	@Override
	public String toString() {
		return center.toString() + " " + Integer.toString(radius);
	}
}
