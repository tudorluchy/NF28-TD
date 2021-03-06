package view;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Console;


public class ImageView extends JFrame implements PropertyChangeListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel pan;
	private JLabel lab;
	private String name;
	
	/**
	 * Constructor
	 * @param string
	 */
	public ImageView(String name) {
		super(name);
		this.name = name;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setMinimumSize(new Dimension(300, 300));
		pan = new JPanel();
		lab = new JLabel() ;
		this.getContentPane().add(pan);
		this.setVisible(true);
		/**
		 * Référence vers le modèle
		 */
		Console.getInstance().addPropertyChangeListener(this);
	}
	
	/**
	 * Receive property change and display new image
	 */
	@Override
	public void propertyChange(PropertyChangeEvent pce) {
		if (pce.getPropertyName().equals(Console.PROPERTY_UPDATE_VIEW_1) && name.equals("ImageView1")) {
			pan.removeAll();
			String image = (String) pce.getNewValue();
			lab.setIcon(new ImageIcon(image));
			pan.add(lab);
			revalidate();
			repaint();
		} else if (pce.getPropertyName().equals(Console.PROPERTY_UPDATE_VIEW_2) && name.equals("ImageView2")) {
			pan.removeAll();
			String image = (String) pce.getNewValue();
			lab.setIcon(new ImageIcon(image));
			pan.add(lab);
			revalidate();
			repaint();
		}
	}
}
