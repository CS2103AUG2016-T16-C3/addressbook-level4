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
    @XmlElement(required = true)
    private String done;
    @XmlElement(required = true)
    private String tag;

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
        desc = source.getDesc().get().getValue();
        venue = source.getVenue().isPresent() ? source.getVenue().get().getValue() : "";
        priority = source.getPriority().isPresent() ? source.getPriority().get().getValue() : "";
        startTime = source.getStartTime().isPresent() ? source.getStartTime().get().getValue() : "";
        endTime = source.getEndTime().isPresent() ? source.getEndTime().get().getValue() : "";
        done = source.getDone().isPresent() ? source.getDone().get().getValue() : "";
        tag = source.getTag().isPresent() ? source.getTag().get().getValue() : "";
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */
    public Task toModelType() throws IllegalValueException {
        return new Task(desc, venue, priority, startTime, endTime, done, tag);
    }
}
