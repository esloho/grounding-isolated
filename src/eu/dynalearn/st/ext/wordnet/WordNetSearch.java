package eu.dynalearn.st.ext.wordnet;



import java.net.MalformedURLException;
import java.net.URL;

import java.util.List;
import java.util.Vector;



import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;
import edu.mit.jwi.morph.WordnetStemmer;
import eu.dynalearn.st.config.Configuration;

public class WordNetSearch {
	
	private IDictionary wnDict;
	
	public WordNetSearch() throws MalformedURLException {
		URL url = new URL("file", null, Configuration.WN_PATH);
		wnDict = new Dictionary(url);
		wnDict.open();
	}
	
	/**
	 * Search in WordNet, return 
	 * @param term: term searched for
	 * @param pos: part of speech identifiers, @see class POS
	 * @return true or false depending on whether the term was found (?)
	 * @author mtrna
	 */
	public boolean searchWN(String term, POS pos) {
		return wnDict.getIndexWord(term, pos) != null;
	}

	// 4 methods instead of 1?
	public boolean searchWNNoun(String term) {
		return wnDict.getIndexWord(term, POS.NOUN) != null;
	}
	
	public boolean searchWNVerb(String term) {
		return wnDict.getIndexWord(term, POS.VERB) != null;
	}
	
	public boolean searchWNAdj(String term) {
		return wnDict.getIndexWord(term, POS.ADJECTIVE) != null;
	}
	
	public boolean searchWNAdv(String term) {
		return wnDict.getIndexWord(term, POS.ADVERB) != null;
	}

	protected Vector<String> getSynonym(String term) {
		Vector<String> synonym = new Vector<String>();
		IIndexWord idxWord = wnDict.getIndexWord(term, POS.NOUN);
		ISynset synset = null;
		if (idxWord != null){
			IWordID wordID = (IWordID) idxWord.getWordIDs().get(0);
			IWord word = wnDict.getWord(wordID);
//			Report.trace("Id = " + wordID);
//			Report.trace("Lemma = " + word.getLemma());
//			Report.trace("Gloss = " + word.getSynset().getGloss());
			synset = word.getSynset();
			if (synset != null) {
				for(IWord w : synset.getWords()) {
					synonym.add(w.toString());	
				}
				return synonym;
			}
		}		
		return null;
	}
	

	protected Vector<String> getHypernym(String word) {
		Vector<String> hypernym = new Vector<String>();
		return hypernym;
	}
	
	public Vector<String> getStemmer(String word) {
		Vector<String> stemmerList = new Vector<String>();

		WordnetStemmer wstemmer = new WordnetStemmer(wnDict);
		List<String> listStemmer = wstemmer.findStems(word);
		if (!listStemmer.isEmpty()) 
			for (int i = 0; i < listStemmer.size(); i++) {
				stemmerList.add(listStemmer.get(i));
			}
		return stemmerList;
	}
	
}
