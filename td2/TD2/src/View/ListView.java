package View;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;

import Model.ListMotDefModel;
import Model.Mot;

public class ListView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JMenu menu;
	private JMenuBar menuBar;
	private JMenuItem menuItemOpen;
	private JMenuItem menuItemExit;
	private JList<Mot> words;
	private JScrollPane scrollPane;
	private DefinitionPane defPane;
	private JSplitPane splitPane;
	/**
	 * Référence vers le modèle
	 */
	private ListMotDefModel model;
	
	/**
	 * Constructor
	 * @throws HeadlessException
	 */
	public ListView() throws HeadlessException {
		super();
		model = ListMotDefModel.getInstance();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(500, 0);
		this.setMinimumSize(new Dimension(500, 500));
		initComponent();
		this.setVisible(true);
	}

	/**
	 * Windows construction
	 */
	public void initComponent() {
		menuBar = new JMenuBar();
		
		menu = new JMenu("Fichier");
		menuItemOpen = new JMenuItem("Ouvrir");
		menuItemExit = new JMenuItem("Quitter");
		menu.add(menuItemOpen);
		menu.add(menuItemExit);
		
		menuBar.add(menu);
		this.setJMenuBar(menuBar);
		
        words = new JList<Mot>();

		/**
		 * Click on "Ouvrir" : choose dictionnary file
		 */
		menuItemOpen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					// clear words
					model.getListModel().clear();
					JFileChooser chooser = new JFileChooser();
					chooser.setDialogTitle("Ouvrir un dictionnaire .txt");
					chooser.setFileFilter(new FileFilter() {
						
						@Override
						public String getDescription() {
							return "*.txt";
						}
						
						@Override
						public boolean accept(File f) {
							if (f.getName().toLowerCase().endsWith(".txt") || f.isDirectory()) {
			                    return true;
			                } else {
			                	return false;
			                }
						}
					});
		            int returnVal = chooser.showOpenDialog(menuItemOpen.getParent());
		            if (returnVal == JFileChooser.APPROVE_OPTION) {
		            	String filePath = chooser.getSelectedFile().getAbsolutePath();
		            	System.out.println("Fichier sélectionné: " + filePath);
		            	if (filePath.toLowerCase().endsWith(".txt")) {
		            		// remplir modèle	
			                model.readDico(filePath);
			                words.setModel(model.getListModel()); 
		            	} else {
		            		System.out.println("Chose a text file (.txt) please.");
		            	}
		            }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		/**
		 * Exit program
		 */
		menuItemExit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					dispose();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		
		/**
		 * Click on an item
		 */
		words.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
                if (!arg0.getValueIsAdjusting()) {
                	Mot word = words.getSelectedValue();
                	model.fireNotification(word);
                }
			}
		});
		
		defPane = new DefinitionPane();
		scrollPane = new JScrollPane(words);
		splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollPane, defPane);
		splitPane.setEnabled(false);
		
		this.getContentPane().add(splitPane);
	}
}
