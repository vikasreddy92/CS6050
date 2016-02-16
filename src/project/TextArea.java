package project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

public class TextArea extends JTextArea implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Editor editor;
	JPopupMenu popup = new JPopupMenu("Menu");
	JMenuItem menuItem;
	static final String EXPORT = "Export to ps";

	class PopUpTrigger extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			if (e.isPopupTrigger()) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		}
	}

	public TextArea(Editor editor) {
		super(0, 15);
		this.editor = editor;
		menuItem = new JMenuItem(EXPORT);
		menuItem.addActionListener(this);
		popup.add(menuItem);
		addMouseListener(new PopUpTrigger());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals(EXPORT)) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileFilter(new FileNameExtensionFilter("Encapsulated PostScript File Format", "eps"));
			int rVal = fileChooser.showSaveDialog(this);
			if (rVal == JFileChooser.APPROVE_OPTION) {
				String fileName = fileChooser.getSelectedFile().getAbsolutePath();
				if (!fileName.endsWith(".eps"))
					fileName = fileName + ".eps";
				Export.export(this.getText(), fileName);
			} else if (rVal == JFileChooser.CANCEL_OPTION) {

			}
		}
	}
}