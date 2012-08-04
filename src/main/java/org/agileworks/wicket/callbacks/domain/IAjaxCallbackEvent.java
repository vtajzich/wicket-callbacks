/**
 * 
 */
package org.agileworks.wicket.callbacks.domain;

import org.apache.wicket.ajax.AjaxRequestTarget;

/**
 * @author tajzich
 * 
 */
public interface IAjaxCallbackEvent extends ICallbackEvent {

	AjaxRequestTarget getAjaxRequestTarget();
}
