/**
 * 
 */
package org.agileworks.wicket.callbacks.domain;

/**
 * @author tajzich
 *
 * Implementor is responsible for delivering broadcast events to all components in all hierarchies.
 *
 */
public interface IBroadcastCallbackAware extends ICallbackAware {

	void onBroadcastCallBack(IBroadcastCallbackEvent event);

}
