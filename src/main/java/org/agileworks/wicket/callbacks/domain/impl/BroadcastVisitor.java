/**
 * 
 */
package org.agileworks.wicket.callbacks.domain.impl;

import org.agileworks.wicket.callbacks.domain.IBroadcastCallbackAware;
import org.agileworks.wicket.callbacks.domain.IBroadcastCallbackEvent;
import org.apache.wicket.Component;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;


/**
 * @author tajzich
 *
 */
public final class BroadcastVisitor implements IVisitor<Component, Object> {

	private final IBroadcastCallbackEvent event;

	public BroadcastVisitor(final IBroadcastCallbackEvent event) {
		this.event = event;
	}

    @Override
    public final void component(Component component, IVisit iVisit) {

        if (component instanceof IBroadcastCallbackAware) {
            ((IBroadcastCallbackAware) component).onBroadcastCallBack(event);
        }
    }
}
