package View;

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
import javax.swing.JTextArea;
import javax.swing.filechooser.FileFilter;

import Model.ContactFacility;
import Model.ContactTreeModel;

public class TreeView extends JFrame {

	private ContactTreePanel treePanel;
	private JSplitPane splitPane;
	private JScrollPane scrollPaneTree;
	
	private JTabbedPane tabbedPane;
	private JScrollPane scrollPaneXML;
	private JTextArea textAreaXML;
	
	private JMenu menu;
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
		            		textAreaXML.setText(model.toXml());
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
		
		// xml
		textAreaXML = new JTextArea();
		scrollPaneXML = new JScrollPane(textAreaXML);
		scrollPaneXML.setMinimumSize(new Dimension(250, 100));
		tabbedPane = new JTabbedPane();
		tabbedPane.addTab("XML", scrollPaneXML);
		
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPaneTree, tabbedPane);
		this.getContentPane().add(splitPane);
	}
}
