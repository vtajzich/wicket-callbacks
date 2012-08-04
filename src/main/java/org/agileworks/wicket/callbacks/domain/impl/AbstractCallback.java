/**
 * 
 */
package org.agileworks.wicket.callbacks.domain.impl;

import org.agileworks.wicket.callbacks.domain.ICallback;
import org.agileworks.wicket.callbacks.domain.ICallbackAware;
import org.agileworks.wicket.callbacks.domain.ICallbackEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author tajzich
 * 
 */
public abstract class AbstractCallback<T extends ICallbackEvent> implements ICallback<T> {

	private static final long serialVersionUID = 2905744960013488285L;
	private final Class<T> supported;
	private final Logger log = LoggerFactory.getLogger(AbstractCallback.class);
	private ICallbackAware parent;

	public AbstractCallback(Class<T> supported) {
		this.supported = supported;
	}

	/** {@inheritDoc} */
	@Override
	public final void callBack(T event) {

		if (!isSupported(event)) {
			if (log.isDebugEnabled()) {
				log.debug("Callback: {} Rejected event: {}", getClass().getName(), event.toString());
			}
			return;
		}

		if (log.isDebugEnabled()) {
			log.debug("Callback: {} Accepted event: {}", getClass().getName(), event.toString());
		}
		doCallBack(event);
		event.markProcessed();
	}

	@Override
	public void setParent(ICallbackAware callbackAware) {
		this.parent = callbackAware;
	}

	protected abstract void doCallBack(T event);

	@SuppressWarnings("unchecked")
	protected <Y> Y getDefaultArgument(T event) {

		if (event instanceof AbstractDefaultCallbackEvent) {
			return ((AbstractDefaultCallbackEvent<Y>) event).getArgument();
		}

		return null;
	}

	private boolean isSupported(ICallbackEvent event) {

		return supported.isAssignableFrom(event.getClass());
	}

	@Override
	public ICallbackAware getParent() {
		return parent;
	}
}
