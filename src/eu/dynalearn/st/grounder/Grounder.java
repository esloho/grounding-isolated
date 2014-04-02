package eu.dynalearn.st.grounder;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.annolab.tt4j.TreeTaggerException;

import eu.dynalearn.st.config.Configuration;
import eu.dynalearn.st.ext.treetagger.Cluster;
import eu.dynalearn.st.ext.treetagger.Tag;
import eu.dynalearn.st.mw.Label;
import eu.dynalearn.st.mw.MWLanguage;
import eu.dynalearn.model.GroundingTerm;

public abstract class Grounder implements GrounderInterface {
	protected String lang;
	
	protected Grounder(String lang) {
		this.lang=lang;
	}
	
	public String getLanguage() {
		return this.lang;
	}
	
	
	// was made protected
	// TODO: consider covering other methods from the interface!!!! 
	protected final String normalize(String term) 
	{
		return term.replace("_", " ");
	}
	
	protected abstract Collection<GroundingTerm> searching(String term) throws GrounderException;
	
	protected Collection<GroundingTerm> searchGroundings(String term) throws GrounderException 
	{
		Collection<GroundingTerm> groundings = new LinkedHashSet<GroundingTerm>();
		
		groundings.addAll(searching(term));
		
		return groundings;
	}
	
	protected void groundCompoundWord(GroundingResults grResults, String label) throws GrounderException 
	{
		try {				
			String[] split = label.split(" ");
			//Only sequences of Adjectives and Nouns are allowed
			String[] poses = new String[]{"JJ","JJS","NN","NNP","NNS","NNPS"};
			if(split.length>1) 
			{
				//New algorithm based on the document D4.1 description
				if (Configuration.MULTIWORDPROCESSING_WITH_TREETAGGER_ENABLED && MWLanguage.IsAValidCompundWordLanguage(lang))
				{	
					//Loading previously found terms to avoid duplicates
					Set<String> grTermURIS=new HashSet<String>();
					for(GroundingRelevance rel:grResults.getPossibleGroundings())
						grTermURIS.add(rel.getGrounding().getURI());
					
					Label mwlabel = new Label(label, MWLanguage.getLanguageForString(lang));
					
					//Cluster _cluster = new Cluster(mwlabel); //TODO: review why this line crash the recursive algorithm 					
					//Iteration for length of word
					for (int i = 1; i < split.length; i++)
					{
						//Iteration for position of the word
						for (int j = 0; j < split.length; j++)
						{		
							if (j <= i)
							{
								Cluster _cluster = new Cluster(mwlabel);
								Cluster result = _cluster.getSubCluster(j, split.length - i, poses);							
								if (result != null){
									
									//Filtering those clusters that only contain adjectives
									boolean includeCluster = false;
									for (Tag cTag : result.getTags())
									{
										if (cTag.getPos() == "NN" || cTag.getPos() == "NNS" || cTag.getPos() == "NNP" || cTag.getPos() == "NNPS")
										{
											includeCluster = true;
											break;
										}
									}
									if (includeCluster)
									{										
										GroundingResults grSingle = this.groundTerm(result.tokensToString());
										for(GroundingRelevance rel: grSingle.getPossibleGroundings())
											if(!grTermURIS.contains(rel.getGrounding().getURI())) grResults.addPossibleGrounding(rel);
									}
								}
							}
						}
					}
				}
				else
				{
					Set<String> grTermURIS = new HashSet<String>();
					for(GroundingRelevance rel:grResults.getPossibleGroundings())
						grTermURIS.add(rel.getGrounding().getURI());
					for(String singleWord: split) 
					{
						GroundingResults grSingle=this.groundTerm(singleWord);
						for(GroundingRelevance rel: grSingle.getPossibleGroundings())
						{
							if(!grTermURIS.contains(rel.getGrounding().getURI())) grResults.addPossibleGrounding(rel);
						}
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new GrounderException(e);
		} catch (TreeTaggerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new GrounderException(e);
		}
	}	
}
