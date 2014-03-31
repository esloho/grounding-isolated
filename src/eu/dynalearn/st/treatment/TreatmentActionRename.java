package eu.dynalearn.st.treatment;

import eu.dynalearn.st.mw.Label;

/**
 * option representing renaming of the label
 * @author mtrna
 *
 */
public class TreatmentActionRename extends TreatmentAction {
	/**
	 * original form of the label
	 */
	protected Label original;
	/**
	 * new form of the label
	 */
	protected Label renamed;

	public TreatmentActionRename () {
	}

	/**
	 * constructor
	 */
	public TreatmentActionRename (Label original, Label renamed) {
		this.original = original;
		this.renamed = renamed;
	}
	/**
	 * convert the option to a string
	 */
	public String toString() {
		return "substitute \""+this.original.toString()+"\" by \""+this.renamed.toString()+"\".";
	}
	
	/**
	 * get new label
	 */
	public String getOriginalLabel() {
		return this.original.getLabel();
	}

	/**
	 * get new label
	 */
	public String getRenamedLabel() {
		return this.renamed.getLabel();
	}
}
