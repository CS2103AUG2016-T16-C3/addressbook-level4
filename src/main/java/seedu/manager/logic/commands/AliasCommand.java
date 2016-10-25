package seedu.manager.logic.commands;

import seedu.manager.commons.exceptions.IllegalValueException;

public class AliasCommand extends Command {
	
	public static final String MESSAGE_SUCCESS = "Old command %1$s changed to %2$s";
	public static final String MESSAGE_WRONG_NUM_ARGS = "Alias command should have exactly 2 parameters";
	public static final String MESSAGE_NO_MATCH = "I cannot recognise the command you want to alias";
	public static final String MESSAGE_ALIAS_TAKEN = "This alias is already taken by the %1$s command";
	
	private final String oldCommand;
	private final String alias;
	
	public AliasCommand(String oldCommand, String alias) {
		this.oldCommand = oldCommand;
		this.alias = alias;
	}

	@Override
	public CommandResult execute() {
		try {
			model.setSingleCommandWord(oldCommand, alias, MESSAGE_NO_MATCH, MESSAGE_ALIAS_TAKEN);
			
			return new CommandResult(String.format(MESSAGE_SUCCESS, oldCommand, alias));
		} catch (IllegalValueException e) {
			return new CommandResult(e.getMessage());
		}
	}
}
