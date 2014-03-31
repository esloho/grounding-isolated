package eu.dynalearn.st.ext.google;

import java.util.Vector;

import org.xeustechnologies.googleapi.spelling.Language;
import org.xeustechnologies.googleapi.spelling.SpellChecker;
import org.xeustechnologies.googleapi.spelling.SpellCorrection;
import org.xeustechnologies.googleapi.spelling.SpellRequest;
import org.xeustechnologies.googleapi.spelling.SpellResponse;

//import eu.dynalearn.ext.spelling.SpellingSuggestions;

public class GoogleSpellingSuggestions implements SpellingSuggestions {

//	private String _query = "";
//	private String _output = "";
	private Vector<String> _results = new Vector<String>();
	private String lang = "en";

	public GoogleSpellingSuggestions() {
		super();
	}

	public GoogleSpellingSuggestions(String lang) {
		super();
		this.lang = lang;
	}

	@Override
	public void extraction(String query) {
		SpellChecker checker = new SpellChecker();
		if(lang.toLowerCase().equals("es")){
			checker.setLanguage( Language.SPANISH ); 
		} 
		else if(lang.toLowerCase().equals("de")){
			checker.setLanguage( Language.GERMAN); 
		} 
		else if(lang.toLowerCase().equals("pt")){
			checker.setLanguage( Language.PORTUGUESE ); 
		} 
		else if(lang.toLowerCase().equals("bu")){
			checker.setLanguage( Language.ENGLISH); 
		} 
		else checker.setLanguage( Language.ENGLISH);
		
		
		 SpellRequest request = new SpellRequest();
		 request.setText( query );
		 request.setIgnoreDuplicates( true ); // Ignore duplicates
		 request.setTextAlreadyClipped(false);
		 SpellResponse spellResponse = checker.check( request );
		 
		 try {
			for( SpellCorrection sc : spellResponse.getCorrections() ){
				 //System.out.println(sc.getValue());
				 String [] tWords = sc.getWords();
				 for (int i = 0; i < tWords.length; i++) {
					 if(!query.toLowerCase().equals(tWords[i]))
						 _results.add( tWords[i] );
				}
				    
				 
			 }
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public Vector<String> getResult() {
		return _results;
	}

}
