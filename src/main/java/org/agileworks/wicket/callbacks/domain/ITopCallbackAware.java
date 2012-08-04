/**
 * 
 */
package org.agileworks.wicket.callbacks.domain;

/**
 * @author mbednar
 * 
 */
public interface ITopCallbackAware extends IBroadcastCallbackAware {

	/**
	 * For internal use only
	 * 
	 * @param event
	 */
	void proceedCallbackEvent(ICallbackEvent event);
}
