package eu.dynalearn.st.ext.treetagger;

import java.io.IOException;
import java.util.*;
import org.annolab.tt4j.*;

import eu.dynalearn.st.config.Configuration;
import eu.dynalearn.st.mw.MWLanguage;

/**
 * encapsulates treetagger
 * @author mtrna
 *
 */
public class TreeTagger {

	/**
	 * handler for tags
	 * @author mtrna
	 *
	 */
	protected class MyTokenHandler implements TokenHandler<String> {
		private final List<Tag> els = new ArrayList<Tag>();
		@Override public void token(String token, String pos, String lemma) {
			els.add(new Tag(token, pos, lemma));
	    }
		public MyTokenHandler clear() {
			els.removeAll(getElements());
			return this;
		}
		public List<Tag> getElements() {
			return Collections.unmodifiableList(els);
		}
	};

	//public final static String [] POSES_NP = new String[] {"NN","NNS","NP","NPS"};
	//public final static String [] POSES_CIN = new String[] {"CC", "IN"};
	//public final static String [] All = new String[] {};

	/**
	 * important tags for English - for Monet
	 */
	public final static String [] EN_POSES_JNPV = new String[] {"NN","NNS","NP","NPS","JJ","JJS","VVD","VVG","VVN"};
	/**
	 * important tags for English - for Monet
	 */
	public final static String [] EN_POSES_JNPVIC = new String[] {"NN","NNS","NP","NPS","JJ","JJS","VVD","VVG","VVN","IN","CC"};

	
	/**
	 * important tags for English
	 */
	public final static String [] EN_POSES_JNP = new String[] {"NN","NNS","NP","NPS","JJ","JJS"};
	/**
	 * important tags for Spanish
	 */
	public final static String [] ES_POSES_JNP = new String[] {"ADJ","NC","NP","NMEA"};
	/**
	 * important tags for Portuguese
	 */
	public final static String [] PT_POSES_JNP = new String[] {"NOM","ADJ"};
	/**
	 * important tags for German
	 */
	public final static String [] DE_POSES_JNP = new String[] {"NN","NE","ADJA","ADJD"};
	/**
	 * important tags for Bulgarian
	 */
	public final static String [] BG_POSES_JNP = new String[] {"N","A"};

	/**
	 * get set of imporant tags for a given language 
	 * @param l
	 * @return
	 * @throws Exception
	 */
	public static String[] getJNPTagSet ( final MWLanguage l) throws Exception {
		if (l==MWLanguage.BG) {
			return TreeTagger.BG_POSES_JNP;
		}
		else if (l==MWLanguage.EN) {
			return TreeTagger.EN_POSES_JNP;
		}
		else if (l==MWLanguage.ES) {
			return TreeTagger.ES_POSES_JNP;
		}
		else if (l==MWLanguage.DE) {
			return TreeTagger.DE_POSES_JNP;
		}
		else if (l==MWLanguage.PT) {
			return TreeTagger.PT_POSES_JNP;
		} else {
			throw new Exception("language not implemented : "+l.toString());
		}
	}
	
	/**
	 * wrapper around treetagger
	 */
	protected final TreeTaggerWrapper<String> tt;
	/**
	 * token handler, receiving tags
	 */
	protected final MyTokenHandler th; 
	/**
	 * language
	 */
	protected final MWLanguage lang;
    
	/**
	 * creates a treetagger instance
	 * @throws IOException
	 */
    public TreeTagger(MWLanguage lang) throws IOException {

    	System.setProperty("treetagger.home",Configuration.TREETAGGER_HOME);

		this.th = new MyTokenHandler();

		this.tt = new TreeTaggerWrapper<String>();
		this.tt.setModel(this.getModel(lang));
		this.tt.setHandler(th);
		
		this.lang = lang;
    }
    
    // --- model
    
    /**
     * returns model (language) setting for a given language
     */
    protected String getModel(MWLanguage lang) {
    	
    	String model = null;
    	
    	if (lang == MWLanguage.EN) model = Configuration.TREETAGGER_MODEL_EN;
    	if (lang == MWLanguage.ES) model =  Configuration.TREETAGGER_MODEL_ES;
    	
    	//System.out.println(">>>>>> "+model+" <<<<<<");
    	return model;
    }

	// --- tag

    /**
     * tag a collection of words
     */
	public List<Tag> tag(Collection<String> c) throws IOException, TreeTaggerException {
		th.clear();
	    tt.process(c);
		return th.getElements();
	}
	
	/**
	 * tag an array of words
	 * @param c
	 * @return
	 * @throws IOException
	 * @throws TreeTaggerException
	 */
	public List<Tag> tag(String[] c) throws IOException, TreeTaggerException {
		return tag(Arrays.asList(c));
	}
	
	/**
	 * tag a string
	 * @param s
	 * @return
	 * @throws IOException
	 * @throws TreeTaggerException
	 */
	public List<Tag> tag(String s) throws IOException, TreeTaggerException {
		return tag(s.split(" "));
	}
	
	// --- extract largest clusters
	
	/**
	 * extract clusters formed of certain POSes from a collection of words  
	 */
	public Cluster[] extractClusters(Collection<String> c, String[] POSes, MWLanguage lang) throws IOException, TreeTaggerException {
		List<Cluster> clusters = new ArrayList<Cluster>();
		
		Cluster cluster = new Cluster(this.lang);
		final List<Tag> tags = tag(c); 
		for (Tag t:tags) {
			boolean isvalid = false;
			for (String pos:POSes) {
				isvalid |= t.getPos().equalsIgnoreCase(pos);
			}
			if (isvalid) {
				cluster.addTag(t);
			} else {
				if (cluster.isEmpty()) ; // nothing
				else {
					clusters.add(cluster);
					cluster = new Cluster(this.lang);
				}
			}
		}
		if (!cluster.isEmpty()) clusters.add(cluster);
		
		return clusters.toArray(new Cluster[0]);
	}
	
	/**
	 * extract clusters formed of certain POSes from an array of words  
	 * @param c
	 * @param POSes
	 * @return
	 * @throws IOException
	 * @throws TreeTaggerException
	 */
	public Cluster[] extractClusters(String[] c, String[] POSes) throws IOException, TreeTaggerException {
		return extractClusters(Arrays.asList(c),POSes,this.lang);
	}

	/**
	 * extract clusters formed of certain POSes from a general string of words  
	 * @param s
	 * @param POSes
	 * @return
	 * @throws IOException
	 * @throws TreeTaggerException
	 */
	public Cluster[] extractClusters(String s, String[] POSes) throws IOException, TreeTaggerException {
		return extractClusters(s.split(" "),POSes);
	}

	// --- extract specified subclusters
	
	/**
	 * extract all subclusters former of certain POSes from collection of words  
	 */
	public Cluster[] extractSubclusters(Collection<String> c, String[] POSes) throws IOException, TreeTaggerException {
		final Set<Cluster> setOfClusters = new HashSet<Cluster>();
				
		// add all adjective+noun
		for (Cluster cl:extractClusters(c,POSes,this.lang)) {
			setOfClusters.add(cl);
		}
		
		// break up long clusters
		final Set<Cluster> shortClusters = new HashSet<Cluster>();
		for (Cluster cluster:setOfClusters) {
			shortClusters.addAll(cluster.allSubClusters());
		}
		setOfClusters.addAll(shortClusters);
		
		
		// find and delete the original cluster
		// note: it is faster to search by size
		Set<Cluster> setOfJunk = new HashSet<Cluster>();
		for (Cluster cluster:setOfClusters) {
			if (cluster.getTags().size()==c.size()) {
				setOfJunk.add(cluster);
			}
		}
		setOfClusters.removeAll(setOfJunk);
		
		return setOfClusters.toArray(new Cluster[0]);
	}
	
	/**
	 * extract clusters former of certain POSes from an array of words  
	 * @param c
	 * @param POSes
	 * @return
	 * @throws IOException
	 * @throws TreeTaggerException
	 */
	public Cluster[] extractSubclusters(String[] c, String[] POSes) throws IOException, TreeTaggerException {
		return extractSubclusters(Arrays.asList(c), POSes);
	}
	
	/**
	 * extract clusters former of certain POSes from a string of words  
	 * @param s
	 * @param POSes
	 * @return
	 * @throws IOException
	 * @throws TreeTaggerException
	 */
	public Cluster[] extractSubclusters(String s, String[] POSes) throws IOException, TreeTaggerException {
		return extractSubclusters(s.split(" "), POSes);
	}
	
	// --- extract NNJJ subclusters

	/**
	 * extract clusters former of adjective and nouns from collection of words  
	 */
	public Cluster[] extractSubclusters(Collection<String> c) throws IOException, TreeTaggerException {
		return extractSubclusters(c,EN_POSES_JNP);
	}
	
	/**
	 * extract clusters former of adjective and nouns from an array of words  
	 */
	public Cluster[] extractSubclusters(String[] c) throws IOException, TreeTaggerException {
		return extractSubclusters(Arrays.asList(c));
	}
	
	/**
	 * extract clusters former of adjective and nouns from a string of words  
	 */
	public Cluster[] extractSubclusters(String s) throws IOException, TreeTaggerException {
		return extractSubclusters(s.split(" "));
	}
	
	// --- finalizer
	
	/**
	 * destroy object correctly
	 */
	protected void finalize() throws Throwable {
	    tt.destroy();
	}
}
