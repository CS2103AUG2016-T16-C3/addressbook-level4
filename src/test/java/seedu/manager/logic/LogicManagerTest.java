package seedu.manager.logic;

import com.google.common.eventbus.Subscribe;

import seedu.manager.commons.core.CommandWord.Commands;
import seedu.manager.commons.core.EventsCenter;
import seedu.manager.commons.core.Messages;
import seedu.manager.commons.events.model.TaskManagerChangedEvent;
import seedu.manager.commons.events.ui.JumpToTagListRequestEvent;
import seedu.manager.commons.events.ui.JumpToTaskListRequestEvent;
import seedu.manager.commons.events.ui.ShowHelpRequestEvent;
import seedu.manager.commons.exceptions.IllegalValueException;
import seedu.manager.logic.Logic;
import seedu.manager.logic.LogicManager;
import seedu.manager.logic.commands.*;
import seedu.manager.logic.parser.ExtensionParser;
import seedu.manager.model.TaskManager;
import seedu.manager.model.Model;
import seedu.manager.model.ModelManager;
import seedu.manager.model.ReadOnlyTaskManager;
import seedu.manager.model.task.*;
import seedu.manager.model.task.Task.TaskProperties;
import seedu.manager.storage.StorageManager;
import seedu.manager.testutil.TestTask;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.manager.commons.core.Messages.*;

public class LogicManagerTest {

    /**
     * See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
     */
    @Rule
    public TemporaryFolder saveFolder = new TemporaryFolder();

    private Model model;
    private Logic logic;

    //These are for checking the correctness of the events raised
    private ReadOnlyTaskManager latestSavedTaskManager;
    private boolean helpShown;
    private int targetedTaskJumpIndex;
    private int targetedTagJumpIndex;

    @Subscribe
    private void handleLocalModelChangedEvent(TaskManagerChangedEvent abce) {
        latestSavedTaskManager = new TaskManager(abce.data);
    }

    @Subscribe
    private void handleShowHelpRequestEvent(ShowHelpRequestEvent she) {
        helpShown = true;
    }
    
    @Subscribe
    private void handleJumpToTaskListRequestEvent(JumpToTaskListRequestEvent je) {
    	targetedTaskJumpIndex = je.getTargetIndex();
    }
    
    @Subscribe
    private void handleJumpToTagListRequestEvent(JumpToTagListRequestEvent je) {
    	targetedTagJumpIndex = je.getTargetIndex();
    }

    @Before
    public void setup() {
        model = new ModelManager();
        String tempTaskManagerFile = saveFolder.getRoot().getPath() + "TempTaskManager.xml";
        String tempPreferencesFile = saveFolder.getRoot().getPath() + "TempPreferences.json";
        logic = new LogicManager(model, new StorageManager(tempTaskManagerFile, tempPreferencesFile));
        EventsCenter.getInstance().registerHandler(this);

        latestSavedTaskManager = new TaskManager(model.getTaskManager()); // last saved assumed to be up to date before.
        helpShown = false;
    }

    @After
    public void teardown() {
        EventsCenter.clearSubscribers();
    }

    @Test
    public void execute_invalid() throws Exception {
        String invalidCommand = "       ";
        assertCommandBehavior(invalidCommand,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
    }

    /**
     * Executes the command and confirms that the result message is correct.
     * Both the 'task manager' and the 'last shown list' are expected to be empty.
     * @see #assertCommandBehavior(String, String, ReadOnlyTaskManager, List)
     */
    private void assertCommandBehavior(String inputCommand, String expectedMessage) throws Exception {
        assertCommandBehavior(inputCommand, expectedMessage, new TaskManager(), Collections.emptyList());
    }

    /**
     * Executes the command and confirms that the result message is correct and
     * also confirms that the following three parts of the LogicManager object's state are as expected:<br>
     *      - the internal task manager data are same as those in the {@code expectedTaskManager} <br>
     *      - the backing list shown by UI matches the {@code shownList} <br>
     *      - {@code expectedTaskManager} was saved to the storage file. <br>
     */
    private void assertCommandBehavior(String inputCommand, String expectedMessage,
                                       ReadOnlyTaskManager expectedTaskManager,
                                       List<? extends ReadOnlyTask> expectedShownList) throws Exception {

        //Execute the command
        CommandResult result = logic.execute(inputCommand);
        
        //Confirm the ui display elements should contain the right data
        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(expectedShownList, model.getSortedFilteredTaskList());

        //Confirm the state of data (saved and in-memory) is as expected
        assertEquals(expectedTaskManager, model.getTaskManager());
        assertEquals(expectedTaskManager, latestSavedTaskManager);
    }


    @Test
    public void execute_unknownCommandWord() throws Exception {
        String unknownCommand = "uicfhmowqewca";
        assertCommandBehavior(unknownCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_help_successful() throws Exception {
        assertCommandBehavior("help", HelpCommand.SHOWING_HELP_MESSAGE);
        assertTrue(helpShown);
    }

    @Test
    public void execute_exit_successful() throws Exception {
        assertCommandBehavior("exit", ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT);
    }

    @Test
    public void execute_clear_successful() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        model.addTask(helper.generateTask(1));
        model.addTask(helper.generateTask(2));
        model.addTask(helper.generateTask(3));

        assertCommandBehavior("clear", ClearCommand.MESSAGE_SUCCESS, new TaskManager(), Collections.emptyList());
    }
    
    // @@author A0147924X
    @Test
    public void execute_sort_successful() throws Exception {
    	TestDataHelper helper = new TestDataHelper();
    	model.addTask(helper.generateTask(1));
    	model.addTask(helper.generateTask(2));
    	model.addTask(helper.generateTask(3));
    	
    	TaskManager expectedTM = new TaskManager();
        expectedTM.addTask(helper.generateTask(1));
        expectedTM.addTask(helper.generateTask(2));
        expectedTM.addTask(helper.generateTask(3));
        
        List<ReadOnlyTask> expectedList = new ArrayList<>(expectedTM.getTaskList());
        expectedList.sort((t1, t2) -> t1.compareProperty(t2, TaskProperties.PRIORITY));
        
        assertCommandBehavior("sort", SortCommand.MESSAGE_SUCCESS, expectedTM, expectedList);
    }
    
    
    @Test
    public void execute_add_invalidTaskData() throws Exception {
        assertCommandBehavior(
                "add Dinner with Lancelot venue Acceptable Venue priority wrong", Priority.MESSAGE_PRIORITY_CONSTRAINTS);
        assertCommandBehavior(
                "add venue No Description priority low", AddCommand.MESSAGE_USAGE);
    }

    @Test
    public void execute_addOnlyDesc_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.guinevere();
        TaskManager expectedTM = new TaskManager();
        expectedTM.addTask(toBeAdded);

        // execute command and verify result
        assertCommandBehavior(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedTM,
                expectedTM.getTaskList());
    }
    
    @Test
    public void execute_addDescContainsKeyword_successful() throws Exception {
    	// setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.morgana();
        TaskManager expectedTM = new TaskManager();
        expectedTM.addTask(toBeAdded);
        
        assertCommandBehavior(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedTM,
                expectedTM.getTaskList());
        assertEquals(0, targetedTaskJumpIndex);
    }
    
    @Test
    public void execute_addContainsEscapedKeyword_successful() throws Exception {
    	// setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.challenge();
        TaskManager expectedTM = new TaskManager();
        expectedTM.addTask(toBeAdded);
        
        assertCommandBehavior(
        		"add Come 'at' me venue Battlefield priority high",
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedTM,
                expectedTM.getTaskList());
        assertEquals(0, targetedTaskJumpIndex);
        
        toBeAdded = helper.canoodle();
        expectedTM.addTask(toBeAdded);
        
        assertCommandBehavior(
        		"add Secret rendezvous venue 'by' the wall",
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedTM,
                expectedTM.getTaskList());
        assertEquals(1, targetedTaskJumpIndex);
    }
    
    // @@author
    @Test
    public void execute_add_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.lancelot();
        TaskManager expectedTM = new TaskManager();
        expectedTM.addTask(toBeAdded);

        // execute command and verify result
        assertCommandBehavior(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded.getAsPrettyText()),
                expectedTM,
                expectedTM.getTaskList());
        assertEquals(0, targetedTaskJumpIndex);
        
        
        toBeAdded = helper.guinevere();
        expectedTM.addTask(toBeAdded);
        
        assertCommandBehavior(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded.getAsPrettyText()),
                expectedTM,
                expectedTM.getTaskList());
        assertEquals(1, targetedTaskJumpIndex);
    }

    @Test
    public void execute_addDuplicate_notAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.guinevere();
        TaskManager expectedTM = new TaskManager();
        expectedTM.addTask(toBeAdded);

        // setup starting state
        model.addTask(toBeAdded); // task already in internal task manager

        // execute command and verify result
        assertCommandBehavior(
                helper.generateAddCommand(toBeAdded),
                AddCommand.MESSAGE_DUPLICATE_TASK,
                expectedTM,
                expectedTM.getTaskList());
    }
    
    // @@author A0147924X
    @Test
    public void execute_addAfterSorting_successful() throws Exception {
    	TestDataHelper helper = new TestDataHelper();
    	Task toBeAdded = helper.guinevere();
    	helper.addToModel(model, 3);
    	
    	TaskManager expectedTM = new TaskManager();
    	helper.addToTaskManager(expectedTM, 3);
        expectedTM.addTask(toBeAdded);
        
        List<ReadOnlyTask> expectedList = new ArrayList<>(expectedTM.getTaskList());
        expectedList.sort((t1, t2) -> t1.compareProperty(t2, TaskProperties.PRIORITY));
        
        logic.execute("sort");
        
        assertCommandBehavior(
                helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded.getAsPrettyText()),
                expectedTM,
                expectedList);
        
        assertEquals(expectedList.indexOf(toBeAdded), targetedTaskJumpIndex);
    }
    
    @Test
    public void execute_editInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("edit", expectedMessage);
    }

    @Test
    public void execute_editIndexInvalid_errorMessageShown() throws Exception {
        String expectedMessage = Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
        assertCommandBehavior("edit 52 Dinner with Arthur", expectedMessage);
        assertCommandBehavior("edit 10 Dinner with Arthur", expectedMessage);
    }

    @Test
    public void execute_editInvalidFromToFormat_errorMessage() throws Exception {
        String expectedMessage = ExtensionParser.EXTENSION_FROM_TO_INVALID_FORMAT;
        assertCommandBehavior("edit 1 from 7:30-8:30", expectedMessage);
        assertCommandBehavior("edit 1 from 7:30", expectedMessage);
    }
    
    @Test
    public void execute_editDuplicateParams_errorMessage() throws Exception {
    	TestDataHelper helper = new TestDataHelper();
        TaskManager expectedTM = new TaskManager();
        Task toBeEdited = helper.guinevere();
        model.addTask(toBeEdited);
        expectedTM.addTask(toBeEdited);
        
        String editCommand = "edit 1 Picnic with Guinevere";
        
    	assertCommandBehavior(
                editCommand,
                String.format(EditCommand.MESSAGE_DUPLICATE_PARAMS, toBeEdited),
                expectedTM,
                expectedTM.getTaskList()
        );
    }

    @Test
    public void execute_edit_successful() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task toBeEdited = helper.lancelot();
        Task newTask = editTaskWithProperty(toBeEdited, TaskProperties.DESC, "Dinner with Guinevere");
        
        model.addTask(toBeEdited);
        model.addTask(helper.morgana());
        TaskManager expectedTM = new TaskManager();
        expectedTM.addTask(helper.morgana());
        expectedTM.addTask(newTask);

        String editCommand = "edit 1 Dinner with Guinevere";

        assertCommandBehavior(
                editCommand,
                String.format(EditCommand.MESSAGE_SUCCESS, newTask.getAsPrettyText()),
                expectedTM,
                expectedTM.getTaskList()
        );
        
        assertEquals(1, targetedTaskJumpIndex);
        
        
        Task newTask1 = editTaskWithProperty(newTask, TaskProperties.DESC, "Dinner with Lancelot");
        newTask1 = editTaskWithProperty(newTask1, TaskProperties.VENUE, "Avalon");

        expectedTM.removeTask(newTask);
        expectedTM.addTask(newTask1);

        String editCommand1 = "edit 2 Dinner with Lancelot venue Avalon";

        assertCommandBehavior(
                editCommand1,
                String.format(EditCommand.MESSAGE_SUCCESS, newTask1.getAsPrettyText()),
                expectedTM,
                expectedTM.getTaskList()
        );
        
        assertEquals(1, targetedTaskJumpIndex);
        
        
        Task newTask2 = editTaskWithProperty(newTask1, TaskProperties.STARTTIME, "7:30pm");
        newTask2 = editTaskWithProperty(newTask2, TaskProperties.ENDTIME, "8:50pm");
        newTask2 = editTaskWithProperty(newTask2, TaskProperties.PRIORITY, "low");

        expectedTM.removeTask(newTask1);
        expectedTM.addTask(newTask2);

        String editCommand2 = "edit 2 from 7:30pm to 8:50pm priority low";

        assertCommandBehavior(
                editCommand2,
                String.format(EditCommand.MESSAGE_SUCCESS, newTask2.getAsPrettyText()),
                expectedTM,
                expectedTM.getTaskList()
        );
        
        assertEquals(1, targetedTaskJumpIndex);
    }
    
    /**
     * Edit a certain property of a given task
     * @param taskToEdit Task that will be edited
     * @param property The property that will be edited
     * @param value The new value of the property
     * @return A new task with the edited property
     * @throws IllegalValueException
     */
    private Task editTaskWithProperty(Task taskToEdit, TaskProperties property, String value)
    		throws IllegalValueException {
    	HashMap<TaskProperties, Optional<String>> newProps = 
                taskToEdit.getPropertiesAsStrings();
        
        newProps.put(property, Optional.of(value));
        
        return new Task(newProps);
    }
    
    @Test
    public void execute_editAfterSorting_successful() throws Exception {
    	TestDataHelper helper = new TestDataHelper();
        Task toBeEdited = helper.lancelot();
    	model.addTask(helper.generateTask(2));
    	model.addTask(helper.generateTask(3));
    	model.addTask(toBeEdited);
    	
    	logic.execute("sort");
    	
    	TaskManager expectedTM = new TaskManager();
        expectedTM.addTask(helper.generateTask(2));
        expectedTM.addTask(helper.generateTask(3));
        
        HashMap<TaskProperties, Optional<String>> newProps = 
                toBeEdited.getPropertiesAsStrings();
        newProps.put(TaskProperties.DESC, Optional.of("Dinner with Guinevere"));
        
        Task newTask = new Task(newProps);
        expectedTM.addTask(newTask);

        String editCommand = "edit 2 Dinner with Guinevere";
        
        List<ReadOnlyTask> expectedList = new ArrayList<>(expectedTM.getTaskList());
        expectedList.sort((t1, t2) -> t1.compareProperty(t2, TaskProperties.PRIORITY));

        assertCommandBehavior(
                editCommand,
                String.format(EditCommand.MESSAGE_SUCCESS, newTask.getAsPrettyText()),
                expectedTM,
                expectedList
        );
        
        assertEquals(expectedList.indexOf(newTask), targetedTaskJumpIndex);
    }
    
    
    @Test
    public void execute_doneInvalidArgsFormat_errorMessageShown() throws Exception {
    	String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE);
    	assertIncorrectIndexFormatBehaviorForCommand("done", expectedMessage);
    }
    
    @Test
    public void execute_doneIndexInvalid_errorMessageShown() throws Exception {
    	String expectedMessage = Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
        assertCommandBehavior("done 52", expectedMessage);
        assertCommandBehavior("done 10", expectedMessage);
    }
    
    @Test
    public void execute_done_successful() throws Exception {
    	TestDataHelper helper = new TestDataHelper();
    	List<Task> threeTasks = helper.generateTaskList(3);
    	helper.addToModel(model, threeTasks);
    	TaskManager expectedTM = helper.generateTaskManager(threeTasks);
        
        Task doneTask = editTaskWithProperty(threeTasks.get(1), TaskProperties.DONE, "Yes");
        expectedTM.removeTask(threeTasks.get(1));
        expectedTM.addTask(doneTask);
        
        String doneCommand = "done 2";

        // execute command and verify result
        assertCommandBehavior(doneCommand,
                String.format(DoneCommand.MESSAGE_SUCCESS, doneTask),
                expectedTM,
                expectedTM.getTaskList());
        assertEquals(2, targetedTaskJumpIndex);
    }

    // @@author
    @Test
    public void execute_list_showsAllTasks() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        TaskManager expectedTM = helper.generateTaskManager(2);
        List<? extends ReadOnlyTask> expectedList = expectedTM.getTaskList();

        // prepare task manager state
        helper.addToModel(model, 2);

        assertCommandBehavior("list",
                ListCommand.MESSAGE_SUCCESS,
                expectedTM,
                expectedList);
    }


    /**
     * Confirms the 'invalid argument index number behaviour' for the given command
     * targeting a single task in the shown list, using visible index.
     * @param commandWord to test assuming it targets a single task in the last shown list based on visible index.
     */
    private void assertIncorrectIndexFormatBehaviorForCommand(String commandWord, String expectedMessage) throws Exception {
        assertCommandBehavior(commandWord , expectedMessage); //index missing
        assertCommandBehavior(commandWord + " +1", expectedMessage); //index should be unsigned
        assertCommandBehavior(commandWord + " -1", expectedMessage); //index should be unsigned
        assertCommandBehavior(commandWord + " 0", expectedMessage); //index cannot be 0
        assertCommandBehavior(commandWord + " not_a_number", expectedMessage);
    }

    /**
     * Confirms the 'invalid argument index number behaviour' for the given command
     * targeting a single task in the shown list, using visible index.
     * @param commandWord to test assuming it targets a single task in the last shown list based on visible index.
     */
    private void assertIndexNotFoundBehaviorForCommand(String commandWord) throws Exception {
        String expectedMessage = MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
        TestDataHelper helper = new TestDataHelper();
        List<Task> taskList = helper.generateTaskList(2);

        // set TM state to 2 tasks
        model.resetData(new TaskManager());
        for (Task p : taskList) {
            model.addTask(p);
        }

        assertCommandBehavior(commandWord + " 3", expectedMessage, model.getTaskManager(), taskList);
    }

    @Test
    public void execute_deleteInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("delete", expectedMessage);
    }

    @Test
    public void execute_deleteIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("delete");
    }

    @Test
    public void execute_deleteIndexInvalid_errorMessageShown() throws Exception {
        String expectedMessage = Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
        assertCommandBehavior("delete 52", expectedMessage);
        assertCommandBehavior("delete 10", expectedMessage);
    }

    @Test
    public void execute_delete_removesCorrectTask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);

        TaskManager expectedTM = helper.generateTaskManager(threeTasks);
        expectedTM.removeTask(threeTasks.get(1));
        helper.addToModel(model, threeTasks);

        assertCommandBehavior("delete 2",
                String.format(DeleteCommand.MESSAGE_SUCCESS, threeTasks.get(1).getAsPrettyText()),
                expectedTM,
                expectedTM.getTaskList());
    }
    
    @Test
    public void execute_undo_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeUndone = helper.lancelot();
        
        // execute command and verify result
        logic.execute(helper.generateAddCommand(toBeUndone));
        assertCommandBehavior("undo",String.format(AddCommand.UNDO_SUCCESS, toBeUndone));
    }


    @Test
    public void execute_find_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
        assertCommandBehavior("find ", expectedMessage);
    }

    @Test
    public void execute_find_onlyMatchesFullWordsInDescs() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateTaskWithDesc("bla bla KEY bla");
        Task pTarget2 = helper.generateTaskWithDesc("bla KEY bla bceofeia");
        Task p1 = helper.generateTaskWithDesc("KE Y");
        Task p2 = helper.generateTaskWithDesc("KEYKEYKEY sduauo");

        List<Task> fourTasks = helper.generateTaskList(p1, pTarget1, p2, pTarget2);
        TaskManager expectedTM = helper.generateTaskManager(fourTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget2);
        helper.addToModel(model, fourTasks);

        assertCommandBehavior("find KEY",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedTM,
                expectedList);
    }

    @Test
    public void execute_find_isNotCaseSensitive() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task p1 = helper.generateTaskWithDesc("bla bla KEY bla");
        Task p2 = helper.generateTaskWithDesc("bla KEY bla bceofeia");
        Task p3 = helper.generateTaskWithDesc("key key");
        Task p4 = helper.generateTaskWithDesc("KEy sduauo");

        List<Task> fourTasks = helper.generateTaskList(p3, p1, p4, p2);
        TaskManager expectedTM = helper.generateTaskManager(fourTasks);
        List<Task> expectedList = fourTasks;
        helper.addToModel(model, fourTasks);

        assertCommandBehavior("find KEY",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedTM,
                expectedList);
    }

    @Test
    public void execute_find_matchesIfAnyKeywordPresent() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateTaskWithDesc("bla bla KEY bla");
        Task pTarget2 = helper.generateTaskWithDesc("bla rAnDoM bla bceofeia");
        Task pTarget3 = helper.generateTaskWithDesc("key key");
        Task p1 = helper.generateTaskWithDesc("sduauo");

        List<Task> fourTasks = helper.generateTaskList(pTarget1, p1, pTarget2, pTarget3);
        TaskManager expectedTM = helper.generateTaskManager(fourTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget2, pTarget3);
        helper.addToModel(model, fourTasks);

        assertCommandBehavior("find key rAnDoM",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedTM,
                expectedList);
    }
    
    // @@author A0147924X
    @Test
    public void execute_findThenSort_successful() throws Exception {
    	TestDataHelper helper = new TestDataHelper();
        Task p1 = helper.generateTaskWithDesc("bla bla bla bla");
        Task p2 = helper.generateTaskWithDesc("bla KEY bla bceofeia");
        Task p3 = helper.generateTaskWithDesc("key key");
        Task p4 = helper.generateTaskWithDesc("KEy sduauo");

        List<Task> fourTasks = helper.generateTaskList(p3, p1, p4, p2);
        TaskManager expectedTM = helper.generateTaskManager(fourTasks);
        helper.addToModel(model, fourTasks);
        
        
        List<Task> expectedList = helper.generateTaskList(p3, p4, p2);

        assertCommandBehavior("find KEY",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedTM,
                expectedList);
        
        expectedList.sort((t1, t2) -> t1.compareProperty(t2, TaskProperties.PRIORITY));
        
        assertCommandBehavior("sort", SortCommand.MESSAGE_SUCCESS, expectedTM, expectedList);
    }
    
    // @@author
    @Test
    public void execute_findVenue_successful() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task p1 = helper.generateTaskWithVenue("Arts");
        Task p2 = helper.generateTaskWithVenue("Biz");
        Task p3 = helper.generateTaskWithVenue("Com");
        Task p4 = helper.generateTaskWithVenue("");
        
        List<Task> fourTasks = helper.generateTaskList(p3, p1, p4, p2);
        TaskManager expectedTM = helper.generateTaskManager(fourTasks);
        helper.addToModel(model, fourTasks);
        
        
        List<Task> expectedList = helper.generateTaskList(p3);
        
        assertCommandBehavior("find venue Com",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedTM,
                expectedList);
    }
    
    @Test
    public void execute_find_noTasksListed() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task p1 = helper.generateTaskWithVenue("Engin");
        Task p2 = helper.generateTaskWithPriority("high");
        Task p3 = helper.generateTaskWithEndTime("6pm");
        Task p4 = helper.generateTaskWithStartTime("8am");
        
        List<Task> fourTasks = helper.generateTaskList(p3, p1, p4, p2);
        TaskManager expectedTM = helper.generateTaskManager(fourTasks);
        helper.addToModel(model, fourTasks);
        
        
        List<Task> expectedList = helper.generateTaskList();
        
        assertCommandBehavior("find venue Com",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedTM,
                expectedList);
    }
    
    @Test
    public void execute_find_invalidExtension() throws Exception {
        assertCommandBehavior("find priority abc", Priority.MESSAGE_PRIORITY_CONSTRAINTS);
    }
    
    // @@author A0147924X
    @Test
    public void execute_findTag_successful() throws Exception {
    	TestDataHelper helper = new TestDataHelper();
        Task p1 = helper.generateTaskWithTagAndDesc("Kill Mordred", "Violent");
        Task p2 = helper.guinevere();
        Task p3 = helper.lancelot();
        Task p4 = helper.morgana();
        Task p5 = helper.generateTaskWithTagAndDesc("Elope with Guinevere", "Home-wrecking");
        
        List<Task> fiveTasks = helper.generateTaskList(p5, p3, p1, p4, p2);
        TaskManager expectedTM = helper.generateTaskManager(fiveTasks);
        helper.addToModel(model, fiveTasks);
        model.addTag(new Tag("Home-wrecking"));
        model.addTag(new Tag("Violent"));
        
        List<Task> expectedList = helper.generateTaskList(p1);
        
        assertCommandBehavior("find tag Violent",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedTM,
                expectedList);
        assertEquals(1, targetedTagJumpIndex);
    }
    
    // @@author
    @Test
    public void execute_findStartTime_successful() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task p1 = helper.generateTaskWithStartTime("2.30pm");
        Task p2 = helper.generateTaskWithStartTime("11.30am");
        Task p3 = helper.generateTaskWithStartTime("12.45pm");
        Task p4 = helper.generateTaskWithStartTime("8am");
        
        List<Task> fourTasks = helper.generateTaskList(p3, p1, p4, p2);
        TaskManager expectedTM = helper.generateTaskManager(fourTasks);
        helper.addToModel(model, fourTasks);
        
        
        List<Task> expectedList = helper.generateTaskList(p1);
        
        assertCommandBehavior("find at 2pm",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedTM,
                expectedList);
    }

    @Test
    public void execute_findEndTime_successful() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task p1 = helper.generateTaskWithEndTime("3.30pm");
        Task p2 = helper.generateTaskWithEndTime("10.30am");
        Task p3 = helper.generateTaskWithEndTime("1.45pm");
        Task p4 = helper.generateTaskWithEndTime("8pm");
        
        List<Task> fourTasks = helper.generateTaskList(p2, p3, p1, p4);
        TaskManager expectedTM = helper.generateTaskManager(fourTasks);
        helper.addToModel(model, fourTasks);
        
        
        List<Task> expectedList = helper.generateTaskList(p2, p3, p1);
        
        assertCommandBehavior("find by 4pm",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedTM,
                expectedList);
    }
    
    @Test
    public void execute_findStartAndEndTime_successful() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task p1 = helper.generateTaskWithStartAndEndTime("5.15pm", "6.15pm");
        Task p2 = helper.generateTaskWithStartAndEndTime("7.30am", "10.45am");
        Task p3 = helper.generateTaskWithStartAndEndTime("1.55pm", "3pm");
        Task p4 = helper.generateTaskWithStartAndEndTime("9.30pm", "10.45pm");
        
        List<Task> fourTasks = helper.generateTaskList(p2, p1, p4, p3);
        TaskManager expectedTM = helper.generateTaskManager(fourTasks);
        helper.addToModel(model, fourTasks);
        
        
        List<Task> expectedList = helper.generateTaskList(p4);
        
        assertCommandBehavior("find from 9pm to 11pm",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedTM,
                expectedList);
    }
    
    @Test
    public void execute_findDescAndVenue_successful() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task p1 = helper.generateTaskWithDescAndVenue("girl", "uptown");
        Task p2 = helper.generateTaskWithDescAndVenue("my love", "an empty street");
        Task p3 = helper.generateTaskWithDescAndVenue("funk", "uptown");
        Task p4 = helper.generateTaskWithDescAndVenue("Road trip", "Genting Highlands");
        
        List<Task> fourTasks = helper.generateTaskList(p2, p4, p1, p3);
        TaskManager expectedTM = helper.generateTaskManager(fourTasks);
        helper.addToModel(model, fourTasks);
        
        
        List<Task> expectedList = helper.generateTaskList(p4);
        
        assertCommandBehavior("find Road venue Genting",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedTM,
                expectedList);
    }
    
    @Test
    public void execute_findVenueThenDesc_successful() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task p1 = helper.generateTaskWithDescAndVenue("girl", "uptown");
        Task p2 = helper.generateTaskWithDescAndVenue("my love", "an empty street");
        Task p3 = helper.generateTaskWithDescAndVenue("funk", "uptown");
        Task p4 = helper.generateTaskWithDescAndVenue("Road trip", "Genting Highlands");
        
        List<Task> fourTasks = helper.generateTaskList(p2, p4, p1, p3);
        TaskManager expectedTM = helper.generateTaskManager(fourTasks);
        helper.addToModel(model, fourTasks);
        
        
        List<Task> expectedList = helper.generateTaskList(p1, p3);
        
        assertCommandBehavior("find venue uptown",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedTM,
                expectedList);
    }
    
    @Test
    public void execute_findPriority_successful() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task p1 = helper.generateTaskWithPriority("low");
        Task p2 = helper.generateTaskWithPriority("med");
        Task p3 = helper.generateTaskWithPriority("high");
        Task p4 = helper.generateTaskWithPriority("");
        
        List<Task> fourTasks = helper.generateTaskList(p1, p2, p3, p4);
        TaskManager expectedTM = helper.generateTaskManager(fourTasks);
        helper.addToModel(model, fourTasks);
        
        
        List<Task> expectedList = helper.generateTaskList(p3);
        
        assertCommandBehavior("find priority high",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedTM,
                expectedList);
    }
    
    // @@author A0147924X
    @Test
    public void execute_alias_wrongNumberOfCommands() throws Exception {
        assertCommandBehavior("alias", AliasCommand.MESSAGE_WRONG_NUM_ARGS);
        assertCommandBehavior("alias add", AliasCommand.MESSAGE_WRONG_NUM_ARGS);
        assertCommandBehavior("alias add + -", AliasCommand.MESSAGE_WRONG_NUM_ARGS);
    }
    
    @Test
    public void execute_alias_doesNotExist() throws Exception {
        assertCommandBehavior("alias - +", AliasCommand.MESSAGE_NO_MATCH);
    }
    
    @Test
    public void execute_alias_alreadyTaken() throws Exception {
        assertCommandBehavior("alias add +", String.format(AliasCommand.MESSAGE_SUCCESS, "add", "+"));
        assertCommandBehavior("alias edit +", String.format(AliasCommand.MESSAGE_ALIAS_TAKEN, Commands.ADD));
    }
    
    @Test
    public void execute_alias_successful() throws Exception {
        assertCommandBehavior("alias add +", String.format(AliasCommand.MESSAGE_SUCCESS, "add", "+"));
        
        TestDataHelper helper = new TestDataHelper();
        TaskManager expectedTM = new TaskManager();
        Task toBeAdded = helper.lancelot();
        expectedTM.addTask(toBeAdded);

        // execute command and verify result
        assertCommandBehavior(helper.generateAddCommandWithAlias(toBeAdded, "+"),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded.getAsPrettyText()),
                expectedTM,
                expectedTM.getTaskList());
        assertEquals(0, targetedTaskJumpIndex);
        
        
        assertCommandBehavior("alias delete -",
                String.format(AliasCommand.MESSAGE_SUCCESS, "delete", "-"),
                expectedTM,
                expectedTM.getTaskList());
        expectedTM.removeTask(toBeAdded);
        
        assertCommandBehavior("- 1",
                String.format(DeleteCommand.MESSAGE_SUCCESS, toBeAdded.getAsPrettyText()),
                expectedTM,
                expectedTM.getTaskList());
        
        
        assertCommandBehavior("alias priority p",
                String.format(AliasCommand.MESSAGE_SUCCESS, "priority", "p"),
                expectedTM,
                expectedTM.getTaskList());
        
        toBeAdded = helper.morgana();
        expectedTM.addTask(toBeAdded);
        
        assertCommandBehavior(helper.generateAddCommandWithAlias(toBeAdded, "+").replace("priority", "p"),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded.getAsPrettyText()),
                expectedTM,
                expectedTM.getTaskList());
        assertEquals(0, targetedTaskJumpIndex);
        
        // cleanup
        logic.execute("alias + add");
        logic.execute("alias - delete");
        logic.execute("alias p priority");
    }
    
    /**
     * A utility class to generate test data.
     */
    class TestDataHelper{

        Task guinevere() throws Exception {
            return new Task("Picnic with Guinevere", "", "", "", "", "", "");
        }


        Task lancelot() throws Exception {
            return new Task("Joust with Lancelot", "Avalon", "med", "7:30", "8:30", "", "");
        }
        
        Task morgana() throws Exception {
        	return new Task("Flatten Morgana", "Camelot", "high", "", "", "", "");
        }
        
        Task challenge() throws Exception {
        	return new Task("Come at me", "Battlefield", "high", "", "", "", "");
        }
        
        Task canoodle() throws Exception {
        	return new Task("Secret rendezvous", "by the wall", "", "", "", "", "");
        }

        /**
         * Generates a valid task using the given seed.
         * Running this function with the same parameter values guarantees the returned task will have the same state.
         * Each unique seed will generate a unique Task object.
         *
         * @param seed used to generate the task data field values
         */
        Task generateTask(int seed) throws Exception {
            return new Task(
                    "Task " + seed,
                    "" + Math.abs(seed),
                    new String[] {"low", "med", "high"}[seed % 3],
                    (Math.abs(seed) % 12 + 1) + ":00",
                    (Math.abs(seed) % 12 + 1) + ":00", 
                    "",
                    ""
            );
        }
        
        /** Generates the correct add command  */
        String generateAddCommandWithAlias(Task p, String alias) {
        	StringBuffer cmd = new StringBuffer();

            cmd.append(alias + " ");

            cmd.append(p.getDesc().get().toString());
            if (p.getVenue().isPresent()) {
                cmd.append(" venue ").append(p.getVenue().get().toString());
            }
            if (p.getPriority().isPresent()) {
                cmd.append(" priority ").append(p.getPriority().get().toString());
            }
            if (p.getStartTime().isPresent()) {
                cmd.append(" from ").append(p.getStartTime().get().toString());
            }
            if (p.getEndTime().isPresent()) {
                cmd.append(" to ").append(p.getEndTime().get().toString());
            }
            if (p.getTag().isPresent()) {
                cmd.append(" tag ").append(p.getTag().get().toString());
            }

            return cmd.toString();
        }

        /** Generates the correct add command based on the task given */
        String generateAddCommand(Task p) {
        	return generateAddCommandWithAlias(p, "add");
        }
        
        // @@author
        /**
         * Generates an TaskManager with auto-generated tasks.
         */
        TaskManager generateTaskManager(int numGenerated) throws Exception{
            TaskManager taskManager = new TaskManager();
            addToTaskManager(taskManager, numGenerated);
            return taskManager;
        }

        /**
         * Generates an TaskManager based on the list of Tasks given.
         */
        TaskManager generateTaskManager(List<Task> tasks) throws Exception{
            TaskManager taskManager = new TaskManager();
            addToTaskManager(taskManager, tasks);
            return taskManager;
        }

        /**
         * Adds auto-generated Task objects to the given TaskManager
         * @param taskManager The TaskManager to which the Tasks will be added
         */
        void addToTaskManager(TaskManager taskManager, int numGenerated) throws Exception{
            addToTaskManager(taskManager, generateTaskList(numGenerated));
        }

        /**
         * Adds the given list of Tasks to the given TaskManager
         */
        void addToTaskManager(TaskManager taskManager, List<Task> tasksToAdd) throws Exception{
            for(Task p: tasksToAdd){
                taskManager.addTask(p);
            }
        }

        /**
         * Adds auto-generated Task objects to the given model
         * @param model The model to which the Tasks will be added
         */
        void addToModel(Model model, int numGenerated) throws Exception{
            addToModel(model, generateTaskList(numGenerated));
        }

        /**
         * Adds the given list of Tasks to the given model
         */
        void addToModel(Model model, List<Task> tasksToAdd) throws Exception{
            for(Task p: tasksToAdd){
                model.addTask(p);
            }
        }

        /**
         * Generates a list of Tasks based on the flags.
         */
        List<Task> generateTaskList(int numGenerated) throws Exception{
            List<Task> tasks = new ArrayList<>();
            for(int i = 1; i <= numGenerated; i++){
                tasks.add(generateTask(i));
            }
            return tasks;
        }

        List<Task> generateTaskList(Task... tasks) {
            return Arrays.asList(tasks);
        }

        /**
         * Generates a Task object with given desc. Other fields will have some dummy values.
         */
        Task generateTaskWithDesc(String desc) throws Exception {
            return new Task(
                    desc,
                    "Some Venue",
                    "low",
                    "7:30pm",
                    "8:30pm",
                    "",
                    ""
            );
        }
        
        /**
         * Generates a Task object with given venue. Other fields will have some dummy values.
         */
        Task generateTaskWithVenue(String venue) throws Exception {
            return new Task(
                    "boo",
                    venue,
                    "med",
                    "10pm",
                    "11pm",
                    "",
                    ""
            );
        }
        
        /**
         * Generates a Task object with given priority. Other fields will have some dummy values.
         */
        Task generateTaskWithPriority(String priority) throws Exception {
            return new Task(
                    "hello it's me",
                    "somewhere over the rainbow",
                    priority,
                    "10am",
                    "12pm",
                    "",
                    ""
            );
        }
        
        /**
         * Generates a Task object with given start time. Other fields will have some dummy values.
         */
        Task generateTaskWithStartTime(String startTime) throws Exception {
            return new Task(
                    "hello",
                    "from the other side",
                    "high",
                    startTime,
                    "3pm",
                    "",
                    ""
            );
        }
        
        /**
         * Generates a Task object with given end time. Other fields will have some dummy values.
         */
        Task generateTaskWithEndTime(String endTime) throws Exception {
            return new Task(
                    "don't let me down",
                    "chainsmokers",
                    "low",
                    "9am",
                    endTime,
                    "",
                    ""
            );
        }
        
        /**
         * Generates a Task object with given start and end time. Other fields will have some dummy values.
         */
        Task generateTaskWithStartAndEndTime(String startTime, String endTime) throws Exception {
            return new Task(
                    "falling down",
                    "london bridge",
                    "high",
                    startTime,
                    endTime,
                    "",
                    ""
            );
        }
        
        /**
         * Generates a Task object with given desc and venue. Other fields will have some dummy values.
         */
        Task generateTaskWithDescAndVenue(String desc, String venue) throws Exception {
            return new Task(
                    desc,
                    venue,
                    "med",
                    "4.30am",
                    "7am",
                    "",
                    ""
            );
        }
        
        /**
         * Generates a Task object with given tag. Other fields will have some dummy values.
         */
        Task generateTaskWithTagAndDesc(String desc, String tag) throws Exception {
            return new Task(
                    desc,
                    "Camelot",
                    "med",
                    "4.30am",
                    "7am",
                    "",
                    tag
            );
        }
    }
}
