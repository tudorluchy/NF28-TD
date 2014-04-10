package model;

import java.awt.datatransfer.Transferable;

import javax.swing.JComponent;
import javax.swing.JTree;
import javax.swing.TransferHandler;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

public class NodeTransfertHandler extends TransferHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Le modèle
	 */
	private ApplicationModel appModel = ApplicationModel.getInstance();
	
	@Override
	public int getSourceActions(JComponent c) {
		return MOVE;
	}

	@Override
	protected Transferable createTransferable(JComponent c) {
		JTree tree = (JTree) c;
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		return new NodeTransferable(node);
	}

	@Override
	public boolean canImport(TransferSupport info) {
//		System.out.println("info.isDataFlavorSupported?" + info.isDataFlavorSupported(NodeTransferable.contactDataFlavor));
		// can be dropped
		if (!info.isDrop()) {
			return false;
		}
		
		// seulement des noeuds
		if (!info.isDataFlavorSupported(NodeTransferable.contactDataFlavor)) {
			return false;
		}
		
		return true;
	}

	@Override
	public boolean importData(TransferSupport info) {
//		System.out.println("Executing import data");
		Transferable t = info.getTransferable();
		// si c'est pas un noeud que je transfère = !ok
		if (!t.isDataFlavorSupported(NodeTransferable.contactDataFlavor)) {
			return false;
		}
		DefaultMutableTreeNode dmt = null;
		try {
			// node transf�r�
			dmt = (DefaultMutableTreeNode) t.getTransferData(NodeTransferable.contactDataFlavor);
			// drop location
			JTree.DropLocation dl = (JTree.DropLocation) info.getDropLocation();
			TreePath tp = dl.getPath();
			// ce n'est pas une destination dans le "tree"
			if (tp == null) {
				return false;
			}
			
			// parent destination
			DefaultMutableTreeNode parent = (DefaultMutableTreeNode) tp.getLastPathComponent();
			
			// destination pas un Contact !
			if (parent.getUserObject() instanceof Contact) {
				return false;
			}
			
//			System.out.println("Source : " + dmt.toString());
//			System.out.println("Destination : " + parent.toString());
			// dmt => source, contacts
			// parent => destination, collegues
			if (dmt.isNodeDescendant(parent)) {
				return false;
			}
			
			JTree tree = (JTree) info.getComponent();
			DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
			parent.add(dmt);
			// update model
			model.reload();
			tree.expandPath(tp);
			// update xml view
			appModel.updateXmlAfterContactDrop();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return false;
	}
}
