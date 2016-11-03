package seedu.manager.logic.commands;

import seedu.manager.model.task.Task.TaskProperties;

// @@author A0147924X
/**
 * Allows user to unsort the displayed tasks, placing completed tasks at the bottom
 *
 */	
public class UnSortCommand extends Command implements UndoableCommand {
	public static final String COMMAND_WORD = "unsort";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Unsorts the task list, moving done tasks to the bottom.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Unsorted task list.";
    public static final String UNDO_SUCCESS = "Sorted task list.";
    
    public UnSortCommand() {}

	@Override
	public CommandResult execute() {
		model.unSortSortedFilteredTaskList();;
		this.addUndo(this);
		return new CommandResult(MESSAGE_SUCCESS);
	}

	@Override
	public CommandResult undoIt() {
		model.sortSortedFilteredTaskListByProperty(TaskProperties.PRIORITY);
	    return new CommandResult(UNDO_SUCCESS);
	}
}
