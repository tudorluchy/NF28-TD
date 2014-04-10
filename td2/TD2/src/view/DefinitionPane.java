package view;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import model.ListMotDefModel;
import model.Mot;

public class DefinitionPane extends JPanel implements PropertyChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea definition;
	private JTextArea traduction;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane2;
	/**
	 * Référence vers le modèle
	 */
	private ListMotDefModel model;
	
	/**
	 * Constructor
	 * @throws HeadlessException
	 */
	public DefinitionPane() throws HeadlessException {
		super();
		model = ListMotDefModel.getInstance();
		model.addPropertyChangeListener(this);
		GridBagLayout gbl_pan = new GridBagLayout();
		this.setLayout(gbl_pan);
				
		definition = new JTextArea();
		traduction = new JTextArea();
		definition.setEditable(false);
		traduction.setEditable(false);
		definition.setLineWrap(true);
		traduction.setLineWrap(true);
		definition.setBackground(new Color(146,198,131));
		traduction.setBackground(new Color(231,108,86));
		scrollPane = new JScrollPane(definition);
		scrollPane2 = new JScrollPane(traduction);
		
//		int vericalPolicy = JScrollPane.VERTICAL_SCROLLBAR_ALWAYS;
//		scrollPane.setVerticalScrollBarPolicy(vericalPolicy);
//		scrollPane2.setVerticalScrollBarPolicy(vericalPolicy);
		
		scrollPane.setPreferredSize(new Dimension(220,260));
		scrollPane2.setPreferredSize(new Dimension(220,260));
		
		GridBagConstraints gbc_text1 = new GridBagConstraints();
		gbc_text1.fill = GridBagConstraints.HORIZONTAL;
		gbc_text1.insets = new Insets(0, 0, 0, 20);
		gbc_text1.gridx = 0;
		gbc_text1.gridy = 0;
		this.add(scrollPane ,gbc_text1);
		
		GridBagConstraints gbc_text2 = new GridBagConstraints();
		gbc_text2.fill = GridBagConstraints.HORIZONTAL;
		gbc_text2.insets = new Insets(0, 0, 0, 0);
		gbc_text2.gridx = 1;
		gbc_text2.gridy = 0;
		this.add(scrollPane2 ,gbc_text2);
	}
	
	/**
	 * Receive property change and display new definition 
	 */
	@Override
	public void propertyChange(PropertyChangeEvent pce) {
		if (pce.getPropertyName().equals(ListMotDefModel.PROPERTY_UPDATE_VIEW_WORD)) {
			// new word
			Mot mot = (Mot) pce.getNewValue();
			// change definition
			if (mot != null) {
				definition.setText(mot.getDefinition());
				traduction.setText(mot.getTranslation());
			}
		}
	}
}
