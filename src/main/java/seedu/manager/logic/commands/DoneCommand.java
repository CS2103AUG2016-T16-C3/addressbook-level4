package seedu.manager.logic.commands;

import java.util.HashMap;
import java.util.Optional;

import seedu.manager.commons.core.Messages;
import seedu.manager.commons.core.UnmodifiableObservableList;
import seedu.manager.commons.exceptions.IllegalValueException;
import seedu.manager.model.task.ReadOnlyTask;
import seedu.manager.model.task.Task;
import seedu.manager.model.task.Task.TaskProperties;
import seedu.manager.model.task.UniqueTaskList.TaskNotFoundException;

// @@author A0147924X
/**
 * Marks a task identified using it's last displayed index as done.
 * 
 */
public class DoneCommand extends Command implements UndoableCommand {

    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks a task identified by the index number used in the last task listing as done.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Marked Task as Done: %1$s";
    
    public static final String UNDO_SUCCESS = "Unmarked the Task to Undone."; 

    public final int targetIndex;
    
    public Task taskToUnmark;

    public DoneCommand(int targetIndex) {
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

        ReadOnlyTask taskToMark = lastShownList.get(targetIndex - 1);

        try {
            Task markedTask = markDonePropertyOfTask(taskToMark, true);
            model.deleteTask(taskToMark);
            model.addTask(markedTask);
            jumpToTask(markedTask);
            
            taskToUnmark = markedTask;
            this.addUndo(this);
            
            return new CommandResult(String.format(MESSAGE_SUCCESS, taskToMark));
        } catch (TaskNotFoundException pnfe) {
        	return new CommandResult("The target task cannot be missing");
        } catch (IllegalValueException e) {
			return new CommandResult(e.getMessage());
		}
    }
    
    // @@author
	@Override
	public CommandResult undoIt() {
	    assert model != null;

        try {
        	Task markedTask = markDonePropertyOfTask(taskToUnmark, false);
            model.deleteTask(taskToUnmark);
            model.addTask(markedTask);
            jumpToTask(markedTask);
        } catch (TaskNotFoundException pnfe) {
        	return new CommandResult("The target task cannot be missing");
        } catch (IllegalValueException e) {
			return new CommandResult(e.getMessage());
        }
		return new CommandResult (UNDO_SUCCESS);
	}
	
	/**
	 * @@author A0147924X
	 * Marks the done property of a task
	 * @param taskToMark The task which should be marked
	 * @param isDone Whether it should be marked as done or not done
	 * @return The new marked task
	 * @throws IllegalValueException
	 */
	private Task markDonePropertyOfTask(ReadOnlyTask taskToMark, boolean isDone) throws IllegalValueException {
		String done = isDone ? "Yes" : "No";
		HashMap<TaskProperties, Optional<String>> propsToEdit = taskToMark.getPropertiesAsStrings();
        propsToEdit.put(TaskProperties.DONE, Optional.of(done));
        return new Task(propsToEdit);
	}
}