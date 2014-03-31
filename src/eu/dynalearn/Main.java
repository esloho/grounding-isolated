package eu.dynalearn;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.store.LockObtainFailedException;

import eu.dynalearn.st.grounder.EnglishGrounder;
import eu.dynalearn.st.grounder.GrounderException;
import eu.dynalearn.st.grounder.GroundingRelevance;
import eu.dynalearn.st.grounder.GroundingResults;

public class Main
{
	public static EnglishGrounder grounder = new EnglishGrounder(); 
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		String term = "Water";
		
//		getFirstGrounding(term);
//		getGroundings(term);		
		
		List<String> context = new ArrayList<String>();
		context.add("polo");
		getFirstGrounding(term, context);  //--> Comprobar que funciona con las descripciones...
	}

	public static void getFirstGrounding(String term)
	{
		try
		{
			String gr = grounder.getFirstGrounding(term);
			System.out.println("The first grounding in the list is: "+gr);
			
		} 
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public static void getGroundings(String term)
	{
		try
		{
			List<String> grs = grounder.getGroundings(term);
			
			for (String gr:grs) {
				System.out.println(gr);
			}
			
		} 
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public static void getFirstGrounding(String term, List<String> context)
	{
		try
		{
			String gr = grounder.getFirstGrounding(term, context);
			System.out.println("The first contextualized grounding in the list is: "+gr);
			
		} 
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public static void getGroundings(String term, List<String> context)
	{
		try
		{
			List<String> grs = grounder.getGroundings(term, context);
			
			for (String gr:grs) {
				System.out.println(gr);
			}
			
		} 
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
