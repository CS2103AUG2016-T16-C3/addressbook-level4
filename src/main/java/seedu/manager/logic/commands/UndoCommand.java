package seedu.manager.logic.commands;

// @@ author A0148003U
/**
 * Allows users to undo the previous operations by typing in "undo";
 */

public class UndoCommand extends Command {
    
	public static final String COMMAND_WORD = "undo";
	
	public static final String NO_UNDOABLE_COMMANDS = "Sorry, there are no commands to undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Undo your last undoable step, "
    		+ "e.g. add, delete, edit, done, alias and sort.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_UNDO_MESSAGE = "Typed in \"undo\" to the input box.";

    public UndoCommand() {}
    
    @Override
    public CommandResult execute() {
    	assert model != null;
    	if (undoList.isEmpty()) {
    		return new CommandResult(NO_UNDOABLE_COMMANDS);
    	}
    	
    	UndoableCommand undoCommand = undoList.getLast();
    	undoList.removeLast();
    	
    	return undoCommand.undoIt();
    }
}

