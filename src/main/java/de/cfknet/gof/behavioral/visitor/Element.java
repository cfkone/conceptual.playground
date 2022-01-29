package de.cfknet.gof.behavioral.visitor;

public interface Element {
	void accept(Switch visitor);

	String doStuff();
}