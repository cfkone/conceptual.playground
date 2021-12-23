package de.cfknet.gof.behavioral.visitor;

final class ConcreteVisitor implements Visitor {

	private String value;

	@Override
	public void visit(ElementA elementA) {
		value = elementA.doStuff();
	}

	@Override
	public void visit(ElementB elementB) {
		value = elementB.doStuff();
	}

	public String getValue() {
		return value;
	}
}