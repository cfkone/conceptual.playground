package de.cfknet.gof.behavioral.visitor;

public interface Switch<R> {
	void visit(ElementA elementA);

	void visit(ElementB elementB);

	R get();
}