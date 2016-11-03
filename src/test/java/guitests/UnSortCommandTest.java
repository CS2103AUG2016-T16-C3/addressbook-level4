package guitests;

import static org.junit.Assert.assertTrue;

import java.util.Comparator;

import org.junit.Test;

import javafx.collections.ObservableList;
import seedu.manager.logic.commands.UnSortCommand;
import seedu.manager.model.task.ReadOnlyTask;
import seedu.manager.model.task.Task.TaskProperties;
import seedu.manager.testutil.TestTask;

//@@author A0147924X
public class UnSortCommandTest extends TaskManagerGuiTest {
	
	@Test
	public void unsort() {
		assertUnSortSuccess(false);
		
		TestTask taskToAdd = td.hotel;
		commandBox.runCommand(taskToAdd.getAddCommand());
        
        assertUnSortSuccess(false);
        
        commandBox.runCommand("done 1");
        assertUnSortSuccess(false);
        
        commandBox.runCommand("sort");
        assertUnSortSuccess(true);
	}
	
	private void assertUnSortSuccess(boolean shouldRunSortCommand) {
        if (shouldRunSortCommand) {
        	commandBox.runCommand("unsort");
		}
        
        Comparator<? super ReadOnlyTask> doneComparator = (t1, t2) -> t1.compareProperty(t2, TaskProperties.DONE);
        
        assertTrue(isSorted(doneComparator, taskListPanel.getListView().getItems()));
        
        if (shouldRunSortCommand) {
        	assertResultMessage(UnSortCommand.MESSAGE_SUCCESS);
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
