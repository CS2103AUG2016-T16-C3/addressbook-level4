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
	
	private void assertSortSuccess(boolean runSortCommand) {
        if (runSortCommand) {
        	commandBox.runCommand("sort");
		}
        
        Comparator<? super ReadOnlyTask> priorityComparator = (t1, t2) -> t1.compareProperty(t2, TaskProperties.PRIORITY);
        
        assertTrue(isSorted(priorityComparator, taskListPanel.getListView().getItems()));
        
        if (runSortCommand) {
        	assertResultMessage(SortCommand.MESSAGE_SUCCESS);
		}
    }
	
	private boolean isSorted(Comparator<? super ReadOnlyTask> comparator, ObservableList<ReadOnlyTask> listToCheck) {
		for (int i = 1; i < listToCheck.size(); i++) {
			if (comparator.compare(listToCheck.get(i - 1), listToCheck.get(i)) > 0) {
				return false;
			}
		}
		return true;
	}
}
