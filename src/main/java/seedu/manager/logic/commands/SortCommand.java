package seedu.manager.logic.commands;

public class SortCommand extends Command {
	
	public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts the displayed tasks by priority, from highest to lowest. Tasks with no priority are shown last.\n"
            + "Parameters: None\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Sorted Tasks";
    
    public SortCommand() {}

	@Override
	public CommandResult execute() {
		model.sortSortedFilteredTaskListByPriority();
		return new CommandResult(MESSAGE_SUCCESS);
	}

}
