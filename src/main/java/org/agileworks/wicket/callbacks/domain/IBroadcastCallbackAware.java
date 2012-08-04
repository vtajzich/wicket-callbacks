/**
 * 
 */
package org.agileworks.wicket.callbacks.domain;

/**
 * @author tajzich
 * 
 */
public interface IBroadcastCallbackAware extends ICallbackAware {

	void onBroadcastCallBack(IBroadcastCallbackEvent event);

}
