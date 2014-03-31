package eu.dynalearn.model;


public class GroundingTerm {
	
//	/**
//	 * @generated
//	 */
//	private java.util.Set<Term> terms = new java.util.HashSet<Term>();

	/**
	 * @generated
	 */
//	private java.util.Set<String> labels = new java.util.HashSet<String>();
//	/**
//	 * @generated
//	 */
//	private java.util.Set<String> descriptions = new java.util.HashSet<String>();


	private String label;
	private String description;
	private String URI;

	/**
	 * @generated
	 */
	public GroundingTerm() {
	}


//	public java.util.Set<Term> getTerms() {
//		return this.terms;
//	}
//
//	/**
//	 * @generated
//	 */
//	public void setTerms(java.util.Set<Term> terms) {
//		this.terms = terms;
//	}
//
//	/**
//	 * @generated
//	 */
//	public void addTerms(Term terms) {
//		getTerms().add(terms);
//		terms.setGroundingTerm(this);
//	}
//
//	/**
//	 * @generated
//	 */
//	public void removeTerms(Term terms) {
//		getTerms().remove(terms);
//		terms.setGroundingTerm(null);
//	}

	public String getURI() {
		return this.URI;
	}
	
	public void setURI(String uri) {
		this.URI = uri;
	}
	
	public String getLabel() {
		return this.label;
	}
	
	public void setLabel(String l) {
		this.label = l;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public void setDescription(String descr) {
		this.description = descr;
	}
	
//	/**
//	 * @generated
//	 */
//	public java.util.Set<String> getLabels() {
//		return this.labels;
//	}
//
//	/**
//	 * @generated
//	 */
//	public void setLabels(java.util.Set<String> labels) {
//		this.labels = labels;
//	}
//
//	/**
//	 * @generated
//	 */
//	public void addLabels(String labels) {
//		getLabels().add(labels);
//	}
//
//	/**
//	 * @generated
//	 */
//	public void removeLabels(String labels) {
//		getLabels().remove(labels);
//	}
//
//	/**
//	 * @generated
//	 */
//	public java.util.Set<String> getDescriptions() {
//		return this.descriptions;
//	}
//
//	/**
//	 * @generated
//	 */
//	public void setDescriptions(java.util.Set<String> descriptions) {
//		this.descriptions = descriptions;
////	}
//
//	/**
//	 * @generated
//	 */
//	public void addDescriptions(String descriptions) {
//		getDescriptions().add(descriptions);
//	}
//
//	/**
//	 * @generated
//	 */
//	public void removeDescriptions(String descriptions) {
//		getDescriptions().remove(descriptions);
//	}
	
	

//	public int hashCode() {
//		org.apache.commons.lang.builder.HashCodeBuilder builder = new org.apache.commons.lang.builder.HashCodeBuilder();
//		builder.append(URI);
//		return builder.toHashCode();
//	}
}