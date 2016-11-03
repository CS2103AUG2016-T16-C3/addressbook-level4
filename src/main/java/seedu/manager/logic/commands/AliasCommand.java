package seedu.manager.logic.commands;

import seedu.manager.commons.core.EventsCenter;
import seedu.manager.commons.events.logic.CommandWordsChangedEvent;
import seedu.manager.commons.exceptions.IllegalValueException;

// @@author A0147924X
/**
 * Allows user to alias commands
 *
 */
public class AliasCommand extends Command implements UndoableCommand {
	
	public static final String COMMAND_WORD = "alias";
	
	public static final String MESSAGE_SUCCESS = "Old command %1$s changed to %2$s";
	public static final String UNDO_SUCCESS = "Alias %1$s changed back to %2$s";
	public static final String MESSAGE_WRONG_NUM_ARGS = "Alias command should have exactly 2 parameters";
	public static final String MESSAGE_NO_MATCH = "I cannot recognise the command you want to alias";
	public static final String MESSAGE_ALIAS_TAKEN = "This alias is already taken by the %1$s command";
	public static final String MESSAGE_USAGE = COMMAND_WORD + ": Aliases a command to whatever you want. "
            + "Parameters: <old alias> <new alias> \n"
            + "Example: " + COMMAND_WORD
            + " add +";
	
	private final String oldCommand;
	private final String alias;
	
	public AliasCommand(String oldCommand, String alias) {
		this.oldCommand = oldCommand;
		this.alias = alias;
	}

	@Override
	public CommandResult execute() {
		return aliasCommand(oldCommand, alias, false);
	}
	
	@Override
	public CommandResult undoIt() {
		return aliasCommand(alias, oldCommand, true);
	}
	
	/**
	 * Alias a given command to a new keyword
	 * @param oldCommand The old command, which should be aliased
	 * @param alias The new alias for this command
	 * @param isBeingUndone Whether the command is being undone or not 
	 * @return CommandResult representing the outcome of the command
	 */
	private CommandResult aliasCommand(String oldCommand, String alias, boolean isBeingUndone) {
		try {
			model.setSingleCommandWord(oldCommand, alias, MESSAGE_NO_MATCH, MESSAGE_ALIAS_TAKEN);
			
			EventsCenter.getInstance().post(new CommandWordsChangedEvent());
			
			if (!isBeingUndone) {
				this.addUndo(this);
			}
			
			String messageToShow = isBeingUndone ? UNDO_SUCCESS : MESSAGE_SUCCESS; 
			return new CommandResult(String.format(messageToShow, oldCommand, alias));
		} catch (IllegalValueException e) {
			return new CommandResult(e.getMessage());
		}
	}
}
