package Model;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class ContactFacility {
	
	/**
	 * Parse un fichier XML et retourne le modele d'arbre associé
	 * @param filename
	 * @return
	 * @throws SAXException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static ContactTreeModel parse(java.lang.String filename) throws SAXException, FileNotFoundException, IOException {
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
		
		// retourne le modele d'arbre
		return handler.getContactTreeModel();
	}
}
