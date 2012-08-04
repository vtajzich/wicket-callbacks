/**
 * 
 */
package org.agileworks.wicket.callbacks.domain;

import org.apache.wicket.IClusterable;

/**
 * @author tajzich
 * 
 */
public interface ICallback<T extends ICallbackEvent> extends IClusterable {

	void callBack(T event);

	void setParent(ICallbackAware callbackAware);

	ICallbackAware getParent();
}
