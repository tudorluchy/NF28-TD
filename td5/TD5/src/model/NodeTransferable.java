package model;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.tree.DefaultMutableTreeNode;

public class NodeTransferable implements Transferable {
	
	protected static final DataFlavor contactDataFlavor = new DataFlavor(
			DataFlavor.javaJVMLocalObjectMimeType, "ContactNode");
	/**
	 * Le noeud transferable
	 */
	public DefaultMutableTreeNode node;
	
	/**
	 * Constructor
	 */
	public NodeTransferable(DefaultMutableTreeNode node) {
		super();
		this.node = node;
	}
	
	/**
	 * Retourner le contenu adequat
	 */
	@Override
	public Object getTransferData(DataFlavor flavor)
			throws UnsupportedFlavorException, IOException {
		
		if (flavor == contactDataFlavor) {
			return node;
		} else if (flavor == DataFlavor.stringFlavor) {
			return new ContactTreeModel(node).toXml();
		} else {
			return null;
		}


	}

	@Override
	public DataFlavor[] getTransferDataFlavors() {
		// node et xml
		DataFlavor[] result = { contactDataFlavor, DataFlavor.stringFlavor };
		return result;
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return Arrays.asList(getTransferDataFlavors()).contains(flavor);
	}

}
