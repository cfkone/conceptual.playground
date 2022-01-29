package de.cfknet.gof.behavioral.visitor;

/**
 * The {@link Handler} is a container for handling the different
 * {@link Visitable}<br/>
 * implementations by using {@link Visitor}
 *
 * @author cfk
 *
 * @param <T>
 */
@FunctionalInterface
public interface Handler<T extends Visitable, R> {
	R handle(T element);
}