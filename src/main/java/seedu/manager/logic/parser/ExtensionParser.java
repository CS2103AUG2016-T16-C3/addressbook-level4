package seedu.manager.logic.parser;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.manager.commons.exceptions.IllegalValueException;
import seedu.manager.model.task.Task;
import seedu.manager.model.task.Task.TaskProperties;

/**
 * Used to parse extensions in the user input
 * @author varungupta
 *
 */
public class ExtensionParser {
    
    public static enum ExtensionCmds {
        VENUE("venue"), BY("by"), EVENT("from"), AT("at"), PRIORITY("priority");
        
        private String value;
        
        private ExtensionCmds(String value) {
            this.value = value;
        }
        
        public String getValue() {
            return value;
        }
    };
    
    private static final String EXTENSION_REGEX_OPTIONS;
    private static final Pattern EXTENSIONS_DESC_FORMAT;
    private static final Pattern EXTENSIONS_ARGS_FORMAT;
    private static final Pattern EXTENSION_ARGS_FORMAT = 
            Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final String EXTENSION_INVALID_FORMAT = "Extensions should have the form <extension> <arguments>";
    
    public static final String EXTENSION_FROM_TO_INVALID_FORMAT = "From-to times should be in the format from <startTime> to <endTime>";
    private static final Pattern EVENT_ARGS_FORMAT = 
            Pattern.compile("(?<startTime>.+?)\\sto\\s(?<endTime>.+)");
    
    public static final String EXTENSION_DUPLICATES = "Extensions should only contain one %1$s"; 
    
    static {
        EXTENSION_REGEX_OPTIONS = String.join("|", Arrays.stream(ExtensionCmds.values()).map(ex -> ex.getValue()).toArray(size -> new String[size]));
        EXTENSIONS_DESC_FORMAT = 
                Pattern.compile("(^.*?(?=(?:(?:(\\s|^)(?:"
                        + EXTENSION_REGEX_OPTIONS
                        + ")\\s)|$)))");
        EXTENSIONS_ARGS_FORMAT =
                Pattern.compile("((?:"
                        + EXTENSION_REGEX_OPTIONS
                        + ").+?(?=(?:(?:\\s(?:"
                        + EXTENSION_REGEX_OPTIONS
                        + ")\\s)|$)))");
    }
    
    public ExtensionParser() {}
    
    /**
     * Build task from extensions
     */
    public HashMap<Task.TaskProperties, Optional<String>> getTaskProperties(String extensionsStr) throws IllegalValueException {
        HashMap<Task.TaskProperties, Optional<String>> properties = new HashMap<>();
        extensionsStr = extensionsStr.trim();
        
        for (Task.TaskProperties property : Task.TaskProperties.values()) {
            properties.put(property, Optional.empty());
        }
        
        Matcher descMatcher = EXTENSIONS_DESC_FORMAT.matcher(extensionsStr);
        if (descMatcher.find()) {
            String desc = descMatcher.group().trim();
            properties.put(TaskProperties.DESC, 
                    desc.equals("") ? Optional.empty() : Optional.of(desc));
        }
        
        Matcher extMatcher = EXTENSIONS_ARGS_FORMAT.matcher(extensionsStr);
        while (extMatcher.find()) {
            parseSingleExtension(extMatcher.group(), properties);
        }
        
        return properties;
    }
    
    /**
     * Parses a single extension
     * @throws IllegalValueException
     */
    private void parseSingleExtension(String extension, HashMap<Task.TaskProperties, Optional<String>> properties) 
            throws IllegalValueException{
        Matcher matcher = EXTENSION_ARGS_FORMAT.matcher(extension);
        if (matcher.matches()) {
            String extensionCommand = matcher.group("commandWord").trim();
            String arguments = matcher.group("arguments").trim();
            ExtensionCmds matchedCommand = null;
            
            for (ExtensionCmds ex : ExtensionCmds.values()) {
                if (ex.value.equals(extensionCommand)) {
                    matchedCommand = ex;
                    break;
                }
            }
            
            if (matchedCommand == null) {
                throw new IllegalValueException(EXTENSION_INVALID_FORMAT);
            }
            
            switch (matchedCommand) {
            case VENUE:
                throwExceptionIfDuplicate(properties, TaskProperties.VENUE, ExtensionCmds.VENUE);
                addToProperties(properties, TaskProperties.VENUE, arguments);
                break;
            case BY:
                throwExceptionIfDuplicate(properties, TaskProperties.ENDTIME, ExtensionCmds.BY);
                addToProperties(properties, TaskProperties.ENDTIME, arguments);
                break;
            case AT:
                throwExceptionIfDuplicate(properties, TaskProperties.STARTTIME, ExtensionCmds.AT);
                addToProperties(properties, TaskProperties.STARTTIME, arguments);
                break;
            case EVENT:
                throwExceptionIfDuplicate(properties, TaskProperties.STARTTIME, ExtensionCmds.EVENT);
                throwExceptionIfDuplicate(properties, TaskProperties.ENDTIME, ExtensionCmds.EVENT);
                addEvent(properties, arguments);
                break;
            case PRIORITY:
                throwExceptionIfDuplicate(properties, TaskProperties.PRIORITY, ExtensionCmds.PRIORITY);
                addToProperties(properties, TaskProperties.PRIORITY, arguments);
                break;
            default:
                throw new IllegalValueException(EXTENSION_INVALID_FORMAT);
            }
        } else {
            throw new IllegalValueException(EXTENSION_INVALID_FORMAT);
        }
    }
    
    private void throwExceptionIfDuplicate(HashMap<Task.TaskProperties, Optional<String>> properties,
            TaskProperties taskProperty, ExtensionCmds extensionCmd) throws IllegalValueException {
        if (properties.get(taskProperty).isPresent()) {
            throw new IllegalValueException(String.format(EXTENSION_DUPLICATES, extensionCmd.getValue()));
        }
    }
    
    private void addEvent(HashMap<Task.TaskProperties, Optional<String>> properties, String arguments)
    throws IllegalValueException {
        Matcher matcher = EVENT_ARGS_FORMAT.matcher(arguments);
        
        if (!matcher.matches()) {
            throw new IllegalValueException(EXTENSION_FROM_TO_INVALID_FORMAT);
        }
        
        String startTime = matcher.group("startTime").trim();
        String endTime = matcher.group("endTime").trim();
        
        addToProperties(properties, TaskProperties.STARTTIME, startTime);
        addToProperties(properties, TaskProperties.ENDTIME, endTime);
    }
    
    private void addToProperties(HashMap<Task.TaskProperties, Optional<String>> properties, 
            TaskProperties taskProperty, String arguments) {
        properties.put(taskProperty, arguments.equals("") ? Optional.empty() : Optional.of(arguments));
    }
}
