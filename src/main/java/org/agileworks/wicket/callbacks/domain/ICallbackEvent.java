/**
 * 
 */
package org.agileworks.wicket.callbacks.domain;

import org.apache.wicket.Component;
import org.apache.wicket.util.io.IClusterable;

/**
 * @author tajzich
 *
 * Basic event interface, see implementations
 *
 */
public interface ICallbackEvent extends IClusterable {

    /**
     *
     * @return true if event is already processed
     */
	boolean isProcessed();

	void markProcessed();

    /**
     *
     * @return component which fired the event
     */
	Component getSource();
}
