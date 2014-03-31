package eu.dynalearn.st.ext.treetagger;

/**
 * class representing a spectrum of frequencies of PoSes in the text/tag
 * @author mtrna
 *
 */
public class Spectrum {
	/**
	 * spectrum of frequencies
	 */
	private final int[] map = new int[POS.values().length];
	/**
	 * implicit constructor
	 */
	public Spectrum () {
	}
	/**
	 * increment frequency of POS by value
	 * @param pos
	 * @param value
	 * @return
	 */
	public Spectrum inc(POS pos, int value) {
		assert value>=0;
		if (pos==null) return this;
		//assert pos!=null;
		map[pos.ordinal()]+=value;
		return this;
	}
	/**
	 * increment frequency of POS by 1
	 * @param pos
	 * @return
	 */
	public Spectrum inc(POS pos) {
		return inc(pos,1);
	}
	/**
	 * add a spectrum to this spectrum
	 * @param spec
	 * @return
	 */
	public Spectrum inc(Spectrum spec) {
		for (POS p:POS.values()) {
			inc(p,spec.getFreq(p));
		}
		return this;
	}
	/**
	 * get frequency of a POS
	 * @param pos
	 * @return
	 */
	public int getFreq(POS pos) {
		return map[pos.ordinal()];
	}
	/**
	 * express the specturm in String format
	 * @param relevant - dont print out those with frequency = 0
	 * @return
	 */
	public String toString(boolean relevant) {
		String s = "";
		for (POS p:POS.values()) {
			String name = p.name();
			if (!relevant || this.getFreq(p)>0) s+=name+": "+this.getFreq(p)+"\n";
		}
		return s;
	}

	/**
	 * express the specturm in String format
	 */
	public String toString() {
		return toString(false);
	}

}
