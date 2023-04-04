package de.cfknet.gof.behavioral.chain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Chain<T, R> {

	private List<Delegatable<T, R>> delegatables = new ArrayList<>();

	/**
	 * Creates a responsibility chain
	 */
	public static <T, R> Chain<T, R> create() {
		return new Chain<>();
	}

	/**
	 * Adds any {@link Delegatable} to the chain
	 */
	public Chain<T, R> add(Delegatable<T, R> delegatable) {
		delegatables.add(delegatable);
		return this;
	}

	/**
	 * If there is no {@link Delegatable} to handle the request, you can do something default.
	 */
	public Chain<T, R> tail(Consumer<T> tail) {
		delegatables.add(t -> {
			tail.accept(t);
			return Optional.empty();
		});
		return this;
	}

	/**
	 * Gets the responsible {@link Delegatable} to handle the request
	 */
	public Delegatable<T, R> get() {
		Optional<Delegatable<T, R>> head = delegatables.stream()
				.reduce(Delegatable::delegate);
		if (head.isPresent()) {
			return head.get();
		} else {
			return request -> Optional.empty();
		}
	}
}