package View;
import javax.swing.JTree;

import Model.ContactTreeModel;


public class ContactTreePanel extends JTree {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * @param model
	 */
	public ContactTreePanel(ContactTreeModel model) {
		this.setModel(model);
	}
	
}
