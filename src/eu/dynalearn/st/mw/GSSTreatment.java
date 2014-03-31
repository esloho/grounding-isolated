package eu.dynalearn.st.mw;

import java.util.ArrayList;
import java.util.List;

import eu.dynalearn.st.ext.google.GoogleSpellingSuggestions;
import eu.dynalearn.st.treatment.TreatmentAction;
import eu.dynalearn.st.treatment.TreatmentActionRename;

/**
 * 
 * @author mtrna
 *
 */
public class GSSTreatment {
	
	/**
	 * label which is treated by this class
	 */
	protected Label label; 
	/**
	 * list of options what to do with the label
	 */
	protected List<TreatmentAction> options;
	
	/**
	 * constructor, which label is being treated here
	 * @param l
	 */
	public GSSTreatment(Label l) {
		this.label = new Label(l);
		this.options = null;
	}
	
	/**
	 * get list of options how to treat a label
	 * @return
	 * @throws Exception
	 */
	public List<TreatmentAction> getOptions() throws Exception {
		
		// we would not compute it again in case we have it already
		if (this.options!=null) return this.options;
		
		this.options = new ArrayList<TreatmentAction>();
		
		final GoogleSpellingSuggestions yss = new GoogleSpellingSuggestions();
		yss.extraction(this.label.getLabel());
		
		for (String s: yss.getResult()) {
			
			final float cValue = getValue(s); 
				
			final TreatmentAction optRename = new TreatmentActionRename(this.label,new Label(s,this.label.getLanguage()));
			optRename.setScore(cValue);//mainClusterValue==0?0:cValue/mainClusterValue);
			optRename.setSource("yahoo");
			this.options.add(optRename);

		}
		
		return this.options;
	} 
	
	/**
	 * compute a score for a cluster
	 * @param c
	 * @return
	 */
	protected float getValue(String s) {
		// TODO: implement ranking on the basis of edit distance
		return 1f;
	}
}
