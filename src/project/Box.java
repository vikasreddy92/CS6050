package project;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

class Box extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	static final String AV = "Add Vertex";
	static final String RV = "Remove Vertex";
	static final String AE = "Add Edge";
	static final String RE = "Remove Edge";
	static final String MV = "Move Vertex";
	static final String AC = "Add Circle";
	static final String RC = "Remove Circle";
	static final String MC = "Move Circle";
	static final String AR = "Add Rectangle";
	static final String MR = "Move Rectangle";
	static final String RR = "Remove Rectangle";
	Editor editor;

	static String mode = AV;

	Box(Editor editor) {
		this.editor = editor;

		JRadioButton av = new JRadioButton(AV, true);
		JRadioButton mv = new JRadioButton(MV);
		JRadioButton rv = new JRadioButton(RV);
		JRadioButton ae = new JRadioButton(AE);
		JRadioButton re = new JRadioButton(RE);
		JRadioButton ac = new JRadioButton(AC);
		JRadioButton mc = new JRadioButton(MC);
		JRadioButton rc = new JRadioButton(RC);
		JRadioButton ar = new JRadioButton(AR);
		JRadioButton mr = new JRadioButton(MR);
		JRadioButton rr = new JRadioButton(RR);

		av.addActionListener(this);
		mv.addActionListener(this);
		rv.addActionListener(this);
		ae.addActionListener(this);
		re.addActionListener(this);
		ac.addActionListener(this);
		mc.addActionListener(this);
		rc.addActionListener(this);
		ar.addActionListener(this);
		mr.addActionListener(this);
		rr.addActionListener(this);

		ButtonGroup group = new ButtonGroup();
		group.add(av);
		group.add(mv);
		group.add(rv);
		group.add(ae);
		group.add(re);
		group.add(ac);
		group.add(mc);
		group.add(rc);
		group.add(ar);
		group.add(mr);
		group.add(rr);

		setLayout(new GridLayout(0, 2));
		add(av);
		add(mv);
		add(rv);
		add(ae);
		add(re);
		add(ac);
		add(mc);
		add(rc);
		add(ar);
		add(mr);
		add(rr);
	}

	public void actionPerformed(ActionEvent e) {
		mode = e.getActionCommand();
		System.out.println("Box.java: " + mode);
	}
}