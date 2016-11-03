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

// @@author A0147924X
public class EditCommandTest extends TaskManagerGuiTest {
    @Test
    public void edit() throws IllegalValueException {
        TestTask[] currentList = td.getTypicalTasks();
        
        TestTask taskToEdit = td.beta;
        TestTask editedTask = editTaskWithProperty(taskToEdit, TaskProperties.DESC, new Desc("Dinner with Guinevere"));
        assertEditSuccess("edit %1$s Dinner with Guinevere", 2, 2, editedTask, currentList);
        
        currentList = TestUtil.addTasksToList(2, TestUtil.removeTaskFromList(currentList, 2), editedTask);
        
        TestTask editedTask1 = editTaskWithProperty(editedTask, TaskProperties.VENUE, new Venue("Avalon"));
        assertEditSuccess("edit %1$s venue Avalon", 3, 2, editedTask1, currentList);
        
        // invalid index
        commandBox.runCommand("edit " + (currentList.length + 1) + " Some Description");
        assertResultMessage("The task index provided is invalid");
    }
    
    private TestTask editTaskWithProperty(TestTask taskToEdit, TaskProperties property, TaskProperty value) {
    	HashMap<TaskProperties, Optional<TaskProperty>> newProps = 
                taskToEdit.getProperties();
        
        newProps.put(property, Optional.of(value));
        
        return new TestTask(newProps);
    }
    
    // @@author
    private void assertEditSuccess(String editCommand, int index, int indexToInsert,
    							   TestTask editedTask, TestTask... currentList) {
        commandBox.runCommand(String.format(editCommand, index));
        
        TaskCardHandle editedCard = taskListPanel.navigateToTask(editedTask.getDesc().get().getValue());
        assertMatching(editedTask, editedCard);
        
        TestTask[] expectedList = TestUtil.addTasksToList(indexToInsert, TestUtil.removeTaskFromList(currentList, index), editedTask);
        assertTrue(taskListPanel.isListMatching(expectedList));
        
        assertResultMessage(String.format(EditCommand.MESSAGE_SUCCESS, editedTask.getAsPrettyText()));
    }
}
