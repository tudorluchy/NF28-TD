package Model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import javax.swing.DefaultListModel;

public class ListMotDefModel {
	private static ListMotDefModel singletonModel = null;
	public static String PROPERTY_NAME = "mot";
	private PropertyChangeSupport pcs;
	/**
	 * Mot = word, definition, traduction
	 */
	private DefaultListModel<Mot> listModel;
	
	/**
	 * Constructor
	 */
	private ListMotDefModel() {
		super();
		pcs = new PropertyChangeSupport(this);
		listModel = new DefaultListModel<Mot>();
	}

	/**
	 * Get the Model instance 
	 * @return
	 */
	public static ListMotDefModel getInstance() {
		if (null == singletonModel) { 
			singletonModel = new ListMotDefModel();
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
	 * Notifie DefinitionPane
	 */
	public void fireNotification(Mot word) {
		pcs.firePropertyChange(PROPERTY_NAME, null, word);
	}
	
	/**
	 * Read dictionnary file
	 * @param path
	 */
	public void readDico(final String path) {
		 try {
			  FileInputStream fstream = new FileInputStream(path);
			  DataInputStream in = new DataInputStream(fstream);
			  BufferedReader br = new BufferedReader(new InputStreamReader(in));
			  String strLine;
			  String[] temp;
			  String[] temp2;
			  
			  while ((strLine = br.readLine()) != null)   {
				  temp = strLine.split("=");
				  if (temp.length > 1) {
					  temp2 = temp[1].split("@");
					  if (temp2.length > 1) {
						  Mot word = new Mot(temp[0], temp2[0], temp2[1]);
						  listModel.addElement(word);
					  }
				  }
			  }
			  in.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * Get list model
	 * @return
	 */
	public DefaultListModel<Mot> getListModel() {
		return listModel;
	}

	/**
	 * Set list model
	 * @param listModel
	 */
	public void setListModel(DefaultListModel<Mot> listModel) {
		this.listModel = listModel;
	}
	
	
}
