package guitests;

import guitests.guihandles.TaskCardHandle;
import org.junit.Test;

import seedu.manager.commons.core.Messages;
import seedu.manager.logic.commands.AddCommand;
import seedu.manager.model.task.ReadOnlyTask;
import seedu.manager.model.task.Task.TaskProperties;
import seedu.manager.testutil.TestTask;
import seedu.manager.testutil.TestUtil;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Comparator;

// @@author A0147924X
public class AddCommandTest extends TaskManagerGuiTest {

    @Test
    public void add() {
        //add one task
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = td.hotel;
        assertAddSuccess(3, taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(3, currentList, taskToAdd);

        //add another task
        taskToAdd = td.india;
        assertAddSuccess(3, taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(3, currentList, taskToAdd);

        //add duplicate task
        commandBox.runCommand(td.hotel.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(taskListPanel.isListMatching(currentList));

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(0, td.alpha);

        //invalid command
        commandBox.runCommand("helps Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertAddSuccess(int indexToInsert, TestTask taskToAdd, TestTask... currentList) {
        commandBox.runCommand(taskToAdd.getAddCommand());

        //confirm the new card contains the right data
        TaskCardHandle addedCard = taskListPanel.navigateToTask(taskToAdd.getDesc().get().getValue());
        assertMatching(taskToAdd, addedCard);

        //confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.addTasksToList(indexToInsert, currentList, taskToAdd);
        
        assertTrue(taskListPanel.isListMatching(expectedList));
    }

}
