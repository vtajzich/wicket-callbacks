package org.agileworks.wicket.callbacks.domain.impl;

import org.agileworks.wicket.callbacks.domain.ICallbackEvent;
import org.agileworks.wicket.callbacks.domain.IMappingCallbackAware;
import org.apache.commons.collections.keyvalue.MultiKey;
import org.apache.wicket.Component;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CallbackMapperTest {

	@Mock
	private ICallbackEvent event;
	@Mock
	private IMappingCallbackAware mappingCallbackAware;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

	}

	@Test
	public void testRemapEventWithoutMapping() {
		when(mappingCallbackAware.getEventsMapping()).thenReturn(IMappingCallbackAware.EMPTY_EVENT_MAPPING);
		CallbackMapper callbackMapper = new CallbackMapper(mappingCallbackAware);
		ICallbackEvent result = callbackMapper.remapEvent(event);
		assertEquals(event, result);

	}

	@Test
	public void testRemapEventWithMapping() {

		Component component = mock(Component.class);
		event = new MyEvent(component);

		when(component.getId()).thenReturn("componentId");

		Map<MultiKey, Class<? extends ICallbackEvent>> mapping = new HashMap<MultiKey, Class<? extends ICallbackEvent>>();
		mapping.put(CallbackMapper.createKey(event.getClass(), event.getSource().getId()), MyEventInterface.class);

		when(mappingCallbackAware.getEventsMapping()).thenReturn(mapping);

		CallbackMapper callbackMapper = new CallbackMapper(mappingCallbackAware);
		ICallbackEvent result = callbackMapper.remapEvent(event);
		assertTrue(result instanceof MyEventInterface);
	}

	@Test
	public void testMethodInvocationOnOriginalEvent() {

		Component component = mock(Component.class);
		event = new MyEvent(component);

		when(component.getId()).thenReturn("componentId");

		Map<MultiKey, Class<? extends ICallbackEvent>> mapping = new HashMap<MultiKey, Class<? extends ICallbackEvent>>();
		mapping.put(CallbackMapper.createKey(event.getClass(), event.getSource().getId()), MyEventInterface.class);

		when(mappingCallbackAware.getEventsMapping()).thenReturn(mapping);

		CallbackMapper callbackMapper = new CallbackMapper(mappingCallbackAware);
		ICallbackEvent result = callbackMapper.remapEvent(event);
		result.markProcessed();
		assertTrue(result instanceof MyEventInterface);
		assertTrue(result.isProcessed());

	}

	@Test
	public void testRemapEventWithoutEventInMapping() {

		Component component = mock(Component.class);
		event = new MyEvent(component);

		when(component.getId()).thenReturn("componentId");

		Map<MultiKey, Class<? extends ICallbackEvent>> mapping = new HashMap<MultiKey, Class<? extends ICallbackEvent>>();
		mapping.put(CallbackMapper.createKey(OtherEvent.class, event.getSource().getId()), MyEventInterface.class);

		when(mappingCallbackAware.getEventsMapping()).thenReturn(mapping);

		CallbackMapper callbackMapper = new CallbackMapper(mappingCallbackAware);
		ICallbackEvent result = callbackMapper.remapEvent(event);
		assertFalse(result instanceof MyEventInterface);
		assertTrue(result instanceof MyEvent);
	}

	interface MyEventInterface extends ICallbackEvent {

	}

	class MyEvent extends AbstractCallbackEvent {

		public MyEvent(Component source) {
			super(source);
		}

	}

	class OtherEvent extends AbstractCallbackEvent {

		public OtherEvent(Component source) {
			super(source);
		}

	}
}
