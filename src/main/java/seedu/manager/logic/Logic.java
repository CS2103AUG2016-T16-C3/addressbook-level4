package seedu.manager.logic;

import javafx.collections.ObservableList;
import seedu.manager.logic.commands.CommandResult;
import seedu.manager.model.task.ReadOnlyTask;

import seedu.manager.commons.exceptions.InvalidTimeException;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     */
    CommandResult execute(String commandText) throws InvalidTimeException;

    /** Returns the filtered list of tasks */
    ObservableList<ReadOnlyTask> getFilteredTaskList();

}
