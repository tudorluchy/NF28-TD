package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.tree.DefaultMutableTreeNode;

import model.ApplicationModel;
import model.Contact;

public class TreeView extends JFrame implements PropertyChangeListener {

	// tree
	private ContactTreePanel treePanel;
	private JScrollPane scrollPaneTree;
	
	// onglets
	private JTabbedPane tabbedPane;
	private XmlPanel xmlPanel;
	private ContactEditPanel contactEditPannel;
	
	// split
	private JSplitPane splitPane;
	
	private JMenu menuFile;
	private JMenu menuContact;
	private JMenuBar menuBar;
	private JMenuItem menuItemOpen;
	private JMenuItem menuItemExit;
	private JMenuItem menuItemSave;
	private JMenuItem menuItemSaveAs;
	private JMenuItem menuItemAddContact;
	
	// click on add contact
	private Boolean clickAddContact;
	
	// model
	private ApplicationModel appModel = ApplicationModel.getInstance();
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor
	 */
	public TreeView() {
		super();
		// listen to the model
		appModel.addPropertyChangeListener(this); 
		
		menuBar = new JMenuBar();
		
		// Fichier
		menuFile = new JMenu("Fichier");
		menuItemOpen = new JMenuItem("Ouvrir");
		menuItemSave = new JMenuItem("Sauver");
		menuItemSaveAs = new JMenuItem("Sauver sous");
		menuItemExit = new JMenuItem("Quitter");
		menuFile.add(menuItemOpen);
		menuFile.add(menuItemSave);
		menuFile.add(menuItemSaveAs);
		menuFile.add(menuItemExit);
		
		// Contact
		menuContact = new JMenu("Contact");
		menuItemAddContact = new JMenuItem("Ajouter Contact");
		menuContact.add(menuItemAddContact);
		
		menuBar.add(menuFile);
		menuBar.add(menuContact);
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
		            		appModel.parse(filePath);
		            		// set tree model
		            		treePanel.setModel(appModel.getModel());
		            		// set xml text
		            		xmlPanel.setTextArea(appModel.getModel().toXml());
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
		
		menuItemSave.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				appModel.save();
			}
		});
		
		menuItemSaveAs.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser saveFile = new JFileChooser();
                saveFile.showSaveDialog(null);
                if (saveFile.getSelectedFile() != null) {
                	String filePath = saveFile.getSelectedFile().getAbsolutePath();
                	appModel.saveAs(filePath);
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
		 * Click on Ajouter un contact
		 */
		menuItemAddContact.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (appModel.getModel() != null) {
					// clear tab Contact
					ContactEditPanel tabContact = (ContactEditPanel) (tabbedPane.getComponentAt(1));
					tabContact.resetTabContact();
					// enable tab Contact
					tabbedPane.setEnabledAt(1, true);
					// go on tab Contact
					tabbedPane.setSelectedIndex(1);
					//
					clickAddContact = true;
				}
			}
		});
		
		// tree
		treePanel = new ContactTreePanel(null);
		scrollPaneTree = new JScrollPane(treePanel);
		scrollPaneTree.setMinimumSize(new Dimension(250, 100));
		
		// changement de contact
		treePanel.addTreeSelectionListener(new TreeSelectionListener() {
			
			@Override
			public void valueChanged(TreeSelectionEvent arg0) {
				
				DefaultMutableTreeNode node = (DefaultMutableTreeNode)treePanel.getLastSelectedPathComponent();          
                // notification model
                appModel.setSelectedNode(node);
                clickAddContact = false;
			}
		});
		
		// onglet xml
		xmlPanel = new XmlPanel();
		// onglet contact edit
		contactEditPannel = new ContactEditPanel();
		tabbedPane = new JTabbedPane();
		tabbedPane.addTab("XML", xmlPanel);
		tabbedPane.addTab("Contact", contactEditPannel);
		
		// disable tab Contact
		tabbedPane.setEnabledAt(1, false);
		// go on tab xml
		tabbedPane.setSelectedIndex(0);

		// split
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPaneTree, tabbedPane);
		
		/**
		 * Click on bouton Valider
		 */
		contactEditPannel.getValidButton().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Contact c = new Contact();
				c.setNom(contactEditPannel.getNomTextArea().getText());
			    c.setMail(contactEditPannel.getMailTextArea().getText());
				c.setIcone(contactEditPannel.getIconePath());
				if (!clickAddContact) {
					appModel.updateContact(c);
				} else {
					appModel.addContact(c);
				}
			}
		});
		
		this.getContentPane().add(splitPane);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(100, 50);
		this.setMinimumSize(new Dimension(800, 500));
		this.setVisible(true);
	}

	/**
	 * Receive property change and display new view
	 */
	@Override
	public void propertyChange(PropertyChangeEvent pce) {
		if (pce.getPropertyName().equals(ApplicationModel.PROPERTY_UPDATE_VIEW_CONTACT_TAB)) {
			// new contact
			Contact contact = (Contact) pce.getNewValue();
			// clear tab Contact
			ContactEditPanel tabContact = (ContactEditPanel) (tabbedPane.getComponentAt(1));
			tabContact.resetTabContact();
			// refresh tab contact
			tabContact.setNomTextArea(contact.getNom());
			tabContact.setMailTextArea(contact.getMail());
			tabContact.setIconeLabel(contact.getIcone());
			// enable tab Contact
			tabbedPane.setEnabledAt(1, true);
			// go on tab Contact
			// tabbedPane.setSelectedIndex(1);
		} else if (pce.getPropertyName().equals(ApplicationModel.PROPERTY_UPDATE_VIEW_XML_TAB)) {
			String xml = (String) pce.getNewValue();
			// reload xml
			xmlPanel.setTextArea(xml);
			revalidate();
			repaint();
		} else if (pce.getPropertyName().equals(ApplicationModel.PROPERTY_UPDATE_VIEW_CATEGORIE)) {
			// disable tab Contact
			tabbedPane.setEnabledAt(1, false);
			// go on tab xml
			tabbedPane.setSelectedIndex(0);
		}
	}
}
