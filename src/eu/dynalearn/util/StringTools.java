package eu.dynalearn.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import eu.dynalearn.st.grounder.GroundingRelevance;
import eu.dynalearn.st.grounder.GroundingResults;

public class StringTools 
{
	
	public static String fixedPoint(float f, int l) 
	{
		String s = f>=0?"":"-";
		f=Math.abs(f);
		s+=""+(int)f+".";
		f-=(int)f;
		
		for (;l>0;l--) 
		{
			f*=10;
			s+=(int)f;
			f-=(int)f;
		}
		return s;
	}
	
	public static String block(final String s, final int size) 
	{
		if (s.length()<size) 
		{
			return s+repeat(" ",size-s.length());
		}
		
		return s;
	}
	
	public static String block0(String s, int size) 
	{
		if (s.length()<size) 
		{
			return repeat("0",size-s.length())+s;
		}
		return s;
	}
	
	public static String repeat(String s, int number) 
	{
		String ret = "";
		// optimizations not used
		for (int i=0;i<number;i++) 
		{
			ret+=s;
		}
		return ret;
	}
	
	public static String implode(String[] ary, String delim) 
	{
	    String out = "";
	    
	    for(int i=0; i<ary.length; i++) 
	    {
	        if(i!=0) 
	        { 
	        	out += delim; 
	        }
	        out += ary[i];
	    }
	    return out;
	}
	
	public static String addSlashes( String text )
	{ 
		final StringBuffer sb = new StringBuffer( text.length() * 2 );
		final StringCharacterIterator iterator = new StringCharacterIterator( text );

		for(char character = iterator.current(); character != StringCharacterIterator.DONE; character = iterator.next() )
		{
			if( character == '"' ) sb.append( "\\\"" );
			else if( character == '\'' ) sb.append( "\\\'" );
			else if( character == '\\' ) sb.append( "\\\\" );
			else if( character == '\n' ) sb.append( "\\n" );
			else if( character == '{' ) sb.append( "\\{" );
			else if( character == '}' ) sb.append( "\\}" );
			else sb.append( character );
		}

		return sb.toString();
	}
	
	// convert a given object to well readable tree form
	// useful for reading results from web services
	public static String objectToString (String ind, Object o) 
	{
		// null -> write null
		if (o==null) 
		{
			return("<null>");
		}
		
		// array -> format an array
		if (o.getClass().isArray()) 
		{
			final Object[] arr = (Object[])o;
			// array is empty
			if (arr.length==0) 
			{
				return ind+"\t<none>\n";
			}
			// array is nonempty
			String s = arr.getClass().getSimpleName() + ":\n";
			for (int i = 0; i < arr.length; i++) 
			{
				String result = objectToString(ind+"\t",arr[i]);
				s+=ind+"\t["+i+"]=("+result+"\n"+ind+"\t)\n";
			}
			return s+ind;
		}
		// collection -> format a collection
		if (o instanceof Collection<?>) 
		{
			final Collection<?> c = (Collection<?>)o;
			// collection is empty
			if (c.size()==0) 
			{
				return ind+"\t<none>\n";
			}
			// collection is nonempty
			String s = "<?>:\n";
			
			for (Object item:c) 
			{
				String result = objectToString(ind+"\t",item);
				s+=ind+"\t[]=("+result+"\n"+ind+"\t)\n";
			}
			return s;
		} 
		
		// simple classes
		if (o instanceof String) return "s:"+(String)o;
		if (o instanceof Character) return "c:"+(Character)o;
		if (o instanceof Integer) return "i:"+(Integer)o;
		if (o instanceof Float) return "f:"+(Float)o;
		if (o instanceof Double) return "d:"+(Double)o;
		if (o instanceof Number) return "n:"+(Number)o;
		
		// grounding result -> take care of the description (don't write it all, just its size...)
		if (o instanceof GroundingRelevance) 
		{
			final GroundingRelevance gr = (GroundingRelevance) o;
			return "\n"+
				ind+"\trelevance=(" + objectToString("",gr.getRelevance()) + ")\n"+
				ind+"\turl=(" + objectToString("",gr.getGrounding())+")"; 
		}
		
		// word, singleword -> write the whole content
		if (o instanceof GroundingResults || o instanceof String ) 
		{
			//final GroundingResults w = (GroundingResults) o;
			final Object w = o;
			//final String class-name = o.getClass().getCanonicalName();
			//classname.substring(classname.lastIndexOf('.'))
			String s = o.getClass().getSimpleName() + ":\n";
			
			for (Method m : w.getClass().getMethods()) 
			{
				// getters w/o parameter
				final boolean isGetter = m.getName().substring(0, 3).equalsIgnoreCase("get");
				final boolean isGetClass = m.getName().equalsIgnoreCase("getClass");
				final boolean isVoid = m.getParameterTypes().length == 0;
				if (isGetter && !isGetClass && isVoid) 
				{
					try 
					{
						String result = objectToString(ind+"\t",m.invoke(w, new Object[0]));
						s+=ind+"\t"+m.getName()+"=("+result+")\n";
					} 
					catch (Exception e) 
					{
//						Report.out("<exception: invoking method \""+m+"()\">");
					}
				}
			}
			return s+ind;
		}
		
		// default for all other classes -> write though toString method
		return o.getClass().toString() + "=(" + o.toString()+")";
	}
		
	/**
	 * computes Levenshtein distance of strings <i>s</i> and <i>t</i>.
	 * complexity: o(mn) time, o(mn) memory (can be optimized to o(n))
	 * @param s string <i>s</i>
	 * @param t string <i>t</i>
	 * @return distance, number of edit-steps
	 * @throws NullPointerException if s==null or t==null
	 */
	public static int levenshteinDistance(final String s, final String t) 
	{
		 
	    final int[][] d = new int[s.length()+1][t.length()+1];
	  
	    for (int i=0;i<=s.length();i++) 
	    {
		   d[i][0] = i;
	    }
	    for (int i=0;i<=t.length();i++) 
	    {
		   d[0][i] = i;
	    }
	    
	    for (int j=1;j<=t.length();j++) 
	    {
	    	for (int i=1;i<=s.length();i++) 
	    	{
	    		if (s.charAt(i-1) == t.charAt(j-1)) 
	    		{ 
	    			d[i][j] = d[i-1][j-1];
	    		} 
	    		else 
	    		{
	                d[i][j] = MathTools.min
	                    (
	                      d[i-1][j] + 1,  // deletion
	                      d[i][j-1] + 1,  // insertion
	                      d[i-1][j-1] + 1 // substitution
	                    );
	    		}
	     	}
	    }
	    
	    return d[s.length()][t.length()];
	}
	
	/**
	 * computes Levenshtein distance of strings <i>s</i> and <i>t</i>.
	 * complexity: o(mn) time, o(mn) memory (can be optimized to o(n))
	 * @param s string <i>s</i>
	 * @param t string <i>t</i>
	 * @return normalized real distance, 0=totally identical, 1=different
	 * @throws NullPointerException if s==null or t==null
	 */
	public static float realLevenshteinDistance(final String s, final String t) 
	{
		float m = MathTools.max(s.length(),t.length());
		
		if (m<=0) 
		{
			return 0; 
		}
		return levenshteinDistance(s,t)/m;
	}
	
	public static Collection<String> fileInCollection(final String filename) 
	{
		List<String> lines = new ArrayList<String>();
		
	    try 
	    {
	    	FileInputStream fstream = new FileInputStream(filename);
	    	DataInputStream in = new DataInputStream(fstream);
	    	BufferedReader br = new BufferedReader(new InputStreamReader(in));
	    	String strLine;
	    	while ((strLine = br.readLine()) != null)   
	    	{
	    		lines.add(strLine);
	    	}
    	    in.close();
	    } 
	    catch (Exception e) 
	    {
//	    	Report.error(e);
	    }
	    return lines;
	} 

	public static String[] fileInLines(final String filename) 
	{
		return fileInCollection(filename).toArray(new String[0]);
	}

	public static String fileInString(final String filename) 
	{
		try 
		{
			final File file = new File(filename);
			return new Scanner(file).useDelimiter("\\Z").next();
		} 
		catch (FileNotFoundException e) 
		{
//			Report.out("<file \""+System.getProperty("user.dir")+System.getProperty("file.separator")+filename+"\" not found>");
			return null;
		}
	}


}
