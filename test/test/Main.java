package test;

import java.util.ArrayList;
import java.util.List;

import eu.dynalearn.model.GroundingTerm;
import eu.dynalearn.st.grounder.EnglishGrounder;

public class Main
{
	public static EnglishGrounder grounder = new EnglishGrounder(); 
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		String term = "building";
		
		// Test grounding without context
//		getFirstGrounding(term);
		getGroundings(term);
//		System.out.println("\nNow with suggestions");
//		getGroundingsWithSuggestions(term);
		
		// Test grounding with context
		List<String> context = new ArrayList<String>();
		context.add("sport");
//		getFirstGrounding(term, context);  //--> Comprobar que funciona con las descripciones...
//		getGroundings(term, context);
	}

	public static void getFirstGrounding(String term)
	{
		try
		{
			GroundingTerm gr = grounder.getFirstGrounding(term);
			
			if (gr != null)
			{
				System.out.println("The first grounding in the list is: "+gr.getLabel()+": "+gr.getURI());
			}
			else
			{
				System.out.println("There is no grounding for such term");
			}
			
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
			List<GroundingTerm> grs = grounder.getGroundings(term);
			
//			for (GroundingTerm gr:grs) {
//				System.out.println(gr.getLabel()+": "+gr.getURI()+" relevance: ");
//			}
			
		} 
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public static void getGroundingsWithSuggestions(String term)
	{
		try
		{
			List<GroundingTerm> grs = grounder.getGroundingsWithSuggestions(term);
			
			for (GroundingTerm gr:grs) {
				System.out.println(gr.getLabel()+": "+gr.getURI());
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
			GroundingTerm gr = grounder.getFirstGrounding(term, context);
			
			if (gr != null)
			{
				System.out.println("The first contextualized grounding in the list is: "+gr.getLabel()+": "+gr.getURI());
			}
			else
			{
				System.out.println("There is no grounding for such term");
			}
			
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
			List<GroundingTerm> grs = grounder.getGroundings(term, context);
			
			for (GroundingTerm gr:grs) {
				System.out.println(gr.getLabel()+": "+gr.getURI());
			}
			
		} 
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
