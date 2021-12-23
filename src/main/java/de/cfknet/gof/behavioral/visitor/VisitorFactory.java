package de.cfknet.gof.behavioral.visitor;

import lombok.Builder;
import lombok.Builder.Default;

/**
 * The {@link VisitorFactory} decouples the {@link Visitor} implementation
 * from<br/>
 * the different purposes of concrete {@link Element} implementation by
 * using<br/>
 * the {@link Handler} interface The functionality to handle any {@link Element}
 * is independent from<br/>
 * the {@link Visitor} implementation
 */

@Builder
public class VisitorFactory {
	@SuppressWarnings("rawtypes")
	static final Handler EMPTY = element -> {
	};

	@Default
	private Handler<ElementA> handlerA = EMPTY;
	@Default
	private Handler<ElementB> handlerB = EMPTY;

	Visitor get() {
		return new Visitor() {

			@Override
			public void visit(ElementB elementB) {
				handlerB.handle(elementB);
			}

			@Override
			public void visit(ElementA elementA) {
				handlerA.handle(elementA);
			}
		};
	}

}