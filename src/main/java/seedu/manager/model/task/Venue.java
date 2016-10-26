package seedu.manager.model.task;

import seedu.manager.commons.exceptions.IllegalValueException;
import seedu.manager.commons.util.StringUtil;

/**
 * Represents a Task's venue number in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValid(String)}
 */
public class Venue extends TaskProperty {

    public static final String MESSAGE_VENUE_CONSTRAINTS = "Task venue can contain any text";
    public static final String VENUE_VALIDATION_REGEX = ".+";
    private String value;

    /**
     * Validates given venue.
     *
     * @throws IllegalValueException if given venue string is invalid.
     */
    public Venue(String venue) throws IllegalValueException {
        super(venue, VENUE_VALIDATION_REGEX, MESSAGE_VENUE_CONSTRAINTS);
        value = venue;
    }

    @Override
    public String toString() {
        return value;
    }
    
    /**
     * Checks if any words from the task's venue matches that with the search function's input words
     */
    @Override
    public boolean matches(TaskProperty venue) {
        assert venue instanceof Venue;

        String[] arr = ((Venue) venue).value.split(" ");
        for (String string : arr) {
            if (StringUtil.containsIgnoreCase(this.value, string)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Venue // instanceof handles nulls
                && this.value.equals(((Venue) other).value)); // state check
    }
}
