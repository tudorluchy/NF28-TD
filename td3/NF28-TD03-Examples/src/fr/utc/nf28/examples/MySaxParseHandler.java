package fr.utc.nf28.examples;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MySaxParseHandler extends DefaultHandler {

	private String currentTag;
	private int level;

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {

		// store tag name for future use
		currentTag = qName;
		
		// Show some interesting properties of the XML file
		printWithSpaces(level++, "start tag " + qName);
		
		for(int i=0;i<attributes.getLength();i++) {
			printWithSpaces(level, "attribute " + attributes.getQName(i) +
						", value" + attributes.getValue(i));	
		}
		
	}
	
	private void printWithSpaces(int level, String s) {
		for(int i=0;i<level;i++) {
			System.out.print("   ");
		}
		System.out.println(s);
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {

		String content = new String(ch, start, length);
		
		// ignore empty content
		if(content.trim().equals("") || content.trim().equals("\n")) 
			return;
		
		printWithSpaces(level,"content: " + content + " belonging to " +
				currentTag);
		
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		printWithSpaces(--level, "end tag " + qName);
	}

}
