package seedu.manager.model.task;


import seedu.manager.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's start time.
 * Guarantees: immutable; is valid as declared in {@link #isValid(String)}
 */
public class StartTime extends TaskProperty {

    public static final String MESSAGE_TIME_CONSTRAINTS =
            "Task times should be in the form <time> or <date>";
    public static final String TIME_VALIDATION_REGEX = ".+";
    
    private String value;

    /**
     * Validates given time.
     *
     * @throws IllegalValueException if given time address string is invalid.
     */
    public StartTime(String startTime) throws IllegalValueException {
        super(startTime, TIME_VALIDATION_REGEX, MESSAGE_TIME_CONSTRAINTS);
        value = startTime;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StartTime // instanceof handles nulls
                && this.value.equals(((StartTime) other).value)); // state check
    }
}
