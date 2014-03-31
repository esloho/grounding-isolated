package eu.dynalearn.st.treatment;

import eu.dynalearn.st.mw.Label;

/**
 * option representing creation of an anchor term
 * @author mtrna
 *
 */
public class TreatmentActionCreateAnchor extends TreatmentAction {
	/**
	 * label
	 */
	protected Label label;

	public TreatmentActionCreateAnchor () {
	}

	/**
	 * constructor
	 * @param label
	 */
	public TreatmentActionCreateAnchor (Label label) {
		this.label = label;
	}
	/**
	 * convert the option to string
	 */
	public String toString() {
		return "create a new anchor term \""+label.toString()+"\".";
	}
	
	/**
	 * get anchor label
	 */
	public String getAnchorTerm() {
		return this.label.getLabel();
	}
}
