package de.cfknet.feature;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Optional;
import org.junit.jupiter.api.Test;

class SelfToggledFeatureTest {

	@Test
	void supplier_true() throws Exception {
		Optional<String> applyIfFeatureEnabled = SelfToggledFeature.with(() -> "Hello", t -> t.startsWith("H"))
				.getIfEnabled(() -> "Foo");

		assertThat(applyIfFeatureEnabled.get(), is("Foo"));
	}

	@Test
	void supplier_true_with_integer_criteria() throws Exception {
		Optional<Integer> applyIfFeatureEnabled = SelfToggledFeature.with(() -> 42, t -> t == 42)
				.getIfEnabled(() -> 23);

		assertThat(applyIfFeatureEnabled.get(), is(23));
	}

	@Test
	void supplier_false() throws Exception {
		Optional<String> applyIfFeatureEnabled = SelfToggledFeature.with(() -> "World", t -> t.startsWith("H"))
				.getIfEnabled(() -> "Foo");

		assertThat(applyIfFeatureEnabled, is(Optional.empty()));
	}

	@Test
	void consumer_true() throws Exception {
		SelfToggledFeature.with(() -> "Hello", t -> t.startsWith("H"))
				.acceptIfEnabled("Foo", t -> assertThat(t, is("Foo")));
	}

	@Test
	void consumer_false() throws Exception {
		SelfToggledFeature.with(() -> "World", t -> t.startsWith("H"))
				.acceptIfEnabled("Foo", t -> fail("should not be called"));
	}

	@Test
	void function_true() throws Exception {
		Optional<String> applyIfEnabled = SelfToggledFeature.with(() -> "Hello", t -> t.startsWith("H"))
				.applyIfEnabled("Foo", t -> t + "Bar");

		assertThat(applyIfEnabled.get(), is("FooBar"));
	}

	@Test
	void function_false() throws Exception {
		Optional<String> applyIfEnabled = SelfToggledFeature.with(() -> "World", t -> t.startsWith("H"))
				.applyIfEnabled("Foo", t -> t + "Bar");

		assertThat(applyIfEnabled, is(Optional.empty()));
	}

}
