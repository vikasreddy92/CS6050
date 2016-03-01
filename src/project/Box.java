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

	public final String AN = "Add Node";
	final String MN = "Move Node";
	final String RN = "Remove Node";
	
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

	final String AP = "Add Polygon";
	final String RP = "Remove Polygon";
	
	JRadioButton an, mn, rn, ae, re, al, rl, ac, mc, rc, ar, mr, rr, ap, rp;

	Editor editor;

	public String mode = "";

	Box(Editor editor) {
		this.editor = editor;

		an = new JRadioButton(AN, true);
		mn = new JRadioButton(MN);
		rn = new JRadioButton(RN);
		
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

		ap = new JRadioButton(AP);
		rp = new JRadioButton(RP);
		
		an.addActionListener(this);
		mn.addActionListener(this);
		rn.addActionListener(this);
		
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

		ap.addActionListener(this);
		rp.addActionListener(this);
		
		ButtonGroup group = new ButtonGroup();
		
		group.add(an);
		group.add(mn);
		group.add(rn);
		
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

		group.add(ap);
		group.add(rp);
		
		setLayout(new GridLayout(0, 1));

	}

	public void actionPerformed(ActionEvent e) {
		mode = e.getActionCommand();
	}

}