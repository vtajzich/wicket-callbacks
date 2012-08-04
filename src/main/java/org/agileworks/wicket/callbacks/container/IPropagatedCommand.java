package org.agileworks.wicket.callbacks.container;

import org.apache.wicket.Component;

import java.io.Serializable;

public interface IPropagatedCommand extends Serializable {

	void executeCommandOn(Component component);
}
