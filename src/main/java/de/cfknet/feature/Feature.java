package de.cfknet.feature;

/**
 * Feature is a functional interface which can be implemented for any functionality <br/>
 * which will be provided as a feature. <br/>
 * The main intention is, to use this by a {@link FeatureToggle} as a decoupled and convenient solution
 *
 */
@FunctionalInterface
public interface Feature {

	void execute();
}