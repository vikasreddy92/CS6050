package project;

import java.awt.Color;

public class Circle {

	Vertex center;
	int radius;
	int thickness;
	Color brushColor;
	Color fillColor;
	
	Circle(Vertex center, int radius, int thickness, Color brushColor, Color fillColor) {
		this.center = center;
		this.radius = radius;
		this.thickness = thickness;
		this.brushColor = brushColor;
		this.fillColor = fillColor;
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
		float[] brushColors = getComponents(brushColor);
		float[] fillColors = getComponents(fillColor);
		return brushColors[0] + " " + brushColors[1] + " " + brushColors[2] + " " + thickness + " " + center + " " + radius + " ";
//		return fillColors[0] + " " + fillColors[1] + " " + fillColors[2] + " " + brushColors[0] + " " + brushColors[1] + " " + brushColors[2] + " " + thickness + " " + center + " " + radius + " ";
	}
	
	private float[] getComponents(Color color) {
		float[] colors = new float[3];
		float MAX_COLOR = 255f;
		colors[0] = ((float) color.getRed())/MAX_COLOR;
		colors[1] = ((float) color.getGreen())/MAX_COLOR;
		colors[2] = ((float) color.getBlue())/MAX_COLOR;
		return colors;
	}
}
