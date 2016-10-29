package seedu.manager.model.task;

import seedu.manager.commons.exceptions.IllegalValueException;
import seedu.manager.commons.util.StringUtil;

/**
 * Represents a Task's desc in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValid(String)}
 */
public class Desc extends TaskProperty {
    
    public static final String MESSAGE_DESC_CONSTRAINTS = "Task descriptions can contain any characters";
    public static final String DESC_VALIDATION_REGEX = ".+";
    
    private String value;
    private static final int PRETTY_MAX_LENGTH = 50; 
    
    /**
     * @@author A0147924X
     * Validates given desc.
     *
     * @throws IllegalValueException if given desc string is invalid.
     */
    public Desc(String desc) throws IllegalValueException {
        super(desc, DESC_VALIDATION_REGEX, MESSAGE_DESC_CONSTRAINTS);
        value = desc;
    }

    //@@author
    @Override
    public String toString() {
        return value;
    }
    
    //@@author A0147924X
    @Override
    public String getPrettyValue() {
    	if (value.length() > PRETTY_MAX_LENGTH) {
    		return value.substring(0, PRETTY_MAX_LENGTH - 3) + "...";
    	} else {
			return value;
		}
    }
    
    /**
     * @@author A0139621H
     * Checks if any words from the task's venue matches that with the search function's input words
     */
    @Override
    public boolean matches(TaskProperty desc) {
        assert desc instanceof Desc;

        String[] arr = ((Desc) desc).value.split(" ");
        for (String string : arr) {
            if (StringUtil.containsIgnoreCase(this.value, string)) {
                return true;
            }
        }
        return false;
    }

    //@@author
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Desc // instanceof handles nulls
                && this.value.equals(((Desc) other).value)); // state check
    }
}
