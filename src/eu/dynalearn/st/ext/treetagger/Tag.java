package eu.dynalearn.st.ext.treetagger;

/**
 * encapsulates the tagging information from treetagger (or any other tagger) 
 * @author mtrna
 *
 */
public class Tag {
	private String token, pos, lemma;
	/**
	 * no implicit constructor
	 */
	private Tag() {}
	/**
	 * constructor
	 * @param token
	 * @param pos
	 * @param lemma
	 */
	public Tag(String token, String pos, String lemma) {
		this();
    	this.token=token;
    	this.pos=pos.toUpperCase();
    	this.lemma=lemma;			
	}
	/**
	 * get token
	 * @return
	 */
    public String getToken() { return this.token; }
    /**
     * get part of speech
     * @return
     */
    public String getPos() { return this.pos; }
    /**
     * get lemma
     * @return
     */
    public String getLemma() { return this.lemma; }
    /**
     * get a string representation
     */
    public String toString() {
    	return getToken()+":"+getPos()+":"+getLemma();
    }
    
}