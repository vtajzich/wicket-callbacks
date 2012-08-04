/**
 * 
 */
package org.agileworks.wicket.callbacks.domain;

import org.apache.wicket.Component;
import org.apache.wicket.IClusterable;

/**
 * @author tajzich
 * 
 */
public interface ICallbackEvent extends IClusterable {

	boolean isProcessed();

	void markProcessed();

	Component getSource();
}
