package seedu.manager.model.task;

import java.util.Date;
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
    
    /**
     * Get properties of the task as a Hashmap mapping from TaskProperties to Optional TaskProperty
     * @return Hashmap representing the task properties
     */
    HashMap<TaskProperties, Optional<TaskProperty>> getProperties();
    
    /**
     * Get properties of the task as a Hashmap mapping from TaskProperties to Optional Strings
     * @return Hashmap representing the task properties
     */
    HashMap<TaskProperties, Optional<String>> getPropertiesAsStrings();
    
    default boolean doesPriorityEqual(String arg) {
    	
    	if (this.getPriority().isPresent() && this.getPriority().get().getValue().equals(arg)) {
    	    return true;
    	}
    	
        return false;
    }
    
    default boolean isPriorityHigh() {	
    	return doesPriorityEqual("high");
    }
    
    default boolean isPriorityLow() {
    	return doesPriorityEqual("low");
    }
    
    default boolean isPriorityMedium() {
    	return doesPriorityEqual("med");
    }
    
    default boolean isDone() {
        return ((Done) this.getDone().get()).isTrue();
    }
    
    default boolean isTaskOverdue() {	
    	if (this.isDone()) {
    		return false;
    	}
    	
    	Date now = new Date();
    	
    	if (this.getEndTime().isPresent()) {
    		return ((Time) this.getEndTime().get()).isBefore(now); 
    	} else if (this.getStartTime().isPresent()) {
    		return ((Time) this.getStartTime().get()).isBefore(now); 
    	} else {
    		return false;
    	}
    }
    
    // @@author A0147924X
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
    
    // @@author A0147924X
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
            builder.append(" Tag: ").append(getTag().get().toPrettyString());
        }
        return builder.toString();
    }
    
    /**
     * Compares a certain property of one task with a certain property of another task
     * @param firstTask First task to compare
     * @param secondTask Second task to compare
     * @param firstProperty Property of first task to compare
     * @param secondProperty Property of second task to compare
     * @return -1 if first task is smaller, 0 if equal, 1 if first task is larger
     */
    default public int compareProperty(ReadOnlyTask firstTask, ReadOnlyTask secondTask, 
    								   TaskProperties firstProperty, TaskProperties secondProperty) {
    	assert firstTask != null && secondTask != null;
    	HashMap<TaskProperties, Optional<TaskProperty>> firstProps = firstTask.getProperties();
    	HashMap<TaskProperties, Optional<TaskProperty>> secondProps = secondTask.getProperties();
    	
    	if (!firstProps.get(firstProperty).isPresent() && !secondProps.get(secondProperty).isPresent()) {
    		return 0;
    	} else if (!firstProps.get(firstProperty).isPresent()) {
    		return 1;
    	} else if (!secondProps.get(secondProperty).isPresent()) {
    		return -1;
    	} else {
    		return firstProps.get(firstProperty).get().compareTo(secondProps.get(secondProperty).get());
    	}
    }
    
    default public int comparePriority(ReadOnlyTask other) {
    	assert other != null;
    	
    	return this.compareProperty(this, other, TaskProperties.PRIORITY, TaskProperties.PRIORITY);
    }
    
    default public int compareTime(ReadOnlyTask other) {
    	assert other != null;
    	HashMap<TaskProperties, Optional<TaskProperty>> thisProps = this.getProperties();
    	HashMap<TaskProperties, Optional<TaskProperty>> otherProps = other.getProperties();
    	
    	if (thisProps.get(TaskProperties.ENDTIME).isPresent() && otherProps.get(TaskProperties.ENDTIME).isPresent()) {
			return this.compareProperty(this, other, TaskProperties.ENDTIME, TaskProperties.ENDTIME);
		} else if (!thisProps.get(TaskProperties.ENDTIME).isPresent() && !otherProps.get(TaskProperties.ENDTIME).isPresent()) {
			return this.compareProperty(this, other, TaskProperties.STARTTIME, TaskProperties.STARTTIME);
		} else if (!otherProps.get(TaskProperties.ENDTIME).isPresent()) {
			return this.compareProperty(this, other, TaskProperties.ENDTIME, TaskProperties.STARTTIME);
		} else {
			return this.compareProperty(this, other, TaskProperties.STARTTIME, TaskProperties.ENDTIME);
		}
    }
    
    default public int compareDone(ReadOnlyTask other) {
    	assert other != null;
    	
    	return compareProperty(this, other, TaskProperties.DONE, TaskProperties.DONE);
    }
    
    // @author A0139621H
    public boolean matches(HashMap<TaskProperties, Optional<TaskProperty>> other);
}
