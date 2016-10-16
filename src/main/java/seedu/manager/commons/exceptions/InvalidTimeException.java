package seedu.manager.commons.exceptions;

/**
 * Signals that the given start time is after the given end time.
 */
public class InvalidTimeException extends Exception {
    /**
     * @param message should contain relevant information on the failed constraint(s)
     */
    public InvalidTimeException (String message) {
        super(message);
    }
}
