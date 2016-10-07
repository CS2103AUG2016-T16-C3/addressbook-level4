package seedu.manager.model.task;

import java.util.Objects;

import seedu.manager.commons.util.CollectionUtil;
import seedu.manager.model.tag.UniqueTagList;

/**
 * Represents a Task in the task manager.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Desc desc;
    private Venue venue;
    private Time time;
    private Priority priority;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Task(Desc desc, Venue venue, Time time, Priority priority, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(desc, venue, time, priority, tags);
        this.venue = venue;
        this.desc = desc;
        this.time = time;
        this.priority = priority;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getDesc(), source.getVenue(), source.getTime(), source.getPriority(), source.getTags());
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
    public Time getTime() {
        return time;
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }

    /**
     * Replaces this task's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
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
        return Objects.hash(desc, venue, time, priority, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
