package seedu.manager.logic.parser;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Optional;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.manager.commons.exceptions.IllegalValueException;
import seedu.manager.model.UserPrefs;
import seedu.manager.model.task.Task.TaskProperties;

// @@author A0147924X
public class ExtensionParserTest {
    private ExtensionParser extensionParser;
    @Before
    public void init_ext_parser() {
        extensionParser = new ExtensionParser((new UserPrefs()).getExtensionsWords());
    }
    
    @Rule
    public ExpectedException expectedEx = ExpectedException.none();
    
    @Test
    public void parse_invalid_duplicateError() throws IllegalValueException {
    	expectedEx.expect(IllegalValueException.class);
    	expectedEx.expectMessage(String.format(ExtensionParser.EXTENSION_DUPLICATES, "venue"));
    	extensionParser.getTaskProperties("Dinner with Lancelot venue Avalon venue Round Table");    	
        
    	expectedEx.expectMessage(String.format(ExtensionParser.EXTENSION_DUPLICATES, "priority"));
        extensionParser.getTaskProperties("Dinner with Lancelot venue Avalon priority high priority low");
        
        expectedEx.expectMessage(String.format(ExtensionParser.EXTENSION_DUPLICATES, "at"));
        extensionParser.getTaskProperties("Dinner with Lancelot priority high at 8:30 at 9:00");
    }
    
    @Test
    public void parse_invalid_fromToFormatError() throws IllegalValueException {
    	expectedEx.expect(IllegalValueException.class);
    	expectedEx.expectMessage(ExtensionParser.EXTENSION_FROM_TO_INVALID_FORMAT);
    	extensionParser.getTaskProperties("Dinner with Lancelot from 8:30");
        
    	expectedEx.expectMessage(ExtensionParser.EXTENSION_FROM_TO_INVALID_FORMAT);
        extensionParser.getTaskProperties("Dinner with Lancelot from 8:30to 9:30");

        expectedEx.expectMessage(ExtensionParser.EXTENSION_FROM_TO_INVALID_FORMAT);
        extensionParser.getTaskProperties("Dinner with Lancelot from 8:30 to9:30");
    }
    
    @Test
    public void parse_allExt_successful() throws IllegalValueException {
        assertEquals(
                constructProperties("Dinner with Lancelot", "Avalon", "high", "7:30", "8:30"),
                extensionParser.getTaskProperties("Dinner with Lancelot venue Avalon priority high from 7:30 to 8:30")
                );
    }
    
    @Test 
    public void parse_ext_successful() throws IllegalValueException {
        assertEquals(
                constructProperties("Dinner with Arthur", "Round Table", "high", "", ""),
                extensionParser.getTaskProperties("Dinner with Arthur venue Round Table priority high")
                );
        assertEquals(
                constructProperties("Dinner with Arthur", "", "low", "7:30pm", ""),
                extensionParser.getTaskProperties("Dinner with Arthur priority low at 7:30pm")
                );
        assertEquals(
                constructProperties("Dinner with Arthur", "", "low", "", "8:30pm"),
                extensionParser.getTaskProperties("Dinner with Arthur priority low by 8:30pm")
                );
        assertEquals(
                constructProperties("Dinner with Arthur", "", "", "7:30pm", "8:30pm"),
                extensionParser.getTaskProperties("Dinner with Arthur from 7:30pm to 8:30pm")
                );
        assertEquals(
                constructProperties("Dinner with Arthur", "", "low", "7:30pm", "8:30pm"),
                extensionParser.getTaskProperties("Dinner with Arthur from 7:30pm to 8:30pm priority low")
                );
    }
    
    @Test
    public void parse_onlyDesc_successful() throws IllegalValueException {
        assertEquals(
                constructProperties("Dinner with Lancelot", "", "", "", ""),
                extensionParser.getTaskProperties("Dinner with Lancelot")
                );
    }
    
    @Test
    public void parse_noDesc_successful() throws IllegalValueException {
        assertEquals(
                constructProperties("", "Avalon", "high", "7:30", "8:30"),
                extensionParser.getTaskProperties("venue Avalon priority high from 7:30 to 8:30")
                );
    }
    
    @Test
    public void parse_extraSpacing_successful() throws IllegalValueException {
        assertEquals(
                constructProperties("Dinner with Lancelot", "Avalon", "high", "7:30", "8:30"),
                extensionParser.getTaskProperties("  Dinner with Lancelot venue Avalon priority high from 7:30 to 8:30")
                );
        assertEquals(
                constructProperties("Dinner with Lancelot", "Avalon", "high", "7:30", "8:30"),
                extensionParser.getTaskProperties("Dinner with Lancelot   venue Avalon priority high from 7:30 to 8:30")
                );
        assertEquals(
                constructProperties("Dinner with Lancelot", "Avalon", "high", "7:30", "8:30"),
                extensionParser.getTaskProperties("Dinner with Lancelot venue Avalon priority high from 7:30 to 8:30  ")
                );
        assertEquals(
                constructProperties("Dinner with Lancelot", "Avalon", "high", "7:30", "8:30"),
                extensionParser.getTaskProperties("Dinner with Lancelot venue Avalon   priority high from 7:30 to 8:30")
                );
        assertEquals(
                constructProperties("Dinner with Lancelot", "Avalon", "high", "7:30", "8:30"),
                extensionParser.getTaskProperties("Dinner with Lancelot venue   Avalon priority high from 7:30  to  8:30")
                );
    }
    
    /**
     * Construct a Hashmap from the given properties. Empty strings get turned into empty Optionals
     * @param desc Task's description
     * @param venue Task's venue
     * @param priority Task's priority
     * @param startTime Task's start time
     * @param endTime Task's end time
     * @return The Hashmap containing all these properties
     * @throws IllegalValueException
     */
    private HashMap<TaskProperties, Optional<String>> constructProperties(
            String desc, String venue, String priority, String startTime, String endTime
            ) throws IllegalValueException {
        HashMap<TaskProperties, Optional<String>> properties = new HashMap<>();
        properties.put(TaskProperties.DESC, desc.equals("") ? Optional.empty() : Optional.of(desc));
        properties.put(TaskProperties.VENUE, venue.equals("") ? Optional.empty() : Optional.of(venue));
        properties.put(TaskProperties.PRIORITY, priority.equals("") ? Optional.empty() : Optional.of(priority));
        properties.put(TaskProperties.STARTTIME, startTime.equals("") ? Optional.empty() : Optional.of(startTime));
        properties.put(TaskProperties.ENDTIME, endTime.equals("") ? Optional.empty() : Optional.of(endTime));
        properties.put(TaskProperties.DONE, Optional.empty());
        properties.put(TaskProperties.TAG, Optional.empty());
        return properties;
    }
}
