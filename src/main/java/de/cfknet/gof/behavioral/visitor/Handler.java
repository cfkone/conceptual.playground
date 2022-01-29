package de.cfknet.gof.behavioral.visitor;

/**
 * The {@link Handler} is a container for handling the different
 * {@link Element}<br/>
 * implementations by using {@link Visitor}
 *
 * @author cfk
 *
 * @param <T>
 */
@FunctionalInterface
public interface Handler<T extends Element> {
	void handle(T element);
}