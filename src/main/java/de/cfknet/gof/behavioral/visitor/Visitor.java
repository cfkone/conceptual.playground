package de.cfknet.gof.behavioral.visitor;

import lombok.Builder;
import lombok.Builder.Default;

/**
 * The {@link Visitor} decouples the {@link Switch} implementation from<br/>
 * the different purposes of concrete {@link Visitable} implementation by
 * using<br/>
 * the {@link Handler} interface The functionality to handle any {@link Visitable}
 * is independent from<br/>
 * the {@link Switch} implementation
 * @param <R>
 */

@Builder(builderMethodName = "with", buildMethodName = "get")
public class Visitor<R> {
	@SuppressWarnings("rawtypes")
	static final Handler EMPTY = element ->	null;

	@Default
	private Handler<ElementA, R> handlerA = EMPTY;
	@Default
	private Handler<ElementB, R> handlerB = EMPTY;

	R visit(Visitable<R> visitable) {
		Switch<R> visitor = new Switch<R>() {

			private R result;

			@Override
			public void visit(ElementB elementB) {
				result = handlerB.handle(elementB);
			}

			@Override
			public void visit(ElementA elementA) {
				result = handlerA.handle(elementA);
			}

			@Override
			public R get() {
				return result;
			}
		};
		visitable.accept(visitor);
		return visitor.get();
	}

}