package guitests;

import guitests.guihandles.TaskCardHandle;
import org.junit.Test;

import seedu.manager.commons.exceptions.IllegalValueException;
import seedu.manager.logic.commands.EditCommand;
import seedu.manager.model.task.Desc;
import seedu.manager.model.task.TaskProperty;
import seedu.manager.model.task.Venue;
import seedu.manager.model.task.Task.TaskProperties;
import seedu.manager.testutil.TestTask;
import seedu.manager.testutil.TestUtil;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Optional;

public class EditCommandTest extends TaskManagerGuiTest {
    @Test
    public void edit() throws IllegalValueException {
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToEdit = td.beta;
        HashMap<TaskProperties, Optional<TaskProperty>> newProps = 
                taskToEdit.getProperties();
        
        // edit desc
        newProps.put(TaskProperties.DESC, Optional.of(new Desc("Dinner with Guinevere")));
        
        TestTask editedTask = new TestTask(newProps);
        
        assertEditSuccess("edit %1$s Dinner with Guinevere", 2, editedTask, currentList);
        
        // edit venue
        currentList = TestUtil.addTasksToList(TestUtil.removeTaskFromList(currentList, 2), editedTask);
        newProps.put(TaskProperties.VENUE, Optional.of(new Venue("Avalon")));
        
        assertEditSuccess("edit %1$s venue Avalon", 7, new TestTask(newProps), currentList);
        
        // invalid index
        commandBox.runCommand("edit " + (currentList.length + 1) + " Some Description");
        assertResultMessage("The task index provided is invalid");
    }
    
    private void assertEditSuccess(String editCommand, int index, TestTask editedTask, TestTask... currentList) {
        commandBox.runCommand(String.format(editCommand, index));
        
        TaskCardHandle addedCard = taskListPanel.navigateToTask(editedTask.getDesc().get().getValue());
        assertMatching(editedTask, addedCard);
        
        TestTask[] expectedList = TestUtil.addTasksToList(TestUtil.removeTaskFromList(currentList, index), editedTask);
        assertTrue(taskListPanel.isListMatching(expectedList));
        
        assertResultMessage(String.format(EditCommand.MESSAGE_SUCCESS, editedTask));
    }
}
