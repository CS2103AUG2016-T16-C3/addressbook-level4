package guitests;

import org.junit.Test;

import seedu.manager.commons.core.Messages;
import seedu.manager.testutil.TestTask;

import static org.junit.Assert.assertTrue;

//@@author A0139621H
public class FindCommandTest extends TaskManagerGuiTest {

    @Test
    public void find_nonEmptyList() {
        assertFindResult("find Mark"); //no results
        assertFindResult("find CS2101", td.alpha, td.beta); //multiple results

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find CS2101",td.beta);
    }

    @Test
    public void find_emptyList(){
        commandBox.runCommand("clear");
        assertFindResult("find Kilo"); //no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("findgolf");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }
    
    @Test
    public void find_venue() {
        assertFindResult("find venue COM2"); //no results
        assertFindResult("find venue School of Computing", td.alpha, td.beta, td.charlie); //multiple results

        //find after deleting one result
        commandBox.runCommand("delete 2");
        assertFindResult("find venue School of Computing", td.alpha, td.charlie);
    }
    
    @Test
    public void find_startAndEndTime() {
        assertFindResult("find from 8pm to 11pm", td.alpha, td.beta, td.echo, td.foxtrot); //multiple results

        //find after deleting one result
        commandBox.runCommand("delete 3");
        assertFindResult("find from 8pm to 10pm", td.alpha, td.foxtrot);
    }

    private void assertFindResult(String command, TestTask... expectedHits ) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
