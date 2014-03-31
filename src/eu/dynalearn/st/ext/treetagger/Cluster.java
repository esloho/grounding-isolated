package eu.dynalearn.st.ext.treetagger;

import java.io.IOException;
import java.util.*;

import org.annolab.tt4j.TreeTaggerException;

import eu.dynalearn.st.mw.Label;
import eu.dynalearn.st.mw.MWLanguage;
import eu.dynalearn.util.StringTools;

/**
 * represents a continuous list of tags 
 * 
 * @author mtrna
 *
 */
public class Cluster {
	
	/**
	 * original label
	 */
	private Label label;
	/**
	 * list of tags
	 */
	private List<Tag> tags;
	/**
	 * instances of tree-tagger for different languages (cached)
	 */
	private static TreeTagger[] tt = new TreeTagger[MWLanguage.values().length];
	static {
		for (int i=0;i<tt.length;i++) {
			tt[i] = null;
		}
	}

	/**
	 * mapping of English tags to internal representation 
	 */
	private static final Map<String,POS> mappingEnglish=new HashMap<String, POS>();
	static {
		mappingEnglish.put("CC", POS.POS_x);
		mappingEnglish.put("CD", POS.POS_x);
		mappingEnglish.put("DT", POS.POS_x);
		mappingEnglish.put("EX", POS.POS_x);
		mappingEnglish.put("FW", POS.POS_x);
		mappingEnglish.put("IN", POS.POS_x);
		mappingEnglish.put("IN/that", POS.POS_x);		
		mappingEnglish.put("JJ", POS.POS_JJx);
		mappingEnglish.put("JJR", POS.POS_JJx);	
		mappingEnglish.put("JJS", POS.POS_JJx);		
		mappingEnglish.put("LS", POS.POS_x);
		mappingEnglish.put("MD", POS.POS_x);		
		mappingEnglish.put("NN", POS.POS_NNx);
		mappingEnglish.put("NNS", POS.POS_NNx);	
		mappingEnglish.put("NP", POS.POS_NPx);
		mappingEnglish.put("NPS", POS.POS_NPx);		
		mappingEnglish.put("PDT", POS.POS_x);
		mappingEnglish.put("POS", POS.POS_x);
		mappingEnglish.put("PP", POS.POS_x);
		mappingEnglish.put("PP$", POS.POS_x);	
		mappingEnglish.put("RB", POS.POS_x);
		mappingEnglish.put("RBR", POS.POS_x);
		mappingEnglish.put("RBS", POS.POS_x);
		mappingEnglish.put("RP", POS.POS_x);
		mappingEnglish.put("SENT", POS.POS_x);
		mappingEnglish.put("SYM", POS.POS_x);	
		mappingEnglish.put("TO", POS.POS_x);
		mappingEnglish.put("UH", POS.POS_x);
		mappingEnglish.put("VB", POS.POS_x);
		mappingEnglish.put("VBD", POS.POS_x);
		mappingEnglish.put("VBG", POS.POS_x);
		mappingEnglish.put("VBN", POS.POS_x);
		mappingEnglish.put("VBP", POS.POS_x);
		mappingEnglish.put("VBZ", POS.POS_x);	
		mappingEnglish.put("VH", POS.POS_x);
		mappingEnglish.put("VHD", POS.POS_x);
		mappingEnglish.put("VHG", POS.POS_x);
		mappingEnglish.put("VHN", POS.POS_x);
		mappingEnglish.put("VHP", POS.POS_x);
		mappingEnglish.put("VHZ", POS.POS_x);	
		mappingEnglish.put("VV", POS.POS_x);
		mappingEnglish.put("VVD", POS.POS_x);
		mappingEnglish.put("VVG", POS.POS_x);
		mappingEnglish.put("VVN", POS.POS_x);
		mappingEnglish.put("VVP", POS.POS_x);
		mappingEnglish.put("VVZ", POS.POS_x);
		mappingEnglish.put("WDT", POS.POS_x);
		mappingEnglish.put("WP", POS.POS_x);
		mappingEnglish.put("WP$", POS.POS_x);
		mappingEnglish.put("WRB", POS.POS_x);
		
		mappingEnglish.put("#", POS.PT_x);
		mappingEnglish.put("$", POS.PT_x);	
		mappingEnglish.put("\"", POS.PT_x);
		mappingEnglish.put("(", POS.PT_x);
		mappingEnglish.put(")", POS.PT_x);
		mappingEnglish.put(",", POS.PT_x);
		mappingEnglish.put(":", POS.PT_x);

	}
	
	/**
	 * mapping of a tag in string (comming from treetagger) on a POS object
	 * @param tag
	 * @param l
	 * @return
	 */
	private static POS map (String tag, MWLanguage l) {
		if (l==MWLanguage.BG || l==MWLanguage.DE || l==MWLanguage.PT || l==MWLanguage.ES) {
			if (tag.startsWith("N")) return POS.POS_NNx;
			if (tag.startsWith("A")) return POS.POS_JJx;
		}
		if (l==MWLanguage.EN) {
			return mappingEnglish.get(tag);
		}
		return null;
	}

	/**
	 * creates an empty cluster 
	 */
	public Cluster(final MWLanguage l) {
		this.label = new Label("",l);
		this.tags = new ArrayList<Tag>(); 
	}
	
	/**
	 * creates an empty cluster 
	 */
	public Cluster(Cluster c) {
		this(c.label.getLanguage());
		assert c!=null;
		assert c.label!=null;
		assert c.tags!=null;
		this.label = c.label;
		this.tags.addAll(c.tags);
	}
	
	/**
	 * takes string "s", tags it by tree-tagger and stores results in the form of cluster 
	 * @param s
	 * @throws IOException
	 * @throws TreeTaggerException
	 */
	public Cluster(final Label l) throws IOException, TreeTaggerException {
		this.label = l;
		final String [] words = l.getLabel().split(" ");
		this.tags = getTT().tag(words);
	}

	/**
	 * takes array of strings "s", tags it by tree-tagger and stores results in the form of cluster
	 * @param s
	 * @throws IOException
	 * @throws TreeTaggerException
	 */
	public Cluster(final String[] s, MWLanguage lang) throws IOException, TreeTaggerException {
		assert s!=null;
		assert lang!=null;
		this.label = new Label(StringTools.implode(s, " "),lang);
		this.tags = getTT().tag(s);
	}

	/**
	 * creates a cluster of a collections of tags
	 * @param tags
	 */
	public Cluster(final Collection<Tag> tags, MWLanguage lang) {
		this(lang);
		this.tags.addAll(tags);
	}
	
	// ---
	
	/**
	 * provides the object with a tree-tagger object,
	 * reuses last instance in case it is called repeatedly
	 */
	protected TreeTagger getTT() throws IOException {
		final int langID = label.getLanguage().ordinal(); 
		if (tt[langID]==null) {
			tt[langID]=new TreeTagger(this.label.getLanguage());
		}
		return tt[langID];
	}
	
	// ---
	
	/**
	 * adds tag to the end of cluster
	 */
	public Cluster addTag(final Tag t) {
		this.tags.add(t);
		return this;
	}
	
	/**
	 * returns list of tags in the cluster
	 * @return
	 */
	public List<Tag> getTags() {
		return Collections.unmodifiableList(this.tags);
	}
	
	/**
	 * provides flag whether the cluster is empty
	 * @return
	 */
	public boolean isEmpty () {
		return this.tags.isEmpty();
	}
	
	/**
	 * converts tokens of the tags to a string
	 * @return
	 */
	public String tokensToString() {
		String s = null;
		for (Tag tag:this.tags) {
			if (s==null) s=tag.getToken();
			else s+=" "+tag.getToken();
		}
		return s;
	}

	/**
	 * converts lemmas of the tags to a string
	 * @return
	 */
	public String lemmasToString() {
		String s = null;
		for (Tag tag:this.tags) {
			if (s==null) s=tag.getLemma();
			else s+=" "+tag.getLemma();
		}
		return s;
	}
	
	/**
	 * converts cluster to a string form
	 */
	public String toString() {
		String s = "";
	    for (Tag tag:this.tags) {
	    	s+=(tag.getToken()+"("+tag.getPos()+")");
	    }
	    return s;
	}
	
	/**
	 * provides a hash code, useful when clusters are put to a set
	 */
	public int hashCode() {
		return this.tokensToString().hashCode();
	}
	
	/**
	 * provides a flag whether cluster is equal to another object
	 */
	public boolean equals(final Object o) {
		if (!(o instanceof Cluster)) return false;
		final Cluster c = (Cluster)o;
		return c.tokensToString().compareToIgnoreCase(this.tokensToString())==0;
	}

	/**
	 * returns a sub-cluster of tags of specified length starting on specified index
	 * only cluster containing specified POSes is taken into account, in case it
	 * contains another POSes, null is returned  
	 * @param index
	 * @param length
	 * @param poses
	 * @return
	 */
	public Cluster getSubCluster(final int index, final int length, final String[] poses) {
		assert index < this.tags.size();
		assert index+length <= this.tags.size();
		
		final Cluster c = new Cluster(this.label.getLanguage());
		for (int i=0;i<length;i++) {
			final Tag t = this.tags.get(index+i);
			
			boolean accept = false;
			for (String pos:poses) accept |= t.getPos().startsWith(pos);  
			if (!accept) return null;
			
			c.addTag(t);
		}
		return c;
	}

	/**
	 * returns a sub-cluster of tags of specified length starting on specified index
	 * @param index
	 * @param length
	 * @return
	 */
	public Cluster getSubCluster(final int index, final int length) {
		return getSubCluster(index,length,new String[] {""});
	}
	
	/**
	 * returns all sub-clusters formed of a specified POSes
	 * @param poses
	 * @return
	 */
	public Set<Cluster> allSubClusters(final String[] poses) {
		final Set<Cluster> toReturn = new HashSet<Cluster>();
		final int size = this.tags.size();
		for (int i=0;i<size;i++) {
			for (int len=1;len+i<=size;len++) {
				final Cluster c = this.getSubCluster(i, len, poses);
				if (c!=null) toReturn.add(c);
			}
		}
		return toReturn;
	}

	/**
	 * returns core
	 * @param poses
	 * @return
	 */
	/*
	public Set<Cluster> getCore() {
		final String[] acceptable = new String[] {
			"JJ.*", "NN.*", "NP.*", "VV.*", "CD.*", "DT.*", "PP.*", "TO", "VBG"
		};
		final Set<Cluster> toReturn = new HashSet<Cluster>();
		if (countPOS("NN")==1) {
			toReturn.addAll(this.allSubClusters(acceptable));
			assert toReturn.size() == 1;
		}
		if (countPOS("NN")>1) {
			toReturn.addAll(this.allSubClusters(acceptable));
		}
		return toReturn;
	}
	*/

	/**
	 * returns all subclusters
	 * @return
	 */
	public Set<Cluster> allSubClusters() {
		return allSubClusters(new String[] {""});
	}

	/**
	 * counts number of occurrences of certain POS in the cluster
	 * @param pos
	 * @return
	 */
	public int countPOS(final String pos) {
		int count = 0;
		for (Tag t:tags) {
			if(t.getPos().startsWith(pos)) count++; 
		}
		return count;
	} 

	/**
	 * counts number of occurrences of certain POS in the cluster
	 * @param pos
	 * @return
	 */
	public int countPOSes(final String[] poses) {
		int count = 0;
		for (Tag t:tags) {
			for (String pos : poses) {
				if(t.getPos().matches(pos)) {
					count++;
					break;
				}
			}
		}
		return count;
	} 

	/**
	 * returns a flag whether there is an occurrence of certain POS in the cluster
	 * @param pos
	 * @return
	 */
	public boolean containsPOS(final String pos) {
		for (Tag t:tags) {
			if(t.getPos().matches(pos)) return true; 
		}
		return false;
	} 
	
	/**
	 * produces a spectrum of occurrences
	 * @return
	 */
	public Spectrum getSpectrum() {
		assert this.label!=null;
		final Spectrum spec = new Spectrum();
		
		for (Tag t:tags) {
			final MWLanguage lang = this.label.getLanguage();
			final POS p = map(t.getPos(),lang);//mappingEnglish.get(t.getPos());
			spec.inc(p);
		}
		return spec;
	}
	
	/**
	 * produces a string
	 */
	public String implode() {
		String s="";
		for (Tag t:this.tags) {
			s+=(s.length()==0?"":" ")+t.getLemma();
		}
		return s;
	}
}
