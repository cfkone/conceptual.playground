package de.cfknet.railway;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * A result interface to handle success and failure of any workflow
 * <p/>
 * you can handle your own workflow by using the boolean methods and get the
 * related values<br/>
 * or you use the onSuccess/onFailure methods (also in a fluent way if you like)
 *
 * @param <S> the value type if succeeded
 * @param <F> the value type if failed
 */
@RequiredArgsConstructor
public abstract class Result<S, F> {

	/**
	 * Creates a success Result
	 *
	 * @param <S>
	 * @param <F>
	 * @param s
	 * @return {@link Result} of S and F
	 */
	public static <S, F> Result<S, F> success(@NonNull final S s) {
		return new Result<S, F>(s, null) {
		};
	}

	/**
	 * Creates a failure Result
	 *
	 * @param <S>
	 * @param <F>
	 * @param f
	 * @return {@link Result} of S and F
	 */
	public static <S, F> Result<S, F> failure(@NonNull final F f) {
		return new Result<S, F>(null, f) {

			@Override
			public boolean isFailure() {
				return true;
			}
		};
	}

	private final S s;
	private final F f;

	/**
	 * Returns success
	 *
	 * @return S
	 */
	public S getS() {
		if (s == null) {
			throw new NoSuchElementException("No success value set.");
		}
		return s;
	}

	/**
	 * Returns failure
	 *
	 * @return F
	 */
	public F getF() {
		if (f == null) {
			throw new NoSuchElementException("No failure value set.");
		}
		return f;
	}

	/**
	 * Maps to another Result if it is success otherwise this
	 *
	 * @param {@link Function} of S and U
	 * @return {@link Result} of U and F
	 */
	@SuppressWarnings("unchecked")
	public <U> Result<U, F> map(final Function<? super S, ? extends U> mapper) {
		Objects.requireNonNull(mapper);
		if (isSuccess()) {
			return Result.success(mapper.apply(getS()));
		} else {
			return (Result<U, F>) this;
		}
	}

	@SuppressWarnings("unchecked")
	public <U> Result<U, F> flatMap(final Function<? super S, ? extends Result<? extends U, ? extends F>> mapper) {
		Objects.requireNonNull(mapper);
		if (!isSuccess()) {
			return (Result<U, F>) this;
		} else {
			return (Result<U, F>) mapper.apply(getS());
		}
	}

	/**
	 * Returns alternative if it is failure otherwise the success value
	 *
	 * @param other {@link Function}
	 * @return S
	 */
	public S orElseGet(final Function<? super F, ? extends S> other) {
		Objects.requireNonNull(other, "other is null");
		if (isSuccess()) {
			return getS();
		} else {
			return other.apply(getF());
		}
	}

	/**
	 * Runs {@link Consumer} on success
	 *
	 * @param consumer {@link Consumer} of S
	 * @return this
	 */
	public Result<S, F> onSuccess(final Consumer<S> consumer) {
		if (isSuccess()) {
			consumer.accept(getS());
		}
		return this;
	}

	/**
	 * Runs {@link Consumer} on failure
	 *
	 * @param consumer {@link Consumer} of F
	 * @return this
	 */
	public Result<S, F> onFailure(final Consumer<F> consumer) {
		if (isFailure()) {
			consumer.accept(getF());
		}
		return this;
	}

	/**
	 * Get {@link Supplier} on succes
	 *
	 * @param supplier {@link Supplier} of R
	 * @return {@link Result}
	 */
	public <R> Result<R, F> onSuccess(final Supplier<R> supplier) {
		if (isSuccess()) {
			return Result.success(supplier.get());
		}
		return Result.failure(getF());
	}

	/**
	 * Get {@link Supplier} on failure
	 *
	 * @param supplier {@link Supplier} of R
	 * @return {@link Result}
	 */
	public <R> Result<S, R> onFailure(final Supplier<R> supplier) {
		if (isFailure()) {
			return Result.failure(supplier.get());
		}
		return Result.success(getS());
	}

	/**
	 * True if failure
	 *
	 * @return boolean
	 */
	public boolean isFailure() {
		return false;
	}

	/**
	 * True if success
	 *
	 * @return boolean
	 */
	public boolean isSuccess() {
		return !isFailure();
	}

}