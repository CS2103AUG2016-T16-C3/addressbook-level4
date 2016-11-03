package seedu.manager.model;


import java.util.List;

import seedu.manager.model.task.ReadOnlyTask;
import seedu.manager.model.task.Tag;
import seedu.manager.model.task.UniqueTaskList;
import seedu.manager.model.tag.UniqueTagList;

/**
 * Unmodifiable view of an task manager
 */
public interface ReadOnlyTaskManager {

    UniqueTaskList getUniqueTaskList();
    
    // @@author A0148042M
    UniqueTagList getUniqueTagList();
    
    // @@author 
    /**
     * Returns an unmodifiable view of tasks list
     */
    List<ReadOnlyTask> getTaskList();
    
    // @@author A0148042M
    List<Tag> getTagList();
}
