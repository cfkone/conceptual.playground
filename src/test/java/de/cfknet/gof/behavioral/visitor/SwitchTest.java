package de.cfknet.gof.behavioral.visitor;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;

class SwitchTest {

	@Test
	void stringVisitor() {
		ConcreteVisitor visitor = new ConcreteVisitor();

		new ElementA().accept(visitor);
		assertThat(visitor.getValue(), is("Hello"));

		new ElementB().accept(visitor);
		assertThat(visitor.getValue(), is("World"));
	}

	@Test
	void visitorFactoryBoth() throws Exception {
		Visitor visitor = Visitor.with()
				.handlerA(element -> assertThat(element.doAStuff(), is("Foo")))
				.handlerB(element -> assertThat(element.doBStuff(), is("Bar")))
				.get();

		ElementA elementA = new ElementA();
		visitor.visit(elementA);

		ElementB elementB = new ElementB();
		visitor.visit(elementB);
	}

	@Test
	void visitorFactoryOnlyA() throws Exception {
		Visitor visitor = Visitor.with()
				.handlerA(element -> assertThat(element.doAStuff(), is("Foo")))
				.get();

		ElementA elementA = new ElementA();
		visitor.visit(elementA);

		ElementB elementB = new ElementB();
		visitor.visit(elementB);
	}

	@Test
	void visitorFactoryOnlyB() throws Exception {
		Visitor visitor = Visitor.with()
				.handlerB(element -> assertThat(element.doBStuff(), is("Bar")))
				.get();

		ElementA elementA = new ElementA();
		visitor.visit(elementA);

		ElementB elementB = new ElementB();
		visitor.visit(elementB);
	}

}
