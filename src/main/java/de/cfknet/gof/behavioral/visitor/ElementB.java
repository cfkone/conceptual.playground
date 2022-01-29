package de.cfknet.gof.behavioral.visitor;

final class ElementB implements Element {

	@Override
	public String doStuff() {
		return "World";
	}

	public String doBStuff() {
		return "Bar";
	}

	@Override
	public void accept(Switch visitor) {
		visitor.visit(this);
	}
}