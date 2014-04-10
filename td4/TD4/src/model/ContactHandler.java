package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.swing.tree.DefaultMutableTreeNode;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ContactHandler extends DefaultHandler {
	/**
	 * Noeud parent
	 */
	private DefaultMutableTreeNode parentNode;
	/**
	 * Noeud courant
	 */
	private DefaultMutableTreeNode currentNode;
	/**
	 * Construction d'une liste de contacts
	 */
    private List<Contact> contactList;
    /**
     * Construction d'un contact
     */
    private Contact contact;
    
    /**
     * Stocker les balises
     */
    private Stack<String> elementStack;
    /**
     * Stocker les objets contacts
     */
    private Stack<Contact> objectStack;
    
    /**
     * Construction du contenu entre les balises
     */
    StringBuilder currentText = new StringBuilder();
 	
    /**
     * Constructor
     */
    public ContactHandler() {
        super();
        parentNode = null;
        currentNode = null;
        elementStack = new Stack<String>();
        objectStack = new Stack<Contact>();
        contactList = new ArrayList<>();
    }
    
	/**
	 * Retourne la racine de l'arbre
	 * @return
	 */
	public DefaultMutableTreeNode getRoot() {
      return parentNode;
    }
	
	/**
	 * Retourne un modèle d'arbre
	 * @return
	 */
	public ContactTreeModel getContactTreeModel() {
		ContactTreeModel contactTreeModel = new ContactTreeModel(getRoot());
		return contactTreeModel;
	}

	/**
	 * Retourne une liste de contacts
	 * @return
	 */
    public List<Contact> getContactList() {
        return contactList;
    }
    
    /**
     * Retourne la balise en cours de traitement
     */
    private String currentElement() {
        return (String) this.elementStack.peek();
    }

    /**
     * Début d'une balise
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
    	DefaultMutableTreeNode newNode = null;
    	
    	//Push it in element stack
    	this.elementStack.push(qName);
    	
    	// pas de feuille : categorie (string) ou contact (objet)
    	if ((qName != "nom") && (qName != "mail") && (qName != "icone")) {
    		// contact
    		if (qName == "contact") {
    	    	contact = new Contact();
    	        newNode = new DefaultMutableTreeNode(contact);
    	        this.objectStack.push(contact);
    		} else { // categorie
    			newNode = new DefaultMutableTreeNode(qName);
    		}
    		// root
    		if (currentNode == null) {
    			parentNode = newNode;
            } else { // add child
            	currentNode.add(newNode);
            	newNode.setParent(currentNode);
            }
    		currentNode = newNode;
    	} 
    }

    /**
     * Fin d'une balise
     */
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
    	// </nom> 
    	if ("nom".equals(currentElement())) {
    		// list
    		((Contact) this.objectStack.peek()).setNom(currentText.toString());
            // node
            ((Contact) currentNode.getUserObject()).setNom(currentText.toString());
            currentText = new StringBuilder();
         // </mail>
    	} else if ("mail".equals(currentElement())) {
    		// list
    		((Contact) this.objectStack.peek()).setMail(currentText.toString());
            // node
            ((Contact) currentNode.getUserObject()).setMail(currentText.toString());
            currentText = new StringBuilder();
         // </icone>
    	} else if ("icone".equals(currentElement())) {
    		// list
    		((Contact) this.objectStack.peek()).setIcone(currentText.toString());
            // node
            ((Contact) currentNode.getUserObject()).setIcone(currentText.toString());
            currentText = new StringBuilder();
    	}
    	
    	//Remove last added  element
        this.elementStack.pop();
        
        //Contact instance has been constructed so pop it from object stack and push in contact list
        if ("contact".equals(qName)) {
            Contact c = (Contact) this.objectStack.pop();
            this.contactList.add(c);
        }
        
        // pas de feuille : on remonte au parent
        if ((qName != "nom") && (qName != "mail") && (qName != "icone")) {
        	currentNode = (DefaultMutableTreeNode)currentNode.getParent();
        }
    }
    
    /**
     * Lit contenu entre les balises
     */
    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
    	String content = new String(ch, start, length);
    	
    	// ignore empty content
		if (content.trim().equals("") || content.trim().equals("\n")) {
			return;
		}
		
		currentText.append(content);
    }
}
