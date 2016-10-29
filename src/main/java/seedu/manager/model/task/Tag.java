package seedu.manager.model.task;


import seedu.manager.commons.exceptions.IllegalValueException;

/**
 * @@author A0148042M
 * Represents a Tag in the task manager.
 * Guarantees: immutable; name is valid as declared in {@link #isValid(String, String)}
 */
public class Tag extends TaskProperty {

    public static final String MESSAGE_TAG_CONSTRAINTS = "Tags names can have anything";
    public static final String TAG_VALIDATION_REGEX = ".+";

    public String tagName;

//    public Tag() {
//    }

    /**
     * Validates given tag name.
     *
     * @throws IllegalValueException if the given tag name string is invalid.
     */
    public Tag(String name) throws IllegalValueException {
        super(name, TAG_VALIDATION_REGEX, MESSAGE_TAG_CONSTRAINTS);
        this.tagName = name;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Tag // instanceof handles nulls
                && this.tagName.equals(((Tag) other).tagName)); // state check
    }

    @Override
    public int hashCode() {
        return tagName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return tagName;
    }
    
    public String toUiString() {
        return " [" + tagName + "]";
    }

    //@@author A0139621H
    @Override
    public boolean matches(TaskProperty tag) {
        assert tag instanceof Tag;
        
        return tag.getValue().equals(this.getValue());
    }

}