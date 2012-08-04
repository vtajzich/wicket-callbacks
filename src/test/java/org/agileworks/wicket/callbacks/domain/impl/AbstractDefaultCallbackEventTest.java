package org.agileworks.wicket.callbacks.domain.impl;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class AbstractDefaultCallbackEventTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetSetArgument() {
		AjaxRequestTarget requestTarget = mock(AjaxRequestTarget.class);
		Object sampleValue = mock(Object.class);
		Object otherValue = mock(Object.class);
		Component component = mock(Component.class);

		AbstractDefaultCallbackEvent<Object> event = new AbstractDefaultCallbackEvent<Object>(component, requestTarget,
				sampleValue) {
		};

		assertEquals(sampleValue, event.getArgument());

		event.setArgument(otherValue);
		assertEquals(otherValue, event.getArgument());

		assertEquals(requestTarget, event.getAjaxRequestTarget());
	}

}
