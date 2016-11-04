package guitests;

import org.junit.Test;

import seedu.manager.logic.commands.AddCommand;
import seedu.manager.testutil.TestTask;
import seedu.manager.testutil.TestUtil;

import static org.junit.Assert.assertTrue;

public class UndoCommandTest  extends TaskManagerGuiTest  {

	@Test
	public void undo() {
		// Test undo add command.
		TestTask[] currentList = td.getTypicalTasks();
		TestUtil.sortListByTime(currentList);
		
		commandBox.runCommand("add abc");
		assertUndoSuccess(currentList);
		
		// Test undo delete command.
		int targetIndex = 1;
		commandBox.runCommand(String.format("delete %1$s", targetIndex));
		TestTask toDelete = currentList[targetIndex - 1];
		currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
		currentList = TestUtil.addTasksToListSortedByTime(currentList, toDelete);
		assertUndoSuccess(currentList);
		
		// Test undo edit command.
		targetIndex = 2;
		commandBox.runCommand(String.format("edit %1$s venue Utown", targetIndex));
		TestTask toEdit = currentList[targetIndex - 1];
		currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
		currentList = TestUtil.addTasksToListSortedByTime(currentList, toEdit);
		assertUndoSuccess(currentList);
		
		// Test undo done command.
		targetIndex = 1;
		commandBox.runCommand(String.format("done %1$s", targetIndex));
		TestTask toDone = currentList[targetIndex - 1];
		currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
		currentList = TestUtil.addTasksToListSortedByTime(currentList, toDone);
		assertUndoSuccess(currentList);
		
		// Test undo sort command.
		commandBox.runCommand("sort");
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
