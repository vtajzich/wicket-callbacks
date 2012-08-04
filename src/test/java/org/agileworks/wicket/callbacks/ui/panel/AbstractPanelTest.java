/**
 * 
 */
package org.agileworks.wicket.callbacks.ui.panel;

import org.agileworks.wicket.callbacks.domain.ICallbackEvent;
import org.agileworks.wicket.callbacks.domain.impl.AbstractCallback;
import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.application.ComponentInstantiationListenerCollection;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.settings.IDebugSettings;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.*;

/**
 * @author bednar
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ MarkupContainer.class, Application.class, WebPage.class })
public class AbstractPanelTest {

	private Application application;

	@Mock
	private Component component;

	@Before
	public void setUp() throws Exception {
		IDebugSettings debugSettings = mock(IDebugSettings.class);

        ComponentInstantiationListenerCollection componentInstantiationListeners = mock(ComponentInstantiationListenerCollection.class);

		MockitoAnnotations.initMocks(this);
		mockStatic(Application.class);
		application = mock(Application.class);
		when(Application.class, "get").thenReturn(application);
		when(application.getDebugSettings()).thenReturn(debugSettings);
        when(application.getComponentInstantiationListeners()).thenReturn(componentInstantiationListeners);
	}

	@SuppressWarnings("serial")
	class SampleEvent implements ICallbackEvent {

		boolean procesed = false;

		@Override
		public boolean isProcessed() {
			return procesed;
		}

		@Override
		public void markProcessed() {
			procesed = true;
		}

		@Override
		public Component getSource() {
			return component;
		}
	}

	@SuppressWarnings({ "serial", "rawtypes" })
	class SampleCallback extends AbstractCallback {

		private boolean called = false;

		@SuppressWarnings("unchecked")
		public SampleCallback() {
			super(SampleEvent.class);
		}

		@Override
		protected void doCallBack(ICallbackEvent event) {
			called = true;
		}

		public boolean isCalled() {
			return called;
		}

	}

	@SuppressWarnings("serial")
	@Test
	public void testSampleCallback() {
		IModel<Integer> model = new Model<Integer>(Integer.MAX_VALUE);
		SampleCallback callback = new SampleCallback();
		AbstractPanel<Integer> panel = new AbstractPanel<Integer>("SamplePanel", model) {
		};
		panel.add(callback);
		panel.onCallBack(new SampleEvent());
		assertTrue(callback.isCalled());
		assertEquals(model, panel.getModel());
		assertEquals(model.getObject(), panel.getModelObject());
	}

	@SuppressWarnings("serial")
	@Test
	public void testInnerPanelCallback() {
		IModel<Integer> model = new Model<Integer>(Integer.MAX_VALUE);
		SampleCallback callback = new SampleCallback();
		AbstractPanel<Integer> panel = new AbstractPanel<Integer>("SamplePanel", model) {
		};
		Panel noCallbackablePanel = new GenericPanel("otherPanel");
		AbstractPanel<Integer> innerPanel = new AbstractPanel<Integer>("InnerPanel") {
		};
		noCallbackablePanel.add(innerPanel);
		panel.add(noCallbackablePanel);
		panel.add(callback);
		innerPanel.onCallBack(new SampleEvent());
		assertTrue(callback.isCalled());
		assertEquals(model, panel.getModel());
		assertEquals(model.getObject(), panel.getModelObject());
	}

}
