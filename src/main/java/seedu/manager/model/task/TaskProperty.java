package seedu.manager.model.task;

import seedu.manager.commons.exceptions.IllegalValueException;

public abstract class TaskProperty {
    protected String value;
    private static String MESSAGE_CONSTRAINTS;
    private static String VALIDATION_REGEX;
    
    public TaskProperty(String property, String validationRegex, String messageConstraints) throws IllegalValueException {
        assert property != null;
        MESSAGE_CONSTRAINTS = messageConstraints;
        VALIDATION_REGEX = validationRegex;
        if (!isValid(property, VALIDATION_REGEX)) {
            throw new IllegalValueException(this.getMessageConstraints());
        }
        this.value = property;
    }
    
    public boolean isValid(String test, String validationRegex) {
        return test.matches(validationRegex);
    }
    
    public String getValue() {
        return this.toString();
    }
    
    public int hashCode() {
        return value.hashCode();
    };
    
    public String getMessageConstraints() {
        return MESSAGE_CONSTRAINTS;
    };
    
    public abstract String toString();
    
    public abstract boolean equals(Object other);
}
