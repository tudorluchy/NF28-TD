package fr.utc.nf28.td05.dragndrop;

import java.awt.FlowLayout;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.TransferHandler;

/**
 * A more realistic effective drag and drop example.
 * 
 * @author marciofk
 * 
 */
public class ExampleDragAndDrop03 extends JFrame {

	private static final long serialVersionUID = 5696777234665603717L;
	private JList<String> list01;
	private JList<String> list02;

	public ExampleDragAndDrop03() {
		super("Example 03 - A more realistic example");

		// Creating a simple default model
		DefaultListModel<String> model = new DefaultListModel<>();
		model.addElement("Le Bistrot du Terroir");
		model.addElement("Le Cafe Parisien");
		model.addElement("Espace Jean Legendre");

		// Creating a simple source JList
		this.list01 = new JList<String>(model);
		this.list01.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.list01.setDragEnabled(true);

		TransferHandler listHandler = new ListTransferHandler();

		list01.setTransferHandler(listHandler);

		this.list02 = new JList<String>(new DefaultListModel<String>());
		this.list02.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.list02.setDragEnabled(true);
		list02.setTransferHandler(listHandler);

		setLayout(new FlowLayout());
		add(new JLabel("Drag elements to the list (ctrl just copy)"));
		add(new JScrollPane(list01));
		add(new JScrollPane(list02));
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);

	}

	public static void main(String[] args) {
		new ExampleDragAndDrop03();
	}

	/**
	 * A TranserHandler that configures how to import and export JList data
	 * 
	 */
	class ListTransferHandler extends TransferHandler {

		private static final long serialVersionUID = 6556556184660251698L;

		@Override
		public int getSourceActions(JComponent c) {
			return COPY_OR_MOVE;
		}

		@Override
		protected Transferable createTransferable(JComponent c) {
			@SuppressWarnings("unchecked")
			JList<String> source = (JList<String>) c;
			return new StringSelection((String) source.getSelectedValue());
		}

		@Override
		/**
		 * Used to apply changes after the drop operation.
		 * Useful when the user wants to move something.
		 */
		protected void exportDone(JComponent c, Transferable data, int action) {

			if (action == MOVE) {
				@SuppressWarnings("unchecked")
				JList<String> source = (JList<String>) c;
				DefaultListModel<String> model = (DefaultListModel<String>) source
						.getModel();
				model.remove(source.getSelectedIndex());
			}
		}

		@Override
		public boolean canImport(TransferSupport info) {
			System.out.println("info.isDataFlavorSupported?"
					+ info.isDataFlavorSupported(DataFlavor.stringFlavor));
			// we only import Strings
			if (!info.isDataFlavorSupported(DataFlavor.stringFlavor)) {
				return false;
			}

			return true;
		}

		@Override
		public boolean importData(TransferSupport info) {
			System.out.println("Executing import data");

			Transferable t = info.getTransferable();

			String data;
			try {
				data = (String) t.getTransferData(DataFlavor.stringFlavor);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}

			@SuppressWarnings("unchecked")
			JList<String> target = (JList<String>) info.getComponent();
			DefaultListModel<String> model = (DefaultListModel<String>) target
					.getModel();
			model.addElement(data);
			return true;
		}

	}

}
