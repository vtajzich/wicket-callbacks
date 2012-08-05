/**
 * 
 */
package org.agileworks.wicket.callbacks.domain.impl;

import org.agileworks.wicket.callbacks.domain.IAjaxCallbackEvent;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;

/**
 * @author tajzich
 *
 * Ajax event should extend this class
 *
 */
public class AjaxCallbackEvent extends AbstractCallbackEvent implements IAjaxCallbackEvent {

	private static final long serialVersionUID = 883537473279535673L;

	private final AjaxRequestTarget ajaxRequestTarget;

	/**
	 * @param target
	 */
	public AjaxCallbackEvent(Component source, AjaxRequestTarget target) {
		super(source);
		this.ajaxRequestTarget = target;
	}

	/** {@inheritDoc} */
	@Override
	public final AjaxRequestTarget getAjaxRequestTarget() {
		return ajaxRequestTarget;
	}

}
