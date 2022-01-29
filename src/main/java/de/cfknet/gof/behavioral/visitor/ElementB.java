package de.cfknet.gof.behavioral.visitor;

final class ElementB implements Visitable<String> {

	public String doStuff() {
		return "World";
	}

	public String doBStuff() {
		return "Bar";
	}

	@Override
	public void accept(Switch<String> visitor) {
		visitor.visit(this);
	}
}