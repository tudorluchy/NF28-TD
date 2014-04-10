package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.filechooser.FileFilter;

import model.ContactFacility;
import model.ContactTreeModel;

public class TreeView extends JFrame {

	// tree
	private ContactTreePanel treePanel;
	private JScrollPane scrollPaneTree;
	
	// onglets
	private JTabbedPane tabbedPane;
	private XmlPanel xmlPanel;
	
	// split
	private JSplitPane splitPane;
	
	private JMenu menuFile;
	private JMenuBar menuBar;
	private JMenuItem menuItemOpen;
	private JMenuItem menuItemExit;
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor
	 */
	public TreeView() {
		super();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(100, 50);
		this.setMinimumSize(new Dimension(800, 500));
		
		menuBar = new JMenuBar();
		menuFile = new JMenu("Fichier");
		menuItemOpen = new JMenuItem("Ouvrir");
		menuItemExit = new JMenuItem("Quitter");
		menuFile.add(menuItemOpen);
		menuFile.add(menuItemExit);
		
		menuBar.add(menuFile);
		this.setJMenuBar(menuBar);
		
		/**
		 * Click on "Ouvrir"
		 */
		menuItemOpen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					JFileChooser chooser = new JFileChooser();
					chooser.setDialogTitle("Ouvrir un dictionnaire .txt");
					chooser.setFileFilter(new FileFilter() {
						
						@Override
						public String getDescription() {
							return "*.xml";
						}
						
						@Override
						public boolean accept(File f) {
							if (f.getName().toLowerCase().endsWith(".xml") || f.isDirectory()) {
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
		            	if (filePath.toLowerCase().endsWith(".xml")) {
		            		// recup modele
		            		ContactTreeModel model =  ContactFacility.parse(filePath);
		            		treePanel.setModel(model);
		            		xmlPanel.setTextArea(model.toXml());
		            		// mise à jour graphique
		            		revalidate();
		            		repaint();
		            	} else {
		            		System.out.println("Chose a text file (.xml) please.");
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
	
		// tree
		treePanel = new ContactTreePanel(null);
		scrollPaneTree = new JScrollPane(treePanel);
		scrollPaneTree.setMinimumSize(new Dimension(250, 100));
		
		
		// onglet xml
		xmlPanel = new XmlPanel();
		tabbedPane = new JTabbedPane();
		tabbedPane.addTab("XML", xmlPanel);
		
		// split
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPaneTree, tabbedPane);
		this.getContentPane().add(splitPane);
		this.setVisible(true);
	}
}
