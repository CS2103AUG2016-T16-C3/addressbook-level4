package seedu.manager.model.task;

import java.util.Objects;

import seedu.manager.commons.util.CollectionUtil;

/**
 * Represents a Task in the task manager.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Desc desc;
    private Venue venue;
    //private Time time;
    private Priority priority;
    private StartTime startTime;
    private EndTime endTime;

    /**
     * Every field must be present and not null.
     */
    public Task(Desc desc, Venue venue, Priority priority, StartTime startTime, EndTime endTime) {
        assert !CollectionUtil.isAnyNull(desc, venue, priority, startTime, endTime);
        this.venue = venue;
        this.desc = desc;
        this.priority = priority;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getDesc(), source.getVenue(), source.getPriority(), source.getStartTime(), source.getEndTime());
    }

    @Override
    public Desc getDesc() {
        return desc;
    }

    @Override
    public Venue getVenue() {
        return venue;
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    @Override
    public StartTime getStartTime() {
        return startTime;
    }

    @Override
    public EndTime getEndTime() {
        return endTime;
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
        return Objects.hash(desc, venue, priority, startTime, endTime);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
