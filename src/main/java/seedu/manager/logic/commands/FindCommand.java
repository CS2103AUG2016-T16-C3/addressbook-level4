package seedu.manager.logic.commands;

import java.util.HashMap;
import java.util.Optional;
import java.util.Map.Entry;

import seedu.manager.commons.core.EventsCenter;
import seedu.manager.commons.events.ui.JumpToTagListRequestEvent;
import seedu.manager.commons.exceptions.IllegalValueException;
import seedu.manager.model.task.Desc;
import seedu.manager.model.task.Done;
import seedu.manager.model.task.EndTime;
import seedu.manager.model.task.Priority;
import seedu.manager.model.task.StartTime;
import seedu.manager.model.task.Tag;
import seedu.manager.model.task.TaskProperty;
import seedu.manager.model.task.Venue;
import seedu.manager.model.task.Task.TaskProperties;

/**
 * Finds and lists all tasks in task manager whose desc contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks whose properties contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: [DESC] [<extensions>]\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie venue home priority med";

    public static final String MESSAGE_SUCCESS = "Found Task(s): %1$s";
    public static final String MESSAGE_DUPLICATE_KEYWORDS = "There are 2 or more of the same keywords you have entered.";

    private final HashMap<TaskProperties, Optional<String>> foundProperties;
    
    public FindCommand(HashMap<TaskProperties, Optional<String>> foundProperties) 
            throws IllegalValueException {
        this.foundProperties = foundProperties;
    }
    
    //@@author A0139621H
    @Override
    public CommandResult execute() {
        try {
        	HashMap<TaskProperties, Optional<TaskProperty>> builtProperties = buildProperties(foundProperties); 
            model.updateFilteredTaskList(builtProperties);
            jumpToTagIfPresent(builtProperties);
            return new CommandResult(getMessageForTaskListShownSummary(model.getSortedFilteredTaskList().size()));
        } catch (IllegalValueException e) {
            return new CommandResult(e.getMessage());
        }
    }
    
    private HashMap<TaskProperties, Optional<TaskProperty>> buildProperties(HashMap<TaskProperties, Optional<String>> propertiesStrings) throws IllegalValueException {
        HashMap<TaskProperties, Optional<TaskProperty>> properties = new HashMap<>();
        for (Entry<TaskProperties, Optional<String>> prop : propertiesStrings.entrySet()) {
            Optional<TaskProperty> taskProperty = buildProperty(prop.getKey(), prop.getValue());
            properties.put(prop.getKey(), taskProperty);
        }
        
        return properties;
    }
    
    /**
     * Builds a TaskProperty object using a value from the TaskProperties enum and a value
     * @param property that should be built
     * @param value of the property
     * @return value of the property if found
     */
    private Optional<TaskProperty> buildProperty(TaskProperties property, Optional<String> value) throws IllegalValueException {
        if (!value.isPresent()) {
            return Optional.empty();
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
        case TAG:
            return Optional.of(new Tag(stringValue));
        default:
            throw new IllegalValueException("Property not found");
        }
    }
    
    // @@author A0147924X
    private void jumpToTagIfPresent(HashMap<TaskProperties, Optional<TaskProperty>> properties) {
    	if (properties.get(TaskProperties.TAG).isPresent()) {
			int targetIndex = model.getIndexOfTag((Tag) properties.get(TaskProperties.TAG).get());
			if (targetIndex != -1) {
				EventsCenter.getInstance().post(new JumpToTagListRequestEvent(targetIndex));
			}
		}
    }
}