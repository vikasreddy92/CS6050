package project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MenuBar extends JMenuBar implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String FILE = "File";
	private static final String IMPORT = "Import from EPS";
	private static final String EXPORT = "Export to EPS";
	private static final String CLEAR = "Clear everything";
	private static final String EXIT = "Exit";

	Editor editor;
	JMenu file;

	JMenuItem importMenuItem;
	JMenuItem exportMenuItem;
	JMenuItem clearMenuItem;
	JMenuItem exitMenuItem;

	public MenuBar(Editor editor)
	{
		super();

		this.editor = editor;
		file = new JMenu(FILE);

		importMenuItem = new JMenuItem(IMPORT);
		exportMenuItem = new JMenuItem(EXPORT);
		clearMenuItem = new JMenuItem(CLEAR);
		exitMenuItem = new JMenuItem(EXIT);

		file.add(importMenuItem);
		file.add(exportMenuItem);
		file.add(clearMenuItem);
		file.add(exitMenuItem);

		add(file);
		importMenuItem.addActionListener(this);
		exportMenuItem.addActionListener(this);
		clearMenuItem.addActionListener(this);
		exitMenuItem.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		String action = e.getActionCommand();
		System.out.println(action);
		if (action.equals(EXIT))
		{
			System.exit(0);
		}
		else if (action.equals(IMPORT))
		{
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
		else if (action.equals(EXPORT))
		{
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileFilter(new FileNameExtensionFilter("Encapsulated PostScript File Format", "eps"));
			int rVal = fileChooser.showSaveDialog(this);
			if (rVal == JFileChooser.APPROVE_OPTION) {
				String filePath = fileChooser.getSelectedFile().getAbsolutePath();
				if (!filePath.endsWith(".eps"))
					filePath = filePath + ".eps";
				FileIO fio = new FileIO(editor);
				fio.export(editor.window.text.getText(), filePath);
			} else if (rVal == JFileChooser.CANCEL_OPTION) {
				//throw an alert?
			}
		
		} else if (action.equals(CLEAR))
		{
			editor.data.clearAll();
			editor.refresh();
		}
	}

}
