package de.cfknet.gof.behavioral.visitor;

final class ElementA implements Visitable<String> {

	public String doStuff() {
		return "Hello";
	}

	public String doAStuff() {
		return "Foo";
	}

	@Override
	public void accept(Switch<String> visitor) {
		visitor.visit(this);
	}
}