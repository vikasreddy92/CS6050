package project;

import java.awt.Color;

public class Node {

	int x, y;
	Color brushColor;

	Node(int x, int y, Color color) {
		this.x = x;
		this.y = y;
		this.brushColor = color;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((brushColor == null) ? 0 : brushColor.hashCode());
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (brushColor == null)
		{
			if (other.brushColor != null)
				return false;
		}
		else if (!brushColor.equals(other.brushColor))
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	@Override
	public String toString() {
		float[] brush = getComponents(brushColor);
		return brush[0] + " " + brush[1] + " " + brush[2] + " " + x + " " + y;
	}
	private float[] getComponents(Color color)
	{
		float[] colors = new float[3];
		float MAX_COLOR = 255f;
		colors[0] = ((float) color.getRed()) / MAX_COLOR;
		colors[1] = ((float) color.getGreen()) / MAX_COLOR;
		colors[2] = ((float) color.getBlue()) / MAX_COLOR;
		return colors;
	}
}