package eu.dynalearn.st.treatment;

/**
 * abstract class of which all the action regarding the grounding derive
 * - MWActionEnrich 
 * - MWActionOption 
 * - MWActionRename 
 * @author mtrna
 *
 */
abstract public class TreatmentAction {
	/**
	 * score of this option
	 */
	private float score;
	/**
	 * source of this option
	 */
	private String source;
	
	public TreatmentAction() {
	}

	/**
	 * 
	 * return score of this option
	 * @return
	 */
	public float getScore() {
		return this.score; 
	}
	/**
	 * 
	 * return source of this option
	 * @return
	 */
	public String getSource() {
		return this.source; 
	}
	/**
	 * set score to a value
	 * @param score
	 * @return
	 */
	public TreatmentAction setScore(final float score) {
		this.score = score;
		return this; 
	}
	/**
	 * set source to this option
	 * @param score
	 * @return
	 */
	public String setSource(final String source) {
		this.source = source;
		return source;
	}
	
	abstract public String toString() ;
}
