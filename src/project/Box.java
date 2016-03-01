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
	final String UN = "Unselect Node";
	
	final String AE = "Add Edge";
	final String RE = "Remove Edge";
	final String UE = "Unselect Edge";


	final String AL = "Add Line";
	final String RL = "Remove Line";
	final String UL = "Unselect Line";

	final String AC = "Add Circle";
	final String MC = "Move Circle";
	final String RC = "Remove Circle";
	final String UC = "Unselect Circle";


	final String AR = "Add Rectangle";
	final String MR = "Move Rectangle";
	final String RR = "Remove Rectangle";
	final String UR = "Unselect Rectangle";

	final String AP = "Add Polygon";
	final String RP = "Remove Polygon";
	final String UP = "Unselect Polygon";

	
	JRadioButton an, mn, rn, un, ae, re, ue, al, rl, ul, ac, mc, rc, uc, ar, mr, rr, ur, ap, rp, up;

	Editor editor;

	public String mode = "";

	Box(Editor editor) {
		this.editor = editor;

		an = new JRadioButton(AN, true);
		mn = new JRadioButton(MN);
		rn = new JRadioButton(RN);
		un = new JRadioButton(UN);
		
		ae = new JRadioButton(AE);
		re = new JRadioButton(RE);
		ue = new JRadioButton(UE);

		al = new JRadioButton(AL);
		rl = new JRadioButton(RL);
		ul = new JRadioButton(UL);

		ac = new JRadioButton(AC);
		mc = new JRadioButton(MC);
		rc = new JRadioButton(RC);
		uc = new JRadioButton(UC);
		
		ar = new JRadioButton(AR);
		mr = new JRadioButton(MR);
		rr = new JRadioButton(RR);
		ur = new JRadioButton(UR);
		
		ap = new JRadioButton(AP);
		rp = new JRadioButton(RP);
		up = new JRadioButton(UP);
		
		an.addActionListener(this);
		mn.addActionListener(this);
		rn.addActionListener(this);
		un.addActionListener(this);
		
		ae.addActionListener(this);
		re.addActionListener(this);
		ue.addActionListener(this);
		
		al.addActionListener(this);
		rl.addActionListener(this);
		ul.addActionListener(this);
		
		ac.addActionListener(this);
		mc.addActionListener(this);
		rc.addActionListener(this);
		uc.addActionListener(this);
		
		ar.addActionListener(this);
		mr.addActionListener(this);
		rr.addActionListener(this);
		ur.addActionListener(this);
		
		ap.addActionListener(this);
		rp.addActionListener(this);
		up.addActionListener(this);
		
		ButtonGroup group = new ButtonGroup();
		
		group.add(an);
		group.add(mn);
		group.add(rn);
		group.add(un);
		
		group.add(ae);
		group.add(re);
		group.add(ue);

		group.add(al);
		group.add(rl);
		group.add(ul);
		
		group.add(ac);
		group.add(mc);
		group.add(rc);
		group.add(uc);
		
		group.add(ar);
		group.add(mr);
		group.add(rr);
		group.add(ur);

		group.add(ap);
		group.add(rp);
		group.add(up);
		
		setLayout(new GridLayout(0, 1));

	}

	public void actionPerformed(ActionEvent e) {
		mode = e.getActionCommand();
	}

}