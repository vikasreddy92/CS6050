package project;

public class Circle {

	Vertex center;
	int radius;

	Circle(Vertex center, int radius) {
		this.center = center;
		this.radius = radius;
	}

	@Override
	public String toString() {
		return "Circle" + " " + center.toString() + " " + Integer.toString(radius);
	}

}
