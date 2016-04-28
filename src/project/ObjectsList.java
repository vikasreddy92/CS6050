package project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

public class ObjectsList extends JList<Object>implements ActionListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Editor editor;
	static DefaultListModel<Object> model = new DefaultListModel<>();

	public ObjectsList(Editor editor)
	{
		super(model);
		super.setLayoutOrientation(JList.VERTICAL_WRAP);
		super.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.editor = editor;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		String action = e.getActionCommand();
		System.out.println(action);
	}
}
