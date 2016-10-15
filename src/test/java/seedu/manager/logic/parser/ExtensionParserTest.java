package seedu.manager.logic.parser;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import seedu.manager.commons.exceptions.IllegalValueException;
import seedu.manager.model.task.Task.TaskProperties;

public class ExtensionParserTest {
    private ExtensionParser extensionParser;
    @Before
    public void init_ext_parser() {
        extensionParser = new ExtensionParser();
    }
    
    @Test
    public void parse_invalid_duplicate_error() {
        try {
            extensionParser.getTaskProperties("Dinner with Lancelot at Avalon at Round Table");
            fail("Didn't throw exception");
        } catch (IllegalValueException e) {
            assertEquals(e.getMessage(), String.format(ExtensionParser.EXTENSION_DUPLICATES, ExtensionParser.ExtensionCmds.VENUE.getValue()));
        }
        
        try {
            extensionParser.getTaskProperties("Dinner with Lancelot venue Avalon priority high priority low");
            fail("Didn't throw exception");
        } catch (IllegalValueException e) {
            assertEquals(e.getMessage(), String.format(ExtensionParser.EXTENSION_DUPLICATES, ExtensionParser.ExtensionCmds.PRIORITY.getValue()));
        }
        
        try {
            extensionParser.getTaskProperties("Dinner with Lancelot priority high after 8:30 after 9:00");
            fail("Didn't throw exception");
        } catch (IllegalValueException e) {
            assertEquals(e.getMessage(), String.format(ExtensionParser.EXTENSION_DUPLICATES, ExtensionParser.ExtensionCmds.AFTER.getValue()));
        }
    }
    
    @Test
    public void parse_invalid_from_to_format_error() {
        try {
            extensionParser.getTaskProperties("Dinner with Lancelot from 8:30");
            fail("Didn't throw exception");
        } catch (IllegalValueException e) {
            assertEquals(e.getMessage(), ExtensionParser.EXTENSION_FROM_TO_INVALID_FORMAT);
        }
        try {
            extensionParser.getTaskProperties("Dinner with Lancelot from 8:30to 9:30");
            fail("Didn't throw exception");
        } catch (IllegalValueException e) {
            assertEquals(e.getMessage(), ExtensionParser.EXTENSION_FROM_TO_INVALID_FORMAT);
        }
        try {
            extensionParser.getTaskProperties("Dinner with Lancelot from 8:30 to9:30");
            fail("Didn't throw exception");
        } catch (IllegalValueException e) {
            assertEquals(e.getMessage(), ExtensionParser.EXTENSION_FROM_TO_INVALID_FORMAT);
        }
    }
    
    @Test
    public void parse_all_ext_successful() throws IllegalValueException {
        assertEquals(
                constructProperties("Dinner with Lancelot", "Avalon", "high", "7:30", "8:30"),
                extensionParser.getTaskProperties("Dinner with Lancelot at Avalon priority high before 8:30 after 7:30")
                );
    }
    
    @Test 
    public void parse_ext_successful() throws IllegalValueException {
        assertEquals(
                constructProperties("Dinner with Arthur", "Round Table", "high", "", ""),
                extensionParser.getTaskProperties("Dinner with Arthur at Round Table priority high")
                );
        assertEquals(
                constructProperties("Dinner with Arthur", "", "low", "7:30pm", ""),
                extensionParser.getTaskProperties("Dinner with Arthur priority low after 7:30pm")
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
    public void parse_only_desc_successful() throws IllegalValueException {
        assertEquals(
                constructProperties("Dinner with Lancelot", "", "", "", ""),
                extensionParser.getTaskProperties("Dinner with Lancelot")
                );
    }
    
    @Test
    public void parse_no_desc_successful() throws IllegalValueException {
        assertEquals(
                constructProperties("", "Avalon", "high", "7:30", "8:30"),
                extensionParser.getTaskProperties("at Avalon priority high from 7:30 to 8:30")
                );
    }
    
    @Test
    public void parse_extra_spacing_successful() throws IllegalValueException {
        assertEquals(
                constructProperties("Dinner with Lancelot", "Avalon", "high", "7:30", "8:30"),
                extensionParser.getTaskProperties("  Dinner with Lancelot at Avalon priority high before 8:30 after 7:30")
                );
        assertEquals(
                constructProperties("Dinner with Lancelot", "Avalon", "high", "7:30", "8:30"),
                extensionParser.getTaskProperties("Dinner with Lancelot   at Avalon priority high from 7:30 to 8:30")
                );
        assertEquals(
                constructProperties("Dinner with Lancelot", "Avalon", "high", "7:30", "8:30"),
                extensionParser.getTaskProperties("Dinner with Lancelot at Avalon priority high from 7:30 to 8:30  ")
                );
        assertEquals(
                constructProperties("Dinner with Lancelot", "Avalon", "high", "7:30", "8:30"),
                extensionParser.getTaskProperties("Dinner with Lancelot at Avalon   priority high before 8:30 after 7:30")
                );
        assertEquals(
                constructProperties("Dinner with Lancelot", "Avalon", "high", "7:30", "8:30"),
                extensionParser.getTaskProperties("Dinner with Lancelot at   Avalon priority high from 7:30  to  8:30")
                );
    }
    
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
        return properties;
    }
}
