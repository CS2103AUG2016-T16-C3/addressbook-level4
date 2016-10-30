package seedu.manager.logic.commands;

import java.util.HashMap;
import java.util.Optional;

import seedu.manager.commons.core.EventsCenter;
import seedu.manager.commons.core.Messages;
import seedu.manager.commons.core.UnmodifiableObservableList;
import seedu.manager.commons.events.ui.JumpToTaskListRequestEvent;
import seedu.manager.commons.exceptions.IllegalValueException;
import seedu.manager.model.task.ReadOnlyTask;
import seedu.manager.model.task.Task;
import seedu.manager.model.task.Task.TaskProperties;
import seedu.manager.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * @@author A0147924X
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
        HashMap<TaskProperties, Optional<String>> propsToEdit = taskToMark.getPropertiesAsStrings();
        propsToEdit.put(TaskProperties.DONE, Optional.of("Yes"));

        try {
            Task markedTask = new Task(propsToEdit);
            taskToUnmark = markedTask;
            model.deleteTask(taskToMark);
            model.addTask(markedTask);
            
            int newIndex = model.getIndexOfTask(markedTask);
            assert newIndex != -1;
            
            EventsCenter.getInstance().post(new JumpToTaskListRequestEvent(newIndex));

        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        } catch (IllegalValueException e) {
			return new CommandResult(e.getMessage());
		}
        this.addUndo(this);
        return new CommandResult(String.format(MESSAGE_SUCCESS, taskToMark));
    }

	@Override
	public CommandResult undoIt() {
	    assert model != null;
	    
	    HashMap<TaskProperties, Optional<String>> propsToEdit = taskToUnmark.getPropertiesAsStrings();
        propsToEdit.put(TaskProperties.DONE, Optional.of("No"));

        try {
            model.deleteTask(taskToUnmark);
            model.addTask(new Task(propsToEdit));
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        } catch (IllegalValueException e) {
			return new CommandResult(e.getMessage());
        }
		return new CommandResult (UNDO_SUCCESS);
	}
}