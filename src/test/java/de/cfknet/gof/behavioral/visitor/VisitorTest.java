package de.cfknet.gof.behavioral.visitor;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.jupiter.api.Test;

class VisitorTest {

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
		Visitor visitor = VisitorFactory.builder()
				.handlerA(element -> assertThat(element.doAStuff(), is("Foo")))
				.handlerB(element -> assertThat(element.doBStuff(), is("Bar")))
				.build()
				.get();

		ElementA elementA = new ElementA();
		visitor.visit(elementA);

		ElementB elementB = new ElementB();
		visitor.visit(elementB);
	}

	@Test
	void visitorFactoryOnlyA() throws Exception {
		Visitor visitor = VisitorFactory.builder()
				.handlerA(element -> assertThat(element.doAStuff(), is("Foo")))
				.build()
				.get();

		ElementA elementA = new ElementA();
		visitor.visit(elementA);

		ElementB elementB = new ElementB();
		visitor.visit(elementB);
	}

	@Test
	void visitorFactoryOnlyB() throws Exception {
		Visitor visitor = VisitorFactory.builder()
				.handlerB(element -> assertThat(element.doBStuff(), is("Bar")))
				.build()
				.get();

		ElementA elementA = new ElementA();
		visitor.visit(elementA);

		ElementB elementB = new ElementB();
		visitor.visit(elementB);
	}

}
