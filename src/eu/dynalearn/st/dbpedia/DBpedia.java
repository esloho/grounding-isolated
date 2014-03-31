package eu.dynalearn.st.dbpedia;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Resource;

import eu.dynalearn.st.config.Configuration;
import eu.dynalearn.util.Pair;

public class DBpedia {
	private static final String rdfs = "http://www.w3.org/2000/01/rdf-schema#";
	private static final String dbprop = "http://dbpedia.org/property/";
	private static final String dbowl = "http://dbpedia.org/ontology/";
	private static MessageFormat LOOKUP_URL=new MessageFormat(Configuration.DBPEDIA_LOOKUP);
//	private static String dbpediaService;
	/*
	 * Process a query to a dbpedia endpoint. Return a null if occurs an error
	 */
	private static DBpediaStatement processQuery_Exception(final String queryString) {
		DBpediaStatement results;
		try 
		{
			Query query = QueryFactory.create(queryString);
			QueryExecution qe = QueryExecutionFactory.sparqlService(Configuration.DBPEDIA, query);
			results=new DBpediaStatement(qe, qe.execSelect());
		} catch (Exception e) {
			System.out.println("Retry dbPedia query: " + queryString);
			return null;
		}
		return results;
	}

	/*
	 * Process a query to a dbpedia endpoint. The method retries until 25 times if an error occurs.
	 */
	private static DBpediaStatement processQuery(final String queryString) {
		DBpediaStatement results=null;
		results = processQuery_Exception(queryString);
		int intends = 0;
		while ((intends < 25) && (results==null)) {
			results = processQuery_Exception(queryString);
			intends++;
		}
		return results;
	}
	
//	private static String getLanguageList(Collection<Language> langs) {
//		StringBuffer mvLangs = new StringBuffer();
//		for (Language lang: langs) {
//			if(mvLangs.length()>0) mvLangs.append('|');
//			mvLangs.append(lang.getDbpediaCode());
//		}
//		return mvLangs.toString();
//	}
	
	public static ResultsParser lookup(String word) throws Exception 
	{
		try 
		{
			return new ResultsParser(LOOKUP_URL.format(new Object[]{URLEncoder.encode(word, "UTF-8")}));
		} catch (UnsupportedEncodingException e) {
			return new ResultsParser(LOOKUP_URL.format(new Object[]{word}));
		}
	}
	
	/*
	 *  return descriptions in a collection from an uri
	 */
	public static Collection<Literal> getDescriptions(String uri) {
		DBpediaStatement stmt=processQuery("PREFIX dbowl: <" + dbowl + "> " +
				                           "SELECT ?description " +
				                           "WHERE {" +
				                           "  <" + uri + "> dbowl:abstract ?description . " +
	                                       "}");
		Collection<Literal> result=new ArrayList<Literal>();
		ResultSet rs=stmt.getResultSet();
		while(rs.hasNext())
			result.add(rs.nextSolution().getLiteral("description"));
		return (result.size()==0)?null:result;
	}

	/*
	 *  return descriptions from an uri in a list of languages
	 */
//	public static Collection<Literal> getDescriptions(String uri, Collection<Language> langs) {
//		DBpediaStatement stmt=null;
//		try {
//			stmt=processQuery("PREFIX dbowl: <" + dbowl + "> " +
//                               "SELECT ?description " +
//                               "WHERE {" +
//                               "  <" + uri + "> dbowl:abstract ?description . " +
//                               " FILTER regex(lang(?description), '("+getLanguageList(langs)+")', 'i')" +
//                               "}");
//			Collection<Literal> result=new ArrayList<Literal>();
//			ResultSet rs=stmt.getResultSet();
//			while(rs.hasNext())
//				result.add(rs.nextSolution().getLiteral("description"));
//			return (result.size()==0)?null:result;
//		}
//		finally {
//			if(stmt!=null) stmt.close();
//		}
//	}

	/*
	 *  return descriptions from an uri in a language
	 */
	public static Collection<String> getDescriptions(String uri, String lang) {
		DBpediaStatement stmt=null;
		try {
			stmt=processQuery("PREFIX dbowl: <" + dbowl + "> " +
                              "SELECT ?description " +
                              "WHERE {" +
                              "  <" + uri + "> dbowl:abstract ?description . " +
                              "  FILTER langMatches( lang(?description), \"" + lang + "\" )" +
                              "}");
			Collection<String> result=new ArrayList<String>();
			ResultSet rs=stmt.getResultSet();
			while(rs.hasNext())
				result.add(rs.nextSolution().getLiteral("description").getString());
			return (result.size()==0)?null:result;
		}
		finally {
			if(stmt!=null) stmt.close();
		}
	}

	/*
	 *  return labels from an uri
	 */
	public static Collection<Literal> getLabels(String uri) {
		DBpediaStatement stmt=null;
		try {
			stmt=processQuery("PREFIX rdfs: <" + rdfs + "> " +
                              "SELECT ?label " +
		                      "WHERE {" +
				              "  <" + uri + "> rdfs:label ?label . " +
				              "}");
			Collection<Literal> result=new ArrayList<Literal>();
			ResultSet rs=stmt.getResultSet();
			while(rs.hasNext())
				result.add(rs.nextSolution().getLiteral("label"));
			return (result.size()==0)?null:result;
		}
		finally {
			if(stmt!=null) stmt.close();
		}
	}

	/*
	 *  return labels from an uri in a list of languages
	 */
//	public static Collection<Literal> getLabels(String uri, Collection<Language> langs) {
//		DBpediaStatement stmt=null;
//		try {
//			stmt=processQuery("PREFIX rdfs: <" + rdfs + "> " +
//                              "SELECT ?label " +
//                              "WHERE {" +
//                              "  <" + uri + "> rdfs:label ?label . " +
//                              " FILTER regex(lang(?label), '("+getLanguageList(langs)+")', 'i') " +
//	                  		  "}");
//			Collection<Literal> result=new ArrayList<Literal>();
//			ResultSet rs=stmt.getResultSet();
//			while(rs.hasNext()) 
//				result.add(rs.nextSolution().getLiteral("label"));
//			return (result.size()==0)?null:result;
//		}
//		finally {
//			if(stmt!=null) stmt.close();
//		}
//	}

	/*
	 *  return labels from an uri in a language
	 */
	public static Collection<Literal> getLabels(String uri, String lang) {
		DBpediaStatement stmt=null;
		try{
			stmt=processQuery("PREFIX rdfs: <" + rdfs + "> " +
	                          "SELECT ?label " +
	                          "WHERE {" +
	                          "  <" + uri + "> rdfs:label ?label ." +
	                          "  FILTER langMatches( lang(?label), \"" + lang + "\" )" +
		                      "}");
			Collection<Literal> result=new ArrayList<Literal>();
			ResultSet rs=stmt.getResultSet();
			while(rs.hasNext())
				result.add(rs.nextSolution().getLiteral("label"));
			return (result.size()==0)?null:result;
		}
		finally {
			if(stmt!=null) stmt.close();
		}
	}

	/*
	 *  return a collection of tuples <language, label, description?>
	 */
	public static Collection<Pair<Literal, Literal>> getLabelsAndDescriptions(String uri) {
		DBpediaStatement stmt=null;
		try {
			stmt=processQuery("PREFIX rdfs: <" + rdfs + "> " +
		                      "PREFIX dbowl: <" + dbowl + "> " +
		                      "SELECT ?label ?description " +
		                      "WHERE { " +
		                      " <" + uri + "> rdfs:label ?label .  " +
		                      " OPTIONAL {<" + uri + "> dbowl:abstract ?description }  " +
		                      " FILTER( lang(?label) = lang(?description) ) " +
		                      "}");
			Collection<Pair<Literal, Literal>> result=new ArrayList<Pair<Literal, Literal>>();
			Literal lit1, lit2;
			QuerySolution qs;
			ResultSet rs=stmt.getResultSet();
			while(rs.hasNext()) {
				qs=rs.nextSolution();
				lit1=qs.getLiteral("label");
				lit2=qs.getLiteral("description");
				result.add(new Pair<Literal, Literal>(lit1, lit2));
			}
			return (result.size()==0)?null:result;
		}
		finally {
			if(stmt!=null) stmt.close();
		}
	}
	
	/*
	 *  return a collection of pair <label, description?> of a set of specific languages
	 */
//	public static Collection<Pair<Literal, Literal>> getLabelsAndDescriptions(String uri, Collection<Language> langs) {
//		DBpediaStatement stmt=null;
//		try {
//			stmt=processQuery("PREFIX rdfs: <" + rdfs + "> " +
//		                      "PREFIX dbowl: <" + dbowl + "> " +
//		                      "SELECT ?label ?description " +
//		                      "WHERE { " +
//		                      " <" + uri + "> rdfs:label ?label .  " +
//		                      " OPTIONAL { " +
//		                      "  <" + uri + "> rdfs:label ?label. " +
//		                      "  <" + uri + "> dbowl:abstract ?description . " +
//		                      "  FILTER( !bound(?description) || lang(?label) = lang(?description) ) " +
//		                      "} " +
//		                      "FILTER regex(lang(?label), '("+getLanguageList(langs)+")', 'i') " +
//		                      "}");
//			Collection<Pair<Literal, Literal>>  result=new ArrayList<Pair<Literal, Literal>>();
//			Literal lit1, lit2;
//			QuerySolution qs;
//			ResultSet rs=stmt.getResultSet();
//			while(rs.hasNext()) {
//				qs=rs.nextSolution();
//				lit1=qs.getLiteral("label");
//				lit2=qs.getLiteral("description");
//				result.add(new Pair<Literal, Literal>(lit1, lit2));
//			}
//			return (result.size()==0)?null:result;
//		}
//		finally {
//			if(stmt!=null) stmt.close();
//		}
//	}
	
	// return a collection of pairs <resource, label>
	public static Collection<Pair<Resource, Literal>> getSynonyms(String uri) {
		DBpediaStatement stmt=null;
		try {
			stmt=processQuery("PREFIX dbprop: <" + dbprop + "> " +
		                      "PREFIX rdfs: <" + rdfs + "> " +
		                      "SELECT ?x ?label " +
		                      "WHERE {" +
		                      "  ?x dbprop:redirect <" + uri + "> . " +
		                      "  OPTIONAL {?x rdfs:label ?label. }" +
		                      "}");
			Collection<Pair<Resource, Literal>> result=new ArrayList<Pair<Resource, Literal>>();
			QuerySolution qs;
			Resource rsrc;
			Literal lit;
			ResultSet rs=stmt.getResultSet();
			while(rs.hasNext()) {
				qs=rs.nextSolution();
				rsrc=qs.getResource("x");
				lit=qs.getLiteral("label");
				result.add(new Pair<Resource, Literal>(rsrc, lit));
			}
			return (result.size()==0)?null:result;
		}
		finally {
			if(stmt!=null) stmt.close();
		}
	}

	// return a collection of pairs <resource, label> in a list of languages
//	public static Collection<Pair<Resource, Literal>> getSynonyms(String uri, Collection<Language> langs) {
//		DBpediaStatement stmt=null;
//		try {
//			stmt=processQuery("PREFIX dbprop: <" + dbprop + "> " +
//		                      "PREFIX rdfs: <" + rdfs + "> " +
//		                      "SELECT ?x ?label " +
//		                      "WHERE {" +
//		                      "  ?x dbprop:redirect <" + uri + "> . " +
//		                      "  OPTIONAL { " +
//		                      "    ?x rdfs:label ?label. " +
//		                      "    FILTER regex(lang(?label), '("+getLanguageList(langs)+")', 'i') " +
//		                      "  }" +
//		                      "}");
//			Collection<Pair<Resource, Literal>> result=new ArrayList<Pair<Resource, Literal>>();
//			QuerySolution qs;
//			Resource rsrc;
//			Literal lit;
//			ResultSet rs=stmt.getResultSet();
//			while(rs.hasNext()) {
//				qs=rs.nextSolution();
//				rsrc=qs.getResource("x");
//				lit=qs.getLiteral("label");
//				result.add(new Pair<Resource, Literal>(rsrc, lit));
//			}
//			return (result.size()==0)?null:result;
//		}
//		finally {
//			if(stmt!=null) stmt.close();
//		}
//	}
}
