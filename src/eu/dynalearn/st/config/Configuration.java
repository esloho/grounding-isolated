package eu.dynalearn.st.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;


final public class Configuration { 
//	public static Properties props = new Properties();
//	public static Properties props = Configuration.loadProps();
	
//	public static String PROP_FILE = "eu.dynalearn.st.configuration";
	public static String PROP_FILE = "configuration.properties";
	
	// debug
	public static String DBG_TRACE_FILENAME = Configuration.getDBG_TRACE_FILENAME();
	public static String DBG_ERROR_FILENAME = Configuration.getDBG_ERROR_FILENAME();
	public static String DBG_LOG_FOLDER = Configuration.getDBG_LOG_FOLDER();

	// treetagger
	
	public static String TREETAGGER_HOME = Configuration.getTREETAGGER_HOME();
	public static String TREETAGGER_MODEL = Configuration.getTREETAGGER_MODEL();
	public static String TREETAGGER_MODEL_BG = Configuration.getTREETAGGER_MODEL_BG();
	public static String TREETAGGER_MODEL_EN = Configuration.getTREETAGGER_MODEL_EN();
	public static String TREETAGGER_MODEL_ES = Configuration.getTREETAGGER_MODEL_ES();
	public static String TREETAGGER_MODEL_GE = Configuration.getTREETAGGER_MODEL_GE();
	public static String TREETAGGER_MODEL_PT = Configuration.getTREETAGGER_MODEL_PT();
	public static boolean MULTIWORDPROCESSING_WITH_TREETAGGER_ENABLED = Configuration.getMULTIWORDPROCESSING_WITH_TREETAGGER_ENABLED();

	// wordnet path
	public static String WN_PATH = Configuration.getWN_PATH();
	
	// web services
//	public static String WSLOCAL_WSDL_LOCATION = ConfigurationPropFile.getWSLOCAL_WSDL_LOCATION();
//	public static String WSLOCAL_METHODS_LOCATION = ConfigurationPropFile.getWSLOCAL_METHODS_LOCATION();
//
//	public static String WSPRODUCTION_WSDL_LOCATION = ConfigurationPropFile.getWSPRODUCTION_WSDL_LOCATION();
//	public static String WSPRODUCTION_METHODS_LOCATION = ConfigurationPropFile.getWSPRODUCTION_METHODS_LOCATION();

	// database
//	public static String DB_DRIVER_CLASS  = ConfigurationPropFile.getDB_DRIVER_CLASS();
//	public static String DB_USERNAME = ConfigurationPropFile.getDB_USERNAME(); 
//	public static String DB_PASSWORD = ConfigurationPropFile.getDB_PASSWORD();
//	public static String DB_TYPE = ConfigurationPropFile.getDB_TYPE();
//	public static String DB_LOCATION = ConfigurationPropFile.getDB_LOCATION();
//	public static String DB_LOCATION_JENA = ConfigurationPropFile.getDB_LOCATION_JENA();
//	public static String DB_LOCATION_JUNIT = ConfigurationPropFile.getDB_LOCATION_JUNIT();
	
	// Jena Assembler
	public static String JENA_LAYOUT = Configuration.getJENA_LAYOUT();

	// dbpedia endpoint
	public static String DBPEDIA  = Configuration.getDBPEDIA();
	public static String DBPEDIA_LOOKUP  = Configuration.getDBPEDIA_LOOKUP();

	public static String PATH_PROPERTIES = Configuration.getPATH_PROPERTIES();
	public static String GROUNDER_RESULT_FILENAME = Configuration.getGROUNDER_RESULT_FILENAME(); 
	public static int LOOKUP_MAXHITS = Configuration.getLOOKUP_MAXHITS();
	
	public static String STOP_WORDS_SET_DE = Configuration.getSTOP_WORDS_SET_DE();
	public static String STOP_WORDS_SET_EN = Configuration.getSTOP_WORDS_SET_EN();
	public static String STOP_WORDS_SET_ES = Configuration.getSTOP_WORDS_SET_ES();
	public static String STOP_WORDS_SET_PT = Configuration.getSTOP_WORDS_SET_PT();
	
	// Methods
	public static String getDBG_TRACE_FILENAME(){
//		ResourceBundle props =
//			ResourceBundle.getBundle(PROP_FILE,
//			Locale.getDefault());
//
//		
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
		
		return props.getProperty("DBG_TRACE_FILENAME");
	}

	public static String getDBG_ERROR_FILENAME(){
//		ResourceBundle props =
//			ResourceBundle.getBundle(PROP_FILE,
//			Locale.getDefault());
//
//		
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
		
		return props.getProperty("DBG_ERROR_FILENAME");
	}
	
	public static String getDBG_LOG_FOLDER(){
//		ResourceBundle props =
//			ResourceBundle.getBundle(PROP_FILE,
//			Locale.getDefault());
//
//		
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
		
		return props.getProperty("DBG_LOG_FOLDER");
	}

	public static String getTREETAGGER_HOME(){
//		ResourceBundle props =
//			ResourceBundle.getBundle(PROP_FILE,
//			Locale.getDefault());
//
//		
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

	public static String getTREETAGGER_MODEL(){
//		ResourceBundle props =
//			ResourceBundle.getBundle(PROP_FILE,
//			Locale.getDefault());
//
//		
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

	public static String getTREETAGGER_MODEL_EN(){
//		ResourceBundle props =
//			ResourceBundle.getBundle(PROP_FILE,
//			Locale.getDefault());
//
//		
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

	public static String getTREETAGGER_MODEL_BG(){
//		ResourceBundle props =
//			ResourceBundle.getBundle(PROP_FILE,
//			Locale.getDefault());
//
//		
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
		
		return props.getProperty("TREETAGGER_MODEL_BG");
	}

	public static String getTREETAGGER_MODEL_ES(){
//		ResourceBundle props =
//			ResourceBundle.getBundle(PROP_FILE,
//			Locale.getDefault());
//
//		
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

	public static String getTREETAGGER_MODEL_GE(){
//		ResourceBundle props =
//			ResourceBundle.getBundle(PROP_FILE,
//			Locale.getDefault());
//
//		
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
		
		return props.getProperty("TREETAGGER_MODEL_GE");
	}

	public static String getTREETAGGER_MODEL_PT(){
//		ResourceBundle props =
//			ResourceBundle.getBundle(PROP_FILE,
//			Locale.getDefault());
//
//		
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
		
		return props.getProperty("TREETAGGER_MODEL_PT");
	}
	
	public static boolean getMULTIWORDPROCESSING_WITH_TREETAGGER_ENABLED(){
//		ResourceBundle props =
//			ResourceBundle.getBundle(PROP_FILE,
//			Locale.getDefault());
//		
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

	public static String getWN_PATH(){
//		ResourceBundle props =
//			ResourceBundle.getBundle(PROP_FILE,
//			Locale.getDefault());
//
//		
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

//	public static String getWSLOCAL_WSDL_LOCATION(){
//		ResourceBundle props =
//			ResourceBundle.getBundle(PROP_FILE,
//			Locale.getDefault());
//
//		
//		return props.getString("WSLOCAL_WSDL_LOCATION");
//	}
//
//	public static String getWSLOCAL_METHODS_LOCATION(){
//		ResourceBundle props =
//			ResourceBundle.getBundle(PROP_FILE,
//			Locale.getDefault());
//
//		
//		return props.getString("WSLOCAL_METHODS_LOCATION");
//	}
//
//	public static String getWSPRODUCTION_WSDL_LOCATION(){
//		ResourceBundle props =
//			ResourceBundle.getBundle(PROP_FILE,
//			Locale.getDefault());
//
//		
//		return props.getString("WSPRODUCTION_WSDL_LOCATION");
//	}
//
//	public static String getWSPRODUCTION_METHODS_LOCATION(){
//		ResourceBundle props =
//			ResourceBundle.getBundle(PROP_FILE,
//			Locale.getDefault());
//
//		
//		return props.getString("WSPRODUCTION_METHODS_LOCATION");
//	}

//	public static String getDB_DRIVER_CLASS(){
//		ResourceBundle props =
//			ResourceBundle.getBundle(PROP_FILE,
//			Locale.getDefault());
//
//		
//		return props.getString("DB_DRIVER_CLASS");
//	}
//
//	public static String getDB_USERNAME(){
//		ResourceBundle props =
//			ResourceBundle.getBundle(PROP_FILE,
//			Locale.getDefault());
//
//		
//		return props.getString("DB_USERNAME");
//	}
//
//	public static String getDB_PASSWORD(){
//		ResourceBundle props =
//			ResourceBundle.getBundle(PROP_FILE,
//			Locale.getDefault());
//
//		
//		return props.getString("DB_PASSWORD");
//	}
//
//	public static String getDB_TYPE(){
//		ResourceBundle props =
//			ResourceBundle.getBundle(PROP_FILE,
//			Locale.getDefault());
//
//		
//		return props.getString("DB_TYPE");
//	}
//
//	public static String getDB_LOCATION(){
//		ResourceBundle props =
//			ResourceBundle.getBundle(PROP_FILE,
//			Locale.getDefault());
//
//		
//		return props.getString("DB_LOCATION");
//	}
//
//	public static String getDB_LOCATION_JUNIT(){
//		ResourceBundle props =
//			ResourceBundle.getBundle(PROP_FILE,
//			Locale.getDefault());
//
//		
//		return props.getString("DB_LOCATION_JUNIT");
//	}
	
	public static URL getJENA_ASSEMBLER(){
//		ResourceBundle props =
//			ResourceBundle.getBundle(PROP_FILE,
//			Locale.getDefault());
//		
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
		
		String path=props.getProperty("JENA_ASSEMBLER");
		if(path!=null) {
			return Configuration.class.getClassLoader().getResource(path);
		}
		else
			return null; 
	}
	
	public static String getJENA_LAYOUT(){
//		ResourceBundle props =
//			ResourceBundle.getBundle(PROP_FILE,
//			Locale.getDefault());
//		
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
		
		return props.getProperty("JENA_LAYOUT");
	}
	
	public static String getPATH_PROPERTIES(){
//		ResourceBundle props =
//			ResourceBundle.getBundle(PROP_FILE,
//			Locale.getDefault());
//
//		
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
		
		return props.getProperty("PATH_PROPERTIES");
	}

	public static String getGROUNDER_RESULT_FILENAME(){
//		ResourceBundle props =
//			ResourceBundle.getBundle(PROP_FILE,
//			Locale.getDefault());
//
//		
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
		
		return props.getProperty("GROUNDER_RESULT_FILENAME");
	}

	public static int getLOOKUP_MAXHITS(){
//		ResourceBundle props =
//			ResourceBundle.getBundle(PROP_FILE,
//			Locale.getDefault());
//
//		
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
		
		return Integer.parseInt(props.getProperty("LOOKUP_MAXHITS"));
	}


	
//	private static float getRELEVANCE_THRESHOLD() {
//		ResourceBundle props =
//			ResourceBundle.getBundle(PROP_FILE,
//			Locale.getDefault());
//
//		
//		return Float.parseFloat(props.getString("RELEVANCE_THRESHOLD"));
//	}	
	
	private static String getSTOP_WORDS_SET_PT() {
//		ResourceBundle props =
//			ResourceBundle.getBundle(PROP_FILE,
//			Locale.getDefault());
//
//		
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
		
		return props.getProperty("STOP_WORDS_SET_PT");
	}

	private static String getSTOP_WORDS_SET_ES() {
//		ResourceBundle props =
//			ResourceBundle.getBundle(PROP_FILE,
//			Locale.getDefault());
//
//		
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
		
		return props.getProperty("STOP_WORDS_SET_ES");
	}

	private static String getSTOP_WORDS_SET_EN() {
//		ResourceBundle props =
//			ResourceBundle.getBundle(PROP_FILE,
//			Locale.getDefault());
//
//		
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
		
		return props.getProperty("STOP_WORDS_SET_EN");
	}

	private static String getSTOP_WORDS_SET_DE() {
//		ResourceBundle props =
//			ResourceBundle.getBundle(PROP_FILE,
//			Locale.getDefault());
//
//		
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
		
		return props.getProperty("STOP_WORDS_SET_DE");
	}
	
	private static String getDBPEDIA() {
//		ResourceBundle props =
//			ResourceBundle.getBundle(PROP_FILE,
//			Locale.getDefault());
//
//		
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

	private static String getDBPEDIA_LOOKUP() {
//		ResourceBundle props =
//			ResourceBundle.getBundle(PROP_FILE,
//			Locale.getDefault());
//
//		
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

