package Model;

public class Mot {

	private String word;
	private String definition;
	private String translation;

	public Mot(String word, String definition, String translation) {
		super();
		this.word = word;
		this.definition = definition;
		this.translation = translation;
	}

	/**
	 * @return
	 */
	public String getWord() {
		return word;
	}

	/**
	 * @param word
	 */
	public void setWord(String word) {
		this.word = word;
	}

	/**
	 * @return
	 */
	public String getDefinition() {
		return definition;
	}

	/**
	 * @param definition
	 */
	public void setDefinition(String definition) {
		this.definition = definition;
	}

	/**
	 * @return
	 */
	public String getTranslation() {
		return translation;
	}

	/**
	 * @param translation
	 */
	public void setTranslation(String translation) {
		this.translation = translation;
	}

	/**
	 * Return a word
	 */
	@Override
	public String toString() {
		return word;
	}
	
}
