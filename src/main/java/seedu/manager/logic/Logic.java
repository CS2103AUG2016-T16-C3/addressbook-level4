package seedu.manager.logic;

import javafx.collections.ObservableList;
import seedu.manager.logic.commands.CommandResult;
import seedu.manager.model.task.ReadOnlyTask;
import seedu.manager.model.task.Tag;


/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     */
    CommandResult execute(String commandText);

    /** Returns the filtered list of tasks */
    ObservableList<ReadOnlyTask> getSortedFilteredTaskList();
    
//    ObservableList<ReadOnlyTask> getSortedFilteredTaskList(Tag selectedTag);
    
    void updateTaskListWhenTagSelected(Tag selectedTag);
    
    // @@author A0148042M
    /** Returns the filtered list of tags */
    ObservableList<Tag> getSortedFilteredTagList();
    
}
