package seedu.manager.logic.commands;

import seedu.manager.commons.exceptions.IllegalValueException;
import seedu.manager.model.task.*;

/**
 * Adds a task to the task manager.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the task manager. "
            + "Parameters: DESC p/VENUE e/TIME a/PRIORITY  [t/TAG]...\n"
            + "Example: " + COMMAND_WORD
            + " John Doe p/98765432 e/johnd@gmail.com a/med t/friends t/owesMoney";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This task already exists in the task manager";

    private final Task toAdd;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String desc, String venue, String priority, String startTime, String endTime)
            throws IllegalValueException {
        this.toAdd = new Task(
                new Desc(desc),
                new Venue(venue),
                new Priority(priority),
                new StartTime(startTime),
                new EndTime(endTime)
        );
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
