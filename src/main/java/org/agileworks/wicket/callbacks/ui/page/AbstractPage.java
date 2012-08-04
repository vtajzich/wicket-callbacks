package org.agileworks.wicket.callbacks.ui.page;

import org.agileworks.wicket.callbacks.domain.IBroadcastCallbackEvent;
import org.agileworks.wicket.callbacks.domain.ICallback;
import org.agileworks.wicket.callbacks.domain.ICallbackEvent;
import org.agileworks.wicket.callbacks.domain.ITopCallbackAware;
import org.agileworks.wicket.callbacks.domain.impl.TopCallbackAware;
import org.agileworks.wicket.callbacks.ui.ApplicationVersion;

import org.apache.wicket.Application;
import org.apache.wicket.markup.html.WebPage;

public abstract class AbstractPage extends WebPage implements ITopCallbackAware {

	private final ITopCallbackAware callbackAware = new TopCallbackAware(this);

	@Override
	public final void add(ICallback<?> callBack) {
		callbackAware.add(callBack);
	}

	@Override
	public final void onBroadcastCallBack(IBroadcastCallbackEvent event) {
		callbackAware.onCallBack(event);
	}

	@Override
	public final void onCallBack(ICallbackEvent event) {
		callbackAware.onCallBack(event);
	}

	@Override
	public final void remove(ICallback<?> callback) {
		callbackAware.remove(callback);
	}

	@Override
	public final void proceedCallbackEvent(ICallbackEvent event) {
		callbackAware.proceedCallbackEvent(event);
	}

	private boolean isDevelopmentMode() {
		return Application.get().getConfigurationType().equals("development");
	}

	/**
	 * @return application version, and SCM revision
	 */
	public String getPageTitle() {
		if (!isDevelopmentMode()) {
			return "";
		}

		StringBuilder sb = new StringBuilder();
		if (ApplicationVersion.getVersion() != null && ApplicationVersion.getRevision() != null) {
			sb.append(" ver. ").append(ApplicationVersion.getVersion()).append(" (rev. ")
					.append(ApplicationVersion.getRevision()).append(")");
		}
		sb.append(" - SessionID: ").append(getSession().getId());
		return sb.toString();
	}

}
