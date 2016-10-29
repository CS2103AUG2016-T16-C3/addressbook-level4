package seedu.manager.commons.events.logic;

import seedu.manager.commons.events.BaseEvent;

/**
 * @@author A0147924X
 * Indicates that command words have changed
 *
 */
public class CommandWordsChangedEvent extends BaseEvent {

	public CommandWordsChangedEvent() {}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}

}
