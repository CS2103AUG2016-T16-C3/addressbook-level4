package seedu.manager.model;

import javafx.collections.ObservableList;
import seedu.manager.model.task.ReadOnlyTask;
import seedu.manager.model.task.Task;
import seedu.manager.model.task.Tag;
import seedu.manager.model.task.UniqueTaskList;
import seedu.manager.model.tag.UniqueTagList;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Wraps all data at the task-manager level
 * Duplicates are not allowed (by .equals comparison)
 */
public class TaskManager implements ReadOnlyTaskManager {

    private final UniqueTaskList tasks;
    private final UniqueTagList tags;

    {
        tasks = new UniqueTaskList();
        tags = new UniqueTagList();
    }

    public TaskManager() {}

    /**
     * Tasks are copied into this taskmanager
     */
    public TaskManager(ReadOnlyTaskManager toBeCopied) {
        this(toBeCopied.getUniqueTaskList());
    }

    /**
     * Tasks are copied into this taskmanager
     */
    public TaskManager(UniqueTaskList tasks) {
        resetData(tasks.getInternalList());
    }

    public static ReadOnlyTaskManager getEmptyTaskManager() {
        return new TaskManager();
    }

//// list overwrite operations

    public ObservableList<Task> getTasks() {
        return tasks.getInternalList();
    }
    
    public ObservableList<Tag> getTags() {
        return tags.getInternalList();
    }

    public void setTasks(List<Task> tasks) {
        this.tasks.getInternalList().setAll(tasks);
    }

    public void resetData(Collection<? extends ReadOnlyTask> newTasks)  {
        setTasks(newTasks.stream().map(Task::new).collect(Collectors.toList()));
    }

    public void resetData(ReadOnlyTaskManager newData) {
        resetData(newData.getTaskList());
    }

//// task-level operations

    /**
     * Adds a task to the task manager.
     *
     * @throws UniqueTaskList.DuplicateTaskException if an equivalent task already exists.
     */
    public void addTask(Task p) throws UniqueTaskList.DuplicateTaskException {
        tasks.add(p);
    }
    
    /**
     * Add a tag to the task manager.
     *
     * @throws UniqueTagList.DuplicateTagException if an equivalent tag already exists.
     */
    public void addTag(Tag tag) throws UniqueTagList.DuplicateTagException {
        tags.add(tag);
    }

    public boolean removeTask(ReadOnlyTask key) throws UniqueTaskList.TaskNotFoundException {
        if (tasks.remove(key)) {
            return true;
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }

//// util methods

    @Override
    public String toString() {
        return tasks.getInternalList().size() + " tasks";
        // TODO: refine later
    }

    @Override
    public List<ReadOnlyTask> getTaskList() {
        return Collections.unmodifiableList(tasks.getInternalList());
    }

    @Override
    public UniqueTaskList getUniqueTaskList() {
        return this.tasks;
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskManager // instanceof handles nulls
                && this.tasks.equals(((TaskManager) other).tasks));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tasks);
    }
}
