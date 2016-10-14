package seedu.manager.model.task;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Optional;

import seedu.manager.commons.exceptions.IllegalValueException;
import seedu.manager.commons.util.CollectionUtil;

/**
 * Represents a Task in the task manager.
 * Guarantees: description is present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private HashMap<TaskProperties, Optional<TaskProperty>> properties = new HashMap<>();

    public static enum TaskProperties {
        DESC, PRIORITY, VENUE, STARTTIME, ENDTIME, DONE
    }


    public Task(HashMap<TaskProperties, Optional<TaskProperty>> properties) {
        assert properties.get(TaskProperties.DESC).isPresent();
        assert !properties.get(TaskProperties.DESC).get().getValue().equals("");

        for (Entry<TaskProperties, Optional<TaskProperty>> prop : properties.entrySet()) {
            this.properties.put(prop.getKey(), prop.getValue());
        }
    }

    /**
     * Every field must be present and not null.
     */
    public Task(String desc, String venue, String priority, String startTime, String endTime, String done) throws IllegalValueException {
       assert !CollectionUtil.isAnyNull(desc, venue, priority, startTime, endTime, done);
       assert !desc.equals("");

       properties.put(TaskProperties.DESC, Optional.of(new Desc(desc)));
       properties.put(TaskProperties.VENUE, venue == "" ? Optional.empty() : Optional.of(new Venue(venue)));
       properties.put(TaskProperties.PRIORITY, priority == "" ? Optional.empty() : Optional.of(new Priority(priority)));
       properties.put(TaskProperties.STARTTIME, startTime == "" ? Optional.empty() : Optional.of(new StartTime(startTime)));
       properties.put(TaskProperties.ENDTIME, endTime == "" ? Optional.empty() : Optional.of(new EndTime(endTime)));
       properties.put(TaskProperties.DONE, done == "" ? Optional.empty() : Optional.of(new Done(done)));
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getProperties());
    }

    @Override
    public HashMap<TaskProperties, Optional<TaskProperty>> getProperties() {
        HashMap<TaskProperties, Optional<TaskProperty>> clone = new HashMap<>();
        for (Entry<TaskProperties, Optional<TaskProperty>> prop : properties.entrySet()) {
            clone.put(prop.getKey(), prop.getValue());
        }
        return clone;
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
