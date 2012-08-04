/**
 * 
 */
package org.agileworks.wicket.callbacks.domain.impl;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;

/**
 * @author tajzich
 * 
 */
public abstract class AbstractDefaultCallbackEvent<T> extends AjaxCallbackEvent {

	private T argument;

	/**
	 * @param argument
	 */
	public AbstractDefaultCallbackEvent(Component component, AjaxRequestTarget ajaxRequestTarget, T argument) {
		super(component, ajaxRequestTarget);
		this.argument = argument;
	}

	/**
	 * @param argument the argument to set
	 */
	public void setArgument(T argument) {
		this.argument = argument;
	}

	/**
	 * @return the argument
	 */
	public T getArgument() {
		return argument;
	}

}
