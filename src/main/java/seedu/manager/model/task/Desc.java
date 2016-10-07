package seedu.manager.model.task;

import seedu.manager.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's desc in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidDesc(String)}
 */
public class Desc extends TaskProperty {
    public static final String MESSAGE_DESC_CONSTRAINTS = "Task descriptions can contain any characters";
    public static final String DESC_VALIDATION_REGEX = "[\\p{Alnum} ]+";
    
    /**
     * Validates given desc.
     *
     * @throws IllegalValueException if given desc string is invalid.
     */
    public Desc(String desc) throws IllegalValueException {
        super(desc, DESC_VALIDATION_REGEX, MESSAGE_DESC_CONSTRAINTS);
    }


    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Desc // instanceof handles nulls
                && this.value.equals(((Desc) other).value)); // state check
    }
}
