package project;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

class Board extends JPanel implements MouseInputListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static int sX, sY, currX, currY;
	Editor editor;
	boolean dragging = false;

	Board(Editor editor) {
		this.editor = editor;
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	public void paint(Graphics g) {
		if (ToolBar.mode.equals(ToolBar.CIRCLE_BUTTON)) {
			for (Circle circle : editor.circles.circles) {
				int x = circle.center.x;
				int y = circle.center.y;
				int radius = circle.radius;
				int r = 5;
				g.fillOval(x - r, y - r, r * 2, r * 2);
				g.drawOval(x - radius, y - radius, radius * 2, radius * 2);
			}

			int r = 5;
			g.fillOval(sX - r, sY - r, r * 2, r * 2);
			if (dragging) {
				int rad = Math.abs(currX - sX);
				g.drawOval(sX - rad, sY - rad, rad * 2, rad * 2);
			}
		} else {
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
		}
	}

	public void mouseClicked(MouseEvent e) {
		System.out.println("Mouse Clicked: " + e.getX() + " " + e.getY());
	}

	public void mouseMoved(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		editor.window.message.setText(editor.data + " " + x + " " + y);
	}

	public void mouseExited(MouseEvent e) {
		editor.window.message.setText(editor.data.toString());
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseDragged(MouseEvent e) {
		dragging = true;
		if (ToolBar.mode.equals(ToolBar.CIRCLE_BUTTON)) {
			currX = e.getX();
			currY = e.getY();
			repaint();
		}
	}

	public void mousePressed(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		if (ToolBar.mode.equals(ToolBar.CIRCLE_BUTTON)) {
			sX = x;
			sY = y;
		} else {
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
			repaint();
			revalidate();
			editor.window.text.setText(editor.data.toText());
			editor.window.message.setText(editor.data + " " + x + " " + y);
		}
	}

	public void mouseReleased(MouseEvent e) {
		dragging = false;
		if (ToolBar.mode.equals(ToolBar.CIRCLE_BUTTON)) {
			int radius = Math.abs(sX - currX);
			editor.circles.add(sX, sY, radius);
			currX = e.getX();
			currY = e.getY();
			revalidate();
			editor.window.text.setText(editor.circles.toString());
		}
	}
}
