package seedu.manager.logic.commands;


/**
 * Represents an incorrect command. Upon execution, produces some feedback to the user.
 */
public class IncorrectCommand extends Command {

    public final String feedbackToUser;

    public IncorrectCommand(String feedbackToUser){
        this.feedbackToUser = feedbackToUser;
    }

    @Override
    public CommandResult execute() {
        indicateAttemptToExecuteIncorrectCommand();
        return new CommandResult(feedbackToUser);
    }
    
    @Override
    public int undoability() {
    	return 0;
    }

	@Override
	public CommandResult undoIt() {
		// TODO Auto-generated method stub
		return null;
	}

}

