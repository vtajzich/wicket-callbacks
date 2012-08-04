/**
 * 
 */
package org.agileworks.wicket.callbacks.domain.impl;

import org.agileworks.wicket.callbacks.domain.IBroadcastCallbackAware;
import org.agileworks.wicket.callbacks.domain.IBroadcastCallbackEvent;
import org.agileworks.wicket.callbacks.domain.ICallback;
import org.agileworks.wicket.callbacks.domain.ICallbackEvent;
import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.settings.IDebugSettings;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.*;

/**
 * @author tajzich
 * 
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ MarkupContainer.class, Application.class, WebPage.class })
public class CallbackAwareTest {

	private CallbackAware callbackAware;

	private TestContainer markupContainer;

	private Application application;

	private WebPage webPage;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {

		IDebugSettings debugSettings = mock(IDebugSettings.class);

		mockStatic(Application.class);
		application = mock(Application.class);
		when(Application.class, "get").thenReturn(application);
		when(application.getDebugSettings()).thenReturn(debugSettings);

		webPage = mock(WebPage.class);

		markupContainer = mock(TestContainer.class);
		callbackAware = new CallbackAware(markupContainer);
		when(markupContainer.getAware()).thenReturn(callbackAware);
		when(markupContainer.getPage()).thenReturn(webPage);
	}

	/**
	 * Test method for
	 * {@link org.agileworks.wicket.callbacks.domain.impl.CallbackAware#onBroadcastCallBack(org.agileworks.wicket.callbacks.domain.ICallbackEvent, org.apache.wicket.Component)}
	 * .
	 */
	@Test
	public void testOnCallBackICallbackEventComponent() {

		doAnswer(Mockito.CALLS_REAL_METHODS).when(markupContainer).onCallBack(any(IBroadcastCallbackEvent.class));

		IBroadcastCallbackEvent event = mock(IBroadcastCallbackEvent.class);

		markupContainer.onCallBack(event);

		Mockito.verify(webPage).visitChildren(any(BroadcastVisitor.class));
	}

	abstract class TestContainer extends WebMarkupContainer implements IBroadcastCallbackAware {

		private final IBroadcastCallbackAware aware;

		public TestContainer(final IBroadcastCallbackAware aware) {
			super("ahoj");
			this.aware = aware;
		}

		@Override
		public void onBroadcastCallBack(final IBroadcastCallbackEvent event) {
			getAware().onBroadcastCallBack(event);
		}

		@Override
		public void add(final ICallback<?> callBack) {
			getAware().add(callBack);
		}

		@Override
		public void onCallBack(final ICallbackEvent event) {
			getAware().onCallBack(event);
		}

		@Override
		public void remove(final ICallback<?> callback) {
			getAware().remove(callback);
		}

		public IBroadcastCallbackAware getAware() {
			return aware;
		}

	}

	class TestAware extends Component implements IBroadcastCallbackAware {

		private final IBroadcastCallbackAware aware;

		public TestAware(final IBroadcastCallbackAware aware) {
			super("test");
			this.aware = aware;
		}

		@Override
		public void onBroadcastCallBack(final IBroadcastCallbackEvent event) {
			getAware().onBroadcastCallBack(event);
		}

		@Override
		public void add(final ICallback<?> callBack) {
			getAware().add(callBack);
		}

		@Override
		public void onCallBack(final ICallbackEvent event) {
			getAware().onCallBack(event);
		}

		@Override
		public void remove(final ICallback<?> callback) {
			getAware().remove(callback);
		}

		public IBroadcastCallbackAware getAware() {
			return aware;
		}

        @Override
        protected void onRender() {
        }
    }


}
