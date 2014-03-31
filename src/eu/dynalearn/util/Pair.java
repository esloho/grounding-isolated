package eu.dynalearn.util;

public class Pair<E1, E2> {
	protected E1 elto1=null;
	protected E2 elto2=null;
	public Pair(E1 elto1, E2 elto2) {
		this.elto1=elto1;
		this.elto2=elto2;
	}
	public E1 getElement1() {
		return elto1;
	}
	public E2 getElement2() {
		return elto2;
	}
}

