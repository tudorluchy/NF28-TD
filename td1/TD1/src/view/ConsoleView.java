package view;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.Console;


public class ConsoleView extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel pan = new JPanel();
	private JTextField text = new JTextField();
	private JButton start = new JButton();
	private JButton stop = new JButton();
	private JSlider slider = new JSlider(0,10);
	/**
	 * Référence modèle
	 */
	private Console console = Console.getInstance();
	
		
	/**
	 * Constructor
	 * @param string
	 */
	public ConsoleView(String name) {
		super(name);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(300, 0);
		this.setMinimumSize(new Dimension(280, 200));
		GridBagLayout gbl_pan = new GridBagLayout();
		gbl_pan.columnWidths = new int[]{110, 110};
		pan.setLayout(gbl_pan);
		
		// default
		text.setText("1000");
		/**
		 * Accepter seulement des chiffres
		 */
		text.addKeyListener(new java.awt.event.KeyAdapter() {
	    	public void keyTyped(KeyEvent evt) {
		    	char c = evt.getKeyChar();		    	
		    	//Accept only numeric key
		    	if (c >= '0' && c <= '9') {
		    	} else {
		    		evt.consume();
		    	}
	    	}
    	});
		
		start.setText("Start");
		/**
		 * Click on start boutton
		 */
		start.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					System.out.println("Start");
					try {
						String s = text.getText();
						if (!s.isEmpty()) {
							Integer i = Integer.parseInt(s);
							if (i > 0) {
							console.setInterval(i);
							console.startTimer();
							start.setEnabled(false);
							stop.setEnabled(true);
							} else {
								JOptionPane.showMessageDialog(null,"Veuillez entrer une valeur strictement superieur à 0", "Alert", JOptionPane.INFORMATION_MESSAGE);
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}			
				}
				});
		
		stop.setText("Stop");
        stop.setEnabled(false);
        /**
         * Click on stop boutton
         */
        stop.addActionListener(new ActionListener() {
        	
        	@Override
        	public void actionPerformed(ActionEvent arg0) {
        		System.out.println("Stop");
        		console.stopTimer();
        		start.setEnabled(true);
        		stop.setEnabled(false);
        	}
        });
		
        Font sliderFont = new Font("Serif", Font.ITALIC, 15);
		slider.setMajorTickSpacing(10);
		slider.setMinorTickSpacing(1);
	    slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));
		slider.setFont(sliderFont);
		// default
		slider.setValue(1);
		/**
		 * Changement de slide
		 */
		slider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
			    JSlider source = (JSlider)arg0.getSource();
		        if (!source.getValueIsAdjusting()) {
		        	String val = String.valueOf(source.getValue()*1000);
		        	text.setText(val);
		        }

			}
		});
										
		GridBagConstraints gbc_text = new GridBagConstraints();
		gbc_text.fill = GridBagConstraints.HORIZONTAL;
		gbc_text.insets = new Insets(5, 0, 5, 0);
		gbc_text.gridx = 0;
		gbc_text.gridy = 0;
		gbc_text.gridwidth = 2;
		pan.add(text ,gbc_text);

		GridBagConstraints gbc_start = new GridBagConstraints();
		gbc_start.fill = GridBagConstraints.HORIZONTAL;
		gbc_start.insets = new Insets(5, 0, 5, 0);
		gbc_start.gridx = 0;
		gbc_start.gridy = 1;
		pan.add(start, gbc_start);
	        
	    GridBagConstraints gbc_stop = new GridBagConstraints();
	    gbc_stop.fill = GridBagConstraints.HORIZONTAL;
	    gbc_stop.insets = new Insets(5, 0, 5, 0);
	    gbc_stop.gridx = 1;
	    gbc_stop.gridy = 1;
	    pan.add(stop, gbc_stop);

	    GridBagConstraints gbc_slider = new GridBagConstraints();
	    gbc_slider.fill = GridBagConstraints.HORIZONTAL;
		gbc_slider.insets = new Insets(5, 0, 5, 0);
	    gbc_slider.gridx = 0;
	    gbc_slider.gridy = 2;
	    gbc_slider.gridwidth = 2;
	    pan.add(slider, gbc_slider);
	        
	    this.getContentPane().add(pan);
		this.setVisible(true);
	}
}
