package seedu.manager.commons.events.ui;

import seedu.manager.commons.events.BaseEvent;
import seedu.manager.model.task.Tag;

/**
 * Represents a selection change in the Task List Panel
 */
public class TagPanelSelectionChangedEvent extends BaseEvent {


    private final Tag newSelection;

    public TagPanelSelectionChangedEvent(Tag newSelection){
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public Tag getNewSelection() {
        return newSelection;
    }
}
