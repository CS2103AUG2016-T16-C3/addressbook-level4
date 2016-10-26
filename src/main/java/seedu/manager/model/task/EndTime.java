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
	
    /**
     * Checks if the end time of a task is equal to or earlier than that of the search function's input
     */
    @Override
    public boolean matches(TaskProperty endTime) {
        assert endTime instanceof EndTime;
        
        return (!((EndTime) endTime).value.before(this.value));
    }
}
