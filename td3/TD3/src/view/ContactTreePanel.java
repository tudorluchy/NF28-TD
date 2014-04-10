package view;
import javax.swing.JTree;

import model.ContactTreeModel;


public class ContactTreePanel extends JTree {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor : set model tree
	 * @param model
	 */
	public ContactTreePanel(ContactTreeModel model) {
		this.setModel(model);
	}
}
