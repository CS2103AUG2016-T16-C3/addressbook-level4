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
    HashMap<TaskProperties, Optional<TaskProperty>> getProperties();
    HashMap<TaskProperties, Optional<String>> getPropertiesAsStrings();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getDesc().equals(this.getDesc()) // state checks here onwards
                && other.getVenue().equals(this.getVenue())
                && other.getPriority().equals(this.getPriority())
                && other.getStartTime().equals(this.getStartTime())
                && other.getEndTime().equals(this.getEndTime()));
    }

    /**
     * Formats the task as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        // TODO: Change string rep
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
        return builder.toString();
    }

}
