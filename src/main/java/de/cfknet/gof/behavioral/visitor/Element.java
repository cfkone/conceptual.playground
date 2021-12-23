package de.cfknet.gof.behavioral.visitor;

public interface Element {
	void accept(Visitor visitor);

	String doStuff();
}