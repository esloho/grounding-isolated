package eu.dynalearn.st.grounder;


public class GrounderException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GrounderException() {
		super();
	}
	
	public GrounderException(String s) {
		super(s);
	}

	public GrounderException(Throwable th) {
		super(th);
	}
	
	public GrounderException(String s, Throwable th) {
		super(s, th);
	}
}
