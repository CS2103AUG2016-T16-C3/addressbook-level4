package seedu.manager.logic.commands;

import java.io.File;

import seedu.manager.commons.core.EventsCenter;
import seedu.manager.commons.events.storage.StorageLocationChangedEvent;

public class StorageCommand extends Command {
	
	public static final String COMMAND_WORD = "storage";
	
    public static final String MESSAGE_SUCCESS = "Changed storage location to %1$s. "
    		+ "Make a change to Task Ninja to save your data in the new location";
    
    public static final String MESSAGE_WRONG_EXTENSION =  "File must have a .xml extension";
    public static final String MESSAGE_CANNOT_CREATE = "Unable to create file, please check path provided";
    public static final String MESSAGE_ALREADY_EXISTS_SUCCESS = MESSAGE_SUCCESS
    			+ ".\nWarning - file already exists, please check that the old file does not contain any important information."
    			+ "\nIf you wish to undo this action, change storage location BEFORE making any changes to the data and BEFORE exiting Task Ninja";
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
		if (fileAlreadyExists(filePath)) {
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
		return new CommandResult(feedbackToUser);
	}
	
	private boolean hasXmlExtension(String filePath) {
		return filePath.endsWith(".xml") && !filePath.equals("");
	}
	
	private boolean fileAlreadyExists(String filePath) {
		File file = new File(filePath);
		System.out.println(file.exists());
		return file.exists();
	}
	
	private boolean canOverWriteExisting(String filePath) {
		File file = new File(filePath).getParentFile();
		return file.canWrite();
	}
	
	private boolean canWriteToFile(String filePath) {
		File file = new File(filePath).getParentFile();
		return file.canWrite();
	}
	
	@Override
	public int undoability() {
	    return 0;
	    }

	@Override
	public CommandResult undoIt() {
		return null;
	}

}
