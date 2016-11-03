package seedu.manager.model.task;

import seedu.manager.commons.exceptions.IllegalValueException;

//@@author A0139621H
/**
 * Represents a Task's start time.
 * Guarantees: immutable; is valid as declared in {@link #isValid(String)}
 */
public class StartTime extends Time {
    
	public StartTime(String startTime) throws IllegalValueException {
		super(startTime);
	}
}
