package guitests;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import javafx.collections.ObservableList;
import seedu.manager.logic.commands.SortCommand;
import seedu.manager.model.task.ReadOnlyTask;
import seedu.manager.model.task.Task.TaskProperties;
import seedu.manager.testutil.TestTask;

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
