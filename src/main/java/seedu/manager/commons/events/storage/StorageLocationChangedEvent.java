package seedu.manager.commons.events.storage;

import seedu.manager.commons.events.BaseEvent;

// @@author A0147924X
/**
 * Indicates that the storage location has changed
 *
 */
public class StorageLocationChangedEvent extends BaseEvent {
	
	private final String filePath;
	
	public StorageLocationChangedEvent(String filePath) {
		this.filePath = filePath;
	}
	
	public String getFilePath() {
		return filePath;
	}
	
	@Override
	public String toString() {
		return "The storage location has been changed to " + filePath;
	}
}
