package org.agileworks.wicket.callbacks.domain;


import org.apache.wicket.util.io.IClusterable;

/**
 * Implementor class is responsible for Callback processing
 * 
 * @author mbednar
 * 
 */
public interface ICallbackAware extends IClusterable {

	/**
	 * Register callback
	 * 
	 * @param callBack
	 */
	void add(ICallback<?> callBack);

	/**
	 * Process event with registered callbacks
	 * 
	 * @param event
	 */
	void onCallBack(ICallbackEvent event);

	/**
	 * Remove registered callback
	 * 
	 * @param callback
	 */
	void remove(ICallback<?> callback);

}
