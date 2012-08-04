/**
 * 
 */
package org.agileworks.wicket.callbacks.domain.impl;

import org.agileworks.wicket.callbacks.domain.ICallbackEvent;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.wicket.Component;

/**
 * @author tajzich
 * 
 */
public abstract class AbstractCallbackEvent implements ICallbackEvent {

	private static final long serialVersionUID = 3794509425662034620L;
	private boolean processed = false;
	private final Component source;

	/**
	 * 
	 */
	public AbstractCallbackEvent(Component source) {
		super();

		if (source == null) {
			throw new IllegalArgumentException("Source cannot be null!");
		}

		this.source = source;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public final boolean isProcessed() {
		return processed;
	}

	@Override
	public final void markProcessed() {
		processed = true;
	}

	@Override
	public final Component getSource() {
		return source;
	}
}
