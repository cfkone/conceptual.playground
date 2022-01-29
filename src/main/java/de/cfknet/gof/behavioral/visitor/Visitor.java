package de.cfknet.gof.behavioral.visitor;

import lombok.Builder;
import lombok.Builder.Default;

/**
 * The {@link Visitor} decouples the {@link Switch} implementation from<br/>
 * the different purposes of concrete {@link Element} implementation by
 * using<br/>
 * the {@link Handler} interface The functionality to handle any {@link Element}
 * is independent from<br/>
 * the {@link Switch} implementation
 */

@Builder(builderMethodName = "with", buildMethodName = "get")
public class Visitor {
	@SuppressWarnings("rawtypes")
	static final Handler EMPTY = element -> {
	};

	@Default
	private Handler<ElementA> handlerA = EMPTY;
	@Default
	private Handler<ElementB> handlerB = EMPTY;

	Switch visit(Element element) {
		Switch visitor = new Switch() {

			@Override
			public void visit(ElementB elementB) {
				handlerB.handle(elementB);
			}

			@Override
			public void visit(ElementA elementA) {
				handlerA.handle(elementA);
			}
		};
		element.accept(visitor);
		return visitor;
	}

}