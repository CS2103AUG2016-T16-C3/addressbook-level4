package seedu.manager.logic.commands;

import seedu.manager.model.task.Task.TaskProperties;

// @@author A0147924X
/**
 * Allows user to sort the displayed tasks by priority
 *
 */	
public class SortCommand extends Command implements UndoableCommand{
	public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts the displayed tasks by priority, from highest to lowest. Tasks with no priority are shown last.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Sorted task list.";
    public static final String UNDO_SUCCESS = "Unsorted task list.";
    
    public SortCommand() {}

	@Override
	public CommandResult execute() {
		model.sortSortedFilteredTaskListByProperty(TaskProperties.PRIORITY);
		this.addUndo(this);
		return new CommandResult(MESSAGE_SUCCESS);
	}
    
    // @@ author A0148003U
	@Override
	public CommandResult undoIt() {
		model.unSortSortedFilteredTaskList();
	    return new CommandResult(UNDO_SUCCESS);
	}
}
