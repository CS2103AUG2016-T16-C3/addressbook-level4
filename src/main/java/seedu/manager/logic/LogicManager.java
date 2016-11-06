package seedu.manager.logic;

import javafx.collections.ObservableList;
import seedu.manager.commons.core.ComponentManager;
import seedu.manager.commons.core.LogsCenter;
import seedu.manager.commons.events.logic.CommandWordsChangedEvent;
import seedu.manager.logic.commands.Command;
import seedu.manager.logic.commands.CommandResult;

import seedu.manager.logic.parser.Parser;
import seedu.manager.model.Model;
import seedu.manager.model.task.ReadOnlyTask;
import seedu.manager.model.task.Tag;
import seedu.manager.storage.Storage;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Parser parser;
    
    // @@author A0147924X
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.parser = new Parser(model.getCommandWords(), model.getExtensionWords());
    }
    
    // @@author
    @Override
    public CommandResult execute(String commandText) {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = parser.parseCommand(commandText);
        command.setData(model);
        
        return command.execute();
    }

    @Override
    public ObservableList<ReadOnlyTask> getSortedFilteredTaskList() {
        return model.getSortedFilteredTaskList();
    }
    
    // @@author A0148042M
    @Override
    /**
     * Update the task list that contains selected tag
     */
    public void updateTaskListWhenTagSelected(Tag selectedTag) {
        model.updateFilteredTaskList(selectedTag);
    }
    
    @Override
    /**
     * Return the sorted and filtered tag list.
     */
    public ObservableList<Tag> getSortedFilteredTagList() {
        return model.getSortedFilteredTagList();        
    }
    
    // @@author A0147924X
    @Subscribe
    /**
     * Updates parser with the new command words when these are changed
     * @param event
     */
    public void handleCommandWordsChangedEvent(CommandWordsChangedEvent event) {
    	parser.compileRegexes();
    }
}
