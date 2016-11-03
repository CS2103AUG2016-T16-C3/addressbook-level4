package seedu.manager.commons.events.storage;

import seedu.manager.commons.events.BaseEvent;

// @@author A0147924X
/**
 * Indicates that the TaskManager Storage location has changed in the Config
 *
 */
public class ConfigFilePathChangedEvent extends BaseEvent {
	private final String filePath;
	
	public ConfigFilePathChangedEvent(String filePath) {
		this.filePath = filePath;
	}
	
	public String getFilePath() {
		return filePath;
	}
	
	@Override
	public String toString() {
		return "Storage location in Config has changed to " + filePath;
	}
}
