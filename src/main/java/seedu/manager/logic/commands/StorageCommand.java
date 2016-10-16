package seedu.manager.logic.commands;

import seedu.manager.commons.core.EventsCenter;
import seedu.manager.commons.events.storage.StorageLocationChangedEvent;

public class StorageCommand extends Command {
	
	public static final String COMMAND_WORD = "storage";
	
    public static final String MESSAGE_SUCCESS = "Changed storage location to %1$s";
    
    private String filePath;
    
    public StorageCommand(String filePath) {
		this.filePath = filePath;
	}

	@Override
	public CommandResult execute() {
		EventsCenter.getInstance().post(new StorageLocationChangedEvent(filePath));
		return new CommandResult(String.format(MESSAGE_SUCCESS, filePath));
	}
}
