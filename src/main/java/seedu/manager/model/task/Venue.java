package seedu.manager.model.task;

import seedu.manager.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's venue number in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValid(String)}
 */
public class Venue extends TaskProperty {

    public static final String MESSAGE_VENUE_CONSTRAINTS = "Task venue numbers should only contain numbers";
    public static final String VENUE_VALIDATION_REGEX = ".+";

    /**
     * Validates given venue.
     *
     * @throws IllegalValueException if given venue string is invalid.
     */
    public Venue(String venue) throws IllegalValueException {
        super(venue, VENUE_VALIDATION_REGEX, MESSAGE_VENUE_CONSTRAINTS);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Venue // instanceof handles nulls
                && this.value.equals(((Venue) other).value)); // state check
    }
}
