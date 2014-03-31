package eu.dynalearn.st.grounder;


public class GrounderException extends Exception {
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
