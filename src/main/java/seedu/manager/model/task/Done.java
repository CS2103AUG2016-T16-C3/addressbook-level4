package seedu.manager.model.task;


import seedu.manager.commons.exceptions.IllegalValueException;

/**
 * Represents whether a task is done.
 * Guarantees: immutable; is valid as declared in {@link #isValid(String)}
 */
public class Done extends TaskProperty {
    
    public static final String MESSAGE_DONE_CONSTRAINTS =
            "Task done indicator should be in the form \"Yes\" or \"No\"";
    public static final String DONE_VALIDATION_REGEX = "Yes|No";
    public static final String COMMAND_WORD = "done";
    private boolean value;

    // @@author A0147924X
    /**
     * Validates given done indicator.
     * @throws IllegalValueException if given done indicator address string is invalid.
     */
    public Done(String done) throws IllegalValueException {
        super(done, DONE_VALIDATION_REGEX, MESSAGE_DONE_CONSTRAINTS);
        value = done.equals("Yes") ? true : false;
    }

    @Override
    public String toString() {
        return value ? "Yes" : "No";
    }
    
    @Override
    public String getPrettyValue() {
        return value ? "\u2713" : "";
    }
    
    // @@author A0139621H
    /**
     * Checks if the task property matches with that of the search function's input
     */
    @Override
    public boolean matches(TaskProperty done) {
        assert done instanceof Done;
        
        return ((Done) done).equals(this);
    }

    // @@author
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Done // instanceof handles nulls
                && this.value == ((Done) other).value); // state check
    }
    
    public boolean isTrue() {
    	return value;
    }
    
    // @@author A0147924X
    @Override
    public int compareTo(TaskProperty other) {
    	assert other instanceof Done;
    	
    	if (this.isTrue() && !((Done) other).isTrue()) {
			return 1;
		} else if (!this.isTrue() && ((Done) other).isTrue()) {
			return -1;
		} else {
			return 0;
		}
    }
}
