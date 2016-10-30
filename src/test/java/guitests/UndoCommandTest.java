package guitests;

import org.junit.Test;
import seedu.manager.testutil.TestTask;
import seedu.manager.testutil.TestUtil;

import static org.junit.Assert.assertTrue;

public class UndoCommandTest  extends TaskManagerGuiTest  {

	@Test
	public void test() {
		// Test undo add command.
		TestTask[] currentList = td.getTypicalTasks();
		commandBox.runCommand("add abc");
		
		assertUndoSuccess(currentList);
		
		// Test undo delete command.
		commandBox.runCommand("delete 1");
		
		TestTask toDelete = currentList[0];
		
		currentList = TestUtil.removeTaskFromList(currentList, 1);
		currentList = TestUtil.addTasksToList(currentList, toDelete);
		
		assertUndoSuccess(currentList);
		
		// Test undo edit command.
		commandBox.runCommand("edit 1 venue Utown");
		
		TestTask toEdit = currentList[0];
		
		currentList = TestUtil.removeTaskFromList(currentList, 1);
		currentList = TestUtil.addTasksToList(currentList, toEdit);
		
		assertUndoSuccess(currentList);
		
		// Test undo done command.
		commandBox.runCommand("done 1");
		
		TestTask toDone = currentList[0];
		
		currentList = TestUtil.removeTaskFromList(currentList, 1);
		currentList = TestUtil.addTasksToList(currentList, toDone);
		
		assertUndoSuccess(currentList);
		
		// Test undo sort command.
		commandBox.runCommand("sort");
		
		assertUndoSuccess(currentList);
	}
	
	private void assertUndoSuccess(TestTask[] expectedList) {
	    commandBox.runCommand("undo");
	    
	    assertTrue(taskListPanel.isListMatching(expectedList));
	    }

}
