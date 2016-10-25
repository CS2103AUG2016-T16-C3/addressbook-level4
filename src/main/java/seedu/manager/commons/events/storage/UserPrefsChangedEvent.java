package seedu.manager.commons.events.storage;

import seedu.manager.commons.events.BaseEvent;
import seedu.manager.model.UserPrefs;

public class UserPrefsChangedEvent extends BaseEvent {
	
	public UserPrefs userPrefs;
	
	public UserPrefsChangedEvent(UserPrefs userPrefs) {
		this.userPrefs = userPrefs;
	}

	@Override
	public String toString() {
		return "User Preferences have changed";
	}

}
