package eu.dynalearn.st.ext.google;

import java.util.Vector;

public interface SpellingSuggestions {

	public void extraction(String query);
	public Vector<String> getResult();

}
