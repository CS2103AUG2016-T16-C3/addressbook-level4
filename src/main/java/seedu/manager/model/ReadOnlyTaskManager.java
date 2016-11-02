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
    
    UniqueTagList getUniqueTagList();
    
    /**
     * Returns an unmodifiable view of tasks list
     */
    List<ReadOnlyTask> getTaskList();
    
    List<Tag> getTagList();
}
