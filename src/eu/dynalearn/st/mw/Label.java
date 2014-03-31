package eu.dynalearn.st.mw;

/**
 * representation of a label = string + language
 * @author mtrna
 *
 */
public class Label {
	/**
	 * string
	 */
	private String label;
	/**
	 * language
	 */
	private MWLanguage lang;
	
	/**
	 * get label
	 * @return
	 */
	public String getLabel() { return this.label; }
	/**
	 * get language
	 * @return
	 */
	public MWLanguage getLanguage() { return this.lang; }
	
	/**
	 * constructor
	 * @param label
	 * @param lang
	 */
	public Label(String label, MWLanguage lang) {
		assert label!=null;
		assert lang!=null;
		this.label = label.trim();
		this.lang = lang;
	}
	/**
	 * copy constructor
	 * @param l
	 */
	public Label(Label l) {
		this(l.label,l.lang);
	}
	/**
	 * constructor
	 * @param label
	 * @param lang
	 */
	public Label(String label, String lang) {
		this(label,MWLanguage.getLanguageForString(lang));
	}
	/**
	 * get array of words
	 * @return
	 */
	public String[] getWords() {
		return label.replaceAll("[ _-]", " ").trim().split(" ");
	}
	/**
	 * convert the label to string
	 */
	public String toString() {
		return label;
	}
	
}
