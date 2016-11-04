package guitests;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.manager.commons.exceptions.IllegalValueException;
import seedu.manager.logic.commands.DoneCommand;
import seedu.manager.model.task.Done;
import seedu.manager.model.task.TaskProperty;
import seedu.manager.model.task.Task.TaskProperties;
import seedu.manager.testutil.TestTask;
import seedu.manager.testutil.TestUtil;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Optional;

// @@author A0147924X
public class DoneCommandTest extends TaskManagerGuiTest {

    @Test
    public void done() throws IllegalValueException {
    	TestTask[] currentList = td.getTypicalTasks();
    	TestUtil.sortListByTime(currentList);
    	
        currentList = assertDoneSuccess(3, currentList);
        currentList = assertDoneSuccess(1, currentList);
        
        // invalid index
        commandBox.runCommand("done " + (currentList.length + 1));
        assertResultMessage("The task index provided is invalid");
    }
    
    /**
     * Marks a task as done
     * @param taskToMark The task which should be marked
     * @return Marked task
     * @throws IllegalValueException
     */
    private TestTask markAsDone(TestTask taskToMark) throws IllegalValueException {
    	HashMap<TaskProperties, Optional<TaskProperty>> newProps = 
                taskToMark.getProperties();
        
        newProps.put(TaskProperties.DONE, Optional.of(new Done("Yes")));
        return new TestTask(newProps);
    }
    
    /**
     * Asserts that the done command worked
     * @param index Index to mark as done
     * @param indexToInsert Index at which to insert the marked task in the current list
     * @param markedTask The marked task which will be inserted into the current list
     * @param currentList Current task list to check panel list against
     * @return The new list of tasks 
     * @throws IllegalValueException 
     */
    private TestTask[] assertDoneSuccess(int indexToMark, TestTask... currentList) throws IllegalValueException {
    	TestTask taskToMark = currentList[indexToMark - 1];
        TestTask markedTask = markAsDone(taskToMark);
    	
        commandBox.runCommand(String.format("done %1$s", indexToMark));
        
        TaskCardHandle addedCard = taskListPanel.navigateToTask(markedTask.getDesc().get().getValue());
        assertMatching(markedTask, addedCard);
        
        TestTask[] expectedList = TestUtil.addTasksToListSortedByTime(TestUtil.removeTaskFromList(currentList, indexToMark), markedTask);

        assertTrue(taskListPanel.isListMatching(expectedList));
        
        assertResultMessage(String.format(DoneCommand.MESSAGE_SUCCESS, markedTask));
        
        return expectedList;
    }
}
