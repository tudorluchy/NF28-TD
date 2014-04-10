package fr.utc.nf28.td05.dragndrop;

import java.awt.FlowLayout;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.TransferHandler;

/**
 * Example showing how to enable drop operations.
 * @author marciofk
 *
 */
public class ExampleDragAndDrop02 extends JFrame {

	private static final long serialVersionUID = 5696777234665603717L;
	private JList<String> list01;
	private JList<String> list02;

	public ExampleDragAndDrop02() {
		super("Example 02 - How to import data");

		// Creating a simple JList
		this.list01 = new JList<String>(new String[] { "Le Bistrot du Terroir",
				"Le Cafe Parisien", "Espace Jean Legendre" });
		// enabling drag
		this.list01.setDragEnabled(true);
		
		// Creating a simple JList
		this.list02 = new JList<String>(new DefaultListModel<String>());
		// enabling the drop operation
		list02.setTransferHandler(new TransferHandler() {

			private static final long serialVersionUID = 7453600879633731680L;

			@Override
			/**
			 * Used to verify if the data is compatible with.
			 */
			public boolean canImport(TransferSupport info) {
				// we only support Strings for the moment
				if (!info.isDataFlavorSupported(DataFlavor.stringFlavor)) {
					return false;
				}
				return true;
			}

			@Override
			/**
			 * Receive the package, unpack and use it.
			 */
			public boolean importData(TransferSupport info) {

				// Get the package
				Transferable t = info.getTransferable();

				// Opening the package 
				String data;
				try {
					data = (String) t.getTransferData(DataFlavor.stringFlavor);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}

				@SuppressWarnings("unchecked")
				
				// Using the data in the package. In fact, adding it to the target JList 
				JList<String> target = (JList<String>) info.getComponent();
				DefaultListModel<String> model = (DefaultListModel<String>) target
						.getModel();
				model.addElement(data);
				return true;
			}

		});

		setLayout(new FlowLayout());
		add(new JLabel("Drag elements from the list on the left to the list on the right"));
		add(new JScrollPane(list01));
		add(new JScrollPane(list02));
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);

	}

	public static void main(String[] args) {
		new ExampleDragAndDrop02();
	}

}
