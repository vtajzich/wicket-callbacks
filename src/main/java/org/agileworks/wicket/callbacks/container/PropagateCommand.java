/**
 * 
 */
package org.agileworks.wicket.callbacks.container;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;

import java.io.Serializable;
import java.util.Iterator;

/**
 * @author tajzich, bednar
 * 
 */
public final class PropagateCommand implements Serializable {

	private static final long serialVersionUID = 41055023244464399L;

	private final MarkupContainer container;
	private final boolean propagateCommandExecution;

	private final IPropagatedCommand command;

	public PropagateCommand(MarkupContainer container, IPropagatedCommand command,
			boolean propagateCommandExecution) {
		this.container = container;
		this.command = command;
		this.propagateCommandExecution = propagateCommandExecution;
	}

	public void executeCommand() {

		if (!propagateCommandExecution) {
			return;
		}

		Iterator<? extends Component> componentIterator = container.iterator();
		while (componentIterator.hasNext()) {

			Component component = componentIterator.next();
			command.executeCommandOn(component);
		}
	}
}
