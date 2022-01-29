package de.cfknet.gof.behavioral.visitor;

final class ConcreteVisitor implements Switch<String> {

	private String value;

	@Override
	public void visit(ElementA elementA) {
		value = elementA.doStuff();
	}

	@Override
	public void visit(ElementB elementB) {
		value = elementB.doStuff();
	}

	@Override
	public String get() {
		return value;
	}

}