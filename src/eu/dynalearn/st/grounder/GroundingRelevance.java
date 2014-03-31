package eu.dynalearn.st.grounder;

import java.util.Comparator;

import eu.dynalearn.model.GroundingTerm;

public class GroundingRelevance {
	
	public static class GroundingRelevanceComparator implements Comparator<GroundingRelevance> {
		public int compare(GroundingRelevance o1, GroundingRelevance o2) {
			return Double.compare(o2.getRelevance(), o1.getRelevance());
		}
	}
	
	protected GroundingTerm grounding=null;
	protected double inHouse=0;
	protected double lucene=0;
	protected double media=0;
	
	public GroundingRelevance(GroundingTerm grounding) {
		this.grounding=grounding;
	}

	public double getInHouse() {
		return inHouse;
	}

	public void setInHouse(double inHouse) {
		this.inHouse = inHouse;
	}

	public double getLucene() {
		return lucene;
	}

	public void setLucene(double lucene) {
		this.lucene = lucene;
	}

	public double getMedia() {
		return media;
	}

	public void setMedia(double media) {
		this.media = media;
	}

	public GroundingTerm getGrounding() {
		return grounding;
	}
	
	public double getRelevance() {
		double relevance = this.inHouse + 3*this.lucene - Math.sqrt(2000f*this.media);
		//this.grounding.
		
		//Code for debugging and define a break point for a specific possibleGrouding
		//String uriS;
		//if (this.getGrounding().getURI().contains("Population_ecology"))
		//	uriS = this.getGrounding().getURI();
		
		//DIC 20120214 Change in raking algorithm to really show the used grounding at top 
		//return this.isPreviousGrounding ? 1 : relevance < 0 ? 0 : (2*Math.atan(relevance)/Math.PI);
		if (relevance < 0)
			return 0;
		else
		{
			//if (this.isPreviousGrounding)
//			if (this.isUsedInAnotherModel())			
//				relevance = relevance + 3; //Weight 3 hard-coded assigned for bringing the possible grounding terms to the TOP
			return (2*Math.atan(relevance)/Math.PI);			
		}
	}	
}
