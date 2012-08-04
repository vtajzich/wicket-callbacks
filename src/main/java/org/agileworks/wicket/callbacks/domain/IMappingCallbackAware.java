package org.agileworks.wicket.callbacks.domain;

import org.apache.commons.collections.keyvalue.MultiKey;

import java.util.Collections;
import java.util.Map;

public interface IMappingCallbackAware {

	final Map<MultiKey, Class<? extends ICallbackEvent>> EMPTY_EVENT_MAPPING = Collections.emptyMap();

	void onEventRemapped(ICallbackEvent event);

	ICallbackEvent remapEvent(ICallbackEvent event);

	Map<MultiKey, Class<? extends ICallbackEvent>> getEventsMapping();

}
