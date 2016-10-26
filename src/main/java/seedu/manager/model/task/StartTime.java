package seedu.manager.model.task;

import seedu.manager.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's start time.
 * Guarantees: immutable; is valid as declared in {@link #isValid(String)}
 */
public class StartTime extends Time {

	public StartTime(String startTime) throws IllegalValueException {
		super(startTime);
	}
	
    /**
     * Checks if the start time of a task is equal to or later than that of the search function's input
     */
	@Override
    public boolean matches(TaskProperty startTime) {
	    assert startTime instanceof StartTime;
	    
	    return (!((StartTime) startTime).value.after(this.value));
    }
}
