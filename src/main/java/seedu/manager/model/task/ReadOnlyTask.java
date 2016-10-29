package seedu.manager.model.task;

import java.util.HashMap;
import java.util.Optional;

import seedu.manager.model.task.Task.TaskProperties;

/**
 * A read-only immutable interface for a Task in the taskmanager.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    Optional<TaskProperty> getDesc();
    Optional<TaskProperty> getVenue();
    Optional<TaskProperty> getPriority();
    Optional<TaskProperty> getStartTime();
    Optional<TaskProperty> getEndTime();
    Optional<TaskProperty> getDone();
    Optional<TaskProperty> getTag();
    HashMap<TaskProperties, Optional<TaskProperty>> getProperties();
    HashMap<TaskProperties, Optional<String>> getPropertiesAsStrings();

    /**
     * @@author A0147924X
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getDesc().equals(this.getDesc()) // state checks here onwards
                && other.getVenue().equals(this.getVenue())
                && other.getPriority().equals(this.getPriority())
                && other.getStartTime().equals(this.getStartTime())
                && other.getEndTime().equals(this.getEndTime())
        		&& other.getDone().equals(this.getDone()))
                && other.getTag().equals(this.getTag());
    }

    /**
     * @@author A0148042M-reused
     * Formats the task as text, showing all properties except done.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDesc().get());

        if (getVenue().isPresent()) {
            builder.append(" Venue: ").append(getVenue().get());
        }
        if (getPriority().isPresent()) {
            builder.append(" Priority: ").append(getPriority().get());
        }
        if (getStartTime().isPresent()) {
            builder.append(" Start Time: ").append(getStartTime().get());
        }
        if (getEndTime().isPresent()) {
            builder.append(" End Time: ").append(getEndTime().get());
        }
        if (getTag().isPresent()) {
            builder.append(" Tag: ").append(getTag().get());
        }
        return builder.toString();
    }
    
    /**
     * Formats the task as pretty text, meant to be displayed on the UI and in messages
     */
    default String getAsPrettyText() {
    	final StringBuilder builder = new StringBuilder();
        builder.append(getDesc().get().toPrettyString());

        if (getVenue().isPresent()) {
            builder.append(" Venue: ").append(getVenue().get().toPrettyString());
        }
        if (getPriority().isPresent()) {
            builder.append(" Priority: ").append(getPriority().get().toPrettyString());
        }
        if (getStartTime().isPresent()) {
            builder.append(" Start Time: ").append(getStartTime().get().toPrettyString());
        }
        if (getEndTime().isPresent()) {
            builder.append(" End Time: ").append(getEndTime().get().toPrettyString());
        }
        if (getTag().isPresent()) {
            builder.append(" Tag: ").append(getTag().get());
        }
        return builder.toString();
    }
    
    //@@author A0147924X
    public int compareProperty(ReadOnlyTask other, TaskProperties property);
    
    //@author A0139621H
    public boolean matches(HashMap<TaskProperties, Optional<TaskProperty>> other);
}
