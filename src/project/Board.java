package project;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputListener;

class Board extends JPanel implements MouseInputListener
{

	private static final long serialVersionUID = 1L;
	private static int sX = Integer.MIN_VALUE, sY = Integer.MIN_VALUE, currX = Integer.MIN_VALUE,
			currY = Integer.MIN_VALUE;
	private static boolean dragging = false, moving = false;
	private static int brushSize = 2;
	private static Color brushColor = Color.BLACK;
	private static Color fillColor = Color.WHITE;

	public boolean convexHull = false;
	public boolean voronoiDiagram = false;
	public static boolean textField = false;

	Editor editor;

	Node movingNode;
	Edge movingEdge;
	Circle movingCircle;
	Rectangle movingRectangle;
	Polygon movingPolygon;

	ArrayList<Vertex> polygonVertices;

	Board(Editor editor)
	{
		this.editor = editor;
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	public void paint(Graphics g)
	{
		g.setColor(Color.WHITE);
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		for (TextBox t : editor.data.textBoxes)
			drawText(t, g2d);
		for (Polygon p1 : editor.data.polygons)
			drawPolygon(p1, g2d);
		for (Circle c : editor.data.circles)
			drawCircle(c, g);
		for (Rectangle r1 : editor.data.rectangles)
			drawRectangle(r1, g2d);
		for (Node n : editor.data.nodes)
			drawNode(n, g2d);
		for (Edge e : editor.data.edges)
			drawEdge(e, g2d);

		if (convexHull && editor.data.convexHull != null)
		{
			int size = editor.data.convexHull.length;
			g2d.setStroke(new BasicStroke(2));
			g2d.setColor(Color.RED);
			if (size > 2)
			{
				Vertex[] hull = editor.data.convexHull;
				for (int i = 1; i < size; i++)
					g2d.drawLine(hull[i - 1].x, hull[i - 1].y, hull[i].x, hull[i].y);
				g2d.drawLine(hull[size - 1].x, hull[size - 1].y, hull[0].x, hull[0].y);
			}
			g2d.setStroke(new BasicStroke(brushSize));
			g2d.setColor(brushColor);
		}
		if (voronoiDiagram && editor.data.voronoiDiagram != null && !editor.data.voronoiDiagram.isEmpty())
		{
			for (VoronoiCell v : editor.data.voronoiDiagram)
			{
				g2d.setColor(v.color);
				for (Vertex u : v.vertices)
					g2d.fillRect(u.x, u.y, 1, 1);
			}
			g2d.setColor(Color.BLACK);
			for (Node n : editor.data.nodes)
				drawNode(n, g2d);
			g2d.setColor(brushColor);
		}

		if (editor.window.box.mode.equals(editor.window.box.MN))
		{
			if (dragging && movingNode != null)
			{
				g2d.setColor(movingNode.brushColor);
//				System.out.println("paint() - MN: " + currX + ", " + currY);
				g2d.fillOval(currX, currY, 20, 20);
			}
			g2d.setColor(brushColor);
		}
		else if (editor.window.box.mode.equals(editor.window.box.AC))
		{
			int radius = getRadius(currX, currY, sX, sY);
			drawCircle(sX, sY, radius, brushColor, brushSize, g);
		}
		else if (editor.window.box.mode.equals(editor.window.box.MC))
		{
			if (dragging && movingCircle != null)
				drawCircle(sX, sY, movingCircle.radius, movingCircle.brushColor, movingCircle.thickness, g);
		}
		else if (editor.window.box.mode.equals(editor.window.box.AR))
		{
			if (dragging)
				drawRect(sX, sY, currX, currY, g);
		}
		else if (editor.window.box.mode.equals(editor.window.box.AL))
		{
			g2d.setColor(brushColor);
			g2d.setStroke(new BasicStroke(brushSize));
			if (dragging)
				g2d.drawLine(sX, sY, currX, currY);
		}
		else if (editor.window.box.mode.equals(editor.window.box.MR))
		{
			if (dragging)
			{
				g2d.setColor(movingRectangle.brushColor);
				g2d.setStroke(new BasicStroke(movingRectangle.thickness));
				g2d.drawRect(movingRectangle.origin.x + (currX - sX), movingRectangle.origin.y + (currY - sY),
						movingRectangle.width, movingRectangle.height);
			}
			g2d.setColor(brushColor);
			g2d.setStroke(new BasicStroke(brushSize));
		}
		else if (editor.window.box.mode.equals(editor.window.box.AP))
		{
			if (polygonVertices != null)
			{
				int n = polygonVertices.size();
				for (int i = 0; i < n - 1; i++)
					drawEdge(new Edge(polygonVertices.get(i), polygonVertices.get(i + 1), brushSize,
							brushColor), g2d);
			}
			if (moving)
			{
				g2d.setColor(brushColor);
				g2d.setStroke(new BasicStroke(brushSize));
				g2d.drawLine(sX, sY, currX, currY);
			}
		}
	}

	private int getRadius(int currX2, int currY2, int sX2, int sY2)
	{
		if (Math.abs(currX2 - sX2) < 10 || Math.abs(currY2 - sY2) < 10)
			return 0;
		return (int) Math.sqrt((double) ((currX2 - sX2) * (currX2 - sX2) + (currY2 - sY2) * (currY2 - sY2)));
	}

	public void mouseClicked(MouseEvent e)
	{
		System.out.println("Board.java: " + editor.window.box.mode);
	}

	public void mouseMoved(MouseEvent e)
	{
		if (editor.window.box.mode.equals(editor.window.box.AP))
		{
			currX = e.getX();
			currY = e.getY();
		}
		editor.refresh();
		editor.window.message.setText(editor.data + "   " + "X: " + e.getX() + ", Y: " + e.getY());
	}

	public void mouseExited(MouseEvent e)
	{
		editor.window.message.setText(editor.data.toString());
		editor.refresh();
	}

	public void mouseEntered(MouseEvent e)
	{
		editor.window.message.setText(editor.data.toString());
		editor.refresh();
	}

	public void mouseDragged(MouseEvent e)
	{
		dragging = true;
		if (editor.window.box.mode.equals(editor.window.box.MN))
		{
			if (movingNode != null)
			{
				currX = e.getX();
				currY = e.getY();
			}
		}
		else if (editor.window.box.mode.equals(editor.window.box.AC))
		{
			currX = e.getX();
			currY = e.getY();
		}
		else if (editor.window.box.mode.equals(editor.window.box.MC))
		{
			if (movingCircle != null)
			{
				sX = movingCircle.center.x - (currX - e.getX());
				sY = movingCircle.center.y - (currY - e.getY());
			}
		}
		else if (editor.window.box.mode.equals(editor.window.box.AR))
		{
			currX = e.getX();
			currY = e.getY();
		}
		else if (editor.window.box.mode.equals(editor.window.box.AL))
		{
			currX = e.getX();
			currY = e.getY();
		}
		else if (editor.window.box.mode.equals(editor.window.box.MR))
		{
			currX = e.getX();
			currY = e.getY();
		}
		else if (editor.window.box.mode.equals(editor.window.box.AP))
		{
			currX = e.getX();
			currY = e.getY();
		}
		editor.window.message.setText(editor.data + "   " + "X: " + e.getX() + ", Y: " + e.getY());
		editor.refresh();
	}

	public void mousePressed(MouseEvent e)
	{
		int x = e.getX();
		int y = e.getY();
		System.out.println("Board.java: " + editor.window.box.mode + ", isRightClick: "
				+ SwingUtilities.isRightMouseButton(e));
		if (SwingUtilities.isRightMouseButton(e))
		{
			movingNode = null;
			movingCircle = null;
			movingRectangle = null;
			editor.data.tempNode = null;
			editor.data.tempEdge = null;
			editor.data.tempCircle = null;
			editor.data.tempRectangle = null;
			editor.data.tempPolygon = null;
		}
		else if (textField)
		{
			TextBox temp = editor.window.textBoxComponent.getTextBox();
			temp.setTextLocation(new Vertex(x, y));
			editor.data.textBoxes.add(temp);
			this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			textField = false;
		}
		else if (editor.window.box.mode.equals(editor.window.box.AN))
		{
			editor.data.add(new Node(x, y, brushColor));
			if (voronoiDiagram)
				VoronoiDiagram.updateVoronoiDiagram(new Node(x, y, brushColor));
		}
		else if (editor.window.box.mode.equals(editor.window.box.SN))
		{
			movingNode = editor.data.moveNode(x, y);
		}
		else if (editor.window.box.mode.equals(editor.window.box.MN))
		{
			movingNode = editor.data.moveNode(x, y);
			if (movingNode != null)
			{
				System.out.println("Moving node: " + movingNode);
				sX = movingNode.x;
				sY = movingNode.y;
				currX = x;
				currY = y;
			}
		}
		else if (editor.window.box.mode.equals(editor.window.box.RN))
		{
			editor.data.removeNode(x, y);
		}
		else if (editor.window.box.mode.equals(editor.window.box.AC))
		{
			currX = sX = x;
			currY = sY = y;
		}
		else if (editor.window.box.mode.equals(editor.window.box.SC))
		{
			movingCircle = editor.data.moveCircle(x, y);
		}
		else if (editor.window.box.mode.equals(editor.window.box.MC))
		{
			movingCircle = editor.data.moveCircle(x, y);
			if (movingCircle != null)
			{
				sX = movingCircle.center.x;
				sY = movingCircle.center.y;
				currX = x;
				currY = y;
			}
		}
		else if (editor.window.box.mode.equals(editor.window.box.RC))
		{
			editor.data.removeCircle(x, y);
		}
		else if (editor.window.box.mode.equals(editor.window.box.AR))
		{
			currX = sX = x;
			currY = sY = y;
		}
		else if (editor.window.box.mode.equals(editor.window.box.SR))
		{
			movingRectangle = editor.data.moveRectangle(x, y);
		}
		else if (editor.window.box.mode.equals(editor.window.box.MR))
		{
			movingRectangle = editor.data.moveRectangle(x, y);
			if (movingRectangle != null)
			{
				currX = sX = x;
				currY = sY = y;
				brushSize = movingRectangle.thickness;
			}
		}
		else if (editor.window.box.mode.equals(editor.window.box.RR))
		{
			editor.data.removeRectangle(x, y);
		}
		else if (editor.window.box.mode.equals(editor.window.box.AL))
		{
			currX = sX = x;
			currY = sY = y;
		}
		else if (editor.window.box.mode.equals(editor.window.box.RL))
		{
			editor.data.removeLine(x, y);
		}
		else if (editor.window.box.mode.equals(editor.window.box.AP))
		{
			if (polygonVertices == null)
			{
				polygonVertices = new ArrayList<Vertex>();
				currX = sX = x;
				currY = sY = y;
				polygonVertices.add(new Vertex(sX, sY));
				moving = true;
			}
			else
			{
				polygonVertices.add(new Vertex(currX, currY));
				sX = currX;
				currX = x;
				sY = currY;
				currY = y;
			}
		}
		else if (editor.window.box.mode.equals(editor.window.box.RP))
		{
			editor.data.removePolygon(x, y);
		}
		else if (editor.window.box.mode.equals(editor.window.box.UN))
		{
			if (editor.data.tempNode != null || movingNode != null)
			{
				editor.data.tempNode = null;
				movingNode = null;
			}
		}
		else if (editor.window.box.mode.equals(editor.window.box.UL))
		{
			if (editor.data.tempEdge != null)
			{
				editor.data.tempEdge = null;
			}
		}
		else if (editor.window.box.mode.equals(editor.window.box.UC))
		{
			if (editor.data.tempCircle != null || movingCircle != null)
			{
				movingCircle = null;
				editor.data.tempCircle = null;
			}
		}
		else if (editor.window.box.mode.equals(editor.window.box.UR))
		{
			if (editor.data.tempRectangle != null || movingRectangle != null)
			{
				movingRectangle = null;
				editor.data.tempRectangle = null;
			}
		}
		else if (editor.window.box.mode.equals(editor.window.box.UP))
		{
			if (editor.data.tempPolygon != null)
			{
				movingRectangle = null;
				editor.data.tempPolygon = null;
			}
		}
		editor.refresh();
	}

	public void mouseReleased(MouseEvent e)
	{
		dragging = false;
		if (textField)
		{
			System.out.println("Board.java: MR textField - " + textField);
		}
		else if (editor.window.box.mode.equals(editor.window.box.MN))
		{
			if (movingNode != null)
			{
				editor.data.add(new Node(currX, currY, movingNode.brushColor));
				editor.data.vertices.remove(movingNode);
				editor.data.nodes.remove(movingNode);
			}
		}
		else if (editor.window.box.mode.equals(editor.window.box.AC))
		{
			int radius = getRadius(currX, currY, sX, sY);
			if (radius != 0)
				editor.data.add(new Circle(new Vertex(sX, sY), radius, brushSize, brushColor, fillColor));
			currX = sX = Integer.MIN_VALUE;
			currY = sY = Integer.MIN_VALUE;

		}
		else if (editor.window.box.mode.equals(editor.window.box.MC))
		{
			if (movingCircle != null)
			{
				editor.data.add(new Circle(new Vertex(sX, sY), movingCircle.radius, movingCircle.thickness,
						movingCircle.brushColor, movingCircle.fillColor));
				editor.data.circles.remove(movingCircle);
			}
			currX = sX = Integer.MIN_VALUE;
			currY = sY = Integer.MIN_VALUE;
		}
		else if (editor.window.box.mode.equals(editor.window.box.AR))
		{
			if (sX != currX && sY != currY)
			{
				editor.data.add(getReactangle(sX, sY, currX, currY, brushSize, brushColor, fillColor));
			}
				currX = sX = Integer.MIN_VALUE;
				currY = sY = Integer.MIN_VALUE;
		}
		else if (editor.window.box.mode.equals(editor.window.box.MR))
		{
			// System.out.println("Board.java:MR: " + currX + ", " + currY + " -- " + sX + ", " + sY);
			if (movingRectangle != null && !(currX == sX && currY == sY))
			{
				editor.data.add(new Rectangle(
						new Vertex(movingRectangle.origin.x + (currX - sX),
								movingRectangle.origin.y + (currY - sY)),
						movingRectangle.width, movingRectangle.height, movingRectangle.thickness,
						movingRectangle.brushColor, movingRectangle.fillColor));
				editor.data.vertices.remove(movingRectangle);
				editor.data.rectangles.remove(movingRectangle);
			}
			// currX = sX = Integer.MIN_VALUE;
			// currY = sY = Integer.MIN_VALUE;
		}
		else if (editor.window.box.mode.equals(editor.window.box.AL))
		{
			if (sX != currX && sY != currY)
			{
				editor.data
						.add(new Edge(new Vertex(sX, sY), new Vertex(currX, currY), brushSize, brushColor));
			}
				currX = sX = Integer.MIN_VALUE;
				currY = sY = Integer.MIN_VALUE;
		}
		else if (editor.window.box.mode.equals(editor.window.box.AP))
		{
			if (Polygon.isPolygone(polygonVertices))
			{
				editor.data.add(new Polygon(polygonVertices.clone(), brushSize, brushColor, fillColor));
				polygonVertices.clear();
				polygonVertices = null;
				moving = false;
				currX = sX = Integer.MIN_VALUE;
				currY = sY = Integer.MIN_VALUE;
			}
		}
		editor.refresh();
	}

	private void drawText(TextBox t, Graphics2D g2d)
	{
		g2d.setColor(t.getTextColor());
		g2d.setStroke(new BasicStroke(t.getTextSize()));
		g2d.setFont(t.getTextFont());
		g2d.drawString(t.text, t.textLocation.x, t.textLocation.y);
		g2d.setColor(brushColor);
	}

	public void drawCircle(Circle c, Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		int r = 3;
		int x = c.center.x;
		int y = c.center.y;
		int radius = c.radius;
		if (c != editor.data.tempCircle)
		{
			g2d.setColor(c.brushColor);
			g2d.setStroke(new BasicStroke(c.thickness));
			g2d.fillOval(x - r, y - r, r * 2, r * 2);
			g2d.drawOval(x - radius, y - radius, radius * 2, radius * 2);

			radius = radius - c.thickness / 2;
			g2d.setColor(c.fillColor);
			g2d.fillOval(x - radius, y - radius, radius * 2, radius * 2);

			g2d.setColor(brushColor);
		}
		else
		{
			radius += 3;
			g2d.setColor(c.brushColor);
			g2d.setStroke(new BasicStroke(c.thickness));
			g2d.fillOval(x - r, y - r, r * 2, r * 2);
			g2d.drawOval(x - radius, y - radius, radius * 2, radius * 2);

			radius = radius - c.thickness / 2;

			g2d.setColor(c.fillColor);
			g2d.fillOval(x - radius, y - radius, radius * 2, radius * 2);

			g2d.setColor(brushColor);
		}
	}

	public void drawCircle(int x, int y, int radius, Color brushColor, int brushSize, Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		int r = 5;
		g2d.setColor(brushColor);
		g2d.setStroke(new BasicStroke(brushSize));
		g2d.fillOval(x - r, y - r, r * 2, r * 2);
		g2d.drawOval(x - radius, y - radius, radius * 2, radius * 2);

		g2d.setColor(fillColor);
		radius = radius - brushSize / 2;
		g2d.fillOval(x - radius, y - radius, radius * 2, radius * 2);

	}

	public void drawRect(int sX2, int sY2, int currX2, int currY2, Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(brushSize));
		g2d.setColor(brushColor);
		Rectangle rect = getReactangle(sX2, sY2, currX2, currY2, brushSize, brushColor, fillColor);
		g2d.drawRect(rect.origin.x, rect.origin.y, rect.width, rect.height);
	}

	public Rectangle getReactangle(int sX2, int sY2, int currX2, int currY2, int thickness, Color brushColor,
			Color fillColor)
	{
		int width = (int) (-sX2 + currX2 + ((thickness % 2 == 0) ? 0.5 * thickness : 0.5 * (thickness + 1)));
		int height = (int) (-sY2 + currY2 + ((thickness % 2 == 0) ? 0.5 * thickness : 0.5 * (thickness + 1)));
		if (width < 0 && height > 0)
			return new Rectangle(new Vertex(currX2, sY2), -width, height, brushSize, brushColor, fillColor);
		else if (width > 0 && height < 0)
			return new Rectangle(new Vertex(sX2, currY2), width, -height, brushSize, brushColor, fillColor);
		else if (width < 0 && height < 0)
			return new Rectangle(new Vertex(currX2, currY2), -width, -height, brushSize, brushColor,
					fillColor);
		else
			return new Rectangle(new Vertex(sX2, sY2), width, height, brushSize, brushColor, fillColor);
	}

	private void drawRectangle(Rectangle r1, Graphics2D g2d)
	{
		if (r1 != editor.data.tempRectangle)
		{
			g2d.setStroke(new BasicStroke(r1.thickness));
			g2d.setColor(r1.brushColor);
			g2d.drawRect(r1.origin.x, r1.origin.y, r1.width, r1.height);
			g2d.setColor(r1.fillColor);
			if (r1.thickness % 2 != 0)
				g2d.fillRect(r1.origin.x + (int) (0.5 * (r1.thickness + 1)),
						r1.origin.y + (int) (0.5 * (r1.thickness + 1)), r1.width - (int) (r1.thickness),
						r1.height - (int) (r1.thickness));
			else
				g2d.fillRect(r1.origin.x + (int) (0.5 * r1.thickness),
						r1.origin.y + (int) (0.5 * r1.thickness), r1.width - (int) (r1.thickness),
						r1.height - (int) (r1.thickness));
		}
		else
		{
			g2d.setStroke(new BasicStroke(r1.thickness + 3));
			g2d.setColor(r1.brushColor);
			g2d.drawRect(r1.origin.x, r1.origin.y, r1.width, r1.height);
			g2d.setColor(r1.fillColor);
			g2d.fillRect(r1.origin.x + (int) (0.5 * r1.thickness), r1.origin.y + (int) (0.5 * r1.thickness),
					r1.width - (int) (r1.thickness), r1.height - (int) (r1.thickness));

		}
		g2d.setColor(brushColor);
	}

	private void drawPolygon(Polygon p1, Graphics2D g2d)
	{
		if (p1 != editor.data.tempPolygon)
		{
			g2d.setStroke(new BasicStroke(p1.thickness + 3));
			g2d.setColor(p1.brushColor);
			g2d.drawPolygon(p1.getXPoints(), p1.getYPoints(), p1.vertices.size());
			g2d.setColor(p1.fillColor);
			g2d.fillPolygon(p1.getXPoints(), p1.getYPoints(), p1.vertices.size());
		}
		else
		{
			g2d.setStroke(new BasicStroke(p1.thickness + 6));
			g2d.setColor(p1.brushColor);
			g2d.drawPolygon(p1.getXPoints(), p1.getYPoints(), p1.vertices.size());
			g2d.setColor(p1.fillColor);
			g2d.fillPolygon(p1.getXPoints(), p1.getYPoints(), p1.vertices.size());
		}
		g2d.setColor(brushColor);
	}

	private void drawEdge(Edge e, Graphics2D g2d)
	{
		if (e != editor.data.tempEdge)
		{
			g2d.setStroke(new BasicStroke(e.thickness));
			g2d.setColor(e.color);
			g2d.drawLine(e.u.x, e.u.y, e.v.x, e.v.y);
		}
		else
		{
			g2d.setStroke(new BasicStroke(e.thickness + 3));
			g2d.setColor(e.color);
			g2d.drawLine(e.u.x, e.u.y, e.v.x, e.v.y);
		}
		g2d.setColor(brushColor);
	}

	private void drawNode(Node n, Graphics2D g2d)
	{
		int r = 10;
		if (n != editor.data.tempNode)
		{
			g2d.setColor(n.brushColor);
			g2d.fillOval(n.x - r, n.y - r, r * 2, r * 2);
		}
		else
		{
			r += 5;
			g2d.setColor(n.brushColor);
			g2d.fillOval(n.x - r, n.y - r, r * 2, r * 2);
			r -= 5;
		}
		g2d.setColor(brushColor);
	}

	public int getBrushSize()
	{
		return brushSize;
	}

	public void setBrushSize(int brushSize)
	{
		Board.brushSize = brushSize;
	}

	public Color getBrushColor()
	{
		return brushColor;
	}

	public void setFillColor(Color fillColor)
	{
		Board.fillColor = fillColor;
	}

	public Color getFillColor()
	{
		return Board.fillColor;
	}

	public void setBrushColor(Color brushColor)
	{
		Board.brushColor = brushColor;
	}
}
