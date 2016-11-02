package seedu.manager.logic.commands;

import java.util.Optional;

import seedu.manager.commons.core.EventsCenter;
import seedu.manager.commons.core.CommandWord.Commands;
import seedu.manager.commons.events.ui.ShowHelpRequestEvent;
import seedu.manager.commons.exceptions.IllegalValueException;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";
    public static final String MESSAGE_WRONG_NUM_ARGS = "Help command cannot have more than 1 parameter";
    public static final String MESSAGE_WRONG_HELP_COMMAND = "Cannot recognise given command, so no help shown";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions. "
    		+ "If a command is specified, then instructions for that command will be shown. \n"
    		+ "Parameters: [<command>] \n"
            + "Example: " + COMMAND_WORD
            + " add";

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";
    
    public static final String MESSAGE_AT_USAGE = "at: Let's you specify that the task takes place at a certain time. "
            + "Parameters: <time> \n"
            + "Example: at tomorrow 5pm";
    public static final String MESSAGE_BY_USAGE = "by: Let's you specify a deadline for the task. "
            + "Parameters: <time> \n"
            + "Example: by tomorrow 5pm";
    public static final String MESSAGE_EVENT_USAGE = "from-to: Let's you specify start and end times for the task. "
            + "Parameters: <start time> to <end time>\n"
            + "Example: from 7pm to 9pm";
    public static final String MESSAGE_PRIORITY_USAGE = "priority: Let's you specify a priority for the task. "
            + "Parameters: <low/med/high>\n"
            + "Example: priority low";
    public static final String MESSAGE_VENUE_USAGE = "venue: Let's you specify the task's venue. "
            + "Parameters: <Task Venue> \n"
            + "Example: venue Avalon";
    public static final String MESSAGE_TAG_USAGE = "tag: Let's you specify the task's tag. "
            + "Parameters: <Tag> \n"
            + "Example: tag Legend";
    
    private final Optional<String> commandToGetHelpFor;

    public HelpCommand(Optional<String> commandToGetHelpFor) {
    	this.commandToGetHelpFor = commandToGetHelpFor;
    }

    @Override
    public CommandResult execute() {
    	System.out.println(commandToGetHelpFor);
    	System.out.println(commandToGetHelpFor.isPresent());
        if (commandToGetHelpFor.isPresent()) {
			return getHelpForMatchingCommand();
		} else {
			EventsCenter.getInstance().post(new ShowHelpRequestEvent());
			return new CommandResult(SHOWING_HELP_MESSAGE);
		}
    }
    
    private CommandResult getHelpForMatchingCommand() {
    	try {
			Commands matchedCommand = getMatchedKeyword(commandToGetHelpFor.get());
			String helpForCommand = constructHelpForCommand(matchedCommand);
			String aliasForCommand = "Alias: " + model.getAliasForCommand(matchedCommand);
			return new CommandResult(helpForCommand + "\n" + aliasForCommand);
		} catch (IllegalValueException e) {
			indicateAttemptToExecuteIncorrectCommand();
			return new CommandResult(e.getMessage());
		}
    }
    
    private Commands getMatchedKeyword(String userInputCommand) throws IllegalValueException {
    	for (Commands command : Commands.values()) {
			if (command.toString().equals(userInputCommand)) {
				return command;
			}
		}
    	
    	throw new IllegalValueException(MESSAGE_WRONG_HELP_COMMAND);
    }
    
    private String constructHelpForCommand(Commands command) {
    	switch (command) {
		case ADD:
			return AddCommand.MESSAGE_USAGE;
		
		case ALIAS:
			return AliasCommand.MESSAGE_USAGE;
		
		case CLEAR:
			return ClearCommand.MESSAGE_USAGE;
		
		case DELETE:
			return DeleteCommand.MESSAGE_USAGE;
		
		case DONE:
			return DoneCommand.MESSAGE_USAGE;
		
		case EDIT:
			return EditCommand.MESSAGE_USAGE;
		
		case EXIT:
			return ExitCommand.MESSAGE_USAGE;
		
		case FIND:
			return FindCommand.MESSAGE_USAGE;
		
		case HELP:
			return HelpCommand.MESSAGE_USAGE;
		
		case LIST:
			return ListCommand.MESSAGE_USAGE;
		
		case SORT:
			return SortCommand.MESSAGE_USAGE;
		
		case STORAGE:
			return StorageCommand.MESSAGE_USAGE;
		
		case UNDO:
			return UndoCommand.MESSAGE_USAGE;
		
		case AT:
			return MESSAGE_AT_USAGE;
		
		case BY:
			return MESSAGE_BY_USAGE;
		
		case EVENT:
			return MESSAGE_EVENT_USAGE;
		
		case PRIORITY:
			return MESSAGE_PRIORITY_USAGE;
		
		case TAG:
			return MESSAGE_TAG_USAGE;
		
		case VENUE:
			return MESSAGE_VENUE_USAGE;

		default:
			assert false;
			return MESSAGE_WRONG_HELP_COMMAND;
		}
    }
}
