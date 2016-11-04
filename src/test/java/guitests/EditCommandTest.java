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
        TestUtil.sortListByTime(currentList);
        
        int indexToEdit = 2;
        TestTask taskToEdit = currentList[indexToEdit - 1];
        TestTask editedTask = editTaskWithProperty(taskToEdit, TaskProperties.DESC, new Desc("Dinner with Guinevere"));
        currentList = assertEditSuccess("edit %1$s Dinner with Guinevere", indexToEdit, editedTask, currentList);
        
        indexToEdit = 3;
        taskToEdit = currentList[indexToEdit - 1];
        TestTask editedTask1 = editTaskWithProperty(taskToEdit, TaskProperties.VENUE, new Venue("Avalon"));
        currentList = assertEditSuccess("edit %1$s venue Avalon", indexToEdit, editedTask1, currentList);
        
        // invalid index
        commandBox.runCommand("edit " + (currentList.length + 1) + " Some Description");
        assertResultMessage("The task index provided is invalid");
    }
    
    /**
     * Edits a task to change a certain property
     * @param taskToEdit The task which should be edited
     * @param property The property of the task to edit
     * @param value The new value for this property
     * @return Edited task
     */
    private TestTask editTaskWithProperty(TestTask taskToEdit, TaskProperties property, TaskProperty value) {
    	HashMap<TaskProperties, Optional<TaskProperty>> newProps = 
                taskToEdit.getProperties();
        
        newProps.put(property, Optional.of(value));
        
        return new TestTask(newProps);
    }
    
    /**
     * Asserts that the edit command worked
     * @param editCommand A string representing the command to run
     * @param index The index of the task to be edited
     * @param indexToInsert Index at which edited task should be inserted into the current list
     * @param editedTask The edited task Current task list to check panel list against
     * @param currentList
     * @return The new list of tasks
     */
    private TestTask[] assertEditSuccess(String editCommand, int index,
    							   TestTask editedTask, TestTask... currentList) {
        commandBox.runCommand(String.format(editCommand, index));
        
        TaskCardHandle editedCard = taskListPanel.navigateToTask(editedTask.getDesc().get().getValue());
        assertMatching(editedTask, editedCard);
        
        TestTask[] expectedList = TestUtil.removeTaskFromList(currentList, index);
        expectedList = TestUtil.addTasksToListSortedByTime(expectedList, editedTask);
        assertTrue(taskListPanel.isListMatching(expectedList));
        
        assertResultMessage(String.format(EditCommand.MESSAGE_SUCCESS, editedTask.getAsPrettyText()));
        
        return expectedList;
    }
}
