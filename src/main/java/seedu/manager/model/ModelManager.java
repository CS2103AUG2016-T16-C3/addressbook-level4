package seedu.manager.model;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.manager.commons.core.ComponentManager;
import seedu.manager.commons.core.LogsCenter;
import seedu.manager.commons.core.UnmodifiableObservableList;
import seedu.manager.commons.core.CommandWord.Commands;
import seedu.manager.commons.events.model.TaskManagerChangedEvent;
import seedu.manager.commons.exceptions.IllegalValueException;
import seedu.manager.commons.util.StringUtil;
import seedu.manager.model.task.ReadOnlyTask;
import seedu.manager.model.task.Task;
import seedu.manager.model.task.Task.TaskProperties;
import seedu.manager.model.task.TaskProperty;
import seedu.manager.model.task.UniqueTaskList;
import seedu.manager.model.task.UniqueTaskList.TaskNotFoundException;

import java.util.HashMap;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Represents the in-memory model of the task manager data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskManager taskManager;
    private final FilteredList<Task> filteredTasks;
    private final SortedList<Task> sortedTasks;
    private final UserPrefs userPrefs;

    /**
     * Initializes a ModelManager with the given TaskManager
     * TaskManager and its variables should not be null
     */
    public ModelManager(TaskManager src, UserPrefs userPrefs) {
        super();
        assert src != null;
        assert userPrefs != null;

        logger.fine("Initializing with task manager: " + src + " and user prefs " + userPrefs);

        taskManager = new TaskManager(src);
        filteredTasks = new FilteredList<>(taskManager.getTasks());
        sortedTasks = new SortedList<>(filteredTasks);
        this.userPrefs = userPrefs;
    }

    public ModelManager() {
        this(new TaskManager(), new UserPrefs());
    }

    public ModelManager(ReadOnlyTaskManager initialData, UserPrefs userPrefs) {
        taskManager = new TaskManager(initialData);
        filteredTasks = new FilteredList<>(taskManager.getTasks());
        sortedTasks = new SortedList<>(filteredTasks);
        this.userPrefs = userPrefs;
    }

    @Override
    public void resetData(ReadOnlyTaskManager newData) {
        taskManager.resetData(newData);
        indicateTaskManagerChanged();
    }

    @Override
    public ReadOnlyTaskManager getTaskManager() {
        return taskManager;
    }
    
    @Override
    public HashMap<Commands, String> getCommandWords() {
    	return userPrefs.commandWords;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateTaskManagerChanged() {
        raise(new TaskManagerChangedEvent(taskManager));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        taskManager.removeTask(target);
        indicateTaskManagerChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        taskManager.addTask(task);
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();
    }
    
	@Override
	public void setSingleCommandWord(String commandToChange, String alias,
			String messageNoMatch, String messageAliasAlreadyTaken) throws IllegalValueException {
		userPrefs.setSingleCommandWord(commandToChange, alias, messageNoMatch, messageAliasAlreadyTaken);
		
	}
    
    //=========== Sorted and Filtered Task List Accessors ===============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getSortedFilteredTaskList() {
        return new UnmodifiableObservableList<>(sortedTasks);
    }
    
    @Override
    public void updateSortedFilteredListToShowAll() {
        updateFilteredListToShowAll();
    }
    
    @Override
    public void updateSortedFilteredTaskList(Set<String> keywords){
        updateFilteredTaskList(new PredicateExpression(new DescQualifier(keywords)));
    }
    
    @Override
    public void sortSortedFilteredTaskListByPriority() {
    	sortedTasks.setComparator((Task t1, Task t2) -> t1.compareProperty(t2, TaskProperties.PRIORITY));
    }
    
    @Override
    public void unSortSortedFilteredTaskList() {
    	sortedTasks.setComparator(null);
    }
    
    @Override
    public int getIndexOfTask(ReadOnlyTask task) {
    	return sortedTasks.indexOf(task);
    }

    //=========== Filtered Task List Accessors ===============================================================

    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    public void updateFilteredListToShowAll() {
        filteredTasks.setPredicate(null);
    }

    public void updateFilteredTaskList(Set<String> keywords) {
        updateFilteredTaskList(new PredicateExpression(new DescQualifier(keywords)));
    }

    public void updateFilteredTaskList(HashMap<TaskProperties, Optional<TaskProperty>> propertiesToMatch) {
        updateFilteredTaskList(new PredicateExpression(new EnhancedSearchQualifier(propertiesToMatch)));
    }
    
    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }

    //========== Inner classes/interfaces used for filtering ==================================================

    interface Expression {
        boolean satisfies(ReadOnlyTask task);
        String toString();
    }

    private class PredicateExpression implements Expression {

        private final Qualifier qualifier;

        PredicateExpression(Qualifier qualifier) {
            this.qualifier = qualifier;
        }

        @Override
        public boolean satisfies(ReadOnlyTask task) {
            return qualifier.run(task);
        }

        @Override
        public String toString() {
            return qualifier.toString();
        }
    }

    interface Qualifier {
        boolean run(ReadOnlyTask task);
        String toString();
    }

    private class DescQualifier implements Qualifier {
        private Set<String> descKeyWords;

        DescQualifier(Set<String> descKeyWords) {
            this.descKeyWords = descKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return descKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(task.getDesc().get().getValue(), keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "desc=" + String.join(", ", descKeyWords);
        }
    }

    private class EnhancedSearchQualifier implements Qualifier {
        private HashMap<TaskProperties, Optional<TaskProperty>> propertiesToMatch;
        
        public EnhancedSearchQualifier(HashMap<TaskProperties, Optional<TaskProperty>> propertiesToMatch) {
            this.propertiesToMatch = propertiesToMatch;
        }
        
        @Override
        public boolean run(ReadOnlyTask task) {
            return task.matches(propertiesToMatch);
        }
    }
}
