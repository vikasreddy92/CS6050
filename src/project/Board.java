package project;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

class Board extends JPanel implements MouseInputListener {

	private static final long serialVersionUID = 1L;
	static int sX = -1, sY = -1, currX, currY;
	static boolean dragging = false;
	Editor editor;
	Circle movingCircle;

	Board(Editor editor) {
		this.editor = editor;
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	public void paint(Graphics g) {
		int r = 5;
		for (Vertex v : editor.data.vertices)
			if (v != editor.data.p)
				g.fillOval(v.x - r, v.y - r, r * 2, r * 2);
			else {
				r += 3;
				g.fillOval(v.x - r, v.y - r, r * 2, r * 2);
				r -= 3;
			}
		for (Edge e : editor.data.edges)
			g.drawLine(e.u.x, e.u.y, e.v.x, e.v.y);
		for (Circle c : editor.data.circles) {
			drawCircle(c.center.x, c.center.y, c.radius, g);
		}
		for (Rectangle rect : editor.data.rectangles) {
			g.drawRect(rect.origin.x, rect.origin.y, rect.width, rect.height);
		}

		if (Box.mode.equals(Box.AC)) {
			if (dragging) {
				int radius = Math.abs(currX - sX);
				drawCircle(sX, sY, radius, g);
			}
		} else if (Box.mode.equals(Box.MC)) {
			if (dragging && movingCircle != null)
				drawCircle(sX, sY, movingCircle.radius, g);
		} else if (Box.mode.equals(Box.AR)) {
			if (dragging) {
				drawRect(sX, sY, currX, currY, g);
			}
		}
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent e) {
		editor.window.message.setText(editor.data + " " + e.getX() + " " + e.getY());
	}

	public void mouseExited(MouseEvent e) {
		editor.window.message.setText(editor.data.toString());
	}

	public void mouseEntered(MouseEvent e) {
		editor.window.message.setText(editor.data.toString());
	}

	public void mouseDragged(MouseEvent e) {
		dragging = true;
		if (Box.mode.equals(Box.AC)) {
			currX = e.getX();
			currY = e.getY();
		} else if (Box.mode.equals(Box.MC)) {
			if (movingCircle != null) {
				sX = movingCircle.center.x - (currX - e.getX());
				sY = movingCircle.center.y - (currY - e.getY());
			}
		} else if (Box.mode.equals(Box.AR)) {
			currX = e.getX();
			currY = e.getY();
		}
		editor.refresh();
	}

	public void mousePressed(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();

		if (Box.mode.equals(Box.AV))
			editor.data.add(x, y);
		else if (Box.mode.equals(Box.RV))
			editor.data.remove(x, y);
		else if (Box.mode.equals(Box.AE))
			editor.data.mark(x, y);
		else if (Box.mode.equals(Box.RE))
			editor.data.mark(x, y);
		else if (Box.mode.equals(Box.MV))
			editor.data.move(x, y);
		else if (Box.mode.equals(Box.AC)) {
			sX = x;
			sY = y;
		} else if (Box.mode.equals(Box.MC)) {
			movingCircle = editor.data.moveCircle(x, y);
			if (movingCircle != null) {
				sX = movingCircle.center.x;
				sY = movingCircle.center.y;
				currX = x;
				currY = y;
			}
		} else if (Box.mode.equals(Box.RC)) {
			editor.data.removeCircle(x, y);
		} else if (Box.mode.equals(Box.AR)) {
			sX = x;
			sY = y;
		}
		editor.refresh();
	}

	public void mouseReleased(MouseEvent e) {
		dragging = false;
		if (Box.mode.equals(Box.AC)) {
			int radius = Math.abs(sX - currX);
			editor.data.add(new Vertex(sX, sY), radius);
			editor.refresh();
		} else if (Box.mode.equals(Box.MC)) {
			if (movingCircle != null) {
				editor.data.add(new Vertex(sX, sY), movingCircle.radius);
				editor.data.circles.remove(movingCircle);
				editor.refresh();
			}
		} else if (Box.mode.equals(Box.AR)) {
			editor.data.add(getReactangle(sX, sY, currX, currY));
		}
	}

	public void drawCircle(int x, int y, int radius, Graphics g) {
		int r = 5;
		g.fillOval(x - r, y - r, r * 2, r * 2);
		g.drawOval(x - radius, y - radius, radius * 2, radius * 2);
	}

	public Rectangle getReactangle(int sX2, int sY2, int currX2, int currY2) {
		int width = -sX2 + currX2;
		int height = -sY2 + currY2;
		if(width < 0 && height > 0)
			return new Rectangle(currX2, sY2, -width, height);
		else if(width > 0 && height < 0)
			return new Rectangle(sX2, currY2, width, -height);
		else if(width < 0 && height < 0) 
			return new Rectangle(currX2, currY2, -width, -height);
		else
			return new Rectangle(sX2, sY2, width, height);
	}
	public void drawRect(int sX2, int sY2, int currX2, int currY2, Graphics g) {
		Rectangle rect = getReactangle(sX2, sY2, currX2, currY2);
		g.drawRect(rect.origin.x, rect.origin.y, rect.width, rect.height);
	}
}