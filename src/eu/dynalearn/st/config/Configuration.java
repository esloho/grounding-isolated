package eu.dynalearn.st.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


final public class Configuration 
{ 	
//	public static String PROP_FILE = "eu.dynalearn.st.configuration";
	public static String PROP_FILE = "configuration.properties";
	
	// debug
//	public static String DBG_TRACE_FILENAME = Configuration.getDBG_TRACE_FILENAME();
//	public static String DBG_ERROR_FILENAME = Configuration.getDBG_ERROR_FILENAME();
//	public static String DBG_LOG_FOLDER = Configuration.getDBG_LOG_FOLDER();

	// treetagger
	
	public static String TREETAGGER_HOME = Configuration.getTREETAGGER_HOME();
	public static String TREETAGGER_MODEL = Configuration.getTREETAGGER_MODEL();
	public static String TREETAGGER_MODEL_EN = Configuration.getTREETAGGER_MODEL_EN();
	public static String TREETAGGER_MODEL_ES = Configuration.getTREETAGGER_MODEL_ES();
	public static boolean MULTIWORDPROCESSING_WITH_TREETAGGER_ENABLED = Configuration.getMULTIWORDPROCESSING_WITH_TREETAGGER_ENABLED();

	// wordnet path
	public static String WN_PATH = Configuration.getWN_PATH();

	// dbpedia endpoint
	public static String DBPEDIA  = Configuration.getDBPEDIA();
	public static String DBPEDIA_LOOKUP  = Configuration.getDBPEDIA_LOOKUP();
 
//	public static int LOOKUP_MAXHITS = Configuration.getLOOKUP_MAXHITS();
	
//	public static String STOP_WORDS_SET_DE = Configuration.getSTOP_WORDS_SET_DE();
//	public static String STOP_WORDS_SET_EN = Configuration.getSTOP_WORDS_SET_EN();
//	public static String STOP_WORDS_SET_ES = Configuration.getSTOP_WORDS_SET_ES();
//	public static String STOP_WORDS_SET_PT = Configuration.getSTOP_WORDS_SET_PT();
	
	// Methods

	public static String getTREETAGGER_HOME()
	{
		Properties props = new Properties();
		
		try
		{			
			FileInputStream in = new FileInputStream(PROP_FILE);
			props.load(in);
			in.close();
		}catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return props.getProperty("TREETAGGER_HOME");
	}

	public static String getTREETAGGER_MODEL()
	{
		Properties props = new Properties();
		
		try
		{			
			FileInputStream in = new FileInputStream(PROP_FILE);
			props.load(in);
			in.close();
		}catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return props.getProperty("TREETAGGER_MODEL");
	}

	public static String getTREETAGGER_MODEL_EN()
	{
		Properties props = new Properties();
		
		try
		{			
			FileInputStream in = new FileInputStream(PROP_FILE);
			props.load(in);
			in.close();
		}catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return props.getProperty("TREETAGGER_MODEL_EN");
	}

	public static String getTREETAGGER_MODEL_ES()
	{
		Properties props = new Properties();
		
		try
		{			
			FileInputStream in = new FileInputStream(PROP_FILE);
			props.load(in);
			in.close();
		}catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return props.getProperty("TREETAGGER_MODEL_ES");
	}
	
	public static boolean getMULTIWORDPROCESSING_WITH_TREETAGGER_ENABLED()
	{
		Properties props = new Properties();
		
		try
		{			
			FileInputStream in = new FileInputStream(PROP_FILE);
			props.load(in);
			in.close();
		}catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String val = props.getProperty("MULTIWORDPROCESSING_WITH_TREETAGGER_ENABLED");
		if(val.toLowerCase().equals("true") || val.equals("1"))
			return true;
		
		return false;
	}

	public static String getWN_PATH()
	{
		Properties props = new Properties();
		
		try
		{			
			FileInputStream in = new FileInputStream(PROP_FILE);
			props.load(in);
			in.close();
		}catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return props.getProperty("WN_PATH");
	}

//	public static int getLOOKUP_MAXHITS()
//	{
//		Properties props = new Properties();
//		
//		try
//		{			
//			FileInputStream in = new FileInputStream(PROP_FILE);
//			props.load(in);
//			in.close();
//		}catch (IOException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		return Integer.parseInt(props.getProperty("LOOKUP_MAXHITS"));
//	}

//	private static String getSTOP_WORDS_SET_PT() 
//	{
////		ResourceBundle props =
////			ResourceBundle.getBundle(PROP_FILE,
////			Locale.getDefault());
////
////		
//		Properties props = new Properties();
//		
//		try
//		{			
//			FileInputStream in = new FileInputStream(PROP_FILE);
//			props.load(in);
//			in.close();
//		}catch (IOException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		return props.getProperty("STOP_WORDS_SET_PT");
//	}
//
//	private static String getSTOP_WORDS_SET_ES() {
////		ResourceBundle props =
////			ResourceBundle.getBundle(PROP_FILE,
////			Locale.getDefault());
////
////		
//		Properties props = new Properties();
//		
//		try
//		{			
//			FileInputStream in = new FileInputStream(PROP_FILE);
//			props.load(in);
//			in.close();
//		}catch (IOException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		return props.getProperty("STOP_WORDS_SET_ES");
//	}
//
//	private static String getSTOP_WORDS_SET_EN() {
////		ResourceBundle props =
////			ResourceBundle.getBundle(PROP_FILE,
////			Locale.getDefault());
////
////		
//		Properties props = new Properties();
//		
//		try
//		{			
//			FileInputStream in = new FileInputStream(PROP_FILE);
//			props.load(in);
//			in.close();
//		}catch (IOException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		return props.getProperty("STOP_WORDS_SET_EN");
//	}
//
//	private static String getSTOP_WORDS_SET_DE() {
////		ResourceBundle props =
////			ResourceBundle.getBundle(PROP_FILE,
////			Locale.getDefault());
////
////		
//		Properties props = new Properties();
//		
//		try
//		{			
//			FileInputStream in = new FileInputStream(PROP_FILE);
//			props.load(in);
//			in.close();
//		}catch (IOException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		return props.getProperty("STOP_WORDS_SET_DE");
//	}
	
	private static String getDBPEDIA() 
	{
		Properties props = new Properties();
		
		try
		{			
			FileInputStream in = new FileInputStream(PROP_FILE);
			props.load(in);
			in.close();
		}catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return props.getProperty("DBPEDIA");
	}

	private static String getDBPEDIA_LOOKUP() 
	{
		Properties props = new Properties();
		
		try
		{			
			FileInputStream in = new FileInputStream(PROP_FILE);
			props.load(in);
			in.close();
		}catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return props.getProperty("DBPEDIA_LOOKUP");
	}
}

