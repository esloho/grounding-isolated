package eu.dynalearn.st.grounder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.store.LockObtainFailedException;

import eu.dynalearn.model.GroundingTerm;
import eu.dynalearn.st.grounder.GroundingRelevance.GroundingRelevanceComparator;
import eu.dynalearn.st.context.InhouseRanking;
import eu.dynalearn.st.context.LuceneRanking;
import eu.dynalearn.st.dbpedia.DBpedia;
import eu.dynalearn.st.dbpedia.ResultsParser;
import eu.dynalearn.st.ext.google.GoogleSpellingSuggestions;
import eu.dynalearn.st.ext.google.SpellingSuggestions;
import eu.dynalearn.st.ext.wordnet.WordNetSearch;
import eu.dynalearn.util.StringTools;

public class EnglishGrounder extends Grounder 
{
	/**
	 * Maximum number of grounding options per term
	 */
	final static int _LIMITED_NUMBER = 10;
	
	public EnglishGrounder() {
		super("en");
//		Assert.assertTrue(lang.getDbpediaCode().equalsIgnoreCase("en"));
	}
	
	/**
	 * Check whether or not a given word is in a list of words.
	 * @param listStr a list of words
	 * @param word a given word
	 * @return true if a given word is in the list, and false if not.
	 */
	private boolean contains(Collection<String> listStr, String word) 
	{
		for (String str:listStr) 
			if (str.compareToIgnoreCase(word) == 0) return true;
		return false;
	}
	
	/**
	 * Return the stems of a given word.
	 * @param word a given word
	 * @return the list of stems.
	 * @throws GrounderException 
	 * @throws MalformedURLException 
	 */
	private Vector<String> getStems(String word) throws GrounderException {
		try {
			WordNetSearch wns;
			Vector<String> stems = new Vector<String>();

			wns = new WordNetSearch();
			stems = wns.getStemmer(word);			
			int size = stems.size();
			int i = 0;

			while (i < size) 
			{
				String str = stems.get(i);
				str = str.replaceAll("_", " ");

				if (str.compareToIgnoreCase(word) == 0) 
				{
					stems.remove(i);
					size = size - 1;
				}
				else 
				{
					stems.set(i, str);
					i++;
				}
			}
			for (i = 0; i < stems.size(); i ++) 
			{
				String str = stems.get(i);
				//str = str.replaceAll(" ", "%20");
				stems.set(i, str);
			}

			return stems;
		}
		catch(MalformedURLException e) {
			throw new GrounderException(e);
		}
	}
	
	/**
	 * Check mis-spelling of a list of words and return a list of suggestions for mis-spelling words.
	 * Each suggestion is encoded by encoding percentage.
	 * @param stems a input list of words
	 * @return a list of suggestions for mis-spelling words.
	 */
	private List<String> checkSpelling(List<String> stems) 
	{
		SpellingSuggestions termExtractor = new GoogleSpellingSuggestions("en");
		List<String> suggestions = new ArrayList<String>();
		
		for (int i = 0; i < stems.size(); i ++) 
		{
			termExtractor.extraction(stems.get(i));
			if (termExtractor.getResult() != null) {
				suggestions = addAllStrings(suggestions, termExtractor.getResult());
			}
		}
		return suggestions;
	}
	
	/**
	 * Returns the grounding results for the given term.
	 * @param term
	 * 			Label of the term to be grounded.
	 * @return A list of grounding results.
	 * @throws GrounderException 
	 */
	protected List<GroundingTerm> searching(String term) throws GrounderException 
	{
		try
		{
			ResultsParser rp = DBpedia.lookup(term);
			List<GroundingTerm> rl = rp.getResults();
			
			if (rl.size() > _LIMITED_NUMBER)
				rl = filterResults(rl, term);
			
			return rl;
		}catch(Exception ex)
		{
			//DIC 20120413: When there is problems with "DBpedia look up service" a search in label_en table is performed
//			return searchingInLocalLanguageCache(sr, term);
			return new ArrayList<GroundingTerm>();
		}
	}
	
	/**
	 * Add a sublist of Strings in a main list of Strings
	 * @param mainlist a main list
	 * @param sublist a sub list
	 * @return a main list after adding
	 */
	private List<String> addAllStrings(final List<String> mainlist, final Collection<String> sublist) 
	{
		for (String str : sublist)
		{
			if (!contains(mainlist, str)) 
			{
				mainlist.add(str);
			}
		}
		return mainlist;
	}
	
	/**
	 * Check whether or not label or redirect contains one of the word in the word list.
	 * @param label a give label
	 * @param description description of a given label
	 * @param source a give redirect
	 * @param stems a list of words
	 * @return true if label or redirect contains one of the words, false if not.
	 */
	private boolean isValuable(String label, String description, String word) 
	{
		boolean value = false;
		
		if (StringUtils.containsIgnoreCase(label, word)) {
			if (description.length() > 0) 
				value = true;
		}
//		else if (StringUtils.equalsIgnoreCase(source, word))
//				value = true;
		return value;
	}
	
	
	/**
	 * Filter out the grounding results which do not contain any word in the list of input words
	 * @param listResults list of grounding results
	 * @param stems the input words
	 * @return list of grounding results after filtering
	 */
	private List<GroundingTerm> filterResults(Collection<GroundingTerm> listResults, String word) 
	{
		List<GroundingTerm> lsResult = new ArrayList<GroundingTerm>();
		for (GroundingTerm aResult : listResults) {
			final String description = aResult.getDescription();
			final String label = aResult.getLabel();
			
			if (isValuable(label, description, word)) {
				lsResult.add(aResult);
			}
		}
		return lsResult;
	}

	/**
	 * 
	 * @param sr
	 * 			Current instance of the semantic repository
	 * @param results
	 * 			Term to be grounded
	 * @param cacheResults
	 * 			Indicates if previous groundings have been found in the cache
	 * @return Term with new grounding options
	 * @throws MalformedURLException
	 * @throws UnsupportedEncodingException
	 * @throws SQLException
	 */
	private GroundingResults process(GroundingResults results, String searchTerm, String lang) throws GrounderException {
		String term = results.getTerm();

		List <String> stems = getStems(term);
		List <String> tocheck = new ArrayList<String>(stems);
		results.setStems(stems);
		
		// Code modified for splitting functionality
		//If term has no stems, 
		//use Yahoo service for checking spelling and getting possible groundings for each suggestion
//		if (!results.hasStems()) 
//		{
			tocheck.add(term);
//			List<String> suggestions = checkSpelling(tocheck);
			List<String> suggestions = tocheck;
			
			for (String suggest : suggestions)
			{
				results.addSuggestion(suggest);
			}
//		}	
		
		return results;
	}
	
	/**
	 * 
	 * @param sr
	 * 			Current instance of the semantic repository
	 * @param results
	 * 			Term to be grounded
	 * @param cacheResults
	 * 			Indicates if previous groundings have been found in the cache
	 * @return Term with new grounding options
	 * @throws MalformedURLException
	 * @throws UnsupportedEncodingException
	 * @throws SQLException
	 */
	private GroundingResults processSuggestions(GroundingResults results, String searchTerm, String lang) throws GrounderException {
		String term = results.getTerm();
		List<String> stems = getStems(term);	
		results.setStems(stems);
	
		List<String> suggestions = new Vector<String>();
		List<String> trm = new Vector<String>();
		trm.add(term);
		suggestions.addAll(checkSpelling(trm));
		
		results.getSuggestions().addAll(suggestions);
		
		return results;
	}
	
	/**
	 * @param term
	 * 			Term to be grounded
	 * @return Term with grounding options.
	 * @throws UnsupportedEncodingException
	 * @throws SQLException
	 * @throws MalformedURLException
	 */
	public GroundingResults groundTerm(String term) throws GrounderException {
		
		term = normalize(term);
		
		GroundingResults results = new GroundingResults(term);
				
		Collection<GroundingTerm> grTerms = super.searchGroundings(term);
		if (grTerms!=null)
			for(GroundingTerm gt: grTerms)
				results.addPossibleGrounding(gt);
		
		//Possible groundings for stems and suggestions for the original term
		results = process(results, term, lang);

		groundCompoundWord(results, term);

		return results;
	}
	
	/**
	 * 
	 * @param term
	 * 			Label of the term to be grounded
	 * @return Term with grounding options.
	 * @throws UnsupportedEncodingException
	 */
	private GroundingResults groundSuggestedTerms (String term) throws GrounderException {
		term = super.normalize(term);
		
		//Include the original term in the results
		GroundingResults results = new GroundingResults(term);
		
		//Possible groundings for suggestions for the original term
		results = processSuggestions(results, term, lang);
		
		return results;
	}
	
	/**
	 * Returns the first grounding from the ranked list of groundings
	 * @param term
	 * @return
	 * @throws GrounderException
	 * @throws CorruptIndexException
	 * @throws LockObtainFailedException
	 * @throws IOException
	 * @throws ParseException
	 */
	public GroundingTerm getFirstGrounding(String label)
	{
		GroundingTerm grounding = null;
		
		GroundingResults results;
		try
		{
			results = groundTerm(label);
		
			results.setEvaluatedGroundings(doRanking(results,label));
			
			Iterator<GroundingRelevance> grs = results.getPossibleGroundings().iterator();
			
			if (grs.hasNext())
				grounding = grs.next().getGrounding();
		} 
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return grounding;
	}
	
	/**
	 * Return the first grounding from the list of groundings, ranked by taking into account the context words
	 * @param label
	 * @param context
	 * @return
	 * @throws GrounderException
	 * @throws CorruptIndexException
	 * @throws LockObtainFailedException
	 * @throws IOException
	 * @throws ParseException
	 */
	public GroundingTerm getFirstGrounding (String label, List<String> context)
	{		
		GroundingTerm grounding = null;
		
		GroundingResults results;
		try
		{
			results = groundTerm(label);
		
			if (context != null && !context.isEmpty() )
			{			
				final Set<String> contextSet = new HashSet<String>();
				
				for (String term:context) 
				{
					contextSet.add(term);
				}
				
				final String contextString = StringTools.implode(contextSet.toArray(new String[0]),", ");
				results.setEvaluatedGroundings(doRanking(results, contextString));
			}
			else
				results.setEvaluatedGroundings(this.doRanking(results,label));
			
			Iterator<GroundingRelevance> grs = results.getPossibleGroundings().iterator();
			
			if (grs.hasNext())
				grounding = grs.next().getGrounding();
		} 
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return grounding;
	}
	
	/**
	 * Returns the ranked list of groundings
	 * @param term
	 * @return
	 * @throws GrounderException
	 * @throws CorruptIndexException
	 * @throws LockObtainFailedException
	 * @throws IOException
	 * @throws ParseException
	 */
	public List<GroundingTerm> getGroundings(String term)
	{		
		List<GroundingTerm> groundings = new ArrayList<GroundingTerm>();
		
		GroundingResults results;
		try
		{
			results = groundTerm(term);
		
			results.setEvaluatedGroundings(doRanking(results,term));
			
			for (GroundingRelevance result:results.getPossibleGroundings()) {
				groundings.add(result.getGrounding());
			}
		} 
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return groundings;
	}
	
	/**
	 * Returns the ranked list of groundings
	 * @param term
	 * @return
	 * @throws GrounderException
	 * @throws CorruptIndexException
	 * @throws LockObtainFailedException
	 * @throws IOException
	 * @throws ParseException
	 */
	public List<GroundingTerm> getGroundingsWithSuggestions(String term) 
	{		
		List<GroundingTerm> groundings = new ArrayList<GroundingTerm>();
		
		GroundingResults results;
		
		try
		{
			results = groundSuggestedTerms(term);
		
			results.setEvaluatedGroundings(doRanking(results,term));
			
			for (GroundingRelevance result:results.getPossibleGroundings()) {
				groundings.add(result.getGrounding());
			}
		} 
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return groundings;
	}
	
	/**
	 * Return the list of groundings, ranked by taking into account the context words
	 * @param label
	 * @param context
	 * @return
	 * @throws GrounderException
	 * @throws CorruptIndexException
	 * @throws LockObtainFailedException
	 * @throws IOException
	 * @throws ParseException
	 */
	public List<GroundingTerm> getGroundings (String label, List<String> context)
	{		
		List<GroundingTerm> groundings = new ArrayList<GroundingTerm>();
		
		GroundingResults results;
		
		try
		{
			results = groundTerm(label);
		
		
			if (context != null && !context.isEmpty() )
			{
				final Set<String> contextSet = new HashSet<String>();
				
				for (String term:context) 
				{
					contextSet.add(term);
				}
				
				final String contextString = StringTools.implode(contextSet.toArray(new String[0]),", ");
				results.setEvaluatedGroundings(doRanking(results, contextString));
			}
			else
				results.setEvaluatedGroundings(this.doRanking(results, label));
			
			for (GroundingRelevance result:results.getPossibleGroundings()) {
				groundings.add(result.getGrounding());
			}
		} 
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return groundings;
	}
	
	protected List<GroundingRelevance> doRanking(final GroundingResults gr, final String contextSerialized) throws CorruptIndexException, LockObtainFailedException, IOException, ParseException 
	{
		List<GroundingRelevance> pg = new ArrayList<GroundingRelevance>(gr.getPossibleGroundings());
		
		final LuceneRanking rankingL = new LuceneRanking();
		rankingL.createContext(pg, eu.dynalearn.st.mw.MWLanguage.EN);
		rankingL.rankLucene(contextSerialized.toLowerCase(), pg);
		rankingL.rankMedia(pg, eu.dynalearn.st.mw.MWLanguage.EN);
		InhouseRanking rankingIH = new InhouseRanking();
		rankingIH.rank(gr.getTerm(), pg, "en");

		Collections.sort(pg, new GroundingRelevanceComparator());

		return pg;
	}
}
