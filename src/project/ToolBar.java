package project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JToolBar;

public class ToolBar extends JToolBar implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Editor editor;
	static final String CIRCLE_BUTTON = "Circle";	
	static String mode = "";
	
	public ToolBar(Editor editor) {
//		super();
		this.editor = editor;
		JButton circleBtn = new JButton(CIRCLE_BUTTON); 
		circleBtn.addActionListener(this);
		add(circleBtn);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		mode = e.getActionCommand();
		System.out.println("Toolbar clicked: " + mode);
		editor.window.board.repaint();
	}
}
