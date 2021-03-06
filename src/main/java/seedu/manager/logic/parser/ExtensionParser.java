package seedu.manager.logic.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.manager.commons.core.CommandWord.Commands;
import seedu.manager.commons.exceptions.IllegalValueException;
import seedu.manager.model.task.Task.TaskProperties;

import seedu.manager.model.task.StartTime;
import seedu.manager.model.task.EndTime;
import seedu.manager.model.task.Tag;

// @@author A0147924X
/**
 * Used to parse extensions in the user input
 *
 */
public class ExtensionParser {
    public static final String EXTENSION_FROM_TO_INVALID_FORMAT = "From-to times should be in the format from <startTime> to <endTime>";
    public static final String EXTENSION_DUPLICATES = "Extensions should only contain one %1$s";
    public static final String START_AFTER_END = "Start time should be before end time.";
    
    private static final String ESCAPING_CHARACTER = "'"; 
    private final String EXTENSION_INVALID_FORMAT = "Extensions should have the form <extension> <arguments>";
    private String EXTENSION_REGEX_OPTIONS;
    private Pattern EXTENSIONS_DESC_FORMAT;
    private Pattern EXTENSIONS_ARGS_FORMAT;
    private final Pattern EXTENSION_ARGS_FORMAT = 
            Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private final Pattern EVENT_ARGS_FORMAT = 
            Pattern.compile("(?<startTime>.+?)\\sto\\s(?<endTime>.+)");
    
    private HashMap<Commands, String> extensionWords = null;
    
    public ExtensionParser(HashMap<Commands, String> extensionWordsIn) {
        extensionWords = extensionWordsIn;
        compileRegexes();
    }
    
    public void compileRegexes() {
    	EXTENSION_REGEX_OPTIONS = String.join("|", extensionWords.values());
        EXTENSIONS_DESC_FORMAT = 
                Pattern.compile("(^.*?(?=(?:(?:(?:\\s|^)(?:"
                        + EXTENSION_REGEX_OPTIONS
                        + ")\\s)|$)))");
        EXTENSIONS_ARGS_FORMAT =
                Pattern.compile("((?:^|\\s)(?:"
                        + EXTENSION_REGEX_OPTIONS
                        + ")\\s.+?(?=(?:(?:\\s(?:"
                        + EXTENSION_REGEX_OPTIONS
                        + ")\\s)|$)))");
    }
    
    /**
     * Build task from extensions
     * @param extensionsStr String containing the extensions to be parsed
     * @return Hashmap of properties representing the new task
     * @throws IllegalValueException
     */
    public HashMap<TaskProperties, Optional<String>> getTaskProperties(String extensionsStr) 
    		throws IllegalValueException {
        HashMap<TaskProperties, Optional<String>> properties = new HashMap<>();
        extensionsStr = extensionsStr.trim();
        
        for (TaskProperties property : TaskProperties.values()) {
            properties.put(property, Optional.empty());
        }
        
        parseDesc(extensionsStr, properties);
        
        Matcher extMatcher = EXTENSIONS_ARGS_FORMAT.matcher(extensionsStr);
        while (extMatcher.find()) {
            parseSingleExtension(extMatcher.group().trim(), properties);
        }
        
        return properties;
    }
    
    /**
     * Parses the task description into the properties
     * @param extensionsStr Full string containing the extensions
     * @param properties The Hashmap of properties to which the description will be added
     */
    private void parseDesc(String extensionsStr, HashMap<TaskProperties, Optional<String>> properties) {
    	Matcher descMatcher = EXTENSIONS_DESC_FORMAT.matcher(extensionsStr);
        if (descMatcher.find()) {
            String desc = descMatcher.group().trim();
            desc = removeEscapingChars(desc);
            properties.put(TaskProperties.DESC, 
                    desc.equals("") ? Optional.empty() : Optional.of(desc));
        }
    }
    
    /**
     * Parses a single extension given a string containing extension word and arguments
     * @param extension User input with only one extension and arguments
     * @param properties The Hashmap of properties to which the new extension will be added
     * @throws IllegalValueException
     */
    private void parseSingleExtension(String extension, HashMap<TaskProperties, Optional<String>> properties) 
            throws IllegalValueException{
    	
        Matcher matcher = EXTENSION_ARGS_FORMAT.matcher(extension);
        
        if (matcher.matches()) {
            String extensionCommand = matcher.group("commandWord").trim();
            String arguments = matcher.group("arguments").trim();
            arguments = removeEscapingChars(arguments);
            
            Commands matchedCommand = getMatchedCommand(extensionCommand);
            
            switch (matchedCommand) {
            case VENUE:
                throwExceptionIfDuplicate(properties, TaskProperties.VENUE, Commands.VENUE);
                addToProperties(properties, TaskProperties.VENUE, arguments);
                break;
            case BY:
                throwExceptionIfDuplicate(properties, TaskProperties.ENDTIME, Commands.BY);
                addToProperties(properties, TaskProperties.ENDTIME, arguments);
                break;
            case AT:
                throwExceptionIfDuplicate(properties, TaskProperties.STARTTIME, Commands.AT);
                addToProperties(properties, TaskProperties.STARTTIME, arguments);
                break;
            case EVENT:
                throwExceptionIfDuplicate(properties, TaskProperties.STARTTIME, Commands.EVENT);
                throwExceptionIfDuplicate(properties, TaskProperties.ENDTIME, Commands.EVENT);
                addEvent(properties, arguments);
                break;
            case PRIORITY:
                throwExceptionIfDuplicate(properties, TaskProperties.PRIORITY, Commands.PRIORITY);
                addToProperties(properties, TaskProperties.PRIORITY, arguments);
                break;
            case TAG:
                throwExceptionIfDuplicate(properties, TaskProperties.TAG, Commands.TAG);
                addToProperties(properties, TaskProperties.TAG, arguments);
                break;
            default:
                throw new IllegalValueException(EXTENSION_INVALID_FORMAT);
            }
        } else {
            throw new IllegalValueException(EXTENSION_INVALID_FORMAT);
        }
    }
    
    /**
     * Get the extension command which matches the input command
     * @param extensionCommand
     */
	private Commands getMatchedCommand(String extensionCommand) throws IllegalValueException {
		for (Commands command : Commands.values()) {
		    if (extensionWords.containsKey(command) && extensionWords.get(command).equals(extensionCommand)) {
		        return command;
		    }
		}
		
		throw new IllegalValueException(EXTENSION_INVALID_FORMAT);
	}
	
	/**
	 * Removes the escaping characters from extension arguments
	 * @param args Arguments to remove escape characters from
	 * @return Arguments without the escaping characters
	 */
	private String removeEscapingChars(String args) {
		for (String extensionWord : extensionWords.values()) {
			args = args.replaceAll(ESCAPING_CHARACTER + extensionWord + ESCAPING_CHARACTER, extensionWord);
		}
		return args;
	}
    
	/**
	 * Throws an exception if duplicate properties are specified
	 * @param properties Properties to look in.
	 * @param taskProperty Property to check.
	 * @param extensionCmd Command that caused duplication.
	 * @throws IllegalValueException
	 */
    private void throwExceptionIfDuplicate(HashMap<TaskProperties, Optional<String>> properties,
            							   TaskProperties taskProperty,
            							   Commands extensionCmd) throws IllegalValueException {
        if (properties.get(taskProperty).isPresent()) {
            throw new IllegalValueException(String.format(EXTENSION_DUPLICATES, extensionWords.get(extensionCmd)));
        }
    }
    
    /**
     * Adds an event (start time and end time) to the task manager
     * @param properties The current task properties
     * @param arguments Arguments containing the start and end time
     * @throws IllegalValueException
     */
    private void addEvent(HashMap<TaskProperties, Optional<String>> properties, String arguments)
    		     throws IllegalValueException {
        Matcher matcher = EVENT_ARGS_FORMAT.matcher(arguments);
        
        if (!matcher.matches()) {
            throw new IllegalValueException(EXTENSION_FROM_TO_INVALID_FORMAT);
        }
        
        String startTime = matcher.group("startTime").trim();
        String endTime = matcher.group("endTime").trim();
        
        addToProperties(properties, TaskProperties.STARTTIME, startTime);
        addToProperties(properties, TaskProperties.ENDTIME, endTime);
        
        throwExceptionIfTimeInvalid(startTime, endTime);
    }
    
    // @@author A0148042M
    /**
     * Parses events and puts the times into the properties
     * Throw an exception if start time is after end time
     * 
     * @param properties Properties to put in.
     * @param arguments Arguments specifying the time.
     * @throws IllegalValueException
     */
    private void throwExceptionIfTimeInvalid(String startTime, String endTime) throws IllegalValueException {
        StartTime start = new StartTime(startTime);
        EndTime end = new EndTime(endTime);
        
        if (start.getTime().after(end.getTime())) {
            throw new IllegalValueException(START_AFTER_END);
        }
    }
    
    // @@author A0147924X
    /**
     * Adds a property to the properties HashMap
     * 
     * @param properties HashMap to put the new property into.
     * @param taskProperty Property to put.
     * @param arguments Value of the property.
     */
    private void addToProperties(HashMap<TaskProperties, Optional<String>> properties, 
            					 TaskProperties taskProperty, String arguments) {
        properties.put(taskProperty, arguments.equals("") ? Optional.empty() : Optional.of(arguments));
    }
}
