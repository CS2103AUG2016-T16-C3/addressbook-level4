package seedu.manager.testutil;

import seedu.manager.model.task.*;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Desc desc;
    private Priority priority;
    //private Time time;
    private Venue venue;
    private StartTime startTime;
    private EndTime endTime;

    public TestTask() {
    }

    public void setDesc(Desc desc) {
        this.desc = desc;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }
    
    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public void setStartTime(StartTime startTime) {
    	this.startTime = startTime;
    }

    public void setEndTime(EndTime endTime) {
    	this.endTime = endTime;
    }

    @Override
    public Desc getDesc() {
        return desc;
    }

    @Override
    public Venue getVenue() {
        return venue;
    }
    /*
    @Override
    public Time getTime() {
        return time;
    }
    */
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
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getDesc().getValue() + " ");
        sb.append("v/" + this.getVenue().getValue() + " ");
        sb.append("st/" + this.getStartTime().getValue() + " ");
        sb.append("et/" + this.getEndTime().getValue() + " ");
        sb.append("p/" + this.getPriority().getValue() + " ");
        return sb.toString();
    }
}
