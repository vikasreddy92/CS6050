package project;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

class Box extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Editor editor;
	static final String AV = "Add Vertex";
	static final String RV = "Remove Vertex";
	static final String AE = "Add Edge";
	static final String RE = "Remove Edge";
	static final String MV = "Move Vertex";

	static String mode = "";

	Box(Editor editor) {
		this.editor = editor;

		JRadioButton av = new JRadioButton(AV);
		JRadioButton rv = new JRadioButton(RV);
		JRadioButton ae = new JRadioButton(AE);
		JRadioButton re = new JRadioButton(RE);
		JRadioButton mv = new JRadioButton(MV);

		av.addActionListener(this);
		ae.addActionListener(this);
		rv.addActionListener(this);
		re.addActionListener(this);
		mv.addActionListener(this);

		ButtonGroup group = new ButtonGroup();
		group.add(av);
		group.add(rv);
		group.add(ae);
		group.add(re);
		group.add(mv);

		setLayout(new GridLayout(0, 1));
		add(av);
		add(rv);
		add(ae);
		add(re);
		add(mv);
	}

	public void actionPerformed(ActionEvent e) {
		mode = e.getActionCommand();
	}

}
