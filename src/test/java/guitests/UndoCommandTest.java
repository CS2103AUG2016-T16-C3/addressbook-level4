package guitests;

import org.junit.Test;

import seedu.manager.logic.commands.AddCommand;
import seedu.manager.model.task.Task.TaskProperties;
import seedu.manager.testutil.TestTask;
import seedu.manager.testutil.TestUtil;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

// @@author A0148003U

public class UndoCommandTest  extends TaskManagerGuiTest  {

	@Test
	public void undo() {
		// Test undo add command.
		TestTask[] currentList = td.getTypicalTasks();
		commandBox.runCommand("add abc");
		assertUndoSuccess(currentList);
		
		// Test undo delete command.
		commandBox.runCommand("delete 1");
		TestTask toDelete = currentList[0];
		currentList = TestUtil.removeTaskFromList(currentList, 1);
		currentList = TestUtil.addTasksToList(2, currentList, toDelete);
		assertUndoSuccess(currentList);
		
		// Test undo edit command.
		commandBox.runCommand("edit 1 venue Utown");
		TestTask toEdit = currentList[0];
		currentList = TestUtil.removeTaskFromList(currentList, 1);
		currentList = TestUtil.addTasksToList(2, currentList, toEdit);
		assertUndoSuccess(currentList);
		
		// Test undo done command.
		commandBox.runCommand("done 1");
		TestTask toDone = currentList[0];
		currentList = TestUtil.removeTaskFromList(currentList, 1);
		currentList = TestUtil.addTasksToList(2, currentList, toDone);
		assertUndoSuccess(currentList);
		
		// Test undo sort command.
		commandBox.runCommand("sort");
		Arrays.sort(currentList, (t1, t2) -> t1.compareProperty(t2, TaskProperties.PRIORITY));
		assertUndoSuccess(currentList);
		
		// Test undo alias command
		commandBox.runCommand("alias add +");
		commandBox.runCommand("undo");
		
		TestTask taskToAdd = td.juliet;
		commandBox.runCommand(taskToAdd.getAddCommand());
		assertResultMessage(String.format(AddCommand.MESSAGE_SUCCESS, taskToAdd.getAsPrettyText()));
	}
	
	private void assertUndoSuccess(TestTask[] expectedList) {
	    commandBox.runCommand("undo");    
	    assertTrue(taskListPanel.isListMatching(expectedList));
    }

}
