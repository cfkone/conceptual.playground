package de.cfknet.feature;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

class FeatureToggleTest {

	private String value;

	public static class NewService {

		String doStuff() {
			return "Hello World";
		}
	}

	public static class OldService {

		String doStuff() {
			return "Say good bye";
		}
	}

	@Test
	void onlyOneFeature() {

		NewService newService = new NewService();

		boolean featureEnable = true;
		FeatureToggle.of(() -> assertThat(newService.doStuff(), is("Hello World")))
				.flag(() -> featureEnable)
				.get()
				.execute();
	}

	@Test
	void newFeature() {

		OldService oldService = new OldService();
		NewService newService = new NewService();

		boolean featureEnable = true;
		String value = featureToggle(newService, oldService, featureEnable);

		assertThat(value, is("Hello World"));
	}

	@Test
	void oldFeature() {

		OldService oldService = new OldService();
		NewService newService = new NewService();

		boolean featureEnable = false;
		String value = featureToggle(newService, oldService, featureEnable);

		assertThat(value, is("Say good bye"));
	}

	@Test
	void nonNullFeatureFlag() {
		NewService newService = new NewService();

		assertThrows(NullPointerException.class,
				() -> FeatureToggle.of(() -> newService.doStuff())
						.flag(null)
						.get()
						.execute());
	}

	@Test
	void nonNullNewFeature() {
		assertThrows(NoSuchElementException.class, () -> FeatureToggle.of(null)
				.flag(() -> true)
				.get()
				.execute());
	}

	private String featureToggle(NewService newService, OldService oldService, boolean featureEnable) {
		FeatureToggle.of(() -> value = newService.doStuff())
				.flag(() -> featureEnable)
				.orElseGet(() -> value = oldService.doStuff())
				.execute();
		return value;
	}

}
