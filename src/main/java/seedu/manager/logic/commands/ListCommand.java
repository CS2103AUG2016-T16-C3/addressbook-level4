package seedu.manager.logic.commands;


/**
 * Lists all tasks in the task manager to the user.
 */
public class ListCommand extends Command{

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all tasks";

    public ListCommand() {}

    @Override
    public CommandResult execute() {
        model.updateSortedFilteredListToShowAll();
        model.unSortSortedFilteredTaskList();
        return new CommandResult(MESSAGE_SUCCESS);
    }
    
    @Override
    public int undoability() {
    	return 0;
    }

	@Override
	public CommandResult undoIt() {
		// TODO Auto-generated method stub
		return null;
	}

}
