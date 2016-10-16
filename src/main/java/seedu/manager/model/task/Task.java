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
        DESC, PRIORITY, VENUE, STARTTIME, ENDTIME, DONE
    }
    
    private HashMap<TaskProperties, Optional<TaskProperty>> properties = new HashMap<>();

    /**
     * Build task from properties represented as Strings
     * @param properties
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
	 * Task Constructor from individual strings
	 * @param desc
	 * @param venue
	 * @param priority
	 * @param startTime
	 * @param endTime
	 * @param done
	 * @throws IllegalValueException
	 * Every field must be present and not null. Desc cannot be empty
	 */
    public Task(String desc, String venue, String priority, String startTime, String endTime, String done) throws IllegalValueException {
       assert !CollectionUtil.isAnyNull(desc, venue, priority, startTime, endTime, done);
       assert !desc.equals("");

       properties.put(TaskProperties.DESC, Optional.of(new Desc(desc)));
       properties.put(TaskProperties.VENUE, venue == "" ? Optional.empty() : Optional.of(new Venue(venue)));
       properties.put(TaskProperties.PRIORITY, priority == "" ? Optional.empty() : Optional.of(new Priority(priority)));
       properties.put(TaskProperties.STARTTIME, startTime == "" ? Optional.empty() : Optional.of(new StartTime(startTime)));
       properties.put(TaskProperties.ENDTIME, endTime == "" ? Optional.empty() : Optional.of(new EndTime(endTime)));
       properties.put(TaskProperties.DONE, done == "" ? Optional.of(new Done("No")) : Optional.of(new Done(done)));
    }

    /**
     * Copy constructor.
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
            clone.put(prop.getKey(),
            		prop.getValue().isPresent() ? 
            				Optional.of(prop.getValue().get().getValue()) : 
            				Optional.empty());
        }
        return clone;
    }
    

    /**
     * Builds a TaskProperty object using a value from the TaskProperties enum and a value
     * @param property that should be built
     * @param value of the property
     * @return
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

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }

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
