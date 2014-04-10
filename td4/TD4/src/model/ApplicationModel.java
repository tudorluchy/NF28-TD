package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.tree.DefaultMutableTreeNode;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class ApplicationModel {
	/**
	 * Singleton
	 */
	private static ApplicationModel singletonModel = null;
	public static String PROPERTY_UPDATE_VIEW_CONTACT_TAB = "updateView1";
	public static String PROPERTY_UPDATE_VIEW_XML_TAB = "updateView2";
	public static String PROPERTY_UPDATE_VIEW_TREE_AND_XML_TAB = "updateView3";
	public static String PROPERTY_UPDATE_VIEW_CATEGORIE = "updateView4";
	private PropertyChangeSupport pcs;
	/**
	 * Noeud selectionné
	 */
	private DefaultMutableTreeNode selectedNode;
	/**
	 * Fichier xml ouvert
	 */
	private String currentFile;
	
	/**
	 * Le modèle
	 */
	private ContactTreeModel model;	
	
	/**
	 * Constructor
	 */
	public ApplicationModel() {
		super();
		pcs = new PropertyChangeSupport(this);
	}
	
	/**
	 * Get the Model instance 
	 * @return
	 */
	public static ApplicationModel getInstance() {
		if (null == singletonModel) { 
			singletonModel = new ApplicationModel();
        }
        return singletonModel;
	}
	
	/**
	 * Add a listener to the source
	 * @param pcl
	 */
	public void addPropertyChangeListener(PropertyChangeListener pcl) {
		pcs.addPropertyChangeListener(pcl);
	}
	
	/**
	 * Notifie update contact tab   
	 */
	public void fireNotificationUpdateContactTab(Contact contact) {
		pcs.firePropertyChange(PROPERTY_UPDATE_VIEW_CONTACT_TAB, null, contact);
	}
	
	/**
	 * Notifie update xml tab
	 */
	public void fireNotificationUpdateXmlTab(String xml) {
		pcs.firePropertyChange(PROPERTY_UPDATE_VIEW_XML_TAB, null, xml);
	}
	
	/**
	 * Notifie update tree and xml tab
	 */
	public void fireNotificationUpdateTreeAndXmlTab() {
		pcs.firePropertyChange(PROPERTY_UPDATE_VIEW_TREE_AND_XML_TAB, null, null);
	}
	
	/**
	 * Notifie update view categorie
	 */
	public void fireNotificationUpdateViewCategorie() {
		pcs.firePropertyChange(PROPERTY_UPDATE_VIEW_CATEGORIE, null, null);
	}
	
	/**
	 * Parse un fichier XML et charge le modèle d'arbre associé
	 * @param filename
	 * @throws SAXException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void parse(java.lang.String filename) throws SAXException, FileNotFoundException, IOException {
		String xmlReaderClassName = "org.apache.xerces.parsers.SAXParser";
		XMLReader xr = XMLReaderFactory.createXMLReader(xmlReaderClassName);
		ContactHandler handler = new ContactHandler();
		xr.setContentHandler(handler);
		xr.setErrorHandler(handler);
		FileReader r = new FileReader(filename);
		xr.parse(new InputSource(r));
		
		// test : ok
//		List<Contact> listC = handler.getContactList();
//		for (Contact contact: listC) {
//			System.out.println("contact : " + contact);
//		}
		
		currentFile = filename;
		model = handler.getContactTreeModel();
	}
	
	/**
	 * Enregistre le nouveau mod�le dans le fichier xml
	 */
	public void save() {
		try {
			if (currentFile != null && model != null) {
			  System.out.println("Sauvegardé : " + currentFile);
	          File file = new File(currentFile);
	          BufferedWriter output = new BufferedWriter(new FileWriter(file, false));
	          output.write(model.toXml());
	          output.close();
			}
        } catch ( IOException e ) {
           e.printStackTrace();
        }
	}
	
	/**
	 * Enregistre le nouveau mod�le dans un nouveau fichier xml
	 * @param filename
	 */
	public void saveAs(String filename) {
		try {
			if (filename != null && model != null) {
			  System.out.println("Sauvegardé sous : " + filename);
	          File file = new File(filename);
	          BufferedWriter output = new BufferedWriter(new FileWriter(file, false));
	          output.write(model.toXml());
	          output.close();
			}
        } catch ( IOException e ) {
           e.printStackTrace();
        }
	}
	
	/**
	 * Ajouter un nouveau contact
	 * @param c
	 */
	public void addContact(Contact c) {
		if (c.getNom().equals("")) {
			c.setNom("empty");
		}
		if (c.getMail().equals("")) {
			c.setMail("");
		}
		if (c.getIcone().equals("")) {
			c.setIcone("");
		}
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(c);
		DefaultMutableTreeNode root =  (DefaultMutableTreeNode) model.getRoot();
		// update model avec le nouveau contact
		model.insertNodeInto(node, root, root.getChildCount());
		// update xml tab
		fireNotificationUpdateXmlTab(model.toXml());
	}
	
	/**
	 * Update view après la sélection d'un noeud
	 * @param node
	 */
	public void setSelectedNode(DefaultMutableTreeNode node) {
		if (node == null) { 
//			System.out.println("Pas de noeud sélectionné.");
        	return;
        }
		selectedNode = node;
		
        Object nodeObject = node.getUserObject();
        // contact sélectionné
        if (nodeObject instanceof Contact) {
        	Contact contact = (Contact) nodeObject;
        	// update contact tab
        	fireNotificationUpdateContactTab(contact);
        } else {
        	// update view categorie
        	fireNotificationUpdateViewCategorie();
        }
	}	
	
	/**
	 * Récupère le modèle
	 * @return
	 */
	public ContactTreeModel getModel() {
		return model;
	}

	/**
	 * Update model et xml tab après mise à jour contact
	 * @param c
	 */
	public void updateContact(Contact c) {
		Object nodeObject = selectedNode.getUserObject();
		if (!(nodeObject instanceof Contact)) {
//			System.out.println("Pas de noeud Contact sélectionné.");
			return;
		} else {
			((Contact)nodeObject).setNom(c.getNom());
			((Contact)nodeObject).setMail(c.getMail());
			((Contact)nodeObject).setIcone(c.getIcone());
			
			// update model
			model.reload(selectedNode);
			// update xml tab
			fireNotificationUpdateXmlTab(model.toXml());	
		}
	}
}
