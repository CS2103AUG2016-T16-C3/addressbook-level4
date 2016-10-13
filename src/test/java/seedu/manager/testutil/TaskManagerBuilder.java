package seedu.manager.testutil;

import seedu.manager.commons.exceptions.IllegalValueException;
import seedu.manager.model.TaskManager;
import seedu.manager.model.task.Task;
import seedu.manager.model.task.UniqueTaskList;

/**
 * A utility class to help with building TaskManager objects.
 * Example usage: <br>
 *     {@code TaskManager ab = new TaskManagerBuilder().withTask("John", "Doe").withTag("Friend").build();}
 */
public class TaskManagerBuilder {

    private TaskManager taskManager;

    public TaskManagerBuilder(TaskManager taskManager){
        this.taskManager = taskManager;
    }

    public TaskManagerBuilder withTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        taskManager.addTask(task);
        return this;
    }

    public TaskManager build(){
        return taskManager;
    }
}
