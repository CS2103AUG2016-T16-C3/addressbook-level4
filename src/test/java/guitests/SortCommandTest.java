package guitests;

import static org.junit.Assert.assertTrue;

import java.util.Comparator;

import org.junit.Test;

import javafx.collections.ObservableList;
import seedu.manager.logic.commands.SortCommand;
import seedu.manager.logic.commands.SortCommand.SortComparators;
import seedu.manager.model.task.ReadOnlyTask;
import seedu.manager.testutil.TestTask;

// @@author A0147924X
public class SortCommandTest extends TaskManagerGuiTest {
	
	private Comparator<ReadOnlyTask> priorityComparator = (t1, t2) -> {
		int doneCompare = t1.compareDone(t2);
		if (doneCompare != 0) {
			return doneCompare;
		} else {
			return t1.comparePriority(t2);
		}
	};
	
	private Comparator<ReadOnlyTask> timeComparator = (t1, t2) -> {
		int doneCompare = t1.compareDone(t2);
		if (doneCompare != 0) {
			return doneCompare;
		} else {
			return t1.compareTime(t2);
		}
	};
	
	@Test
	public void sortByTime() {
		runSortTest(SortComparators.TIME);
	}
	
	@Test
	public void sortByPriority() {
		runSortTest(SortComparators.PRIORITY);
	}
	
	private void runSortTest(SortComparators comparator) {
		assertSortSuccess(true, comparator);
		
		TestTask taskToAdd = td.hotel;
		commandBox.runCommand(taskToAdd.getAddCommand());
        
        assertSortSuccess(false, comparator);
        
        commandBox.runCommand("find CS2101");
        assertSortSuccess(true, comparator);
	}
	
	/**
	 * Asserts that the sort command worked
	 * @param shouldRunSortCommand Whether the sort command should be run or not
	 */
	private void assertSortSuccess(boolean shouldRunSortCommand, SortComparators comparator) {
        if (shouldRunSortCommand) {
        	commandBox.runCommand(String.format("sortby %1$s", comparator.getValue()));
		}
        
        switch (comparator) {
		case PRIORITY:
			assertTrue(isSorted(priorityComparator, taskListPanel.getListView().getItems()));
			break;

		case TIME:
			assertTrue(isSorted(timeComparator, taskListPanel.getListView().getItems()));
			break;
			
		default:
			assert false;
			break;
		}
        
        if (shouldRunSortCommand) {
        	assertResultMessage(String.format(SortCommand.MESSAGE_SUCCESS, comparator.getValue()));
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
