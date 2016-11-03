package seedu.manager.logic.commands;


/**
 * Lists all tasks in the task manager to the user.
 */
public class ListCommand extends Command{

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all tasks";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all tasks. \n"
            + "Example: " + COMMAND_WORD;

    public ListCommand() {}

    @Override
    public CommandResult execute() {
        model.updateSortedFilteredTaskListToShowAll();
        model.unSortSortedFilteredTaskList();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
