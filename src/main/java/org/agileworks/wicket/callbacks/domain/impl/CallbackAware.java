/**
 * 
 */
package org.agileworks.wicket.callbacks.domain.impl;

import org.agileworks.wicket.callbacks.domain.*;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tajzich
 * 
 */
public class CallbackAware implements IBroadcastCallbackAware {

	private static final long serialVersionUID = -6520730145088316575L;

	private static final Logger LOG = LoggerFactory.getLogger(CallbackAware.class);

	private final MarkupContainer markupContainer;
	protected final List<ICallback<?>> callBacks = new ArrayList<ICallback<?>>();

	public CallbackAware(final MarkupContainer markupContainer) {
		this.markupContainer = markupContainer;
	}

	@Override
	public final void add(final ICallback<?> callBack) {

		ICallbackAware callbackAware = callBack.getParent();

		if (callbackAware != null) {
			LOG.warn("Callback {} is going to be removed!", callBack.getClass());
			callbackAware.remove(callBack);
		}

		callBacks.add(callBack);
		callBack.setParent(this);
	}

	/** {@inheritDoc} */
	@Override
	public final void onCallBack(final ICallbackEvent event) {
		ICallbackEvent newEvent;
		if (markupContainer instanceof IMappingCallbackAware) {
			newEvent = ((IMappingCallbackAware) markupContainer).remapEvent(event);
		} else {
			newEvent = event;
		}
		if (newEvent instanceof IBroadcastCallbackEvent) {
			Page page = markupContainer.getPage();
			if (page instanceof ITopCallbackAware) {
				ITopCallbackAware callbackAware = (ITopCallbackAware) page;
				callbackAware.proceedCallbackEvent(newEvent);
			}
			page.visitChildren(new BroadcastVisitor((IBroadcastCallbackEvent) newEvent));
		} else {
			proceedCallbackEvent(newEvent);
			lookupParent(newEvent);
		}
	}

	@Override
	public final void remove(final ICallback<?> callback) {
		callBacks.remove(callback);
		callback.setParent(null);
	}

	@Override
	public final void onBroadcastCallBack(final IBroadcastCallbackEvent event) {

		proceedCallbackEvent(event);
	}

	private void lookupParent(final ICallbackEvent event) {

		MarkupContainer parent = markupContainer.getParent();

		while (!(parent instanceof ICallbackAware)) {
			if (null == parent) {
				if (!event.isProcessed()) {
					LOG.error("Event ({}) wasn't catched by any callback in hiearchy !", event.toString());
				}
				break;
			}
			parent = parent.getParent();
		}

		if (parent instanceof ICallbackAware && !(event instanceof IBroadcastCallbackEvent)) {
			((ICallbackAware) parent).onCallBack(event);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void proceedCallbackEvent(final ICallbackEvent event) {

		for (ICallback callBack : callBacks) {
			callBack.callBack(event);
		}
	}
}
