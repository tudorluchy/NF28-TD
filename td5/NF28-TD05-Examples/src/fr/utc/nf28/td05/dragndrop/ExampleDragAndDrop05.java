package fr.utc.nf28.td05.dragndrop;

import java.awt.FlowLayout;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.TransferHandler;

/**
 * Using different "flavors" of data.
 * 
 * @author marciofk
 * 
 */
public class ExampleDragAndDrop05 extends JFrame {

	private static final long serialVersionUID = 5696777234665603717L;
	private JList<Pizza> list01;
	private JList<Pizza> list02;

	public ExampleDragAndDrop05() {
		super("Example 05 - Using different flavors of data");
		DefaultListModel<Pizza> model = new DefaultListModel<>();
		model.addElement(new Pizza("Pepperoni", "Italia"));
		model.addElement(new Pizza("Taco Bell", "Mexico"));
		model.addElement(new Pizza("Lombinho", "Brazil"));

		// Creating a simple source JList
		this.list01 = new JList<Pizza>(model);
		this.list01.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.list01.setDragEnabled(true);

		TransferHandler listHandler = new ListTransferHandler();

		list01.setTransferHandler(listHandler);

		this.list02 = new JList<Pizza>(new DefaultListModel<Pizza>());
		this.list02.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.list02.setDragEnabled(true);
		list02.setTransferHandler(listHandler);

		setLayout(new FlowLayout());
		add(new JLabel("Drag elements to the list or any string input"));
		add(new JScrollPane(list01));
		add(new JScrollPane(list02));
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);

	}

	public static void main(String[] args) {
		new ExampleDragAndDrop05();
	}

	class ListTransferHandler extends TransferHandler {

		private static final long serialVersionUID = -3538143695204584619L;

		@Override
		public int getSourceActions(JComponent c) {
			return COPY;
		}

		@Override
		/**
		 * We export different "flavors" of pizza!
		 */
		protected Transferable createTransferable(JComponent c) {
			@SuppressWarnings("unchecked")
			JList<Pizza> source = (JList<Pizza>) c;
			return new PizzaTransferable(source.getSelectedValue());
		}

		@Override
		public boolean canImport(TransferSupport info) {
			// we only import Pizzas!
			if (!info.isDataFlavorSupported(PizzaTransferable.pizzaDataFlavor)) {
				return false;
			}
			return true;
		}

		@Override
		public boolean importData(TransferSupport info) {

			Transferable t = info.getTransferable();
			Pizza data;
			try {
				data = (Pizza) t
						.getTransferData(PizzaTransferable.pizzaDataFlavor);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}

			@SuppressWarnings("unchecked")
			JList<Pizza> target = (JList<Pizza>) info.getComponent();
			DefaultListModel<Pizza> model = (DefaultListModel<Pizza>) target
					.getModel();
			model.addElement(data);
			return true;
		}

	}

	static class PizzaTransferable implements Transferable {

		public static final DataFlavor pizzaDataFlavor = new DataFlavor(
				Pizza.class, "Pizza");

		public Pizza pizza;

		public PizzaTransferable(Pizza pizza) {
			this.pizza = pizza;
		}

		@Override
		/**
		 * We export pizzas and strings
		 */
		public DataFlavor[] getTransferDataFlavors() {
			DataFlavor[] result = { pizzaDataFlavor, DataFlavor.stringFlavor };
			return result;
		}

		@Override
		public boolean isDataFlavorSupported(DataFlavor flavor) {
			return Arrays.asList(getTransferDataFlavors()).contains(flavor);
		}

		@Override
		public Object getTransferData(DataFlavor flavor)
				throws UnsupportedFlavorException, IOException {

			if (flavor == pizzaDataFlavor) {
				return pizza;
			} else if (flavor == DataFlavor.stringFlavor) {
				return pizza.toJson();
			} else {
				return null;
			}

		}

	}

	class Pizza {
		private String name;
		private String origin;

		public Pizza(String name, String origin) {
			this.name = name;
			this.origin = origin;
		}

		public String getName() {
			return name;
		}

		public String getOrigin() {
			return origin;
		}

		public String toJson() {
			return "{" + "\"name\":\"" + name + "\"" + "," + "\"origin\":\""
					+ origin + "\"" + "}";
		}

		public String toString() {
			return name + " " + origin;
		}
	}

}
