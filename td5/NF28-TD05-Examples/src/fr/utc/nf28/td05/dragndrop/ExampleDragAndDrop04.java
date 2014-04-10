package fr.utc.nf28.td05.dragndrop;

import java.awt.FlowLayout;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.TransferHandler;

/**
 * Example on how drop a list of files in a jlabel (must be an image to work :)
 * )
 * 
 * @author marciofk
 * 
 */
public class ExampleDragAndDrop04 extends JFrame {

	private static final long serialVersionUID = 5696777234665603717L;
	private JLabel label;

	public ExampleDragAndDrop04() {
		super("Example 04 - Dropping a file to the component");
		label = new JLabel("Drop a file here (must be an image)");
		label.setTransferHandler(new TransferHandler() {

			private static final long serialVersionUID = -7157498391755560994L;

			@Override
			public boolean canImport(TransferSupport support) {
				if (!support
						.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
					return false;
				}
				return true;
			}

			@Override
			public boolean importData(TransferSupport support) {

				Transferable data = support.getTransferable();
				try {
					@SuppressWarnings("rawtypes")
					List files = (List) data
							.getTransferData(DataFlavor.javaFileListFlavor);

					File img = (File) files.get(0);

					label.setIcon(new ImageIcon(img.getAbsolutePath()));
					pack();

				} catch (Exception e) {
					throw new RuntimeException(e);
				}
				return true;

			}

		});
		add(label);

		setLayout(new FlowLayout());
		setSize(300, 300);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);

	}

	public static void main(String[] args) {
		new ExampleDragAndDrop04();
	}

}
