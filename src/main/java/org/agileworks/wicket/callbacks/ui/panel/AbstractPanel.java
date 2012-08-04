/**
 * 
 */
package org.agileworks.wicket.callbacks.ui.panel;

import org.agileworks.wicket.callbacks.container.IPropagatedCommand;
import org.agileworks.wicket.callbacks.container.PropagateCommand;
import org.agileworks.wicket.callbacks.domain.*;
import org.agileworks.wicket.callbacks.domain.impl.CallbackAware;
import org.agileworks.wicket.callbacks.domain.impl.CallbackMapper;
import org.apache.commons.collections.keyvalue.MultiKey;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import java.util.Map;

/**
 * @author tajzich
 */
public abstract class AbstractPanel<T> extends Panel implements IBroadcastCallbackAware, IMappingCallbackAware {

	private static final long serialVersionUID = -14928467128294245L;
	private final IBroadcastCallbackAware callbackAware = new CallbackAware(this);
	private final CallbackMapper callbackMapper = new CallbackMapper(this);

	private PropagateCommand modelChangedPropagate;

	private boolean onModelChangedPropagation;
	private PropagateCommand modelChangingPropagate;

	/**
	 * @param id
	 */
	public AbstractPanel(final String id) {
		super(id);
	}

	/**
	 * @param id
	 * @param model
	 */
	public AbstractPanel(final String id, final IModel<? extends T> model) {
		super(id, model);
	}

	/**
	 * @param id
	 */
	public AbstractPanel(final String id, final boolean onModelChangedPropagation) {
		super(id);
		this.onModelChangedPropagation = onModelChangedPropagation;
    }

	/**
	 * @param id
	 * @param model
	 */
	public AbstractPanel(final String id, final IModel<? extends T> model, final boolean onModelChangedPropagation) {
		super(id, model);
		this.onModelChangedPropagation = onModelChangedPropagation;
	}

    @Override
    protected void onInitialize() {
        super.onInitialize();

        init();
    }

    @Override
	public final void add(final ICallback<?> callBack) {
		callbackAware.add(callBack);
	}

	@Override
	public final void remove(final ICallback<?> callback) {
		callbackAware.remove(callback);
	}

	@SuppressWarnings("unchecked")
	public final IModel<T> getModel() {
		return (IModel<T>) getDefaultModel();
	}

	@SuppressWarnings("unchecked")
	public final T getModelObject() {
		return (T) getDefaultModelObject();
	}

	private void init() {
		setOutputMarkupId(true);
		modelChangedPropagate = new PropagateCommand(this, new IPropagatedCommand() {

			private static final long serialVersionUID = -5136859209397202787L;

			@Override
			public void executeCommandOn(final Component component) {
				component.modelChanged();
			}
		}, onModelChangedPropagation);

		modelChangingPropagate = new PropagateCommand(this, new IPropagatedCommand() {

			private static final long serialVersionUID = -8903342284801147829L;

			@Override
			public void executeCommandOn(final Component component) {
				component.modelChanging();
			}
		}, onModelChangedPropagation);

	}

	@Override
	public final void onCallBack(final ICallbackEvent event) {
		callbackAware.onCallBack(event);
	}

	@Override
	public final void onBroadcastCallBack(final IBroadcastCallbackEvent event) {
		callbackAware.onBroadcastCallBack(event);
	}

	@Override
	protected void onModelChanged() {
		modelChangedPropagate.executeCommand();
	}

	@Override
	protected void onModelChanging() {
		modelChangingPropagate.executeCommand();
	}

	@Override
	public void onEventRemapped(final ICallbackEvent event) {

	}

	@Override
	public ICallbackEvent remapEvent(final ICallbackEvent event) {
		return callbackMapper.remapEvent(event);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * e.g. mapping.put(CallbackMapper.createKey(event), MyEventInterface.class);
	 * */
	@Override
	public Map<MultiKey, Class<? extends ICallbackEvent>> getEventsMapping() {
		return IMappingCallbackAware.EMPTY_EVENT_MAPPING;
	}
}
