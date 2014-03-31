package eu.dynalearn.st.context;

import java.util.ArrayList;
import java.util.Collection;

import eu.dynalearn.model.GroundingTerm;
import eu.dynalearn.st.grounder.GroundingRelevance;
import eu.dynalearn.util.Pair;

public class InhouseRanking {
	
	private float fitnessScore (final String proposal, final String original) 
	{
		final String proposalLow = proposal.toLowerCase();
		final String originalLow = original.toLowerCase();
		
		// find the longest part of proposal that is included in label
		for (int i = original.length(); i>0; i--) 
		{
			final int pos = proposalLow.indexOf(originalLow.substring(0, i));
			
			if (pos!=-1) 
			{
				// inverse containment of a string is penalized
				final float relInvContent = (i+1f)/(original.length()+1f);
				// excessive length is penalized by following means:
				// (a)
				final float relContent = (i+1f)/(proposal.length()+1f);
				// (b)
				final float relMul = (float)Math.pow(0.5, original.length()-i);
				
				// return the result
				return relInvContent * relContent * relMul;
			}
		}
		return 0;
	}
	
	/**
	 * ranks "proposals" according to similarity to "originalLabel"
	 * @param originalLabel
	 * @param proposals
	 * @return
	 */
	public Collection<GroundingRelevance> rank (final String originalLabel, Collection<GroundingRelevance> proposals, String lang) {
		for (GroundingRelevance p:proposals) {
//			for(String label: p.getGrounding().getLabels()) {
////				if(label.getLanguage().equals(lang)) p.setInHouse(fitnessScore(label.getLabel(), originalLabel));
//				p.setInHouse(fitnessScore(label, originalLabel));
//			}
			String label = p.getGrounding().getLabel();
			p.setInHouse(fitnessScore(label, originalLabel));
		}
		return proposals;
	}
}
