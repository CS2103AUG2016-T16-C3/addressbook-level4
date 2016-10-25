package seedu.manager.logic.commands;

import seedu.manager.commons.core.Messages;
import seedu.manager.commons.core.UnmodifiableObservableList;
import seedu.manager.model.task.ReadOnlyTask;
import seedu.manager.model.task.Task;
import seedu.manager.model.task.UniqueTaskList;
import seedu.manager.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Deletes a task identified using it's last displayed index from the task manager.
 */
public class DeleteCommand extends Command implements UndoableCommand {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Deleted Task: %1$s";
    public static final String UNDO_SUCCESS = "Undone the previous deleting: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This task already exists in the task manager";

    public final int targetIndex;
    public Task taskToDelete;

    public DeleteCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult execute() {
        assert model != null;

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getSortedFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToDelete = lastShownList.get(targetIndex - 1);
        this.taskToDelete = new Task (taskToDelete);

        try {
            model.deleteTask(taskToDelete);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }
        this.addUndo(this);
        return new CommandResult(String.format(MESSAGE_SUCCESS, taskToDelete.getAsPrettyText()));
    }
    
    @Override
    public CommandResult undoIt() {
    	 assert model != null;
         try {
             model.addTask(taskToDelete);
             return new CommandResult(String.format(UNDO_SUCCESS, taskToDelete));
         } catch (UniqueTaskList.DuplicateTaskException e) {
             return new CommandResult(MESSAGE_DUPLICATE_PERSON);
        }
    }
}

