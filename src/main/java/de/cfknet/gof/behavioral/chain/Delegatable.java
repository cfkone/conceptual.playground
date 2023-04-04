package de.cfknet.gof.behavioral.chain;

import java.util.Optional;

/**
 * To use within a {@link Chain} to handle any request object
 */
@FunctionalInterface
public interface Delegatable<T, R> {

	/**
	 * Where the magic happens
	 */
	Optional<R> handle(T request);

	default Delegatable<T, R> delegate(Delegatable<T, R> next) {
		return request -> {
			Optional<R> result = handle(request);
			if (!result.isPresent()) {
				result = next.handle(request);
			}
			return result;
		};
	}

}