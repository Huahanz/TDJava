package Atoms;

import java.util.ArrayList;

public class AtomNode {
	public final int id;
	public AtomNode[] nibors;
	private int ref;

	public AtomNode(int id, int ref, AtomNode[] nibors) {
		this.id = id;
		this.ref = ref;
		this.nibors = nibors;
	}
}
