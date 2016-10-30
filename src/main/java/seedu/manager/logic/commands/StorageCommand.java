package seedu.manager.logic.commands;

import java.io.File;

import seedu.manager.commons.core.EventsCenter;
import seedu.manager.commons.events.model.TaskManagerChangedEvent;
import seedu.manager.commons.events.storage.StorageLocationChangedEvent;

/**
 * @@author A0147924X
 * Allows user to change the storage location
 *
 */
public class StorageCommand extends Command {
	
	public static final String COMMAND_WORD = "storage";
	
    public static final String MESSAGE_SUCCESS = "Changed storage location to %1$s";
    public static final String MESSAGE_WRONG_EXTENSION =  "File must have a .xml extension";
    public static final String MESSAGE_CANNOT_CREATE = "Unable to create file, please check path provided";
    public static final String MESSAGE_ALREADY_EXISTS_SUCCESS = MESSAGE_SUCCESS
    			+ "\nWarning - file already exists, overwriting old data";
    public static final String MESSAGE_ALREADY_EXISTS_NO_OVERWRITE = "File already exists, and I don't have permission to overwrite it";
    public static final String MESSAGE_NO_PERMISSION = "I don't have permission to access this location";
    
    private String filePath;
    
    public StorageCommand(String filePath) {
		this.filePath = filePath;
	}

	@Override
	public CommandResult execute() {
		if (!hasXmlExtension(filePath)) {
			return new CommandResult(MESSAGE_WRONG_EXTENSION);
		}
		
		String feedbackToUser;
		if (doesfileAlreadyExist(filePath)) {
			if (canOverWriteExisting(filePath)) {
				feedbackToUser = String.format(MESSAGE_ALREADY_EXISTS_SUCCESS, filePath);
			} else {
				return new CommandResult(MESSAGE_ALREADY_EXISTS_NO_OVERWRITE);
			}
		} else {
			if (canWriteToFile(filePath)) {
				feedbackToUser = String.format(MESSAGE_SUCCESS, filePath);
			} else {
				return new CommandResult(MESSAGE_NO_PERMISSION);
			}
		}
		
		EventsCenter.getInstance().post(new StorageLocationChangedEvent(filePath));
		EventsCenter.getInstance().post(new TaskManagerChangedEvent(model.getTaskManager())); // save to new location
		return new CommandResult(feedbackToUser);
	}
	
	private boolean hasXmlExtension(String filePath) {
		return filePath.endsWith(".xml") && !filePath.equals("");
	}
	
	private boolean doesfileAlreadyExist(String filePath) {
		File file = new File(filePath);
		return file.exists();
	}
	
	private boolean canOverWriteExisting(String filePath) {
		try {
			File file = new File(filePath).getParentFile();
			return file.canWrite();
		} catch (Exception e) {
			return false;
		}
	}
	
	private boolean canWriteToFile(String filePath) {
		try {
			File file = new File(filePath).getParentFile();
			return file.canWrite();
		} catch (Exception e) {
			return false;
		}
	}
}
