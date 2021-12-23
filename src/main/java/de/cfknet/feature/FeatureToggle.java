package de.cfknet.feature;

import java.util.NoSuchElementException;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * The {@link FeatureToggle} makes possible to simply toggle a {@link Feature} by using a {@link FeatureFlag}<br/>
 * It's fluent and convenient to avoid boilerplate conditional code and it decouples the condition from the<br/>
 * feature functionality.
 * <p/>
 * Examples:
 * <p/>
 * Toggle one feature on or off
 * <p/>
 * FeatureToggle.of(() -> doSomeStuff())<br/>
 * .flag(() -> useFeature == true)<br/>
 * .get()<br/>
 * .execute();<br/>
 * <p/>
 * Toggle between two features
 * <p/>
 * FeatureToggle.of(() -> doSomeStuff())<br/>
 * .flag(() -> useFeature == false)<br/>
 * .orElseGet(() -> doOtherStuff())<br/>
 * .execute();
 */
@RequiredArgsConstructor(staticName = "of")
public final class FeatureToggle {

	private static final FeatureToggle EMPTY = new FeatureToggle(null);

	private final Feature feature;

	public Feature orElseGet(Feature otherFeature) {
		return this.isEmpty() ? otherFeature : feature;
	}

	public FeatureToggle flag(@NonNull FeatureFlag featureFlag) {
		return featureFlag.isEnabled() ? this : this.empty();
	}

	public Feature get() {
		if (this.isEmpty()) {
			throw new NoSuchElementException("No feature present");
		}

		return feature;
	}

	private boolean isEmpty() {
		return feature == null;
	}

	private FeatureToggle empty() {
		return EMPTY;
	}

}