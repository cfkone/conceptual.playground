package de.cfknet.feature;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "with")
public final class SelfToggledFeature<C> {

	private final Supplier<C> criteria;
	private final Predicate<C> isEnabled;

	public <T> void acceptIfEnabled(final T input, final Consumer<T> consumer) {
		if (isEnabled()) {
			consumer.accept(input);
		}
	}

	public <T, R> Optional<R> applyIfEnabled(final T input, final Function<T, R> function) {
		if (isEnabled()) {
			R result = function.apply(input);
			return Optional.ofNullable(result);
		}
		return Optional.empty();
	}

	public <R> Optional<R> getIfEnabled(final Supplier<R> supplier) {
		if (isEnabled()) {
			R result = supplier.get();
			return Optional.ofNullable(result);
		}
		return Optional.empty();
	}

	private boolean isEnabled() {
		return isEnabled.test(criteria.get());
	}
}