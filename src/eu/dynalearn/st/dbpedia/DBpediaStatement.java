package eu.dynalearn.st.dbpedia;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.ResultSet;

class DBpediaStatement {
	private QueryExecution qe=null;
	private ResultSet rset=null;
	
	DBpediaStatement(QueryExecution qe, ResultSet rset) {
		this.qe=qe;
		this.rset=rset;
	}
	
	public ResultSet getResultSet() {
		return this.rset;
	}
	
	public void close() {
		if(qe!=null) qe.close();
	}

	protected void finalize() {
		this.close();
	}
}
