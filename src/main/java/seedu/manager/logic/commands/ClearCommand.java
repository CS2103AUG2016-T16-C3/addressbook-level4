package seedu.manager.logic.commands;

import seedu.manager.model.TaskManager;

/**
 * Clears the task manager.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Task manager has been cleared!";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Clears all tasks in the task manager. \n"
            + "Example: " + COMMAND_WORD;

    public ClearCommand() {}


    @Override
    public CommandResult execute() {
        assert model != null;
        model.resetData(TaskManager.getEmptyTaskManager());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
