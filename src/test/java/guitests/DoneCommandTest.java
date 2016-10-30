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
        TestTask taskToMark = td.charlie;
        TestTask markedTask = markAsDone(taskToMark);
        assertDoneSuccess("done %1$s", 3, markedTask, currentList);
        
        // propagate changes to current list
        currentList = TestUtil.addTasksToList(TestUtil.removeTaskFromList(currentList, 3), markedTask);
        
        // mark another as done
        TestTask taskToMark1 = td.alpha;
        TestTask markedTask1 = markAsDone(taskToMark1);
        assertDoneSuccess("done %1$s", 1, markedTask1, currentList);
        
        // invalid index
        commandBox.runCommand("done " + (currentList.length + 1));
        assertResultMessage("The task index provided is invalid");
    }
    
    private TestTask markAsDone(TestTask taskToMark) throws IllegalValueException {
    	HashMap<TaskProperties, Optional<TaskProperty>> newProps = 
                taskToMark.getProperties();
        
        newProps.put(TaskProperties.DONE, Optional.of(new Done("Yes")));
        return new TestTask(newProps);
    }
    
    private void assertDoneSuccess(String doneCommand, int index, TestTask markedTask, TestTask... currentList) {
        commandBox.runCommand(String.format(doneCommand, index));
        
        TaskCardHandle addedCard = taskListPanel.navigateToTask(markedTask.getDesc().get().getValue());
        assertMatching(markedTask, addedCard);
        
        TestTask[] expectedList = TestUtil.addTasksToList(TestUtil.removeTaskFromList(currentList, index), markedTask);
        assertTrue(taskListPanel.isListMatching(expectedList));
        
        assertResultMessage(String.format(DoneCommand.MESSAGE_SUCCESS, markedTask));
    }
}
