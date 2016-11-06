package seedu.manager.model.task;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Optional;

import seedu.manager.commons.exceptions.IllegalValueException;
import seedu.manager.commons.util.CollectionUtil;


/**
 * Represents a Task in the task manager.
 * Guarantees: description is present and not null, done is present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {
    public static enum TaskProperties {
        DESC, PRIORITY, VENUE, STARTTIME, ENDTIME, DONE, TAG
    }
    
    public static final String START_AFTER_END = "Start time should be before end time.";
    
    private HashMap<TaskProperties, Optional<TaskProperty>> properties = new HashMap<>();

    // @@author A0147924X
    /**
     * Build task from properties represented as Strings
     * @param properties Hashmap with properties represented as strings
     * @throws IllegalValueException
     */
    public Task(HashMap<TaskProperties, Optional<String>> properties) throws IllegalValueException {
        assert properties.get(TaskProperties.DESC).isPresent();
        assert !properties.get(TaskProperties.DESC).get().equals("");
        
        for (Entry<TaskProperties, Optional<String>> prop : properties.entrySet()) {
        	Optional<TaskProperty> taskProperty = buildProperty(prop.getKey(), prop.getValue());
            this.properties.put(prop.getKey(), taskProperty);
        }
    }

	/**
	 * Task Constructor from individual strings. Empty strings indicate empty properties. 
	 * Every field must be present and not null. Desc cannot be empty
	 * @param desc Task's description
	 * @param venue Task's venue
	 * @param priority Task's priority
	 * @param startTime Task's start time
	 * @param endTime Task's end time
	 * @param done Whether task is done
	 * @param tag Task's tag
	 * @throws IllegalValueException
	 */
    public Task(String desc, String venue, String priority, String startTime, String endTime, String done, String tag) throws IllegalValueException {
       assert !CollectionUtil.isAnyNull(desc, venue, priority, startTime, endTime, done, tag);
       assert !desc.equals("");

       properties.put(TaskProperties.DESC, Optional.of(new Desc(desc)));
       properties.put(TaskProperties.VENUE, venue.equals("") ? Optional.empty() : Optional.of(new Venue(venue)));
       properties.put(TaskProperties.PRIORITY, priority.equals("") ? Optional.empty() : Optional.of(new Priority(priority)));
       properties.put(TaskProperties.STARTTIME, startTime.equals("") ? Optional.empty() : Optional.of(new StartTime(startTime)));
       properties.put(TaskProperties.ENDTIME, endTime.equals("") ? Optional.empty() : Optional.of(new EndTime(endTime)));
       properties.put(TaskProperties.DONE, done.equals("") ? Optional.of(new Done("No")) : Optional.of(new Done(done)));
       properties.put(TaskProperties.TAG, tag.equals("") ? Optional.empty() : Optional.of(new Tag(tag)));
    }

    /**
     * Copy constructor
     * @param source Task which will be copied
     */
    public Task(ReadOnlyTask source) {
        HashMap<TaskProperties, Optional<TaskProperty>> properties = source.getProperties();
        assert properties.containsKey(TaskProperties.DESC) && properties.get(TaskProperties.DESC).isPresent();
        assert properties.containsKey(TaskProperties.DONE) && properties.get(TaskProperties.DONE).isPresent();
        
        for (Entry<TaskProperties, Optional<TaskProperty>> prop : properties.entrySet()) {
            this.properties.put(prop.getKey(), prop.getValue());
        }
    }

    /**
     * Get properties of task as TaskProperty objects
     * @return clone of Task's properties
     */
    @Override
    public HashMap<TaskProperties, Optional<TaskProperty>> getProperties() {
        HashMap<TaskProperties, Optional<TaskProperty>> clone = new HashMap<>();
        for (Entry<TaskProperties, Optional<TaskProperty>> prop : properties.entrySet()) {
            clone.put(prop.getKey(), prop.getValue());
        }
        return clone;
    }
    
    /**
     * Get properties of task as Strings
     */
    @Override
    public HashMap<TaskProperties, Optional<String>> getPropertiesAsStrings() {
        HashMap<TaskProperties, Optional<String>> clone = new HashMap<>();
        for (Entry<TaskProperties, Optional<TaskProperty>> prop : properties.entrySet()) {
            clone.put(prop.getKey(), prop.getValue().map(TaskProperty::getValue));
        }
        return clone;
    }
    
    /**
     * Builds a TaskProperty object using a value from the TaskProperties enum and a value
     * @param property that should be built
     * @param Optional which may have the value of the property, or be empty
     * @return An optional containing a property if the input had one, else empty
     */
    private Optional<TaskProperty> buildProperty(TaskProperties property, Optional<String> value) throws IllegalValueException {
    	if (!value.isPresent()) {
    		if (property == TaskProperties.DONE) {
				return Optional.of(new Done("No"));
			}
    		else {
    			return Optional.empty();
			}
    	}
    	String stringValue = value.get();
    	
		switch (property) {
		case DESC:
			return Optional.of(new Desc(stringValue));
		case VENUE:
			return Optional.of(new Venue(stringValue));
		case STARTTIME:
			return Optional.of(new StartTime(stringValue));
		case ENDTIME:
			return Optional.of(new EndTime(stringValue));
		case PRIORITY:
			return Optional.of(new Priority(stringValue));
		case DONE:
			return Optional.of(new Done(stringValue));
		case TAG:
		    return Optional.of(new Tag(stringValue));
		default:
			throw new IllegalValueException("Property not found");
		}
	}

    @Override
    public Optional<TaskProperty> getDesc() {
        return properties.get(TaskProperties.DESC);
    }

    @Override
    public Optional<TaskProperty> getVenue() {
        return properties.get(TaskProperties.VENUE);
    }

    // @@author A0139621H
    @Override
    public Optional<TaskProperty> getPriority() {
        return properties.get(TaskProperties.PRIORITY);
    }

    @Override
    public Optional<TaskProperty> getStartTime() {
        return properties.get(TaskProperties.STARTTIME);
    }

    @Override
    public Optional<TaskProperty> getEndTime() {
        return properties.get(TaskProperties.ENDTIME);
    }

    @Override
    public Optional<TaskProperty> getDone() {
    	return properties.get(TaskProperties.DONE);
    }
    
    // @@author A0148042M
    @Override
    public Optional<TaskProperty> getTag() {
        return properties.get(TaskProperties.TAG);
    }
    
    // @@author
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instance of handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }
    
    // @@author A0147924X
    @Override
    /**
     * Checks whether given properties "match" against this task. Properties match if there's a fuzzy
     * relationship between them. For an understanding of how different properties "match", see the
     * Overridden method matches in the respective task property classes
     * @param otherProps The properties to match against
     * @return Whether the task matches these properties or not
     */
    public boolean matches(HashMap<TaskProperties, Optional<TaskProperty>> otherProps) {
    	HashMap<TaskProperties, Optional<TaskProperty>> thisProps = this.getProperties();
    	
        for (TaskProperties property : TaskProperties.values()) {
            if (otherProps.get(property).isPresent()) {
                if (!thisProps.get(property).isPresent()) {
                	if (!matchStartOrEndTime(otherProps, property)) {
						return false;
					}
                } else if (!thisProps.get(property).get().matches(otherProps.get(property).get())){
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * Tries to match start time against end time in the case that this task doesn't have a start time
     * and vice versa 
     * @param other Other properties to compare against
     * @param property Property to compare on
     * @return true if property is start or end time and matches with this task, false otherwise
     */
    private boolean matchStartOrEndTime(HashMap<TaskProperties, Optional<TaskProperty>> other, TaskProperties property) {
    	if (property.equals(TaskProperties.STARTTIME)) {
			if (!(this.properties.get(TaskProperties.ENDTIME).isPresent() &&
					this.properties.get(TaskProperties.ENDTIME).get().matches(other.get(property).get()))) {
				return false;
			} else {
				return true;
			}
		} else if (property.equals(TaskProperties.ENDTIME)) {
			if (!(this.properties.get(TaskProperties.STARTTIME).isPresent() &&
					this.properties.get(TaskProperties.STARTTIME).get().matches(other.get(property).get()))) {
				return false;
			} else {
				return true;
			}
		} else {
			return false;
		}
    }
    
    // @@author
    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return properties.hashCode();
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
