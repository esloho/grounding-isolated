package eu.dynalearn.st.treatment;

import eu.dynalearn.st.mw.Label;

/**
 * option representing enriching of the vocabulary
 * @author mtrna
 *
 */
public class TreatmentActionEnrich extends TreatmentAction {
	/**
	 * original label
	 */
	protected Label label;

	public TreatmentActionEnrich () {
	}

	/**
	 * constructor
	 * @param label
	 */
	public TreatmentActionEnrich (Label label) {
		this.label = label;
	}
	/**
	 * convert the option to a string
	 */
	public String toString() {
		return "add \""+label.toString()+"\" to the model.";
	}
	
	/**
	 * get new label
	 */
	public String getNewLabel() {
		return this.label.getLabel();
	}

}
