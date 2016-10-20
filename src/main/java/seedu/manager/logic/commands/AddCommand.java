package seedu.manager.logic.commands;

import java.util.HashMap;
import java.util.Optional;

import seedu.manager.commons.exceptions.IllegalValueException;
import seedu.manager.model.task.*;
import seedu.manager.model.task.Task.TaskProperties;

/**
 * Adds a task to the task manager.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the task manager. "
            + "Parameters: DESC [<extensions>] \n"
            + "Example: " + COMMAND_WORD
            + " Dinner with Lancelot venue Avalon after 8:30pm before 9:00pm priority med";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This task already exists in the task manager";

    private final Task toAdd;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(HashMap<TaskProperties, Optional<String>> properties)
            throws IllegalValueException {
        if (!properties.get(TaskProperties.DESC).isPresent()) {
            throw new IllegalValueException(MESSAGE_USAGE);
        }
        this.toAdd = new Task(properties);
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.addTask(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_PERSON);
        }

    }

}
