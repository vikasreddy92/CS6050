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
	JMenuItem exportMenuItem;
	JMenuItem importMenuItem;
	static final String EXPORT = "Export to ps";
	static final String IMPORT = "Import to Editor";

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
		
		exportMenuItem = new JMenuItem(EXPORT);
		importMenuItem = new JMenuItem(IMPORT);
		
		exportMenuItem.addActionListener(this);
		importMenuItem.addActionListener(this);
		popup.add(exportMenuItem);
		popup.add(importMenuItem);
		
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
				String filePath = fileChooser.getSelectedFile().getAbsolutePath();
				if (!filePath.endsWith(".eps"))
					filePath = filePath + ".eps";
				FileIO fio = new FileIO(editor);
				fio.export(this.getText(), filePath);
			} else if (rVal == JFileChooser.CANCEL_OPTION) {
				//throw a alert?
			}
		} else if(action.equals(IMPORT)) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileFilter(new FileNameExtensionFilter("Encapsulated PostScript File Format", "eps"));
			int rVal = fileChooser.showOpenDialog(this);
			if(rVal == JFileChooser.APPROVE_OPTION) {
				String filePath = fileChooser.getSelectedFile().getAbsolutePath();
				FileIO fio = new FileIO(editor);
				System.out.println("Importing... " + filePath);
				fio.importToEditor(filePath);
				editor.refresh();
			}
		}
	}
}