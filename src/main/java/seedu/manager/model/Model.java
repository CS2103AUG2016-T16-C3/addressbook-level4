package seedu.manager.model;

import java.util.HashMap;
import java.util.Optional;

import seedu.manager.commons.core.UnmodifiableObservableList;
import seedu.manager.commons.core.CommandWord.Commands;
import seedu.manager.commons.exceptions.IllegalValueException;
import seedu.manager.model.task.ReadOnlyTask;
import seedu.manager.model.task.Task;
import seedu.manager.model.task.Tag;
import seedu.manager.model.task.UniqueTaskList;
import seedu.manager.model.task.TaskProperty;
import seedu.manager.model.task.Task.TaskProperties;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyTaskManager newData);

    /** Returns the TaskManager */
    ReadOnlyTaskManager getTaskManager();
    
    // @@author A0147924X
    /** Returns the Command words */
    HashMap<Commands, String> getCommandWords();
    
    /** Returns the Extension words */
    HashMap<Commands, String> getExtensionWords();
    
    // @@author
    /** Deletes the given task. */
    void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;
    
    // @@author A0148042M
    /** Deletes the given tag. */
    void deleteTag(Tag tag);
    
    // @@author
    /** Adds the given task */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;
    
    // @@author A0148042M
    /** Adds the given tag */
    void addTag(Tag tag);
    
    // @@author A0147924X
    /**
     * Gets the alias for a certain command from the user preferences
     * @param command Command for which alias will be returned 
     * @return Alias of the command
     */
    public String getAliasForCommand(Commands command);
    
    /**
     * Changes a command to a given alias in the user preferences
	 * @param commandToChange The command which should be aliased
	 * @param alias The alias which will be assigned to the command
	 * @param messageNoMatch Message used in error which will be thrown if there is no matching command
	 * @param messageAliasAlreadyTaken Message used in error which will be thrown if alias is already taken
	 * @throws IllegalValueException
     */
    public void setSingleCommandWord(String commandToChange, String alias,
    		String messageNoMatch, String messageAliasAlreadyTaken) throws IllegalValueException;
    
    // @@author A0148042M
    /** Returns the sorted and filtered tag list as an {@code UnmodifiableObservableList<Tag>} */
    UnmodifiableObservableList<Tag> getSortedFilteredTagList();
    
    // @@author A0147924X
    /**
     * Get the index of a specified tag in the list currently being displayed
     * @param tag Tag whose index will be returned
     * @return index of the tag
     */
    int getIndexOfTag(Tag tag);
    
    // @@author
    /** Returns the sorted and filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getSortedFilteredTaskList();
    
    /** Updates the filter of the sorted and filtered task list to show all tasks */
    void updateSortedFilteredTaskListToShowAll();
    
    /** Updates the filter of the sorted and filtered task list to filter by the given properties from the find function*/
    void updateFilteredTaskList(HashMap<TaskProperties, Optional<TaskProperty>> propertiesToMatch);
    
    // @@author A0147924X
    /** Sorts the sorted and filtered task list by priority */
    void sortSortedFilteredTaskListByPriority();
    
    /** Sorts the sorted and filtered task list by time */
    void sortSortedFilteredTaskListByTime();
    
    /** 
     * Get the index of a specified tag in the list currently being displayed
     * @param task Task whose index will be returned
     * @return index of the task 
     */
    int getIndexOfTask(ReadOnlyTask task);
}
