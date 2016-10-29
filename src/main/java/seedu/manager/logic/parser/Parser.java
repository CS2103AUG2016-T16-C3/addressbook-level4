package seedu.manager.logic.parser;

import static seedu.manager.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.manager.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.manager.commons.core.CommandWord.Commands;
import seedu.manager.commons.exceptions.IllegalValueException;
import seedu.manager.commons.util.StringUtil;
import seedu.manager.logic.commands.*;

/**
 * Parses user input.
 */
public class Parser {
    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private static final Pattern PERSON_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");
    
    private static final Pattern EDIT_KEYWORDS_FORMAT =
            Pattern.compile("(?<targetIndex>\\d+)\\s(?<arguments>.+)");
    
    private static final Pattern FIND_KEYWORDS_FORMAT =
            Pattern.compile("(?<arguments>.+)");
    
    private final ExtensionParser extParser;
    
    private HashMap<Commands, String> commandWords = null;

    // @@author A0147924X
    public Parser(HashMap<Commands, String> commandWordsIn, HashMap<Commands, String> extensionWordsIn) {
    	commandWords = commandWordsIn;
    	extParser = new ExtensionParser(extensionWordsIn);
    }
    
    /**
     * Compiles the regexes used in the parser
     */
    public void compileRegexes() {
    	extParser.compileRegexes();
    }
    
    // @@author
    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input 
     */
    public Command parseCommand(String userInput) {
    	assert commandWords != null;
    	
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord").trim();
        final String arguments = matcher.group("arguments").trim();
        
        Commands matchedCommand;
        
        try {
			matchedCommand = getMatchedCommand(commandWord);
		} catch (IllegalValueException e) {
			return new IncorrectCommand(e.getMessage());
		}
        
        switch (matchedCommand) {

        case ADD:
            return prepareAdd(arguments);

        case DELETE:
            return prepareDelete(arguments);
            
        case EDIT:
            return prepareEdit(arguments);

        case CLEAR:
            return new ClearCommand();

        case FIND:
            return prepareFind(arguments);
        
        case DONE:
            return prepareDone(arguments);

        case LIST:
            return new ListCommand();

        case EXIT:
            return new ExitCommand();

        case HELP:
            return new HelpCommand();
        
        case STORAGE:
            return new StorageCommand(arguments);
        
        case SORT:
        	return new SortCommand();
        
        case UNDO:
        	return new UndoCommand();

        case ALIAS:
        	return prepareAlias(arguments);

        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }
    
    /**
     * @@author A0147924X
     * Get the command which matches with the command word entered by the user
     * @param commandWord
     * @throws IllegalValueException
     */
    private Commands getMatchedCommand(String commandWord) throws IllegalValueException {
    	for (Commands command : Commands.values()) {
			if (commandWords.containsKey(command) && commandWords.get(command).equals(commandWord)) {
				return command;
			}
		}
    	
    	throw new IllegalValueException(MESSAGE_UNKNOWN_COMMAND);
    }

	/**
	 * @@author
     * Parses arguments in the context of the add task command.
     *
     * @param args full command args string
     * @return the prepared command 
     */
    private Command prepareAdd(String args) {        
        try {
            return new AddCommand(
                    extParser.getTaskProperties(args)
            );
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

    /**
     * Parses arguments in the context of the delete task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareDelete(String args) {

        Optional<Integer> index = parseIndex(args);
        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        return new DeleteCommand(index.get());
    }
    
    /**
     * Parses arguments in the context of the edit task command.
     *
     * @param args full command args string
     * @return the prepared command 
     */
    private Command prepareEdit(String args) {
        final Matcher matcher = EDIT_KEYWORDS_FORMAT.matcher(args);
        if(!matcher.matches()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }
        
        Optional<Integer> index = parseIndex(matcher.group("targetIndex"));
        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }
        
        try {
            return new EditCommand(index.get(), extParser.getTaskProperties(matcher.group("arguments")));
        } catch (IllegalValueException e) {
            return new IncorrectCommand(e.getMessage());
        } 
    }
    
    /**
     * @@author A0147924X
     * Parses arguments in the context of the done task command
     * @param args full commmand args string
     * @return the prepared command
     */
    private Command prepareDone(String args) {
    	Optional<Integer> index = parseIndex(args);
        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE));
        }

        return new DoneCommand(index.get());
	}

    /**
     * @@author
     * Returns the specified index in the {@code command} IF a positive unsigned integer is given as the index.
     *   Returns an {@code Optional.empty()} otherwise.
     */
    private Optional<Integer> parseIndex(String command) {
        final Matcher matcher = PERSON_INDEX_ARGS_FORMAT.matcher(command.trim());
        if (!matcher.matches()) {
            return Optional.empty();
        }

        String index = matcher.group("targetIndex");
        if(!StringUtil.isUnsignedInteger(index)){
            return Optional.empty();
        }
        return Optional.of(Integer.parseInt(index));

    }

    /**
     * Parses arguments in the context of the find task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareFind(String args) {
        final Matcher matcher = FIND_KEYWORDS_FORMAT.matcher(args);
        if(!matcher.matches()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        
        try {
            return new FindCommand(extParser.getTaskProperties(matcher.group("arguments")));
        } catch (IllegalValueException e) {
            return new IncorrectCommand(e.getMessage());
        }
    }
    
    /**
     * @@author A0147924X
     * Parses arguments in the context of the alias command
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareAlias(String args) {
    	String[] splitArgs = args.split(" ");
    	if (splitArgs.length != 2) {
			return new IncorrectCommand(AliasCommand.MESSAGE_WRONG_NUM_ARGS);
		}
    	
    	return new AliasCommand(splitArgs[0], splitArgs[1]);
    }
}