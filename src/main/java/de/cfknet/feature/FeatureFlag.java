package de.cfknet.feature;

/**
 * The FeatureFlag is part of the {@link FeatureToggle} mechanism and can be implemented<br/>
 * to toggle any {@link Feature}
 *
 */
@FunctionalInterface
public interface FeatureFlag {

	boolean isEnabled();
}