package seedu.manager.logic.commands;

import seedu.manager.model.task.Task.TaskProperties;

// @@author A0147924X
/**
 * Allows user to sort the displayed tasks by priority
 *
 */	
public class SortCommand extends Command {
	public static final String COMMAND_WORD = "sortby";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts the displayed tasks by priority, from highest to lowest. Tasks with no priority are shown last.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Sorted task list by %1$s";
    public static final String MESSAGE_NOT_A_COMPARATOR = "This is not a valid property to sort by";
    
    public static enum SortComparators {
    	TIME("time"), PRIORITY("priority");
    	
    	private String value;
    	
    	private SortComparators(String value) {
			this.value = value;
		}
    	
    	public String getValue() {
    		return value;
    	}
    }
    
    private SortComparators comparator;
    
    public SortCommand(SortComparators comparator) {
    	this.comparator = comparator;
    }

	@Override
	public CommandResult execute() {
		switch (comparator) {
		case PRIORITY:
			model.sortSortedFilteredTaskListByPriority();
			break;
		
		case TIME:
			model.sortSortedFilteredTaskListByTime();
			break;

		default:
			return new CommandResult(MESSAGE_NOT_A_COMPARATOR);
		}
		
		return new CommandResult(String.format(MESSAGE_SUCCESS, comparator.getValue()));
	}
}
