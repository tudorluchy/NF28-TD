package model;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.TransferHandler;

public class FileTransfertHandler extends TransferHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Le modèle
	 */
	private ApplicationModel appModel = ApplicationModel.getInstance();
	
	@Override
	public boolean canImport(TransferSupport support) {
		// can be dropped
		if (!support.isDrop()) {
			return false;
		}
		
		// drop file and string (node contact into textarea)
		if (!support.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
			if (!support.isDataFlavorSupported(DataFlavor.stringFlavor)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean importData(TransferSupport support) {
		Transferable data = support.getTransferable();
		// file
		if (data.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
			try {
				@SuppressWarnings("rawtypes")
				List files = (List) data.getTransferData(DataFlavor.javaFileListFlavor);
				File xmlFile = (File) files.get(0);
				appModel.loadModelFromFileDrop(xmlFile.getAbsolutePath());
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		// string
		} else if (data.isDataFlavorSupported(DataFlavor.stringFlavor)) {
			String text = null;
			try {
				text = (String) data.getTransferData(DataFlavor.stringFlavor);
			} catch (UnsupportedFlavorException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			JTextArea jtextArea = (JTextArea) support.getComponent();
			JTextArea.DropLocation dl = (JTextArea.DropLocation) support.getDropLocation();
			int index = dl.getIndex();
	        jtextArea.insert(text, index);
		} else {
			return false;
		}
		return true;
	}
}