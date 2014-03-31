package eu.dynalearn.st.context;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.snowball.SnowballFilter;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
//import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.junit.Assert;

import eu.dynalearn.st.config.Configuration;
import eu.dynalearn.st.grounder.GroundingRelevance;
import eu.dynalearn.st.mw.MWLanguage;
import eu.dynalearn.util.StringTools;

/**
 * 
 * Application of Apache Lucene for ranking according to context.
 * http://lucene.apache.org/
 * 
 * Available stemmers:
 * http://lucene.apache.org/java/3_0_2/api/all/org/tartarus/snowball/ext/package-summary.html
 * 
 * Lists of stop-words:
 * http://www.semantikoz.com/2008/04/02/free-stop-word-lists-in-23-languages/
 * 
 * @author mtrna
 *
 */

public class LuceneRanking 
{
	
	private static Analyzer analyzerDe = null;
	private static Analyzer analyzerEn = null;
	private static Analyzer analyzerEs = null;
	private static Analyzer analyzerPt = null;
	
	private static final String[] MEDIA_WORDS_DE = { };
	private static final String[] MEDIA_WORDS_EN = {
		// media:
		"actor", "actress", "writer", "movie", 
		"polititian", "TV", "television", "comics", "broadcast",
		"baseball", "football", "basketball", "soccer", 
		"singer", "dancer", "comedian", "musical", "comedy",
		"starring", "novel", "punk",
		// army/navy:
		"HMS", "USS", "HNLMS",
		// not suitable::
		//"show", "sport", "radio", "film", "rock"
	};
	private static final String[] MEDIA_WORDS_ES = { };
	private static final String[] MEDIA_WORDS_PT = { };

	private static final Set<String> STOP_WORDS_SET_DE = new HashSet<String>();
	private static final Set<String> STOP_WORDS_SET_EN = new HashSet<String>();
	private static final Set<String> STOP_WORDS_SET_ES = new HashSet<String>();
	private static final Set<String> STOP_WORDS_SET_PT = new HashSet<String>();
	
	static 
	{
//		STOP_WORDS_SET_DE.addAll(StringTools.fileInCollection("data/stopwords/de.txt"));
//		STOP_WORDS_SET_EN.addAll(StringTools.fileInCollection("data/stopwords/en.txt"));
//		STOP_WORDS_SET_ES.addAll(StringTools.fileInCollection("data/stopwords/es.txt"));
//		STOP_WORDS_SET_PT.addAll(StringTools.fileInCollection("data/stopwords/pt.txt"));
		STOP_WORDS_SET_DE.addAll(StringTools.fileInCollection(Configuration.STOP_WORDS_SET_DE));
		STOP_WORDS_SET_EN.addAll(StringTools.fileInCollection(Configuration.STOP_WORDS_SET_EN));
		STOP_WORDS_SET_ES.addAll(StringTools.fileInCollection(Configuration.STOP_WORDS_SET_ES));
		STOP_WORDS_SET_PT.addAll(StringTools.fileInCollection(Configuration.STOP_WORDS_SET_PT));
	}

	// names of fields in the constructed documents
	private static final String FIELD_URI = "URI";
	private static final String FIELD_DESC = "DESC";
	private static final String FIELD_LABEL = "LABEL";
	
	// weights of fields
	private static final float WEIGHT_LABEL = 0.6f;
	private static final float WEIGHT_DESC = 0.4f;
	
    private static final Map<String,Float> mapBoosts = new HashMap<String,Float>();
    
    static 
    {
	    mapBoosts.put(FIELD_LABEL, new Float(WEIGHT_LABEL));
	    mapBoosts.put(FIELD_DESC, new Float(WEIGHT_DESC));
    }
	
	public LuceneRanking() 
	{
	}
	
	private static Analyzer createGermanAnalyzer() 
	{
		if (analyzerDe!=null) return analyzerDe;
		analyzerDe = new Analyzer() {
			@Override public TokenStream tokenStream(String fieldName, Reader reader)
			{
				TokenStream result = new StandardTokenizer(Version.LUCENE_30, reader);
				result = new StandardFilter(result);
				result = new LowerCaseFilter(result);
				result = new StopFilter(false, result, STOP_WORDS_SET_DE);
				result = new SnowballFilter(result, "German");
				return result;
			}
		};
		return analyzerDe;
	}

	private static Analyzer createEnglishAnalyzer() 
	{
		if (analyzerEn!=null) return analyzerEn;
		
		analyzerEn = new Analyzer() 
		{
			@Override public TokenStream tokenStream(String fieldName, Reader reader)
			{
				TokenStream result = new StandardTokenizer(Version.LUCENE_30, reader);
				result = new StandardFilter(result);
				result = new LowerCaseFilter(result);
				result = new StopFilter(false, result, STOP_WORDS_SET_EN);
				result = new SnowballFilter(result, "English");
				return result;
			}
		};
		return analyzerEn;
	}

	private static Analyzer createPortugueseAnalyzer() 
	{
		if (analyzerPt!=null) return analyzerPt;
		
		analyzerPt = new Analyzer() 
		{
			@Override public TokenStream tokenStream(String fieldName, Reader reader)
			{
				TokenStream result = new StandardTokenizer(Version.LUCENE_30, reader);
				result = new StandardFilter(result);
				result = new LowerCaseFilter(result);
				result = new StopFilter(false, result, STOP_WORDS_SET_PT);
				result = new SnowballFilter(result, "Portuguese");
				return result;
			}
		};
		return analyzerPt;
	}

	private static Analyzer createSpanishAnalyzer() 
	{
		if (analyzerEs!=null) return analyzerEs;
		analyzerEs = new Analyzer() 
		{
			@Override public TokenStream tokenStream(String fieldName, Reader reader)
			{
				TokenStream result = new StandardTokenizer(Version.LUCENE_30, reader);
				result = new StandardFilter(result);
				result = new LowerCaseFilter(result);
				result = new StopFilter(false, result, STOP_WORDS_SET_ES);
				result = new SnowballFilter(result, "Spanish");
				return result;
			}
		};
		return analyzerEs;
	}

	private static Analyzer createAnalyzer(final MWLanguage lang) 
	{
		if (lang==MWLanguage.DE) return createGermanAnalyzer();
		if (lang==MWLanguage.EN) return createEnglishAnalyzer();
		if (lang==MWLanguage.ES) return createSpanishAnalyzer();
		if (lang==MWLanguage.PT) return createPortugueseAnalyzer();
	    else return null;
	}
	
	/**
	 * harvest context from "model" and assign media ranks to all the grounding proposals "docs"
	 * run AFTER createContext is called
	 * @param model
	 * @param docs
	 * @param lang
	 * @return
	 * @throws CorruptIndexException
	 * @throws LockObtainFailedException
	 * @throws IOException
	 * @throws ParseException
	 */
	public Collection<GroundingRelevance> rankMedia(final List<String> context, Collection<GroundingRelevance> docs, MWLanguage lang) throws ParseException, IOException {
		
//		final List<String> q = new ArrayList<String>();
//		
//		for (Term term: model.getTerms().values()) {
//			for (String i:QRModel.Ingredients.INGREDIENTS_GROUNDABLE) {
//				if (term.getType().equalsIgnoreCase(i)) {
//					q.add(term.getLabel());
//				}
//			}
//		}
		return doRankMedia(StringTools.implode(context.toArray(new String[0]), ", "), docs);
	}

	/**
	 * harvest context from the grounding proposals "docs" and assign media rankings
	 * run AFTER createContext is called
	 * @param docs
	 * @param lang
	 * @return
	 * @throws CorruptIndexException
	 * @throws LockObtainFailedException
	 * @throws IOException
	 * @throws ParseException
	 */
	public Collection<GroundingRelevance> rankMedia(Collection<GroundingRelevance> docs, final MWLanguage lang) throws ParseException, IOException {
		final String[] MEDIA_WORDS;
		if (lang==MWLanguage.DE) MEDIA_WORDS = MEDIA_WORDS_DE;
		else if (lang==MWLanguage.EN) MEDIA_WORDS = MEDIA_WORDS_EN;
		else if (lang==MWLanguage.ES) MEDIA_WORDS = MEDIA_WORDS_ES;
		else if (lang==MWLanguage.PT) MEDIA_WORDS = MEDIA_WORDS_PT;
		else MEDIA_WORDS = new String[]{};
		return doRankMedia(StringTools.implode(MEDIA_WORDS, ","),docs);
	}
	
	/**
	 * harvest context from "model" and assign lucene ranks to all the grounding proposals "docs"
	 * run AFTER createContext is called
	 * @param model
	 * @param docs
	 * @return
	 * @throws CorruptIndexException
	 * @throws LockObtainFailedException
	 * @throws IOException
	 * @throws ParseException
	 */
	public Collection<GroundingRelevance> rank(final List<String> context, Collection<GroundingRelevance> docs) throws ParseException, IOException {
//		final List<String> q = new ArrayList<String>();
//		
//		for (Term term:model.getTerms().values()) {
//			for (String i:QRModel.Ingredients.INGREDIENTS_GROUNDABLE) {
//				if (term.getType().equalsIgnoreCase(i)) 
//					q.add(term.getLabel());
//			}
//		}
		return doRankLucene(StringTools.implode(context.toArray(new String[0]), ", "), docs);
	}

	/**
	 * harvest context from "model" and assign lucene ranks to all the grounding proposals "docs"
	 * run AFTER createContext is called
	 * @param query
	 * @param docs
	 * @return
	 * @throws CorruptIndexException
	 * @throws LockObtainFailedException
	 * @throws IOException
	 * @throws ParseException
	 */
	public Collection<GroundingRelevance> rankLucene(final String query, Collection<GroundingRelevance> docs) throws ParseException, IOException {
		return doRankLucene(query,docs);
	}
	
	private Analyzer analyzer = null;
    private Directory directory = new RAMDirectory();
	private IndexSearcher isearcher = null;
	
	/**
	 * create index of documents for later ranking
	 * @param docs
	 * @param lang
	 * @return
	 * @throws LockObtainFailedException 
	 * @throws IOException 
	 * @throws CorruptIndexException 
	 */
	public LuceneRanking createContext(Collection<GroundingRelevance> docs, final MWLanguage lang) throws CorruptIndexException, LockObtainFailedException, IOException {
		// create an analyzer, store the index in memory:
	    this.analyzer = createAnalyzer(lang);
	    //final Directory directory = new RAMDirectory();
	    final IndexWriter iwriter = new IndexWriter(directory, analyzer, new IndexWriter.MaxFieldLength(25000));

	    // load the documents to index
	    for (GroundingRelevance pg:docs) {
//	    	String label=null;
//	    	String desc=null;
//	    	for(Iterator<String> it = pg.getGrounding().getLabels().iterator(); label == null && it.hasNext(); ) {
//	    		String l=it.next();
//	    		if(l.getLanguage().getDbpediaCode().equalsIgnoreCase(lang.toString())) label=l;
//	    	}
//	    	for(Iterator<String> it = pg.getGrounding().getDescriptions().iterator(); desc == null && it.hasNext(); ) {
//	    		Description d=it.next();
//	    		if(d.getLanguage().getDbpediaCode().equalsIgnoreCase(lang.toString())) desc=d;
//	    	}
	    	
	    	String label = pg.getGrounding().getLabel();
	    	String descr = pg.getGrounding().getDescription();
	    	
	    	Document doc = new Document();
	    	doc.add(new Field(FIELD_URI, pg.getGrounding().getURI(), Field.Store.YES, Field.Index.ANALYZED));
	    	if(label != null)
	    		doc.add(new Field(FIELD_LABEL, label, Field.Store.YES, Field.Index.ANALYZED));
	    	if(descr != null)
	    		doc.add(new Field(FIELD_DESC, descr, Field.Store.YES, Field.Index.ANALYZED));
	    	iwriter.addDocument(doc);
	    }
	    
	    iwriter.close();
	    this.isearcher = new IndexSearcher(directory);
		return this;
	}
	
	private Collection<GroundingRelevance> doRankLucene(final String q, Collection<GroundingRelevance> docs) throws ParseException, IOException {
		Assert.assertNotNull(this.analyzer);
		Assert.assertNotNull(this.directory);
		Assert.assertNotNull(this.isearcher);
		
		Map<String, GroundingRelevance> map_docs=new LinkedHashMap<String, GroundingRelevance>();
		for(GroundingRelevance gr: docs)
			map_docs.put(gr.getGrounding().getURI(), gr);
	    
	    // now search the index:
	    final MultiFieldQueryParser parser = new MultiFieldQueryParser(
	    		Version.LUCENE_30, mapBoosts.keySet().toArray(new String[0]),
	    		analyzer, null);
	    final Query query = parser.parse(q);
	    final ScoreDoc[] hits = isearcher.search(query, null, 1000).scoreDocs;
	    
	    // assign new scores
	    for (ScoreDoc hit:hits) {
	    	final Document hitDoc = isearcher.doc(hit.doc);
	    	GroundingRelevance gr=map_docs.get(hitDoc.get(FIELD_URI));
	    	if (gr!=null) gr.setLucene(hit.score);
	    }
	    
		return docs;
	}

	private Collection<GroundingRelevance> doRankMedia(final String q, Collection<GroundingRelevance> docs) throws ParseException, IOException {
		Assert.assertNotNull(this.analyzer);
		Assert.assertNotNull(this.directory);
		Assert.assertNotNull(this.isearcher);
		
		Map<String, GroundingRelevance> map_docs=new LinkedHashMap<String, GroundingRelevance>();
		for(GroundingRelevance gr: docs)
			map_docs.put(gr.getGrounding().getURI(), gr);
	    
	    // now search the index:
	    final MultiFieldQueryParser parser = new MultiFieldQueryParser(
	    		Version.LUCENE_30, mapBoosts.keySet().toArray(new String[0]),
	    		analyzer, null);
	    final Query query = parser.parse(StringTools.implode(MEDIA_WORDS_EN," "));
	    final ScoreDoc[] hits = isearcher.search(query, null, 1000).scoreDocs;
	    
	    // assign new scores
	    for (ScoreDoc hit:hits) {
	    	final Document hitDoc = isearcher.doc(hit.doc);
	    	GroundingRelevance gr=map_docs.get(hitDoc.get(FIELD_URI));
	    	if (gr!=null) gr.setMedia(hit.score);
	    }
	    
		return docs;
	}
	
	protected void finalize() throws Throwable 
	{
	    isearcher.close();
	    directory.close();
	    super.finalize();
	} 
}
