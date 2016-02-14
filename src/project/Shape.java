package project;

import java.util.ArrayList;
import java.util.List;

public class Shape {

	List<Circle> circles;
	Editor editor;

	Shape(Editor editor) {
		this.editor = editor;
		circles = new ArrayList<Circle>();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Circle circle : circles) {
			sb.append(circle.toString());
			sb.append("\n");
		}
		return sb.toString();
	}
	
	public void add(int sX, int sY, int radius) {
		Circle circle = new Circle(new Vertex(sX, sY), radius);
		this.circles.add(circle);
	}

}
