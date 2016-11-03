package seedu.manager.model.task;

import seedu.manager.commons.exceptions.IllegalValueException;

// @@author A0147924X
/**
 * An interface representing a property of a task
 *
 */
public abstract class TaskProperty implements Comparable<TaskProperty> {
    private String MESSAGE_CONSTRAINTS;
    private String VALIDATION_REGEX;
    
    /**
     * Create a TaskProperty given a string representing its value, a validation regex and a constraints message
     * @param property Value of the property. 
     * @param validationRegex Regex to validate against
     * @param messageConstraints Message with the constraints to show if property is not valid
     * @throws IllegalValueException
     */
    public TaskProperty(String property, String validationRegex, String messageConstraints) throws IllegalValueException {
        assert property != null;
        MESSAGE_CONSTRAINTS = messageConstraints;
        VALIDATION_REGEX = validationRegex;
        if (!isValid(property)) {
            throw new IllegalValueException(MESSAGE_CONSTRAINTS);
        }
    }
    
    /**
     * Tests whether a string is valid or not
     * @param test
     * @return Whether the string matches or not
     */
    public boolean isValid(String test) {
        return test.matches(VALIDATION_REGEX);
    }
    
    /**
     * Gets value of property as a string
     * @return Value as string
     */
    public String getValue() {
        return this.toString();
    }
    
    /**
     * Gets value of property as a pretty string, for displaying on the UI
     * @return Value as pretty string
     */
    public String getPrettyValue() {
        return this.toPrettyString();
    }
    
    public int hashCode() {
        return this.toString().hashCode();
    };
    
    public String getMessageConstraints() {
        return MESSAGE_CONSTRAINTS;
    };
    
    public abstract String toString();
    
    public String toPrettyString() {
    	return this.toString();
    }
    
    public abstract boolean equals(Object other);
    
    public int compareTo(TaskProperty other) {
    	return -1;
    }
    
    //@@author A0139621H
	public abstract boolean matches(TaskProperty property);
}