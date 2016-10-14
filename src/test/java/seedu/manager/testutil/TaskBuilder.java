package seedu.manager.testutil;

import seedu.manager.commons.exceptions.IllegalValueException;
import seedu.manager.model.task.*;

/**
 *
 */
public class TaskBuilder {

    private TestTask task;

    public TaskBuilder() {
        this.task = new TestTask();
    }

    public TaskBuilder withDesc(String desc) throws IllegalValueException {
        this.task.setDesc(new Desc(desc));
        return this;
    }

    public TaskBuilder withPriority(String priority) throws IllegalValueException {
        this.task.setPriority(new Priority(priority));
        return this;
    }

    public TaskBuilder withVenue(String venue) throws IllegalValueException {
        this.task.setVenue(new Venue(venue));
        return this;
    }

    public TaskBuilder withStartTime(String startTime) throws IllegalValueException {
        this.task.setStartTime(new StartTime(startTime));
        return this;
    }

    public TaskBuilder withEndTime(String endTime) throws IllegalValueException {
        this.task.setEndTime(new EndTime(endTime));
        return this;
    }

    public TaskBuilder withDone(String done) throws IllegalValueException {
		this.task.setDone(new Done(done));
		return this;
	}

    public TestTask build() {
        return this.task;
    }

}
