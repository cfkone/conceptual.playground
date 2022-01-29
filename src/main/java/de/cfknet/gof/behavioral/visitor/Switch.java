package de.cfknet.gof.behavioral.visitor;

public interface Switch {
	void visit(ElementA elementA);

	void visit(ElementB elementB);
}