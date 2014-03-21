package fr.utc.nf28.examples;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

/**
 * A Simple example that transverses a generic XML file
 * To see more check: http://docs.oracle.com/javase/tutorial/jaxp/sax/parsing.html
 *
 */
public class MySAXParserExample {

	public static void parse(File xmlFile) {

		try {

			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			MySaxParseHandler handler = new MySaxParseHandler();
			saxParser.parse(xmlFile, handler);

		} catch (IOException e) {
			throw new RuntimeException("I/O Error", e);
		} catch (ParserConfigurationException e) {
			throw new RuntimeException("Parsing configuration error", e);
		} catch (SAXException e) {
			throw new RuntimeException("SAX exception ocurred", e);
		}

	}

	public static void main(String[] args) {

		JFileChooser fc = new JFileChooser(new File("/Users/marciofk/Documents/Info/Phd2012/Teaching/2014/NF28/Theme/implmfk/NF28-TD03-Examples/etc"));
		fc.setAcceptAllFileFilterUsed(false);
		fc.addChoosableFileFilter(new FileFilter() {

			@Override
			public String getDescription() {
				return "XML contacts";
			}

			@Override
			public boolean accept(File f) {
				return f.getName().endsWith(".xml");
			}
		});

		int returnVal = fc.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File f = fc.getSelectedFile();
			parse(f);
		}

		
	}

}
