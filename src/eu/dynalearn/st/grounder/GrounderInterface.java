package eu.dynalearn.st.grounder;

public interface GrounderInterface {
	String getLanguage();

	
//	abstract GroundingResults groundSuggestedTerms (String term) throws GrounderException;
	abstract GroundingResults groundTerm(String term) throws GrounderException;
	
	
	//abstract List<Word> groundModel(SemanticRepositoryInterface sr, QRModel qrmodel, String lang);
}
