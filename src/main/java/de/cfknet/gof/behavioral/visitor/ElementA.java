package de.cfknet.gof.behavioral.visitor;

final class ElementA implements Element {

	@Override
	public String doStuff() {
		return "Hello";
	}

	public String doAStuff() {
		return "Foo";
	}

	@Override
	public void accept(Switch visitor) {
		visitor.visit(this);
	}
}