package seedu.manager.commons.events.storage;

import seedu.manager.commons.events.BaseEvent;

/**
 * Indicates that the storage location has changed
 * @author varungupta
 *
 */
public class StorageLocationChangedEvent extends BaseEvent {
	
	public final String filePath;
	
	public StorageLocationChangedEvent(String filePath) {
		this.filePath = filePath;
	}
	
	@Override
	public String toString() {
		return "The storage location has been changed to " + filePath;
	}
}
