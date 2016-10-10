package seedu.manager.logic.parser;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import seedu.manager.commons.exceptions.IllegalValueException;
import seedu.manager.model.task.Task.TaskProperties;
import seedu.manager.model.task.Desc;
import seedu.manager.model.task.EndTime;
import seedu.manager.model.task.Priority;
import seedu.manager.model.task.StartTime;
import seedu.manager.model.task.TaskProperty;
import seedu.manager.model.task.Venue;

public class ExtensionParserTest {
    private ExtensionParser extensionParser;
    @Before
    public void init_ext_parser() {
        extensionParser = new ExtensionParser();
    }
    
    @Test
    public void parse_invalid_duplicate_error() {
        try {
            extensionParser.getTaskProperties("Dinner with Lancelot venue Avalon venue Round Table");
            fail("Didn't throw correct exception");
        } catch (IllegalValueException e) {
            assertEquals(e.getMessage(), String.format(ExtensionParser.EXTENSION_DUPLICATES, ExtensionParser.ExtensionCmds.VENUE.getValue()));
        }
        
        try {
            extensionParser.getTaskProperties("Dinner with Lancelot venue Avalon priority high priority low");
            fail("Didn't throw correct exception");
        } catch (IllegalValueException e) {
            assertEquals(e.getMessage(), String.format(ExtensionParser.EXTENSION_DUPLICATES, ExtensionParser.ExtensionCmds.PRIORITY.getValue()));
        }
        
        try {
            extensionParser.getTaskProperties("Dinner with Lancelot priority high after 8:30 after 9:00");
            fail("Didn't throw correct exception");
        } catch (IllegalValueException e) {
            assertEquals(e.getMessage(), String.format(ExtensionParser.EXTENSION_DUPLICATES, ExtensionParser.ExtensionCmds.AFTER.getValue()));
        }
    }
    
    @Test
    public void parse_all_ext_successful() throws IllegalValueException {
        assertEquals(
                constructProperties("Dinner with Lancelot", "Avalon", "high", "7:30", "8:30"),
                extensionParser.getTaskProperties("Dinner with Lancelot venue Avalon priority high before 8:30 after 7:30")
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
                extensionParser.getTaskProperties("venue Avalon priority high before 8:30 after 7:30")
                );
    }
    
    @Test
    public void parse_extra_spacing_successful() throws IllegalValueException {
        assertEquals(
                constructProperties("Dinner with Lancelot", "Avalon", "high", "7:30", "8:30"),
                extensionParser.getTaskProperties("  Dinner with Lancelot venue Avalon priority high before 8:30 after 7:30")
                );
        assertEquals(
                constructProperties("Dinner with Lancelot", "Avalon", "high", "7:30", "8:30"),
                extensionParser.getTaskProperties("Dinner with Lancelot   venue Avalon priority high before 8:30 after 7:30")
                );
        assertEquals(
                constructProperties("Dinner with Lancelot", "Avalon", "high", "7:30", "8:30"),
                extensionParser.getTaskProperties("Dinner with Lancelot venue Avalon priority high before 8:30 after 7:30  ")
                );
        assertEquals(
                constructProperties("Dinner with Lancelot", "Avalon", "high", "7:30", "8:30"),
                extensionParser.getTaskProperties("Dinner with Lancelot venue Avalon   priority high before 8:30 after 7:30")
                );
        assertEquals(
                constructProperties("Dinner with Lancelot", "Avalon", "high", "7:30", "8:30"),
                extensionParser.getTaskProperties("Dinner with Lancelot venue   Avalon priority high before 8:30 after 7:30")
                );
    }
    
    private HashMap<TaskProperties, Optional<TaskProperty>> constructProperties(
            String desc, String venue, String priority, String startTime, String endTime
            ) throws IllegalValueException {
        HashMap<TaskProperties, Optional<TaskProperty>> properties = new HashMap<>();
        properties.put(TaskProperties.DESC, desc.equals("") ? Optional.empty() : Optional.of(new Desc(desc)));
        properties.put(TaskProperties.VENUE, venue.equals("") ? Optional.empty() : Optional.of(new Venue(venue)));
        properties.put(TaskProperties.PRIORITY, priority.equals("") ? Optional.empty() : Optional.of(new Priority(priority)));
        properties.put(TaskProperties.STARTTIME, startTime.equals("") ? Optional.empty() : Optional.of(new StartTime(startTime)));
        properties.put(TaskProperties.ENDTIME, endTime.equals("") ? Optional.empty() : Optional.of(new EndTime(endTime)));
        return properties;
    }
}
