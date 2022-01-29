package de.cfknet.gof.behavioral.visitor;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import org.junit.jupiter.api.Test;

class SwitchTest {

	@Test
	void stringVisitor() {
		ConcreteVisitor visitor = new ConcreteVisitor();

		new ElementA().accept(visitor);
		assertThat(visitor.get(), is("Hello"));

		new ElementB().accept(visitor);
		assertThat(visitor.get(), is("World"));
	}

	@Test
	void visitorFactoryBoth() throws Exception {
		Visitor<String> visitor = Visitor.<String>with()
				.handlerA(element -> "Foo")
				.handlerB(element -> "Bar")
				.get();

		ElementA elementA = new ElementA();
		String valueA = visitor.visit(elementA);
		assertThat(valueA, is("Foo"));

		ElementB elementB = new ElementB();
		String valueB = visitor.visit(elementB);
		assertThat(valueB, is("Bar"));
	}

	@Test
	void visitorFactoryOnlyA() throws Exception {
		Visitor<String> visitor = Visitor.<String>with()
				.handlerA(element -> "Foo")
				.get();

		ElementA elementA = new ElementA();
		String valueA = visitor.visit(elementA);
		assertThat(valueA, is("Foo"));

		ElementB elementB = new ElementB();
		String valueB = visitor.visit(elementB);
		assertThat(valueB, is(nullValue()));
	}

	@Test
	void visitorFactoryOnlyB() throws Exception {
		Visitor<String> visitor = Visitor.<String>with()
				.handlerB(element -> "Bar")
				.get();

		ElementA elementA = new ElementA();
		String valueA = visitor.visit(elementA);
		assertThat(valueA, is(nullValue()));

		ElementB elementB = new ElementB();
		String valueB = visitor.visit(elementB);
		assertThat(valueB, is("Bar"));
	}

}
