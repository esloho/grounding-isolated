package eu.dynalearn.st.mw;

public enum MWLanguage {
	BG, EN, ES, PT, DE;
	
	public static MWLanguage getLanguageForString ( String s ) {
		for (MWLanguage lang:values()) {			
			if (lang.toString().compareToIgnoreCase(s)==0){
				System.out.println(lang.toString()+" : "+s);
				return lang;
			}
		}
		return null;
	}
	
	public static boolean IsAValidCompundWordLanguage(String s)
	{
		MWLanguage mLang = MWLanguage.getLanguageForString(s);
		if (mLang != null )
			return true;
		else
			return false;
	}
}
