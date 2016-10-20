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

public class DoneCommandTest extends TaskManagerGuiTest {

    @Test
    public void done() throws IllegalValueException {
    	TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToMark = td.charlie;
        HashMap<TaskProperties, Optional<TaskProperty>> newProps = 
                taskToMark.getProperties();
        
        // mark as done
        newProps.put(TaskProperties.DONE, Optional.of(new Done("Yes")));
        TestTask markedTask = new TestTask(newProps);
        assertDoneSuccess("done %1$s", 3, markedTask, currentList);
        
        // propagate changes to current list
        currentList = TestUtil.addTasksToList(TestUtil.removeTaskFromList(currentList, 3), markedTask);
        
        // mark another as done
        TestTask taskToMark1 = td.alpha;
        HashMap<TaskProperties, Optional<TaskProperty>> newProps1 = 
                taskToMark1.getProperties();
        
        newProps1.put(TaskProperties.DONE, Optional.of(new Done("Yes")));
        TestTask markedTask1 = new TestTask(newProps1);
        assertDoneSuccess("done %1$s", 1, markedTask1, currentList);
        
        // invalid index
        commandBox.runCommand("done " + (currentList.length + 1));
        assertResultMessage("The task index provided is invalid");
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
