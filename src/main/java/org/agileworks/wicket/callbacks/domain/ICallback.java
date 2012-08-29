/**
 * 
 */
package org.agileworks.wicket.callbacks.domain;


import org.apache.wicket.util.io.IClusterable;

/**
 * @author tajzich
 *
 * Callback object is responsible for processing events thrown from UI components. This object should contain logic to
 * call underlaying services, update models and refresh components (call {@link org.apache.wicket.ajax.AjaxRequestTarget#addComponent(org.apache.wicket.Component...)})
 *
 */
public interface ICallback<T extends ICallbackEvent> extends IClusterable {

    /**
     * when event is thrown then this method on every callback is called
     *
     * @param event thrown event
     */
	void callBack(T event);

    /**
     *
     * @param callbackAware calback aware parent
     */
	void setParent(ICallbackAware callbackAware);

    /**
     *
     * @return callback aware parent - might be null!
     */
	ICallbackAware getParent();
}
