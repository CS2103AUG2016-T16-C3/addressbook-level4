package seedu.manager.commons.events.storage;

import seedu.manager.commons.events.BaseEvent;
import seedu.manager.model.UserPrefs;

/**
 * @@author A0147924X
 * Indicates that User Preferences have changed
 *
 */
public class UserPrefsChangedEvent extends BaseEvent {
	
	private UserPrefs userPrefs;
	
	public UserPrefsChangedEvent(UserPrefs userPrefs) {
		this.userPrefs = userPrefs;
	}
	
	public UserPrefs getUserPrefs() {
		return userPrefs;
	}

	@Override
	public String toString() {
		return "User Preferences have changed";
	}

}
