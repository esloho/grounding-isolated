package eu.dynalearn.st.grounder;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.sql.SQLException;

public interface GrounderInterface {
	String getLanguage();
	
//	/**
//	 * Return a list of proposed grounded terms for a given term.
//	 * @param term a given term
//	 * @return List of proposed groundings of a given term
//	 */
//	abstract Vector<String> getTermGroundings(String term);
//	
	/**
	 * Ground a given term and return the result of grounding.
	 * @param sr a object of SemanticRepository
	 * @param term a given term
	 * @param lang language of a given term
	 * @param typeTerm type of a given term (entity, agent or quantity)
	 * @throws UnsupportedEncodingException 
	 * @throws SQLException 
	 * @throws MalformedURLException 
	 */
	abstract GroundingResults groundSuggestedTerms (String term) throws GrounderException;
	abstract GroundingResults groundTerm(String term) throws GrounderException;
	
	/**
	 * Ground a given model
	 * @param sr SemanticRepository object
	 * @param qrmodel a given model
	 * @param lang language used in a given model
	 * @return list of the grounding results for the un-grounded terms in a given QR model
	 */
	//abstract List<Word> groundModel(SemanticRepositoryInterface sr, QRModel qrmodel, String lang);
//
//	abstract boolean storeSynonym(String term1, String term2);
//	abstract boolean storeTranslation(String term1, String term2);
}
