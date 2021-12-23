package de.cfknet.feature;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;

class FeatureFlagTest {

	private static final class ConcreteFlag implements FeatureFlag {

		private final int conditional;

		public ConcreteFlag(int conditional) {
			this.conditional = conditional;
		}

		@Override
		public boolean isEnabled() {
			return conditional == 11;
		}
	}

	@Test
	void executeByFlag() throws Exception {
		Feature feature = spy(new Feature() {

			@Override
			public void execute() {
				System.out.println("Hello World");
			}
		});

		int pvOrgId = 11;
		FeatureToggle.of(feature)
				.flag(new ConcreteFlag(pvOrgId))
				.get()
				.execute();

		verify(feature).execute();
	}

	@Test
	void executeOtherByFlag() throws Exception {
		Feature feature = spy(new Feature() {

			@Override
			public void execute() {
				System.out.println("Hello World");
			}
		});
		Feature orThatFeature = spy(new Feature() {

			@Override
			public void execute() {
				System.out.println("Good bye");
			}
		});

		int pvOrgId = 42;
		FeatureToggle.of(feature)
				.flag(new ConcreteFlag(pvOrgId))
				.orElseGet(orThatFeature)
				.execute();

		verify(orThatFeature).execute();
	}
}
