package eu.dynalearn.st.mw;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import eu.dynalearn.st.ext.treetagger.Cluster;
import eu.dynalearn.st.ext.treetagger.POS;
import eu.dynalearn.st.ext.treetagger.Spectrum;
import eu.dynalearn.st.ext.treetagger.TreeTagger;
import eu.dynalearn.st.treatment.TreatmentAction;
import eu.dynalearn.st.treatment.TreatmentActionEnrich;
import eu.dynalearn.st.treatment.TreatmentActionRename;

/**
 * 
 * @author mtrna
 *
 */
public class MWTreatment {
	
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
	public MWTreatment(Label l) {
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

		final String[] splitted = label.getWords();
		final Cluster mainCluster = new Cluster( splitted, label.getLanguage() );

		final Set<Cluster> clusters =  mainCluster.allSubClusters(TreeTagger.getJNPTagSet(label.getLanguage()));
		final float mainClusterValue = getValue(mainCluster);
		System.out.println("--> "+ mainClusterValue);
		
		// for each cluster (that has some grounding proposals)...
		// ...add options to the list of possible treatments
		for (Cluster c:clusters) {
			final float cValue = getValue(c); 
			if (c.toString().compareToIgnoreCase(mainCluster.toString())!=0) {
				final Label labelNew = new Label( c.implode(), label.getLanguage() ); 
				
				final TreatmentAction optRename = new TreatmentActionRename(this.label,labelNew);
				optRename.setScore(cValue);//mainClusterValue==0?0:cValue/mainClusterValue);
				optRename.setSource("mw");
				
				this.options.add(optRename);

				final TreatmentAction optEnrich = new TreatmentActionEnrich(labelNew); 
				optEnrich.setScore(cValue);//mainClusterValue==0?0:cValue/mainClusterValue);
				optEnrich.setSource("mw");
				this.options.add(optEnrich);
			}
		}
		
		/*
		final TreatmentAction optAnchor = new TreatmentActionCreateAnchor(this.label);
		optAnchor.setScore(0);
		this.options.add(optAnchor);
		*/
		
		return this.options;
	} 
	
	/**
	 * compute a score for a cluster
	 * @param c
	 * @return
	 */
	protected float getValue(Cluster c) {
		final Spectrum s = c.getSpectrum();
		final int nouns = s.getFreq(POS.POS_NNx);
		final int propNouns = s.getFreq(POS.POS_NPx);
		final int adjectives = s.getFreq(POS.POS_JJx);
		final float value = 1*adjectives + 2*nouns + 3*propNouns;
		return value;
	}
}
