package project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class Box extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	public final String AV = "Add Vertex";
	final String MV = "Move Vertex";
	final String RV = "Remove Vertex";

	public final String AN = "Add Node";
	final String SN = "Select Node";
	final String MN = "Move Node";
	final String RN = "Remove Node";
	final String UN = "Unselect Node";
	
	final String AE = "Add Edge";
	final String SE = "Select Edge";
	final String RE = "Remove Edge";
	final String UE = "Unselect Edge";


	final String AL = "Add Line";
	final String SL = "Select Line";
	final String RL = "Remove Line";
	final String UL = "Unselect Line";

	final String AC = "Add Circle";
	final String SC = "Select Circle";
	final String MC = "Move Circle";
	final String RC = "Remove Circle";
	final String UC = "Unselect Circle";


	final String AR = "Add Rectangle";
	final String SR = "Select Rectangle";
	final String MR = "Move Rectangle";
	final String RR = "Remove Rectangle";
	final String UR = "Unselect Rectangle";

	final String AP = "Add Polygon";
	final String SP = "Select Polygon";
	final String RP = "Remove Polygon";
	final String UP = "Unselect Polygon";

	
	final String TEXT = "Text";
	
	JRadioButton an, sn, mn, rn, un, ae, se, re, ue, al, sl, rl, ul, ac, sc, mc, rc, uc, ar, sr, mr, rr, ur, ap, sp, rp, up;

	Editor editor;

	public String mode = "";

	Box(Editor editor) {
		this.editor = editor;

		an = new JRadioButton(AN, true);
		sn = new JRadioButton(SN);
		mn = new JRadioButton(MN);
		rn = new JRadioButton(RN);
		un = new JRadioButton(UN);
		
		ae = new JRadioButton(AE);
		se = new JRadioButton(SE);
		re = new JRadioButton(RE);
		ue = new JRadioButton(UE);

		al = new JRadioButton(AL);
		sl = new JRadioButton(SL);
		rl = new JRadioButton(RL);
		ul = new JRadioButton(UL);

		ac = new JRadioButton(AC);
		sc = new JRadioButton(SC);
		mc = new JRadioButton(MC);
		rc = new JRadioButton(RC);
		uc = new JRadioButton(UC);
		
		ar = new JRadioButton(AR);
		sr = new JRadioButton(SR);
		mr = new JRadioButton(MR);
		rr = new JRadioButton(RR);
		ur = new JRadioButton(UR);
		
		ap = new JRadioButton(AP);
		sp = new JRadioButton(SP);
		rp = new JRadioButton(RP);
		up = new JRadioButton(UP);
	
		
		an.addActionListener(this);
		sn.addActionListener(this);
		mn.addActionListener(this);
		rn.addActionListener(this);
		un.addActionListener(this);
		
		ae.addActionListener(this);
		se.addActionListener(this);
		re.addActionListener(this);
		ue.addActionListener(this);
		
		al.addActionListener(this);
		sl.addActionListener(this);
		rl.addActionListener(this);
		ul.addActionListener(this);
		
		ac.addActionListener(this);
		sc.addActionListener(this);
		mc.addActionListener(this);
		rc.addActionListener(this);
		uc.addActionListener(this);
		
		ar.addActionListener(this);
		sr.addActionListener(this);
		mr.addActionListener(this);
		rr.addActionListener(this);
		ur.addActionListener(this);
		
		ap.addActionListener(this);
		sp.addActionListener(this);
		rp.addActionListener(this);
		up.addActionListener(this);

		ButtonGroup group = new ButtonGroup();
		
		group.add(an);
		group.add(sn);
		group.add(mn);
		group.add(rn);
		group.add(un);
		
		group.add(ae);
		group.add(se);
		group.add(re);
		group.add(ue);

		group.add(al);
		group.add(sl);
		group.add(rl);
		group.add(ul);
		
		group.add(ac);
		group.add(sc);
		group.add(mc);
		group.add(rc);
		group.add(uc);
		
		group.add(ar);
		group.add(sr);
		group.add(mr);
		group.add(rr);
		group.add(ur);

		group.add(ap);
		group.add(sp);
		group.add(rp);
		group.add(up);
		
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
	}

	public void actionPerformed(ActionEvent e) {
		mode = e.getActionCommand();
		System.out.println("Box.java: " + mode);
	}

}