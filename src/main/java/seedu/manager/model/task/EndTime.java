package seedu.manager.model.task;


import seedu.manager.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's end time.
 * Guarantees: immutable; is valid as declared in {@link #isValid(String)}
 */
public class EndTime extends TaskProperty {

    public static final String MESSAGE_TIME_CONSTRAINTS =
            "Task times should be in the form <time> or <date>";
    public static final String TIME_VALIDATION_REGEX = ".+";
    
    private String value;

    /**
     * Validates given time.
     *
     * @throws IllegalValueException if given time address string is invalid.
     */
    public EndTime(String endTime) throws IllegalValueException {
        super(endTime, TIME_VALIDATION_REGEX, MESSAGE_TIME_CONSTRAINTS);
        value = endTime;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EndTime // instanceof handles nulls
                && this.value.equals(((EndTime) other).value)); // state check
    }
}
