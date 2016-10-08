package seedu.manager.model.task;

import seedu.manager.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's priority in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValid(String)}
 */
public class Priority extends TaskProperty {
    public static final String MESSAGE_PRIORITY_CONSTRAINTS = "Task priorities should be either low, med or high";
    public static final String PRIORITY_VALIDATION_REGEX = ".+";
    public static final String COMMAND_WORD = "priority";
    
    /**
     * Validates given priority.
     *
     * @throws IllegalValueException if given priority string is invalid.
     */
    public Priority(String priority) throws IllegalValueException {
        super(priority, PRIORITY_VALIDATION_REGEX, MESSAGE_PRIORITY_CONSTRAINTS);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Priority // instanceof handles nulls
                && this.value.equals(((Priority) other).value)); // state check
    }
}