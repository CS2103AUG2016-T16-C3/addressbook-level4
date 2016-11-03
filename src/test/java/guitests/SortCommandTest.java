package guitests;

import static org.junit.Assert.assertTrue;

import java.util.Comparator;

import org.junit.Test;

import javafx.collections.ObservableList;
import seedu.manager.logic.commands.SortCommand;
import seedu.manager.model.task.ReadOnlyTask;
import seedu.manager.model.task.Task.TaskProperties;
import seedu.manager.testutil.TestTask;

// @@author A0147924X
public class SortCommandTest extends TaskManagerGuiTest {
	
	@Test
	public void sort() {
		assertSortSuccess(true);
		
		TestTask taskToAdd = td.hotel;
		commandBox.runCommand(taskToAdd.getAddCommand());
        
        assertSortSuccess(false);
        
        commandBox.runCommand("find CS2101");
        assertSortSuccess(true);
	}
	
	/**
	 * Asserts that the sort command worked
	 * @param shouldRunSortCommand Whether the sort command should be run or not
	 */
	private void assertSortSuccess(boolean shouldRunSortCommand) {
        if (shouldRunSortCommand) {
        	commandBox.runCommand("sort");
		}
        
        Comparator<? super ReadOnlyTask> priorityComparator = (t1, t2) -> t1.compareProperty(t2, TaskProperties.PRIORITY);
        
        assertTrue(isSorted(priorityComparator, taskListPanel.getListView().getItems()));
        
        if (shouldRunSortCommand) {
        	assertResultMessage(SortCommand.MESSAGE_SUCCESS);
		}
    }
	
	/**
	 * Uses a comparator to check whether a given list is sorted or not
	 * @param comparator The comparator which decides ordering of the tasks
	 * @param listToCheck The list which will be checked
	 * @return True if list is sorted, else False
	 */
	private boolean isSorted(Comparator<? super ReadOnlyTask> comparator, ObservableList<ReadOnlyTask> listToCheck) {
		for (int i = 1; i < listToCheck.size(); i++) {
			if (comparator.compare(listToCheck.get(i - 1), listToCheck.get(i)) > 0) {
				return false;
			}
		}
		return true;
	}
}
