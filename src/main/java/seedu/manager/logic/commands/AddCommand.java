package seedu.manager.logic.commands;

import java.util.HashMap;
import java.util.Optional;

import seedu.manager.commons.exceptions.IllegalValueException;
import seedu.manager.model.task.*;
import seedu.manager.model.task.Task.TaskProperties;
import seedu.manager.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Adds a task to the task manager.
 */
public class AddCommand extends Command implements UndoableCommand {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the task manager. "
            + "Parameters: DESC [<extensions>] \n"
            + "Example: " + COMMAND_WORD
            + " Dinner with Lancelot venue Avalon after 8:30pm before 9:00pm priority med";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String UNDO_SUCCESS = "Previous added task deleted: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager";
    public static final String MESSAGE_INVALID_TAG = "This is an invalid tag";

    private final Task toAdd;
    
    // @@author A0147924X
    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(HashMap<TaskProperties, Optional<String>> properties)
            throws IllegalValueException {
        if (!properties.get(TaskProperties.DESC).isPresent()) {
            throw new IllegalValueException(MESSAGE_USAGE);
        }
        this.toAdd = new Task(properties);
        
    }
    
    // @@author A0148042M
    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.addTask(toAdd);
            addTasksTagToModel(toAdd);
            jumpToTask(toAdd);
            
            this.addUndo(this);
            
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd.getAsPrettyText()));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }
    }
    
    // @@author A0148003U
    @Override
	public CommandResult undoIt() {
    	assert model != null;
        try {
            model.deleteTask(toAdd);
            deleteTasksTagFromModel(toAdd);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }

        return new CommandResult(String.format(UNDO_SUCCESS, toAdd));
    }
}
