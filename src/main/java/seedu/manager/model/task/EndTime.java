package seedu.manager.model.task;


import seedu.manager.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's end time.
 * Guarantees: immutable; is valid as declared in {@link #isValid(String)}
 */
public class EndTime extends Time {

	public EndTime(String endTime) throws IllegalValueException {
		super(endTime);
	}
}
