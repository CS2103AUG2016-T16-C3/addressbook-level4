package seedu.manager.logic.commands;

public class SortCommand extends Command implements UndoableCommand{
/**
 * @@author A0147924X
 * Allows user to sort the displayed tasks by priority
 *
 */	
	public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts the displayed tasks by priority, from highest to lowest. Tasks with no priority are shown last.\n"
            + "Parameters: None\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Sorted task list.";
    public static final String UNDO_SUCCESS = "Unsorted task list.";
    public SortCommand() {}

	@Override
	public CommandResult execute() {
		model.sortSortedFilteredTaskListByPriority();
		this.addUndo(this);
		return new CommandResult(MESSAGE_SUCCESS);
	}

	@Override
	public CommandResult undoIt() {
		model.unSortSortedFilteredTaskList();
	    return new CommandResult(UNDO_SUCCESS);
	}
}
