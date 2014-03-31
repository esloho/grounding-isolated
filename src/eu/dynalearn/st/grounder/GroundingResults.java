package eu.dynalearn.st.grounder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import eu.dynalearn.model.GroundingTerm;

/**
 * A Term with grounding result, including a label, a language, URI, 
 * a type of a term and a list of grounding results. 
 * 
 */
public class GroundingResults {
	/**
	 * Original term from the model with possible groundings
	 */
	private String term;
	
	/**
	 * List of stems from WordNet, each one with correspondent possible groundings
	 */
	private Collection<String> stems = new ArrayList<String>();
	
	/**
	 * Suggest other terms in case of misspelling 
	 */
	private Collection<String> suggestions = new ArrayList<String>();
	
	/**
	 * Possible grounding for the given term
	 */
	private Map<GroundingTerm, GroundingRelevance> possibleGroundings= new HashMap<GroundingTerm, GroundingRelevance>();
	
	
	/* Methods */
	
	public GroundingResults(String term) {
		this.term = term;
	}

	public String getTerm() { 
		return term; 
	} 

	public Collection<String> getStems() {
		return this.stems;
	}
	
	public void addStem(String t) {
		this.stems.add(t);
	}

	public void setStems(Collection<String> stems) {
		this.stems = stems;
	}
	
	public Collection<String> getSuggestions() {
		return this.suggestions;
	}

	public void addSuggestion(String t) {
		suggestions.add(t);
	}

	
	public void setSuggestions(Collection<String> suggestions) {
		this.suggestions = suggestions;
	}

	public Collection<GroundingRelevance> getPossibleGroundings() {
		return this.possibleGroundings.values();
	}

	public void addPossibleGrounding(GroundingRelevance relevance) {
		this.possibleGroundings.put(relevance.getGrounding(), relevance);
	}

	public void addPossibleGrounding(GroundingTerm gr) {
		GroundingRelevance relevance = this.possibleGroundings.get(gr);
		if(relevance == null)
			this.addPossibleGrounding(new GroundingRelevance(gr));
	}

	public void addPossibleGrounding(GroundingTerm gr, double lucene, double media, double inHouse) {
		GroundingRelevance relevance = new GroundingRelevance(gr);
		relevance.setLucene(lucene);
		relevance.setMedia(media);
		relevance.setInHouse(inHouse);
		this.addPossibleGrounding(relevance);
	}

	public void setEvaluatedGroundings(Collection<GroundingRelevance> grs) {
		this.possibleGroundings = new LinkedHashMap<GroundingTerm, GroundingRelevance>();
		for(GroundingRelevance relevance: grs)
			this.possibleGroundings.put(relevance.getGrounding(), relevance);
	}

	public void setPossibleGroundings(Collection<GroundingTerm> grs) {
		this.possibleGroundings = new LinkedHashMap<GroundingTerm, GroundingRelevance>();
		for(GroundingTerm gt: grs)
			this.possibleGroundings.put(gt, new GroundingRelevance(gt));
	}

}
