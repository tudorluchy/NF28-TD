package Model;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

public class ContactTreeModel extends DefaultTreeModel {

	private StringBuilder sb;
	
	public ContactTreeModel(TreeNode node) {
		super(node);
		sb = new StringBuilder();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Retourne le XML d'un arbre
	 * @return
	 */
    public String toXml() {
    	processTree((DefaultMutableTreeNode) getRoot());
        return sb.toString();
    }	


    /**
     * Viste les noeuds d'un arbre et construit le XML récursivement
     * @param node
     * @return
     */
    public void processTree(DefaultMutableTreeNode node) {
        Object tempObj = node.getUserObject();
        
        if (tempObj instanceof String) {
        	sb.append("<"+ tempObj +">\n");
            for (int i=0; i < node.getChildCount(); i++) {
	            DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) node.getChildAt(i);
	            processTree(childNode);
            }
            sb.append("</"+ tempObj +">\n");
        } else {
            Contact ctt = (Contact) tempObj;
            sb.append("<contact>\n");
            sb.append("<nom>"+ ctt.getNom() +"</nom>\n");
            sb.append("<mail>"+ ctt.getMail() +"</mail>\n");
            sb.append("<icone>"+ ctt.getIcone() +"</icone>\n");
            sb.append("</contact>\n");
        }
    }
}
