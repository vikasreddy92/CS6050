package project;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class Box extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	public final String AV = "Add Vertex";
	final String MV = "Move Vertex";
	final String RV = "Remove Vertex";
	final String AE = "Add Edge";
	final String RE = "Remove Edge";

	final String AL = "Add Line";
	final String RL = "Remove Line";

	final String AC = "Add Circle";
	final String MC = "Move Circle";
	final String RC = "Remove Circle";

	final String AR = "Add Rectangle";
	final String MR = "Move Rectangle";
	final String RR = "Remove Rectangle";

	JRadioButton av, mv, rv, ae, re, al, rl, ac, mc, rc, ar, mr, rr;

	Editor editor;

	public String mode = "";

	Box(Editor editor) {
		this.editor = editor;

		av = new JRadioButton(AV, true);
		mv = new JRadioButton(MV);
		rv = new JRadioButton(RV);
		ae = new JRadioButton(AE);
		re = new JRadioButton(RE);

		al = new JRadioButton(AL);
		rl = new JRadioButton(RL);

		ac = new JRadioButton(AC);
		mc = new JRadioButton(MC);
		rc = new JRadioButton(RC);
		ar = new JRadioButton(AR);
		mr = new JRadioButton(MR);
		rr = new JRadioButton(RR);

		av.addActionListener(this);
		mv.addActionListener(this);
		rv.addActionListener(this);
		ae.addActionListener(this);
		re.addActionListener(this);

		al.addActionListener(this);
		rl.addActionListener(this);

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

		group.add(al);
		group.add(rl);

		group.add(ac);
		group.add(mc);
		group.add(rc);
		group.add(ar);
		group.add(mr);
		group.add(rr);

		setLayout(new GridLayout(0, 1));

	}

	public void actionPerformed(ActionEvent e) {
		mode = e.getActionCommand();
	}

}