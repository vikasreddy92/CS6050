package project;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

class Board extends JPanel implements MouseInputListener {

	private static final long serialVersionUID = 1L;
	private static int sX = -1, sY = -1, currX, currY;
	private static boolean dragging = false;
	private int brushSize;

	Editor editor;
	Circle movingCircle;
	Rectangle movingRectangle;

	Board(Editor editor) {
		this.editor = editor;
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	public void paint(Graphics g) {
		int r = 5;
		for (Vertex v : editor.data.vertices) {
			if (v != editor.data.p)
				g.fillOval(v.x - r, v.y - r, r * 2, r * 2);
			else {
				r += 3;
				g.fillOval(v.x - r, v.y - r, r * 2, r * 2);
				r -= 3;
			}
		}
		
		for (Edge e : editor.data.edges) {
			if (e != editor.data.tempEdge) {
				g.drawLine(e.u.x, e.u.y, e.v.x, e.v.y);
			} else {
				g.setColor(Color.RED);
				g.drawLine(e.u.x, e.u.y, e.v.x, e.v.y);
				g.setColor(Color.BLACK);
			}
		}

		for (Circle c : editor.data.circles) {
			if (c != editor.data.tempCircle) {
				drawCircle(c.center.x, c.center.y, c.radius, g);
			} else {
				g.setColor(Color.RED);
				drawCircle(c.center.x, c.center.y, c.radius, g);
				g.setColor(Color.BLACK);
			}
		}

		Graphics2D g2d = (Graphics2D) g;
		for (Rectangle r1 : editor.data.rectangles) {
			g2d.setStroke(new BasicStroke(r1.thickness));
			if (r1 != editor.data.tempRectangle) {
				g2d.drawRect(r1.origin.x, r1.origin.y, r1.width, r1.height);
			} else {
				g2d.setColor(Color.RED);
				g2d.drawRect(r1.origin.x, r1.origin.y, r1.width, r1.height);
				g2d.setColor(Color.BLACK);
			}
		}

		if (editor.window.box.mode.equals(editor.window.box.AC)) {
			if (dragging) {
				int radius = Math.abs(currX - sX);
				drawCircle(sX, sY, radius, g);
			}
		} else if (editor.window.box.mode.equals(editor.window.box.MC)) {
			if (dragging && movingCircle != null)
				drawCircle(sX, sY, movingCircle.radius, g);
		} else if (editor.window.box.mode.equals(editor.window.box.AR)) {
			drawRect(sX, sY, currX, currY, g);
		} else if (editor.window.box.mode.equals(editor.window.box.AL)) {
			if (dragging)
				g.drawLine(sX, sY, currX, currY);
		} else if (editor.window.box.mode.equals(editor.window.box.MR)) {
			if (dragging)
				g.drawRect(currX, currY, movingRectangle.width, movingRectangle.height);
		}
	}

	public void mouseClicked(MouseEvent e) {}

	public void mouseMoved(MouseEvent e) {
		editor.window.message.setText(editor.data + "   " + "X: " + e.getX() + ", Y: " + e.getY());
	}

	public void mouseExited(MouseEvent e) {
		editor.window.message.setText(editor.data.toString());
		editor.refresh();
	}

	public void mouseEntered(MouseEvent e) {
		editor.window.message.setText(editor.data.toString());
		editor.refresh();
	}

	public void mouseDragged(MouseEvent e) {
		dragging = true;
		if (editor.window.box.mode.equals(editor.window.box.AC)) {
			currX = e.getX();
			currY = e.getY();
		} else if (editor.window.box.mode.equals(editor.window.box.MC)) {
			if (movingCircle != null) {
				sX = movingCircle.center.x - (currX - e.getX());
				sY = movingCircle.center.y - (currY - e.getY());
			}
		} else if (editor.window.box.mode.equals(editor.window.box.AR)) {
			currX = e.getX();
			currY = e.getY();
		} else if (editor.window.box.mode.equals(editor.window.box.AL)) {
			currX = e.getX();
			currY = e.getY();
		} else if (editor.window.box.mode.equals(editor.window.box.MR)) {
			currX = e.getX();
			currY = e.getY();
		}
		editor.window.message.setText(editor.data + "   " + "X: " + e.getX() + ", Y: " + e.getY());
		editor.refresh();
	}

	public void mousePressed(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();

		if (editor.window.box.mode.equals(editor.window.box.AV))
			editor.data.add(x, y);
		else if (editor.window.box.mode.equals(editor.window.box.MV))
			editor.data.move(x, y);
		else if (editor.window.box.mode.equals(editor.window.box.RV))
			editor.data.remove(x, y);
		else if (editor.window.box.mode.equals(editor.window.box.AE))
			editor.data.mark(x, y);
		else if (editor.window.box.mode.equals(editor.window.box.RE))
			editor.data.mark(x, y);
		else if (editor.window.box.mode.equals(editor.window.box.AC)) {
			sX = x;
			sY = y;
		} else if (editor.window.box.mode.equals(editor.window.box.MC)) {
			movingCircle = editor.data.moveCircle(x, y);
			if (movingCircle != null) {
				sX = movingCircle.center.x;
				sY = movingCircle.center.y;
				currX = x;
				currY = y;
			}
		} else if (editor.window.box.mode.equals(editor.window.box.RC))
			editor.data.removeCircle(x, y);
		else if (editor.window.box.mode.equals(editor.window.box.AR)) {
			currX = sX = x;
			currY = sY = y;
		} else if (editor.window.box.mode.equals(editor.window.box.MR)) {
			movingRectangle = editor.data.moveRectangle(x, y);
			if (movingRectangle != null) {
				currX = movingRectangle.origin.x;
				currY = movingRectangle.origin.y;
			}
		} else if (editor.window.box.mode.equals(editor.window.box.RR)) {
			editor.data.removeRectangle(x, y);
		} else if (editor.window.box.mode.equals(editor.window.box.AL)) {
			sX = x;
			sY = y;
		} else if (editor.window.box.mode.equals(editor.window.box.RL)) {
			editor.data.removeLine(x, y);
		}
		editor.refresh();
	}

	public void mouseReleased(MouseEvent e) {
		dragging = false;
		if (editor.window.box.mode.equals(editor.window.box.AC)) {
			int radius = Math.abs(sX - currX);
			editor.data.add(new Vertex(sX, sY), radius);
		} else if (editor.window.box.mode.equals(editor.window.box.MC)) {
			if (movingCircle != null) {
				editor.data.add(new Vertex(sX, sY), movingCircle.radius);
				editor.data.circles.remove(movingCircle);
			}
		} else if (editor.window.box.mode.equals(editor.window.box.AR)) {
			editor.data.add(getReactangle(sX, sY, currX, currY, brushSize));
		} else if (editor.window.box.mode.equals(editor.window.box.MR)) {
			if (movingRectangle != null) {
				editor.data.add(new Rectangle(new Vertex(currX, currY), movingRectangle.width, movingRectangle.height, brushSize));
				editor.data.rectangles.remove(movingRectangle);
			}
		} else if (editor.window.box.mode.equals(editor.window.box.AL)) {
			editor.data.add(new Vertex(sX, sY), new Vertex(currX, currY));
		}
		currX = sX = e.getX();
		currY = sY = e.getY();
		editor.refresh();
	}
	
	public void drawCircle(int x, int y, int radius, Graphics g) {
		int r = 5;
		g.fillOval(x - r, y - r, r * 2, r * 2);
		g.drawOval(x - radius, y - radius, radius * 2, radius * 2);
	}

	public void drawRect(int sX2, int sY2, int currX2, int currY2, Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(brushSize));
		Rectangle rect = getReactangle(sX2, sY2, currX2, currY2, brushSize);
		g2d.drawRect(rect.origin.x, rect.origin.y, rect.width, rect.height);
	}
	
	public Rectangle getReactangle(int sX2, int sY2, int currX2, int currY2, int thickness) {
		int width = -sX2 + currX2;
		int height = -sY2 + currY2;
		if (width < 0 && height > 0)
			return new Rectangle(currX2, sY2, -width, height, brushSize);
		else if (width > 0 && height < 0)
			return new Rectangle(sX2, currY2, width, -height, brushSize);
		else if (width < 0 && height < 0)
			return new Rectangle(currX2, currY2, -width, -height, brushSize);
		else
			return new Rectangle(sX2, sY2, width, height, brushSize);
	}
	
	public int getBrushSize() {
		return brushSize;
	}

	public void setBrushSize(int brushSize) {
		this.brushSize = brushSize;
	}
}