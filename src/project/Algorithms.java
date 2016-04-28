package project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

public class Algorithms extends JPanel implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Editor editor;
	final String CH = "Convex Hull";
	final String VD = "Voronoi Diagram";
	JToggleButton ch;
	JToggleButton vd;

	public String mode = "";

	public Algorithms(Editor editor)
	{
		this.editor = editor;

		ch = new JToggleButton(CH, false);
		vd = new JToggleButton(VD, false);

		ch.addActionListener(this);
		vd.addActionListener(this);

		this.add(ch);
		this.add(vd);

		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		mode = e.getActionCommand();
		if (mode.equals(CH))
		{
			if (ch.isSelected())
			{
				editor.data.createConvexHull();
				editor.window.board.convexHull = true;
			}
			else
			{
				editor.window.board.convexHull = false;
			}
		}
		else if (mode.equals(VD))
		{
			if (vd.isSelected())
			{
				editor.data.createVoronoiDiagram();
				editor.window.board.voronoiDiagram = true;
			}
			else
			{
				editor.window.board.voronoiDiagram = false;
			}
		}
		editor.refresh();
	}
}
