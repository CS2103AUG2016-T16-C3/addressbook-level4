package seedu.manager.model.task;


import seedu.manager.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's priority in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValid(String)}
 */
public class Priority extends TaskProperty {
    public static final String MESSAGE_PRIORITY_CONSTRAINTS = "Task priorities should be either low, med or high";
    public static final String PRIORITY_VALIDATION_REGEX = "low|med|high";
    
    //@@author A0147924X
    private static enum VALUES {
    	LOW("low"), MED("med"), HIGH("high");
    	
    	private String value;
        
        private VALUES(String value) {
            this.value = value;
        }
    }
    private static VALUES defaultValue = VALUES.MED;
    private VALUES value = null;
    
    /**
     * @@author A0147924X
     * Validates given priority.
     *
     * @throws IllegalValueException if given priority string is invalid.
     */
    public Priority(String priority) throws IllegalValueException {
        super(priority, PRIORITY_VALIDATION_REGEX, MESSAGE_PRIORITY_CONSTRAINTS);
        for (VALUES val : VALUES.values()) {
			if (priority.equals(val.value)) {
				value = val;
			}
		}
        if (value == null) {
			value = defaultValue;
		}
    }

    @Override
    public String toString() {
        return value.value;
    }
    
    /**
     * @@author A0139621H
     * Checks if the task property matches with that of the search function's input
     */
    @Override
    public boolean matches(TaskProperty priority) {
        assert priority instanceof Priority;
        
        return ((Priority) priority).equals(this);
    }
    
    //@@author
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Priority // instanceof handles nulls
                && this.value.equals(((Priority) other).value)); // state check
    }
    
    // @@author A0147924X
    @Override
    public int compareTo(TaskProperty other) {
    	assert other instanceof Priority;
    	
    	if (this.value.equals(((Priority) other).value)) {
			return 0;
		} else if (this.value.equals(VALUES.LOW) || ((Priority) other).value.equals(VALUES.HIGH)) {
			return 1;
		} else {
			return -1;
		}
    }
}