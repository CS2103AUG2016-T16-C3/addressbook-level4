package seedu.manager.logic.commands;

import seedu.manager.commons.exceptions.IllegalValueException;

public class AliasCommand extends Command {
	
	public static final String MESSAGE_SUCCESS = "Old command %1$s changed to %2$s";
	
	private final String oldCommand;
	private final String alias;
	
	public AliasCommand(String oldCommand, String alias) {
		this.oldCommand = oldCommand;
		this.alias = alias;
	}

	@Override
	public CommandResult execute() {
		try {
			model.setSingleCommandWord(oldCommand, alias);
			
			return new CommandResult(String.format(MESSAGE_SUCCESS, oldCommand, alias));
		} catch (IllegalValueException e) {
			return new CommandResult(e.getMessage());
		}
	}
}
