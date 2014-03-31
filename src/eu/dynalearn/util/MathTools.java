package eu.dynalearn.util;

public class MathTools 
{
	public static int min( int... numbers ) 
	{
		int minN = Integer.MAX_VALUE;
	    for ( int n : numbers ) 
	    {
	    	if (n<minN) minN=n;
	    }
	  	return minN;
	}

	public static int max( int... numbers ) 
	{
		int maxN = Integer.MIN_VALUE;
	    for ( int n : numbers ) 
	    {
	    	if (n>maxN) maxN=n;
	    }
	  	return maxN;
	}

}
