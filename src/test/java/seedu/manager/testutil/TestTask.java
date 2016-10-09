package seedu.manager.testutil;

import java.util.HashMap;
import java.util.Optional;

import seedu.manager.model.task.*;
import seedu.manager.model.task.Task.TaskProperties;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {
    private HashMap<TaskProperties, Optional<TaskProperty>> properties = new HashMap<>();
    
    public TestTask() {
    }
    
    public TestTask(HashMap<TaskProperties, Optional<TaskProperty>> properties) {
        this.properties = properties;
    }

    public void setDesc(Desc desc) {
        this.properties.put(TaskProperties.DESC, Optional.of(desc));
    }

    public void setPriority(Priority priority) {
        this.properties.put(TaskProperties.PRIORITY, Optional.of(priority));
    }
    
    public void setVenue(Venue venue) {
        this.properties.put(TaskProperties.VENUE, Optional.of(venue));
    }

    public void setStartTime(StartTime startTime) {
        this.properties.put(TaskProperties.STARTTIME, Optional.of(startTime));
    }

    public void setEndTime(EndTime endTime) {
        this.properties.put(TaskProperties.ENDTIME, Optional.of(endTime));
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
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getDesc().get().getValue() + " ");
        if (this.getVenue().isPresent()) {
            sb.append("venue " + this.getVenue().get().getValue() + " ");  
        }
        if (this.getPriority().isPresent()) {
            sb.append("priority " + this.getPriority().get().getValue() + " ");
        }
        if (this.getStartTime().isPresent()) {
            sb.append("after " + this.getStartTime().get().getValue() + " ");
        }
        sb.append("before " + this.getEndTime().get().getValue() + " ");
        return sb.toString();
    }

    @SuppressWarnings("unchecked")
    @Override
    public HashMap<TaskProperties, Optional<TaskProperty>> getProperties() {
        return (HashMap<TaskProperties, Optional<TaskProperty>>) properties.clone();
    }
}
