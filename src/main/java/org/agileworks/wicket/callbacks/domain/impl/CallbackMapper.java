package org.agileworks.wicket.callbacks.domain.impl;

import org.agileworks.wicket.callbacks.domain.ICallbackEvent;
import org.agileworks.wicket.callbacks.domain.IMappingCallbackAware;
import org.apache.commons.collections.keyvalue.MultiKey;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public final class CallbackMapper implements Serializable {

	private static final long serialVersionUID = 7755328360051245624L;
	private final IMappingCallbackAware mappingCallbackAware;

	public CallbackMapper(IMappingCallbackAware mappingCallbackAware) {
		this.mappingCallbackAware = mappingCallbackAware;
	}

	public ICallbackEvent remapEvent(ICallbackEvent event) {
		final Map<MultiKey, Class<? extends ICallbackEvent>> eventsMapping = mappingCallbackAware.getEventsMapping();
		if (eventsMapping.equals(IMappingCallbackAware.EMPTY_EVENT_MAPPING)) {
			return event;
		}

		MultiKey key = createKey(event.getClass(), event.getSource().getId());

		if (!eventsMapping.containsKey(key)) {
			return event;
		}

		Class<? extends ICallbackEvent> mappedInterface = eventsMapping.get(key);

		List<Class<?>> classes = new ArrayList<Class<?>>(Arrays.asList(event.getClass().getInterfaces()));
		classes.add(mappedInterface);

		ICallbackEvent remappedEvent = (ICallbackEvent) Proxy.newProxyInstance(getClass().getClassLoader(),
				classes.toArray(new Class[classes.size()]), new EventInvocationHandler(event));

		mappingCallbackAware.onEventRemapped(remappedEvent);

		return remappedEvent;
	}

	public static MultiKey createKey(Class<? extends ICallbackEvent> clazz, String sourceId) {
		return new MultiKey(clazz, sourceId);
	}

	private static class EventInvocationHandler implements InvocationHandler {

		final Object realObject;

		/** contructor accepts the real subject */
		public EventInvocationHandler(Object real) {
			realObject = real;
		}

		/** a generic, reflection-based secure invocation */
		@Override
		public Object invoke(Object target, java.lang.reflect.Method method, Object[] arguments) throws Throwable {
			try {
				return method.invoke(realObject, arguments);
			} catch (java.lang.reflect.InvocationTargetException e) {
				throw e.getTargetException();
			}
		}
	}
}
