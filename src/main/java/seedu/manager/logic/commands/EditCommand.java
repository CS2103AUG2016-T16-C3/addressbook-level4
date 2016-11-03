package seedu.manager.logic.commands;

import java.util.HashMap;
import java.util.Optional;

import seedu.manager.commons.core.Messages;
import seedu.manager.commons.core.UnmodifiableObservableList;
import seedu.manager.commons.exceptions.IllegalValueException;
import seedu.manager.model.task.ReadOnlyTask;
import seedu.manager.model.task.Task;
import seedu.manager.model.task.Tag;
import seedu.manager.model.task.UniqueTaskList;
import seedu.manager.model.task.Task.TaskProperties;
import seedu.manager.model.task.UniqueTaskList.TaskNotFoundException;

// @@author A0147924X
/**
 * Allows tasks to be edited. Uses an index to extract the task to be edited and changes its
 * properties according to the new properties given
 *
 */
public class EditCommand extends Command implements UndoableCommand {
    
    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer) [DESC] [<extensions>]\n"
            + "Example: " + COMMAND_WORD + " 1 Dinner with Guinevere venue Under the Stars priority high";

    public static final String MESSAGE_SUCCESS = "Edited Task: %1$s";
    public static final String MESSAGE_DUPLICATE_PARAMS = "The new parameters are the same as before";
    public static final String UNDO_SUCCESS = "Undone the previous edit: %1$s";

    public final int targetIndex;
    
    private final HashMap<TaskProperties, Optional<String>> editedProperties;

    public EditCommand(int targetIndex, HashMap<TaskProperties, Optional<String>> editedProperties) 
            throws IllegalValueException {
        this.targetIndex = targetIndex;
        this.editedProperties = editedProperties;
    }

    public Task newTask;
    public Task oldTask;

    @Override
    public CommandResult execute() {
        assert model != null;
        
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getSortedFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToEdit = lastShownList.get(targetIndex - 1);
        
        try {
        	HashMap<TaskProperties, Optional<String>> newProperties = 
        		buildNewPropsFromOldAndEdited(taskToEdit.getPropertiesAsStrings(), editedProperties);
        	
            newTask = new Task(newProperties);
            oldTask = new Task(taskToEdit);
            
            model.addTask(newTask);
            model.deleteTask(taskToEdit);
            
            if(newTask.getTag().isPresent()) {
                model.addTag((Tag) newTask.getTag().get());
            }
            if(taskToEdit.getTag().isPresent()) {
                model.deleteTag((Tag) taskToEdit.getTag().get());
            }
            
            jumpToTask(newTask);
            this.addUndo(this);
            
            return new CommandResult(String.format(MESSAGE_SUCCESS, newTask.getAsPrettyText()));
        } catch (TaskNotFoundException e) {
            return new CommandResult("The target task cannot be missing");
        } catch (UniqueTaskList.DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_PARAMS);
        } catch (IllegalValueException e) {
			return new CommandResult(e.getMessage());
		}
    }
    
    /**
     * Builds a HashMap of new properties using old properties of task and new properties entered
     * by user
     * 
     * @param oldProperties
     * @param editedProperties
     */
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
    
    // @@author
    @Override
    public CommandResult undoIt() {
    	assert model != null;
    	
    	try {
    		model.addTask(oldTask);
    		model.deleteTask(newTask);
    		
            if(oldTask.getTag().isPresent()) {
                model.addTag((Tag) oldTask.getTag().get());
            }
            if(newTask.getTag().isPresent()) {
                model.deleteTag((Tag) newTask.getTag().get());
            }
            
            jumpToTask(oldTask);
    		
            return new CommandResult(String.format(UNDO_SUCCESS, oldTask));
        } catch (TaskNotFoundException e) {
            return new CommandResult("The target task cannot be missing");
        } catch (UniqueTaskList.DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_PARAMS);
        }
    	
    }
    
}
