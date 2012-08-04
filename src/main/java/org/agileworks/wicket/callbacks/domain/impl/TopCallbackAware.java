package org.agileworks.wicket.callbacks.domain.impl;

import org.agileworks.wicket.callbacks.domain.ICallbackEvent;
import org.agileworks.wicket.callbacks.domain.ITopCallbackAware;

import org.apache.wicket.MarkupContainer;

public class TopCallbackAware extends CallbackAware implements ITopCallbackAware {

	private static final long serialVersionUID = 1171121876756049607L;

	public TopCallbackAware(MarkupContainer markupContainer) {
		super(markupContainer);
	}

	@Override
	public final void proceedCallbackEvent(final ICallbackEvent event) {
		super.proceedCallbackEvent(event);
	}

}
