package fr.utc.nf28.td05.dragndrop;

import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Simple example showing the usage of the setDragEnabled method.
 * @author marciofk
 * @see http://docs.oracle.com/javase/tutorial/uiswing/dnd/index.html
 *
 */
public class ExampleDragAndDrop01 extends JFrame {

	private static final long serialVersionUID = 5696777234665603717L;
	
	private JList<String> list;
	private JTextArea area;

	public ExampleDragAndDrop01() {
		super("Example 01 - setDragEnabled usage");
		// Creating a simple JList
		this.list = new JList<String>(new String[] { "Le Bistrot du Terroir",
				"Le Cafe Parisien", "Espace Jean Legendre" });
		
		// enabling the drag operation
		this.list.setDragEnabled(true);
		
		// Creating a text area
		this.area = new JTextArea(3, 30);

		setLayout(new FlowLayout());
		add(new JLabel("Drag elements to the textarea"));
		add(new JScrollPane(list));
		add(new JScrollPane(area));
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);

	}

	public static void main(String[] args) {
		new ExampleDragAndDrop01();
	}

}
