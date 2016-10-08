package seedu.manager.logic.commands;

import seedu.manager.commons.core.Messages;
import seedu.manager.commons.core.UnmodifiableObservableList;
import seedu.manager.commons.exceptions.IllegalValueException;
import seedu.manager.model.task.Desc;
import seedu.manager.model.task.EndTime;
import seedu.manager.model.task.Priority;
import seedu.manager.model.task.ReadOnlyTask;
import seedu.manager.model.task.StartTime;
import seedu.manager.model.task.Task;
import seedu.manager.model.task.UniqueTaskList;
import seedu.manager.model.task.Venue;
import seedu.manager.model.task.UniqueTaskList.TaskNotFoundException;

public class EditCommand extends Command {
    
    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer) DESC v/VENUE st/STARTTIME et/ENDTIME p/PRIORITY\n"
            + "Example: " + COMMAND_WORD + " 1 Dinner with Lancelot v/Avalon st/8:30pm et/9:00pm p/high";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Task: %1$s";
    public static final String MESSAGE_DUPLICATE_PARAMS = "The new parameters are the same as before";

    public final int targetIndex;
    
    private final Task editedTask;

    public EditCommand(int targetIndex, String desc, String venue, String priority, String startTime, String endTime) 
            throws IllegalValueException {
        this.targetIndex = targetIndex;
        this.editedTask = new Task(
                new Desc(desc),
                new Venue(venue),
                new Priority(priority),
                new StartTime(startTime),
                new EndTime(endTime)
        );
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToEdit = lastShownList.get(targetIndex - 1);
        
        try {
            
        } catch (TaskNotFoundException e) {
            assert false : "The target task cannot be missing";
        } catch (UniqueTaskList.DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_PARAMS);
        }
    }

}
