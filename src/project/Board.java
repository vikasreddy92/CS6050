package project;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.event.MouseInputListener;

class Board extends JPanel implements MouseInputListener
{

	private static final long serialVersionUID = 1L;
	private static int sX = Integer.MIN_VALUE, sY = Integer.MIN_VALUE, currX = Integer.MIN_VALUE,
			currY = Integer.MIN_VALUE;
	private static boolean dragging = false, moving = false;
	private static int brushSize = 1;
	private static Color brushColor = Color.BLACK;
	private static Color fillColor = Color.WHITE;

	Editor editor;
	
	Node movingNode;
	Circle movingCircle;
	Rectangle movingRectangle;
	ArrayList<Vertex> polygonVertices;

	Board(Editor editor)
	{
		this.editor = editor;
		AffineTransform at = new AffineTransform();
		at.translate(0, 800);
		super.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), "Canvas"));
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	public void paint(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;

		for (Polygon p1 : editor.data.polygons)
			drawPolygon(p1, g2d);

		for (Node n : editor.data.nodes)
			drawNode(n, g2d);

		for (Edge e : editor.data.edges)
			drawEdge(e, g2d);

		for (Circle c : editor.data.circles)
			drawCircle(c, g);

		for (Rectangle r1 : editor.data.rectangles)
			drawRectangle(r1, g2d);

		if (editor.window.box.mode.equals(editor.window.box.AC))
		{
			int radius = Math.abs(currX - sX);
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
				g.drawRect(currX, currY, movingRectangle.width, movingRectangle.height);
		}
		else if (editor.window.box.mode.equals(editor.window.box.AP))
		{
			if (polygonVertices != null)
			{
				int n = polygonVertices.size();
				for (int i = 0; i < n - 1; i++)
					drawEdge(new Edge(polygonVertices.get(i), polygonVertices.get(i + 1), brushSize, brushColor), g2d);
			}
			if (moving)
			{
				g2d.setStroke(new BasicStroke(brushSize));
				g2d.drawLine(sX, sY, currX, currY);
			}
		}
	}

	public void mouseClicked(MouseEvent e)
	{
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
		if (editor.window.box.mode.equals(editor.window.box.AC))
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

		if (editor.window.box.mode.equals(editor.window.box.AN))
		{
			editor.data.add(new Node(x, y, brushColor));
		}
		else if (editor.window.box.mode.equals(editor.window.box.MN))
		{
			movingNode = editor.data.moveNode(x, y);
			if (movingNode != null)
			{
				sX = movingNode.x;
				sY = movingNode.y;
			}
		}
		else if (editor.window.box.mode.equals(editor.window.box.RN))
		{
			editor.data.removeNode(x, y);
		}
		else if (editor.window.box.mode.equals(editor.window.box.AC))
		{
			sX = x;
			sY = y;
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
		else if (editor.window.box.mode.equals(editor.window.box.MR))
		{
			movingRectangle = editor.data.moveRectangle(x, y);
			if (movingRectangle != null)
			{
				currX = x - movingRectangle.origin.x;
				currY = y - movingRectangle.origin.y;
				brushSize = movingRectangle.thickness;
				// fillColor = movingRectangle.fillColor;
			}
		}
		else if (editor.window.box.mode.equals(editor.window.box.RR))
		{
			editor.data.removeRectangle(x, y);
		}
		else if (editor.window.box.mode.equals(editor.window.box.AL))
		{
			sX = x;
			sY = y;
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
		editor.refresh();
	}

	public void mouseReleased(MouseEvent e)
	{
		dragging = false;
		if (editor.window.box.mode.equals(editor.window.box.AC))
		{
			int radius = Math.abs(sX - currX);
			editor.data.add(new Circle(new Vertex(sX, sY), radius, brushSize, brushColor));
			currX = sX = Integer.MIN_VALUE;
			currY = sY = Integer.MIN_VALUE;
		}
		else if (editor.window.box.mode.equals(editor.window.box.MC))
		{
			if (movingCircle != null)
			{
				editor.data.add(new Circle(new Vertex(sX, sY), movingCircle.radius, movingCircle.thickness,
						movingCircle.brushColor));
				editor.data.circles.remove(movingCircle);
			}
			currX = sX = Integer.MIN_VALUE;
			currY = sY = Integer.MIN_VALUE;
		}
		else if (editor.window.box.mode.equals(editor.window.box.AR))
		{
			editor.data.add(getReactangle(sX, sY, currX, currY, brushSize, brushColor, fillColor));
			currX = sX = Integer.MIN_VALUE;
			currY = sY = Integer.MIN_VALUE;
		}
		else if (editor.window.box.mode.equals(editor.window.box.MR))
		{
			if (movingRectangle != null)
			{
				editor.data.add(new Rectangle(new Vertex(currX, currY), movingRectangle.width, movingRectangle.height,
						movingRectangle.thickness, movingRectangle.brushColor));
				editor.data.rectangles.remove(movingRectangle);
			}
			currX = sX = Integer.MIN_VALUE;
			currY = sY = Integer.MIN_VALUE;
		}
		else if (editor.window.box.mode.equals(editor.window.box.AL))
		{
			editor.data.add(new Edge(new Vertex(sX, sY), new Vertex(currX, currY), brushSize, brushColor));
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
			g2d.setColor(c.brushColor);
			g2d.setStroke(new BasicStroke(c.thickness));
			g2d.fillOval(x - r, y - r, r * 2, r * 2);
			g2d.drawOval(x - radius, y - radius, radius * 2, radius * 2);

			/*
			 * radius = radius - 1;// - c.thickness + 1;
			 * g2d.setColor(c.fillColor); g2d.fillOval(x - radius, y - radius,
			 * radius * 2, radius * 2);
			 */
		}
		else
		{
			radius += 3;
			g2d.setColor(Color.RED);
			g2d.setStroke(new BasicStroke(c.thickness));
			g2d.fillOval(x - r, y - r, r * 2, r * 2);
			g2d.drawOval(x - radius, y - radius, radius * 2, radius * 2);
			/*
			 * radius = radius - 1;// - c.thickness + 1;
			 * g2d.setColor(c.fillColor); g2d.fillOval(x - radius, y - radius,
			 * radius * 2, radius * 2);
			 */
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
		/*
		 * g2d.setColor(fillColor); radius = radius - 1; g2d.fillOval(x -
		 * radius, y - radius, radius * 2, radius * 2);
		 */
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
			return new Rectangle(new Vertex(currX2, sY2), -width, height, brushSize, brushColor);
		else if (width > 0 && height < 0)
			return new Rectangle(new Vertex(sX2, currY2), width, -height, brushSize, brushColor);
		else if (width < 0 && height < 0)
			return new Rectangle(new Vertex(currX2, currY2), -width, -height, brushSize, brushColor);
		else
			return new Rectangle(new Vertex(sX2, sY2), width, height, brushSize, brushColor);
	}

	private void drawRectangle(Rectangle r1, Graphics2D g2d)
	{
		if (r1 != editor.data.tempRectangle)
		{
			g2d.setStroke(new BasicStroke(r1.thickness));
			g2d.setColor(r1.brushColor);
			g2d.drawRect(r1.origin.x, r1.origin.y, r1.width, r1.height);
			/*
			 * g2d.setColor(r1.fillColor); g2d.fillRect(r1.origin.x + (int) (0.5
			 * * r1.thickness), r1.origin.y + (int) (0.5 * r1.thickness),
			 * r1.width - (int) (r1.thickness), r1.height - (int)
			 * (r1.thickness));
			 */
		}
		else
		{
			g2d.setStroke(new BasicStroke(r1.thickness + 3));
			g2d.setColor(Color.RED);
			g2d.drawRect(r1.origin.x, r1.origin.y, r1.width, r1.height);
			/*
			 * g2d.setColor(r1.fillColor); g2d.fillRect(r1.origin.x + (int) (0.5
			 * * r1.thickness), r1.origin.y + (int) (0.5 * r1.thickness),
			 * r1.width - (int) (r1.thickness), r1.height - (int)
			 * (r1.thickness));
			 */
		}
		g2d.setColor(brushColor);
	}

	private void drawPolygon(Polygon p1, Graphics2D g2d)
	{
		if (p1 != editor.data.tempPolygon)
		{
			g2d.setStroke(new BasicStroke(p1.thickness));
			g2d.setColor(p1.brushColor);
			g2d.drawPolygon(p1.getXPoints(), p1.getYPoints(), p1.vertices.size());
			g2d.setColor(p1.fillColor);
			g2d.fillPolygon(p1.getXPoints(), p1.getYPoints(), p1.vertices.size());
		}
		else
		{
			g2d.setStroke(new BasicStroke(p1.thickness + 3));
			g2d.setColor(Color.RED);
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
			g2d.setColor(Color.RED);
			g2d.drawLine(e.u.x, e.u.y, e.v.x, e.v.y);
		}
		g2d.setColor(brushColor);
	}

	private void drawNode(Node n, Graphics2D g2d)
	{
		int r = 15;
		if (n != editor.data.tempNode)
		{
			g2d.setColor(n.brushColor);
			g2d.fillOval(n.x - r, n.y - r, r * 2, r * 2);
		}
		else
		{
			r += 3;
			g2d.setColor(n.brushColor);
			g2d.fillOval(n.x - r, n.y - r, r * 2, r * 2);
			r -= 3;
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