package seedu.manager.model.task;


import seedu.manager.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's venue number in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 */
public class EndTime extends TaskProperty {

    public static final String MESSAGE_TIME_CONSTRAINTS =
            "Task times should be in the form <time> or <date>";
    public static final String TIME_VALIDATION_REGEX = ".+";

    /**
     * Validates given time.
     *
     * @throws IllegalValueException if given time address string is invalid.
     */
    public EndTime(String endTime) throws IllegalValueException {
        super(endTime, TIME_VALIDATION_REGEX, MESSAGE_TIME_CONSTRAINTS);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Time // instanceof handles nulls
                && this.value.equals(((Time) other).value)); // state check
    }
}
