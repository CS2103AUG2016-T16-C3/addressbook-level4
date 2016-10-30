package seedu.manager.logic.commands;

public class UndoCommand extends Command {
    
	public static final String COMMAND_WORD = "undo";
	
	public static final String NO_UNDOABLE_COMMANDS = "Sorry. the undo_command list is empty.";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Undo your last undoable step. e.g. add, delete or edit.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_UNDO_MESSAGE = "Typed in \"undo\" to the input box.";

    public UndoCommand() {}
    
    @Override
    public CommandResult execute() {
    	assert model != null;
    	if(undoList.isEmpty()) {
    		return new CommandResult(NO_UNDOABLE_COMMANDS);
    	}
    	
    	UndoableCommand undoCommand = undoList.getLast();
    	undoList.removeLast();
    	
    	return undoCommand.undoIt();
    }
}
