package seedu.manager.logic.commands;

import java.util.HashMap;
import java.util.Optional;

import seedu.manager.commons.core.Messages;
import seedu.manager.commons.core.UnmodifiableObservableList;
import seedu.manager.commons.exceptions.IllegalValueException;
import seedu.manager.model.task.ReadOnlyTask;
import seedu.manager.model.task.Task;
import seedu.manager.model.task.UniqueTaskList;
import seedu.manager.model.task.Task.TaskProperties;
import seedu.manager.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Allows tasks to be edited. Uses an index to extract the task to be edited and changes its
 * properties according to the new properties given
 * @author varungupta
 *
 */
public class EditCommand extends Command {
    
    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer) [DESC] [<extensions>]\n"
            + "Example: " + COMMAND_WORD + " 1 Dinner with Guinevere venue Under the Stars priority high";

    public static final String MESSAGE_SUCCESS = "Edited Task: %1$s";
    public static final String MESSAGE_DUPLICATE_PARAMS = "The new parameters are the same as before";

    public final int targetIndex;
    
    private final HashMap<TaskProperties, Optional<String>> editedProperties;

    public EditCommand(int targetIndex, HashMap<TaskProperties, Optional<String>> editedProperties) 
            throws IllegalValueException {
        this.targetIndex = targetIndex;
        this.editedProperties = editedProperties;
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToEdit = lastShownList.get(targetIndex - 1);
        
        try {
            Task newTask = new Task(buildNewPropsFromOldAndEdited(taskToEdit.getPropertiesAsStrings(), editedProperties));
            model.addTask(newTask);
            model.deleteTask(taskToEdit);
            return new CommandResult(String.format(MESSAGE_SUCCESS, newTask));
        } catch (TaskNotFoundException e) {
            return new CommandResult("The target task cannot be missing");
        } catch (UniqueTaskList.DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_PARAMS);
        } catch (IllegalValueException e) {
			return new CommandResult(e.getMessage());
		}
    }
    
    private HashMap<TaskProperties, Optional<String>> buildNewPropsFromOldAndEdited(
            HashMap<TaskProperties, Optional<String>> oldProperties, 
            HashMap<TaskProperties, Optional<String>> editedProperties
            ) {
        HashMap<TaskProperties, Optional<String>> newProperties = new HashMap<>();
        
        for (TaskProperties prop : TaskProperties.values()) {
            if (editedProperties.get(prop).isPresent()) {
                newProperties.put(prop, editedProperties.get(prop));
            } else {
                newProperties.put(prop, oldProperties.get(prop));
            }
        }
        
        return newProperties;
    }
}
