package seedu.manager.model;

import java.util.Set;

import seedu.manager.commons.core.UnmodifiableObservableList;
import seedu.manager.model.task.ReadOnlyTask;
import seedu.manager.model.task.Task;
import seedu.manager.model.task.Tag;
import seedu.manager.model.task.UniqueTaskList;
import seedu.manager.model.tag.UniqueTagList;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyTaskManager newData);

    /** Returns the TaskManager */
    ReadOnlyTaskManager getTaskManager();

    /** Deletes the given task. */
    void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;

    /** Adds the given task */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;
    
    /** Adds the given tag */
    void addTag(Tag tag);

    /** Returns the sorted and filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getSortedFilteredTaskList();
    
    /** Returns the sorted and filtered tag list as an {@code UnmodifiableObservableList<Tag>} */
    UnmodifiableObservableList<Tag> getSortedFilteredTagList();
    
    /** Updates the filter of the sorted and filtered task list to show all tasks */
    void updateSortedFilteredListToShowAll();
    
    /** Updates the filter of the sorted and filtered task list to filter by the given keywords */
    void updateSortedFilteredTaskList(Set<String> keywords);
    
    /** Sorts the sorted and filtered task list by priority */
    void sortSortedFilteredTaskListByPriority();
    
    /** Unsort the sorted and filtered task list */
    void unSortSortedFilteredTaskList();
    
    /** Returns index of task in sorted and filtered list */
    int getIndexOfTask(ReadOnlyTask task);
}
