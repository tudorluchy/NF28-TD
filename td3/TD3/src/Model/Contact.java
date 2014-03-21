package Model;

public class Contact {
	private String nom;
	private String mail;
	private String icone;
	
	/**
	 * Constructor
	 */
	public Contact() {
		super();
	}

	/**
	 * Constructor
	 * @param nom
	 * @param mail
	 * @param icone
	 */
	public Contact(String nom, String mail, String icone) {
		super();
		this.nom = nom;
		this.mail = mail;
		this.icone = icone;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getIcone() {
		return icone;
	}

	public void setIcone(String icone) {
		this.icone = icone;
	}

	@Override
	public String toString() {
//		return "Contact [nom=" + nom + ", mail=" + mail + ", icone=" + icone + "]";
		return nom;
	}
}
