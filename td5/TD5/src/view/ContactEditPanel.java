package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

public class ContactEditPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// nom
	private JPanel nomPanel;
	private JLabel nomLabel;
	private JTextField nomTextArea;
	
	// mail
	private JPanel mailPanel;
	private JLabel mailLabel;
	private JTextField mailTextArea;
	 
	// icone
    private JPanel iconePanel;
    private JLabel iconeLabel;
    private ImageIcon iconeImageIcon;
    private JButton iconeButton;
    private String iconePath = "";
    
    // bouton valider
    private JButton validButton;

	/**
	 * Constructor
	 * @param tree
	 * @param xml
	 */
    public ContactEditPanel() {
		super();
		this.setVisible(true);
		GridBagLayout gbl = new GridBagLayout();
		this.setLayout(gbl);
		
		// nom
		nomPanel = new JPanel();
		nomLabel = new JLabel("Nom : ");
		nomLabel.setPreferredSize(new Dimension(80, 30));
		nomTextArea = new JTextField();
		nomTextArea.setPreferredSize(new Dimension(350, 30));
		nomPanel.add(nomLabel);
		nomPanel.add(nomTextArea);
		
		// mail
		mailPanel = new JPanel();
		mailLabel = new JLabel("Mail : ");
		mailLabel.setPreferredSize(new Dimension(80, 30));
		mailTextArea = new JTextField();
		mailTextArea.setPreferredSize(new Dimension(350, 30));
		mailPanel.add(mailLabel);
		mailPanel.add(mailTextArea);
		
		// icone
		iconePanel = new JPanel();
		iconePanel.setLayout(new BorderLayout());
		iconeImageIcon = new ImageIcon();
		iconeButton = new JButton("Image");
		iconeLabel = new JLabel(iconeImageIcon);
		iconePanel.add(iconeLabel, BorderLayout.CENTER);
		iconePanel.add(iconeButton, BorderLayout.SOUTH);
		
		// bouton valider
		validButton = new JButton("Valider");
		validButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		// nom
		GridBagConstraints gbcNom = new GridBagConstraints();
		gbcNom.fill = GridBagConstraints.HORIZONTAL;
		gbcNom.insets = new Insets(0, 0, 0, 0);
		gbcNom.gridx = 0;
		gbcNom.gridy = 0;
		gbcNom.gridwidth = 1;
		gbcNom.gridheight = 1;
		// add to general panel
		this.add(nomPanel ,gbcNom);
		
		// mail
		GridBagConstraints gbcMail = new GridBagConstraints();
		gbcMail.fill = GridBagConstraints.HORIZONTAL;
		gbcMail.insets = new Insets(0, 0, 0, 0);
		gbcMail.gridx = 0;
		gbcMail.gridy = 1;
		gbcMail.gridwidth = 1;
		gbcMail.gridheight = 1;
		// add to general panel
		this.add(mailPanel ,gbcMail);
		
		// icone
		GridBagConstraints gbcIcone = new GridBagConstraints();
		gbcIcone.fill = GridBagConstraints.HORIZONTAL;
		gbcIcone.insets = new Insets(0, 0, 0, 0);
		gbcIcone.gridx = 0;
		gbcIcone.gridy = 2;
		gbcIcone.gridwidth = 1;
		gbcIcone.gridheight = 1;
		gbcIcone.weighty = 1; // espace avec le mail 
		// add to general panel
		this.add(iconePanel ,gbcIcone);
		
		// bouton valider
		GridBagConstraints gbcValidButton = new GridBagConstraints();
		gbcValidButton.fill = GridBagConstraints.HORIZONTAL;
		gbcValidButton.insets = new Insets(0, 0, 0, 0);
		gbcValidButton.gridx = 0;
		gbcValidButton.gridy = 3;
		gbcValidButton.gridwidth = 1;
		gbcValidButton.gridheight = 1;
		gbcValidButton.weighty = 1;
		// add to general panel
		this.add(validButton, gbcValidButton);
		
		// changer icone
		iconeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					JFileChooser chooser = new JFileChooser();
					chooser.setDialogTitle("Ouvrir une image (300x300 maximum)");
					chooser.setFileFilter(new FileFilter() {
						
						@Override
						public String getDescription() {
							return "*.png or *.jpg";
						}
						
						@Override
						public boolean accept(File f) {
							String fName = f.getName().toLowerCase();
							if (fName.endsWith(".png") || fName.endsWith(".jpg")|| f.isDirectory()) {
			                    return true;
			                } else {
			                	return false;
			                }
						}
					});
		            int returnVal = chooser.showOpenDialog(null);
		            if (returnVal == JFileChooser.APPROVE_OPTION) {
		            	String filePath = chooser.getSelectedFile().getAbsolutePath();
		            	System.out.println("Fichier sélectionné: " + filePath);
		            	if (filePath.toLowerCase().endsWith(".png") || filePath.toLowerCase().endsWith(".jpg")) {
		            		iconeImageIcon = new ImageIcon(filePath);
		            		if (iconeImageIcon.getIconHeight() <= 300 && iconeImageIcon.getIconWidth() <= 300) {
			            		iconePath = filePath;
		            			iconeLabel.setIcon(iconeImageIcon);
			            		// mise à jour graphique
			            		revalidate();
			            		repaint();
		            		} else {
		            			System.out.println("Ouvrir une image type icone s'il vous plaît (300x300 maximum).");
		            		}
		            	} else {
		            		System.out.println("Ouvrir une image s'il vous plaît.");
		            	}
		            }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
    
    /**
     * Reset tab Contact
     */
	public void resetTabContact() {
		nomTextArea.setText(null);
		mailTextArea.setText(null);
		iconeLabel.setIcon(null);
		iconePath = "";
	}
	
	public JTextField getNomTextArea() {
		return nomTextArea;
	}

	public void setNomTextArea(String nomTextArea) {
		this.nomTextArea.setText(nomTextArea);
	}

	public JTextField getMailTextArea() {
		return mailTextArea;
	}

	public void setMailTextArea(String mailTextArea) {
		this.mailTextArea.setText(mailTextArea);
	}

	public JLabel getIconeLabel() {
		return iconeLabel;
	}

	public void setIconeLabel(String iconePath) {
		if (iconePath == null) {
			iconePath = "";
		}
		iconeImageIcon = new ImageIcon(iconePath);
		iconeLabel.setIcon(iconeImageIcon);
		this.iconePath = iconePath;
	}

	public JButton getValidButton() {
		return validButton;
	}

	public void setValidButton(JButton validButton) {
		this.validButton = validButton;
	}

	public String getIconePath() {
		return iconePath;
	}
	
}
