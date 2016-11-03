package seedu.manager.logic.commands;

import java.util.HashMap;
import java.util.Optional;

import seedu.manager.commons.core.EventsCenter;
import seedu.manager.commons.core.CommandWord.Commands;
import seedu.manager.commons.events.ui.ShowHelpRequestEvent;
import seedu.manager.commons.exceptions.IllegalValueException;

// @@author A0147924X
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
    private static final HashMap<Commands, String> usageMessages = new HashMap<>();
    
    static {
    	buildUsageMessageHashmap();
    }

    public HelpCommand(Optional<String> commandToGetHelpFor) {
    	this.commandToGetHelpFor = commandToGetHelpFor;
    	buildUsageMessageHashmap();
    }

    @Override
    public CommandResult execute() {
        if (commandToGetHelpFor.isPresent()) {
			return getHelpForMatchingCommand(commandToGetHelpFor.get());
		} else {
			EventsCenter.getInstance().post(new ShowHelpRequestEvent());
			return new CommandResult(SHOWING_HELP_MESSAGE);
		}
    }
    
    /**
     * Creates a hashmap of usage messages for all commands and extensions
     */
    private static void buildUsageMessageHashmap() {
    	usageMessages.put(Commands.ADD, AddCommand.MESSAGE_USAGE);
		usageMessages.put(Commands.ALIAS, AliasCommand.MESSAGE_USAGE);
		usageMessages.put(Commands.CLEAR, ClearCommand.MESSAGE_USAGE);
		usageMessages.put(Commands.DELETE, DeleteCommand.MESSAGE_USAGE);
		usageMessages.put(Commands.DONE, DoneCommand.MESSAGE_USAGE);
		usageMessages.put(Commands.EDIT, EditCommand.MESSAGE_USAGE);
		usageMessages.put(Commands.EXIT, ExitCommand.MESSAGE_USAGE);
		usageMessages.put(Commands.FIND, FindCommand.MESSAGE_USAGE);
		usageMessages.put(Commands.HELP, HelpCommand.MESSAGE_USAGE);
		usageMessages.put(Commands.LIST, ListCommand.MESSAGE_USAGE);
		usageMessages.put(Commands.SORT, SortCommand.MESSAGE_USAGE);
		usageMessages.put(Commands.STORAGE, StorageCommand.MESSAGE_USAGE);
		usageMessages.put(Commands.UNSORT, UnSortCommand.MESSAGE_USAGE);
		usageMessages.put(Commands.UNDO, UndoCommand.MESSAGE_USAGE);
		
		usageMessages.put(Commands.AT, MESSAGE_AT_USAGE);
		usageMessages.put(Commands.BY, MESSAGE_BY_USAGE);
		usageMessages.put(Commands.EVENT, MESSAGE_EVENT_USAGE);
		usageMessages.put(Commands.PRIORITY, MESSAGE_PRIORITY_USAGE);
		usageMessages.put(Commands.TAG, MESSAGE_TAG_USAGE);
		usageMessages.put(Commands.VENUE, MESSAGE_VENUE_USAGE);
    }
    
    /**
     * Gets the help message for a certain command
     * @param commandToGetHelpFor The command to get help for
     * @return CommandResult representing the help for the given command
     */
    private CommandResult getHelpForMatchingCommand(String commandToGetHelpFor) {
    	try {
			Commands matchedCommand = getMatchedKeyword(commandToGetHelpFor);
			String helpForCommand = constructUsageMessageForCommand(matchedCommand);
			
			String aliasForCommand = "Alias: " + model.getAliasForCommand(matchedCommand);
			
			return new CommandResult(helpForCommand + "\n" + aliasForCommand);
			
		} catch (IllegalValueException e) {
			indicateAttemptToExecuteIncorrectCommand();
			return new CommandResult(e.getMessage());
		}
    }
    
    /**
     * Gets the command keyword matching the user input
     * @param userInputCommand Command that user gave as a String
     * @return Command that matches the user input
     * @throws IllegalValueException If no matching command
     */
    private Commands getMatchedKeyword(String userInputCommand) throws IllegalValueException {
    	for (Commands command : Commands.values()) {
			if (command.toString().equals(userInputCommand)) {
				return command;
			}
		}
    	
    	throw new IllegalValueException(MESSAGE_WRONG_HELP_COMMAND);
    }
    
    /**
     * Constructs the usage message for a command
     * @param command The command for which the usage message will be made
     * @return The usage message for input command
     */
    private String constructUsageMessageForCommand(Commands command) {
    	if (usageMessages.containsKey(command)) {
			return usageMessages.get(command);
		} else {
			return MESSAGE_WRONG_HELP_COMMAND;
		}
    }
}
