package guitests;

import org.junit.Test;

import seedu.manager.testutil.TestTask;
import seedu.manager.testutil.TestUtil;

import static org.junit.Assert.assertTrue;

public class ClearCommandTest extends TaskManagerGuiTest {

    @Test
    public void clear() {

        //verify a non-empty list can be cleared
    	TestTask[] currentList = td.getTypicalTasks();
    	TestUtil.sortListByTime(currentList);
        assertTrue(taskListPanel.isListMatching(currentList));
        assertClearCommandSuccess();

        //verify other commands can work after a clear command
        commandBox.runCommand(td.hotel.getAddCommand());
        assertTrue(taskListPanel.isListMatching(td.hotel));
        commandBox.runCommand("delete 1");
        assertListSize(0);

        //verify clear command works when the list is empty
        assertClearCommandSuccess();
    }

    private void assertClearCommandSuccess() {
        commandBox.runCommand("clear");
        assertListSize(0);
        assertResultMessage("Task manager has been cleared!");
    }
}
