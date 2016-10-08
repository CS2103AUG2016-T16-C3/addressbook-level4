package seedu.manager.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.manager.commons.exceptions.IllegalValueException;
import seedu.manager.model.task.*;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String desc;
    @XmlElement(required = true)
    private String venue;
    @XmlElement(required = true)
    private String priority;
    @XmlElement(required = true)
    private String startTime;
    @XmlElement(required = true)
    private String endTime;

    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedTask() {}


    /**
     * Converts a given Task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedTask
     */
    public XmlAdaptedTask(ReadOnlyTask source) {
        desc = source.getDesc().getValue();
        venue = source.getVenue().getValue();
        priority = source.getPriority().getValue();
        startTime = source.getStartTime().getValue();
        endTime = source.getEndTime().getValue();
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */
    public Task toModelType() throws IllegalValueException {
        final Desc desc = new Desc(this.desc);
        final Venue venue = new Venue(this.venue);
        final Priority priority = new Priority(this.priority);
        final StartTime startTime = new StartTime(this.startTime);
        final EndTime endTime = new EndTime(this.endTime);
        return new Task(desc, venue, priority, startTime, endTime);
    }
}
