package guitests;

import org.junit.Test;

import seedu.manager.commons.core.Messages;
import seedu.manager.testutil.TestTask;

import static org.junit.Assert.assertTrue;

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

    private void assertFindResult(String command, TestTask... expectedHits ) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
