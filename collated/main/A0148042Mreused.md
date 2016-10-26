# A0148042Mreused
###### /java/seedu/manager/model/task/ReadOnlyTask.java
``` java
     * Formats the task as text, showing all properties except done.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDesc().get());

        if (getVenue().isPresent()) {
            builder.append(" Venue: ").append(getVenue().get());
        }
        if (getPriority().isPresent()) {
            builder.append(" Priority: ").append(getPriority().get());
        }
        if (getStartTime().isPresent()) {
            builder.append(" Start Time: ").append(getStartTime().get());
        }
        if (getEndTime().isPresent()) {
            builder.append(" End Time: ").append(getEndTime().get());
        }
        if (getTag().isPresent()) {
            builder.append(" Tag: ").append(getTag().get());
        }
        return builder.toString();
    }
    
    /**
     * Formats the task as pretty text, meant to be displayed on the UI and in messages
     */
    default String getAsPrettyText() {
    	final StringBuilder builder = new StringBuilder();
        builder.append(getDesc().get().toPrettyString());

        if (getVenue().isPresent()) {
            builder.append(" Venue: ").append(getVenue().get().toPrettyString());
        }
        if (getPriority().isPresent()) {
            builder.append(" Priority: ").append(getPriority().get().toPrettyString());
        }
        if (getStartTime().isPresent()) {
            builder.append(" Start Time: ").append(getStartTime().get().toPrettyString());
        }
        if (getEndTime().isPresent()) {
            builder.append(" End Time: ").append(getEndTime().get().toPrettyString());
        }
        if (getTag().isPresent()) {
            builder.append(" Tag: ").append(getTag().get());
        }
        return builder.toString();
    }
    
    public int compareProperty(ReadOnlyTask other, TaskProperties property);
    
    public boolean matches(HashMap<TaskProperties, Optional<TaskProperty>> other);
}
```
