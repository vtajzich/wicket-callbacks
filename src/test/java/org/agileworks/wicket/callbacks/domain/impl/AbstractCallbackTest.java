package org.agileworks.wicket.callbacks.domain.impl;

import org.agileworks.wicket.callbacks.domain.ICallbackEvent;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class AbstractCallbackTest {

	private Component component;

	@Before
	public void setUp() throws Exception {
		component = mock(Component.class);
	}

	@Test
	public void testDoCallBack() {
		final List<Object> list = new ArrayList<Object>();

		@SuppressWarnings("serial")
		final class SampleEvent extends AbstractCallbackEvent {

			public SampleEvent(Component component) {
				super(component);
			}
		}

		SampleEvent event = new SampleEvent(component);
		ICallbackEvent someOtherEvent = new AbstractCallbackEvent(component) {
		};

		AbstractCallback callback = new AbstractCallback(SampleEvent.class) {

			@Override
			protected void doCallBack(ICallbackEvent event) {
				list.add(getDefaultArgument(event));
				assertEquals(component, event.getSource());
			}
		};

		callback.callBack(event);
		callback.callBack(someOtherEvent);

		assertEquals(1, list.size());
		assertNull(list.get(0));
		assertTrue(event.isProcessed());
		assertFalse(someOtherEvent.isProcessed());
	}

	@Test
	public void testDoCallBackWithdefaultParam() {
		final List<Object> list = new ArrayList<Object>();

		class SampleAjaxEvent extends AbstractDefaultCallbackEvent<Object> {

			public SampleAjaxEvent(Component component, AjaxRequestTarget ajaxRequestTarget, Object param) {
				super(component, ajaxRequestTarget, param);
			}
		}

		Object param = new Object();
		SampleAjaxEvent event = new SampleAjaxEvent(component, null, param);
		ICallbackEvent someOtherEvent = new AbstractCallbackEvent(component) {
		};

		AbstractCallback callback = new AbstractCallback(SampleAjaxEvent.class) {

			@Override
			protected void doCallBack(ICallbackEvent event) {
				list.add(getDefaultArgument(event));
				assertEquals(component, event.getSource());
			}
		};

		callback.callBack(event);
		callback.callBack(someOtherEvent);

		assertEquals(1, list.size());
		assertEquals(param, list.get(0));
	}
}
