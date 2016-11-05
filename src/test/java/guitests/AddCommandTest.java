package guitests;

import guitests.guihandles.TaskCardHandle;
import org.junit.Test;

import seedu.manager.commons.core.Messages;
import seedu.manager.logic.commands.AddCommand;
import seedu.manager.testutil.TestTask;
import seedu.manager.testutil.TestUtil;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

// @@author A0147924X
public class AddCommandTest extends TaskManagerGuiTest {

    @Test
    public void add() {
        //add one task
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = td.hotel;
        currentList = assertAddSuccess(taskToAdd, currentList);

        //add another task
        taskToAdd = td.india;
        currentList = assertAddSuccess(taskToAdd, currentList);

        //add duplicate task
        commandBox.runCommand(td.hotel.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(taskListPanel.isListMatching(currentList));

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(td.alpha);

        //invalid command
        commandBox.runCommand("helps Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }
    
    /**
     * Asserts that the add command worked
     * @param indexToInsert Index at which new task should be inserted into the current list
     * @param taskToAdd The task which should be added to the current list
     * @param currentList Current task list to check panel list against
     * @return The new list of tasks
     */
    private TestTask[] assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
        commandBox.runCommand(taskToAdd.getAddCommand());

        //confirm the new card contains the right data
        TaskCardHandle addedCard = taskListPanel.navigateToTask(taskToAdd.getDesc().get().getValue());
        assertMatching(taskToAdd, addedCard);

        //confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.addTasksToListSortedByTime(currentList, taskToAdd);
        
        assertTrue(taskListPanel.isListMatching(expectedList));
        
        return expectedList;
    }
}
