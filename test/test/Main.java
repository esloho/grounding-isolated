package test;

import java.util.ArrayList;
import java.util.List;

import eu.dynalearn.st.grounder.EnglishGrounder;

public class Main
{
	public static EnglishGrounder grounder = new EnglishGrounder(); 
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		String term = "Water";
		
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
	
	public static void getGroundingsWithSuggestions(String term)
	{
		try
		{
			List<String> grs = grounder.getGroundingsWithSuggestions(term);
			
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
