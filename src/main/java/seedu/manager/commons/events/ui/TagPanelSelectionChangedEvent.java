package seedu.manager.commons.events.ui;

import seedu.manager.commons.events.BaseEvent;
import seedu.manager.model.task.Tag;

/**
 * @@author A0148042M
 * Represents a selection change in the Tag List Panel
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
