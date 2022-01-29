package de.cfknet.gof.behavioral.visitor;

public interface Visitable<R> {
	void accept(Switch<R> visitor);

}