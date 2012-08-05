package org.agileworks.wicket.callbacks.domain;

import org.apache.commons.collections.keyvalue.MultiKey;

import java.util.Collections;
import java.util.Map;

/**
 * Implementor class is responsible for handling remap of events. Example: when component fire ButtonClickedEvent then component in hierarchy above
 * can remap this event to other event type e.g. SomethingSubmittedEvent
 */
public interface IMappingCallbackAware {

    /**
     * Empty event mapping
     */
	final Map<MultiKey, Class<? extends ICallbackEvent>> EMPTY_EVENT_MAPPING = Collections.emptyMap();

    /**
     * called after remap is done
     * @param event new event type
     */
	void onEventRemapped(ICallbackEvent event);

    /**
     * actual logic for event remap
     *
     * @param event original event
     * @return new remapped event
     */
	ICallbackEvent remapEvent(ICallbackEvent event);

    /**
     * {@see {@link org.agileworks.wicket.callbacks.domain.impl.CallbackMapper#createKey(Class, String)}}
     *
     * @return mapping
     */
	Map<MultiKey, Class<? extends ICallbackEvent>> getEventsMapping();

}
