package de.cfknet.gof.behavioral.chain;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.Optional;
import org.junit.jupiter.api.Test;

class ChainTest {

	@Test
	void functional() throws Exception {
		Delegatable<String, Long> chain = Chain.<String, Long>create()
				.add(t -> {
					if ("Foo".equals(t)) {
						return Optional.of(42L);
					}
					return Optional.empty();
				})
				.add(t -> {
					if ("Bar".equals(t)) {
						return Optional.of(23L);
					}
					return Optional.empty();
				})
				.tail(t -> assertThat(t, is("Ups")))
				.get();

		Optional<Long> result1 = chain.handle("Foo");
		Optional<Long> result2 = chain.handle("Bar");
		Optional<Long> result3 = chain.handle("Ups");

		assertThat(result1.get(), is(42L));
		assertThat(result2.get(), is(23L));
		assertThat(result3, is(Optional.empty()));
	}

	@Test
	void functionalNoDelegates() throws Exception {
		Delegatable<String, Long> build = Chain.<String, Long>create()
				.get();

		Optional<Long> result1 = build.handle("Foo");
		Optional<Long> result2 = build.handle("Bar");
		Optional<Long> result3 = build.handle("Ups");

		assertThat(result1, is(Optional.empty()));
		assertThat(result2, is(Optional.empty()));
		assertThat(result3, is(Optional.empty()));
	}

}
