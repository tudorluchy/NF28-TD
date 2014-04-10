package view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import model.FileTransfertHandler;

public class XmlPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea textArea;
	private JScrollPane scrollPaneXML;
	
	/**
	 * Constructor
	 */
	public XmlPanel() {
		super();
		FileTransfertHandler fth = new FileTransfertHandler();
		this.setLayout(new BorderLayout());
		textArea = new JTextArea();
		textArea.setEditable(true);
		textArea.setEnabled(true);
		textArea.setTransferHandler(fth);
		scrollPaneXML = new JScrollPane(textArea);
		scrollPaneXML.setMinimumSize(new Dimension(250, 100));
		this.add(scrollPaneXML, BorderLayout.CENTER);
	}

	public JTextArea getTextArea() {
		return textArea;
	}

	public void setTextArea(String text) {
		this.textArea.setText(text);
	}
}
